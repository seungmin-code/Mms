package com.min.mms.menu.realtimemonth.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.min.mms.menu.realtimesido.controller.RealTimeSidoRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/realTimeMonth")
public class RealTimeMonthRestController {

    @Value("${DataPotalApiKey}")
    private String serviceKey;

    /* RestTemplate 인스턴스 */
    private final RestTemplate restTemplate;

    /* 로그백 로거 */
    private static final Logger logger = LoggerFactory.getLogger(RealTimeMonthRestController.class);

    public RealTimeMonthRestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("fetchData")
    public ResponseEntity<Map<String, Object>> realTimeMonthFetchData() {
        Map<String, Object> response = new HashMap<>();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("url", "https://apis.data.go.kr/B552584/ArpltnStatsSvc/getMsrstnAcctoRMmrg");
        parameters.put("serviceKey", serviceKey);
        parameters.put("returnType", "json");
        parameters.put("pageNo", "1");
        parameters.put("numOfRows", "100");
        parameters.put("inqBginMm", "202409");
        parameters.put("inqEndMm", "202501");
        parameters.put("msrstnName", "강남구");

        response = apiCall(parameters);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public Map<String, Object> apiCall(Map<String, Object> parameters) {
        Map<String, Object> response = new HashMap<>();

        String url = parameters.get("url") + "?";
        String apiUrl = buildApiUrl(url, parameters);
        logger.info("API 호출 : {}", apiUrl);
        ResponseEntity<JsonNode> apiResponse = restTemplate.getForEntity(URI.create(apiUrl), JsonNode.class);
        logger.info("API 응답 : {}", apiResponse);

        return response;
    }

    public String buildApiUrl(String url, Map<String, Object> parameters) {
        StringBuilder apiUrl = new StringBuilder(url);

        parameters.remove("url");
        parameters.forEach((key, value) -> {
            apiUrl.append("&").append(key).append("=").append(value);
        });

        return apiUrl.toString();
    }

}
