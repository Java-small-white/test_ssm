package com.hjn.tmall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjn.tmall.mapper.ProductMapper;
import com.hjn.tmall.pojo.Category;
import com.hjn.tmall.pojo.Product;
import com.hjn.tmall.pojo.ProductExample;
import com.hjn.tmall.pojo.ProductImage;
import com.hjn.tmall.service.CategoryService;
import com.hjn.tmall.service.OrderItemService;
import com.hjn.tmall.service.ProductImageService;
import com.hjn.tmall.service.ProductService;
import com.hjn.tmall.service.ReviewService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductMapper productMapper;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductImageService productImageService;
	@Autowired
	OrderItemService orderItemService;
	@Autowired
	ReviewService reviewService;
	
	@Override
	public void add(Product p) {
		// TODO Auto-generated method stub
		productMapper.insert(p);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		productMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(Product p) {
		// TODO Auto-generated method stub
		productMapper.updateByPrimaryKeySelective(p);	
	}

	@Override
	public Product get(int id) {
		// TODO Auto-generated method stub	
		Product p=productMapper.selectByPrimaryKey(id);
		Category c=categoryService.get(p.getCid());
		p.setCategory(c);
		setFirstProductImage(p);
		return p;
	}

	@Override
	public List list(int cid) {
		// TODO Auto-generated method stub
		ProductExample example=new ProductExample();
		example.createCriteria().andCidEqualTo(cid);
		example.setOrderByClause("id desc");
		List<Product> products=productMapper.selectByExample(example);
		for(Product p:products) {//为产品设置分类，逻辑关系多对一
			Category c=categoryService.get(p.getCid());
			p.setCategory(c);
		}
		setFirstProductImage(products);
		return products;
	}

	//给单个产品设置图片
	@Override
	public void setFirstProductImage(Product p) {
		// TODO Auto-generated method stub
		List<ProductImage> pis=productImageService.list(p.getId(), ProductImageService.type_single);
		if(!pis.isEmpty()) {
			ProductImage piImage=pis.get(0);
			p.setFirstProductImage(piImage);
		}
	}
	
	//给多个产品设置图片
	public void setFirstProductImage(List<Product> ps) {
		for(Product p:ps) {
			setFirstProductImage(p);
		}
	}

	//为所有分类设置产品,逻辑关系一对多
	@Override
	public void fill(List<Category> cs) {
		// TODO Auto-generated method stub
		for(Category c:cs) {
			fill(c);
		}
	}

	//为一个分类设置产品，逻辑关系一对多
	@Override
	public void fill(Category c) {
		// TODO Auto-generated method stub
		List<Product> ps=list(c.getId());
		c.setProducts(ps);
	}

	@Override
	public void fillRecommend(List<Category> cs) {
		// TODO Auto-generated method stub
		int RecommendNumberRow=8;//每行推荐产品数
		for(Category c:cs) {
			List<Product> ps=c.getProducts();
			List<List<Product>> Recommend=new ArrayList<>();
			//下面的for是将该分类下的产品集合，拆为多行，每行8个产品放入推荐集合Recommend中
			for(int start=0;start<ps.size();start+=RecommendNumberRow) {	
				int end=start+RecommendNumberRow;
				//判断产品集合的索引是否到了最后一段位置
				end=end>ps.size()?ps.size():end;
				Recommend.add(ps.subList(start, end));
			}
			//最后设置分类的推荐集合。
			c.setProductsRecommend(Recommend);
		}
	}

	@Override
	public void setSaleAndReviewNumber(Product p) {
		// TODO Auto-generated method stub
		int saleCount=orderItemService.getSaleCount(p.getId());
		int reviewCount=reviewService.getCount(p.getId());
		p.setSaleCount(saleCount);
		p.setReviewCount(reviewCount);
	}

	@Override
	public void setSaleAndReviewNumber(List<Product> ps) {
		// TODO Auto-generated method stub
		for(Product p:ps) {
			setSaleAndReviewNumber(p);
		}
	}

	@Override
	public List<Product> search(String keyword) {
		// TODO Auto-generated method stub
		ProductExample example=new ProductExample();
		example.createCriteria().andNameLike("%"+keyword+"%");
		example.setOrderByClause("id desc");
		List<Product> ps=productMapper.selectByExample(example);
		for(Product p:ps) {
			List<ProductImage> pis=productImageService.list(p.getId(), "type_single");
			if(!pis.isEmpty()) {
				p.setFirstProductImage(pis.get(0));
			}
		}
		return ps;
	}

}
