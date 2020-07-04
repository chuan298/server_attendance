package com.api.btlwsandroid.dao.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class ScheduleWeek implements Serializable {
    private static final long serialVersionUID =  6543353554L;
    private Integer week_number;
    private DaySchedule daySchedule;
}
