package com.lijenny.springbootmall.controller;
import com.lijenny.springbootmall.dto.UserRegisterRequest;
import com.lijenny.springbootmall.dto.UserLoginRequest;
import com.lijenny.springbootmall.model.User;
import org.apache.coyote.Request;
import org.springframework.http.MediaType;//這啥?
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lijenny.springbootmall.dao.UserDao;
import com.lijenny.springbootmall.dto.UserRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDao userDao;

    private ObjectMapper objectMapper=new ObjectMapper();
    //註冊新帳號
    @Test
    public void register_success() throws Exception{
        UserRegisterRequest userRegisterRequest=new UserRegisterRequest();
        userRegisterRequest.setEmail("test1@gmail.com");
        userRegisterRequest.setPassword("123");

        String json=objectMapper.writeValueAsString(userRegisterRequest);
        //?
        RequestBuilder requestBuilder= MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.userId",notNullValue()))
                .andExpect(jsonPath("$.email",equalTo("test1@gmail.com")))
                .andExpect(jsonPath("$.createdDate",notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate",notNullValue()));
        //檢查資料庫中密碼不為明碼
        User user =userDao.getUserByEmail(userRegisterRequest.getEmail());
        assertNotEquals(userRegisterRequest.getPassword(),user.getPassword());

    }

    @Test
    public void register_invalidEmailFormat () throws Exception{
        //測試email參數格數不正確
        UserRegisterRequest userRegisterRequest=new UserRegisterRequest();
        userRegisterRequest.setEmail("3gd8e7q34l9");
        userRegisterRequest.setPassword("123");

        String json=objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder=MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    public void register_emailAlreadyExist() throws Exception{
        //先註冊一個帳號
        UserRegisterRequest userRegisterRequest=new UserRegisterRequest();
        userRegisterRequest.setEmail("test2@gmail.com");
        userRegisterRequest.setPassword("123");

        String json=objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder=MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201));
        //再次使用同個 email 註冊
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }
    //登入
    @Test
    public void login_success() throws Exception{
        // 先註冊新帳號
        UserRegisterRequest userRegisterRequest=new UserRegisterRequest();
        userRegisterRequest.setEmail("test3@gmail.com");
        userRegisterRequest.setPassword("123");

        register(userRegisterRequest);

        // 再測試登入功能
        UserLoginRequest userLoginRequest=new UserLoginRequest();
        userLoginRequest.setEmail(userRegisterRequest.getEmail());
        userLoginRequest.setPassword(userRegisterRequest.getPassword());

        String json=objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder=MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.userId",notNullValue()))
                .andExpect(jsonPath("$.email",equalTo(userRegisterRequest.getEmail())))
                .andExpect(jsonPath("$.createdDate",notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate",notNullValue()));
    }


    @Test
    public void login_wrongPassword() throws Exception{
        //先註冊帳號
        UserRegisterRequest userRegisterRequest=new UserRegisterRequest();
        userRegisterRequest.setEmail("test4@gmail.com");
        userRegisterRequest.setPassword("123");

        register(userRegisterRequest);

        //測試密碼輸入錯誤的情況
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail(userRegisterRequest.getEmail());
        userLoginRequest.setPassword("unknown");

        String json=objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder=MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }
    //登入時email格式輸入錯誤的情況
    @Test
    public void login_invalidEmailFormat() throws Exception{
        UserLoginRequest userLoginRequest=new UserLoginRequest();
        userLoginRequest.setEmail("hkbudsr324");
        userLoginRequest.setPassword("123");

        String json=objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder =MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));

    }
    //使用者登入時輸入從來沒有註冊過的emil
    @Test
    public void login_emailNotExist() throws Exception{
        UserLoginRequest userLoginRequest=new UserLoginRequest();
        userLoginRequest.setEmail("unknown@gmail.com");
        userLoginRequest.setPassword("123");

        String json=objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder=MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }
    private void register(UserRegisterRequest userRegisterRequest) throws Exception{
        String json=objectMapper.writeValueAsString(userRegisterRequest);
        RequestBuilder requestBuilder=MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201));
    }















}