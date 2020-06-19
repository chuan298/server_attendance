package com.api.btlwsandroid.service;

import com.api.btlwsandroid.dao.entity.Schedule;

import java.util.List;

public interface ScheduleService {
    List<Schedule> getSchedulesOfStudent(Integer studentId);
}
