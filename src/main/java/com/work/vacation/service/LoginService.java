package com.work.vacation.service;

import com.work.vacation.dao.MemberDao;
import com.work.vacation.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginService {

    @Autowired
    MemberDao memberDao;

    // 유저정보 조회
    public Member selectMember(String id){
        Member memberParam = new Member();
        memberParam.setId(id);
        return memberDao.selectMember(memberParam);
    }

    // 유저 휴가 정보 0일로 세팅
    public int updateMemberVacationInfo(){
        return memberDao.updateMemberVacationInfo();
    }

}
