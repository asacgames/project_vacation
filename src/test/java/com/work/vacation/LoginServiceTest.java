package com.work.vacation;

import com.work.vacation.model.Member;
import com.work.vacation.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class LoginServiceTest {

    @Autowired
    LoginService service;

    @Test
    public void selectMember() {
        Member member = service.selectMember("test");
        log.info("member : {}", member);
    }
}
