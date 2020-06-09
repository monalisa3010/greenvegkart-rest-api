package com.mycart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycart.exception.CartException;
import com.mycart.model.api.CreateOrderRequest;
import com.mycart.model.api.CreateOrderResponse;
import com.mycart.model.api.Order;
import com.mycart.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	OrderService orderService;

	@PostMapping(value = "/createorder", consumes = "application/json", produces = "application/json")
	public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest createOrderRequest) throws CartException {
		LOGGER.info("'createOrder()' called");
		CreateOrderResponse createOrderResponse = orderService.createOrder(createOrderRequest);
		LOGGER.info("Exit from 'createOrder()'");
		return createOrderResponse;
	}

	@GetMapping(value = "/orderdetails/{orderid}", produces = "application/json")
	public Order fetchOrder(@PathVariable("orderid") String orderid) throws CartException {
		LOGGER.info("'createOrder()' called");
		Order order = orderService.getOrderDetails(orderid);
		LOGGER.info("Exit from 'createOrder()'");
		return order;
	}
}
