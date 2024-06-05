package com.lijenny.springbootmall.controller;

import com.lijenny.springbootmall.dto.CreateOrderRequest;
import com.lijenny.springbootmall.dto.OrderQueryParams;
import com.lijenny.springbootmall.dto.ProductQueryParams;
import com.lijenny.springbootmall.model.AllBuyerByProductId;
import com.lijenny.springbootmall.model.Order;
import com.lijenny.springbootmall.model.OrderInfoByProductId;
import com.lijenny.springbootmall.service.OrderService;
import com.lijenny.springbootmall.util.Page;
import com.lijenny.springbootmall.util.Page2;
import com.lijenny.springbootmall.util.Page3;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Validated
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity <Page3<Order>> getOrders(
            @PathVariable Integer userId,
            @RequestParam (defaultValue = "10") @Max(1000) @Min(0) Integer limit,
            @RequestParam (defaultValue ="0") @Min(0) Integer offset){
        OrderQueryParams orderQueryParams=new OrderQueryParams();
        orderQueryParams.setUserId(userId);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);

        //取得orderList
        List<Order> orderList =orderService.getOrders(orderQueryParams);

        //取得order總數
        Integer count=orderService.countOrders(orderQueryParams);
        Integer totalShoppingAmount=orderService.getTotalShoppingAmount(orderQueryParams);

        //分頁
        Page3 <Order> page=new Page3 <> ();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setTotalShoppingAmount(totalShoppingAmount);
        page.setResults(orderList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/users/{productId}/ordersAndBuyers")
    public ResponseEntity <Page2> getOrdersAndBuyerByProductId(
            @PathVariable Integer productId,
            @RequestParam (defaultValue = "10") @Max(1000) @Min(0) Integer limit,
            @RequestParam (defaultValue ="0") @Min(0) Integer offset){
        ProductQueryParams productQueryParams=new ProductQueryParams();
        productQueryParams.setProductId(productId);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        //
        OrderInfoByProductId orderInfoByProductId =orderService.getOrdersInfoByProductId(productQueryParams);

        Integer count=orderService.countOrdersByProductId(productQueryParams);

        //分頁
        Page2 page=new Page2  ();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setResults(orderInfoByProductId);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity <?> createOrder(@PathVariable Integer userId,
                                          @RequestBody @Valid CreateOrderRequest createOrderRequest){
        //因為要接住前端傳來的object=>createOrderRequest裡面有notEmpty註解，所以要加上 @Valid 註解
        Integer orderId = orderService.createOrder(userId,createOrderRequest);

        Order order =orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);

    }
}
