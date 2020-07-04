package com.api.btlwsandroid.controller;


import com.api.btlwsandroid.dao.entity.Attendance;
import com.api.btlwsandroid.dao.entity.Student;
import com.api.btlwsandroid.dto.request.AttendanceRequest;
import com.api.btlwsandroid.dto.request.LoginRequest;
import com.api.btlwsandroid.dto.response.AttendanceResponse;
import com.api.btlwsandroid.dto.response.RecognizeResponse;
import com.api.btlwsandroid.security.JwtService;
import com.api.btlwsandroid.service.AttendanceService;
import com.google.gson.Gson;
import javafx.util.Pair;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = {"Access-Control-Allow-Origin", "Access-Control-Expose-Headers"} )
@RestController
@RequestMapping("/api")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private JwtService jwtService;

    private static final Gson gson = new Gson();

    @PostMapping("/attendance")
    public ResponseEntity<String> Attendance(@RequestBody AttendanceRequest attendanceRequest, @RequestHeader HttpHeaders headers){
        try {
            String token = jwtService.getJwtFromHeader(headers);
            Integer id = jwtService.getIdFromToken(token);
            String username = jwtService.getUsernameFromToken(token);
            attendanceRequest.setStudentId(id);
            attendanceRequest.setUsername(username);
            System.out.println("AttendanceRequest: " + attendanceRequest);
            AttendanceResponse responses = attendanceService.checkAttendance(attendanceRequest.getImgbase64(), attendanceRequest.getStudentId()+ "", attendanceRequest.getUsername(), attendanceRequest.getSubject_group_id(), attendanceRequest.getTime(), attendanceRequest.getLatitude(), attendanceRequest.getLongitude());
            return new ResponseEntity<>(gson.toJson(responses), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.OK);

    }

    @GetMapping("/get-attendance")
    public ResponseEntity<String> getAttendanceOfStudent(@Nullable @RequestParam(name = "student_id") String studentId, @RequestHeader HttpHeaders headers){
        try {
            String token = jwtService.getJwtFromHeader(headers);
            Integer id = jwtService.getIdFromToken(token);
            String username = jwtService.getUsernameFromToken(token);
            List<Attendance> attendances = attendanceService.getAttendancesOfStudent(id);
            if(attendances != null)
                return new ResponseEntity<>(gson.toJson(attendances), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/get-attendance-subject")
    public ResponseEntity<String> getAttendanceOfSubjectOfStudent(@Nullable @RequestParam(name = "student_id") String studentId, @RequestParam(name = "subject_id") String subjectId, @RequestHeader HttpHeaders headers){
        try {
            String token = jwtService.getJwtFromHeader(headers);
            Integer id = jwtService.getIdFromToken(token);
            String username = jwtService.getUsernameFromToken(token);
            List<Attendance> attendances = attendanceService.getAttendancesOfSubjectOfStudent(Integer.parseInt(subjectId), id);
            if(attendances != null)
                return new ResponseEntity<>(gson.toJson(attendances), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }


}
