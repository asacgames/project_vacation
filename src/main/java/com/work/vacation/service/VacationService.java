package com.work.vacation.service;

import com.work.vacation.dao.VacationDao;
import com.work.vacation.model.Vacation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class VacationService {

    @Autowired
    VacationDao vacationDao;

    // 휴가 중복 체크
    public boolean selectVacationDulCheck(Vacation param){
        boolean result = false;
        Vacation vacation = vacationDao.selectVacationDulCheck(param);
        if(vacation != null){
            result = true;
        }
        return result;
    }

    // 휴가 신청
    public int insertVacation(Vacation param){
        return vacationDao.insertVacation(param);
    }

    // 휴가 신청 정보 갱신
    public int updateVacationRegister(HashMap<String, Object> param){
        return vacationDao.updateVacationRegister(param);
    }

    
    // 휴가 신청 내역
    public List<Map<String, Object>> selectVacationInfo(Vacation param){

        List<Map<String, Object>> list = vacationDao.selectVacationInfo(param);

        return list;
    }

    // 휴가 신청내역 카운트
    public int selectVacationInfoCnt(Vacation param){
        return vacationDao.selectVacationInfoCnt(param);
    }

    // 휴가 취소 유효성 체크
    public Map<String, Object> selectVacationCancelCheck(Vacation param){
        return vacationDao.selectVacationCancelCheck(param);
    }

    // 휴가 취소 정보 갱신
    public int updateVacationCancel(HashMap<String, Object> param){
        return vacationDao.updateVacationCancel(param);
    }


}
