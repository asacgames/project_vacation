package com.work.vacation.dao;

import com.work.vacation.model.Member;
import com.work.vacation.model.Vacation;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface VacationDao {

    Vacation selectVacationDulCheck(Vacation param);

    int insertVacation(Vacation param);

    int updateVacationRegister(HashMap<String,Object> param);

    List<Map<String, Object>> selectVacationInfo(Vacation param);

    int selectVacationInfoCnt(Vacation param);

    Map<String, Object> selectVacationCancelCheck(Vacation param);

    int updateVacationCancel(HashMap<String,Object> param);

}
