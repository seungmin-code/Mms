package com.min.mms.admin.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/user/list")
    public String userList() {
        return "admin/users/user-list";
    }

    @GetMapping("/user/update")
    public String userUpdate() {
        return "admin/users/user-update";
    }

    @GetMapping("/user/insert")
    public String userInsert() {
        return "admin/users/user-insert";
    }

}
