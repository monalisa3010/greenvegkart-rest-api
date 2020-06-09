package com.mycart.model.api;

import java.util.Date;

public class CreateOrderResponse {

	private String orderId;
	private Date expectedDeliveryDate;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}

	public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}

	public CreateOrderResponse(String orderId, Date expectedDeliveryDate) {
		super();
		this.orderId = orderId;
		this.expectedDeliveryDate = expectedDeliveryDate;
	}

	public CreateOrderResponse() {
		super();
	}

	@Override
	public String toString() {
		return "CreateOrderResponse [orderId=" + orderId + ", expectedDeliveryDate=" + expectedDeliveryDate + "]";
	}

}
