package com.hjn.tmall.service;

import java.util.List;

import com.hjn.tmall.pojo.Review;

public interface ReviewService {
	void add(Review r);
	void delete(int id);
	void update(Review r);
	Review get(int id);//根据id查一条评论
	Review get(int pid,int uid);
	List list(int pid);//查询该产品的所有评论
	int getCount(int pid);//返回该产品的评论数
	List<Review> list(int uid,int pid);//返回uid用户对pid产品的所有评价
}
