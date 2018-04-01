package com.hjn.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hjn.tmall.mapper.OrderMapper;
import com.hjn.tmall.pojo.Order;
import com.hjn.tmall.pojo.OrderExample;
import com.hjn.tmall.pojo.OrderItem;
import com.hjn.tmall.pojo.User;
import com.hjn.tmall.service.OrderItemService;
import com.hjn.tmall.service.OrderService;
import com.hjn.tmall.service.UserService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderMapper orderMapper;
	@Autowired
	UserService userService;
	@Autowired
	OrderItemService orderItemService;
	
	@Override
	public void add(Order o) {
		// TODO Auto-generated method stub
		orderMapper.insert(o);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		orderMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(Order o) {
		// TODO Auto-generated method stub
		orderMapper.updateByPrimaryKeySelective(o);
	}

	@Override
	public Order get(int id) {
		// TODO Auto-generated method stub	
		return orderMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Order> list() {
		// TODO Auto-generated method stub
		OrderExample example=new OrderExample();
		example.setOrderByClause(" id desc");
		List<Order> os=orderMapper.selectByExample(example);
		for(Order o:os) {
			User u=userService.get(o.getUid());
			o.setUser(u);
		}
		return os;
	}

	/**
	 * @Description 添加订单，更新订单项的oid,计算订单总价,用注解进行事务管理
	 * @return 订单总价
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public float add(Order o, List<OrderItem> ois) {
		// TODO Auto-generated method stub
		float total=0;
		add(o);
		//测试事务管理。
		//if(false) throw new RuntimeException();
		for(OrderItem oi:ois) {
			oi.setOid(o.getId());
			orderItemService.update(oi);
			total+=oi.getProduct().getPromotePrice()*oi.getNumber();
		}
		return total;
	}

	@Override
	public List<Order> list(int uid, String orderStatus) {
		// TODO Auto-generated method stub
		OrderExample example=new OrderExample();
		example.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(orderStatus);
		example.setOrderByClause("id desc");	
		return orderMapper.selectByExample(example);
	}

}
