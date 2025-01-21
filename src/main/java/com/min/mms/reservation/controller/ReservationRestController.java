package com.min.mms.reservation.controller;

import com.min.mms.reservation.service.ReservationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reservation")
public class ReservationRestController {

    private final ReservationService reservationService;

    public ReservationRestController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/dbconnect")
    public Map<String, Object> dbconnect() {
        Map<String, Object> result = new HashMap<>();
        result = reservationService.checkDbConnect();
        return result;
    }



}
