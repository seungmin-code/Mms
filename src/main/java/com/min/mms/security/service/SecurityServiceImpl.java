package com.min.mms.security.service;

import com.min.mms.security.mapper.SecurityMapper;
import com.min.mms.security.model.UserSignUpDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final SecurityMapper securityMapper;
    private final PasswordEncoder passwordEncoder;

    public SecurityServiceImpl(SecurityMapper securityMapper, PasswordEncoder passwordEncoder) {
        this.securityMapper = securityMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public int usernameExists(String username) {
        return securityMapper.usernameExists(username);
    }

    @Override
    public int emailExists(String email) {
        return securityMapper.emailExists(email);
    }

    @Override
    @Transactional
    public void memberJoin(UserSignUpDTO userSignUpDTO) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userSignUpDTO.getPassword());  // 비밀번호 암호화
        // 암호화된 비밀번호로 DTO 수정
        userSignUpDTO.setPassword(encodedPassword);
        // 회원가입
        securityMapper.memberJoin(userSignUpDTO);
    }

    @Override
    public String findUsernameByEmail(String email) {
        return securityMapper.findUsernameByEmail(email);
    }
}
