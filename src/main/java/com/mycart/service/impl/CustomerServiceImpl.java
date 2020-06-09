package com.mycart.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycart.dao.CustomerDao;
import com.mycart.exception.CartException;
import com.mycart.model.UserAddress;
import com.mycart.model.UserDetails;
import com.mycart.model.api.LoginRequest;
import com.mycart.model.api.LoginResponse;
import com.mycart.model.api.UpdateAddressRequest;
import com.mycart.model.api.UpdateAddressResponse;
import com.mycart.model.api.UserRegistrationRequest;
import com.mycart.model.api.UserRegistrationResponse;
import com.mycart.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerDao customerDao;

	@Override
	public UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationRequest) throws CartException {
		LOGGER.info("---Entering service method registerUser()-----");
		UserRegistrationResponse registrationResponse = new UserRegistrationResponse();
		int statusCode = customerDao.registerUser(userRegistrationRequest);
		registrationResponse.setSuccess(statusCode == 1 ? true : false);
		LOGGER.info("---Leaving service registerUser() with status code :----- " + statusCode);
		return registrationResponse;
	}

	@Override
	public UpdateAddressResponse updateAddress(UpdateAddressRequest updateAddressRequest) throws CartException {
		LOGGER.info("----Entering service method updateAddress()------- ");
		Map<String, Object> addAddressResponseMap = customerDao.updateAddress(updateAddressRequest);
		UpdateAddressResponse updateAddressResponse = new UpdateAddressResponse();
		// updateAddressResponse.setSuccess(returnCode == 1 ? true : false);
		// LOGGER.info("----Leaving service method updateAddress() , status code is: " +
		// returnCode);
		if (addAddressResponseMap.containsKey("o_return_code")) {
			int returnCode = ((BigDecimal) addAddressResponseMap.get("o_return_code")).intValue();
			updateAddressResponse.setSuccess(returnCode == 1);
		}
		if (addAddressResponseMap.containsKey("o_cur_addresses")) {
			List<UserAddress> addresses = (List<UserAddress>) addAddressResponseMap.get("o_cur_addresses");
			updateAddressResponse.setUserAddresses(addresses);
		}
		LOGGER.info("----Leaving service method updateAddress() , status is: " + updateAddressResponse.isSuccess());
		return updateAddressResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LoginResponse checkLogin(LoginRequest loginRequest) {
		LOGGER.info("----Entering service method checkLogin()------- ");
		Map<String, Object> map = customerDao.checkUserLogin(loginRequest);
		LoginResponse loginResponse = new LoginResponse();
		if (((BigDecimal) map.get("o_return_code")).intValue() == 1) {
			UserDetails userDetails = ((List<UserDetails>) map.get("cur_user_details")).get(0);
			List<UserAddress> userAddressList = (List<UserAddress>) map.get("cur_address_list");
			userDetails.setUserAddressList(userAddressList);
			loginResponse.setUserDetails(userDetails);
		}
		loginResponse.setMessage((String) map.get("o_message"));
		loginResponse.setStatusCode(((BigDecimal) map.get("o_return_code")).intValue());
		LOGGER.info("----Leaving service method checkLogin() , status code is:  "
				+ ((BigDecimal) map.get("o_return_code")).intValue());
		return loginResponse;
	}

	@Override
	public List<UserAddress> deleteAddressById(int userid, int addressid) throws CartException {
		Map<String, Object> deleteAddressResultMap = customerDao.deleteAddressById(userid, addressid);
		return (List<UserAddress>) deleteAddressResultMap.get("o_cur_addresses");

	}

	@Override
	public List<UserAddress> updateAddress(int userid, UserAddress userAddress) throws CartException {
		Map<String, Object> updateAddressResultMap = customerDao.updateAddress(userid, userAddress);
		return (List<UserAddress>) updateAddressResultMap.get("o_cur_addresses");
	}

	@Override
	public boolean isUserNameExists(String username) throws CartException {
		return customerDao.isUserNameExists(username);
	}

}
