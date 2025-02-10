package com.min.mms.common.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommonMapper {

    @Select("SELECT DISTINCT LEFT(addr, 2) AS addr " +
            "FROM Mms.observation_station;")
    List<String> fetchSidoData();

    @Select("SELECT DISTINCT station_name " +
            "FROM Mms.observation_station WHERE LEFT(addr, 2) = #{sido}")
    List<String> fetchStationData(String sido);

}
