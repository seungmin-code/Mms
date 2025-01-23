package com.min.mms.user.mapper;

import com.min.mms.user.model.UserDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT COUNT(*)" +
            "FROM common_user " +
            "WHERE user_num = #{username}")
    boolean existByUsername(String username);

    @Insert("INSERT INTO common_user " +
            "(username, password, name, email, phone, `role`, create_date, change_date) " +
            "VALUES(#{username}, #{password}, #{name}, #{email}, #{phone}, #{role}, NOW(), NULL);")
    void memberJoin(UserDTO userDTO);

    @Delete("DELETE FROM common_user " +
            "WHERE username = #{user_name}")
    void memberDelete(String user_name);

}
