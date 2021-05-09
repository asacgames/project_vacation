package com.work.vacation.common;

public class CommonUtils {

    /*
     *  문자열 반환
     *  null일 경우 rtVal 값을 반환
     *  null이 아닐 경우 해당 값을 반환
     */
    public static String isNullToStr(Object val, String rtVal){
        String result = "";
        if(val == null){return "";}
        else{result = val.toString();}
        return result;
    }

    /*
     *  문자열 공백 또는 null 체크
     *  공백 또는 null일 경우 true
     */
    public static Boolean isEmptyNull(String val){
        if(val == null){return true;}
        if(val.trim().equals("")){return true;}
        return false;
    }

}
