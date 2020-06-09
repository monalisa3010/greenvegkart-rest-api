package com.mycart.model.api;

import java.util.List;

import com.mycart.model.UserAddress;

public class UpdateAddressResponse {

	private boolean success;
	private List<UserAddress> userAddresses;

	@Override
	public String toString() {
		return "UpdateAddressResponse [success=" + success + ", userAddresses=" + userAddresses + "]";
	}

	public UpdateAddressResponse(boolean success, List<UserAddress> userAddresses) {
		super();
		this.success = success;
		this.userAddresses = userAddresses;
	}

	public List<UserAddress> getUserAddresses() {
		return userAddresses;
	}

	public void setUserAddresses(List<UserAddress> userAddresses) {
		this.userAddresses = userAddresses;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public UpdateAddressResponse() {
		super();
	}

}
