package com.min.mms.user.service;

import com.min.mms.config.UserAlreadyExistsException;
import com.min.mms.config.UserNotFoundException;
import com.min.mms.user.mapper.UserMapper;
import com.min.mms.user.model.UserCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public void memberJoin(UserCreateDTO userCreateDTO) {
        // 아이디 중복검사
        if (userMapper.existByUsername(userCreateDTO.getUsername())) {
            throw new UserAlreadyExistsException("이미 존재하는 아이디입니다");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userCreateDTO.getPassword());
        userCreateDTO.setPassword(encodedPassword);

        // 회원가입 정보 저장
        userMapper.memberJoin(userCreateDTO);
    }

    @Override
    @Transactional
    public void memberDelete(String username) {
        // 아이디 존재 여부 체크
        if (!userMapper.existByUsername(username)) {
            throw new UserNotFoundException("존재하지 않는 회원입니다");
        }
        
        // 회원 정보 삭제
        userMapper.memberDelete(username);
    }

    @Override
    @Transactional
    public List<Map<String, Object>> memberSelect(Map<String, Object> params) {
        return userMapper.memberSelect(params);
    }

    @Override
    @Transactional
    public Map<String, Object> memberDetailSelect(String username) {
        // 아이디 존재 여부 체크
        if (!userMapper.existByUsername(username)) {
            throw new UserNotFoundException("존재하지 않는 회원입니다");
        }
        
        // 회원 상세정보 조회
        return userMapper.memberDetailSelect(username);
    }

    @Override
    @Transactional
    public void memberUpdate(String username, Map<String, Object> params) {
        // 아이디 존재 여부 체크
        if (!userMapper.existByUsername(username)) {
            throw new UserNotFoundException("존재하지 않는 회원입니다");
        }

        userMapper.memberUpdate(username, params);
    }
}
