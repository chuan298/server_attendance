package com.api.btlwsandroid.service;

import com.api.btlwsandroid.dao.entity.PracticeGroup;
import com.api.btlwsandroid.dao.entity.Schedule;
import com.api.btlwsandroid.dao.entity.StudentCourse;
import com.api.btlwsandroid.dao.entity.SubjectGroup;
import com.api.btlwsandroid.dao.repo.PracticeGroupRepository;
import com.api.btlwsandroid.dao.repo.ScheduleRepository;
import com.api.btlwsandroid.dao.repo.StudentCourseRepository;
import com.api.btlwsandroid.dao.repo.SubjectGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    private SubjectGroupRepository subjectGroupRepository;

    @Autowired
    private PracticeGroupRepository practiceGroupRepository;

    @Override
    public List<Schedule> getSchedulesOfStudent(Integer studentId) {
        List<Schedule> schedules = null;
        List<StudentCourse> studentCourses = null;
        List<PracticeGroup> practiceGroups = new ArrayList<>();
        try{
            schedules = scheduleRepository.fillAllSubjectGroupScheduleOfStudent(studentId+"");

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return schedules;
    }
}
