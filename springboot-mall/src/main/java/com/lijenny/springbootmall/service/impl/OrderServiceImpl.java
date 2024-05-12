package com.lijenny.springbootmall.service.impl;

import com.lijenny.springbootmall.dao.OrderDao;
import com.lijenny.springbootmall.dao.ProductDao;
import com.lijenny.springbootmall.dao.UserDao;
import com.lijenny.springbootmall.dto.BuyItem;
import com.lijenny.springbootmall.dto.CreateOrderRequest;
import com.lijenny.springbootmall.model.Order;
import com.lijenny.springbootmall.model.OrderItem;
import com.lijenny.springbootmall.model.Product;
import com.lijenny.springbootmall.model.User;
import com.lijenny.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    private final static Logger log= LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderDao orderDao;


    @Override
    public Order getOrderById(Integer orderId) {
        Order order=orderDao.getOrderById(orderId);
        List <OrderItem> orderItemList=orderDao.getOrderItemsByOrderId(orderId);
        order.setOrderItemList(orderItemList);
        return order;
    }

    @Override
    @Transactional
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        //在創建訂單之前先檢查是否為有效帳號
        User user=userDao.getUserById(userId);
        if(user==null){
            log.warn("該 userId {} 不存在",userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount=0;
        List<OrderItem> orderItemList=new ArrayList<>();
        for(BuyItem buyItem :createOrderRequest.getBuyItemList()){
            Product product=productDao.getProductById(buyItem.getProductId());
            //先檢查商品是否存在、庫存是否足夠
            if(product==null){
                log.warn("商品 {} 不存在",buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }else if (product.getStock() <buyItem.getQuantity()){
                log.warn("商品 {} 庫存數量不足 ，無法購買。剩餘庫存 {} ，欲購買數量 {}" ,
                        buyItem.getProductId() ,product.getStock() , buyItem.getQuantity() );
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            //扣除商品庫存
            productDao.updateStock(product.getProductId(),product.getStock()-buyItem.getQuantity());

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
