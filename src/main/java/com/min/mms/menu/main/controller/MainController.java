package com.min.mms.menu.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainSelect() {
        return "menu/main/main_select";
    }

}
