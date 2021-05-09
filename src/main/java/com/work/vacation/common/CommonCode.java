package com.work.vacation.common;

public class CommonCode {

    // 로그인 결과 메시지
    public final static String SUCCESS                      = "1000";        // 성공
    public final static String FAIL                         = "9000";        // 실패
    public final static String INVAILD_PARAM                = "1001";        // 유효하지 않는 파라미터
    public final static String NO_MEMBER_INFO               = "1002";        // 회원정보 없음
    public final static String NO_MEMBER_ID                 = "1003";        // 입력된 아이디가 없음
    public final static String NO_MEMBER_PASSWORD           = "1004";        // 입력된 비밀번호가 없음
    public final static String INVAILD_PASSWORD             = "1005";        // 유효하지 않은 비밀번호

    // 휴가 신청 관련
    public final static String REGISTER_SUCCESS             = "1000";        // 휴가 신청 성공
    public final static String REGISTER_INVAILD_PARAM       = "8000";        // 파라미터 오류
    public final static String REGISTER_NO_MEMBER_INFO      = "2001";        // 휴가신청 회원정보 없음
    public final static String REGISTER_ALREADY             = "2002";        // 이미 휴가 신청된 날짜 오류
    public final static String REGISTER_LACK_VACATION       = "2003";        // 연차 수 부족
    public final static String REGISTER_SYSTEM_ERROR        = "9999";        // 시스템 오류

    // 휴가 취소 관련
    public final static String CANCEL_SUCCESS               = "1000";        // 휴가 신청 성공
    public final static String CANCEL_NO_VACATION_INFO      = "3001";        // 휴가신청한 내역이 없음
    public final static String CACEL_ALREADY                = "3002";        // 이미 휴가취소 처리 됨
    public final static String CANCEL_FAIL                  = "3003";        // 휴가취소 실패

    // 휴가 유형
    public final static String VACATION_DAY                 = "VD";          // 휴가
    public final static String VACATION_HALF_DAY            = "VH";          // 반차
    public final static String VACATION_HALF_AND_HALF_DAY   = "VHH";         // 반반차

}
