<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<ul class="cp_parameter" >
<c:choose>
<c:when test="${empty  shops}">
<c:forEach var="shop" items="${shops}">
	 <li><span title="${shop}" class="icon-dpcx"></span></li> 
	</c:forEach>	            	
</c:when>

	
<c:when test="${empty  items}">
<c:forEach var="item" items="${items} ">
	<li><span title="${item}" class="icon-spcx"></span></li>
</c:forEach>
</c:when>	    
        	</c:choose>
</ul>
