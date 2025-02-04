package com.min.mms.menu.station.mapper;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class StationSqlProvider {
    public String getStationData(Map<String, Object> params) {
        return new SQL() {{
            SELECT("id, station_name, addr, dm_x, dm_y, item, mang_name, `year`, created_at, updated_at");
            FROM("Mms.observation_station");

            if (params.get("category") != null && params.get("category") != "") {
                WHERE("mang_name = #{category}");
            }
        }}.toString();
    }
}
