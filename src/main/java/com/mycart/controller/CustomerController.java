package com.mycart.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycart.exception.CartException;
import com.mycart.model.UserAddress;
import com.mycart.model.api.LoginRequest;
import com.mycart.model.api.LoginResponse;
import com.mycart.model.api.UpdateAddressRequest;
import com.mycart.model.api.UpdateAddressResponse;
import com.mycart.model.api.UserRegistrationRequest;
import com.mycart.model.api.UserRegistrationResponse;
import com.mycart.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
@Validated
public class CustomerController {

	@Value("${customer.service.sleeptime}")
	private long sleepTime;

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	CustomerService customerService;

	@PostMapping(value = "/registeruser", consumes = "application/json")
	public UserRegistrationResponse registerUser(@RequestBody @Valid UserRegistrationRequest userRegistrationRequest)
			throws CartException {
		LOGGER.info("'registeruser()' called");
		sleepForSomeTime();
		UserRegistrationResponse registrationResponse = customerService.registerUser(userRegistrationRequest);
		LOGGER.info("Exit from 'registeruser()'");
		return registrationResponse;
	}

	@PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
	public LoginResponse validateLogin(@RequestBody @Valid LoginRequest loginRequest) {
		LOGGER.info("'validateLogin()' called");
		sleepForSomeTime();
		LoginResponse loginResponse = customerService.checkLogin(loginRequest);
		LOGGER.info("Exit from 'validateLogin()'");
		return loginResponse;
	}

	@PostMapping(value = "/updateAddress", consumes = "application/json", produces = "application/json")
	public UpdateAddressResponse updateUserAddress(@RequestBody @Valid UpdateAddressRequest updateAddressRequest)
			throws CartException {
		LOGGER.info("'updateUserAddress()' called");
		sleepForSomeTime();
		UpdateAddressResponse updateAddressResponse = customerService.updateAddress(updateAddressRequest);
		LOGGER.info("Exit from 'updateUserAddress()'");
		return updateAddressResponse;
	}

	@GetMapping(value = "{userid}/address/delete/{addressid}", produces = "application/json")
	public List<UserAddress> deleteAddressById(@PathVariable("userid") int userid,
			@PathVariable("addressid") int addressid) throws CartException {
		List<UserAddress> addresses = customerService.deleteAddressById(userid, addressid);
		return addresses;
	}

	@PutMapping(value = "{userid}/address/update", consumes = "application/json", produces = "application/json")
	public List<UserAddress> updateAddressById(@PathVariable("userid") int userid,
			@RequestBody @Valid UserAddress userAddress) throws CartException {
		List<UserAddress> addresses = customerService.updateAddress(userid, userAddress);
		return addresses;
	}

	@GetMapping(value = "checkusername/{username}",produces = "application/json")
	public boolean check_availability(@PathVariable("username") String username) throws CartException {
		boolean isUserNameExists = customerService.isUserNameExists(username);
		return isUserNameExists;
	}

	private void sleepForSomeTime() {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
