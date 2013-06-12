<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<script src="${static_resource_host}/resource/script/marketing.js" type="text/javascript"></script>
 <script>
 $(document).ready(function(){
	 initMeal();
 	 initLimitDiscount();
 });
 </script>
<body>

<div class="wrapper">
<!--店内活动-->
<div class="behavior_activity" id="behavior_activity">
	 <h1 class="childModuleTit">店内活动</h1>
     <div  class="module ui-accordion" id="behavior_setmeal">
     	<h3 class="ui-accordion-header"><span></span><a>套餐</a></h3>
        <div  id="marketing_meal" class="con ui-accordion-content"></div>
     </div>
     <div  class="module ui-accordion" id="behavior_panicbuying">
		 <h3 class="ui-accordion-header"><span></span><a>限时抢购</a></h3>
 		<div id="marketing_limitdiscount" class="con ui-accordion-content"></div></div>
</div>
<script type="text/javascript">
$(function(){
	  $(".ui-accordion").accordion({"extend":true});
	 $("#behavior_setmeal,#behavior_panicbuying").find('a.xq').click(function(){
	 	 $(this).parent('td').parent('tr').next('tr').eq(0).children('td').toggle();
	 })
	 $("#behavior_setmeal ul.tclist li").children('div.img').hover(function(){
	 	$(this).addClass('hover');
	 },function(){
	 	$(this).removeClass('hover');
	 })
	 $("ul.ourActitvteList li").hover(function(){
		 	$(this).addClass("publicHoverBgColor").find(".send").show();
		 },function(){$(this).removeClass("publicHoverBgColor").find(".send").hide();})
})
</script>
</div>
</body>
</html>