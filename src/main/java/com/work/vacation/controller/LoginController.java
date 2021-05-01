package com.work.vacation.controller;

import org.springframework.web.bind.annotation.*;
import com.work.vacation.service.LoginService;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/loginApi")
public class LoginController {

    private LoginService loginService;

    // 로그인
    @PostMapping("/login")
    public String login(){

        String sendUrl = "/";

//        session.setAttribute("loginId","test");

        System.out.println("login Success");

        return sendUrl;
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session){

        String sendUrl = "";

        session.invalidate();

        return sendUrl;
    }



}
