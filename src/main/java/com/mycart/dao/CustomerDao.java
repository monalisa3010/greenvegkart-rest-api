package com.mycart.dao;

import java.util.Map;

import com.mycart.exception.CartException;
import com.mycart.model.UserAddress;
import com.mycart.model.api.LoginRequest;
import com.mycart.model.api.UpdateAddressRequest;
import com.mycart.model.api.UserRegistrationRequest;

public interface CustomerDao {

	public Map<String, Object> checkUserLogin(LoginRequest loginRequest);

	public int registerUser(UserRegistrationRequest userRegistrationRequest) throws CartException;

	public Map<String, Object> updateAddress(UpdateAddressRequest userAddress) throws CartException;
	
	Map<String, Object> deleteAddressById(int userid, int addressid) throws CartException;

	Map<String, Object> updateAddress(int userid, UserAddress userAddress);
	
	public boolean isUserNameExists(String username) throws CartException;

}
