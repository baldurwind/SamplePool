<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<!--搜索关键字-->
<c:choose>
	<c:when test="${not empty keywords}">
                <p class="ui-keywords">
                    	<c:forEach var="keyword" items="${keywords}">
              	      	<span>	${keyword[0] }</span></c:forEach>
                </p>
                <div class="clear"></div>
	</c:when>
	<c:otherwise><div class="tips_wujilu">当前暂无记录</div></c:otherwise>
</c:choose>
 
 
