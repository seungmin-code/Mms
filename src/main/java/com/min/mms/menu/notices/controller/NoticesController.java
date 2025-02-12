package com.min.mms.menu.notices.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notices")
public class NoticesController {

    /**
     * 공지사항 목록 조회 페이지로 이동합니다.
     *
     * @return 공지사항 목록 조회 페이지의 뷰 이름 ("menu/notices/notices_select")
     */
    @GetMapping("/select")
    public String noticesSelect() {
        return "menu/notices/notices_select";
    }

    /**
     * 공지사항 상세 조회 페이지로 이동합니다.
     *
     * @param id    공지사항의 고유 ID (PathVariable을 통해 URL에서 추출)
     * @param model Spring의 Model 객체로, 뷰에 데이터를 전달하기 위해 사용됩니다.
     * @return 공지사항 상세 조회 페이지의 뷰 이름 ("menu/notices/notices_detail")
     */
    @GetMapping("/detail/{id}")
    public String noticesDetail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "menu/notices/notices_detail";
    }

    /**
     * 공지사항 생성 페이지로 이동합니다.
     *
     * @return 공지사항 생성 페이지의 뷰 이름 ("menu/notices/notices_create")
     */
    @GetMapping("/create")
    public String noticesCreate() {
        return "menu/notices/notices_create";
    }

    /**
     * 공지사항 수정 페이지로 이동합니다.
     *
     * @param id    공지사항의 고유 ID (PathVariable을 통해 URL에서 추출)
     * @param model Spring의 Model 객체로, 뷰에 데이터를 전달하기 위해 사용됩니다.
     * @return 공지사항 수정 페이지의 뷰 이름 ("menu/notices/notices_update")
     */
    @GetMapping("/update/{id}")
    public String noticesUpdate(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "menu/notices/notices_update";
    }


}
