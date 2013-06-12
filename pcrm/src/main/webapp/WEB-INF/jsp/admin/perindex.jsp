<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/admin/common/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>Sypro1.0</title>

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

<link rel="stylesheet" type="text/css" href="${static_resource_host}/resource/jslib/syCss.css">
<script type="text/javascript" charset="UTF-8" src="${static_resource_host}/resource/jslib/syUtil.js"></script>

<script type="text/javascript" charset="UTF-8">
	$(function() {

		sy.loginFun = function() {
			var selectedTab = sy.$loginTabs.tabs('getSelected');
			var f = selectedTab.find('form');
			var data = {};
			if (f.attr('id') == 'loginInputForm') {
				data = f.serialize();
			} else if (f.attr('id') == 'loginDatagridForm') {
				data = {
					name : sy.$loginDatagridName.combogrid('getValue'),
					password : f.find('input[name=password]').val()
				};
			} else if (f.attr('id') == 'loginComboboxForm') {
				data = {
					name : sy.$loginComboboxName.combobox('getValue'),
					password : f.find('input[name=password]').val()
				};
			}
			if (f.form('validate')) {
				$.ajax({
					url : 'userController?login',
					data : data,
					dataType : 'json',
					success : function(r) {
						if (r.success) {
							$.messager.show({
								title : '提示',
								msg : r.msg
							});
							sy.$loginDialog.dialog('close');
							$('#indexLayout').layout('panel', 'center').panel('setTitle', sy.fs('[{0}]，欢迎您！[{1}]', r.obj.name, r.obj.ip));
						} else {
							$.messager.alert('提示', r.msg, 'error');
						}
					}
				});
			}
		};


	});
</script>





</head>

<body id="indexLayout" class="easyui-layout">


	
	<div href="layout/south.jsp" region="south" style="height:20px;overflow: hidden;"></div>
		<div href="layout/west.jsp" region="west" split="true" title="导航" style="width:200px;overflow: hidden;"></div>
		<div href="layout/center.jsp" region="center" title="欢迎使用Chamago权限系统v1.0" style="overflow: hidden;"></div>

	

</body>
</html>
