package com.api.btlwsandroid.service;

import com.api.btlwsandroid.dao.entity.Student;
import com.api.btlwsandroid.dao.repo.StudentRepository;
import com.api.btlwsandroid.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student findById(Integer id) {
        Student student = null;
        try{
            student = studentRepository.findById(id).get();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public boolean checkLogin(String tk, String mk) {

        Optional<Student> student = studentRepository.findByUsernameAndPassword(tk, mk);
        return student.isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Kiểm tra xem user có tồn tại trong database không?
        Optional<Student> user = studentRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user.get());
    }
}
