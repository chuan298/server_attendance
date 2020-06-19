package com.api.btlwsandroid.service;

import com.api.btlwsandroid.dao.entity.Attendance;
import com.api.btlwsandroid.dao.repo.AttendanceRepository;
import com.api.btlwsandroid.dto.request.LoginRequest;
import com.api.btlwsandroid.dto.response.AnResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public HttpResponse checkAttendance(String imgbase64) throws IOException, JSONException {
//        CloseableHttpClient client = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost("http://localhost:8081/api/test");
//        JSONObject jsonObject = null;
//        jsonObject = new JSONObject().put("imgbase64", imgbase64);
//        StringEntity entity = new StringEntity(jsonObject.toString());
//        httpPost.setEntity(entity);
//        httpPost.setHeader("Accept", "application/json");
//        httpPost.setHeader("Content-type", "application/json");
//        HttpResponse httpResponse = client.execute(httpPost);
//
//        return httpResponse;
        RestTemplate restTemplate = new RestTemplate();

        // Dữ liệu đính kèm theo yêu cầu.
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        HttpEntity<ObjectNode> requestBody = new HttpEntity<>(objectNode.put("imgbase64", imgbase64));

        // Gửi yêu cầu với phương thức POST.
        ResponseEntity<ObjectNode> result = restTemplate.postForEntity("http://localhost:8081/api/test", requestBody, ObjectNode.class);
        return null;

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


//    public static void main(String[] args) throws JsonProcessingException {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//
//        TestRequest testRequest = new TestRequest("imgbase64 test");
//
//        HttpEntity<Object> requestBody = new HttpEntity<>(testRequest, headers);
//        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:8081/api/test", requestBody, String.class);
//
//        TypeReference<TestRequest> typeReference = new TypeReference<TestRequest>() {};
//
//        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        TestRequest response = mapper.readValue(result.getBody(), typeReference);
//        System.out.println(response.getImgbase64());
//
//    }
}
