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

    public Member selectMember(Member param){
        return memberDao.selectMember(param);
    }

    public int updateMemberVacationInfo(){
        return memberDao.updateMemberVacationInfo();
    }

}
