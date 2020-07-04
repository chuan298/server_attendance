package com.api.btlwsandroid.service;

import com.api.btlwsandroid.dao.entity.DaySchedule;
import com.api.btlwsandroid.dao.entity.Schedule;
import com.api.btlwsandroid.dao.entity.ScheduleCourse;
import javafx.util.Pair;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ScheduleService {
    Map<Integer, List<DaySchedule>> getSchedulesOfStudent(Integer studentId);
    ScheduleCourse getCurrentScheduleOfStudent(String studentId);
}
