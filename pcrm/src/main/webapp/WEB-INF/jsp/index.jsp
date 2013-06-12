<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<head><title>淘个性-工作台</title>
<script type="text/javascript" src="${static_resource_host}/resource/script/index.js"></script>
<script>
$(document).ready(function(){
	servertable.pcrmInit();
});


</script>
</head>
<body>
<div class="wrapper">
<div id="crm_module" >
<div class="index_status" id="member_status">
	<ul>
      <li class="thisColor">等待发货（<a href='javascript:queryTradeByStatus("WAIT_SELLER_SEND_GOODS")' id='waitingsds'>0</a>）</li>
      <li class="thisColor">申请退款（<a href='javascript:queryTradeWithRefund()' id='refundnums'>0</a>）</li>
      <li class="thisColor">售后处理中（<a href="javascript:queryAfterService()" id='csinums'>0</a>）</li>
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
         <td class="shuju" id="tradecount">0</td>
        <td class="shuju" id="tradeamount">0</td>
        <td class="shuju" id="tradeavg">0</td>
        <td class="shuju nobored" id="lasttradetime"></td>
      </tr>
  </table>
</div>
</div>


</div>
<!-- <div class="behavior_keyword" id="behavior_keyword"> -->
<div class="ui-accordion" id="keyword_module">
     <h3 class="ui-accordion-header"><span></span><a>买家最近搜索的关键字</a></h3>
	<div   class="ui-accordion-content" id="keyword_content_module" ></div>
</div>


<div  class="ui-accordion" id="zxunjilu"></div>
	<jsp:include page="behavior/item_module.jsp"/> 
</div>
</body>
</html>

