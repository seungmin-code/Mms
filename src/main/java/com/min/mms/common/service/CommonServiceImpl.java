package com.min.mms.common.service;

import com.min.mms.common.mapper.CommonMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {

    private final CommonMapper commonMapper;

    public CommonServiceImpl(CommonMapper commonMapper) {
        this.commonMapper = commonMapper;
    }

    @Override
    public List<String> fetchSidoData() {
        return commonMapper.fetchSidoData();
    }

    @Override
    public List<String> fetchStationData(String sido) {
        return commonMapper.fetchStationData(sido);
    }
}
