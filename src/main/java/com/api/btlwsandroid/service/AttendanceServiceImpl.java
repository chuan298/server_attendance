package com.api.btlwsandroid.service;

import com.api.btlwsandroid.dao.entity.Attendance;
import com.api.btlwsandroid.dao.entity.ScheduleCourse;
import com.api.btlwsandroid.dao.entity.StudentCourse;
import com.api.btlwsandroid.dao.repo.AttendanceRepository;
import com.api.btlwsandroid.dao.repo.StudentCourseRepository;
import com.api.btlwsandroid.dto.request.RecognizeRequest;
import com.api.btlwsandroid.dto.response.AttendanceResponse;
import com.api.btlwsandroid.dto.response.RecognizeResponse;
import com.api.btlwsandroid.security.JwtService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private JwtService jwtService;

    private static final String URL_RECOG = "https://5a793888a1a4.ngrok.io";

    private static final int ALLOWED_TIME = 15;
    private static final Double LATITUDE = 20.9807015;
    private static final Double LONGITUDE = 105.7870774;
    private static final Double RANGE = 0.0005;
    @Override
    public AttendanceResponse checkAttendance(String imgbase64, String studentId, String username, Integer subject_group_id, String time, Double latitude, Double longitude) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        RecognizeRequest testRequest = new RecognizeRequest(imgbase64);

        HttpEntity<Object> requestBody = new HttpEntity<>(testRequest, headers);

        //
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String timeNow = dtf.format(now);

        int hour_time = Integer.parseInt(time.substring(0,2));
        int minute_time = Integer.parseInt(time.substring(3,5));
        int hour_now = Integer.parseInt(timeNow.substring(0,2));
        int minute_now = Integer.parseInt(timeNow.substring(3,5));
        System.out.println("thoi gian chenh lech: " + ((hour_now - hour_time)*60 + minute_now - minute_time));
        System.out.println("vi tri: " + latitude + " " + longitude);
        //
        String message = "";
        if((latitude > LATITUDE - RANGE && longitude > LONGITUDE - RANGE) && (latitude < LATITUDE + RANGE && longitude < LONGITUDE + RANGE)){
            if(((hour_now - hour_time)*60 + minute_now - minute_time) > 0 && ((hour_now - hour_time)*60 + minute_now - minute_time) < ALLOWED_TIME){
//                System.out.println("thoi gian chenh lech: " + ((hour_now - hour_time)*60 + minute_now - minute_time));
                ResponseEntity<String> result = restTemplate.postForEntity(URL_RECOG + "/reco", requestBody, String.class);

                //TypeReference<List<RecognizeResponse>> typeReference = new TypeReference<List<RecognizeResponse>>() {};

                //ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                Gson gson = new Gson();
                //List<RecognizeResponse> response = mapper.readValue(result.getBody(), typeReference);
                Type typeReference = new TypeToken<List<RecognizeResponse>>() {}.getType();
                List<RecognizeResponse> response = gson.fromJson(result.getBody(), typeReference);
                System.out.println(response);
                assert response != null;

                for(RecognizeResponse recognizeResponse : response){
                    System.out.println(recognizeResponse.getMaSv() + "    " + username);
                    if(recognizeResponse.getMaSv().equals(username)){
                        System.out.println("thoa man");
                        try{
                            System.out.println("username, subject_group_id: " + username + " " + subject_group_id);
                            StudentCourse studentCourse = studentCourseRepository.findByStudentUsernameAndSubjectGroupId(username, subject_group_id).get();
                            Attendance attendance = new Attendance();
                            attendance.setStudentCourse(studentCourse);
                            attendance.setTime(timeNow);
                            attendance.setImage(imgbase64);
                            ScheduleCourse scheduleCourse = scheduleService.getCurrentScheduleOfStudent(studentId);
                            attendance.setSchedule(scheduleCourse.getSchedule());
                            attendance.setType_schedule(scheduleCourse.getTypeSchedule());
                            attendanceRepository.save(attendance);

                            return new AttendanceResponse(recognizeResponse, true, "Điểm danh thành công");
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        return new AttendanceResponse(null, false, "Điểm danh thành công nhưng chưa lưu dc vào database");

                    }
                }
            }
            //
            else return new AttendanceResponse(null, false, "Thời gian điểm danh > 15p");
        }
        else {
            return new AttendanceResponse(null, false, "Bạn hiện không ở trường học =)))");
        }

        return new AttendanceResponse(null, false, "Xảy ra lỗi");
    }

    @Override
    public List<Attendance> getAttendancesOfStudent(Integer studentId) {
        List<Attendance> attendances = null;
        try{
            attendances = attendanceRepository.findAllByStudentId(studentId+"");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return attendances;
    }

    @Override
    public List<Attendance> getAttendancesOfSubjectOfStudent(Integer subjectId, Integer studentId) {
        List<Attendance> attendances = null;
        try{
            attendances = attendanceRepository.findAllByStudentIdAndSubjectId(subjectId + "", studentId + "");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return attendances;
    }

    //    public static void main(String[] args) {
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
//        LocalDateTime now = LocalDateTime.now();
//        System.out.println(dtf.format(now));
//    }
}
