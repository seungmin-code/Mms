package com.min.mms.reservation.controller;

import com.min.mms.reservation.model.ReservationRequest;
import com.min.mms.reservation.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reservation")
public class ReservationRestController {

    private final ReservationService reservationService;

    public ReservationRestController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
            // 파라미터 유효성 검사
            reservationRequest.reservationRequestValidate();

            // 예약처리 작업
            // reservationService.createReservation();

            response.put("status", "success");
            response.put("message", "예약성공");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "예약실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}
