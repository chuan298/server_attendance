package com.api.btlwsandroid.controller;


import com.api.btlwsandroid.dao.entity.Attendance;
import com.api.btlwsandroid.dao.entity.Student;
import com.api.btlwsandroid.dto.request.AttendanceRequest;
import com.api.btlwsandroid.dto.request.LoginRequest;
import com.api.btlwsandroid.service.AttendanceService;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = {"Access-Control-Allow-Origin", "Access-Control-Expose-Headers"} )
@RestController
@RequestMapping("/api")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    private static final Gson gson = new Gson();

    @PostMapping("/attendance")
    public ResponseEntity<String> Attendance(@RequestBody AttendanceRequest attendanceRequest, UriComponentsBuilder builder){
        try {
            Boolean response = attendanceService.checkAttendance(attendanceRequest.getImgbase64());
            return new ResponseEntity<>(gson.toJson(response), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.OK);

    }

    @GetMapping("/get-attendance")
    public ResponseEntity<String> getAttendanceOfStudent(@RequestParam(name = "student_id") String studentId){
        List<Attendance> attendances = attendanceService.getAttendancesOfStudent(Integer.parseInt(studentId));
        if(attendances != null)
            return new ResponseEntity<>(gson.toJson(attendances), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/get-attendance-subject")
    public ResponseEntity<String> getAttendanceOfSubjectOfStudent(@RequestParam(name = "student_id") String studentId, @RequestParam(name = "subject_id") String subjectId){
        List<Attendance> attendances = attendanceService.getAttendancesOfSubjectOfStudent(Integer.parseInt(subjectId), Integer.parseInt(studentId));
        if(attendances != null)
            return new ResponseEntity<>(gson.toJson(attendances), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }


}
