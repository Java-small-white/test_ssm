package com.hjn.tmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hjn.tmall.pojo.Product;
import com.hjn.tmall.pojo.PropertyValue;
import com.hjn.tmall.service.ProductService;
import com.hjn.tmall.service.PropertyValueService;

@Controller
@RequestMapping("")
public class PropertyValueController {
	//对于属性值，只有修改和更新的业务需求。不允许增加、删除属性值。
	@Autowired
	PropertyValueService propertyValueService;
	
	@Autowired
	ProductService productService;
	
	@RequestMapping("admin_propertyValue_edit")
	public String edit(Model model,int pid) {
		Product p=productService.get(pid);
		propertyValueService.init(p);//初始化该产品的属性值,第一次访问时，该产品的属性值是不存在的。
		List<PropertyValue> pvs=propertyValueService.list(p.getId());
		model.addAttribute("p", p);
		model.addAttribute("pvs", pvs);
		return "admin/editPropertyValue";
	}
	
	@RequestMapping("admin_propertyValue_update")
	@ResponseBody
	//@ResponseBody :返回的数据不是html标签的页面，而是其他某种格式的数据时（如json、xml等）使用.
	public String update(PropertyValue pv) {//更新一条属性值
		propertyValueService.update(pv);
		return "success";
	}
}
