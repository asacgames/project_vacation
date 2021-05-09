package com.work.vacation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.work.vacation.service.LoginService;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    // 로그아웃
    @GetMapping("/logout.do")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }

}
