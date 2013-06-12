<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<head>

<title>淘个性-订单</title>
<script src="${static_resource_host}/resource/script/trade.js" type="text/javascript"></script>
</head>
<body>
<div class="wrapper">

<div  id="trade_module" class="trade_order_index"></div>
<div id="postage" style="display:none" title="修改运费"><jsp:include page="/WEB-INF/jsp/trade/postage.jsp"/></div>
<!-- <div id="trace" style="display:none"  title="快递跟踪"   ></div> -->
<div class="order_mianban" id="memo" style="display:none;" title="备忘"></div>
<div class="order_mianban" id="trace"  style="display:none"   title="快递跟踪"   ></div>
<div class="order_mianban" id="shipping" style="display:none;" title="收货信息"></div>
<div id="close" style="display:none"    title="关闭交易"   ><jsp:include page="/WEB-INF/jsp/trade/close.jsp"/></div>
<div id="status_div"  style="display:none" > </div>
</div>
<input id="tradeStatus"  type="hidden" value="${tradeStatus}"/>
<input id="tradeWith"  type="hidden" value="${tradeWith}"/>
<input id="afterServiceTids"  type="hidden" value="${afterServiceTids}"/>
</body></html>
<script>
 $(document).ready(function(){
	 initTrade();
  	
 });
</script>