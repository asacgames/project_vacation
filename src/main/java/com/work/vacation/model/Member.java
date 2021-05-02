package com.work.vacation.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("member")
public class Member {

    private String id;
    private String name;
    private String password;
    private double annual;
    private double annual_use;

    public Member(){

    }

    public Member(String id, String name, String password, double annual, double annual_use){
        this.id = id;
        this.name = name;
        this.password = password;
        this.annual = annual;
        this.annual_use = annual_use;
    }

}
