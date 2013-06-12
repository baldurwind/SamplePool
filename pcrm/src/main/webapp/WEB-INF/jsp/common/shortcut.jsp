<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/navigation.jsp"/>


<body>
<div class="wrapper">

<div class="systemSet" id="systemSet">
 <!-- 	 <ul>
     	<li><p class="key"><span>Ctrl+H</span></p><p class="keyInfo">服务台</p><div class="clear"></div></li>
     	<li><p class="key"><span>Ctrl+K</span></p><p class="keyInfo">当前用户/订单所能参与的活动</p><div class="clear"></div></li>
        <li><p class="key"><span>Ctrl+S</span></p><p class="keyInfo">商品搜索</p><div class="clear"></div></li>
        <li><p class="key"><span>Ctrl+Y</span></p><p class="keyInfo">查看可赠优惠券</p><div class="clear"></div></li>
        <li><p class="key"><span>Ctrl+D</span></p><p class="keyInfo">当前客户订单及状态</p><div class="clear"></div></li>
        <li><p class="key"><span>Ctrl+B</span></p><p class="keyInfo">查看当前客户备注</p><div class="clear"></div></li>
     </ul>
     <div class="qykjj"><input onclick="selectShortcut()"  id="shortcut" type="checkbox"  style="vertical-align:middle"/><label style="vertical-align:middle">&nbsp;启用快捷键</label>
     <label>&nbsp;&nbsp;&nbsp;&nbsp;切换面板：</label><p class="toggleBtn" id="toggleBtn"></p>
     </div>
        <div class="clear"></div> -->
</div>
</div>
</body>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
</html>

<script type="text/javascript">
$(document).ready(function(){
	$.cookie('has_shortcut')=='checked'?$('#shortcut').attr('checked','checked'):$('#shortcut').attr('checked',null);
	var page="";
	var userinfo=getSeller()+"_"+getCurUserName();
	$(function(){
		$("#toggleBtn").click(function(){
			$(this).toggleClass("toggle");
			if($("#toggleBtn").hasClass("toggle"))
				page="back";
			else
				page="front";
			var url='saveDefaultIndex?userinfo='+userinfo+'&page='+page;
			$.ajax({
				url:url,
				type:'post',
				success: function(result){
					alert('ok');
				} 
			});  
		});
	})
})
</script>