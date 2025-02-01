package com.min.mms.menu.user.notices.service;

import com.min.mms.menu.user.notices.mapper.NoticesMapper;
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
    public List<Map<String, Object>> getNoticesDataList(Map<String, Object> params) {
        return noticesMapper.getNoticesDataList(params);
    }

    @Override
    public Map<String, Object> getNoticesDetail(Long id) {
        return noticesMapper.getNoticesDetail(id);
    }

    @Override
    public void insertNotice(Map<String, Object> params) {
        noticesMapper.insertNotice(params);
    }

    @Override
    public void deleteNotice(Long id) {
        noticesMapper.deleteNotice(id);
    }
}
