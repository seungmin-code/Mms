package com.min.mms.menu.user.notices.service;

import java.util.List;
import java.util.Map;

public interface NoticesService {

    List<Map<String, Object>> getNoticesDataList(Map<String, Object> params);

}
