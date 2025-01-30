package com.min.mms.menu.admin.user.controller;

import com.min.mms.menu.admin.user.model.UserCreateDTO;
import com.min.mms.menu.admin.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<String, Object>> memberJoin(@RequestBody UserCreateDTO userCreateDTO) {
        Map<String, Object> response = new HashMap<String, Object>();

        // DB에 회원 등록
        userService.memberJoin(userCreateDTO);

        // 회원가입 성공 시 status, message, 201 코드 반환
        response.put("status", "success");
        response.put("message", "회원가입이 성공적으로 완료되었습니다");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/users/{username}")
    public ResponseEntity<Map<String, Object>> memberDelete(@PathVariable String username) {
        Map<String, Object> response = new HashMap<String, Object>();

        // DB의 회원 삭제
        userService.memberDelete(username);

        // 회원삭제 성공 시 status. message, 204 코드 반환
        response.put("status", "success");
        response.put("message", "회원 삭제가 성공적으로 완료되었습니다");;
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> memberSelect(@RequestParam Map<String, Object> params) {
        Map<String, Object> response = new HashMap<String, Object>();

        // DB의 회원 조회
        response.put("data", userService.memberSelect(params));

        // 회원조회 성공 시 status. message, 200 코드 반환
        response.put("status", "success");
        response.put("message", "회원 정보를 성공적으로 가져왔습니다");;
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<Map<String, Object>> memberDetailSelect(@PathVariable String username) {
        Map<String, Object> response = new HashMap<String, Object>();

        // DB의 회원 상세조회
        response.put("data", userService.memberDetailSelect(username));

        // 회원 상세조회 성공 시 status. message, 200 코드 반환
        response.put("status", "success");
        response.put("message", "회원 상세정보를 성공적으로 가져왔습니다");;
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/users/{username}")
    public ResponseEntity<Map<String, Object>> memberUpdate(@PathVariable String username, @RequestBody Map<String, Object> params) {
        Map<String, Object> response = new HashMap<String, Object>();

        // DB의 회원 수정
        userService.memberUpdate(username, params);
        
        // 회원수정 성공 시 status. message, 204 코드 반환
        response.put("status", "success");
        response.put("message", "회원 수정이 성공적으로 완료되었습니다");;
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
