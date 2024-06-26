package com.lijenny.springbootmall.dao.impl;

import com.lijenny.springbootmall.dao.ProductDao;
import com.lijenny.springbootmall.dto.BuyItem;
import com.lijenny.springbootmall.dto.ProductRequest;
import com.lijenny.springbootmall.model.Product;
import com.lijenny.springbootmall.model.ProductItem;
import com.lijenny.springbootmall.rowmpper.ProductItemRowMapper;
import com.lijenny.springbootmall.rowmpper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer countProduct(BuyItem.ProductQueryParams productQueryParams) {
        String sql="SELECT COUNT(*) FROM product WHERE 1=1";

        Map <String ,Object > map=new HashMap <> ();
        sql=addFilteringSql(sql,map,productQueryParams);
        Integer total= namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);

        return total;
    }

    @Override
    public List<Product> getProducts(BuyItem.ProductQueryParams productQueryParams) {
        String sql="SELECT product_id,product_name, category, image_url, price, stock, description, created_date, last_modified_date FROM product WHERE 1=1";

        Map <String ,Object >map=new HashMap <> ();
        //查詢條件
        sql=addFilteringSql(sql,map,productQueryParams);
        //排序
        sql=sql+" ORDER BY "+productQueryParams.getOrderBy()+" "+productQueryParams.getSort();
        //分頁
        sql=sql+" LIMIT :limit OFFSET :offset";
        map.put("limit",productQueryParams.getLimit());
        map.put("offset",productQueryParams.getOffset());
        // sql injection => 資料庫隱碼攻擊
        // string account = ' or 1=1; Delete User; ##
        // select * from User where Account = '' or 1=1 --' and Password = ''
        List <Product> productList=namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());
        return productList;
    }

    @Override
    public List<ProductItem> getProductsContainsSalesNumber(BuyItem.ProductQueryParams productQueryParams) {
        String sql="SELECT  product.product_id, product.product_name,  product.category,  product.image_url,  product.price,  product.stock,  product.description,  product.created_date,  product.last_modified_date , IFNULL(SUM(order_item.quantity),0)  AS salesNumber FROM product LEFT JOIN order_item ON product.product_id=order_item.product_id WHERE 1=1 ";

        Map <String ,Object > map =new HashMap <> ();
        sql=addFilteringSql(sql,map,productQueryParams);

        sql=sql+" GROUP BY product.product_id, product.product_name, product.category, product.image_url, product.price, product.stock, product.description, product.created_date, product.last_modified_date  HAVING 1=1 ";



        sql=sql+" ORDER BY "+productQueryParams.getOrderBy()+" "+productQueryParams.getSort();
        sql=sql+" LIMIT :limit OFFSET :offset";
        map.put("limit",productQueryParams.getLimit());
        map.put("offset",productQueryParams.getOffset());
        List <ProductItem> productItemList=namedParameterJdbcTemplate.query(sql,map,new ProductItemRowMapper());


        return productItemList;
    }

    @Override
    public Product getProductById(Integer productId) {
        String sql="select product_id,product_name, category, image_url, price, stock, description, created_date, last_modified_date FROM product WHERE product_id=:productId";

        Map <String,Object> map=new HashMap<>();
        map.put("productId",productId);

        List <Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if(productList.size() >0){
            return productList.get(0);
        }else{
            return null;
        }

    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql="INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) "+
                "VALUES (:productName ,:category ,:imageUrl ,:price ,:stock , :description ,:createdDate , :lastModifiedDate)";
        Map <String ,Object> map=new HashMap<>();
        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        Date now=new Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);

        KeyHolder keyHolder=new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

        int productId=keyHolder.getKey().intValue();
        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql="UPDATE product SET product_name=:productName ,category=:category ,image_url=:imageUrl ,"+
                "price =:price , stock=:stock ,description=:description ,last_modified_date=:lastModifiedDate "+
                "WHERE product_id=:productId";
        Map <String,Object> map=new HashMap<>();
        map.put("productId",productId);
        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());
        map.put("lastModifiedDate",new Date());
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void updateStock(Integer productId, Integer stock) {
        String sql="UPDATE product SET stock=:stock ,last_modified_date=:lastModifiedDate "+
                " WHERE product_id=:productId" ;

        Map <String ,Object > map=new HashMap <> ();
        map.put("productId",productId);
        map.put("stock",stock);
        map.put("lastModifiedDate",new Date());

        namedParameterJdbcTemplate.update(sql,map);

    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql="DELETE FROM product WHERE product_id=:productId";

        Map <String ,Object> map=new HashMap<>();
        map.put("productId",productId);

        namedParameterJdbcTemplate.update(sql,map);
    }

    private String addFilteringSql(String sql, Map<String ,Object> map, BuyItem.ProductQueryParams productQueryParams){
        //查詢條件
        if(productQueryParams.getCategory()!=null){
            sql+=" AND category =:category";
            map.put("category",productQueryParams.getCategory().name());
        }
        //使用jdbc的百分比，要寫在map的值裡面，不能寫在sql裡面，這是spring jebc的限制
        if(productQueryParams.getSearch()!=null){
            sql=sql+" And product_name LIKE :search";
            map.put("search","%"+productQueryParams.getSearch()+"%");
        }
        return sql;
    }
}
