<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<c:choose>
<c:when test="${empty shipping}">
	<div class='tips_wujilu'>${msg}</div></c:when>
<c:otherwise>
	${shipping.receiverName}, ${shipping.receiverMobile},${shipping.receiverPhone} , ${shipping.location.state} ${shipping.location.city} ${shipping.location.district}  ${shipping.location.address}, ${shipping.location.zip}
</c:otherwise>
</c:choose> 
	
