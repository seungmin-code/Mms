package com.min.mms.menu.reatimeday.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/realTimeDay")
public class RealTimeDayController {

    @GetMapping("/select")
    public String realTimeDaySelect() {
        return "menu/realtimeday/realtime_day_select";
    }

}
