package com.mycart.dao;

import java.util.List;

import com.mycart.model.Category;
import com.mycart.model.Product;

public interface ProductDetailsDao {

	public List<Product> retrieveProducts();
	public List<Category> retrieveCategories();
	public List<Product> retrieveProductsByCategoryId(int categoryId);
}
