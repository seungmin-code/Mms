package com.min.mms.common;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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


}

