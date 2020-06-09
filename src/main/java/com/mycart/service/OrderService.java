package com.mycart.service;

import com.mycart.exception.CartException;
import com.mycart.model.api.CreateOrderRequest;
import com.mycart.model.api.CreateOrderResponse;
import com.mycart.model.api.Order;

public interface OrderService {
	
	public CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest)  throws CartException;

	Order getOrderDetails(String orderID) throws CartException;

}
