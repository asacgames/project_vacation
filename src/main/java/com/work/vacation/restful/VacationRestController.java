package com.work.vacation.restful;

import com.work.vacation.common.CommonCode;
import com.work.vacation.common.CommonUtils;
import com.work.vacation.model.Member;
import com.work.vacation.model.Vacation;
import com.work.vacation.service.LoginService;
import com.work.vacation.service.VacationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/vacation")
public class VacationRestController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private VacationService vacationService;

    // 휴가 신청
    @ResponseBody
    @PostMapping("/register")
    public HashMap<String, Object> vacationResister(@RequestBody HashMap<String, Object> req){

        HashMap<String, Object> result = new HashMap<>();

        // 유효성 체크 및 파라미터 세팅
        HashMap<String, Object> param = registerParamCheck(req);

        String userId = ""; // 유저아이디

        // 유효성 체크 성공 여부
        if(!"success".equals(param.get("paramResult"))){
            result.put("resultCode", CommonCode.REGISTER_INVAILD_PARAM);
            result.put("resultMsg", param.get("errorCode"));
            return result;
        }

        try {
            userId = (String) param.get("userId");

            // 로그인 정보 조회
            Member member = loginService.selectMember(userId);

            if (member != null) {

                Vacation vacationInfo = new Vacation();
                vacationInfo.setId(userId);
                vacationInfo.setVacation_st_date((String) param.get("startDayTime"));
                vacationInfo.setVacation_end_date((String) param.get("endDayTime"));
                vacationInfo.setVacation_type((String) param.get("vacationType"));
                vacationInfo.setUse_day((double) param.get("vacationDayNum"));
                vacationInfo.setStartTimeCheck((String) param.get("startTimeCheck"));
                vacationInfo.setEndTimeCheck((String) param.get("endTimeCheck"));
                vacationInfo.setComment((String) param.get("comment"));

                double totalVacationNum = member.getVacation();
                double useVacationNum = member.getVacation_use();
                double viewVacationNum = (double) param.get("vacationDayNum");

                if((totalVacationNum-useVacationNum) < viewVacationNum) {
                    result.put("resultCode", CommonCode.REGISTER_LACK_VACATION);
                    result.put("resultMsg", "남은 연차일이 부족합니다.");
                    return result;
                }

                boolean alreadyCheck = vacationService.selectVacationDulCheck(vacationInfo);
                if(alreadyCheck){
                    result.put("resultCode", CommonCode.REGISTER_ALREADY);
                    result.put("resultMsg", "휴가 날짜가 겹쳐서 신청 할 수 없습니다.");
                    return result;
                }

                // 휴가 등록
                vacationService.insertVacation(vacationInfo);
                vacationService.updateVacationRegister(param);

            } else {
                result.put("resultCode", CommonCode.REGISTER_NO_MEMBER_INFO);
                result.put("resultMsg", "휴가 신청 회원정보가 없습니다.");
                return result;
            }

            result.put("resultCode", CommonCode.REGISTER_SUCCESS);
            result.put("resultMsg", "휴가 신청이 정상적으로 처리되었습니다.");

        }catch (NullPointerException e){
            log.error("vacationResister NullpointException : " + e.getMessage());
            result.put("resultCode", CommonCode.REGISTER_SYSTEM_ERROR);
            result.put("resultMsg", "오류가 발생했습니다. 관리자에게 문의해주세요.");
        }catch (Exception e){
            log.error("vacationResister Exception : " + e.getMessage());
            result.put("resultCode", CommonCode.REGISTER_SYSTEM_ERROR);
            result.put("resultMsg", "오류가 발생했습니다. 관리자에게 문의해주세요.");
        }finally {
            return result;
        }

    }

    // 휴가신청 유효성 체크 및 파라미터 세팅
    public HashMap<String, Object> registerParamCheck(HashMap<String, Object> param){

        HashMap<String, Object> result = new HashMap<>();

        String userId = CommonUtils.isNullToStr(param.get("userId"), "");
        String startDate = CommonUtils.isNullToStr(param.get("startDate"), "");
        String endDate = CommonUtils.isNullToStr(param.get("endDate"), "");
        String startTime = CommonUtils.isNullToStr(param.get("startTime"), "");
        String endTime = CommonUtils.isNullToStr(param.get("endTime"), "");
        String vacationType = "";
        String comment = CommonUtils.isNullToStr(param.get("comment"), "");

        double dayCal = 0;
        double day = 0;
        double time = 0;

        // null 및 빈 값 체크
        if("".equals(userId.trim())){
            result.put("paramResult", "fail");
            result.put("errorCode", "파라미터가 올바르지 않습니다.");
            return result;
        }
        if("".equals(startDate.trim())){
            result.put("paramResult", "fail");
            result.put("errorCode", "파라미터가 올바르지 않습니다.");
            return result;
        }
        if("".equals(endDate.trim())){
            result.put("paramResult", "fail");
            result.put("errorCode", "파라미터가 올바르지 않습니다.");
            return result;
        }

        if("".equals(startTime.trim()) && !"".equals(endTime.trim())){
            startTime = "09";
        }else if(!"".equals(startTime.trim()) && "".equals(endTime.trim())){
            endTime = "18";
        }else if("".equals(startTime.trim()) && "".equals(endTime.trim())){
            startTime = "09";
            endTime = "18";
        }

        // 날짜 데이터 형식 체크
        startDate = startDate.replaceAll("[^0-9]", "");
        endDate = endDate.replaceAll("[^0-9]", "");

        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");

        try {
            dtFormat.parse(startDate);
            dtFormat.parse(endDate);
        }catch(ParseException e){
            result.put("paramResult", "fail");
            result.put("errorCode", "날짜가 올바르지 않습니다.");
            return result;
        }

        startTime = startTime.replaceAll("[^0-9]", "");
        endTime = endTime.replaceAll("[^0-9]", "");

        // 날짜 계산
        day = numberOfDay(startDate, endDate);

        if(day == -99){
            result.put("paramResult", "fail");
            result.put("errorCode", "날짜가 올바르지 않습니다.");
            return result;
        }

        // 시간 계산
        time = numberOfTime(startTime, endTime);

        if(time == -99){
            result.put("paramResult", "fail");
            result.put("errorCode", "날짜가 올바르지 않습니다.");
            return result;
        }

        dayCal = day + time;

        if(dayCal <= 0){
            result.put("paramResult", "fail");
            result.put("errorCode", "날짜가 올바르지 않습니다.");
            return result;
        }

        // 0.25 :반반차, 0.5 : 반차, 나머지 : 휴가
        if(dayCal == 0.25){
            vacationType = CommonCode.VACATION_HALF_AND_HALF_DAY;
        }else if(dayCal == 0.5){
            vacationType = CommonCode.VACATION_HALF_DAY;
        }else{
            vacationType = CommonCode.VACATION_DAY;
        }

        // 주말 여부 확인
        try {
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            Date date1 = df.parse(startDate.substring(0, 8));
            Date date2 = df.parse(endDate.substring(0, 8));
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(date1);
            cal2.setTime(date2);

            if ((Calendar.SATURDAY == cal1.get(Calendar.DAY_OF_WEEK))
                    || (Calendar.SUNDAY == cal1.get(Calendar.DAY_OF_WEEK))) {

                result.put("paramResult", "fail");
                result.put("errorCode", "주말은 휴가를 신청할 수 없습니다.");
                return result;
            }

            if ((Calendar.SATURDAY == cal2.get(Calendar.DAY_OF_WEEK))
                    || (Calendar.SUNDAY == cal2.get(Calendar.DAY_OF_WEEK))) {

                result.put("paramResult", "fail");
                result.put("errorCode", "주말은 휴가를 신청할 수 없습니다.");
                return result;
            }

        }catch (ParseException e){
            result.put("paramResult", "fail");
            result.put("paramResult", "주말여부 확인 실패");
            return result;
        }

        // 파라미터 세팅
        result.put("paramResult", "success");
        result.put("userId", userId);
        if("".equals(CommonUtils.isNullToStr(param.get("startTime"), ""))){
            result.put("startDayTime", startDate);
        }else{
            result.put("startDayTime", startDate + param.get("startTime"));
            result.put("startTimeCheck", "Y");
        }
        if("".equals(CommonUtils.isNullToStr(param.get("endTime"), ""))) {
            result.put("endDayTime", endDate);
        }else{
            result.put("endDayTime", endDate + param.get("endTime"));
            result.put("endTimeCheck", "Y");
        }

        result.put("vacationDayNum", dayCal);
        result.put("vacationType", vacationType);
        result.put("comment", comment);

        return result;
    }

    // 날짜 계산하여 반환
    public double numberOfDay(String startDate, String endDate){
        double result = 0;

        try{
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            Date date1 = df.parse(startDate.substring(0,8));
            Date date2 = df.parse(endDate.substring(0,8));
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(date1);
            cal2.setTime(date2);

            int numberOfDays = 0;
            while (cal1.before(cal2)) {
                if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
                        &&(Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
                    numberOfDays++;
                }
                cal1.add(Calendar.DATE,1);
            }

            result = numberOfDays;

        }catch(ParseException e){
            return -99;
        }

        return result;
    }

    
    // 시간 계산하여 반환
    public double numberOfTime(String startTime, String endTime){
        double result = 0;

        int stTime = Integer.parseInt(startTime.substring(0,2));
        int edTime = Integer.parseInt(endTime.substring(0,2));
        int calTime = edTime - stTime;

        if(calTime == 0){
            result = 0;
        }else if(calTime == 2 || calTime == 3){
            result = 0.25;
        }else if(calTime == 4 || calTime == 5 || calTime == 6){
            result = 0.5;
        }else if(calTime == 7){
            result = 0.75;
        }else if(calTime == 9){
            result = 1;
        }else if(calTime == -2 || calTime == -3){
            result = -0.25;
        }else if(calTime == -4 || calTime == -5 || calTime == -6){
            result = -0.5;
        }else if(calTime == -7){
            result = -0.75;
        }else if(calTime == -9){
            result = -1;
        }else{
            result = -99;
        }

        return result;
    }

    // 휴가 취소
    @ResponseBody
    @PostMapping("/cancel")
    public HashMap<String, Object> vacationRegCancel(@RequestBody HashMap<String, Object> req){

        HashMap<String, Object> result = new HashMap<>();

        // 유효성 체크 및 파라미터 세팅
        HashMap<String, Object> param = cancelParamCheck(req);

        // 유효성 체크 성공 여부
        if(!"success".equals(param.get("paramResult"))){
            result.put("resultCode", CommonCode.REGISTER_INVAILD_PARAM);
            result.put("resultMsg", "파라미터가 올바르지 않습니다.");
            return result;
        }

        try{

            Vacation vacationInfo = new Vacation();
            vacationInfo.setId((String) param.get("userId"));
            vacationInfo.setIndex((Integer) param.get("index"));

            Map<String, Object> cancelCheck = vacationService.selectVacationCancelCheck(vacationInfo);

            if(cancelCheck == null){
                result.put("resultCode", CommonCode.CANCEL_NO_VACATION_INFO);
                result.put("resultMsg", "휴가취소 기한이 지났거나 휴가 신청정보가 없습니다.");
                return result;
            }

            if("Y".equals(cancelCheck.get("CANCEL_YN"))){
                result.put("resultCode", CommonCode.CACEL_ALREADY);
                result.put("resultMsg", "이미 취소 처리된 건입니다.");
                return result;
            }

            HashMap<String, Object> cancelParam = new HashMap<>();
            cancelParam.put("id", (String) param.get("userId"));
            cancelParam.put("index", param.get("index"));
            cancelParam.put("cancelDay", cancelCheck.get("USE_DAY"));

            int updateResult = vacationService.updateVacationCancel(cancelParam);

            if(updateResult < 1){
                result.put("resultCode", CommonCode.CANCEL_FAIL);
                result.put("resultMsg", "휴가 취소에 실패했습니다.");
                return result;
            }

            result.put("resultCode", CommonCode.CANCEL_SUCCESS);
            result.put("resultMsg", "휴가 신청이 정상적으로 처리되었습니다.");

        }catch (NullPointerException e){
            log.error("vacationRegCancel NullpointException : " + e.getMessage());
            result.put("resultCode", CommonCode.REGISTER_SYSTEM_ERROR);
            result.put("resultMsg", "오류가 발생했습니다. 관리자에게 문의해주세요.");
        }catch (Exception e){
            log.error("vacationRegCancel Exception : " + e.getMessage());
            result.put("resultCode", CommonCode.REGISTER_SYSTEM_ERROR);
            result.put("resultMsg", "오류가 발생했습니다. 관리자에게 문의해주세요.");
        }finally {
            return result;
        }

    }

    // 휴가취소 유효성 체크 및 파라미터 세팅
    public HashMap<String, Object> cancelParamCheck(HashMap<String, Object> param){

        HashMap<String, Object> result = new HashMap<>();

        String userId = CommonUtils.isNullToStr(param.get("userId"), "");
        int index = Integer.valueOf((String) param.get("vacationNum"));

        // null 및 빈 값 체크
        if("".equals(userId.trim())){
            result.put("paramResult", "fail");
            return result;
        }
        if(index < 0){
            result.put("paramResult", "fail");
            return result;
        }

        // 파라미터 세팅
        result.put("paramResult", "success");
        result.put("userId", userId);
        result.put("index", index);

        return result;
    }

}
