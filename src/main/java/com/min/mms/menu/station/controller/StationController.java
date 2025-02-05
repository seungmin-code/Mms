package com.min.mms.menu.station.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     * 특정 측정소의 상세 페이지로 이동.
     *
     * @param station_name 조회할 측정소의 이름 (경로 변수)
     * @param model 뷰에 데이터를 전달할 모델 객체
     * @return 측정소 상세 화면 템플릿 경로
     */
    @GetMapping("/{station_name}")
    public String stationDetail(@PathVariable String station_name, Model model) {
        return "menu/station/station_detail";
    }

    /**
     * 측정소 생성 페이지로 이동.
     *
     * @return 측정소 생성 화면 템플릿 경로
     */
    @GetMapping("/create")
    public String stationCreate() {
        return "menu/station/station_create";
    }
}
