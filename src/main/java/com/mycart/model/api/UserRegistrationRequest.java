package com.mycart.model.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.mycart.model.UserDetails;

public class UserRegistrationRequest {
	@NotNull
	@Valid
	private UserDetails userDetails;
	@Pattern(regexp="^(\\w){8,20}$")
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public UserRegistrationRequest() {
		super();
	}

	public UserRegistrationRequest(UserDetails userDetails, String password) {
		super();
		this.userDetails = userDetails;
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserRegistrationRequest [userDetails=" + userDetails + ", password=" + password + "]";
	}

}
