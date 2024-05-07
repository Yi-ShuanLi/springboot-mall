package com.lijenny.springbootmall.dao;

import com.lijenny.springbootmall.constant.ProductCategory;
import com.lijenny.springbootmall.dto.ProductRequest;
import com.lijenny.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getProducts(ProductCategory category,String search);
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId,ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
