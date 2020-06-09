package com.mycart.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.mycart.validations.Phone;

public class UserDetails {
	@Override
	public String toString() {
		return "UserDetails [userId=" + userId + ", username=" + username + ", firstName=" + firstName + ", lastName="
				+ lastName + ", contact=" + contact + ", email=" + email + ", userAddressList=" + userAddressList + "]";
	}

	public UserDetails(int userId, String username, String firstName, String lastName, long contact, String email) {
		super();
		this.userId = userId;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contact = contact;
		this.email = email;
	}

	
	private int userId;
	@NotEmpty(message="Username is required")
	@Pattern(regexp="^(\\w){8,20}$",message="Username must be min of 8 to 20 character long")
	private String username;
	
	@NotEmpty(message="First name is required")
	private String firstName;
	@NotEmpty(message="Last name is required")
	private String lastName;
	
	private long contact;
	@NotEmpty(message="Email is required")
	@Email
	private String email;
	
	private List<UserAddress> userAddressList = new ArrayList<UserAddress>();

	public UserDetails() {
		super();
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public long getContact() {
		return contact;
	}

	public void setContact(long contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<UserAddress> getUserAddressList() {
		return userAddressList;
	}

	public void setUserAddressList(List<UserAddress> userAddressList) {
		this.userAddressList = userAddressList;
	}

	public UserDetails(int userId, String username, String firstName, String lastName, long contact, String email,
			List<UserAddress> userAddressList) {
		super();
		this.userId = userId;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contact = contact;
		this.email = email;
		this.userAddressList = userAddressList;
	}
	
	public UserDetails(String username, String firstName, String lastName, long contact, String email) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contact = contact;
		this.email = email;
	}


}
