package com.api.btlwsandroid.service;

import com.api.btlwsandroid.dao.entity.Subject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface SubjectService {
    List<Subject> findAll();
}