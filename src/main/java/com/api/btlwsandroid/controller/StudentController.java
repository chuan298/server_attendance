package com.api.btlwsandroid.controller;

import com.api.btlwsandroid.dao.entity.Student;
import com.api.btlwsandroid.dto.request.LoginRequest;
import com.api.btlwsandroid.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import com.google.gson.Gson;

@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = {"Access-Control-Allow-Origin", "Access-Control-Expose-Headers"} )

@RestController
@RequestMapping("/api")
public class StudentController{

    private static final Gson gson = new Gson();
    @Autowired
    private StudentService studentService;

    @PostMapping("/login")
    public ResponseEntity<String> Login(@RequestBody LoginRequest request){
        System.out.println(studentService.checkLogin(request.getUsername(), request.getPassword()));
        if(studentService.checkLogin(request.getUsername(), request.getPassword())){

            return ResponseEntity.ok(gson.toJson(null));
        }
        else {
            return new ResponseEntity<>(gson.toJson("thong tin khong dung"), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
    }
    @GetMapping("/get")
    public ResponseEntity<String> getUser(@RequestParam("id") String id){
        Student student = studentService.findById(Integer.parseInt(id));
        if(student != null)
            return new ResponseEntity<>(gson.toJson(student), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

    }
}
