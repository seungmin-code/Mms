package com.min.mms.security.custom;

import com.min.mms.security.mapper.SecurityMapper;
import com.min.mms.security.model.UserLoginDTO;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final SecurityMapper securityMapper;

    public CustomUserDetailsService(SecurityMapper securityMapper) {
        this.securityMapper = securityMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLoginDTO userLoginDTO = securityMapper.selectByUsername(username);
        return User.builder()
                .username(userLoginDTO.getUsername())
                .password(userLoginDTO.getPassword())
                .roles(userLoginDTO.getRole())
                .build();
    }
}
