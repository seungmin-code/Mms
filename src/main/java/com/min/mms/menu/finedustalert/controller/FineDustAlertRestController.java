package com.min.mms.menu.finedustalert.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.min.mms.common.component.CommonComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 미세먼지 경보이력 API를 제공하는 컨트롤러
 */
@RestController
@RequestMapping("/finedustalert")
public class FineDustAlertRestController {

    @Value("${DataPotalApiKey}")
    private String serviceKey;

    private final CommonComponent commonComponent;
    private static final Logger logger = LoggerFactory.getLogger(FineDustAlertRestController.class);

    public FineDustAlertRestController(CommonComponent commonComponent) {
        this.commonComponent = commonComponent;
    }

    /**
     * 공공데이터포털에서 미세먼지 경보이력을 가져오는 메소드
     * @param year 조회년도
     * @return 반환결과
     */
    @GetMapping("fetchApiCallData")
    public ResponseEntity<Map<String, Object>> fetchApiCallData(@RequestParam String year) {
        Map<String, Object> response = new HashMap<>();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("url", "https://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo");
        parameters.put("serviceKey", serviceKey);
        parameters.put("returnType", "json");
        parameters.put("pageNo", "1");
        parameters.put("numOfRows", "100");
        parameters.put("year", year);
        parameters.put("itemCode", "");

        JsonNode apiResponse = commonComponent.apiCall(parameters);

        JsonNode items = apiResponse.path("response").path("body").path("items");

        response.put("data", items);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
