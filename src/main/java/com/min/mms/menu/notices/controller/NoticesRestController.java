package com.min.mms.menu.notices.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.min.mms.common.component.CommonComponent;
import com.min.mms.menu.notices.service.NoticesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notices")
public class NoticesRestController {
    /* 파일 기본 저장 경로 */
    private static final String UPLOAD_DIR = "./files";

    private final NoticesService noticesService;

    /* 공통 컴포넌트 */
    private final CommonComponent commonComponent;

    private static final Logger logger = LoggerFactory.getLogger(NoticesRestController.class);

    public NoticesRestController(NoticesService noticesService, CommonComponent commonComponent) {
        this.noticesService = noticesService;
        this.commonComponent = commonComponent;
    }

    /**
     * 공지사항 목록 데이터 조회
     * @param request 요청 파라미터 (페이지, 사이즈)
     * @return 공지사항 목록과 페이징 정보
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> noticesGet(@RequestParam Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        int page = CommonComponent.getPage(request);
        int size = CommonComponent.getSize(request);

        // 페이지 처리 시작
        PageHelper.startPage(page, size);
        List<Map<String, Object>> noticesData = noticesService.getNoticesData(request);

        response.put("data", noticesData);

        // 페이징 정보 추가
        PageInfo<Map<String, Object>> pagination = new PageInfo<>(noticesData);
        response.put("pagination", pagination);
        return ResponseEntity.ok(response);
    }

    /**
     * 공지사항 상세 데이터 조회
     * @param id 공지사항 아이디
     * @return 공지사항 상세 정보
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> noticesGetDetail(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> noticesDetailData = noticesService.getNoticesDetailData(id);
        response.put("data", noticesDetailData);
        return ResponseEntity.ok(response);
    }

    /**
     * 새로운 공지사항 등록
     * @param title 제목
     * @param content 내용
     * @param file 첨부 파일 (선택)
     * @return 공지사항 등록 결과
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> noticesPostCreate(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {

        // 파일 업로드 디렉토리 경로 설정
        String uploadDir = "./files";

        // 폴더가 없으면 생성
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 파일 경로 및 파일명 초기화
        String filePath = null;
        String fileName = null;
        String fileExtension = null;  // 확장자 저장용 변수

        if (file != null && !file.isEmpty()) {
            // 파일 이름 추출 및 확장자 확인
            String originalFileName = file.getOriginalFilename();
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

            // 현재 시간을 기준으로 파일명 생성
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            fileName = originalFileName.substring(0, originalFileName.lastIndexOf(".")) + "_" + timestamp + fileExtension;

            // 중복된 파일명이 있으면 변경
            File uploadedFile = new File(uploadDir + File.separator + fileName);
            int count = 1;
            while (uploadedFile.exists()) {
                fileName = fileName.substring(0, fileName.lastIndexOf("_")) + "_" + count + fileExtension;
                uploadedFile = new File(uploadDir + File.separator + fileName);
                count++;
            }

            // 최종 파일 경로 설정
            filePath = uploadDir + File.separator + fileName;

            // 파일 저장
            Path path = Path.of(filePath);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        }

        // 요청 데이터 맵에 담기
        Map<String, Object> request = new HashMap<>();
        request.put("title", title);
        request.put("content", content);
        request.put("file_name", fileName);
        request.put("file_path", filePath);

        // 공지사항 생성 서비스 호출
        noticesService.createNotices(request);

        // 응답 반환
        Map<String, Object> response = new HashMap<>();
        response.put("message", "공지사항이 성공적으로 생성되었습니다.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /**
     * 공지사항 일부 수정
     * @param id 공지사항 아이디
     * @param title 제목
     * @param content 내용
     * @param file 첨부 파일 (선택)
     * @param deleteFile 파일 삭제 여부 (선택)
     * @return 공지사항 수정 결과
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> noticesPatch(
            @PathVariable String id,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "deleteFile", required = false) String deleteFile) throws IOException {

        // 파일 업로드 디렉토리 경로 설정
        String uploadDir = "./files";

        // 폴더가 없으면 생성
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 기존 공지사항 데이터 조회
        Map<String, Object> existingData = noticesService.getNoticesDetailData(id);
        String existingFilePath = (String) existingData.get("file_path");
        String existingFileName = (String) existingData.get("file_name");

        // 파일 삭제 처리 (deleteFile이 "true"일 경우)
        if ("true".equals(deleteFile)) {
            if (existingFilePath != null && !existingFilePath.isEmpty()) {
                File existingFile = new File(existingFilePath);
                if (existingFile.exists()) {
                    existingFile.delete();  // 기존 파일 삭제
                }
            }
            // 기존 파일 정보 초기화
            existingFileName = null;
            existingFilePath = null;
        }

        // 새로운 파일 저장을 위한 변수 설정
        String filePath = existingFilePath;
        String fileName = existingFileName;
        String fileExtension = null;

        if (file != null && !file.isEmpty()) {
            // 파일 이름 추출 및 확장자 확인
            String originalFileName = file.getOriginalFilename();
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

            // 현재 시간을 기준으로 파일명 생성
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            fileName = originalFileName.substring(0, originalFileName.lastIndexOf(".")) + "_" + timestamp + fileExtension;

            // 중복된 파일명이 있으면 변경
            File uploadedFile = new File(uploadDir + File.separator + fileName);
            int count = 1;
            while (uploadedFile.exists()) {
                fileName = fileName.substring(0, fileName.lastIndexOf("_")) + "_" + count + fileExtension;
                uploadedFile = new File(uploadDir + File.separator + fileName);
                count++;
            }

            // 최종 파일 경로 설정
            filePath = uploadDir + File.separator + fileName;

            // 파일 저장
            Path path = Path.of(filePath);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // 기존 파일 삭제 (새 파일이 업로드 된 경우)
            if (existingFilePath != null && !existingFilePath.equals(filePath)) {
                File existingFile = new File(existingFilePath);
                if (existingFile.exists()) {
                    existingFile.delete();  // 기존 파일 삭제
                }
            }
        }

        // 요청 데이터 맵에 담기
        Map<String, Object> request = new HashMap<>();
        request.put("title", title);
        request.put("content", content);
        request.put("file_name", fileName);  // 파일명
        request.put("file_path", filePath);  // 파일 경로

        // 공지사항 수정 서비스 호출
        noticesService.patchNotices(id, request);

        // 응답 반환
        Map<String, Object> response = new HashMap<>();
        response.put("message", "공지사항이 성공적으로 수정되었습니다.");
        return ResponseEntity.ok(response);
    }



    /**
     * 공지사항 삭제
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

    /**
     * 파일 다운로드 기능
     * @param fileName 다운로드할 파일의 이름
     * @return 파일 다운로드 응답
     */
    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) throws IOException {
        // 파일 경로 설정
        Path filePath = Paths.get(UPLOAD_DIR, fileName);

        // 파일이 존재하는지 확인
        File file = filePath.toFile();
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null); // 파일이 없으면 404 응답
        }

        // 파일을 byte[]로 읽기
        byte[] fileBytes = Files.readAllBytes(filePath);

        // 파일의 MIME 타입 추정
        String mimeType = Files.probeContentType(filePath);
        if (mimeType == null) {
            mimeType = "application/octet-stream"; // 기본 MIME 타입 설정
        }

        // 파일명 인코딩 처리
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

        // 응답 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName);
        headers.add(HttpHeaders.CONTENT_TYPE, mimeType);
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()));

        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }
}
