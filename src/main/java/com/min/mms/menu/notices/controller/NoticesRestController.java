package com.min.mms.menu.notices.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.min.mms.common.component.CommonComponent;
import com.min.mms.menu.notices.model.NoticesUpdateDTO;
import com.min.mms.menu.notices.service.NoticesService;
import com.min.mms.menu.realtimemonth.controller.RealTimeMonthRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
        response.put("data", noticesDetailData);
        return ResponseEntity.ok(response);
    }

    /**
     * 데이터 생성하기
     * @param title 제목   
     * @param content 내용
     * @param file 파일
     * @return 공지사항 등록 결과
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> noticesPostCreate(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {

        String filePath = "";
        String fileName = "";

        if (file != null && !file.isEmpty()) {
            // 파일 저장 경로 설정 (프로젝트 실행 경로 내 files 폴더)
            String uploadDirectory = System.getProperty("user.dir") + "/files/";

            // files 폴더가 없으면 생성
            File folder = new File(uploadDirectory);
            if (!folder.exists()) {
                folder.mkdir();
            }

            // 파일명 추출 및 고유한 파일명 생성 (중복 방지)
            String originalFileName = file.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String baseFileName = originalFileName.substring(0, originalFileName.lastIndexOf("."));

            // 파일명에 현재 시간 또는 UUID 추가
            fileName = baseFileName + "_" + System.currentTimeMillis() + extension; // 예: file_1638329232000.txt
            filePath = uploadDirectory + fileName;

            // 파일을 지정 경로로 저장
            file.transferTo(new File(filePath));
        }

        // 요청 데이터 맵 작성
        Map<String, Object> request = new HashMap<>();
        request.put("title", title);
        request.put("content", content);
        request.put("file_path", filePath);  // 파일 저장 경로 저장
        request.put("file_name", fileName);  // 고유한 파일명 저장

        // 공지사항 서비스 호출
        noticesService.createNotices(request);

        // 응답 반환
        Map<String, Object> response = new HashMap<>();
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
    public ResponseEntity<Map<String, Object>> noticesPatch(@PathVariable String id, @RequestBody NoticesUpdateDTO request) {
        Map<String, Object> response = new HashMap<>();
        System.out.println(request);
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
