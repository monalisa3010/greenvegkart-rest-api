package com.mycart.model.api;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.mycart.model.UserAddress;

public class UpdateAddressRequest {

	private int userId;
	@NotNull
	@Valid
	private List<UserAddress> userAddressList = new ArrayList<UserAddress>();

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<UserAddress> getUserAddressList() {
		return userAddressList;
	}

	public void setUserAddressList(List<UserAddress> userAddressList) {
		this.userAddressList = userAddressList;
	}

	public UpdateAddressRequest(int userId, List<@NotEmpty UserAddress> userAddressList) {
		super();
		this.userId = userId;
		this.userAddressList = userAddressList;
	}

	public UpdateAddressRequest() {
		super();
	}

}
