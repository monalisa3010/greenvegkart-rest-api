package com.mycart.service;

import java.util.List;

import com.mycart.exception.CartException;
import com.mycart.model.UserAddress;
import com.mycart.model.api.LoginRequest;
import com.mycart.model.api.LoginResponse;
import com.mycart.model.api.UpdateAddressRequest;
import com.mycart.model.api.UpdateAddressResponse;
import com.mycart.model.api.UserRegistrationRequest;
import com.mycart.model.api.UserRegistrationResponse;

public interface CustomerService {

	public LoginResponse checkLogin(LoginRequest loginRequest);

	public UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationRequest) throws CartException;

	public UpdateAddressResponse updateAddress(UpdateAddressRequest updateAddressRequest) throws CartException;

	public List<UserAddress> deleteAddressById(int userid, int addressid) throws CartException;
	
	public List<UserAddress> updateAddress(int userid, UserAddress userAddress) throws CartException;
	
	public boolean isUserNameExists(String username) throws CartException;
}
