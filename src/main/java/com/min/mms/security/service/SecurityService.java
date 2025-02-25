package com.min.mms.security.service;

import com.min.mms.security.model.UserSignUpDTO;

public interface SecurityService {

    int usernameExists(String username);

    int emailExists(String email);

    void memberJoin(UserSignUpDTO userSignUpDTO);

    String findUsernameByEmail(String email);

    String findEmailByUsername(String username);

    void generateFindCode(String code, String username);

    String verifyCode(String username);

    void updatePassword(String username, String password);

}
