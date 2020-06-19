package com.api.btlwsandroid.service;

import com.api.btlwsandroid.dao.entity.PracticeGroup;
import com.api.btlwsandroid.dao.entity.StudentCourse;
import com.api.btlwsandroid.dao.entity.SubjectGroup;

import java.util.List;

public interface StudentCourseService {
    List<SubjectGroup> getSubjectGroupOfStudent(Integer idSudent);
    List<PracticeGroup> getPracticeGroupOfSubjectGroup(Integer idSubjectGroup);
}
