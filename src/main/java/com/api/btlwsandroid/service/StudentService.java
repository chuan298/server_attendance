package com.api.btlwsandroid.service;

import com.api.btlwsandroid.dao.entity.Student;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface StudentService {
    void save(Student student);
    Student findById(Integer id);
    boolean checkLogin(String tk, String mk);
    UserDetails loadUserByUsername(String username);
}
