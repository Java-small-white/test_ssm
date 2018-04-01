package com.hjn.tmall.service;

import java.util.List;

import com.hjn.tmall.pojo.Order;
import com.hjn.tmall.pojo.OrderItem;

public interface OrderService {
	String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";
    
    void add(Order o);
    void delete(int id);
    void update(Order o);
    Order get(int id);
    List<Order> list();
    float add(Order o,List<OrderItem> ois);
    /**
     * @param uid 用户Id
     * @param orderStatus 订单状态
     * @return 该用户指定状态除外的订单
     */
    List<Order> list(int uid,String orderStatus);
}
