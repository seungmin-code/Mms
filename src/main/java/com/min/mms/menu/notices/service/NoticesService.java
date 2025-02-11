package com.min.mms.menu.notices.service;

import java.util.List;
import java.util.Map;

public interface NoticesService {

    List<Map<String, Object>> getNoticesData(Map<String, Object> request);

    Map<String, Object> getNoticesDetailData(String id);

    void createNotices(Map<String, Object> request);

    void patchNotices(String id, Map<String, Object> request);

    void deleteNotices(String id);

}
