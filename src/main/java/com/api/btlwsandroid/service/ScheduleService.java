package com.api.btlwsandroid.service;

import com.api.btlwsandroid.dao.entity.Schedule;
import javafx.util.Pair;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ScheduleService {
    List<Schedule> getSchedulesOfStudent(Integer studentId);
    Pair<Schedule, Boolean> getCurrentScheduleOfStudent(String studentId);
}
