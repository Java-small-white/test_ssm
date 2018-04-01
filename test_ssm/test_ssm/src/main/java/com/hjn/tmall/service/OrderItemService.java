package com.hjn.tmall.service;

import java.util.List;

import com.hjn.tmall.pojo.Order;
import com.hjn.tmall.pojo.OrderItem;

public interface OrderItemService {
	void add(OrderItem orderItem);
	void delete(int id);
	void update(OrderItem orderItem);
	OrderItem get(int id);
	
	/**
	 * 
	 * @param pid
	 * @param oid
	 * @param uid
	 * @return 返回唯一订单项
	 */
	OrderItem get(int pid,int oid,int uid);
	List list();
	
	/**调用fill(Order order);
	 * @Description 为订单设置订单项、总价、总件数;为订单项设置产品、isReview
	 * 
	 */
	void fill(List<Order> orders);
	
	/**
	 * @Description 为订单设置订单项、总价、总件数;为订单项设置产品、isReview
	 */
	void fill(Order order);
	int getSaleCount(int pid);
	List<OrderItem> UserOrderItem(int uid);
	
	/**fill(Order order)中调用本函数
	 * @Description 检查该订单项是否已评价，并设置isReview属性
	 */
	void checkIsReview(OrderItem oi);
	
	/**
	 * 
	 * @param uid
	 * @return 设置订单项的产品逻辑关系,返回当前用户,oid为空的订单项集合。
	 */
	List<OrderItem> CartOrderItem(int uid);
}
