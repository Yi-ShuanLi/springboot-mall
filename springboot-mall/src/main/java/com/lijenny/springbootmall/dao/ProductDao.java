package com.lijenny.springbootmall.dao;

import com.lijenny.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);
}