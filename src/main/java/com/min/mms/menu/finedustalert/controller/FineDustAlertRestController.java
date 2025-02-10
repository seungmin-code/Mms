package com.min.mms.menu.finedustalert.controller;

import com.min.mms.common.component.CommonComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/finedustalert")
public class FineDustAlertRestController {

    private final CommonComponent commonComponent;
    private static final Logger logger = LoggerFactory.getLogger(FineDustAlertRestController.class);

    public FineDustAlertRestController(CommonComponent commonComponent) {
        this.commonComponent = commonComponent;
    }

    @GetMapping("fetchApiCallData")
    public ResponseEntity<Map<String, Object>> fetchApiCallData(@RequestParam String itemCode) {
        Map<String, Object> response = new HashMap<>();

        return ResponseEntity.ok(response);
    }


}
