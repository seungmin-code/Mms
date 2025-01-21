package com.min.mms.reservation.service;

import com.min.mms.reservation.mapper.ReservationMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationMapper reservationMapper;

    public ReservationServiceImpl(ReservationMapper reservationMapper) {
        this.reservationMapper = reservationMapper;
    }



}
