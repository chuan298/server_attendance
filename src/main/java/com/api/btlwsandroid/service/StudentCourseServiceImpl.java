package com.api.btlwsandroid.service;

import com.api.btlwsandroid.dao.entity.PracticeGroup;
import com.api.btlwsandroid.dao.entity.StudentCourse;
import com.api.btlwsandroid.dao.entity.SubjectGroup;
import com.api.btlwsandroid.dao.repo.PracticeGroupRepository;
import com.api.btlwsandroid.dao.repo.StudentCourseRepository;
import com.api.btlwsandroid.dao.repo.SubjectGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentCourseServiceImpl implements StudentCourseService {

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    private SubjectGroupRepository subjectGroupRepository;

    @Autowired
    private PracticeGroupRepository practiceGroupRepository;

    @Override
    public List<SubjectGroup> getSubjectGroupOfStudent(Integer idSudent) {
        List<SubjectGroup> subjectGroups = null;

        try{
            subjectGroups = subjectGroupRepository.fillAllSubjectGroupsOfStudent(idSudent+"");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return subjectGroups;
    }



    @Override
    public List<PracticeGroup> getPracticeGroupOfSubjectGroup(Integer idSubjectGroup) {
        List<PracticeGroup> practiceGroups = null;
        try {
            practiceGroups = practiceGroupRepository.findAllBySubjectGroupId(idSubjectGroup);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return practiceGroups;
    }
}
