
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<script>
  function testInitTrade(tradeStatus){
	$('#trade_module').load("trade?tradeStatus="+tradeStatus+"&current_page=0&seller="+getSeller()+"&buyer="+getBuyer());
} 
  function bindTopCookieEvent(){
	 	$("#topNav").find('a').live('click',function(){
			var _temp = $(this).attr('curr').toString();
			$.cookie('currNav',_temp,{expires: null, path: '/'});
			$.cookie('bottonCurrNav',null,{expires: null, path: '/'});
		});
	    setTopCurrNav($.cookie('currNav'));
	 }
	 
	function setTopCurrNav(v){
		if(v){
			$(".nav a").each(function(index, element) {
				 if(index+1 == v)
					 $(element).addClass("current");
				else
					$(element).removeClass("current");
			 });
		}else
			return ;
	} 
  $(document).ready(function(){
	  $("#topNav  ul.left li").live('click',function(){
			 $(this).addClass("current").siblings('li').removeClass("current");
		});
		$("#topNav ul.right li").live('click',function(){
			 $(this).parent().siblings('ul.left').find('li').removeClass("current");
		});
		bindTopCookieEvent();
 });
</script>
<div id="topNav" class="nav">
	<ul >
    	<li class="left"><a href="javascript:getLink('')" title="服务台" class="current" curr="1">服务台</a></li>
        <li class="left"><a href="javascript:getLink('marketing')" title="活动"  curr="2">活动</a></li>
        <li class="left"><a href="javascript:getLink('marketing/coupon/index')" title="优惠" curr="3" >优惠</a></li>
        <li class="left"><a href="javascript:getLink('trade/index')" title="订单" curr="4" >订单</a></li>
        <li class="left"><a href="javascript:getLink('customerService/index')" title="售后"curr="5" >售后</a></li>
        <li class="right"><a href="javascript:getLink('item/toSearch')" title="查找" curr="7">查找</a></li>
        <li class="right"><a href="javascript:getLink('leadsMemo/index')" title="备注" curr="6">备注</a></li>
    </ul>
    <div class="clear"></div>
    <div class="tips"></div>
</div>
<div class="wrapbox">
<input  id="seller" value="${seller}"/>  <!-- stevemadden旗舰店 芳蕾玫瑰粉粉旗舰店 -->
<input  id="buyer" value="${buyer }"/>  <!-- 芳蕾玫瑰粉粉旗舰店 晶典珠宝11  wccctest01  3994226377  良无限_非洲菊-->
<input  id="numiid" value="${numiid }"/>
<input  id="nick" value="${nick }"/>
<input  id="tid" value="${tid }"/>
<button value="C" onclick="testInitTrade('');"></button>
