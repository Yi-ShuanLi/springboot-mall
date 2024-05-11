package com.lijenny.springbootmall.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class CreateOrderRequest {
    private List <BuyItem> buyItemList;

    @NotEmpty
    public List<BuyItem> getBuyItemList() {
        return buyItemList;
    }

    public void setBuyItemList(List<BuyItem> buyItemList) {
        this.buyItemList = buyItemList;
    }
}
