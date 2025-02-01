package com.min.mms.menu.user.resources.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class ResourcesController {
    // 자료실
    @GetMapping("resources")
    public String boardResources() {
        return "user/resources/resources_select";
    }
}
