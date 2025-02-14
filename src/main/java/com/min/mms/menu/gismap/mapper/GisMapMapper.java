package com.min.mms.menu.gismap.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface GisMapMapper {
    @Select("SELECT id, station_name, addr, dm_x, dm_y, item, mang_name, `year`, created_at, updated_at\n" +
            "FROM Mms.observation_station;")
    List<Map<String, Object>> getStationMarkerData();
}
