package com.hjn.tmall.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjn.tmall.pojo.Order;
import com.hjn.tmall.pojo.OrderExample;
import com.hjn.tmall.service.OrderItemService;
import com.hjn.tmall.service.OrderService;
import com.hjn.tmall.util.Page;

/*
 * 对于订单，增加和删除只能由前台完成。
 * 后台只有查询的业务需求以及模拟发货操作。
 */

@Controller
public class OrderController {
	@Autowired
	OrderService orderService;
	@Autowired
	OrderItemService orderItemService;
	
	@RequestMapping("admin_order_list")
	public String list(Model model,Page page) {
		PageHelper.offsetPage(page.getStart(), page.getCount());
		List<Order> os=orderService.list();
		int total=(int) new PageInfo<Order>(os).getTotal();
		page.setTotal(total);
		orderItemService.fill(os);//为所有订单项填充总金额，件数，逻辑关系（订单项和产品）
		
		model.addAttribute("os", os);
		model.addAttribute("page", page);
		return "admin/listOrder";
	}
	
	/*
	 * 当订单状态是waitDelivery的时候，就会出现发货按钮
	 * 1. 发货按钮链接跳转到admin_order_delivery
	 * 2. OrderController.delivery()方法被调用
	 * 2.1 注入订单对象
	 * 2.2 修改发货时间，设置发货状态
	 * 2.3 更新到数据库
	 * 2.4 客户端跳转到admin_order_list页面
	 */
	@RequestMapping("admin_order_delivery")
	public String deliver(Order o) {
		o.setDeliveryDate(new Date());
		o.setStatus(orderService.waitConfirm);//修改订单状态：待收货
		orderService.update(o);
		return "redirect:admin_order_list";
	}
}
