package com.lijenny.springbootmall.dto;

//前端所傳過來的請求參數，把它蒐集在這個class
public class OrderQueryParams {
    private Integer userId;
    private Integer limit;
    private Integer offset;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
