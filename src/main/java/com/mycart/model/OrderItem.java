package com.mycart.model;

import java.math.BigDecimal;

public class OrderItem {

	private int productId;
	private int quantity;
	private String productName;
	private BigDecimal productPrice;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	@Override
	public String toString() {
		return "OrderItem [productId=" + productId + ", quantity=" + quantity + "]";
	}

	public OrderItem(int productId, int quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}
	
	

	public OrderItem(int productId, int quantity, String productName, BigDecimal productPrice) {
		super();
		this.productId = productId;
		this.quantity = quantity;
		this.productName = productName;
		this.productPrice = productPrice;
	}

	public OrderItem() {
		super();
	}

}
