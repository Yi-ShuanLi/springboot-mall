package com.lijenny.springbootmall.service;

import com.lijenny.springbootmall.dto.CreateOrderRequest;
import com.lijenny.springbootmall.model.Order;
import com.lijenny.springbootmall.service.impl.OrderServiceImpl;

public interface OrderService  {
    Order getOrderById(Integer orderId);
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

}
