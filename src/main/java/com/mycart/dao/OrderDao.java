package com.mycart.dao;

import java.util.Map;

import com.mycart.exception.CartException;
import com.mycart.model.api.CreateOrderRequest;

public interface OrderDao {
	public String createOrder(CreateOrderRequest createOrderRequest) throws CartException;
	
	public Map<String, Object> fetchOrderDetails(String orderid) throws CartException;
	
}
