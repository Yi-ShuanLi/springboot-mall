package com.lijenny.springbootmall.rowmpper;

import com.lijenny.springbootmall.model.AllBuyerByProductId;
import com.lijenny.springbootmall.model.OrderItem;
import com.lijenny.springbootmall.model.ProductItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AllBuyerByProductIdRowMapper implements RowMapper<AllBuyerByProductId> {
    @Override
    public AllBuyerByProductId mapRow(ResultSet resultSet, int i)throws SQLException{
        AllBuyerByProductId allBuyerByProductId = new AllBuyerByProductId();
        allBuyerByProductId.setOrederItemId(resultSet.getInt("order_item_id"));
        allBuyerByProductId.setOrderId(resultSet.getInt("order_id"));
        allBuyerByProductId.setQuantity(resultSet.getInt("quantity"));
        allBuyerByProductId.setAmount(resultSet.getInt("amount"));
        allBuyerByProductId.setUserId(resultSet.getInt("user_id"));
        allBuyerByProductId.setEmail(resultSet.getString("email"));
        allBuyerByProductId.setCreatedDate(resultSet.getTimestamp("created_date"));
        return allBuyerByProductId;
    }
}
