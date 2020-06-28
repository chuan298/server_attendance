package com.api.btlwsandroid.service;

import com.api.btlwsandroid.dao.entity.Attendance;
import org.apache.http.HttpResponse;
import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface AttendanceService {
    Boolean checkAttendance(String imgbase64) throws IOException, JSONException;

    List<Attendance> getAttendancesOfStudent(Integer studentId);

    List<Attendance> getAttendancesOfSubjectOfStudent(Integer studentId, Integer subjectId);
}
