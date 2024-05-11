package com.lijenny.springbootmall.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;
//對應到前端所傳過來的jsonObject  =>槽狀的object json寫法
public class CreateOrderRequest {
    //NotEmpty 註解是用來加在List 或是Map類型的變數上面，去驗證這個集合不可以為空
    @NotEmpty
    private List <BuyItem> buyItemList;


    public List<BuyItem> getBuyItemList() {
        return buyItemList;
    }

    public void setBuyItemList(List<BuyItem> buyItemList) {
        this.buyItemList = buyItemList;
    }
}
