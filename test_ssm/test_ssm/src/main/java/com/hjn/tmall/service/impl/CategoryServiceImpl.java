package com.hjn.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjn.tmall.mapper.CategoryMapper;
import com.hjn.tmall.pojo.Category;
import com.hjn.tmall.pojo.CategoryExample;
import com.hjn.tmall.service.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	CategoryMapper categoryMapper;
	
	@Override
	public List<Category> list() {
		// TODO Auto-generated method stub
		CategoryExample example =new CategoryExample();
        example.setOrderByClause("id desc");
        return categoryMapper.selectByExample(example);
	};

	@Override
	public void add(Category category) {
		// TODO Auto-generated method stub
		 categoryMapper.insert(category);
	}
	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		 categoryMapper.deleteByPrimaryKey(id);
	}
	@Override
	public Category get(int id) {
		// TODO Auto-generated method stub
		 return categoryMapper.selectByPrimaryKey(id);
	}
	@Override
	public void update(Category category) {
		// TODO Auto-generated method stub
		categoryMapper.updateByPrimaryKeySelective(category);
	}
	
}
