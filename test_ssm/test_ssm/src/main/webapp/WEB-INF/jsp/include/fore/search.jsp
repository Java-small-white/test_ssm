<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
 <!-- 搜索框页面 -->
    <a href="forehome">
        <img id="logo" src="img/site/logo.gif" class="logo">
    </a>
     
    <form action="foreSearch" method="post" >
        <div class="searchDiv">
            <input name="keyword" type="text" placeholder="时尚男鞋  太阳镜 ">
            <button  type="submit" class="searchButton">搜索</button>
            <div class="searchBelow">
                <c:forEach items="${cs}" var="c" varStatus="st">
                    <c:if test="${st.count>=1 and st.count<=4}">
                        <span>
                            <a href="foreCategory?cid=${c.id}">
                                ${c.name}
                            </a>
                            <c:if test="${st.count!=4}">             
                                <span>|</span>             
                            </c:if>
                        </span>          
                    </c:if>
                </c:forEach>     
            </div>
        </div>
    </form>  