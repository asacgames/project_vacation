package com.work.vacation.schedule;

import com.work.vacation.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VacationManagement {

    @Autowired
    private LoginService loginService;

    @Scheduled(cron = "0 0 0 1 1 *")
    public void vacationSet(){
        int count = loginService.updateMemberVacationInfo();
        log.info("연차 15일 부여된 직원 수 : " + count);
    }
}
