package com.min.mms.dataChart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dataChart")
public class DataChartController {

    @GetMapping("/main")
    public String dataChartMain() {
        return "dataChart/main";
    }

}
