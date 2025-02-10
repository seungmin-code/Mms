package com.min.mms.menu.realtimemonth.service;

import com.min.mms.menu.realtimemonth.mapper.RealTimeMonthMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RealTimeMonthServiceImpl implements RealTimeMonthService {

    private final RealTimeMonthMapper realTimeMonthMapper;

    public RealTimeMonthServiceImpl(RealTimeMonthMapper realTimeMonthMapper) {
        this.realTimeMonthMapper = realTimeMonthMapper;
    }

}
