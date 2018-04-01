package com.hjn.tmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjn.tmall.pojo.User;
import com.hjn.tmall.service.UserService;
import com.hjn.tmall.util.Page;

/*
 * 对于用户，只有查询的业务功能。
 * (1)增加用户，是在前台注册，取决于用户。
 * (2)删除用户，作为重要的业务信息，不建议删除。
 * (3)修改用户，这部分应将权利交给用户,如修改密码。
*/

@Controller
public class UserController {
	@Autowired
	UserService userService;
	
	@RequestMapping("admin_user_list")
	public String list(Model model,Page page) {
		/*pageHelper是Mybatis的分页插件。它工作在执行器和底层数据对象之间。
		 * 在查询的SQL语句执行之前，添加一行代码PageHelper.startPage(1, 10);
		 * 第一个参数表示第几页，第二个参数表示每页显示的记录数。
		 * 这样在执行sql后就会将记录按照语句中设置的那样进行分页。
		 * 如果需要获取总记录数的话，使用PageInfo类的对象可以获取总记录数，
		*/
		PageHelper.offsetPage(page.getStart(), page.getCount());
		List<User> us=userService.list();
		int total=(int)new PageInfo<>(us).getTotal();
		page.setTotal(total);
		model.addAttribute("us", us);
		model.addAttribute("page", page);
		return "admin/listUser";
	}
}
