package com.min.mms.menu.user.forecast.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forecast")
public class ForeCastController {

    // 초미세먼지 주간예보
    @GetMapping("/weeklyFinedustForecast")
    public String weeklyFinedustForecast() {
        return "user/forecast/weekly_finedust_forecast";
    }

    // 대기오염 경보발령
    @GetMapping("/airPollutionWarning")
    public String airPollutionWarning() {
        return "user/forecast/air_pollution_warning";
    }
}
