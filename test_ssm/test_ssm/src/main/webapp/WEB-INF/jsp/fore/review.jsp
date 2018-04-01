<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
 
<%@include file="../include/fore/header.jsp"%>
<%@include file="../include/fore/top.jsp"%>
<%@include file="../include/fore/search.jsp"%>
<div class="reviewDiv">
    <div class="reviewProductInfoDiv">
    	<!-- 显示产品的第一张单独图片 -->
        <div class="reviewProductInfoImg"><img width="400px" height="400px" src="img/productSingle/${p.firstProductImage.id}.jpg"></div>
        <!-- 显示产品名字,价格、配送、月销量 -->
        <div class="reviewProductInfoRightDiv">
            <div class="reviewProductInfoRightText">
                ${p.name}
            </div>
            <!-- table显示价格、配送、月销量 -->
            <table class="reviewProductInfoTable">
                <tr>
                    <td width="75px">价格:</td>
                    <td><span class="reviewProductInfoTablePrice">￥<fmt:formatNumber type="number" value="${p.originalPrice}" minFractionDigits="2"/></span> 元 </td>
                </tr>
                <tr>
                    <td>配送</td>
                    <td>快递:  0.00</td>
                </tr>
                <tr>
                    <td>月销量:</td>
                    <td><span class="reviewProductInfoTableSellNumber">${p.saleCount}</span> 件</td>
                </tr>
            </table>
             <!-- 显示下单时间 -->
            <div class="reviewProductInfoRightBelowDiv">
                <span class="reviewProductInfoRightBelowImg"><img1 src="img/site/reviewLight.png"></span>
                <span class="reviewProductInfoRightBelowText" >现在查看的是 您所购买商品的信息于
                <fmt:formatDate value="${o.createDate}" pattern="yyyy年MM月dd hh:mm:ss"/>下单购买了此商品 </span>
            </div>
        </div>
        <div style="clear:both"></div>
    </div>
    
    <div class="reviewStasticsDiv">
    	<!-- 累计评价 -->
        <div class="reviewStasticsLeft">
                <div class="reviewStasticsLeftTop"></div>
                <div class="reviewStasticsLeftContent">累计评价 <span class="reviewStasticsNumber"> ${p.reviewCount}</span></div>
                <div class="reviewStasticsLeftFoot"></div>
        </div>
        <!--  -->
        <div class="reviewStasticsRight">
            <div class="reviewStasticsRightEmpty"></div>
            <div class="reviewStasticsFoot"></div>
        </div>
    </div>       
     
    <!--  -->
    <c:if test="${param.isReview==true}">
    <div class="reviewDivlistReviews">
        <c:forEach items="${reviews}" var="r">
            <div class="reviewDivlistReviewsEach">
                <div class="reviewDate"><fmt:formatDate value="${r.createDate}" pattern="yyyy-MM-dd"/></div>
                <div class="reviewContent">${r.content}</div>
                <div class="reviewUserInfo pull-right">${r.user.anonymousName}<span class="reviewUserInfoAnonymous">(匿名)</span></div>
            </div>
        </c:forEach>
    </div>
    </c:if>
     
     <!-- 显示填写评价栏 ,showonly没有控制器传过来,默认null-->
    <c:if test="${param.isReview!=true}">
        <div class="makeReviewDiv">
        <form method="post" action="foreReview">
            <div class="makeReviewText">其他买家，需要你的建议哦！</div>
            <table class="makeReviewTable">
                <tr>
                    <td class="makeReviewTableFirstTD">评价商品</td>
                    <td><textarea name="content"></textarea></td>
                </tr>
            </table>
            <!-- 2个隐藏域，o.id,p.id 以及 提交按钮-->
            <div class="makeReviewButtonDiv">
                <input type="hidden" name="oid" value="${o.id}">
                <input type="hidden" name="pid" value="${p.id}">
                <button type="submit">提交评价</button>
            </div>
        </form>
        </div>   
    </c:if>
</div>
<%@include file="../include/fore/footer.jsp"%>