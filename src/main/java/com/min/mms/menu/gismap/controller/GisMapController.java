package com.min.mms.menu.gismap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gismap")
public class GisMapController {

    @GetMapping("/select")
    public String gisMapSelect() {
        return "menu/gismap/gis_map_select";
    }
}
