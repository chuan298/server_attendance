package com.api.btlwsandroid.service;

import com.api.btlwsandroid.dao.entity.Subject;
import com.api.btlwsandroid.dao.repo.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public List<Subject> findAll() {
        return (List<Subject>) subjectRepository.findAll();
    }
}
