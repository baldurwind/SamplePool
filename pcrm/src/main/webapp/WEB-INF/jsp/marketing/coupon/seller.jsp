<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="../../common/include.jsp" %>
 <h1 class="childModuleTit childModuleTit-lovly">优惠券</h1>
 <input id="ww_msg_plugin" type="hidden" message="" value="点击演示在C++中处理按钮的onclick事件"/> 
 <table width="100%" border="0" cellspacing="0" cellpadding="0">
	<c:choose>
		<c:when test="${coupon_seller == null}">
			<tr><td colspan="4"><div class="tips_wujilu">当前暂无记录</div></td></tr>
		</c:when>
		<c:otherwise>
				<c:forEach var="obj" items="${coupon_seller}">
					<tr>
	                   <td class="bold" width="20%"><span class="thisColor">${obj[5]/100}元</span>代金券</td>
	                   <td  width="20%">满${obj[6]/100}可用</td>
	                   <td  width="40%">有效期&nbsp;&nbsp;&nbsp;&nbsp;<fmt:parseDate value="${obj[7]}" var="date" pattern="yyyy-MM-dd"></fmt:parseDate><fmt:formatDate value="${date}"  type="date" dateStyle="long"/></td>
	                   <td><div class="publicThemeBtn send" onclick="ww_msg_send('${obj[5]/100}元代金券　满${obj[6]/100}可用　${obj[1]}');">赠送</div></td>
	                </tr>
				</c:forEach>
		</c:otherwise>
	</c:choose>
</table>

<script>
	$(function(){
		//table悬浮效果
		$("table tr").hover(function(){$(this).toggleClass("publicHoverBgColor");});
	});
</script>