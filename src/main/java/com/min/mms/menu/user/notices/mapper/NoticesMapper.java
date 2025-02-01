package com.min.mms.menu.user.notices.mapper;

import com.min.mms.config.provider.SqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface NoticesMapper {

    @SelectProvider(type = SqlProvider.class, method = "noticesSelectProvider")
    List<Map<String, Object>> getNoticesDataList(Map<String, Object> params);

    @Select("SELECT id, title, content, file_path, create_by, update_by, created_at, updated_at\n" +
            "FROM Mms.board_notices WHERE id = #{id}")
    Map<String, Object> getNoticesDetail(Long id);

    @Insert("INSERT INTO Mms.board_notices " +
            "(title, content, file_path, create_by, created_at, updated_at) " +
            "VALUES(#{title}, #{content}, #{file_path}, '관리자', current_timestamp(), current_timestamp());")
    void insertNotice(Map<String, Object> params);

    @Delete("DELETE FROM board_notices WHERE id = #{id}")
    void deleteNotice(Long id);

}
