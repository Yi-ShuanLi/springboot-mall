package com.lijenny.springbootmall.controller;

import com.lijenny.springbootmall.dto.CreateOrderRequest;
import com.lijenny.springbootmall.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity <?> createOrder(@PathVariable Integer userId,
                                          @RequestBody @Valid CreateOrderRequest createOrderRequest){
        //因為要接住前端傳來的object=>createOrderRequest裡面有notEmpty註解，所以要加上 @Valid 註解
        Integer orderId = orderService.createOrder(userId,createOrderRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderId);

    }
}
