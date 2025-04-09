package com.min.mms.menu.realtimesido.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 미세먼지 실시간 평균 시도별 API를 제공하는 컨트롤러
 */
@RestController
@RequestMapping("/realTimeSido")
public class RealTimeSidoRestController {

    @Value("${DataPotalApiKey}")
    private String serviceKey;

    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(RealTimeSidoRestController.class);

    public RealTimeSidoRestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 
     * @param itemCode 미세먼지 또는 초미세먼지 셀렉트박스 선택 값
     * @param dataGubun 시간평균 또는 일평균 셀렉트박스 선택 값
     * @param searchCondition  dataGubun값이 일평균일때 일주일 또는 한달단위 셀렉트박스 선택 값
     * @return 반환 결과
     */
    @GetMapping("/callRealTimeSidoApi")
    public ResponseEntity<Map<String, Object>> callRealTimeSidoApi(
            @RequestParam String itemCode,  // (SO2, CO, O3, NO2, PM10, PM2.5)
            @RequestParam String dataGubun, // (시간평균: HOUR, 일평균: DAILY)
            @RequestParam(required = false) String searchCondition // (일주일: WEEK, 한달: MONTH)
    ) {
        Map<String, Object> response = new HashMap<>();

        if (searchCondition == null) {
            searchCondition = "";
        }

        String url = "https://apis.data.go.kr/B552584/ArpltnStatsSvc/getCtprvnMesureLIst";
        String returnType = "json";

        int pageNo = 1; // 페이지 번호
        int numOfRows = 1000; // 한 페이지 결과 수

        String apiUrl = String.format(
                "%s?serviceKey=%s&returnType=%s&numOfRows=%d&pageNo=%d&itemCode=%s&dataGubun=%s&searchCondition=%s",
                url, serviceKey, returnType, numOfRows, pageNo, itemCode, dataGubun, searchCondition
        );

        try {
            // API 호출
            logger.info("Calling API: {}", apiUrl);
            ResponseEntity<JsonNode> apiCallResponse = restTemplate.getForEntity(URI.create(apiUrl), JsonNode.class);

            // API 호출 결과 체크
            if (apiCallResponse.getStatusCode() != HttpStatus.OK) {
                throw new IllegalStateException("API 호출이 실패했습니다. 상태 코드: " + apiCallResponse.getStatusCode());
            }

            // JSON 데이터에서 'items' 추출
            JsonNode items = Optional.ofNullable(apiCallResponse.getBody())
                    .map(body -> body.path("response").path("body").path("items"))
                    .orElseThrow(() -> new IllegalStateException("응답에 'items' 데이터가 없습니다"));

            // 성공실패 결과 반환
            if (items.isArray() && !items.isEmpty()) {
                response.put("items", items);
                response.put("itemCode", itemCode);  // 프론트로 넘길 파라미터 추가
                response.put("dataGubun", dataGubun); // 프론트로 넘길 파라미터 추가
                response.put("searchCondition", searchCondition); // 프론트로 넘길 파라미터 추가
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response); // NO CONTENT 상태 코드 반환
            }

        } catch (IllegalStateException e) {
            // API 호출 중 오류 발생
            logger.error("API 호출 중 오류: {}", e.getMessage(), e);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 400 오류 상태 코드 반환
        } catch (Exception e) {
            // 예상치 못한 오류 발생
            logger.error("예상치 못한 오류: {}", e.getMessage(), e);
            response.put("error", "예상치 못한 오류가 발생했습니다");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 500 오류 상태 코드 반환
        }
    }

}


