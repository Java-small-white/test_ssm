<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@include file="../include/fore/header.jsp"%>
<%@include file="../include/fore/top.jsp"%>
<%@include file="../include/fore/search.jsp"%>



<title>模仿天猫官网 ${p.name}</title>

	<!--
<div class="categoryPictureInProductPageDiv">
    <img class="categoryPictureInProductPage" src="img/category/${p.category.id}.jpg">
</div> -->
	<div class="productPageDiv">

		<%@include file="../include/fore/product/ItemAndInfo.jsp"%>

		<%@include file="../include/fore/product/productReview.jsp"%>

		<%@include file="../include/fore/product/ItemContent.jsp"%>
	</div>

	<%@include file="../include/fore/footer.jsp"%>
