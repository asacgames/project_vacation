package com.work.vacation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    // 기본페이지
    @RequestMapping("/")
    public String mainPage(){
        return "index";
    }
}
