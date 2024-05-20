package com.lijenny.springbootmall.controller;

import com.lijenny.springbootmall.dto.ResponseData;
import com.lijenny.springbootmall.dto.UserLoginRequest;
import com.lijenny.springbootmall.dto.UserRegisterRequest;
import com.lijenny.springbootmall.model.User;
import com.lijenny.springbootmall.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users/register")
    public ResponseData register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
//        ResponseData responseData = userService.register(userRegisterRequest);
//        if(userId == -1){
//            return  new ResponseData(400,"此email已註冊!");
//        }

        return  userService.register(userRegisterRequest);
    }

    @PostMapping("/users/login")
    public ResponseData login(@RequestBody @Valid UserLoginRequest userLoginRequest){

       return userService.login(userLoginRequest);
    }
}
