package com.hjn.tmall.service;

import java.util.List;

import com.hjn.tmall.pojo.User;

public interface UserService {
	void add(User u);
	void delete(int id);
	void update(User u);
	User get(int id);
	List<User> list();
	boolean isExist(String name);//判断用户名是否已存在
	User get(String name,String password);
}
