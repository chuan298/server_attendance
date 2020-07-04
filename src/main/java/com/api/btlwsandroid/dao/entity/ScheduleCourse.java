package com.api.btlwsandroid.dao.entity;


import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
@AllArgsConstructor
public class ScheduleCourse implements Serializable {
    private static final long serialVersionUID =  13243353554L;
    private Schedule schedule;
    private Integer typeSchedule;
    private Boolean isAttendanced;
}
