package com.lijenny.springbootmall.service.impl;


import com.lijenny.springbootmall.dao.ProductDao;
import com.lijenny.springbootmall.dto.BuyItem;
import com.lijenny.springbootmall.dto.ProductRequest;
import com.lijenny.springbootmall.model.Product;
import com.lijenny.springbootmall.model.ProductItem;
import com.lijenny.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Integer countProduct(BuyItem.ProductQueryParams productQueryParams) {
        return productDao.countProduct(productQueryParams);
    }

    @Override
    public List<Product> getProducts(BuyItem.ProductQueryParams productQueryParams) {
        return productDao.getProducts(productQueryParams);
    }

    @Override
    public List<ProductItem> getProductsContainsSalesNumber(BuyItem.ProductQueryParams productQueryParams) {
        return productDao.getProductsContainsSalesNumber(productQueryParams);
    }

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
       productDao.updateProduct(productId,productRequest);
    }

    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }
}
