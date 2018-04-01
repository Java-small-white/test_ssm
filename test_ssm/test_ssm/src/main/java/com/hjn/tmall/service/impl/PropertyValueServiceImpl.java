package com.hjn.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjn.tmall.mapper.PropertyValueMapper;
import com.hjn.tmall.pojo.Product;
import com.hjn.tmall.pojo.Property;
import com.hjn.tmall.pojo.PropertyValue;
import com.hjn.tmall.pojo.PropertyValueExample;
import com.hjn.tmall.service.PropertyService;
import com.hjn.tmall.service.PropertyValueService;

@Service
public class PropertyValueServiceImpl implements PropertyValueService{
	
	@Autowired
	PropertyValueMapper propertyValueMapper;
	
	@Autowired
	PropertyService propertyService;
	
	@Override
	public void init(Product p) {  //初始化PropertyValue
		// TODO Auto-generated method stub
		//获取分类下的所有属性
		List<Property> pts=propertyService.list(p.getCid());
		for(Property pt:pts) {
			//根据属性id和产品id查询属性值
			PropertyValue pv=get(pt.getId(),p.getId());
			if(pv==null) {//数据库中不存在，则初始化属性值。
				pv=new PropertyValue();
				pv.setPid(p.getId());
				pv.setPtid(pt.getId());
				propertyValueMapper.insert(pv);//将属性值插入到数据库中。
			}
		}
	}

	@Override
	public void update(PropertyValue pv) {
		// TODO Auto-generated method stub
		propertyValueMapper.updateByPrimaryKeySelective(pv);
	}

	@Override
	public PropertyValue get(int ptid, int pid) {//返回该产品的一个指定属性值
		// TODO Auto-generated method stub
		PropertyValueExample example = new PropertyValueExample();
		example.createCriteria().andPtidEqualTo(ptid).andPidEqualTo(pid);
		List<PropertyValue> pvs=propertyValueMapper.selectByExample(example);
		if(pvs.isEmpty()) {
			return null;
		}
		return pvs.get(0);
	}

	@Override
	public List<PropertyValue> list(int pid) {//返回该产品的所有属性值
		// TODO Auto-generated method stub
		PropertyValueExample example=new PropertyValueExample();
		example.createCriteria().andPidEqualTo(pid);
		List<PropertyValue> pvs=propertyValueMapper.selectByExample(example);
		for(PropertyValue pv:pvs) {//为所有属性值设置逻辑(一对多)关联
			Property property=propertyService.get(pv.getPtid());
			pv.setProperty(property);
		}
		return pvs;
	}

}
