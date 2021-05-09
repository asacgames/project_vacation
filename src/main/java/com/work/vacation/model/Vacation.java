package com.work.vacation.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("vacation")
public class Vacation {

    private int index;
    private String id;
    private String vacation_type;
    private String vacation_st_date;
    private String vacation_end_date;
    private double use_day;
    private String comment;

    // 반차/반반차 여부
    private String startTimeCheck;
    private String endTimeCheck;

    public Vacation(){

    }

    public Vacation(int index, String id, String vacation_type, String vacation_st_date, String vacation_end_date, double use_day, String comment, String startTimeCheck, String endTimeCheck){
        this.index = index;
        this.id = id;
        this.vacation_type = vacation_type;
        this.vacation_st_date = vacation_st_date;
        this.vacation_end_date = vacation_end_date;
        this.use_day = use_day;
        this.comment = comment;
        this.startTimeCheck = startTimeCheck;
        this.endTimeCheck = endTimeCheck;
    }

}
