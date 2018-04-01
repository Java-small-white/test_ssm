package com.hjn.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjn.tmall.mapper.ReviewMapper;
import com.hjn.tmall.pojo.OrderItemExample;
import com.hjn.tmall.pojo.Review;
import com.hjn.tmall.pojo.ReviewExample;
import com.hjn.tmall.pojo.User;
import com.hjn.tmall.service.ReviewService;
import com.hjn.tmall.service.UserService;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	ReviewMapper reviewMapper;
	
	@Autowired
	UserService userService;
	
	@Override
	public void add(Review r) {
		// TODO Auto-generated method stub
		reviewMapper.insert(r);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		reviewMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(Review r) {
		// TODO Auto-generated method stub
		reviewMapper.updateByPrimaryKeySelective(r);
	}

	@Override
	public Review get(int id) {
		// TODO Auto-generated method stub
		return reviewMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Review> list(int pid) {
		// TODO Auto-generated method stub
		ReviewExample example=new ReviewExample();
		example.createCriteria().andPidEqualTo(pid);
		example.setOrderByClause("id desc");
		List<Review> rs=reviewMapper.selectByExample(example);
		for(Review r:rs) {
			User u=userService.get(r.getUid());
			r.setUser(u);
		}
		return rs;
	}

	@Override
	public int getCount(int pid) {
		// TODO Auto-generated method stub
		return list(pid).size();
	}

	@Override
	public List<Review> list(int uid, int pid) {
		// TODO Auto-generated method stub
		ReviewExample example=new ReviewExample();
		example.createCriteria().andUidEqualTo(uid).andPidEqualTo(pid);
		return reviewMapper.selectByExample(example);
	}

	@Override
	public Review get(int pid, int uid) {
		// TODO Auto-generated method stub
		ReviewExample example=new ReviewExample();
		example.createCriteria().andPidEqualTo(pid).andUidEqualTo(uid);
		List<Review> rs=reviewMapper.selectByExample(example);
		return rs.isEmpty()?null:rs.get(0);
	}

	
}
