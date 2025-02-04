package com.min.mms.menu.station.service;

import com.min.mms.menu.station.mapper.StationMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StationServiceImpl implements StationService {

    private final StationMapper stationMapper;

    public StationServiceImpl(StationMapper stationMapper) {
        this.stationMapper = stationMapper;
    }

    @Override
    public List<Map<String, Object>> getStationData(Map<String, Object> params) {
        return stationMapper.getStationData(params);
    }

    @Override
    public List<String>getStationCategory() {
        return stationMapper.getStationCategory();
    }
}
