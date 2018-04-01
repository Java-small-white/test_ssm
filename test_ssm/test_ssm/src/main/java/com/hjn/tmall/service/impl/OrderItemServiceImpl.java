package com.hjn.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjn.tmall.mapper.OrderItemMapper;
import com.hjn.tmall.pojo.Order;
import com.hjn.tmall.pojo.OrderItem;
import com.hjn.tmall.pojo.OrderItemExample;
import com.hjn.tmall.pojo.Product;
import com.hjn.tmall.pojo.Review;
import com.hjn.tmall.pojo.User;
import com.hjn.tmall.service.OrderItemService;
import com.hjn.tmall.service.ProductService;
import com.hjn.tmall.service.ReviewService;
import com.hjn.tmall.service.UserService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	OrderItemMapper orderItemMapper;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ReviewService reviewService;
	
	@Override
	public void add(OrderItem orderItem) {
		// TODO Auto-generated method stub
		orderItemMapper.insert(orderItem);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		orderItemMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(OrderItem orderItem) {
		// TODO Auto-generated method stub
		orderItemMapper.updateByPrimaryKeySelective(orderItem);
	}

	@Override
	public OrderItem get(int id) {
		// TODO Auto-generated method stub
		OrderItem oi=orderItemMapper.selectByPrimaryKey(id);
		Product p=productService.get(oi.getPid());
		oi.setProduct(p);//为OrderItem对象设置逻辑关系
		return oi;
	}

	@Override
	public List<OrderItem> list() {
		// TODO Auto-generated method stub
		OrderItemExample example=new OrderItemExample();
		example.setOrderByClause("id desc");
		return orderItemMapper.selectByExample(example);
	}

	@Override
	public void fill(List<Order> orders) {
		// TODO Auto-generated method stub
		for(Order order:orders) {
			fill(order);
		}
	}

	/**
	 * @Description 为订单设置订单项、总价、总件数;为订单项设置产品、isReview
	 *
	 */
	@Override
	public void fill(Order order) {
		// TODO Auto-generated method stub
		OrderItemExample example=new OrderItemExample();
		example.createCriteria().andOidEqualTo(order.getId());
		example.setOrderByClause("id desc");
		List<OrderItem> ois=orderItemMapper.selectByExample(example);
		float total=0;//该订单的总金额
		int totalNumber=0;//该订单的总件数
		for(OrderItem oi:ois) {//为每个订单项设置Product、isReview逻辑属性
			Product p=productService.get(oi.getPid());
			oi.setProduct(p);
			//累加总金额，总件数。
			total+=oi.getNumber()*p.getPromotePrice();
			totalNumber+=oi.getNumber();
			//设置isReview
			checkIsReview(oi);
		}	
		order.setOrderItems(ois);
		order.setTotal(total);
		order.setTotalNumber(totalNumber);
	}

	@Override
	public int getSaleCount(int pid) {
		// TODO Auto-generated method stub
		OrderItemExample example=new OrderItemExample();
		example.createCriteria().andPidEqualTo(pid);
		List<OrderItem> ois=orderItemMapper.selectByExample(example);
		int SaleCount=0;
		for(OrderItem oi:ois) {
			SaleCount=oi.getNumber();
		}
		return SaleCount;
	}

	@Override
	public List<OrderItem> UserOrderItem(int uid) {
		// TODO Auto-generated method stub
		OrderItemExample example=new OrderItemExample();
		example.createCriteria().andUidEqualTo(uid);
		List<OrderItem> ois=orderItemMapper.selectByExample(example);
		for(OrderItem oi:ois) {
			Product p=productService.get(oi.getPid());
			oi.setProduct(p);
		}
		return ois;
	}

	@Override
	public OrderItem get(int pid, int oid,int uid) {
		// TODO Auto-generated method stub
		OrderItemExample example=new OrderItemExample();
		example.createCriteria().andPidEqualTo(pid).andOidEqualTo(oid).andUidEqualTo(uid);
		List<OrderItem> ois=orderItemMapper.selectByExample(example);
		Product p=productService.get(pid);
		ois.get(0).setProduct(p);//设置逻辑关系
		return ois.get(0);
	}

	/**fill(Order order)中调用本函数
	 * @Description 检查该订单项是否已评价，并设置isReview属性
	 */
	@Override
	public void checkIsReview(OrderItem oi) {
		// TODO Auto-generated method stub
		//pid,uid OrderItem Review
		//找到该订单项所对应的评价记录
		Review r=reviewService.get(oi.getPid(),oi.getUid());
		if(r==null) {//评价记录为空
			oi.setIsReview(false);
		}else {//有评价记录
			oi.setIsReview(true);
		}
	}

	/**
	 * 
	 * @param uid
	 * @return 设置订单项的产品逻辑关系,返回当前用户,oid为空的订单项集合。
	 */
	@Override
	public List<OrderItem> CartOrderItem(int uid) {
		// TODO Auto-generated method stub
		OrderItemExample example=new OrderItemExample();
		example.createCriteria().andUidEqualTo(uid).andOidIsNull();
		List<OrderItem> ois=orderItemMapper.selectByExample(example);
		for(OrderItem oi:ois) {
			Product p=productService.get(oi.getPid());
			oi.setProduct(p);
		}
		return ois;
	}
}
