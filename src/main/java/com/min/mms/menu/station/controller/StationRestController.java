package com.min.mms.menu.station.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.min.mms.menu.station.service.StationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/station/api")
public class StationRestController {

    private final StationService stationService;

    public StationRestController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/data")
    public ResponseEntity<Map<String, Object>> getStationData(@RequestParam Map<String, Object> params) {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            int page = params.get("page") == null ? 1 : Integer.parseInt(params.get("page").toString());
            int size = params.get("size") == null ? 10 : Integer.parseInt(params.get("size").toString());

            PageHelper.startPage(page, size);

            List<Map<String, Object>> stationDataList = stationService.getStationData(params);

            PageInfo<Map<String, Object>> pagination = new PageInfo<>(stationDataList);

            response.put("status", "success");
            response.put("data", stationDataList);
            response.put("pagination", pagination);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/category")
    public ResponseEntity<Map<String, Object>> getStationCategory() {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            List<String> stationCategoryList = stationService.getStationCategory();
            response.put("status", "success");
            response.put("data", stationCategoryList);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

}
