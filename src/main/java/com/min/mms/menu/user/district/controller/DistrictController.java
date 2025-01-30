package com.min.mms.menu.user.district.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/district")
public class DistrictController {

    // 시도별 대기정보 (초미세먼지)
    @GetMapping("/finedustQuality")
    public String districtFinedust() {
        return "user/district/finedust_quality";
    }

    // 시도별 대기정보 (미세먼지)
    @GetMapping("/dustQuality")
    public String districtDust() {
        return "user/district/dust_quality";
    }

}
