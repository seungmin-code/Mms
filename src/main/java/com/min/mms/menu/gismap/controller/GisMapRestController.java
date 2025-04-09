package com.min.mms.menu.gismap.controller;

import com.min.mms.menu.gismap.service.GisMapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GIS 구성을 위해 측정소 데이터를 제공하기 위한 컨트롤러
 */
@RestController
@RequestMapping("/gismap")
public class GisMapRestController {

    private final GisMapService gisMapService;

    public GisMapRestController(GisMapService gisMapService) {
        this.gisMapService = gisMapService;
    }

    /**
     * 측정소 좌표데이터를 가져오는 메소드
     * @return 반환 결과
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> stationMarkerData() {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> stationMarkerData = gisMapService.getStationMarkerData();
        response.put("data", stationMarkerData);
        response.put("message", "측정소 좌표 데이터를 성공적으로 가져왔습니다");
        return ResponseEntity.status(200).body(response);
    }

}
