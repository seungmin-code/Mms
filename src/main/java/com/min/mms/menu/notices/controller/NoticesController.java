package com.min.mms.menu.notices.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notices")
public class NoticesController {

    @GetMapping("/select")
    public String noticesSelect() {
        return "menu/notices/notices_select";
    }

    @GetMapping("/detail/{id}")
    public String noticesDetail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "menu/notices/notices_detail";
    }

    @GetMapping("/create")
    public String noticesCreate() {
        return "menu/notices/notices_create";
    }

    @GetMapping("/update")
    public String noticesUpdate() {
        return "menu/notices/notices_update";
    }

}
