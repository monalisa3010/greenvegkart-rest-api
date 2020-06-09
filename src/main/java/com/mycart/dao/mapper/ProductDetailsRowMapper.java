package com.mycart.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mycart.model.Category;
import com.mycart.model.Product;

public class ProductDetailsRowMapper implements RowMapper<Product> {

	@Override
	public Product mapRow(ResultSet rs, int arg1) throws SQLException {
		Product product = new Product();
		Category category = new Category();
		category.setId(rs.getInt("category_id"));
		category.setName(rs.getString("category_name"));
		product.setName(rs.getString("product_name"));
		product.setPrice(rs.getInt("product_price"));
		product.setMeasuringScale(rs.getString("product_measuring_scale"));
		product.setImage(rs.getString("product_image"));
		product.setId(rs.getInt("product_id"));
		product.setDescription(rs.getString("product_description"));
		product.setCategory(category);
		return product;
	}

}
