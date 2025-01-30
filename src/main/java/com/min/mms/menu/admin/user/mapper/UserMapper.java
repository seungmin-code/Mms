package com.min.mms.menu.admin.user.mapper;

import com.min.mms.menu.admin.user.model.UserCreateDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    @Select("SELECT COUNT(*)" +
            "FROM common_user " +
            "WHERE username = #{username}")
    boolean existByUsername(String username);

    @Insert("INSERT INTO common_user " +
            "(username, password, name, email, phone, `role`, create_date, change_date) " +
            "VALUES(#{username}, #{password}, #{name}, #{email}, #{phone}, #{role}, NOW(), NULL);")
    void memberJoin(UserCreateDTO userCreateDTO);

    @Delete("DELETE FROM common_user " +
            "WHERE username = #{username}")
    void memberDelete(String username);

    //@SelectProvider(type = SqlProvider.class, method = "memberSelectProvider")
    List<Map<String, Object>> memberSelect(Map<String, Object> params);

    @Select("SELECT user_num, username, password, name, email, phone, `role`, create_date, change_date " +
            "FROM common_user " +
            "WHERE username = #{username}")
    Map<String, Object> memberDetailSelect(String username);


    @Update("UPDATE common_user " +
            "SET name=#{params.name}, email=#{params.email}, phone=#{params.phone}, `role`=#{params.role}, create_date=NOW(), change_date=NULL " +
            "WHERE username=#{username};")
    void memberUpdate(String username, Map<String, Object> params);

}
