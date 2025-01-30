package com.min.mms.menu.user.notices.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.min.mms.common.CommonComponent;
import com.min.mms.menu.user.notices.service.NoticesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data/notices")
public class NoticesRestController {

    private final NoticesService noticesService;
    private final CommonComponent commonComponent;

    public NoticesRestController(NoticesService noticesService, CommonComponent commonComponent) {
        this.noticesService = noticesService;
        this.commonComponent = commonComponent;
    }


    // 공지사항 목록 조회
    @GetMapping
    public ResponseEntity<Map<String, Object>> getNotices(@RequestParam Map<String, Object> params) {
        Map<String, Object> response = new HashMap<String, Object>();

        int page = commonComponent.getParameter(params, "page", 1);
        int pageSize = commonComponent.getParameter(params, "pageSize", 10);

        // PageHelper 페이징 처리 시작
        PageHelper.startPage(page, pageSize);

        // 공지사항 목록 조회
        List<Map<String, Object>> noticesDataList = noticesService.getNoticesDataList(params);

        // PageInfo 통해 페이징 정보 담기
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(noticesDataList);

        // 데이터 및 페이징 정보
        response.put("data", noticesDataList);
        response.put("pagination", pageInfo);
        
        // API 호출결과 정보
        response.put("status", "success");
        response.put("message", "성공적으로 공지사항 목록을 가져왔습니다");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
