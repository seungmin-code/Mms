package com.min.mms.menu.realtimesido.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/realTimeSido")
public class RealTimeSidoController {

    @GetMapping("/averages")
    public String realTimeSidoAverages(Model model) {
        return "menu/realtimesido/realtime_sido_select";
    }

}
