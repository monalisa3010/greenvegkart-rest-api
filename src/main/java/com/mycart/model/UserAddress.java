package com.mycart.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserAddress {

	@Override
	public String toString() {
		return "UserAddress [addressId=" + addressId + ", addrLine1=" + addrLine1 + ", addrLine2=" + addrLine2
				+ ", city=" + city + ", state=" + state + ", pin=" + pin + "]";
	}
	private int addressId;
	@NotEmpty(message="Address is required")
	private String addrLine1;
	private String addrLine2;
	@NotEmpty(message="City is required")
	private String city;
	@NotEmpty(message="State is required")
	private String state;
	@Min(value=6,message="wrong pin")
	private int pin;

	public UserAddress() {
		super();
	}

	
	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getAddrLine1() {
		return addrLine1;
	}

	public void setAddrLine1(String addrLine1) {
		this.addrLine1 = addrLine1;
	}

	public String getAddrLine2() {
		return addrLine2;
	}

	public void setAddrLine2(String addrLine2) {
		this.addrLine2 = addrLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}
	
	public UserAddress(String addrLine1, String addrLine2, String city, String state, int pin) {
		super();
		this.addrLine1 = addrLine1;
		this.addrLine2 = addrLine2;
		this.city = city;
		this.state = state;
		this.pin = pin;
	}

	public UserAddress(int addressId, String addrLine1, String addrLine2, String city, String state, int pin) {
		super();
		this.addressId = addressId;
		this.addrLine1 = addrLine1;
		this.addrLine2 = addrLine2;
		this.city = city;
		this.state = state;
		this.pin = pin;
	}

}
