package com.min.mms.menu.realtimesido.controller;

import com.min.mms.menu.station.controller.StationRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/realTimeSido")
public class RealTimeSidoController {

    private static final Logger logger = LoggerFactory.getLogger(RealTimeSidoController.class);

    @GetMapping("/select")
    public String realTimeSidoSelect(Model model) {
        return "menu/realtimesido/realtime_sido_select";
    }

}
