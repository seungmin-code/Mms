package com.min.mms.admin.controller;

import com.min.mms.admin.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.servlet.function.ServerResponse.status;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    private final AdminService adminService;

    public AdminRestController(AdminService adminService) {
        this.adminService = adminService;
    }

    // 사용자 리스트 조회
    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getUsers(@RequestParam Map<String, Object> searchParams) {
        Map<String, Object> response = new HashMap<>();

        try {
            response.put("status", "success");
            response.put("message", "사용자 리스트 조회 성공");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "사용자 리스트 조회 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 사용자 등록
    @PostMapping("/users")
    public ResponseEntity<Map<String, Object>> addUser(@RequestBody Map<String, Object> addUserParams) {
        Map<String, Object> response = new HashMap<>();

        try {
            response.put("status", "success");
            response.put("message", "사용자 등록 성공");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "사용자 등록 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // 사용자 수정(권한설정)
    @PatchMapping("/users/{username}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable String username) {
        Map<String, Object> response = new HashMap<>();

        try {
            response.put("status", "success");
            response.put("message", "사용자 정보 수정 성공");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "사용자 정보 수정 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 사용자 삭제
    @DeleteMapping("/users/{username}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable String username) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            response.put("status", "success");
            response.put("message", "사용자 삭제 성공");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "사용자 삭제 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // -------------------------------------------------------------------------
    
    // 이메일 및 문자 알림 발송 (등록, 수정, 삭제)
    
    // -------------------------------------------------------------------------
    
    // 분류별 로그 조회

    // -------------------------------------------------------------------------

    //
    


    

}
