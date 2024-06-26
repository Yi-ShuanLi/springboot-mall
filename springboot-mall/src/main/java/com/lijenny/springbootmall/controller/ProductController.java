package com.lijenny.springbootmall.controller;

import com.lijenny.springbootmall.constant.ProductCategory;
import com.lijenny.springbootmall.dto.BuyItem;
import com.lijenny.springbootmall.dto.ProductRequest;
import com.lijenny.springbootmall.model.Product;
import com.lijenny.springbootmall.model.ProductItem;
import com.lijenny.springbootmall.service.ProductService;
import com.lijenny.springbootmall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity <Page<Product>> getProducts(
            //查詢條件 Filtering
           @RequestParam(required=false) ProductCategory category,
           @RequestParam(required=false) String search ,
           //排序 Sorting
           @RequestParam(defaultValue="created_date") String orderBy,
           @RequestParam(defaultValue="desc") String sort,
            //控制分頁 Pagination 有最大和最小值，需在class上面加上 @Validated
            @RequestParam(defaultValue="5") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue="0") @Min(0) Integer offset){

        BuyItem.ProductQueryParams productQueryParams=new BuyItem.ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        //取得 product list
        List <Product> productList =productService.getProducts(productQueryParams);
        //取得 product 總數
        Integer total=productService.countProduct(productQueryParams);

        //分頁
        Page<Product> page=new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);

    }

    @GetMapping("/productsContainSalesNumber")
    public ResponseEntity <Page<ProductItem>> getProductsContainSalesNumber(
            //查詢條件 Filtering
            @RequestParam(required=false) ProductCategory category,
            @RequestParam(required=false) String search ,
            //排序 Sorting
            @RequestParam(defaultValue="created_date") String orderBy,
            @RequestParam(defaultValue="desc") String sort,
            //控制分頁 Pagination 有最大和最小值，需在class上面加上 @Validated
            @RequestParam(defaultValue="5") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue="0") @Min(0) Integer offset){

        BuyItem.ProductQueryParams productQueryParams=new BuyItem.ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        //取得 product list
        List <ProductItem> ProductItemListList =productService.getProductsContainsSalesNumber(productQueryParams);
        //取得 product 總數
        Integer total=productService.countProduct(productQueryParams);

        //分頁
        Page<ProductItem> page=new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(ProductItemListList);

        return ResponseEntity.status(HttpStatus.OK).body(page);

    }

    @GetMapping("/products/{productId}")
    public ResponseEntity <Product> getProduct(@PathVariable Integer productId){
        Product product=productService.getProductById(productId);
        if(product!=null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity <Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId=productService.createProduct(productRequest);

        Product product=productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);

    }

    @PutMapping("/products/{productId}")
    public ResponseEntity <Product> updateProduct(@PathVariable Integer productId,
                                                  @RequestBody @Valid ProductRequest productRequest){
        //先去檢查product是否存在
        Product product=productService.getProductById(productId);
        if(product==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        //修改商品的數據
        productService.updateProduct(productId,productRequest);

        Product updatedProduct=productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity <?> deleteProduct(@PathVariable Integer productId){
        productService.deleteProductById(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
