package com.hjn.tmall.pojo;

import java.util.List;

public class Category {
    private Integer id;

    private String name;
    
    //以下非数据库字段
    List<Product> products;
    
    //首页纵向导航栏的推荐产品列表
    List<List<Product>> productsRecommend;
    
    public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<List<Product>> getProductsRecommend() {
		return productsRecommend;
	}

	public void setProductsRecommend(List<List<Product>> productsRecommend) {
		this.productsRecommend = productsRecommend;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}