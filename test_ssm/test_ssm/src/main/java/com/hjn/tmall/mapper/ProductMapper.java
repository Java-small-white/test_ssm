package com.hjn.tmall.mapper;

import java.util.List;

import com.hjn.tmall.pojo.Product;
import com.hjn.tmall.pojo.ProductExample;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    List<Product> selectByExample(ProductExample example);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
}