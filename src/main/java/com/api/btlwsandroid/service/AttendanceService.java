package com.api.btlwsandroid.service;

import com.api.btlwsandroid.dao.entity.Attendance;
import com.api.btlwsandroid.dto.response.AttendanceResponse;
import com.api.btlwsandroid.dto.response.RecognizeResponse;
import javafx.util.Pair;
import org.apache.http.HttpResponse;
import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface AttendanceService {
    AttendanceResponse checkAttendance(String imgbase64, String studentId, String username, Integer subject_group_id, String time, Double latitude, Double longitude);

    List<Attendance> getAttendancesOfStudent(Integer studentId);

    List<Attendance> getAttendancesOfSubjectOfStudent(Integer studentId, Integer subjectId);
}
