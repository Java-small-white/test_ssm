<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%@include file="../include/fore/header.jsp"%>
<%@include file="../include/fore/top.jsp"%>
<%@include file="../include/fore/search.jsp"%>
<script>
var deleteOrder = false;
var deleteOrderid = 0;
 
$(function(){
	//所有带有orderStatus的a元素 点击事件
	//即5个超链接： 所有订单、待付款、待发货、待收货、待评价
    $("a[orderStatus]").click(function(){
        var orderStatus = $(this).attr("orderStatus");
        if('all'==orderStatus){
        	//显示所有table
            $("table[orderStatus]").show();
        }
        else{
        	//隐藏table
            $("table[orderStatus]").hide();
            //显示指定订单状态的table
        	$("table[orderStatus="+orderStatus+"]").show();        
        }
        //移除orderType下所有div的class
        $("div.orderType div").removeClass("selectedOrderType");
        //为当前选择器的div添加class(红色下划线)
        $(this).parent("div").addClass("selectedOrderType");
    });
     
    //删除图标点击事件
    $("a.deleteOrderLink").click(function(){
        deleteOrderid = $(this).attr("oid");
        deleteOrder = false;
        //显示delete模态框
        $("#deleteConfirmModal").modal("show");
    });
     
    //delete模态框中的确认按钮 点击事件 
    $("button.deleteConfirmButton").click(function(){
        deleteOrder = true;
        $("#deleteConfirmModal").modal('hide');
    });
     
    //delete模态框  bootstarp模态框隐藏事件监听
    $('#deleteConfirmModal').on('hidden.bs.modal', function (e) {
        if(deleteOrder){
            var page="foreDeleteOrder";
            $.post(
                    page,
                    {"oid":deleteOrderid},
                    function(result){
                        if("success"==result){
                        	//隐藏当前订单
                            $("table.orderListItemTable[oid="+deleteOrderid+"]").hide();
                        }
                        else{
                            location.href="login.jsp";
                        }
                    }
                );      
        }
    })     
     
    $(".ask2delivery").click(function(){
        var link = $(this).attr("link");
        $(this).hide();
        page = link;
        $.ajax({
               url: page,
               success: function(result){
                alert("卖家已秒发，刷新当前页面，即可进行确认收货")
               }
            });
         
    });
});
 
</script>
<title>我的订单页</title>
<div class="boughtDiv">
	<!-- 订单状态 -->
	<div class="orderType">
		<div class="selectedOrderType">
			<a orderStatus="all" href="#nowhere">所有订单</a>
		</div>
		<div>
			<a orderStatus="waitPay" href="#nowhere">待付款</a>
		</div>
		<div>
			<a orderStatus="waitDelivery" href="#nowhere">待发货</a>
		</div>
		<div>
			<a orderStatus="waitConfirm" href="#nowhere">待收货</a>
		</div>
		<div>
			<a orderStatus="waitReview" href="#nowhere" class="noRightborder">待评价</a>
		</div>
		<div class="orderTypeLastOne">
			<a class="noRightborder"> </a>
		</div>
	</div>
	<div style="clear: both"></div>
	
	<!--用table显示信息： 宝贝、单价、数量、实付款、交易操作-->
	<div class="orderListTitle">
		<table class="orderListTitleTable">
			<tr>
				<td>宝贝</td>
				<td width="100px">单价</td>
				<td width="100px">数量</td>
				<td width="120px">实付款</td>
				<td width="100px">交易操作</td>
			</tr>
		</table>
	</div>
	
	<!-- 下面显示订单信息 -->
	<div class="orderListItem">
	<!-- 遍历订单集合 -->
		<c:forEach items="${os}" var="o">
			<table class="orderListItemTable" orderStatus="${o.status}"
				oid="${o.id}">
				<!-- 显示时间、订单号，天猫商场、和我联系、删除图标 -->
				<tr class="orderListItemFirstTR">
					<td colspan="2"><b><fmt:formatDate value="${o.createDate}"
								pattern="yyyy-MM-dd HH:mm:ss" /></b> <span>订单号:
							${o.orderCode} </span></td>
					<td colspan="2"><img width="13px"
						src="img/site/orderItemTmall.png">天猫商场</td>
					<td colspan="1"><a class="wangwanglink" href="#nowhere">
							<div class="orderItemWangWangGif"></div>
					</a></td>
					<td class="orderItemDeleteTD"><a class="deleteOrderLink"
						oid="${o.id}" href="#nowhere"> <span
							class="orderListItemDelete glyphicon glyphicon-trash"></span>
					</a></td>
				</tr>
				<!-- 遍历订单项 -->
				<c:forEach items="${o.orderItems}" var="oi" varStatus="st">
					<tr class="orderItemProductInfoPartTR">
						<!-- 显示产品图片 -->
						<td class="orderItemProductInfoPartTD"><img width="80"
							height="80"
							src="img/productSingle_middle/${oi.product.firstProductImage.id}.jpg"></td>
						<!-- 显示产品名字，3个小图标 -->
						<td class="orderItemProductInfoPartTD">
							<div class="orderListItemProductLinkOutDiv">
								<a href="foreProduct?pid=${oi.product.id}">${oi.product.name}</a>
								<div class="orderListItemProductLinkInnerDiv">
									<img src="img/site/creditcard.png" title="支持信用卡支付"> <img
										src="img/site/7day.png" title="消费者保障服务,承诺7天退货"> <img
										src="img/site/promise.png" title="消费者保障服务,承诺如实描述">
								</div>
							</div>
						</td>
						<!-- 原价、促销价 -->
						<td class="orderItemProductInfoPartTD" width="100px">

							<div class="orderListItemProductOriginalPrice">
								￥
								<fmt:formatNumber type="number"
									value="${oi.product.originalPrice}" minFractionDigits="2" />
							</div>
							<div class="orderListItemProductPrice">
								￥
								<fmt:formatNumber type="number"
									value="${oi.product.promotePrice}" minFractionDigits="2" />
							</div>

						</td>
						<!-- 显示订单项的产品数量 -->
						<td valign="top" class="orderListItemNumberTD orderItemOrderInfoPartTD"
							width="100px"><span class="orderListItemNumber">${oi.number}</span>
						</td>
						<!-- 当遍历到第一个订单项时，显示付款金额、交易操作 (除评价外)
						因为这2列只需要每一个订单有显示一次即可。不需要每个订单项重复显示。
						-->
						<c:if test="${st.count==1}">
							
							<!-- 显示订单总价 -->
							<td valign="top" rowspan="${fn:length(o.orderItems)}"
								width="120px"
								class="orderListItemProductRealPriceTD orderItemOrderInfoPartTD">
								<div class="orderListItemProductRealPrice">
									￥
									<fmt:formatNumber minFractionDigits="2" maxFractionDigits="2"
										type="number" value="${o.total}" />
								</div>
								<div class="orderListItemPriceWithTransport">(含运费：￥0.00)</div>
							</td>
							<!-- 显示交易操作,jstl标签c:if动态显示对应按钮 -->
							<!--  -->
							<c:if test="${o.status!='waitReview' }">
							<td valign="top" rowspan="${fn:length(o.orderItems)}"
							class="orderListItemButtonTD orderItemOrderInfoPartTD"
								width="100px"><c:if test="${o.status=='waitConfirm' }">
									<a href="foreToConfirmReceipt?oid=${o.id}">
										<button class="orderListItemConfirm">确认收货</button>
									</a>
								</c:if> <c:if test="${o.status=='waitPay' }">
									<a href="forePay?oid=${o.id}&total=${o.total}">
										<button class="orderListItemConfirm">付款</button>
									</a>
								</c:if> <c:if test="${o.status=='waitDelivery' }">
									<span>待发货</span>
									<%--                                     <button class="btn btn-info btn-sm ask2delivery" link="admin_order_delivery?id=${o.id}">催卖家发货</button> --%>

								</c:if> 
								<!--  
								<c:if test="${o.status=='waitReview' }">
									<a href="foreReview?oid=${o.id}&pid=${oi.pid}">
										<button class="orderListItemReview">评价</button>
									</a>
								</c:if> -->
							</td>
							</c:if>
						</c:if>
						
						<!-- 每个订单项需要一个评价按钮 ，其余种类按钮共享一个-->
						<!-- 只有该订单的所有订单项都评价完，订单状态才会改变。-->
						<c:if test="${o.status=='waitReview'}">
							
							<td valign="top" rowspan="1" class="orderListItemButtonTD orderItemOrderInfoPartTD">
								<c:if test="${oi.isReview==false}">
									<a href="foreToReview?oid=${o.id}&pid=${oi.pid}">
										<button class="orderListItemReview">评价</button>
									</a>
								</c:if>
							</td>
						</c:if>
					</tr>
				</c:forEach>
			</table>
		</c:forEach>
	</div>
</div>
<%@include file="../include/fore/footer.jsp"%>