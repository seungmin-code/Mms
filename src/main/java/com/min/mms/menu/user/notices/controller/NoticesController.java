package com.min.mms.menu.user.notices.controller;

import com.min.mms.menu.user.notices.service.NoticesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user/notices")
public class NoticesController {

    public String noticesViewPage() {
        return "notices";
    }

    public ModelAndView noticesDetailPage(String notices_num) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("notices_num", notices_num);
        mav.setViewName("notices");
        return mav;
    }



}
