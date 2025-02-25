package com.min.mms.security.mapper;

import com.min.mms.security.model.UserLoginDTO;
import com.min.mms.security.model.UserSignUpDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    @Select("SELECT email\n" +
            "FROM common_user\n" +
            "WHERE username = #{username}")
    String findEmailByUsername(String username);

    @Update("UPDATE Mms.common_user\n" +
            "SET find_code=#{code}\n" +
            "WHERE username=#{username};")
    void generateFindCode(String code, String username);

    @Select("SELECT find_code\n" +
            "FROM Mms.common_user\n" +
            "WHERE username = #{username}")
    String verifyCode(String username);

    @Update("UPDATE Mms.common_user\n" +
            "SET password=#{password}\n" +
            "WHERE username=#{username};")
    void updatePassword(String username, String password);
}
