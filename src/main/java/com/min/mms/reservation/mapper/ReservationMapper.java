package com.min.mms.reservation.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface ReservationMapper {

    @Select("select res_code from reservation")
    public Map<String, Object> checkDbConnect();

}
