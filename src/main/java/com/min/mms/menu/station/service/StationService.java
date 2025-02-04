package com.min.mms.menu.station.service;

import java.util.List;
import java.util.Map;

public interface StationService {

    List<Map<String, Object>> getStationData(Map<String, Object> params);

    List<String> getStationCategory();

}
