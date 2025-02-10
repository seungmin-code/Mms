package com.min.mms.menu.finedustalert.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/finedustalert")
public class FineDustAlertController {

    @GetMapping("/select")
    public String fineDustAlertSelect() {
        return "menu/finedustalert/fine_dust_alert_select";
    }

}
