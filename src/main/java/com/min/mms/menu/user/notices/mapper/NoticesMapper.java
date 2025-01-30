package com.min.mms.menu.user.notices.mapper;

import com.min.mms.config.provider.SqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface NoticesMapper {

    @SelectProvider(type = SqlProvider.class, method = "noticesSelectProvider")
    List<Map<String, Object>> getNoticesDataList(Map<String, Object> params);

}
