package com.work.vacation;

import com.work.vacation.model.Member;
import com.work.vacation.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class LoginServiceTest {

    LoginService service;

    @Test
    public void selectMember() {
        Member param = new Member();
        param.setId("test");
        param.setPassword("test");
        Member member = service.selectMember(param);
        log.info("member : {}", member.getName());
    }
}
