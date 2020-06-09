package com.mycart.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mycart.model.UserDetails;

public class UserDetailsRowMapper implements RowMapper<UserDetails> {

	@Override
	public UserDetails mapRow(ResultSet rs, int arg1) throws SQLException {
		UserDetails userDetails = new UserDetails();
		userDetails.setUserId(rs.getInt("user_id"));
		userDetails.setUsername(rs.getString("user_name"));
		userDetails.setFirstName(rs.getString("first_name"));
		userDetails.setLastName(rs.getString("last_name"));
		userDetails.setContact(rs.getLong("contact"));
		userDetails.setEmail(rs.getString("email"));
		return userDetails;
	}

}
