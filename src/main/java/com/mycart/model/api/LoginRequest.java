package com.mycart.model.api;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class LoginRequest {

	@Override
	public String toString() {
		return "LoginRequest [username=" + username + ", password=" + password + "]";
	}

	@NotEmpty(message="Username is required")
	@Pattern(regexp="^(\\w){8,20}$",message="Username must be min of 8 to 20 character long")
	private String username;
	@NotEmpty(message="Password is required")
	@Pattern(regexp="^(\\w){8,20}$",message="Password must be min of 8 to 20 character long and must contain atleast a Capital leeter, a small letter and a numeric")
	private String password;

	public LoginRequest() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LoginRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

}
