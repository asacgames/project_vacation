package com.work.vacation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VacationController {

    // 휴가신청(등록)
    @RequestMapping("/vacationPage.do")
    public String vacationReg(){
        return "vacationPage";
    }
}
