package com.mycart.model.api;

import com.mycart.model.UserDetails;

public class LoginResponse {

	@Override
	public String toString() {
		return "LoginResponse [userDetails=" + userDetails + ", message=" + message + ", statusCode=" + statusCode
				+ "]";
	}

	private UserDetails userDetails;
	private String message;
	private int statusCode;

	public LoginResponse() {
		super();
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public LoginResponse(UserDetails userDetails, String message, int statusCode) {
		super();
		this.userDetails = userDetails;
		this.message = message;
		this.statusCode = statusCode;
	}

}
