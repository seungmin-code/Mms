package com.min.mms.menu.user.observation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/observation")
public class ObservationController {

    // 측정소 정보
    @GetMapping("/information")
    public String observationInformation() {
        return "user/observation/information";
    }

}
