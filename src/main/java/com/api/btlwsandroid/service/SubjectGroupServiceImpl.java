package com.api.btlwsandroid.service;

import com.api.btlwsandroid.dao.entity.SubjectGroup;
import com.api.btlwsandroid.dao.repo.SubjectGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubjectGroupServiceImpl implements SubjectGroupService {

    @Autowired
    private SubjectGroupRepository subjectGroupRepository;
//    @Override
//    public List<SubjectGroup> getSubjectGroupsOfStudent(Integer idStudent) {
//        List<SubjectGroup> subjectGroups = null;
//        try {
//            subjectGroups = subjectGroupRepository.getSubjectGroupsOfStudent(idStudent+"");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return subjectGroups;
//    }
}
