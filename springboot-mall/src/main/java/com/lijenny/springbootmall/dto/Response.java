package com.lijenny.springbootmall.dto;

public class Response {

    public String Message = "執行成功!";
    public int Code = 200;
    public boolean IsSuccess = true;
    public Response(String message, int code, boolean success) {
        this.Message = message;
        this.Code = code;
        IsSuccess = success;
    }

    public Response(String message, int code) {
        this.Message = message;
        this.Code = code;
    }

    public Response(){

    }
}
