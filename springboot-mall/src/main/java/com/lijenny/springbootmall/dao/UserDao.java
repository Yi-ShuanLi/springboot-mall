package com.lijenny.springbootmall.dao;

import com.lijenny.springbootmall.dto.UserRegisterRequest;
import com.lijenny.springbootmall.model.User;

public interface UserDao {
    Integer createUser(UserRegisterRequest userRegisterRequest);
    User getUserById(Integer userId);
}
