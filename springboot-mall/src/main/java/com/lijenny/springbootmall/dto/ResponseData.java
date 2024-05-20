package com.lijenny.springbootmall.dto;

public class ResponseData extends Response{

    public Object Data;
    public ResponseData(Object Data){
        this.Data=Data;
    }
    public ResponseData(int code,Object data) {
        this.Code = code;
        this.Data  = data;
    }

    public ResponseData(int code,String msg) {
        this.Code = code;
        this.Message = msg;
    }
}
