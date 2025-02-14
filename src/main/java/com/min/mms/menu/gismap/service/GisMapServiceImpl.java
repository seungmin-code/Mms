package com.min.mms.menu.gismap.service;

import com.min.mms.menu.gismap.mapper.GisMapMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GisMapServiceImpl implements GisMapService {

    private final GisMapMapper gisMapMapper;

    public GisMapServiceImpl(GisMapMapper gisMapMapper) {
        this.gisMapMapper = gisMapMapper;
    }

    @Override
    public List<Map<String, Object>> getStationMarkerData() {
        return gisMapMapper.getStationMarkerData();
    }
}
