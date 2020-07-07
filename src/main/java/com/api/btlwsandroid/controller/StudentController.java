package com.api.btlwsandroid.controller;

import com.api.btlwsandroid.dao.entity.Student;
import com.api.btlwsandroid.dto.request.ChangeInfoRequest;
import com.api.btlwsandroid.dto.request.LoginRequest;
import com.api.btlwsandroid.dto.response.LoginResponse;
import com.api.btlwsandroid.security.JwtService;
import com.api.btlwsandroid.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;

import javax.annotation.Nullable;


@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = {"Access-Control-Allow-Origin", "Access-Control-Expose-Headers"} )

@RestController
@RequestMapping("/api")
public class StudentController{

    private static final Gson gson = new Gson();
    @Autowired
    private StudentService studentService;
    @Autowired
    private JwtService jwtService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> Login(@RequestBody LoginRequest request){
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
    public ResponseEntity<String> getUser(@Nullable @RequestParam("student_id") String id, @RequestHeader HttpHeaders headers){
        try{
            String token = jwtService.getJwtFromHeader(headers);
            Integer idUser = jwtService.getIdFromToken(token);
            String username = jwtService.getUsernameFromToken(token);
            Student student = studentService.findById(Integer.parseInt(idUser + ""));
            student.setPassword(null);
            return new ResponseEntity<>(gson.toJson(student), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.OK);


    }
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangeInfoRequest request, @RequestHeader HttpHeaders headers){
        try {
            String token = jwtService.getJwtFromHeader(headers);
            Integer id = jwtService.getIdFromToken(token);
            String username = jwtService.getUsernameFromToken(token);
            request.setStudent_id(id);
            if (studentService.changePassword(request.getStudent_id(), request.getOldPassword(), request.getNewPassword())){
                return new ResponseEntity<>("Đổi mật khẩu thành công", HttpStatus.OK);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Đổi mật khẩu thất bại", HttpStatus.CONFLICT);
    }
    @PutMapping("/change-avatar")
    public ResponseEntity<String> changeAvatar(@RequestBody ChangeInfoRequest request, @RequestHeader HttpHeaders headers){
        try {
            String token = jwtService.getJwtFromHeader(headers);
            Integer id = jwtService.getIdFromToken(token);
            String username = jwtService.getUsernameFromToken(token);
            request.setStudent_id(id);
            if (studentService.changeAvatar(request.getStudent_id(), request.getImgbase64())){
                return new ResponseEntity<>("Đổi ảnh đại diện thành công", HttpStatus.OK);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Đổi ảnh đại diện thất bại", HttpStatus.CONFLICT);
    }
}
