<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<script>
	$(function() {
		$("#MyOrder").click(function() {
			var page = "foreCheckLogin";
			$.get(page, function(result) {
				if ("success" == result) {
					location.href = "foreMyOrder";
				} else {
					$("#loginModal").modal('show');
				}
			});
		});
		
		$("#ShopCart").click(function() {
			var page = "foreCheckLogin";
			$.get(page, function(result) {
				if ("success" == result) {
					location.href = "foreShowCart";
				} else {
					$("#loginModal").modal('show');
				}
			});
		});
		
		$("button.loginSubmitButton").click(function() {
			var name = $("#name").val();
			var password = $("#password").val();

			if (0 == name.length || 0 == password.length) {
				$("span.errorMessage").html("请输入账号密码");
				$("div.loginErrorMessageDiv").show();
				return false;
			}

			var page = "foreLoginAjax";
			$.get(page, {
				"name" : name,
				"password" : password
			}, function(result) {
				console.log(result);
				if ("success" == result) {
					
					location.reload();
				} else {
					$("span.errorMessage").html("账号密码错误");
					$("div.loginErrorMessageDiv").show();
				}
			});

			return true;
		});
	});
</script>
<nav class="top ">
	<a href="${contextPath}"> <span style="color: #C40000; margin: 0px"
		class=" glyphicon glyphicon-home redColor"></span> 天猫首页
	</a> <span>喵，欢迎来天猫</span>

	<c:if test="${!empty user}">
		<a href="loginPage">${user.name}</a>
		<a href="forelogout">退出</a>
	</c:if>

	<c:if test="${empty user}">
		<a href="loginPage">请登录</a>
		<a href="registerPage">免费注册</a>
	</c:if>


	<span class="pull-right"> 
	<a id="MyOrder">我的订单</a> 
	<a	id="ShopCart"> <span
			style="color: #C40000; margin: 0px"
			class=" glyphicon glyphicon-shopping-cart redColor"></span> 购物车<strong>${cartTotalItemNumber}</strong>件
	</a>
	</span>


</nav>



