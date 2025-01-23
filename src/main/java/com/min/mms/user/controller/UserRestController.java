package com.min.mms.user.controller;

import com.min.mms.config.UserAlreadyExistsException;
import com.min.mms.user.model.UserDTO;
import com.min.mms.user.service.UserService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<Map<String, Object>> memberJoin(@RequestBody UserDTO userDTO) {
        Map<String, Object> response = new HashMap<String, Object>();

        // DB에 회원 등록
        userService.memberJoin(userDTO);

        // 회원가입 성공 시 status, message, 201 코드 반환
        response.put("status", "success");
        response.put("message", "회원가입이 성공적으로 완료되었습니다");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/users")
    public ResponseEntity<Map<String, Object>> memberDelete(@RequestBody String username) {
        Map<String, Object> response = new HashMap<String, Object>();
        
        // DB의 회원 삭제
        userService.memberDelete(username);
        
        // 회원삭제 성공 시 status. message, 204 코드 반환
        response.put("status", "error");
        response.put("message", "회원 삭제가 성공적으로 완료되었습니다");;
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);

    }

}
