package com.min.mms.config.provider;

import org.springframework.stereotype.Component;

import java.util.Map;

public class SqlProvider {

    public String noticesSelectProvider(Map<String, String> params) {
        StringBuilder query = new StringBuilder(
                "SELECT id, title, content, file_path, create_by, update_by, " +
                        "DATE_FORMAT(created_at, '%Y-%m-%d') AS created_at, " +
                        "DATE_FORMAT(updated_at, '%Y-%m-%d') AS updated_at " +
                        "FROM Mms.board_notices WHERE 1=1");

        String searchType = params.get("searchType");
        String searchText = params.get("searchText");

        if (searchText != null && !searchText.isEmpty()) {
            if ("all".equals(searchType)) {
                query.append(" AND (title LIKE CONCAT('%', #{searchText}, '%')")
                        .append(" OR create_by LIKE CONCAT('%', #{searchText}, '%')")
                        .append(" OR content LIKE CONCAT('%', #{searchText}, '%'))");
            } else if (searchType != null) {
                query.append(" AND ")
                        .append(searchType)
                        .append(" LIKE CONCAT('%', #{searchText}, '%')");
            }
        }

        // 정렬 추가
        query.append(" ORDER BY id DESC");

        return query.toString();
    }


}
