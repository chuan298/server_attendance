package com.api.btlwsandroid.controller;

import com.api.btlwsandroid.dao.entity.PracticeGroup;
import com.api.btlwsandroid.dao.entity.Schedule;
import com.api.btlwsandroid.dao.entity.SubjectGroup;
import com.api.btlwsandroid.service.ScheduleService;
import com.api.btlwsandroid.service.StudentCourseService;
import com.api.btlwsandroid.service.SubjectGroupService;
import com.google.gson.Gson;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = {"Access-Control-Allow-Origin", "Access-Control-Expose-Headers"} )
@RestController
@RequestMapping("/api")
public class CourseController {
    private static final Gson gson = new Gson();
    @Autowired
    private SubjectGroupService subjectGroupService;
    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/get-timetable")
    public ResponseEntity<String> getTimeTable(@RequestParam("student_id") String studentId){
        List<Schedule> schedules = scheduleService.getSchedulesOfStudent(Integer.parseInt(studentId));

        if(schedules != null){
            return new ResponseEntity<>(gson.toJson(schedules), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
    @GetMapping("get-current-schedule")
    public ResponseEntity<String> getCurrentSchedule(@RequestParam("student_id") String studentId){
        Pair<Schedule, Boolean> scheduleBooleanPair = scheduleService.getCurrentScheduleOfStudent(studentId);

        if(scheduleBooleanPair != null){
            return new ResponseEntity<>(gson.toJson(scheduleBooleanPair), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
