package com.hjn.tmall.service;

import java.util.List;

import com.hjn.tmall.pojo.Category;
import com.hjn.tmall.util.Page;

public interface CategoryService {
//	int total();
//	List<Category> list(Page page);
	List<Category> list();
	void add(Category category);
	void delete(int id);
	Category get(int id);
	void update(Category category);
}
