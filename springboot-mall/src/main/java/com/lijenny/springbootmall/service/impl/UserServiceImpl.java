package com.lijenny.springbootmall.service.impl;

import com.lijenny.springbootmall.dao.UserDao;
import com.lijenny.springbootmall.dto.ResponseData;
import com.lijenny.springbootmall.dto.UserLoginRequest;
import com.lijenny.springbootmall.dto.UserRegisterRequest;
import com.lijenny.springbootmall.model.User;
import com.lijenny.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log= LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public ResponseData register(UserRegisterRequest userRegisterRequest) {
        //檢查註冊的email
        User user=userDao.getUserByEmail(userRegisterRequest.getEmail());
        if(user != null){
            log.warn("該email {} 已經被註冊",userRegisterRequest.getEmail());
            //String message=userRegisterRequest.getEmail()+"已經被註冊!";
            return new ResponseData(400,userRegisterRequest.getEmail()+"已經被註冊!");
        }
        //使用MD5生成密碼的雜湊值
        String hashedPassword= DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);
        //創建帳號=>先回傳新增的userId
        Integer userId=userDao.createUser(userRegisterRequest);
        //創建帳號=>回傳user的資料
        User newUser=userDao.getUserById(userId);
        //給前端做溝通
        return  new ResponseData(newUser);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public ResponseData login(UserLoginRequest userLoginRequest) {
        User user=userDao.getUserByEmail(userLoginRequest.getEmail());
        //檢查user是否存在
        if(user==null){
            log.warn("該email {} 尚未註冊",userLoginRequest.getEmail());
            String message=userLoginRequest.getEmail()+"尚未註冊!";
            return  new ResponseData(404,message);
        }

        //使用MD5使用者輸入的密碼，生成密碼的雜湊值
        String hashedPassword=DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());


        //比較密碼是否正確
        if(user.getPassword().equals(hashedPassword)){
            return new ResponseData(user);
        }else{
            log.warn("email {} 的密碼不正確",userLoginRequest.getEmail());
            return  new ResponseData(400,"密碼錯誤!!");
        }
    }


}
