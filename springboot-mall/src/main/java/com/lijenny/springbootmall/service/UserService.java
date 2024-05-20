package com.lijenny.springbootmall.service;

import com.lijenny.springbootmall.dto.ResponseData;
import com.lijenny.springbootmall.dto.UserLoginRequest;
import com.lijenny.springbootmall.dto.UserRegisterRequest;
import com.lijenny.springbootmall.model.User;

public interface UserService {
    ResponseData register(UserRegisterRequest userRegisterRequest);
    User getUserById(Integer userId);
    ResponseData login(UserLoginRequest userLoginRequest);
}
