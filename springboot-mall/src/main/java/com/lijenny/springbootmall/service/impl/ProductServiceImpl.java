package com.lijenny.springbootmall.service.impl;

import com.lijenny.springbootmall.dao.ProductDao;
import com.lijenny.springbootmall.model.Product;
import com.lijenny.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
}
