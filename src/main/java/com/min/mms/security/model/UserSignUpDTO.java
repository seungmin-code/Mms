package com.min.mms.security.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpDTO {

    private String username;

    private String password;

    private String confirmPassword;

    private String email;
}
