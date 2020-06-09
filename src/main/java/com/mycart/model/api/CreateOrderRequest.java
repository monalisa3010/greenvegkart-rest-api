package com.mycart.model.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mycart.model.OrderItem;

public class CreateOrderRequest {
	/*CREATE TABLE "MONALISA"."ORDERS" 
	   (	"ORDER_ID" VARCHAR2(50 BYTE) NOT NULL ENABLE, 
		"DELIVERY_ADDRESS_ID" NUMBER NOT NULL ENABLE, 
		"PAYMENT_TYPE" VARCHAR2(20 BYTE) NOT NULL ENABLE, 
		"DELIVERY_DATE" DATE NOT NULL ENABLE, 
		"ORDER_CREATION_DATE" DATE NOT NULL ENABLE, 
		"CUSTOMER_ID" NUMBER NOT NULL ENABLE, 
		"TOTAL_AMOUNT" NUMBER(10,2) NOT NULL ENABLE, 
		"ORDER_STATUS"*/
	private int customerId;
	private int deliveryAddressId;
	private String paymentType;
	private BigDecimal totalAmount;
	List<OrderItem> orderItems = new ArrayList<OrderItem>();

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getDeliveryAddressId() {
		return deliveryAddressId;
	}

	public void setDeliveryAddressId(int deliveryAddressId) {
		this.deliveryAddressId = deliveryAddressId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public CreateOrderRequest(int customerId, int deliveryAddressId, String paymentType, BigDecimal totalAmount,
			List<OrderItem> orderItems) {
		super();
		this.customerId = customerId;
		this.deliveryAddressId = deliveryAddressId;
		this.paymentType = paymentType;
		this.totalAmount = totalAmount;
		this.orderItems = orderItems;
	}

	public CreateOrderRequest() {
		super();
	}

	@Override
	public String toString() {
		return "CreateOrderRequest [customerId=" + customerId + ", deliveryAddressId=" + deliveryAddressId
				+ ", paymentType=" + paymentType + ", totalAmount=" + totalAmount + ", orderItems=" + orderItems + "]";
	}

}
