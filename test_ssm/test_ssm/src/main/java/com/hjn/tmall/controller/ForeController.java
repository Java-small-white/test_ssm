package com.hjn.tmall.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.github.pagehelper.PageHelper;
import com.hjn.tmall.pojo.Category;
import com.hjn.tmall.pojo.Order;
import com.hjn.tmall.pojo.OrderItem;
import com.hjn.tmall.pojo.Product;
import com.hjn.tmall.pojo.ProductImage;
import com.hjn.tmall.pojo.PropertyValue;
import com.hjn.tmall.pojo.Review;
import com.hjn.tmall.pojo.User;
import com.hjn.tmall.service.CategoryService;
import com.hjn.tmall.service.OrderItemService;
import com.hjn.tmall.service.OrderService;
import com.hjn.tmall.service.ProductImageService;
import com.hjn.tmall.service.ProductService;
import com.hjn.tmall.service.PropertyValueService;
import com.hjn.tmall.service.ReviewService;
import com.hjn.tmall.service.UserService;

import comparator.DateSort;
import comparator.GeneralSort;
import comparator.PriceSort;
import comparator.ReviewSort;
import comparator.SaleSort;

/*
 *该控制器处理前台页面路径映射
 */
@Controller
public class ForeController {

	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	@Autowired
	UserService userService;
	@Autowired
	ProductImageService productImageService;
	@Autowired
	PropertyValueService propertyValueService;
	@Autowired
	ReviewService reviewService;
	@Autowired
	OrderItemService orderItemService;
	@Autowired
	OrderService orderService;
	//纵向导航栏：Category链接、推荐产品列表
	/*
	 * 
	 */
	@RequestMapping("forehome")
	public String home(Model model) {
		List<Category> cs=categoryService.list();
		productService.fill(cs);//为分类设置产品集合
		productService.fillRecommend(cs);//为分类设置推荐产品集合
		model.addAttribute("cs", cs);
		return "fore/home";
	}

	/*作用：注册用户。
	 *过程：1.通过参数User获取浏览器提交的账号密码
	 * 
	 */
	@RequestMapping("foreRegister")
	public String register(Model model,User user) {
		//判断用户是否已存在
		//用户名转义，防治SQL注入HtmlUtils.htmlEscape(user.getName());
		boolean exist=userService.isExist(user.getName());
		if(!exist) {//非空
			model.addAttribute("msg","用户名已存在");
			model.addAttribute("user", null);
			return "fore/register";
		}else {
			userService.add(user);
			return "redirect:/registerSuccessPage";
		}
	}

	@RequestMapping("forelogin")
	public String login(@RequestParam("name") String name, @RequestParam("password") String password,
			Model model,HttpSession session) {
		//通过name,password查询用户
		User user=userService.get(name, password);
		if(user==null) {
			model.addAttribute("msg", "账号密码错误");
			return "fore/login";
		}
		session.setAttribute("user", user);
		return "redirect:forehome";
	}

	@RequestMapping("forelogout")
	public String logout(HttpSession session){
		session.removeAttribute("user");
		return "redirect:forehome";
	}

	/**
	 * @Description 产品标题/图片跳转至此。负责跳转到产品页面，显示一个产品的信息。
	 * @param pid 
	 * @param model
	 * @return
	 */
	@RequestMapping("foreProduct")
	public String product(int pid,Model model) {
		Product p=productService.get(pid);
		List<ProductImage> singleImage=productImageService.list(pid, "type_single");
		List<ProductImage> detailImage=productImageService.list(pid, "type_detail");
		p.setProductSingleImages(singleImage);
		p.setProductDetailImages(detailImage);
		List<PropertyValue> pvs=propertyValueService.list(pid);
		List<Review> reviews=reviewService.list(pid);
		productService.setSaleAndReviewNumber(p);
		model.addAttribute("p", p);
		model.addAttribute("reviews", reviews);
		model.addAttribute("pvs",pvs);
		return "fore/product";
	}

	/**
	 * @Description 前台页面js代码异步提交至此.负责检查session中是否有User对象 
	 * @param session
	 * @return success 已登录 , fail 未登录 
	 *  
	 */
	@RequestMapping("foreCheckLogin")
	@ResponseBody
	public String checkLogin(HttpSession session) {
		User user=(User) session.getAttribute("user");
		if(user!=null) {
			System.out.println("----返回成功---");
			return "success";
		}
		System.out.println("----返回失败---");
		return "fail";
	}

	/**
	 * @Description 前台页面js异步提交至此.根据注入参数，调用服务层获取User对象
	 * @param name 用户名
	 * @param password 密码
	 * @param session 会话对象
	 * @return success 登录成功, fail 登录失败
	 */
	@RequestMapping("foreLoginAjax")
	@ResponseBody
	public String loginAjax(@RequestParam("name")String name,
			@RequestParam("password")String password,HttpSession session){
		User user=userService.get(name, password);
		if(user==null) {
			return "fail";
		}
		session.setAttribute("user", user);
		return "success";
	}

	@RequestMapping("foreCategory")
	public String category(int cid,String sort,Model model) {
		Category c=categoryService.get(cid);
		//为分类填充产品
		productService.fill(c);
		//为产品填充销量、评价数
		productService.setSaleAndReviewNumber(c.getProducts());
		if(sort!=null) {
			switch(sort) {
			case "general":Collections.sort(c.getProducts(), new GeneralSort());
			break;
			case "price":Collections.sort(c.getProducts(), new PriceSort());
			break;
			case "review":Collections.sort(c.getProducts(), new ReviewSort());
			break;
			case "sale":Collections.sort(c.getProducts(), new SaleSort());
			break;
			case "date":Collections.sort(c.getProducts(), new DateSort());
			break;
			}
		}
		model.addAttribute("c", c);
		return "fore/category";
	}
	
	@RequestMapping("foreSearch")
	public String search(String keyword,Model model) {
		PageHelper.startPage(0, 20);
		List<Product> ps=productService.search(keyword);
		productService.setSaleAndReviewNumber(ps);
		model.addAttribute("ps", ps);
		return "fore/searchReturn";
	}
	
	/**
	 * 立即购买按钮跳转至此.
	 * 负责生成订单项对象，跳转到foreConfirmOrder
	 */
	@RequestMapping("foreBuy")
	public String Buy(int pid,int num,HttpSession session,Model model) {
		//生成订单项对象，放在域里面传到下个控制器(填地址)。
		User user=(User)session.getAttribute("user");
		OrderItem oi=new OrderItem();
		oi.setPid(pid);
		oi.setUid(user.getId());
		oi.setNumber(num);
		oi.setProduct(productService.get(pid));
		
		orderItemService.add(oi);
		//上一行add()方法调用后，因为Mapper文件中设置了useGeneratedKeys，表示会把自动生成的主键返回
		int oiid=oi.getId();
		return "redirect:foreConfirmOrder?oiid="+oiid;
	}
	
	/**立即购买按钮->foreBuy->跳转至此.
	 * 购物车->选中订单项->结算跳转至此.
	 * @Description 接收订单项ID,返回订单项集合、订单项总价.跳转至确认订单页面fore/ConfirmOrder
	 */
	@RequestMapping("foreConfirmOrder")
	public String ConfirmOrder(String[] oiid,HttpSession session,Model model) {
		List<OrderItem> ois=new ArrayList<OrderItem>();
		float total=0;
		for(String oneOiid:oiid) {
			int orderItemId=Integer.parseInt(oneOiid);
			OrderItem  oi=orderItemService.get(orderItemId);
			ois.add(oi);
			total=total+oi.getNumber()*oi.getProduct().getPromotePrice();
		}
		session.setAttribute("ois", ois);
		model.addAttribute("total", total);
		return "fore/ConfirmOrder";
	}
	
	@RequestMapping("foreAddCart")
	@ResponseBody
	public String AddCart(int pid,int num,Model model,HttpSession session) {
		Product p=productService.get(pid);
		User user=(User) session.getAttribute("user");
		boolean exist=false;
		//查询该用户的所有订单项
		List<OrderItem> ois=orderItemService.UserOrderItem(user.getId());
		for(OrderItem oi:ois) {//遍历订单项，查找产品是否已在购物车中
			if(oi.getProduct().getId()==pid) {
				oi.setNumber(oi.getNumber()+num);
				orderItemService.update(oi);
				exist=true;
			}
		}
		if(!exist) {//不存在，则新增订单项.
			OrderItem oi=new OrderItem();
			oi.setPid(pid);
			
			oi.setUid(user.getId());
			oi.setNumber(num);
			orderItemService.add(oi);
		}
		return "success";
	}

	/**我的购物车按钮,负责查询该用户的所有在购物车的订单项(无oid的订单项表示在购物车中，用户未下单)
	 * 
	 */
	@RequestMapping("foreShowCart")
	public String showCart(Model model,HttpSession session) {
		User user=(User) session.getAttribute("user");
		List<OrderItem> ois=orderItemService.CartOrderItem(user.getId());
		model.addAttribute("ois", ois);
		return "fore/showCart";
	}
	
	/**购物车页面的修改订单项操作(增加、减少产品数量)。
	 * @param
	 */
	@RequestMapping("foreUpdateOI")
	@ResponseBody
	public String UpdateOI(int pid,int number,HttpSession session,Model model) {
		User user=(User) session.getAttribute("user");
		if(user==null) {
			return "fail";
		}
		List<OrderItem> ois=orderItemService.UserOrderItem(user.getId());
		for(OrderItem oi:ois) {
			if(oi.getProduct().getId().intValue()==pid) {
				oi.setNumber(number);
				orderItemService.update(oi);
				break;
			}
		}
		return "success";
	}
	
	/**
	 * 购物车页面，删除订单项。
	 */
	@RequestMapping("foreDeleteOI")
	@ResponseBody
	public String DeleteOI(int oiid,HttpSession session) {
		User user=(User) session.getAttribute("user");
		if(user==null) {
			return "fail";
		}

		
		orderItemService.delete(oiid);
		return "success";
	}
	/**提交订单按钮跳转到此.
	 * 负责创建订单。并重定向到forePay所指定的支付页面
	 */
	@RequestMapping("foreSubmitOrder")
	public String SubmitOrder(Order order,HttpSession session) {
		User user=(User)session.getAttribute("user");
		//订单号，由当前时间加上1000以内的数字生成。
		String orderCode=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		Random random=new Random(); 
		order.setOrderCode(orderCode+Integer.toString(random.nextInt(1000)));
		order.setCreateDate(new Date());
		order.setUid(user.getId());
		order.setStatus(OrderService.waitPay);
		//在foreConfirmOrder中，session存了订单项集合
		List<OrderItem> ois=(List<OrderItem>) session.getAttribute("ois");
		//将订单写入数据库，返回订单总价。
		float total=orderService.add(order, ois);
		return "redirect:forePay?oid="+order.getId()+"&total="+total;
	}
	
	/**
	 * @Description 支付页面->确认支付 跳转至此。负责更新订单(状态、支付时间)
	 * @return
	 */
	@RequestMapping("foreConfirmPay")
	public String ConfirmPay(int oid,float total,Model model) {
		Order order=orderService.get(oid);
		order.setStatus(orderService.waitDelivery);
		order.setPayDate(new Date());
		orderService.update(order);
		model.addAttribute("o", order);
		return "fore/paySuccess";
	}
	
	/**
	 * @Description 我的订单按钮、查看已买宝贝  跳转至此.负责调用Service获取当前用户的所有订单。
	 * @param model
	 * @param session
	 * @return 跳转到前台, 我的订单页面
	 */
	@RequestMapping("foreMyOrder")
	public String MyOrder(Model model,HttpSession session) {
		User user=(User)session.getAttribute("user");
		//返回订单状态不是delete的订单集合
		List<Order> os=orderService.list(user.getId(), OrderService.delete);
		//为订单设置订单项 逻辑关系
		orderItemService.fill(os);
		model.addAttribute("os", os);
		return "fore/myOrder";
	}
	
	/**我的订单->确认收货->跳转至此.
	 * @Description 负责为订单设置订单项、总价、总件数
	 * @return 跳转至前台,确认收货页面
	 */
	@RequestMapping("foreToConfirmReceipt")
	public String toConfirmReceipt(int oid,HttpSession session,Model model) {
		Order o=orderService.get(oid);
		orderItemService.fill(o);
		model.addAttribute("o", o);
		return "fore/confirmReceipt";
	}
	
	/**确认收货页面->确认收货->跳转至此.
	 * @Description 负责修改订单状态、收货时间,调用Service层更新记录。
	 * @param oid 订单ID
	 * @param session 会话对象
	 * @return 跳转至 ，收货成功页面
	 */
	@RequestMapping("foreConfirmReceipt")
	public String confirmReceipt(int oid,Model model) {
		Order o=orderService.get(oid);
		o.setConfirmDate(new Date());
		o.setStatus(orderService.waitReview);
		orderService.update(o);
		return "fore/receiptSuccess";
	}
	
	/**
	 * @Description myOrder.jsp页面，删除订单ajax请求到此控制器.
	 * @param oid
	 * @return 返回字符串success给myOrder.jsp
	 */
	//参数Model未加
	@RequestMapping("foreDeleteOrder")
	@ResponseBody
	public String deleteOrder(int oid) {
		Order o=orderService.get(oid);
		//考虑到订单这种业务数据，具有一定价值，可用作大数据分析，故不作删除。
		o.setStatus(OrderService.delete);
		orderService.update(o);
		return "success";
	}
	
	/**
	 * 我的订单页面->评价->跳转至此
	 * @Description 查询订单、产品，获取当前产品的所有评价。
	 * @return 跳转至评价页
	 */
	@RequestMapping("foreToReview")
	public String toReview(int oid,int pid,Model model) {
		Order o=orderService.get(oid);
		orderItemService.fill(o);
		OrderItem oi=orderItemService.get(pid, oid,o.getUid());
		oi.setIsReview(true);//表示已评价
		Product p=oi.getProduct();
		//获取当前产品的所有评价
		List<Review> reviews=reviewService.list(pid);
		productService.setSaleAndReviewNumber(p);
			
		model.addAttribute("o",o);
		//model.addAttribute("isReview", oi.getIsReview());
		model.addAttribute("p", p);
		model.addAttribute("reviews", reviews);
		return "fore/review";
	}
	
	/**
	 * 评价页面->提交评价按钮->跳转至此
	 * @Description 更新订单状态，生成review并写入数据库。
	 * @return 重定向到foreToReview控制器
	 */
	@RequestMapping("foreReview")
	public String review(@RequestParam("oid") int oid,@RequestParam("pid") int pid,String content,
			Model model,HttpSession session) {
		
		Order o=orderService.get(oid);
		orderItemService.fill(o);
		//判断是否所有订单项都评价了
		List<OrderItem> ois=o.getOrderItems();//获取当前订单的所有订单项
		User user=(User) session.getAttribute("user");
		int i=0;//表示订单中的 订单项已评价数量
		System.out.println("---ois的大小："+ois.size());
		for(OrderItem oi:ois) {//遍历订单项
			//判断该订单项是否评价了，在评价表中根据uid,订单项pid查询
			List<Review> rs=reviewService.list(user.getId(),oi.getPid());
			if(!rs.isEmpty()) {//能找到与(uid,pid)订单项相等的评价记录，表示已评价
				//评价了,i++;
				i++;
			}
		}
		if(i==ois.size()-1) {//表示还差一个才评价完，此时更新订单状态，否则不更新。
			o.setStatus(orderService.finish);
			orderService.update(o);
		}
		Product p=productService.get(pid);
		//content=HtmlUtils.htmlEscape(content);//转义
		
		Review r=new Review();//生成review对象
		r.setContent(content);
		r.setCreateDate(new Date());
		r.setPid(pid);
		r.setUid(user.getId());
		r.setUser(user);
		reviewService.add(r);//将review对象写入数据库
		
		//重定向到foreToReview控制器,显示评价
		return "redirect:foreToReview?oid="+oid+"&pid="+pid+"&isReview=true";
	}
}
