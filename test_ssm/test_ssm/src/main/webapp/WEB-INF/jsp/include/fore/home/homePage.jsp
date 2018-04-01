<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

<title>模仿天猫官网</title>
 
<div class="homepageDiv">
 <!-- 首页业务需要的数据： 
 1.左侧导航栏显示所有分类、分类对应的推荐产品集合
 2.右侧4个分类链接
 3.图片轮播
 4.页面中间区域详细显示分类及分类所对应的5种产品,每种产品显示第一张图片、标题、价格等信息。
 若将这些需求都写在一个jsp中，会难以维护。
 
 categoryAndcarousel.jsp:负责1、2、3
 homepageCategoryProducts.jsp:负责4,即中间区域显示所有分类及每种分类对应的5个产品
 -->
    <%@include file="categoryAndcarousel.jsp"%>
    <%@include file="homepageCategoryProducts.jsp"%> 
</div>