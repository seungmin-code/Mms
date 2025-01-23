package com.min.mms.user.service;

import com.min.mms.config.UserAlreadyExistsException;
import com.min.mms.config.UserNotFoundException;
import com.min.mms.user.mapper.UserMapper;
import com.min.mms.user.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void memberJoin(UserDTO userDTO) {
        // 아이디 중복검사
        if (userMapper.existByUsername(userDTO.getUsername())) {
            throw new UserAlreadyExistsException("이미 존재하는 아이디입니다");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);

        userMapper.memberJoin(userDTO);
    }

    @Override
    public void memberDelete(String user_name) {
        System.out.println("왜" + userMapper.existByUsername(user_name));
        // 아이디 존재 여부 체크
/*        if (!userMapper.existByUsername(user_name)) {
            throw new UserNotFoundException("존재하지 않는 회원입니다");
        }*/

        userMapper.memberDelete(user_name);
    }
}
