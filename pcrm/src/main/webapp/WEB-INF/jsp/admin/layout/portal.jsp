<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<title></title>

<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<script type="text/javascript" charset="UTF-8" src="${static_resource_host}/resource/jslib/My97DatePicker4.8Beta2/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" charset="UTF-8" src="${static_resource_host}/resource/jslib/jquery-easyui-1.2.5/jquery-1.7.1.min.js"></script>

<script type="text/javascript" charset="UTF-8" src="${static_resource_host}/resource/jslib/jquery.cookie.js"></script>

<link id="easyuiTheme" rel="stylesheet" type="text/css" href="${static_resource_host}/resource/jslib/jquery-easyui-1.2.5/themes/gray/easyui.css">
<script type="text/javascript" charset="UTF-8" src="${static_resource_host}/resource/jslib/changeEasyuiTheme.js"></script>
<link rel="stylesheet" type="text/css" href="${static_resource_host}/resource/jslib/jquery-easyui-1.2.5/themes/icon.css">
<script type="text/javascript" charset="UTF-8" src="${static_resource_host}/resource/jslib/jquery-easyui-1.2.5/jquery.easyui.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="${static_resource_host}/resource/jslib/jquery-easyui-1.2.5/locale/easyui-lang-zh_CN.js"></script>

<link rel="stylesheet" type="text/css" href="${static_resource_host}/resource/jslib/jquery-easyui-portal/portal.css">
<script type="text/javascript" charset="UTF-8" src="${static_resource_host}/resource/jslib/jquery-easyui-portal/jquery.portal.js"></script>

<link rel="stylesheet" type="text/css" href="${static_resource_host}/resource/jslib/JQuery-zTree-v3.1/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" charset="UTF-8" src="${static_resource_host}/resource/jslib/JQuery-zTree-v3.1/js/jquery.ztree.all-3.1.min.js"></script>

<link rel="stylesheet" type="text/css" href="${static_resource_host}resource/jsli/b/syCss.css">
<script type="text/javascript" charset="UTF-8" src="${static_resource_host}/resource/jslib/syUtil.js"></script>

<script type="text/javascript" charset="UTF-8">
	$(function() {

		sy.$portal = $('#portal').portal({
			border : false,
			fit : true
		});
		for ( var i = 0; i < 2; i++) {
			sy.$portal.portal('add', {
				panel : $('<div/>').panel({
					title : 'Title' + i,
					content : '<div style="padding:5px;">Content' + (i + 1) + '</div>',
					height : 100,
					collapsible : true
				}),
				columnIndex : i
			});
		}
		sy.$portal.portal('resize');

	});
</script>

</head>

<body class="easyui-layout" style="overflow: hidden;">
	<img src="${static_resource_host}/resource/admin/images/promiss.png"/>
</body>
</html>