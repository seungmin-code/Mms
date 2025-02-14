package com.min.mms.menu.gismap.controller;

import com.min.mms.menu.gismap.service.GisMapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gismap")
public class GisMapRestController {

    private final GisMapService gisMapService;

    public GisMapRestController(GisMapService gisMapService) {
        this.gisMapService = gisMapService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> stationMarkerData() {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> stationMarkerData = gisMapService.getStationMarkerData();
        response.put("data", stationMarkerData);
        response.put("message", "측정소 좌표 데이터를 성공적으로 가져왔습니다");
        return ResponseEntity.status(200).body(response);
    }

}
