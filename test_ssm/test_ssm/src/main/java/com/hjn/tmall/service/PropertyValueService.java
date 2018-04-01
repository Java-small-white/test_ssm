package com.hjn.tmall.service;

import java.util.List;

import com.hjn.tmall.pojo.Product;
import com.hjn.tmall.pojo.PropertyValue;

public interface PropertyValueService {
	void init(Product p);
	void update(PropertyValue pv);
	PropertyValue get(int ptid,int pid);
	List<PropertyValue> list(int pid);
}
