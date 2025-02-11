package com.min.mms.menu.notices.service;

import com.min.mms.menu.notices.mapper.NoticesMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NoticesServiceImpl implements NoticesService {

    private final NoticesMapper noticesMapper;

    public NoticesServiceImpl(NoticesMapper noticesMapper) {
        this.noticesMapper = noticesMapper;
    }

    @Override
    public List<Map<String, Object>> getNoticesData(Map<String, Object> request) {
        return noticesMapper.getNoticesData(request);
    }

    @Override
    public Map<String, Object> getNoticesDetailData(String id) {
        return noticesMapper.getNoticesDetailData(id);
    }

    @Override
    public void createNotices(Map<String, Object> request) {
        noticesMapper.createNotices(request);
    }

    @Override
    public void patchNotices(String id, Map<String, Object> request) {
        noticesMapper.patchNotices(id, request);
    }

    @Override
    public void deleteNotices(String id) {
        noticesMapper.deleteNotices(id);
    }
}
