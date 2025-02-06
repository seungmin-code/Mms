package com.min.mms.menu.reatimeday.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/realTimeDay")
public class RealTimeDayController {

    @GetMapping("/averages")
    public String realTimeDayAverages(Model model) {
        return "menu/realtimeday/realtime_day_select";
    }

}
