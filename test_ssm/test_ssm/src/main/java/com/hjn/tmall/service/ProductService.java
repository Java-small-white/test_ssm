package com.hjn.tmall.service;

import java.util.List;

import com.hjn.tmall.pojo.Category;
import com.hjn.tmall.pojo.Product;

public interface ProductService {
	void add(Product p);
	void delete(int id);
	void update(Product p);
	Product get(int id);
	List list(int cid);
	void setFirstProductImage(Product p);
	void fill(List<Category> cs);//为所有分类设置产品
	void fill(Category c);//为一个分类设置产品
	
	/**为所有分类设置推荐产品，逻辑关系一对多
	* 把分类下的产品集合，拆为多行，每行8个产品进行推荐
	* @param cs 分类集合
	**/
	void fillRecommend(List<Category> cs);
	void setSaleAndReviewNumber(Product p);
	void setSaleAndReviewNumber(List<Product> ps);
	List<Product> search(String keyword);
}
