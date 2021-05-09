package com.work.vacation.controller;

import com.work.vacation.common.CommonUtils;
import com.work.vacation.model.Member;
import com.work.vacation.model.Vacation;
import com.work.vacation.service.LoginService;
import com.work.vacation.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class VacationController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private VacationService vacationService;

    // 휴가신청(등록) 화면
    @RequestMapping("/vacationPage.do")
    public String vacationReg(HttpSession session, Model model){

        String userId = (String)session.getAttribute("loginId");
        String today = "";

        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd");

        Date time = new Date();

        Calendar cal = Calendar.getInstance();

        cal.setTime(time);

        if (Calendar.SATURDAY == cal.get(Calendar.DAY_OF_WEEK)) {
            cal.add(Calendar.DATE,2);
        }else if(Calendar.SUNDAY == cal.get(Calendar.DAY_OF_WEEK)){
            cal.add(Calendar.DATE,1);

        }

        today = format.format(cal.getTime());

        // 로그인 정보 조회
        Member member = loginService.selectMember(userId);

        model.addAttribute("positionNm", member.getPosition_nm());
        model.addAttribute("groupNm", member.getGroup_nm());
        if(member.getVacation()%1==0) {
            int vacation = (int) member.getVacation();
            model.addAttribute("vacation", vacation);
        }else{
            model.addAttribute("vacation", member.getVacation());
        }
        if(member.getVacation_use()%1==0) {
            int vacationUse = (int) member.getVacation_use();
            model.addAttribute("vacationUse", vacationUse);
        }else{
            model.addAttribute("vacationUse", member.getVacation_use());
        }
        if(member.getVacation() - member.getVacation_use()%1==0) {
            int vacationRest = (int) ((int) member.getVacation() - member.getVacation_use());
            model.addAttribute("vacationRest", vacationRest);
        }else{
            model.addAttribute("vacationRest", member.getVacation() - member.getVacation_use());
        }

        model.addAttribute("startDt", member.getStart_dt());
        model.addAttribute("today",today);

        return "vacationPage";
    }

    // 휴가신청 내역 화면
    @RequestMapping("/vacationListPage.do")
    public String vacationList(HttpSession session, Model model, HttpServletRequest req){

        String userId = (String)session.getAttribute("loginId");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyyMMdd");

        startDate = CommonUtils.isNullToStr(startDate, "");
        endDate = CommonUtils.isNullToStr(endDate, "");

        // 기본 한달전 한달후로 세팅
        if("".equals(startDate) || "".equals(endDate)){
            cal.add(Calendar.MONTH, 1);
            endDate = df.format(cal.getTime());
            cal.add(Calendar.MONTH, -2);
            startDate = df.format(cal.getTime());
        }

        Vacation vacationInfo = new Vacation();
        vacationInfo.setId(userId);
        vacationInfo.setVacation_st_date(startDate);
        vacationInfo.setVacation_end_date(endDate);

        int resultCnt = vacationService.selectVacationInfoCnt(vacationInfo);
        List<Map<String, Object>> list = null;

        if(resultCnt > 0) {
            // 로그인 정보 조회
            list = vacationService.selectVacationInfo(vacationInfo);
        }

        model.addAttribute("vacationList", list);
        model.addAttribute("resultCount", resultCnt);

        return "vacationListPage";
    }
}
