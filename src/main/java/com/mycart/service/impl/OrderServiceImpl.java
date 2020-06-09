package com.mycart.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycart.dao.OrderDao;
import com.mycart.exception.CartException;
import com.mycart.model.OrderItem;
import com.mycart.model.UserAddress;
import com.mycart.model.api.CreateOrderRequest;
import com.mycart.model.api.CreateOrderResponse;
import com.mycart.model.api.Order;
import com.mycart.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	OrderDao orderDao;

	@Override
	public CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest) throws CartException {
		LOGGER.info("---Entering service method registerUser()-----");
		String orderId = orderDao.createOrder(createOrderRequest);
		CreateOrderResponse createOrderResponse = new CreateOrderResponse();
		createOrderResponse.setOrderId(orderId);
		createOrderResponse.setExpectedDeliveryDate(new Date());
		LOGGER.info("---Leaving service registerUser() with order id: :----- " + orderId);
		return createOrderResponse;

	}

	@Override
	public Order getOrderDetails(String orderID) throws CartException {
		LOGGER.info("---Entering service method getOrderDetails()-----");
		Map<String, Object> map = orderDao.fetchOrderDetails(orderID);
		Order order = new Order();
		order.setOrderid(orderID);
		if (map.containsKey("o_items_cur")) {
			List<OrderItem> items = (List<OrderItem>) map.get("o_items_cur");
			order.setOrderItems(items);
		}
		if (map.containsKey("o_addr_cur")) {
			List<UserAddress> addresses = (List<UserAddress>) map.get("o_addr_cur");
			order.setDeliveryAddress(addresses.get(0));
		}
		if (map.containsKey("o_expected_delivery")) {
			order.setDeliveryDate((java.sql.Date) map.get("o_expected_delivery"));
		}
		if (map.containsKey("o_order_status")) {
			order.setOrderStatus((String) map.get("o_order_status"));
		}

		LOGGER.info("---Leaving service getOrderDetails()");
		return order;

	}
}
