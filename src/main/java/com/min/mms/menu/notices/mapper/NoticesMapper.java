package com.min.mms.menu.notices.mapper;

import com.min.mms.menu.notices.model.NoticesUpdateDTO;
import com.min.mms.menu.station.mapper.StationSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface NoticesMapper {

    @SelectProvider(type = NoticesSQLProvider.class, method = "getNoticesData")
    List<Map<String, Object>> getNoticesData(Map<String, Object> request);

    @Select("SELECT id, title, content, file_path, file_name, create_by, update_by, DATE_FORMAT(created_at, '%Y-%m-%d %H:%i') AS created_at, DATE_FORMAT(updated_at, '%Y-%m-%d %H:%i') AS updated_at\n" +
            "FROM Mms.board_notices\n" +
            "WHERE id=#{id}")
    Map<String, Object> getNoticesDetailData(String id);

    @Insert("INSERT INTO Mms.board_notices\n" +
            "(title, content, file_path, file_name, create_by, update_by, created_at, updated_at)\n" +
            "VALUES(#{title}, #{content}, #{file_path}, #{file_name}, '사용자(수정필요)', NULL, current_timestamp(), current_timestamp())")
    void createNotices(Map<String, Object> request);

    @Update("UPDATE Mms.board_notices\n" +
            "SET title=#{request.title}, content=#{request.content}, file_path=#{request.file_path}, file_name=#{request.file_name}, update_by='임시', updated_at=current_timestamp()\n" +
            "WHERE id=#{id}")
    void patchNotices(String id, Map<String, Object> request);

    @Delete("DELETE FROM Mms.board_notices\n" +
            "WHERE id=#{id}")
    void deleteNotices(String id);

}
