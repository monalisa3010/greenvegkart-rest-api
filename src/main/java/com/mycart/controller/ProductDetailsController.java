package com.mycart.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycart.model.Category;
import com.mycart.model.Product;
import com.mycart.service.ProductDetailsService;

@RestController
@RequestMapping("/api/product")
public class ProductDetailsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductDetailsController.class);

	@Autowired
	ProductDetailsService productDetailsService;

	@GetMapping(value = "/getallproducts", produces = "application/json")
	public List<Product> fetchProducts() {
		LOGGER.info("'fetchProducts()' called");
		List<Product> products = productDetailsService.fetchAllProducts();
		LOGGER.info("Exit from 'fetchProducts()'");
		return products;
	}

	@GetMapping(value = "/getallcategories", produces = "application/json")
	public List<Category> fetchCategories() {
		LOGGER.info("'fetchCategories()' called");
		List<Category> categoryList = productDetailsService.fetchAllCategories();
		LOGGER.info("Exit from 'fetchCategories()..'");
		return categoryList;
	}

	@GetMapping(value = "/category/{id}", produces = "application/json")
	public List<Product> fetchProductsByCategoryId(@PathVariable("id") int id) {
		LOGGER.info("'fetchProductsByCategoryId()' called");
		List<Product> products = productDetailsService.fetchProductsByCategoryId(id);
		LOGGER.info("Exit from 'fetchProductsByCategoryId()'");
		return products;
	}

}
