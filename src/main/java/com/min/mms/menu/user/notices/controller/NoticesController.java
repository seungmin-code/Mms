package com.min.mms.menu.user.notices.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class NoticesController {

    // 공지사항 조회
    @GetMapping("notices")
    public String notices() {
        return "user/notices/notices_select";
    }
    
    // 공지사항 등록
    @GetMapping("notices/create")
    public String noticesCreate() {
        return "user/notices/notices_create";
    }

    // 공지사항 상세보기 + 삭제
    @GetMapping("notices/detail")
    public String noticesDetail() {
        return "user/notices/notices_detail";
    }

    // 공지사항 수정
    @GetMapping("notices/update")
    public String noticesUpdate() {
        return "user/notices/notices_update";
    }

}
