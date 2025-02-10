package com.min.mms.menu.station.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.min.mms.common.component.CommonComponent;
import com.min.mms.menu.station.service.StationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 측정소 관련 API를 제공하는 REST 컨트롤러.
 * <p>
 * 이 컨트롤러는 측정소 데이터를 조회하고, 카테고리 목록을 반환하는 API 엔드포인트를 제공합니다.
 * 페이징 처리를 지원하며, 공통 오류 응답을 활용하여 예외를 일관되게 처리합니다.
 * </p>
 */
@RestController
@RequestMapping("/station/api")
public class StationRestController {

    private static final Logger logger = LoggerFactory.getLogger(StationRestController.class);

    private final StationService stationService;
    private final CommonComponent commonComponent;

    public StationRestController(StationService stationService, CommonComponent commonComponent) {
        this.stationService = stationService;
        this.commonComponent = commonComponent;
    }

    /**
     * 측정소 데이터를 페이징 처리하여 반환하는 API.
     * <p>
     * 클라이언트는 `page`와 `size` 파라미터를 사용하여 원하는 페이지의 데이터를 요청할 수 있습니다.
     * 기본값은 `page=1`, `size=10`입니다.
     * </p>
     * @param params 요청 파라미터 (페이징에 필요한 page, size 포함)
     * @return 페이징 처리된 측정소 데이터 및 페이징 정보가 포함된 응답
     */
    @GetMapping("/data")
    public ResponseEntity<Map<String, Object>> getStationData(@RequestParam Map<String, Object> params) {
        try {
            Map<String, Object> response = new HashMap<>();

            int page = CommonComponent.getPage(params);
            int size = CommonComponent.getSize(params);

            logger.info("Fetching station data for page {} with size {}", page, size);

            PageHelper.startPage(page, size);

            // 측정소 데이터 가져오기
            List<Map<String, Object>> stationDataList = stationService.getStationData(params);
            // 페이징 처리
            PageInfo<Map<String, Object>> pagination = new PageInfo<>(stationDataList);

            // 결과 반환
            response.put("status", "success");
            response.put("data", stationDataList);
            response.put("pagination", pagination);

            logger.info("Successfully fetched {} station data", stationDataList.size());
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            logger.error("Error occurred while fetching station data: {}", e.getMessage(), e);
            // 공통 오류 응답 반환
            return commonComponent.createErrorResponse(e.getMessage());
        }
    }

    /**
     * 측정소 카테고리 목록을 반환하는 API.
     * <p>
     * 측정소 데이터를 분류하는 카테고리 목록을 제공합니다.
     * </p>
     * @return 측정소 카테고리 목록이 포함된 응답
     */
    @GetMapping("/category")
    public ResponseEntity<Map<String, Object>> getStationCategory() {
        try {
            Map<String, Object> response = new HashMap<>();

            // 카테고리 데이터 가져오기
            List<String> stationCategoryList = stationService.getStationCategory();
            response.put("category", stationCategoryList);

            // 결과 반환
            response.put("status", "success");
            response.put("data", stationCategoryList);

            logger.info("Successfully fetched station categories");
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            logger.error("Error occurred while fetching station categories: {}", e.getMessage(), e);
            // 공통 오류 응답 반환
            return commonComponent.createErrorResponse(e.getMessage());
        }
    }

    /**
     * 시도 목록을 반환하는 API.
     * <p>
     * 시도 데이터를 반환합니다.
     * </p>
     * @return 시도 목록이 포함된 응답
     */
    @GetMapping("/sidoCategory")
    public ResponseEntity<Map<String, Object>> getSidoCategory() {
        try {
            Map<String, Object> response = new HashMap<>();
            // 시도 데이터 가져오기
            List<String> sidoCategoryList = stationService.getSidoCategory();

            // 결과 반환
            response.put("status", "success");
            response.put("data", sidoCategoryList);

            logger.info("Successfully fetched sido categories");
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            logger.error("Error occurred while fetching sido categories: {}", e.getMessage(), e);
            // 공통 오류 응답 반환
            return commonComponent.createErrorResponse(e.getMessage());
        }
    }

    /**
     * 엑셀 다운로드 API.
     * <p>
     * 측정소 데이터를 엑셀 파일로 다운로드할 수 있습니다.
     * </p>
     * @param params 요청 파라미터
     * @param response HTTP 응답 객체
     */
    @GetMapping("/excelDownload")
    public void excelDownload(@RequestParam Map<String, Object> params, HttpServletResponse response) {
        try {
            logger.info("Starting excel download with params: {}", params);

            List<Map<String, Object>> excelDownloadData = stationService.getStationData(params);
            commonComponent.excelDownload(excelDownloadData, response, "station");

            logger.info("Excel download completed successfully");
        } catch (Exception e) {
            logger.error("Error occurred during excel download: {}", e.getMessage(), e);
        }
    }
}
