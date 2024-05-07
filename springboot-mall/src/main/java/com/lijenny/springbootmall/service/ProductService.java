package com.lijenny.springbootmall.service;


import com.lijenny.springbootmall.dto.ProductRequest;
import com.lijenny.springbootmall.model.Product;

public interface ProductService {
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
}
