package com.lijenny.springbootmall.service.impl;

import com.lijenny.springbootmall.dao.OrderDao;
import com.lijenny.springbootmall.dao.ProductDao;
import com.lijenny.springbootmall.dto.BuyItem;
import com.lijenny.springbootmall.dto.CreateOrderRequest;
import com.lijenny.springbootmall.model.OrderItem;
import com.lijenny.springbootmall.model.Product;
import com.lijenny.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderDao orderDao;


    @Override
    @Transactional
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        int totalAmount=0;
        List<OrderItem> orderItemList=new ArrayList<>();
        for(BuyItem buyItem :createOrderRequest.getBuyItemList()){
            Product product=productDao.getProductById(buyItem.getProductId());
            //計算總價錢
            int amount=buyItem.getQuantity()*product.getPrice();
            totalAmount+=amount;

            //轉換 BuyItem to OrderItem
            OrderItem orderItem=new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }
        //創建訂單
        //操作多個資料庫要加上@Transactional 萬一中間噴出了 exception 的話，他會去復原已經執行過的資料庫操作的
        Integer orderId=orderDao.createOrder(userId,totalAmount);
        orderDao.createOrderItems(orderId,orderItemList);
        return orderId;
    }
}
