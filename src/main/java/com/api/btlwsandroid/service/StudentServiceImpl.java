package com.api.btlwsandroid.service;

import com.api.btlwsandroid.dao.entity.Student;
import com.api.btlwsandroid.dao.repo.StudentRepository;
import com.api.btlwsandroid.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
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

        Optional<Student> user = studentRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user.get());
    }

    @Override
    public Boolean changePassword(Integer studentId, String oldPassword, String newPassword) {
        Optional<Student> student = studentRepository.findById(studentId);
        try{
            Student stu = student.get();
            System.out.println(stu);
            if(stu.getPassword().equals(oldPassword)){
                stu.setPassword(newPassword);
                save(stu);
                return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public Student getStudentByUsername(String username) {
        Optional<Student> student = studentRepository.findByUsername(username);
        if (!student.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return student.get();
    }

    @Override
    public Boolean changeAvatar(Integer studentId, String imgbase64) {
        Optional<Student> student = studentRepository.findById(studentId);
        try{
            Student stu = student.get();
            stu.setAvatar(imgbase64);
            save(stu);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

//    public static void main(String[] args) {
//        StudentServiceImpl a = new StudentServiceImpl();
////        System.out.println(a.changePassword(1, "123"));
////        a.changePassword(1, "123");
//        a.checkLogin("dá", "dsa");
//    }
}
