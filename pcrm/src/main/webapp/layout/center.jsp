<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<script type="text/javascript" charset="UTF-8">
	$(function() {

		$('#centerTabs').tabs();
		$('#centerTabs').tabs('add', {
			title : '首页',
			content : '<iframe src="homeController?portal" frameborder="0" style="border:0;width:100%;height:99.2%;"></iframe>',
			closable : true,
			iconCls : ''
		});

	});
</script>
<div id="centerTabs" border="false" fit="true" style="overflow: hidden;"></div>