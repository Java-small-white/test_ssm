<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script>
//对推荐产品做随机20%的概率高亮显示
$(function(){
    $("div.productsAsideCategorys div.row a").each(function(){
        var v = Math.round(Math.random() *5);
        if(v == 1)
            $(this).css("color","#87CEFA");
    });
});
 
</script>
<!-- 三层遍历：先取出分类->分类的推荐产品集合->产品的一段标题 -->
<c:forEach items="${cs}" var="c">
	<div cid="${c.id}" class="productsAsideCategorys">

		<c:forEach items="${c.productsRecommend}" var="ps">
			<div class="row show1">
				<c:forEach items="${ps}" var="p">
					<c:if test="${!empty p.subTitle}">
						<a href="foreProduct?pid=${p.id}"> <c:forEach
								items="${fn:split(p.subTitle, ' ')}" var="title" varStatus="st">
								<c:if test="${st.index==0}">
                                    ${title}
                                </c:if>
							</c:forEach>
						</a>
					</c:if>
				</c:forEach>
				<div class="seperator"></div>
			</div>
		</c:forEach>
	</div>
</c:forEach>
