package com.work.vacation.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("member")
public class Member {

    private String id;
    private String name;
    private String password;
    private String position_nm;
    private String group_nm;
    private double vacation;
    private double vacation_use;
    private String start_dt;

    public Member(){

    }

    public Member(String id, String name, String password, String position_nm, String group_nm, double vacation, double vacation_use, String start_dt){
        this.id = id;
        this.name = name;
        this.password = password;
        this.position_nm = position_nm;
        this.group_nm = group_nm;
        this.vacation = vacation;
        this.vacation_use = vacation_use;
        this.start_dt = start_dt;
    }

}
