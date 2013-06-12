<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/include.jsp" %>
<!--售后列表-->
<div class="searchForm">
    	<p class="left"><input class="searchInput" id="txtTradeId"  type="text"/></p>
        <div class="right" onclick="searchCustomerService('1');">
        	<div class="list" style="display:none"></div>
        </div>
        <div class="clear"></div>
    </div>
<div class="serviceList">
	<div class="serviceResultList" id="serviceResultList">
	
    </div>
</div>

<script>
function searchCustomerService(pageNo) {
	var type = $("#txtType").val();
	var priority = $("#txtPriority").val();
	$("#serviceResultList").html("<div class='tips_loading'>查询中...</div>");
	$("#serviceResultList").load("customerService/findDetail?wangwangNick=${nick}&sellerNick=" + getSeller() + "&buyerNick=" + getBuyer() + "&pageNo=" + pageNo + "&tradeId=" + $("#txtTradeId").val());
}
searchCustomerService('1');	
</script>