package com.lijenny.springbootmall.rowmpper;

import com.lijenny.springbootmall.constant.ProductCategory;
import com.lijenny.springbootmall.model.Product;
import com.lijenny.springbootmall.model.ProductItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductItemRowMapper implements RowMapper<ProductItem> {
    @Override
    public ProductItem mapRow(ResultSet resultSet, int i)throws SQLException {
        ProductItem productItem=new ProductItem();
        productItem.setProductId(resultSet.getInt("product_id"));
        productItem.setProductName(resultSet.getString("product_name"));

        //String categoryStr=resultSet.getString("category");
        //ProductCategory category =ProductCategory.valueOf(categoryStr);
        //ProductCategory category =ProductCategory.valueOf(categoryStr);
        //product.setCategory(resultSet.getString("category"));

        ProductCategory category =ProductCategory.valueOf(resultSet.getString("category"));
        productItem.setCategory(category);
        productItem.setImageUrl(resultSet.getString("image_url"));
        productItem.setPrice(resultSet.getInt("price"));
        productItem.setStock(resultSet.getInt("stock"));
        productItem.setDescription(resultSet.getString("description"));
        productItem.setCreatedDate(resultSet.getTimestamp("created_date"));
        productItem.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));
        productItem.setSalesNumber(resultSet.getInt("salesNumber"));

        return productItem;

    }
}
