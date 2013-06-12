<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
 <!-- //num_iid numIid,title,price,pic_url picUrl -->
 <c:forEach var="item" items="${meals_detail }">
 	<li class="main">
	<div class="img">
	<img  src="${item.picUrl}" title="${item.title}" width="70" height="70"/></div>
	</li>
 </c:forEach>

