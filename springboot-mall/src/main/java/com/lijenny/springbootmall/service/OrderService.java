package com.lijenny.springbootmall.service;

import com.lijenny.springbootmall.dto.CreateOrderRequest;
import com.lijenny.springbootmall.service.impl.OrderServiceImpl;

public interface OrderService  {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

}
