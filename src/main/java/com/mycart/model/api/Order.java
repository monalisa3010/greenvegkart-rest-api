package com.mycart.model.api;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.mycart.model.OrderItem;
import com.mycart.model.UserAddress;

public class Order {

	private Date deliveryDate;
	private String orderid;
	private String orderStatus;
	private UserAddress deliveryAddress;
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public UserAddress getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(UserAddress deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Order() {
		super();
	}

}
