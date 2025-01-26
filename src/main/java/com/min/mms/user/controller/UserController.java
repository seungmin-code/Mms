package com.min.mms.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/memberJoinPage")
    public String memberJoinPage() {
        return "user/member_join";
    }

    @GetMapping("/memberDeletePage")
    public String memberDeletePage() {
        return "user/member_delete";
    }

    @GetMapping("memberSelectPage")
    public String memberSelectPage() {
        return "user/member_select";
    }

    @GetMapping("memberUpdatePage")
    public String memberUpdatePage() {
        return "user/member_update";
    }

}
