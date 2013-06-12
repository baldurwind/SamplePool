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
			url : 'userController?datagrid&sellernick='+getSproSeller(),
			border : true,
			pagination : true,
			queryParams : {},
			sortName : 'name',
			sortOrder : 'asc',
			idField : 'id',
			striped : true,
			fit : true,
			fitColumns : true,
			title : '用户列表',
			rownumbers : false,
			frozenColumns : [ [ {
				field : 'id',
				checkbox : true
			}, {
				title : '用户名',
				field : 'name',
				width : 80,
				sortable : true,
				editor : {
					type : 'validatebox',
					options : {
						required : true
					}
				}
			} ] ],
			columns : [ [  {
				field : 'sellernick',
				title : '店铺名',
				sortable : true,
				width : 130
			
			},  {
				field : 'createdatetime',
				title : '创建时间',
				sortable : true,
				width : 130
			}/* , {
				field : 'modifydatetime',
				title : '最后修改时间',
				sortable : true,
				width : 130
			} */ ] ],
			toolbar : [ '-', {
				text : '取消编辑',
				iconCls : 'icon-undo',
				handler : function() {
					$('#datagrid').datagrid('unselectAll');
					$('#datagrid').datagrid('rejectChanges');
					editRowIndex = -1;
				}
			}, '-', {
				text : '修改密码',
				iconCls : 'icon-edit',
				handler : function() {
					var rows = $('#datagrid').datagrid('getSelections');
					if (rows.length > 0) {
						sy.$modifyPwdDialog.dialog('open');
					} else {
						$.messager.alert('提示', '请选择要修改密码的记录！', 'error');
					}
				}
			},'-', {
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
			},'-', {
				text : '查找',
				iconCls : 'icon-search',
				handler : function() {
				
					$('#datagrid').datagrid('load', {
						name : $('#whereTable [name=name]').val(),
						//sellernick:$('#seller').val(),
						createdatetimeStart : $('#whereTable [name=createdatetimeStart]').val(),
						createdatetimeEnd : $('#whereTable [name=createdatetimeEnd]').val(),
						modifydatetimeStart : $('#whereTable [name=modifydatetimeStart]').val(),
						modifydatetimeEnd : $('#whereTable [name=modifydatetimeEnd]').val()
					});
				}
			}, '-', {
				text : '清空条件',
				iconCls : 'icon-search',
				handler : function() {
					$('#whereTable input').val('');
					$('#datagrid').datagrid('load', {});
				}
			}, '-' ],
			onSelect : function(rowIndex, rowData) {
				if (editRowIndex > -1 && editRowIndex != rowIndex) {
					$.messager.alert('提示', '您没有结束之前编辑的数据，请先保存或取消编辑！', 'error');
					$('#datagrid').datagrid('unselectRow',rowIndex);
					editRowIndex = -1;
				} else if (editRowIndex != rowIndex) {
					//$('#datagrid').datagrid('beginEdit', rowIndex);
					editRowIndex = rowIndex;
					sy.checkRolesFun(rowData);/*选中当前用户的角色*/
				}
			},
			
			onUnselect : function(rowIndex, rowData){
				sy.$roleDatagrid.datagrid('unselectAll');
				editRowIndex = -1;
			},
			
	
			
			onLoadSuccess : function(data){
			$("div[class='datagrid-header-check']").find('input').attr("disabled","disabled");
			},
			
			onAfterEdit : function(rowIndex, rowData, changes) {
				if ($('#datagrid').datagrid('validateRow', rowIndex)) {
					var url = 'userController?';
					if (rowData.id) {
						url += 'edit';
					} else {
						url += 'add' ;
					}
					var roleIds = [];
					var rows = $('#roleDatagrid').datagrid('getSelections');
					for ( var i = 0; i < rows.length; i++) {
						roleIds.push(rows[i].id);
					}
					rowData.roleIds = roleIds.join(',');
			
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
								$('#datagrid').datagrid('reload');
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

		sy.$modifyPwdForm = $('#modifyPwdForm').form();

		sy.$modifyPwdDialog = $('#modifyPwdDialog').show().dialog({
			title : '修改所选用户密码',
			modal : true,
			closed : true,
			width : 240,
			buttons : [ {
				text : '修改',
				iconCls : '',
				handler : function() {
					if (sy.$modifyPwdForm.form('validate')) {
						var ids = [];
						var rows = $('#datagrid').datagrid('getSelections');
						for ( var i = 0; i < rows.length; i++) {
							ids.push(rows[i].id);
						}
						$.ajax({
							url : 'userController?modifyUsersPassword',
							data : {
								ids : ids.join(','),
								password : sy.$modifyPwdForm.find('[name=password]').val()
							},
							cache : false,
							dataType : "json",
							type : "POST",
							success : function(r) {
								if (r.success) {
									$.messager.show({
										title : '提示',
										msg : r.msg
									});
									sy.$modifyPwdForm.find('[name=password]').val('');
									sy.$modifyPwdDialog.dialog('close');
									$('#datagrid').datagrid('reload');
								} else {
									$.messager.alert('提示', r.msg, 'error');
								}
							}
						});
					}
				}
			} ],
			onOpen : function() {
				window.setTimeout(function() {
					sy.$modifyPwdForm.find('[name=password]').focus();
				}, 1);
			}
		});

		sy.$modifyPwdForm.find('[name=password]').bind('keyup', function(event) {/* 增加回车提交功能 */
			if (event.keyCode == '13') {
			}
		});

		sy.$roleDatagrid = $('#roleDatagrid').datagrid({
			url : 'userController?roleDatagrid&sellernick='+$('#seller').val(),
			border : false,
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
				title : '用户名',
				field : 'name',
				width : 100,
				sortable : true
			} ] ],
			columns : [ [ {
				field : 'comments',
				title : '角色描述',
				sortable : true,
				width : 100
			} ] ]
		});

		$('#roleDatagridPanel').panel({
			fit : true,
			title : '用户角色列表',
			border : false,
			tools : [ {
				iconCls : 'icon-reload',
				handler : function() {
					sy.$roleDatagrid.datagrid('unselectAll');
				}
			} ]
		});

		sy.checkRolesFun = function(rowData) {
			$('#roleDatagrid').datagrid('unselectAll');
			$.messager.progress({
				text : '数据处理中....',
				interval : 100
			});
			$.ajax({
				url : 'userController?getUserRoles',
				data : {
					userId : rowData.id
				},
				cache : false,
				dataType : "json",
				type : "POST",
				success : function(response) {
					if (response.success) {
						var ids = response.obj;
						for ( var i = 0; i < ids.length; i++) {
							$('#roleDatagrid').datagrid('selectRecord', ids[i]);
						}
					} else {
						$.messager.alert('提示', response.msg, 'error');
					}
					$.messager.progress('close');
				}
			});
		};

	});

	
	//alert($("div[class='datagrid-header-check']").length);//.find('input:checkbox').attr("disabled","disabled");
	//$("div[class='datagrid-header-check']").find('input:checkbox').attr("checked","checked");
	
	
</script>

</head>

<body class="easyui-layout" style="overflow: hidden;">

	<div region="north" border="false" style="height:82px;overflow: hidden;" title="查询条件">
		<table class="tableForm" id="whereTable"   style="overflow: hidden; font-size:12px;">
			<tr>
				<th>用户名</th>
				<td colspan='3'><input name="name" /></td>
			</tr>
			<tr>
				<th style="width: 80px;">创建时间</th>
				<td><input name="createdatetimeStart" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:$(this).next('input').val(),readOnly:true});" style="width: 46%;" />&nbsp;&nbsp;至&nbsp;&nbsp;<input name="createdatetimeEnd" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:$(this).prev('input').val(),readOnly:true});" style="width: 46%;" /></td>
				<th style="width: 80px;">最后修改时间</th>
				<td><input name="modifydatetimeStart" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:$(this).next('input').val(),readOnly:true});" style="width: 46%;" />&nbsp;&nbsp;至&nbsp;&nbsp;<input name="modifydatetimeEnd" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:$(this).prev('input').val(),readOnly:true});" style="width: 46%;" />
				</td>
				</td>
			</tr>
		</table>
	</div>

	<div region="center" border="false" style="overflow: hidden;">
		<table id="datagrid"></table>
	</div>

	<div region="east" style="width: 300px;overflow: hidden;" split="true">
		<div id="roleDatagridPanel" style="overflow: hidden;">
			<table id="roleDatagrid"></table>
		</div>
	</div>

	<div id="modifyPwdDialog" style="display: none;">
		<form id="modifyPwdForm">
			<table class="tableForm">
				<tr>
					<th style="width: 40px;">密码</th>
					<td><input name="password" type="password" class="easyui-validatebox" required="true" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
