package com.mycart.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.mycart.dao.ProductDetailsDao;
import com.mycart.dao.mapper.CategoriesRowMapper;
import com.mycart.dao.mapper.ProductDetailsRowMapper;
import com.mycart.model.Category;
import com.mycart.model.Product;

import oracle.jdbc.OracleTypes;

@Repository
public class ProductDetailsDaoImpl implements ProductDetailsDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductDetailsDaoImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	SimpleJdbcCall simpleJdbcCallForCategories;
	SimpleJdbcCall simpleJdbcCallForProducts;
	SimpleJdbcCall simpleJdbcCallForProductsByCategoryId;

	@PostConstruct
	public void init() {
		simpleJdbcCallForProducts = new SimpleJdbcCall(jdbcTemplate).withProcedureName("get_all_product_details")
				.declareParameters(new SqlOutParameter("o_cur_products_details", OracleTypes.CURSOR,
						new ProductDetailsRowMapper()));
		
		simpleJdbcCallForCategories = new SimpleJdbcCall(jdbcTemplate).withProcedureName("get_all_categories")
				.declareParameters(
						new SqlOutParameter("o_cur_categories", OracleTypes.CURSOR, new CategoriesRowMapper()));
		
		simpleJdbcCallForProductsByCategoryId = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("get_products_by_category_id")
				.declareParameters(
						new SqlParameter("category_id",OracleTypes.NUMBER),
						new SqlOutParameter("o_cur_products_by_category", OracleTypes.CURSOR, new ProductDetailsRowMapper()));
	
		LOGGER.info("simpleJdbcCallForProducts template initialaized sucessfully");
		LOGGER.info("simpleJdbcCallForProductsByCategoryId template initialaized sucessfully");
		LOGGER.info("simpleJdbcCallForCategories template initialaized sucessfully");
	}

	@Override
	public List<Product> retrieveProducts() {
		LOGGER.info("'retrieveProducts()' called");
		Map<String, Object> map = simpleJdbcCallForProducts.execute();
		List<Product> productDetailsList = null;
		if (map == null) {
			productDetailsList = new ArrayList<Product>();
		}
		productDetailsList = (List<Product>) map.get("o_cur_products_details");
		LOGGER.info("exit from 'retrieveProducts()'");
		return productDetailsList;
	}

	@Override
	public List<Category> retrieveCategories() {
		LOGGER.info("'retrieveCategories()' called");
		Map<String, Object> map = simpleJdbcCallForCategories.execute();
		List<Category> categoryList = null;
		if (map == null) {
			categoryList = new ArrayList<Category>();
		}
		categoryList = (List<Category>) map.get("o_cur_categories");
		LOGGER.info("exit from 'retrieveCategories()'");
		return categoryList;
	}
	@Override
	public List<Product> retrieveProductsByCategoryId(int categoryId) {
		LOGGER.info("'retrieveProductsByCategoryId()' called");
		HashMap<String, Object> inMap = new HashMap<>();
		inMap.put("category_id", categoryId);
		Map<String, Object> map = simpleJdbcCallForProductsByCategoryId.execute(inMap);
		List<Product> productDetailsList = null;
		if (map == null) {
			productDetailsList = new ArrayList<Product>();
		}
		productDetailsList = (List<Product>) map.get("o_cur_products_by_category");
		LOGGER.info("exit from 'retrieveProductsByCategoryId()'");
		return productDetailsList;
	}

}
