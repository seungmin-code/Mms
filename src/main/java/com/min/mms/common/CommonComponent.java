package com.min.mms.common;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 공통 기능을 제공하는 컴포넌트 클래스.
 * 애플리케이션에서 자주 사용하는 기능을 모아놓은 유틸리티 클래스입니다.
 */
@Component
public class CommonComponent {

    private static final Logger logger = LoggerFactory.getLogger(CommonComponent.class);

    /**
     * 오류 응답을 생성하는 메서드.
     *
     * @param errorMessage 오류 메시지
     * @return HTTP 500 상태 코드와 함께 오류 메시지를 포함한 응답 객체
     */
    public ResponseEntity<Map<String, Object>> createErrorResponse(String errorMessage) {
        logger.error("Error Response: {}", errorMessage);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", errorMessage);
        return ResponseEntity.status(500).body(response);
    }

    /**
     * 요청 파라미터에서 "page" 값을 가져옵니다.
     * 기본값: 1
     */
    public static int getPage(Map<String, Object> params) {
        try {
            return params.get("page") == null ? 1 : Integer.parseInt(params.get("page").toString());
        } catch (NumberFormatException e) {
            logger.warn("Invalid page parameter: {}", params.get("page"));
            return 1;  // 기본값 반환
        }
    }

    /**
     * 요청 파라미터에서 "size" 값을 가져옵니다.
     * 기본값: 10
     */
    public static int getSize(Map<String, Object> params) {
        try {
            return params.get("size") == null ? 10 : Integer.parseInt(params.get("size").toString());
        } catch (NumberFormatException e) {
            logger.warn("Invalid size parameter: {}", params.get("size"));
            return 10; // 기본값 반환
        }
    }

    /**
     * 엑셀 파일을 생성하여 HTTP 응답으로 전송하는 메서드
     *
     * @param dataList 엑셀 다운로드할 데이터 리스트
     * @param response HTTP 응답 객체
     * @param fileName 엑셀 파일의 기본 파일명
     */
    public void excelDownload(List<Map<String, Object>> dataList, HttpServletResponse response, String fileName) {
        if (dataList == null || dataList.isEmpty()) {
            logger.error("Excel download failed: No data available.");
            throw new IllegalArgumentException("다운로드할 데이터가 없습니다.");
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            // 엑셀 시트 생성
            Sheet sheet = workbook.createSheet("Sheet");

            // 헤더 생성
            Row headerRow = sheet.createRow(0);
            int headerCellIndex = 0;
            for (String column : dataList.get(0).keySet()) {
                Cell cell = headerRow.createCell(headerCellIndex++);
                cell.setCellValue(column);
            }

            // 데이터 행 생성
            int rowIndex = 1;
            for (Map<String, Object> data : dataList) {
                Row row = sheet.createRow(rowIndex++);
                int cellIndex = 0;
                for (Object value : data.values()) {
                    Cell cell = row.createCell(cellIndex++);
                    if (value != null) {
                        cell.setCellValue(value.toString());
                    }
                }
            }

            // 파일명에 오늘 날짜 추가
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String finalFileName = fileName + "_" + today + ".xlsx";

            // 응답 헤더 설정
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + finalFileName);

            // 엑셀 파일을 HTTP 응답으로 전송
            try (ServletOutputStream outputStream = response.getOutputStream()) {
                workbook.write(outputStream);
                logger.info("Excel file '{}' successfully generated and sent.", finalFileName);
            }
        } catch (IOException e) {
            logger.error("Excel download failed due to IO error.", e);
            throw new RuntimeException("엑셀 파일 생성 중 오류가 발생했습니다.");
        } catch (Exception e) {
            logger.error("Unexpected error in excelDownload method.", e);
            throw new RuntimeException("알 수 없는 오류가 발생했습니다.");
        }
    }
}
