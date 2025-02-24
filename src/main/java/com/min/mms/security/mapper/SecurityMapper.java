package com.min.mms.security.mapper;

import com.min.mms.security.model.UserLoginDTO;
import com.min.mms.security.model.UserSignUpDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SecurityMapper {

    @Select("SELECT user_num, username, password, name, email, phone, `role`, create_date, change_date\n" +
            "FROM Mms.common_user WHERE username = #{username}")
    UserLoginDTO selectByUsername(String username);

    @Select("SELECT COUNT(*) FROM common_user WHERE username = #{username}")
    int usernameExists(String username);

    @Insert("INSERT INTO Mms.common_user\n" +
            "(username, password, email, `role`, create_date, change_date)\n" +
            "VALUES(#{username}, #{password}, #{email}, 'USER', current_timestamp(), NULL);")
    void memberJoin(UserSignUpDTO userSignUpDTO);

    @Select("SELECT COUNT(*) FROM common_user WHERE email = #{email}")
    int emailExists(String email);

    @Select("SELECT username\n" +
            "FROM Mms.common_user\n" +
            "WHERE email = #{email}")
    String findUsernameByEmail(String email);
}
