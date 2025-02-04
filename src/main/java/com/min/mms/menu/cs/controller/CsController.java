package com.min.mms.menu.cs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cs")
public class CsController {

    @GetMapping("/notices")
    public String notices() {
        return "menu/cs/notice_select";
    }

}
