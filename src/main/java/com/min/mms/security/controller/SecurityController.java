package com.min.mms.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/security")
public class SecurityController {

    @GetMapping("/loginForm")
    public String loginForm(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            // 이미 로그인되어 있는 경우
            model.addAttribute("loginResult", true);
        }
        return "security/login_form";
    }

    @GetMapping("/signUpForm")
    public String signUpForm() {
        return "security/sign_up_form";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "security/access_denied";
    }

    @GetMapping("/findUsername")
    public String findUsername() {
        return "security/find_username";
    }

    @GetMapping("/findPassword")
    public String findPassword() {
        return "security/find_password";
    }

    @GetMapping("/resetPassword/{username}")
    public String resetPassword(@PathVariable("username") String username, Model model) {
        model.addAttribute("username", username);
        return "security/reset_password";
    }
}
