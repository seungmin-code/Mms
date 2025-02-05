package com.min.mms.menu.station.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 측정소 관련 웹 페이지를 제공하는 컨트롤러.
 *
 * 측정소 목록 조회, 상세 조회, 생성 페이지로 이동하는 요청을 처리합니다.
 */
@Controller
@RequestMapping("/station")
public class StationController {

    /**
     * 측정소 목록 페이지로 이동.
     *
     * @param model 뷰에 데이터를 전달할 모델 객체
     * @return 측정소 목록 화면 템플릿 경로
     */
    @GetMapping("/stations")
    public String stations(Model model) {
        return "menu/station/station_select";
    }

}
