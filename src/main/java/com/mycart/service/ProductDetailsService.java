package com.mycart.service;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

import com.mycart.model.Category;
import com.mycart.model.Product;


public interface ProductDetailsService {


	public List<Product> fetchAllProducts();
	public List<Category> fetchAllCategories();
	public List<Product> fetchProductsByCategoryId(int categoryId);
}
