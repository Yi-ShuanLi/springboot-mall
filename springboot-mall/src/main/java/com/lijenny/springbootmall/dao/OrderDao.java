package com.lijenny.springbootmall.dao;

import com.lijenny.springbootmall.dto.OrderQueryParams;
import com.lijenny.springbootmall.model.Order;
import com.lijenny.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {
    Integer countOrders(OrderQueryParams orderQueryParams);

    List <Order> getOrders(OrderQueryParams orderQueryParams);

    Order getOrderById(Integer orderId);

    List <OrderItem> getOrderItemsByOrderId(Integer orderId);

    Integer createOrder(Integer userId ,Integer totalAmount);

    void createOrderItems(Integer orderId, List <OrderItem> orderItemList);
}
