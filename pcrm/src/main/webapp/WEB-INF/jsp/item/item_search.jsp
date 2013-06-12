<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../common/include.jsp" %>
<%@ include file="../common/header.jsp"%>
<title>search</title>
</head>
<body>
	<input id="ww_msg_plugin" type="hidden" message="" value="点击演示在C++中处理按钮的onclick事件"/> 
	<!--导航 -->
	<input type="hidden" id="txtP" value="-1"/>
	<!--搜索-->
	<div class="wrapper">  
	<div class="item_search" id="lead_remark">
	 	<div class="searchForm">
	    	<p class="left"><input class="searchInput" id="txtTitle"  type="text"/></p>
	        <div class="right" onclick="searchClick();">
	        	<div class="list" style="display:none"></div>
	        </div>
	        <div class="clear"></div>
	    </div>
	    <div class="resultList">
	    	 <ul class="rt_nav" id="searchWay">
	         	<!-- <li><a href="javascript:void(0);" class="default">按销量</a></li> -->
	            <li id="newSpecies" onclick="newSpecies();"><a href="javascript:void(0);" class="default">按新品</a></li>
	            <li id="price" onclick="price();"><a href="javascript:void(0);" class="default">按价格</a></li>
	            <!-- <li><a href="javascript:void(0);" class="default">按收藏</a></li> -->
	             <div style="clear:both"></div>
	         </ul>
	         <div id="searchResultList">
	         	
           	</div>
        </div>
	</div>
	</div>
	<!--工作台 -->
<script type="text/javascript">
	function search(page) {
		var title = $("#txtTitle").val();
		var type = "title";
		var p = $("#txtP").val();
		$("#searchResultList").html("<div class='tips_loading'>查询中...</div>");
		$("#searchResultList").load("item/search?title=" +  encodeURIComponent(title) + "&sellerNick=" + getSeller() + "&type=" + type + "&p=" + p + "&pageNo=" + page);
	}
	
	function searchClick() {
		$("#txtP").val("-1");
		$("#price").removeClass("click");
		$("#price").removeClass("click2");
		$("#collect").removeClass("click");
		$("#collect").removeClass("click2");
		$("#newSpecies").removeClass("click");
		$("#newSpecies").removeClass("click2");
		$("#salesVol").removeClass("click");
		$("#salesVol").removeClass("click2");
		
		search('0');
	}
	
	//按价格
	function price() {
		var p = $("#txtP").val();
		if(p == "0") {
			$("#price").removeClass("click2");	
			$("#price").addClass("click");
			p = 1;
		} else {
			p = 0;
			$("#price").removeClass("click");
			$("#price").addClass("click2");
		}
		$("#txtP").val(p);
		search('0');
		$("#collect").removeClass("click");
		$("#collect").removeClass("click2");
		$("#newSpecies").removeClass("click");
		$("#newSpecies").removeClass("click2");
		$("#salesVol").removeClass("click");
		$("#salesVol").removeClass("click2");
	}
	
	//按新品
	function newSpecies() {
		var p = $("#txtP").val();
		if(p == "5") {
			$("#newSpecies").removeClass("click2");
			$("#newSpecies").addClass("click");
			p = 4;
		} else {
			$("#newSpecies").removeClass("click");
			$("#newSpecies").addClass("click2");
			p = 5;
		}
		$("#txtP").val(p);
		search('0');
		$("#price").removeClass("click");
		$("#collect").removeClass("click");
		$("#salesVol").removeClass("click");
		$("#price").removeClass("click2");
		$("#collect").removeClass("click2");
		$("#salesVol").removeClass("click2");
	}
	
</script>
</body>
</html>