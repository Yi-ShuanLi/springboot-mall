package com.lijenny.springbootmall.service;



import com.lijenny.springbootmall.dto.BuyItem;
import com.lijenny.springbootmall.dto.ProductRequest;
import com.lijenny.springbootmall.model.Product;
import com.lijenny.springbootmall.model.ProductItem;

import java.util.List;

public interface ProductService {
    Integer countProduct(BuyItem.ProductQueryParams productQueryParams);
    List <Product > getProducts(BuyItem.ProductQueryParams productQueryParams);
    List <ProductItem> getProductsContainsSalesNumber(BuyItem.ProductQueryParams productQueryParams);
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId,ProductRequest productRequest);
    void deleteProductById(Integer productId);

}
