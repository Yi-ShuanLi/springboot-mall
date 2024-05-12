package com.lijenny.springbootmall.service;

import com.lijenny.springbootmall.dto.CreateOrderRequest;
import com.lijenny.springbootmall.dto.OrderQueryParams;
import com.lijenny.springbootmall.model.Order;
import com.lijenny.springbootmall.service.impl.OrderServiceImpl;

import java.util.List;

public interface OrderService  {
    Integer countOrders (OrderQueryParams orderQueryParams);
    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Order getOrderById(Integer orderId);
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

}
