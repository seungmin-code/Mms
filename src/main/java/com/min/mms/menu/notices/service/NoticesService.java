package com.min.mms.menu.notices.service;

import com.min.mms.menu.notices.model.NoticesUpdateDTO;

import java.util.List;
import java.util.Map;

public interface NoticesService {

    List<Map<String, Object>> getNoticesData(Map<String, Object> request);

    Map<String, Object> getNoticesDetailData(String id);

    void createNotices(Map<String, Object> request);

    void patchNotices(String id, NoticesUpdateDTO request);

    void deleteNotices(String id);

}
