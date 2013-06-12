<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<c:choose>
<c:when test="${empty member }">
<div class="tips_wujilu">当前暂无记录</div>
</c:when>
<c:otherwise>
<!--状态-->
<div class="index_status" id="member_status">
	<ul>
      <li class="thisColor">等待发货（<a href='javascript:queryTradeByStatus("WAIT_SELLER_SEND_GOODS")'>${waitingSendGoodsTradeList}</a>）</li>
      <li class="thisColor">申请退款（<a href='javascript:queryTradeWithRefund()'>${refundOidList}</a>）</li>
      <li class="thisColor">售后处理中（<a href="javascript:queryAfterService()">${csi}</a>）</li>
    </ul>
    <div class="clear"></div>
</div>

<div  class="index_tradeData" id="crm_member">
<div class="index_tradeData_top">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="30">本店消费次数</td>
        <td>总消费金额 </td>
        <td>平均客单价</td>
        <td>上次消费</td>
      </tr>
      <tr>
         <td class="shuju">${ member.tradeCount }</td>
        <td class="shuju">${member.tradeAmount }</td>
        <td class="shuju">
         <c:if test="${member.tradeCount==0}">0</c:if>
         <c:if test="${member.tradeCount!=0}"> <fmt:formatNumber value="${member.tradeAmount/member.tradeCount}" pattern="0.00"/> </c:if>
       
        </td>
        <td class="shuju nobored"><fmt:formatDate value="${member.lastTradeTime}" pattern="yyyy-M-d"/></td>
      </tr>
  </table>
</div>
</div>


</c:otherwise>
</c:choose>










