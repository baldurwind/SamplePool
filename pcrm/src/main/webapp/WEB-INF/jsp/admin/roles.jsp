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
	var editRowIndex = -1;
	$(function() {

		$('#datagrid').datagrid({
			url : 'roleController?datagrid&sellernick='+getSproSeller(),
			border : true,
			pagination : true,
			queryParams : {},
			sortName : 'name',
			sortOrder : 'asc',
			idField : 'id',
			striped : true,
			fit : true,
			fitColumns : true,
			title : '',
			rownumbers : false,
			frozenColumns : [ [ {
				field : 'id',
				checkbox : true
			}, {
				title : '角色名称',
				field : 'name',
				width : 100,
				sortable : true,
				editor : {
					type : 'validatebox',
					options : {
						required : true
					}
				}
			} ] ],
			columns : [ [ {
				field : 'comments',
				title : '角色描述',
				sortable : true,
				width : 100,
				editor : {
					type : 'validatebox',
					options : {
						required : false
					}
				}
			},
			{
				field : 'sellernick',
				title : '店铺名',
				sortable : true,
				width : 130
			
			}] ],
			toolbar : [ '-',  {
				text : '取消编辑',
				iconCls : 'icon-undo',
				handler : function() {
					$('#datagrid').datagrid('unselectAll');
					$('#datagrid').datagrid('rejectChanges');
					editRowIndex = -1;
				}
			}, '-', {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					$('#datagrid').datagrid('unselectAll');
					if (editRowIndex > -1) {
						$('#datagrid').datagrid('beginEdit',editRowIndex);
						$('#datagrid').datagrid('endEdit', editRowIndex);
						editRowIndex = -1;
					}
				}
			},'-' ],
			onSelect : function(rowIndex, rowData) {
				if (editRowIndex > -1 && editRowIndex != rowIndex) {
					$.messager.alert('提示', '您没有结束之前编辑的数据，请先保存或取消编辑！', 'error');
			
					$('#datagrid').datagrid('unselectRow',rowIndex);
					editRowIndex = -1;
				} else if (editRowIndex != rowIndex) {
					//sy.$tree.reAsyncChildNodes(null, "refresh");
				//	$('#datagrid').datagrid('beginEdit', rowIndex);
				
					editRowIndex = rowIndex;
					sy.checkNodesFun(rowData);/*选中当前角色已有的资源*/
				}
			},
			
			
			onUnselect : function(rowIndex, rowData){
				sy.$tree.reAsyncChildNodes(null, "refresh");
				editRowIndex = -1;
			},
			
			onLoadSuccess : function(data){
			$("div[class='datagrid-header-check']").find('input').attr("disabled","disabled");
			},
			
			onAfterEdit : function(rowIndex, rowData, changes) {
				if ($('#datagrid').datagrid('validateRow', rowIndex)) {
					var url = 'roleController?';
					if (rowData.id) {
						url += 'edit';
					} else {
						url += 'add';
					}
					var nodes = sy.$tree.getCheckedNodes();
					var resourceIds = [];
					for ( var i = 0; i < nodes.length; i++) {
						resourceIds.push(nodes[i].id);
					}
					rowData.resourceIds = resourceIds.join(',');
					$.ajax({
						url : url,
						data : rowData,
						cache : false,
						dataType : "json",
						type : "POST",
						success : function(response) {
							if (response.success) {
								$('#datagrid').datagrid('acceptChanges');
								var data = $('#datagrid').datagrid('getData');
								data.rows[rowIndex].id = response.obj.id;
								$('#datagrid').datagrid('loadData', data);
								/*$('#datagrid').datagrid('reload');*/
								$.messager.show({
									title : '提示',
									msg : response.msg
								});
							} else {
								$.messager.alert('提示', response.msg, 'error');
								$('#datagrid').datagrid('rejectChanges');
							}
						}
					});
				}
			}
		});

		$('.datagrid-toolbar').append('查询：<input id="searchRoleNameInput"/>');
		$('#searchRoleNameInput').searchbox({
			searcher : function(value, name) {
				if ($.trim(value) == '') {
					value = '';
				}
				$('#datagrid').datagrid('load', {
					name : value
				});
			},
			prompt : '请输入要查找的角色名称',
			width : 170
		});

		sy.$tree = $.fn.zTree.init($('#ztree'), {
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pid",
					rootPId : 0
				}
			},
			check : {
				enable : true
			},
			async : {
				enable : true,
				url : 'roleController?resourceTree',
				autoParam : [ 'id' ]
			}
		});

		sy.checkNodesFun = function(rowData) {
			sy.$tree.checkAllNodes(false);
			$.messager.progress({
				text : '数据处理中....',
				interval : 100
			});
			$.ajax({
				url : 'roleController?getRoleResources',
				data : {
					roleId : rowData.id
				},
				cache : false,
				dataType : "json",
				type : "POST",
				success : function(response) {
					if (response.success) {
						var ids = response.obj;
						for ( var i = 0; i < ids.length; i++) {
							var node = sy.$tree.getNodeByParam('id', ids[i]);
							sy.$tree.checkNode(node);
						}
					} else {
						$.messager.alert('提示', response.msg, 'error');
					}
					$.messager.progress('close');
				}
			});
		};

		$('#treePanel').panel({
			fit : true,
			title : '角色权限',
			border : false,
			tools : [ {
				iconCls : 'icon-reload',
				handler : function() {
					sy.$tree.reAsyncChildNodes(null, "refresh");
				}
			} ]
		});

	});
</script>

</head>
<body class="easyui-layout" style="overflow: hidden;">
	<div region="center" title="角色列表" border="false" style="overflow: hidden;">
		<table id="datagrid"></table>
	</div>
	<div region="east" style="width: 250px;overflow: hidden;" split="true">
		<div id="treePanel">
			<ul id="ztree" class="ztree"></ul>
		</div>
	</div>
</body>
</html>
