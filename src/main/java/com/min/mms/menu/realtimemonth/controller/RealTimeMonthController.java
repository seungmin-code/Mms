package com.min.mms.menu.realtimemonth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/realTimeMonth")
public class RealTimeMonthController {

    @GetMapping("/select")
    public String realTimeMonthSelect() {
        return "menu/realtimemonth/realtime_month_select";
    }

}
