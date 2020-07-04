package com.api.btlwsandroid.dao.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class DaySchedule implements Serializable {
    private static final long serialVersionUID =  7843353554L;

    private Integer day_number;
    private List<ScheduleCourse> scheduleCourseList;



}
