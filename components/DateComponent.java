package com.executions.demo.components;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class DateComponent {
    @JsonProperty("time")
    public String time;
    
    @JsonProperty("milliseconds_since_epoch")
    public Long epoch;

    @JsonProperty("date")
    public String date;

    public DateComponent(Date date){
        this.time = String.format("%tr", date);;
        this.epoch = date.getTime();
        this.date = String.format("%tm-%<td-%<tY", date);;
    }

}
