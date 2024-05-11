package com.lijenny.springbootmall.dto;

import jakarta.validation.constraints.NotNull;
//對應到前端所傳過來的object 裡面的buyItemList的 object
public class BuyItem {

    @NotNull
    private Integer productId;

    @NotNull
    private Integer quantity;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
