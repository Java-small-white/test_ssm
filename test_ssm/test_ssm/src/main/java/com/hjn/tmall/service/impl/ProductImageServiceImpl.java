package com.hjn.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjn.tmall.mapper.ProductImageMapper;
import com.hjn.tmall.pojo.ProductImage;
import com.hjn.tmall.pojo.ProductImageExample;
import com.hjn.tmall.service.ProductImageService;

@Service
public class ProductImageServiceImpl implements ProductImageService{

	@Autowired
	ProductImageMapper productImageMapper;
	@Override
	public void add(ProductImage pi) {
		// TODO Auto-generated method stub
		productImageMapper.insert(pi);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		productImageMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(ProductImage pi) {
		// TODO Auto-generated method stub
		productImageMapper.updateByPrimaryKeySelective(pi);
	}

	@Override
	public ProductImage get(int id) {
		// TODO Auto-generated method stub
		return productImageMapper.selectByPrimaryKey(id);
	}

	@Override
	public List list(int pid, String type) {
		// TODO Auto-generated method stub
		ProductImageExample example =new ProductImageExample();
        example.createCriteria()
                .andPidEqualTo(pid)
                .andTypeEqualTo(type);
        example.setOrderByClause("id desc");
        return productImageMapper.selectByExample(example);	
	}

}
