package com.min.mms.menu.notices.mapper;

import com.min.mms.menu.station.mapper.StationSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface NoticesMapper {

    @SelectProvider(type = NoticesSQLProvider.class, method = "getNoticesData")
    List<Map<String, Object>> getNoticesData(Map<String, Object> request);

    @Select("SELECT id, title, content, file_path, create_by, update_by, created_at, updated_at\n" +
            "FROM Mms.board_notices\n" +
            "WHERE id=#{id}")
    Map<String, Object> getNoticesDetailData(String id);

    @Insert("INSERT INTO Mms.board_notices\n" +
            "(title, content, file_path, create_by, update_by, created_at, updated_at)\n" +
            "VALUES('', '', NULL, '', NULL, current_timestamp(), current_timestamp())")
    void createNotices(Map<String, Object> request);

    @Update("UPDATE Mms.board_notices\n" +
            "SET title='', content='', file_path=NULL, create_by='', update_by=NULL, created_at=current_timestamp(), updated_at=current_timestamp()\n" +
            "WHERE id=#{id}")
    void patchNotices(String id, Map<String, Object> request);

    @Delete("DELETE FROM Mms.board_notices\n" +
            "WHERE id=#{id}")
    void deleteNotices(String id);

}
