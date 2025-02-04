package com.min.mms.menu.station.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/station")
public class StationController {

    @GetMapping("/stations")
    public String stations(Model model) {
        return "menu/station/station_select";
    }

    @GetMapping("/{station_name}")
    public String stationDetail(@PathVariable String station_name, Model model) {
        return "menu/station/station_detail";
    }

    @GetMapping("/create")
    public String stationCreate() {
        return "menu/station/station_create";
    }

}
