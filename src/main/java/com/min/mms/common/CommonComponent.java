package com.min.mms.common;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommonComponent {

    // 기본값 설정 함수
    public int getParameter(Map<String, Object> params, String key, int defaultValue) {
        return params.containsKey(key) ? Integer.parseInt(params.get(key).toString()) : defaultValue;
    }

}
