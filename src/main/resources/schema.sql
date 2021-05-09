-- 초기 DB세팅

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
                      , CRET_DT TIMESTAMP                   -- 생성일시
                      , AMDR_ID VARCHAR(20)                 -- 수정자
                      , AMD_DT TIMESTAMP                    -- 수정일시
                    );

-- 휴가관리 테이블 생성
CREATE TABLE VACATION_INFO (INDEX INT AUTO_INCREMENT NOT NULL, ID VARCHAR(20) NOT NULL, VACATION_ST_DATE TIMESTAMP NOT NULL, VACATION_END_DATE TIMESTAMP NOT NULL, VACATION_TYPE VARCHAR(3), USE_DAY DECIMAL(5,2), CANCEL_YN CHAR(1), COMMENT VARCHAR(200), CANCEL_DT TIMESTAMP, CRETR_ID VARCHAR(20), CRET_DT TIMESTAMP, AMDR_ID VARCHAR(20), AMD_DT TIMESTAMP);
ALTER TABLE VACATION_INFO ADD CONSTRAINT VACATION_INFO_PK PRIMARY KEY(INDEX);
CREATE INDEX ID ON VACATION_INFO(ID);

-- 로그인을 위한 유저 데이터 추가
INSERT INTO MEMBER (ID, NAME, PASSWORD, POSITION_NM, GROUP_NM, VACATION, VACATION_USE, START_DT, CRETR_ID, CRET_DT) VALUES ('admin', '관리자', 'admin', '관리자', '크로키닷컴', 0, 0, TO_DATE('20200412', 'YYYYMMDD'), 'admin', SYSDATE);
INSERT INTO MEMBER (ID, NAME, PASSWORD, POSITION_NM, GROUP_NM, VACATION, VACATION_USE, START_DT, CRETR_ID, CRET_DT) VALUES ('test', '테스터', 'test', '테스터', '크로키닷컴', 15, 0, SYSDATE, 'admin',SYSDATE);