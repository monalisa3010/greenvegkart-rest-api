package com.mycart.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycart.dao.ProductDetailsDao;
import com.mycart.model.Category;
import com.mycart.model.Product;
import com.mycart.service.ProductDetailsService;

@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductDetailsServiceImpl.class);

	@Autowired
	ProductDetailsDao productDetailsDao;

	@Override
	public List<Product> fetchAllProducts() {
		LOGGER.info("---Entering service method fetchAllProducts()-----");
		List<Product> productDetailsList = productDetailsDao.retrieveProducts();
		LOGGER.info("---Leaving service fetchAllProducts():----- ");
		return productDetailsList;
	}

	@Override
	public List<Category> fetchAllCategories() {
		LOGGER.info("---Entering service method fetchAllCategories()-----");
		List<Category> categoryList = productDetailsDao.retrieveCategories();
		LOGGER.info("---Leaving service fetchAllCategories():----- ");
		return categoryList;
	}

	@Override
	public List<Product> fetchProductsByCategoryId(int categoryId) {
		LOGGER.info("---Entering service method fetchAllProducts()-----");
		List<Product> productDetailsList = productDetailsDao.retrieveProductsByCategoryId(categoryId);
		LOGGER.info("---Leaving service fetchAllProducts():----- ");
		return productDetailsList;
	}

}
