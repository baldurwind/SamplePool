<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="common/include.jsp" %>
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

<link rel="stylesheet" type="text/css" href="${static_resource_host}/resource/jslib/syCss.css">
<script type="text/javascript" charset="UTF-8" src="${static_resource_host}/resource/jslib/syUtil.js"></script>

<script type="text/javascript" charset="UTF-8">
	$(function() {

		$('#datagrid').treegrid({
			url : 'resourceController?datagrid',
			border : false,
			queryParams : {},
			sortName : 'id',
			sortOrder : 'asc',
			idField : 'id',
			treeField : 'name',
			striped : true,
			fit : true,
			fitColumns : true,
			title : '',
			rownumbers : false,
			frozenColumns : [ [ {
				field : 'id',
				hidden : true
			}, {
				title : '资源名称',
				field : 'name',
				width : 200,
				editor:'text'
			} ] ],
			columns : [ [ {
				field : 'url',
				title : '资源地址',
				width : 150,
				editor:'text'
			}, {
				field : 'comments',
				title : '资源描述',
				width : 100,
				editor:'text'
			}, {
				field : 'onoff',
				title : '是否验证',
				width : 35,
				editor:'text',
				formatter : function(value, rowData, rowIndex) {
					if (value == '1') {
						return '<img src="resource/images/icons/done_square.png"/>开启';
					} else {
						return '<img src="resource/images/icons/caution.png"/>关闭';
					}
				}
			} ] ],
			toolbar : [ '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					var rows = $('#datagrid').treegrid('getSelections');
					if(rows[0].onoff==1){
						alert("改节点下有子节点，无法删除！");
						return;
					}
					
					if (rows.length > 0) {
						$.messager.confirm('请确认', '您要删除当前所选项目？', function(r) {
							if (r) {
								$.ajax({
									url : 'resourceController?delete',
									data : {
										id : rows[0].id
									},
									cache : false,
									dataType : "json",
									type : "POST",
									success : function(response) {
										$('#datagrid').treegrid('unselectAll');
										$('#datagrid').treegrid('reload');
										$.messager.show({
											title : '提示',
											msg : response.msg
										});
									}
								});
							}
						});
					} else {
						$.messager.alert('提示', '请选择要删除的记录！', 'error');
					}
				}
			}, '-',  {
				text : '添加',
				iconCls : 'icon-add',
				handler : function() {
					
					var node = $('#datagrid').treegrid('getSelected');
					if(!node){
						$.messager.alert('提示', '请选择所属节点！', 'error');
						return;
					}
					 $('#nodeid').val(node.id);
					 alert(node.id);
					open();
				}
				
			}, '-', {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					var node = $('#datagrid').treegrid('getSelected');
					$('#datagrid').treegrid('unselectAll');
					$('#datagrid').treegrid('endEdit', node.id);
				}
			}, '-'],
			
			
			onAfterEdit : function( rowData, changes) {
				var url = 'resourceController?';
				if (rowData.id) {
					url += 'edit';
				} else {
					url += 'add';
				}
			
				$.ajax({
					url : url,
					data : rowData,
					cache : false,
					dataType : "json",
					type : "POST",
					success : function(response) {
						if (response.success) {
							$('#datagrid').treegrid('acceptChanges');
							var data = $('#datagrid').treegrid('getData');
							data.id = response.obj.id;
							$('#datagrid').treegrid('loadData', data);
							$('#datagrid').treegrid('reload');
							$.messager.show({
								title : '提示',
								msg : response.msg
							});
						} else {
							$.messager.alert('提示', response.msg, 'error');
							$('#datagrid').treegrid('rejectChanges');
						}
					}
				});
			},
			
			onDblClickRow : function(row){
				editNode();
			}
			
		});
			


	});
	
	
	function editNode(){
		var node = $('#datagrid').treegrid('getSelected');
		if (node){
			$('#datagrid').treegrid('beginEdit',node.id);
		}
	}
	
	function open(){
			$('#dd').dialog({
				
				
			});
	}
	
</script>

</head>

<body class="easyui-layout" style="overflow: hidden;">

<div id="dd" icon="icon-save" style="padding:5px;width:400px;height:200px;">
			<div style="background:#fafafa;padding:10px;width:300px;height:300px;">
	    <form id="ff" method="post" action="resourceController?add" onsubmit="$('#datagrid').treegrid('reload');">
	       <input type="hidden" name="_parentId" value="" id="nodeid"/>
	        <div>
	            <label for="name">资源名称:</label>
	            <input class="easyui-validatebox" type="text" name="name" required="true"></input>
	        </div>
	        <div>
	            <label for="url">资源地址:</label>
	            <input class="easyui-validatebox" type="text" name="url" required="true"></input>
	        </div>
	        <div>
	            <label for="comments">资源描述:</label>
	            <input class="easyui-validatebox" type="text" name="comments" required="true"></input>
	        </div>
	        
	          <div>
	            <label for="onoff">是否验证</label>
	            <input type="radio" class="easyui-validatebox name="onoff" value="1">是
	             <input type="radio" class="easyui-validatebox name="onoff" value="0">否
	           <!--  <input class="easyui-validatebox" type="text" name="onoff" required="true"></input> -->
	        </div>
	        <div>
	            <input type="submit" value="Submit">
	        </div>
	    </form>
	</div>
	</div>
	<div region="center" title="资源树" border="false" style="overflow: hidden;">
		<table id="datagrid"></table>
	</div>
</body>
</html>