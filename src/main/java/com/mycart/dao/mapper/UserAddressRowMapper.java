package com.mycart.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mycart.model.UserAddress;

public class UserAddressRowMapper implements RowMapper<UserAddress> {

	@Override
	public UserAddress mapRow(ResultSet rs, int arg1) throws SQLException {
		UserAddress userAddress = new UserAddress();
		userAddress.setAddressId(rs.getInt("address_id"));
		userAddress.setAddrLine1(rs.getString("addr_line_1"));
		userAddress.setAddrLine2(rs.getString("addr_line_2"));
		userAddress.setCity(rs.getString("city"));
		userAddress.setState(rs.getString("state"));
		userAddress.setPin(rs.getInt("pin"));
		return userAddress;
	}

}
