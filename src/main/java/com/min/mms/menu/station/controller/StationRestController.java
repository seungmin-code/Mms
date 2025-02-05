package com.min.mms.menu.station.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.min.mms.common.CommonComponent;
import com.min.mms.menu.station.service.StationService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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

            PageHelper.startPage(page, size);

            // 측정소 데이터 가져오기
            List<Map<String, Object>> stationDataList = stationService.getStationData(params);
            // 페이징 처리
            PageInfo<Map<String, Object>> pagination = new PageInfo<>(stationDataList);

            // 결과 반환
            response.put("status", "success");
            response.put("data", stationDataList);
            response.put("pagination", pagination);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
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
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
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
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            // 공통 오류 응답 반환
            return commonComponent.createErrorResponse(e.getMessage());
        }
    }

    @GetMapping("/excelDownload")
    public void excelDownload(@RequestParam Map<String, Object> params, HttpServletResponse response) {
        // 엑셀 다운로드 데이터 가져오기
        List<Map<String, Object>> excelDownloadData = stationService.getStationData(params);

        // XSSFWorkbook을 사용하여 .xlsx 형식의 워크북 생성
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Station Data");

            // 헤더 생성
            Row headerRow = sheet.createRow(0);
            int headerCellIndex = 0;
            for (String column : excelDownloadData.get(0).keySet()) {
                Cell cell = headerRow.createCell(headerCellIndex++);
                cell.setCellValue(column);
            }

            // 데이터 행 생성
            int rowIndex = 1;
            for (Map<String, Object> data : excelDownloadData) {
                Row row = sheet.createRow(rowIndex++);
                int cellIndex = 0;
                for (Object value : data.values()) {
                    Cell cell = row.createCell(cellIndex++);
                    if (value != null) {
                        cell.setCellValue(value.toString());
                    }
                }
            }

            // 엑셀 파일 응답으로 보내기
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=station_data.xlsx");

            try (ServletOutputStream outputStream = response.getOutputStream()) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
