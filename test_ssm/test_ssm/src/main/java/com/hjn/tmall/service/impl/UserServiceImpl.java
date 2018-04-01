package com.hjn.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjn.tmall.mapper.UserMapper;
import com.hjn.tmall.pojo.User;
import com.hjn.tmall.pojo.UserExample;
import com.hjn.tmall.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;

	@Override
	public void add(User u) {
		// TODO Auto-generated method stub
		userMapper.insert(u);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(User u) {
		// TODO Auto-generated method stub
		userMapper.updateByPrimaryKey(u);
	}

	@Override
	public User get(int id) {
		// TODO Auto-generated method stub	
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<User> list() {
		// TODO Auto-generated method stub
		UserExample userExample=new UserExample();
		userExample.setOrderByClause("id desc");	
		return userMapper.selectByExample(userExample);
	}

	@Override
	public boolean isExist(String name) {
		// TODO Auto-generated method stub
		UserExample example=new UserExample();
		example.createCriteria().andNameEqualTo(name);
		List<User> users=userMapper.selectByExample(example);	
		return users.isEmpty();
	}

	@Override
	public User get(String name, String password) {
		// TODO Auto-generated method stub
		UserExample example=new UserExample();
		example.createCriteria().andNameEqualTo(name).andPasswordEqualTo(password);
		List<User> us=userMapper.selectByExample(example);
		//us为空则返回null表示没有该用户,非空则返回第一个元素，因为查找到的也只有一个。
		return us.isEmpty()?null:us.get(0);
	}

}
