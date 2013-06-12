<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<input id="current_page_limitdiscount" type="hidden"  value="${current_page_limitdiscount}"/>
<input id="ww_msg_plugin" type="hidden" value="" />
<c:if test="${empty  limitDiscounts.pageList}"><div class="tips_wujilu">当前暂无记录</div></c:if>
<c:if test="${not empty  limitDiscounts.pageList}"> 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr><td width="25%" align="center">名称</td><td width="15%" align="center">数量</td><td width="25%"  align="center">开始时间</td>
<td width="25%"  align="center">结束时间</td><td    align="center">操作</td>
</tr>
<c:forEach  var="obj" items="${limitDiscounts.pageList }">
	<tr>
		<td>${obj.name}</td>
		<td align="center"><span class="price">${obj.itemNum }</span></td>
		<td align="center"><span class="time"><fmt:formatDate value="${obj.startTime }" pattern="yy-M-d HH:mm"/></span></td>
		<td align="center"><span class="time"><fmt:formatDate value="${obj.endTime }" pattern="yy-M-d HH:mm"/></span></td>
		<td align="center"><a href="javascript:limitDiscountDetail(${obj.limitDiscountId})" class="xq">详情</a></td></tr>
	<tr>
		<td  id="limitdiscount_detail_module_${obj.limitDiscountId}" colspan="5" class="info"></td></tr>
  </c:forEach>            
              
 <tr>
 <td colspan="5" align="center">
<div class="ui-pager">
	<ul>
		<li class="ui-pager-step">第${limitDiscounts.page+1}页</li>
		<li class="ui-pager-count">共${limitDiscounts.pageCount }页</li>
		<c:if test="${limitDiscounts.firstPage}"><li class="ui-pager-firstPage-disable"></li>  <li class="ui-pager-prePage-disable"></li></c:if>
		<c:if test="${!limitDiscounts.firstPage}"><li class="ui-pager-firstPage" onclick="limitDiscountPage('first')"></li>  <li class="ui-pager-prePage" onclick="limitDiscountPage('previous')"></li></c:if>
		<c:if test="${!limitDiscounts.lastPage}"><li class="ui-pager-nextPage"  onclick="limitDiscountPage('next')"></li>  <li class="ui-pager-lastPage"  onclick="limitDiscountPage('last')"></li></c:if>
		<c:if test="${limitDiscounts.lastPage}"><li class="ui-pager-nextPage-disable"></li>  <li class="ui-pager-lastPage-disable"></li></c:if>
		<div class="clear"></div>
	</ul>
<div class="clear"></div>
</div>
	</td>
 
</tr>

</table>
</c:if>
<script>
$("#behavior_panicbuying").find('a.xq').click(function(){
	 $(this).parent('td').parent('tr').next('tr').eq(0).children('td').toggle();
});

</script>
