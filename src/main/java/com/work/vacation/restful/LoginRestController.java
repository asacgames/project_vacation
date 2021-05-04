package com.work.vacation.restful;

import com.work.vacation.common.CommonCode;
import com.work.vacation.common.CommonUtils;
import com.work.vacation.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.work.vacation.service.LoginService;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/loginApi")
public class LoginRestController {

    @Autowired
    private LoginService loginService;

    // 로그인
    @PostMapping("/login")
    public String login(String inputId, String inputPass, HttpSession session) {

        String resultMsg = CommonCode.SUCCESS;  // 결과메시지
        String id = "";     // 아이디
        String name = "";   // 이름
        String pass = "";   // 비밀번호

        if(CommonUtils.isEmptyNull(inputId)){
            return CommonCode.NO_MEMBER_ID;
        }
        if(CommonUtils.isEmptyNull(inputPass)){
            return CommonCode.NO_MEMBER_PASSWORD;
        }

        Member member;
        Member memberParam = new Member();
        memberParam.setId(inputId);
        memberParam.setPassword(inputPass);

        // 로그인 정보 조회
        member = loginService.selectMember(memberParam);

        if(member != null) {
            id = member.getId();
            name = member.getName();
            pass = member.getPassword();

            if(!inputPass.equals(pass)){
                return CommonCode.INVAILD_PASSWORD;
            }

            session.setAttribute("loginId", id);
            session.setAttribute("loginName", name);
        }else{
            resultMsg = CommonCode.NO_MEMBER_INFO;
        }

        return resultMsg;
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session){

        String sendUrl = "";

        session.invalidate();

        return sendUrl;
    }



}
