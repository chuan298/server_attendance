package com.api.btlwsandroid.service;

import com.api.btlwsandroid.dao.entity.Attendance;
import com.api.btlwsandroid.dao.repo.AttendanceRepository;
import com.api.btlwsandroid.dto.request.RecognizeRequest;
import com.api.btlwsandroid.dto.response.RecognizeResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public Boolean checkAttendance(String imgbase64) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        RecognizeRequest testRequest = new RecognizeRequest("imgbase64 test");

        HttpEntity<Object> requestBody = new HttpEntity<>(testRequest, headers);
        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:8081/api/test", requestBody, String.class);

        TypeReference<RecognizeResponse> typeReference = new TypeReference<RecognizeResponse>() {};

        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Gson gson = new Gson();
        RecognizeResponse response = mapper.readValue(result.getBody(), typeReference);
        RecognizeResponse as = gson.fromJson(result.getBody(), RecognizeResponse.class);
        System.out.println(as.getMaSv());

        return true;
    }

    @Override
    public List<Attendance> getAttendancesOfStudent(Integer studentId) {
        List<Attendance> attendances = null;
        try{
            attendances = attendanceRepository.findAllByStudentId(studentId);
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
            attendances = attendanceRepository.findAllByStudentIdAndSubjectId(subjectId, studentId);
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


    public static void main(String[] args) throws IOException {
        AttendanceServiceImpl attendance = new AttendanceServiceImpl();

        attendance.checkAttendance("aaaaaa");

    }
}
