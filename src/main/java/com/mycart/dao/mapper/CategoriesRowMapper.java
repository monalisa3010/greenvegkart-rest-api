package com.mycart.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mycart.model.Category;

public class CategoriesRowMapper implements RowMapper<Category> {

	@Override
	public Category mapRow(ResultSet rs, int arg1) throws SQLException {
		Category category = new Category(rs.getInt("ID"), rs.getString("NAME"), rs.getString("DESCRIPTION"));
		return category;
	}

}
