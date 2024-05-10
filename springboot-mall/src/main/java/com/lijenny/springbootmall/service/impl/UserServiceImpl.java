package com.lijenny.springbootmall.service.impl;

import com.lijenny.springbootmall.dao.UserDao;
import com.lijenny.springbootmall.dto.UserRegisterRequest;
import com.lijenny.springbootmall.model.User;
import com.lijenny.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }


}
