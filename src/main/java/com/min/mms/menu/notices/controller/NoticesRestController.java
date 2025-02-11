package com.min.mms.menu.notices.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.min.mms.common.component.CommonComponent;
import com.min.mms.menu.notices.service.NoticesService;
import com.min.mms.menu.realtimemonth.controller.RealTimeMonthRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notices")
public class NoticesRestController {

    private final NoticesService noticesService;
    private final CommonComponent commonComponent;

    private static final Logger logger = LoggerFactory.getLogger(NoticesRestController.class);

    public NoticesRestController(NoticesService noticesService, CommonComponent commonComponent) {
        this.noticesService = noticesService;
        this.commonComponent = commonComponent;
    }

    /**
     * 데이터 가져오기
     * @return 공지사항 데이터
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> noticesGet(@RequestParam Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        int page = CommonComponent.getPage(request);
        int size = CommonComponent.getSize(request);

        PageHelper.startPage(page, size);

        List<Map<String, Object>> noticesData =  noticesService.getNoticesData(request);
        response.put("data", noticesData);

        PageInfo<Map<String, Object>> pagination = new PageInfo<>(noticesData);
        response.put("pagination", pagination);
        return ResponseEntity.ok(response);
    }

    /**
     * 상세 데이터 가져오기
     * @param id 공지사항 아이디
     * @return 공지사항 상세 데이터
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> noticesGetDetail(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> noticesDetailData = noticesService.getNoticesDetailData(id);
        return ResponseEntity.ok(response);
    }

    /**
     * 데이터 생성하기
     * @param request 공지사항 생성 할 데이터
     * @return 공지사항 생성 결과
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> noticesPostCreate(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        noticesService.createNotices(request);
        response.put("message", "공지사항이 성공적으로 생성되었습니다.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 데이터 수정하기(일부)
     * @param id 공지사항 아이디
     * @param request 공지사항 수정 할 데이터
     * @return 공지사항 수정 결과(일부)
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> noticesPatch(@PathVariable String id, @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        noticesService.patchNotices(id, request);
        response.put("message", "공지사항 일부가 성공적으로 수정되었습니다.");
        return ResponseEntity.ok(response);
    }

    /**
     * 데이터 삭제하기
     * @param id 공지사항 아이디
     * @return 공지사항 삭제 결과
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> noticesDelete(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        noticesService.deleteNotices(id);
        response.put("message", "공지사항이 성공적으로 삭제되었습니다.");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

}
