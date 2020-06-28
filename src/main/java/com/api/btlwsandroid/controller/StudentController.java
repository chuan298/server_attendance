package com.api.btlwsandroid.controller;

import com.api.btlwsandroid.dao.entity.Student;
import com.api.btlwsandroid.dto.request.ChangePassRequest;
import com.api.btlwsandroid.dto.request.LoginRequest;
import com.api.btlwsandroid.dto.response.LoginResponse;
import com.api.btlwsandroid.security.JwtService;
import com.api.btlwsandroid.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;


@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = {"Access-Control-Allow-Origin", "Access-Control-Expose-Headers"} )

@RestController
@RequestMapping("/api")
public class StudentController{

    private static final Gson gson = new Gson();
    @Autowired
    private StudentService studentService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/login-au")
    public ResponseEntity<String> Login(@RequestBody LoginRequest request){
        System.out.println(studentService.checkLogin(request.getUsername(), request.getPassword()));
        if(studentService.checkLogin(request.getUsername(), request.getPassword())){

            return new ResponseEntity<>(gson.toJson("dang nhap thanh cong"), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(gson.toJson("thong tin khong dung"), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> Login_Au(@RequestBody LoginRequest request){
        String result = "";
        HttpStatus httpStatus = null;
        System.out.println(request);
        try {
            if (studentService.checkLogin(request.getUsername(), request.getPassword())) {
                Student student = studentService.getStudentByUsername(request.getUsername());

                result = jwtService.generateTokenLogin(student);
                httpStatus = HttpStatus.OK;
            } else {
                result = "Wrong userId and password";
                httpStatus = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception ex) {
            result = "Server Error";
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(new LoginResponse(result), httpStatus);
    }

    @GetMapping("/get-info")
    public ResponseEntity<String> getUser(@RequestParam("student_id") String id){
        Student student = studentService.findById(Integer.parseInt(id));
        if(student != null)
            return new ResponseEntity<>(gson.toJson(student), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

    }
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePassRequest request){
        try {
            if (studentService.changePassword(request.getStudent_id(), request.getPassword())){
                return new ResponseEntity<>("Đổi mật khẩu thành công", HttpStatus.OK);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Đổi mật khẩu thất bại", HttpStatus.NO_CONTENT);
    }
}
