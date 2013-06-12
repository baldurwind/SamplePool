<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../common/include.jsp"%>
<%@ include file="../../common/header.jsp"%>
<html>
<head>
<title>coupon</title>
</head>
<body>
	<!--导航 -->
	<div class="wrapper">
		<!--店内优惠券-->
		<div class="behavior_coupon" id="behavior_coupon">
			<div class="usableList">
				<h1 class="childModuleTit youhuiTitle">
					可用优惠券
				</h1>
				<div id="div_buyer_coupon">
        		
        		</div>
			</div>
			<div class="presentList" id="persentList">
	    
			</div>
		</div>
		<!--end_店内优惠券-->
	</div>
</body>

<script type="text/javascript">
	$(document).ready(function(){
		 $("#div_buyer_coupon").html("<div class='tips_loading'>查询中...</div>");
		 $("#div_buyer_coupon").load("marketing/coupon/buyer?sellernick=" + getSeller() + "&buyernick=" + getBuyer());
		 $("#persentList").html("<div class='tips_loading'>查询中...</div>");
		 $("#persentList").load("marketing/coupon/seller?sellernick=" + getSeller());
		 $("#behavior_setmeal,#behavior_panicbuying").find('a.xq').click(function(){
		 	 $(this).parent('td').parent('tr').next('tr').eq(0).children('td').toggle();
		 });
		 $("#behavior_setmeal ul.tclist li").children('div.img').hover(function(){
		 	$(this).addClass('hover');
		 },function(){
		 	$(this).removeClass('hover');
		 });
		 $("#usableList .close").click(function(){
		  	$("#usableList").hide();
		  });
	});
	
	function showBuyerCouponList(page) {
		$("#div_buyer_coupon").html("<div class='tips_loading'>查询中...</div>");	
		$("#div_buyer_coupon").load("marketing/coupon/buyer?page=" + page + "&sellernick=" + getSeller() + "&buyernick=" + getBuyer());
	}
</script>
</html>