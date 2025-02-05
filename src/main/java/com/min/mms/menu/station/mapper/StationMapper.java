package com.min.mms.menu.station.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface StationMapper {

    @SelectProvider(type = StationSqlProvider.class, method = "getStationData")
    List<Map<String, Object>> getStationData(Map<String, Object> params);

    @Select("SELECT DISTINCT(mang_name) " +
            "FROM Mms.observation_station")
    List<String> getStationCategory();

    @Select("SELECT DISTINCT LEFT(addr, 2) AS addr " +
            "FROM Mms.observation_station;")
    List<String> getSidoCategory();

}
