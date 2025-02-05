package com.min.mms.common;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
 * 오류 응답 생성, 페이징 파라미터 처리 등의 기능을 제공합니다.
 */
@Component
public class CommonComponent {

    /**
     * 오류 응답을 생성하는 메서드.
     *
     * <p>예외 발생 시 클라이언트에게 표준화된 오류 응답을 반환합니다.</p>
     *
     * @param errorMessage 오류 메시지
     * @return HTTP 500 상태 코드와 함께 오류 메시지를 포함한 응답 객체
     */
    public ResponseEntity<Map<String, Object>> createErrorResponse(String errorMessage) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", errorMessage);
        return ResponseEntity.status(500).body(response);
    }

    /**
     * 요청 파라미터에서 "page" 값을 가져옵니다.
     *
     * <p>클라이언트가 `page` 값을 제공하지 않으면 기본값 1을 반환합니다.</p>
     *
     * @param params 요청 파라미터 맵
     * @return 페이지 번호 (기본값: 1)
     */
    public static int getPage(Map<String, Object> params) {
        return params.get("page") == null ? 1 : Integer.parseInt(params.get("page").toString());
    }

    /**
     * 요청 파라미터에서 "size" 값을 가져옵니다.
     *
     * <p>클라이언트가 `size` 값을 제공하지 않으면 기본값 10을 반환합니다.</p>
     *
     * @param params 요청 파라미터 맵
     * @return 페이지 크기 (기본값: 10)
     */
    public static int getSize(Map<String, Object> params) {
        return params.get("size") == null ? 10 : Integer.parseInt(params.get("size").toString());
    }

    /**
     * 엑셀 파일을 생성하여 HTTP 응답으로 전송하는 메서드
     *
     * @param dataList 엑셀 다운로드할 데이터 리스트. 각 데이터는 Map 형식으로, 컬럼명이 key이고 값은 그에 해당하는 데이터입니다.
     * @param response HTTP 응답 객체. 엑셀 파일을 클라이언트로 전송하기 위해 사용됩니다.
     * @param fileName 엑셀 파일의 기본 파일명. 파일명 뒤에 오늘 날짜(yyyyMMdd)가 붙여져서 저장됩니다.
     */
    public void excelDownload(List<Map<String, Object>> dataList, HttpServletResponse response, String fileName) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            // 엑셀 시트 생성
            Sheet sheet = workbook.createSheet("Sheet");

            // 헤더 생성
            Row headerRow = sheet.createRow(0);
            int headerCellIndex = 0;
            // 첫 번째 데이터 항목에서 키 값(컬럼명)을 가져와 헤더 셀에 삽입
            for (String column : dataList.get(0).keySet()) {
                Cell cell = headerRow.createCell(headerCellIndex++);
                cell.setCellValue(column);
            }

            // 데이터 행 생성
            int rowIndex = 1;
            for (Map<String, Object> data : dataList) {
                Row row = sheet.createRow(rowIndex++);
                int cellIndex = 0;
                // 각 데이터 항목의 값을 해당 셀에 삽입
                for (Object value : data.values()) {
                    Cell cell = row.createCell(cellIndex++);
                    if (value != null) {
                        cell.setCellValue(value.toString());
                    }
                }
            }

            // 파일명에 오늘 날짜(연월일) 추가
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String finalFileName = fileName + "_" + today + ".xlsx";

            // 응답 헤더 설정
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + finalFileName);

            // 엑셀 파일을 HTTP 응답으로 작성
            try (ServletOutputStream outputStream = response.getOutputStream()) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

