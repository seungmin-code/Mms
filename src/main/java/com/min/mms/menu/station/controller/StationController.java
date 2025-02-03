package com.min.mms.menu.station.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StationController {

    @GetMapping("/station/read")
    public String stationRead() {
        return "menu/station/station_read";
    }

}
