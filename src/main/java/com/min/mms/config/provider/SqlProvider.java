package com.min.mms.config.provider;

import java.util.Map;

public class SqlProvider {

    public String memberSelectProvider(Map<String, Object> params) {
        StringBuilder query = new StringBuilder(
                "SELECT user_num, username, password, name, email, phone, `role`, create_date, change_date " +
                        "FROM common_user WHERE 1=1");

        if (params.get("name") != null && !params.get("name").toString().isEmpty()) {
            query.append(" AND name = #{name}");
        }

        if (params.get("role") != null && !params.get("role").toString().isEmpty()) {
            String role = params.get("role").toString();
            if ("ADMIN".equals(role) || "USER".equals(role)) {
                query.append(" AND role = #{role}");
            }
        }

        return query.toString();
    }

}
