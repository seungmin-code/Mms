package com.min.mms.security.mapper;

import com.min.mms.security.model.UserLoginDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SecurityMapper {

    @Select("SELECT user_num, username, password, name, email, phone, `role`, create_date, change_date\n" +
            "FROM Mms.common_user WHERE username = #{username}")
    UserLoginDTO selectByUsername(String username);

}
