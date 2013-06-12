<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../common/include.jsp"%>
<div class="pubOrderDetial">
 <div class="topInfo">
     <h4 class="block FB">${customerServiceIssuesDetail[3]}</h4> 
     <p class="section">
         <strong>订单ID：</strong><span class="thisColor">${customerServiceIssuesDetail[0]}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <strong>类型：</strong>${customerServiceIssuesDetail[1]}
     </p>
     <p class="section"><strong>状态：</strong>${customerServiceIssuesDetail[7]}
     	 <c:choose>
			<c:when test="${customerServiceIssuesDetail[9] == 1}">
				<i class="greenico"></i>
			</c:when>
			<c:when test="${customerServiceIssuesDetail[9] == 2}">
				<i class="yellowico"></i>
			</c:when>
			<c:when test="${customerServiceIssuesDetail[9] == 3}">
				<i class="redico"></i>
			</c:when>
		</c:choose>
     </p>	
 </div>
<div class="bottomTable">
	<h4 class="block FB">问题描述：</h4>
	 <p class="problemDetial">
		${customerServiceIssuesDetail[4]}&nbsp;
       	<c:if test="${customerServiceIssuesDetail[6] != null && customerServiceIssuesDetail[6] != ''}">
       		<a href="customerService/downloadFile?fileName=${customerServiceIssuesDetail[6]}"><img src="${bathPath}resource/images/uploader.gif" alt="附件" /></a>
       	</c:if>
	</p>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="order_mianban_table">
		<tr class="head">
			<td width="18%" height="20" align="center" class="1"><strong>处理人</strong></td>
			<td width="24%" align="center"><strong>结果</strong></td>
			<td width="58%" align="center"><strong>备注</strong></td>
		</tr>
		
		<c:forEach items="${csiDetailList}" var="detail" varStatus="idx">	
			<tr>
				<td  class="l">${detail[2]}&nbsp;</td>
				<td><span class="FL">${detail[3]}</span>
				<c:choose>
					<c:when test="${detail[8] == 1}">
						<i class="FR greenico"></i>
					</c:when>
					<c:when test="${detail[8] == 2}">
						<i class="FR yellowico"></i>
					</c:when>
					<c:when test="${detail[8] == 3}">
						<i class="FR redico"></i>
					</c:when>
				</c:choose>
				</td>
				<td>
				${detail[4]}
					<c:if test="${detail[5] != null}">
						<a href="customerService/downloadFile?fileName=${detail[5]}"><img src="${bathPath}resource/images/uploader.gif" /></a>
					</c:if>
					&nbsp;&nbsp;
				</td>
			</tr>
 		</c:forEach>
	</table>
	<div class="clear"></div>
</div>
</div>