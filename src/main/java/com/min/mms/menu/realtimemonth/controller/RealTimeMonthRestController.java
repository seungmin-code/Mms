package com.min.mms.menu.realtimemonth.controller;

import com.fasterxml.jackson.databind.JsonNode;

import com.min.mms.common.component.CommonComponent;
import com.min.mms.menu.realtimemonth.service.RealTimeMonthService;
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
 * 측정소별 월평균 데이터 API를 제공하는 컨트롤러
 */
@RestController
@RequestMapping("/realTimeMonth")
public class RealTimeMonthRestController {

    @Value("${DataPotalApiKey}")
    private String serviceKey;

    private final CommonComponent commonComponent;
    private final RealTimeMonthService realTimeMonthService;

    public RealTimeMonthRestController(CommonComponent commonComponent, RealTimeMonthService realTimeMonthService) {
        this.commonComponent = commonComponent;
        this.realTimeMonthService = realTimeMonthService;
    }

    private static final Logger logger = LoggerFactory.getLogger(RealTimeMonthRestController.class);

    /**
     * 
     * @param inqBginMm 조회시작일자
     * @param inqEndMm 조회종료일자
     * @param msrstnName 관측소명
     * @return 반환결과
     */
    @GetMapping("fetchApiCallData")
    public ResponseEntity<Map<String, Object>> fetchApiCallData(
            @RequestParam String inqBginMm,
            @RequestParam String inqEndMm,
            @RequestParam String msrstnName
    ) {
        Map<String, Object> response = new HashMap<>();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("url", "https://apis.data.go.kr/B552584/ArpltnStatsSvc/getMsrstnAcctoRMmrg");
        parameters.put("serviceKey", serviceKey);
        parameters.put("returnType", "json");
        parameters.put("pageNo", "1");
        parameters.put("numOfRows", "100");
        parameters.put("inqBginMm", inqBginMm);
        parameters.put("inqEndMm", inqEndMm);
        parameters.put("msrstnName", msrstnName);

        JsonNode apiResponse = commonComponent.apiCall(parameters);

        JsonNode items = apiResponse.path("response").path("body").path("items");

        response.put("data", items);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
