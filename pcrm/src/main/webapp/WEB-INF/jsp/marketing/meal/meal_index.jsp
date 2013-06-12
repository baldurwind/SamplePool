<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<input id="current_page_meal"  type="hidden"  value="${current_page_meal}"/>
<input id="ww_msg_plugin" type="hidden" value="" />
<c:if test="${empty  meals.pageList}"><div class="tips_wujilu">当前暂无记录</div></c:if>
<c:if test="${not empty  meals.pageList}"> 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr class="FB"><td width="50%" align="center">名称</td><td align="center">价格</td><td align="center">操作</td></tr>
<c:forEach var="meal" items="${meals.pageList}">  
	<tr>
		<td>${meal.mealName }</td>
		<td align="center"><span class="price">${meal.mealPrice/100 }</span></td>
		<td align="center"><a href="javascript:mealDetail(${meal.mealId})" class="xq">详情</a>
		<a href='javascript:ww_msg_send("http://item.taobao.com/meal_detail.htm?meal_id=${meal.mealId}")'>发送</a></td></tr>
	<tr >
		<td colspan="3" class="info">
			<div class="tcinfo">
				<ul class="tclist" id="meal_detail_module_${meal.mealId}"></ul></div></td></tr>
</c:forEach>
<tr>
<td colspan="3" align="center">
<div class="ui-pager">
	<ul>
		<li class="ui-pager-step">第${meals.page+1}页</li>
		<li class="ui-pager-count">共${meals.pageCount }页</li>
		<c:if test="${meals.firstPage}"><li class="ui-pager-firstPage-disable"></li>  <li class="ui-pager-prePage-disable"></li></c:if>
		<c:if test="${!meals.firstPage}"><li class="ui-pager-firstPage" onclick="mealPage('first')"></li>  <li class="ui-pager-prePage" onclick="mealPage('previous')"></li></c:if>
		<c:if test="${!meals.lastPage}"><li class="ui-pager-nextPage"  onclick="mealPage('next')"></li>  <li class="ui-pager-lastPage"  onclick="mealPage('last')"></li></c:if>
		<c:if test="${meals.lastPage}"><li class="ui-pager-nextPage-disable"></li>  <li class="ui-pager-lastPage-disable"></li></c:if>
		<div class="clear"></div>
	</ul>
<div class="clear"></div>
</div>
	</td></tr>
</c:if>
</table>
<script>
$("#behavior_setmeal").find('a.xq').click(function(){
	 $(this).parent('td').parent('tr').next('tr').eq(0).children('td').toggle();
})
</script>
