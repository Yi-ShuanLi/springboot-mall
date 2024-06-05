package com.lijenny.springbootmall.model;

import java.util.Date;

public class AllBuyerByProductId {
    private Integer orederItemId;
    private Integer orderId;
    private Integer quantity;
    private Integer amount;
    private Integer userId;
    private String email;
    private Date createdDate;


    public Integer getOrederItemId() {
        return orederItemId;
    }

    public void setOrederItemId(Integer orederItemId) {
        this.orederItemId = orederItemId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
