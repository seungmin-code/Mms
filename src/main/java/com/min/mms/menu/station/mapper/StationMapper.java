package com.min.mms.menu.station.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface StationMapper {

    @Select("SELECT id, station_name, addr, dm_x, dm_y, item, mang_name, `year`, created_at, updated_at " +
            "FROM Mms.observation_station")
    List<Map<String, Object>> getStationData(Map<String, Object> params);

    @Select("SELECT DISTINCT(mang_name) " +
            "FROM Mms.observation_station")
    List<String> getStationCategory();

}
