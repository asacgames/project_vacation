-- 테이블이 존재하면 드랍
DROP TABLE IF EXISTS MEMBER; -- 유저 정보
DROP TABLE IF EXISTS VACATION_INFO; -- 휴가 정보

-- 유저 테이블 생성
CREATE TABLE MEMBER (
                        ID VARCHAR(20) NOT NULL PRIMARY KEY -- 아이디
                      , NAME VARCHAR(20) NOT NULL           -- 이름
                      , PASSWORD VARCHAR(50) NOT NULL       -- 비밀번호
                      , POSITION_NM VARCHAR(20)             -- 직책
                      , GROUP_NM VARCHAR(30)                -- 조직
                      , VACATION DECIMAL(5, 2)              -- 총 연차
                      , VACATION_USE DECIMAL(5, 2)          -- 사용한 연차
                      , START_DT DATE                       -- 입사일
                      , CRETR_ID VARCHAR(20)                -- 생성자
                      , CRET_DT DATE                        -- 생성일시
                      , AMDR_ID VARCHAR(20)                 -- 수정자
                      , AMD_DT DATE                         -- 수정일시
                    );

-- 휴가관리 테이블 생성
CREATE TABLE VACATION_INFO (ID VARCHAR(20) PRIMARY KEY,VACATION_TYPE CHAR(1), VACATION_ST_DATE DATE, VARCATION_END_DATE DATE, USE_DAY DECIMAL(5,2), CRETR_ID VARCHAR(20), CRET_DT DATE);

-- 로그인을 위한 유저 데이터 추가
INSERT INTO MEMBER (ID, NAME, PASSWORD, POSITION_NM, GROUP_NM, VACATION, VACATION_USE, START_DT, CRETR_ID, CRET_DT) VALUES ('admin', '관리자', 'admin', '관리자', '크로키닷컴', 0, 0, TO_DATE('20200412', 'YYYYMMDD'), 'admin', CURRENT_DATE);
INSERT INTO MEMBER (ID, NAME, PASSWORD, POSITION_NM, GROUP_NM, VACATION, VACATION_USE, START_DT, CRETR_ID, CRET_DT) VALUES ('test', '테스터', 'test', '테스터', '크로키닷컴', 15, 0, CURRENT_DATE, 'admin',CURRENT_DATE);