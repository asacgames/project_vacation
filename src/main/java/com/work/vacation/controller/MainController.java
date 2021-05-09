package com.work.vacation.controller;

import com.work.vacation.model.Member;
import com.work.vacation.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    private LoginService loginService;

    // 기본페이지
    @RequestMapping("/main")
    public String mainPage(){
        return "index";
    }


    // 로그인 이후 메인페이지
    @GetMapping("/loginMain.do")
    public String logout(HttpSession session, Model model){

        String userId = (String)session.getAttribute("loginId");

        // 로그인 정보 조회
        Member member = loginService.selectMember(userId);

        model.addAttribute("member", member);
        model.addAttribute("restVacationNum", member.getVacation() - member.getVacation_use());

        return "loginPage";
    }

}
