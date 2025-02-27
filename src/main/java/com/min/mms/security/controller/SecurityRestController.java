package com.min.mms.security.controller;

import com.min.mms.security.model.UserSignUpDTO;
import com.min.mms.security.service.SecurityService;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/security")
public class SecurityRestController {

    @Autowired
    private JavaMailSender mailSender;

    private final SecurityService securityService;

    public SecurityRestController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/roles")
    public Collection<? extends GrantedAuthority> getUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities();
    }

    @PostMapping("/memberJoin")
    public ResponseEntity<Map<String, Object>> memberJoin(@RequestBody UserSignUpDTO userSignUpDTO) {
        Map<String, Object> response = new HashMap<>();

        String username = userSignUpDTO.getUsername();
        int usernameCnt = securityService.usernameExists(username);

        if (usernameCnt > 0) {
            response.put("status", "error");
            response.put("message", "이미 존재하는 사용자명입니다.");
            return ResponseEntity.badRequest().body(response);  // 400 Bad Request
        }

        String email = userSignUpDTO.getEmail();
        int emailCnt = securityService.emailExists(email);

        if (emailCnt > 0) {
            response.put("status", "error");
            response.put("message", "이미 존재하는 이메일입니다.");
            return ResponseEntity.badRequest().body(response);  // 400 Bad Request
        }

        // 비밀번호 확인 일치 여부 체크
        if (!userSignUpDTO.getPassword().equals(userSignUpDTO.getConfirmPassword())) {
            response.put("status", "error");
            response.put("message", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            return ResponseEntity.badRequest().body(response);  // 400 Bad Request
        }

        // 회원가입 처리
        securityService.memberJoin(userSignUpDTO);
        response.put("status", "success");
        response.put("message", "회원등록이 성공적으로 완료되었습니다.");
        return ResponseEntity.ok(response);  // 200 OK
    }

    @PostMapping("/findUsername")
    public ResponseEntity<Map<String, Object>> findUsername(@RequestBody Map<String, String> requestBody) {
        Map<String, Object> response = new HashMap<>();
        String email = requestBody.get("email");
        String username = securityService.findUsernameByEmail(email);

        if (username == null) {
            response.put("status", "error");
            response.put("message", "존재하지 않는 사용자입니다");
            return ResponseEntity.status(400).body(response);
        } else {
            // 이메일 발송
            try {
                sendFindUsernameMail(email, username);
                response.put("status", "success");
                response.put("message", "입력하신 이메일로 사용자 아이디가 전송되었습니다");
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                response.put("status", "error");
                response.put("message", "이메일 발송에 실패하였습니다");
                System.out.println(e.getMessage());
                return ResponseEntity.status(500).body(response);
            }
        }
    }

    @PostMapping("/usernameExist")
    public ResponseEntity<Map<String, Object>> usernameExist(@RequestBody Map<String, String> requestBody) throws MessagingException {
        Map<String, Object> response = new HashMap<>();
        int usernameCnt = securityService.usernameExists(requestBody.get("username"));

        String code = generateRandomNumber(6);
        System.out.println("임의로 생성된 6자리 숫자: " + code);

        securityService.generateFindCode(code, requestBody.get("username"));

        if (usernameCnt > 0) { // 사용자명이 존재하면
            sendCodeMail(requestBody.get("username"), code);
            response.put("status", true);
            return ResponseEntity.ok(response);
        } else { // 사용자명이 존재하지않으면
            response.put("status", false);
            return ResponseEntity.ok(response);
        }
    }

    public static String generateRandomNumber(int length) {
        Random random = new Random();
        StringBuilder number = new StringBuilder();

        // 6자리 숫자 생성
        for (int i = 0; i < length; i++) {
            number.append(random.nextInt(10)); // 0부터 9까지의 랜덤 숫자
        }

        return number.toString();
    }

    private void sendCodeMail(String username, String code) throws MessagingException {
        String email = securityService.findEmailByUsername(username);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // HTML 이메일 본문 설정
        String htmlContent = "<html>"
                + "<head><style>"
                + "body {font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; color: #333; background-color: #f4f4f9; padding: 40px; margin: 0;}"
                + ".email-container {background-color: #ffffff; border-radius: 8px; padding: 30px; box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); max-width: 600px; margin: 0 auto;}"
                + ".email-header {text-align: center; font-size: 28px; color: #2d3e50; font-weight: bold; margin-bottom: 20px;}"
                + ".email-content {font-size: 16px; color: #555; line-height: 1.5; margin-top: 20px;}"
                + ".username {font-weight: bold; color: #4CAF50; font-size: 20px; margin: 15px 0;}"
                + ".footer {text-align: center; margin-top: 40px; font-size: 12px; color: #aaa;}"
                + ".button {display: inline-block; padding: 12px 24px; background-color: #4CAF50; color: #ffffff; text-decoration: none; font-weight: bold; border-radius: 4px; margin-top: 20px;}"
                + ".button:hover {background-color: #45a049;}"
                + "</style></head>"
                + "<body>"
                + "<div class='email-container'>"
                + "<div class='email-header'>MMS 시스템 인증번호 발송</div>"
                + "<div class='email-content'>"
                + "<p>안녕하세요,</p>"
                + "<p>요청하신 인증번호는 다음과 같습니다</p>"
                + "<p class='username'>" + code + "</p>"
                + "</div>"
                + "<div class='footer'>"
                + "<p>본 이메일은 시스템에서 자동으로 발송된 것입니다. 문의 사항은 시스템 담당자에게 연락해 주세요.</p>"
                + "<p>Thank you for using our service!</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        helper.setTo(email);
        helper.setSubject("MMS 시스템 인증번호 발송");
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    private void sendFindUsernameMail(String toEmail, String username) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // HTML 이메일 본문 설정
        String htmlContent = "<html>"
                + "<head><style>"
                + "body {font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; color: #333; background-color: #f4f4f9; padding: 40px; margin: 0;}"
                + ".email-container {background-color: #ffffff; border-radius: 8px; padding: 30px; box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); max-width: 600px; margin: 0 auto;}"
                + ".email-header {text-align: center; font-size: 28px; color: #2d3e50; font-weight: bold; margin-bottom: 20px;}"
                + ".email-content {font-size: 16px; color: #555; line-height: 1.5; margin-top: 20px;}"
                + ".username {font-weight: bold; color: #4CAF50; font-size: 20px; margin: 15px 0;}"
                + ".footer {text-align: center; margin-top: 40px; font-size: 12px; color: #aaa;}"
                + ".button {display: inline-block; padding: 12px 24px; background-color: #4CAF50; color: #ffffff; text-decoration: none; font-weight: bold; border-radius: 4px; margin-top: 20px;}"
                + ".button:hover {background-color: #45a049;}"
                + "</style></head>"
                + "<body>"
                + "<div class='email-container'>"
                + "<div class='email-header'>MMS 시스템 아이디 찾기</div>"
                + "<div class='email-content'>"
                + "<p>안녕하세요,</p>"
                + "<p>요청하신 아이디는 다음과 같습니다</p>"
                + "<p class='username'>" + username + "</p>"
                + "</div>"
                + "<div class='footer'>"
                + "<p>본 이메일은 시스템에서 자동으로 발송된 것입니다. 문의 사항은 시스템 담당자에게 연락해 주세요.</p>"
                + "<p>Thank you for using our service!</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        helper.setTo(toEmail);
        helper.setSubject("MMS 시스템 아이디 찾기");
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    @PostMapping("/verifyCode")
    public ResponseEntity<Map<String, Object>> verifyCode(@RequestBody Map<String, Object> requestBody) {
        Map<String, Object> response = new HashMap<>();
        String code = securityService.verifyCode(requestBody.get("username").toString());

        if (code.equals(requestBody.get("code"))) {
            response.put("status", true);
            response.put("message", "인증번호 일치");
        } else {
            response.put("status", false);
            response.put("message", "인증번호 불일치");
        }
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<Map<String, Object>> updatePassword(@RequestBody Map<String, Object> requestBody) {
        Map<String, Object> response = new HashMap<>();

        // 입력된 데이터 추출
        String username = (String) requestBody.get("username");
        String password = (String) requestBody.get("password");
        String confirmPassword = (String) requestBody.get("confirmPassword");

        // 비밀번호와 확인된 비밀번호가 일치하는지 검사
        if (!password.equals(confirmPassword)) {
            response.put("status", false);
            response.put("message", "비밀번호가 일치하지 않습니다.");
            return ResponseEntity.badRequest().body(response); // 비밀번호 불일치 시 400 오류
        }

        // 사용자가 존재하는지 확인 (서비스나 DB에서 사용자 검색)
        int usernameCnt = securityService.usernameExists(username);
        if (usernameCnt > 0) {
            securityService.updatePassword(username, password);
            response.put("status", true);
            response.put("message", "비밀번호가 성공적으로 변경되었습니다.");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", false);
            response.put("message", "사용자를 찾을 수 없습니다.");
            return ResponseEntity.badRequest().body(response); // 사용자 미존재 시 400 오류
        }
    }

}
