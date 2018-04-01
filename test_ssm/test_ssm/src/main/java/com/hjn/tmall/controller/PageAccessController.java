package com.hjn.tmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**前台页面register.jsp在WEB-INF目录下，不能通过浏览器直接跳转
 * 该控制器专门做跳转需求
 * @author 小白
 *
 */
@Controller
public class PageAccessController {
	
	/**
	 * @return 跳转至前台注册页面
	 */
	@RequestMapping("registerPage")
	public String registerPage() {
		return "fore/register";
	}
	/**
	 * @return 跳转至注册成功页面
	 */
	@RequestMapping("registerSuccessPage")
	public String registerSuccessPage() {
		return "include/fore/registerSuccess";
	}
	/**
	 * @return 跳转至登录页面
	 */
	@RequestMapping("loginPage")
	public String loginPage() {
		return "fore/login";
	}
	/**
	 * @return 跳转至支付页面
	 */
	@RequestMapping("forePay")
	public String pay() {
		return "fore/pay";
	}
}
