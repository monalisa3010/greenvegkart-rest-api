package com.mycart.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mycart.model.OrderItem;

public class OrderItemRowMapper implements RowMapper<OrderItem> {

	@Override
	public OrderItem mapRow(ResultSet rs, int arg1) throws SQLException {
		// int productId, int quantity, String productName, String productPrice
		OrderItem orderItem = new OrderItem(rs.getInt("PRODUCT_ID"), rs.getInt("QUANTITY"), rs.getString("NAME"),
				rs.getBigDecimal("PRICE"));
		return orderItem;
	}

}
