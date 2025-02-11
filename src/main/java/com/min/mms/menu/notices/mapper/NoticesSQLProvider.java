package com.min.mms.menu.notices.mapper;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class NoticesSQLProvider {

    public String getNoticesData(Map<String, Object> request) {
        String category = (String) request.get("category");
        String keyword = (String) request.get("keyword");

        return new SQL() {{
            SELECT("id, title, content, file_path, create_by, update_by, " +
                    "DATE_FORMAT(created_at, '%Y-%m-%d %H:%i') AS created_at, updated_at");
            FROM("Mms.board_notices");

            if (keyword != null && !keyword.trim().isEmpty()) {
                if (category != null && !category.trim().isEmpty()) {
                    WHERE("${category} LIKE CONCAT('%', #{keyword}, '%')");
                } else {
                    WHERE("title LIKE CONCAT('%', #{keyword}, '%') OR content LIKE CONCAT('%', #{keyword}, '%') OR create_by LIKE CONCAT('%', #{keyword}, '%')");
                }
            }

            ORDER_BY("id DESC");
        }}.toString();
    }

}
