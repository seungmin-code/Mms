package com.min.mms.security.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDTO {

    int user_num;
    String username;
    String password;
    String name;
    String email;
    String phone;
    String role;;
    String create_date;
    String change_date;

}
