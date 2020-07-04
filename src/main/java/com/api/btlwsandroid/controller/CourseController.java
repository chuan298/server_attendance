package com.api.btlwsandroid.controller;

import com.api.btlwsandroid.dao.entity.*;
import com.api.btlwsandroid.security.JwtService;
import com.api.btlwsandroid.service.ScheduleService;
import com.api.btlwsandroid.service.StudentCourseService;
import com.api.btlwsandroid.service.SubjectGroupService;
import com.google.gson.Gson;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private JwtService jwtService;

    @GetMapping("/get-timetable")
    public ResponseEntity<String> getTimeTable(@Nullable @RequestParam("student_id") String studentId, @RequestHeader HttpHeaders headers){
        try{
            String token = jwtService.getJwtFromHeader(headers);
            Integer id = jwtService.getIdFromToken(token);
            String username = jwtService.getUsernameFromToken(token);
            Map<Integer, List<DaySchedule>> schedules = scheduleService.getSchedulesOfStudent(Integer.parseInt(id + ""));
            if(schedules != null){
                return new ResponseEntity<>(gson.toJson(schedules), HttpStatus.OK);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    @GetMapping("get-current-schedule")
    public ResponseEntity<String> getCurrentSchedule(@Nullable @RequestParam("student_id") String studentId, @RequestHeader HttpHeaders headers){
        try {
            String token = jwtService.getJwtFromHeader(headers);
            Integer id = jwtService.getIdFromToken(token);
            String username = jwtService.getUsernameFromToken(token);
            ScheduleCourse scheduleBooleanPair = scheduleService.getCurrentScheduleOfStudent(id + "");

            if(scheduleBooleanPair != null){
                return new ResponseEntity<>(gson.toJson(scheduleBooleanPair), HttpStatus.OK);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
