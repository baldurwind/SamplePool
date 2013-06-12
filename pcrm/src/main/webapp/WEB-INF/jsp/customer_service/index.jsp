<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/include.jsp" %>
<%@ include file="../common/header.jsp" %>
<script type="text/javascript" src="${static_resource_host}/resource/script/ajaxfileupload.js"></script>
<html>
  <head>
    <title>customer_Service</title>
  </head>
  
  <body>
  	<!--导航 -->
	<div class="wrapper">
	  	<div  id="customer_serivce" class="customer_service">
	  		
	  	</div>
  	</div>
  	<!--工作台 -->
  </body>
  <script type="text/javascript">
	function open(tradeId) {
		$.ajax({
			type: 'post',
			url: contextPath+"customerService/checkTradeIdExists",
			dataType: 'json',
			data: {"tradeId":tradeId},
			success: function(msg) {
				if(msg.result=='true') {
					detail(msg.id);		
				} else {
					$("#customer_serivce").load("customerService/open?tradeId=" + tradeId + "&sellerNick=" + getSeller());
				}
			}
		});
	}
	
	function detail(id) {
		$("#customer_serivce").load("customerService/detail?id=" + id + "&sellerNick=" + getSeller() + "&userid=" + getCurUser());
	}
	
	function doLoadCustomerServiceList(nick) {
		$("#customer_serivce").load("customerService/find?nick=" + encodeURIComponent(nick));
	}
	function formReset() {
		$("#myForm")[0].reset();
		$("#btnUploadFile").attr("disabled",false);
		$("#btnUploadFile").val("上传");
		$("#uploada").val("");
		if($.trim($("#txtFilePath").val()).length > 0) {
			deleteUploadFile($("#txtFilePath").val());
		}
		$("#txtFilePath").val("");
	}
	
	function deleteUploadFile(fileName) {
		$.ajax({
  			type: 'post',
  			url: contextPath+"customerService/deleteFile",
  			dataType: 'json',
  			data: {"fileName":fileName}
  		});
	}
  </script>
  
   <c:choose>  
	  <c:when test="${status == 'open'}">
	  	<script>
			open("${tradeId}");
	  	</script>
	  </c:when>
	  <c:otherwise>
	  	<script>
	  		doLoadCustomerServiceList("${userid}");
	  	</script>
	  </c:otherwise>
  </c:choose>
</html>