<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/admin/common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/admin/common/header.jsp"%>
<title>管理后台-售后管理</title>
</head>
<body>
	<!--左侧功能栏-->
	<div class=" backend_left">
		<h2 class="menu_fl menu_fl_active">
			<a href="#">系统设置</a>
		</h2>
		<ul style="display: block" class="menu_zl">
			<li><a href="#" class="menu_zl_choose">系统亮灯设置</a></li>
			<li><a href="#">其他设置</a></li>
		</ul>
	</div>

	<!--右侧内容-->
	<div class="backend_right">
		<div class="rightDataManage">
			<h2>系统各状态亮灯设置</h2>
			<div class="dataContiner set_deng">
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr class="head">
						<td width="5%">状态名称</td>
						<td width="20%"><i class="greenico"></i>变 <i class="yellowico"></i>的时间/小时</td>
						<td width="20%"><i class="yellowico"></i>变 <i class="redico"></i>的时间/小时</td>
						<td width="10%">最近一次修改时间</td>
						<td width="15%">&nbsp;&nbsp;&nbsp;&nbsp;操作</td>
					</tr>
					<c:forEach items="${customAlertTimeList}" var="status">
						<tr>
							<td>${status.name}</td>
							<td><span id="spanYelloLightAlertTime${status.statusId}">${status.yelloLightAlertTime}</span><input id="txtYelloLightAlertTime${status.statusId}" value="${status.yelloLightAlertTime}" style="width:30px;display: none;"></td>
							<td><span id="spanRedLightAlertTime${status.statusId}">${status.redLightAlertTime}</span><input id="txtRedLightAlertTime${status.statusId}" value="${status.redLightAlertTime}" style="width:30px;display: none;"></td>
							<td><fmt:formatDate value="${status.modifyTime}" pattern="yyyy-MM-dd" type="both"/></td>
							<td class="dataContinerOperate">
								<c:choose>
									<c:when test="${status.modifyTime == null}">
										<span id="spanSave${status.statusId}">
											<a href="javascript:void(0);" onclick="saveCustomAlertTime('${status.statusId}');">设置</a>
										</span>
										<span id="spanSaveConfirm${status.statusId}" style="display: none;">
											<a href="javascript:void(0);" onclick="doSaveCustomAlertTime('${status.statusId}');">设置</a>
											<a href="javascript:void(0);" onclick="resetSave('${status.statusId}');">取消</a>
										</span>
									</c:when>
									<c:otherwise>
										<span id="spanUpdate${status.statusId}">
											<a href="javascript:void(0);" onclick="updateCustomAlertTime('${status.statusId}');">修改</a>
										</span>
										<span id="spanUpdateConfirm${status.statusId}" style="display: none;">
											<a href="javascript:void(0);" onclick="doUpdateCustomAlertTime('${status.statusId}');">修改</a>
											<a href="javascript:void(0);" onclick="resetUpdate('${status.statusId}');">取消</a>
										</span>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	<div class="clear"></div>
	<!--底部-->
	<div></div>
	
	<script type="text/javascript">
		function updateCustomAlertTime(id) {
			$("#spanYelloLightAlertTime" + id).hide();
			$("#spanRedLightAlertTime" + id).hide();
			$("#txtYelloLightAlertTime" + id).show();
			$("#txtRedLightAlertTime" + id).show();
			$("#spanUpdate" + id).hide();
			$("#spanUpdateConfirm" + id).show();
		}
		
		function saveCustomAlertTime(id) {
			$("#spanYelloLightAlertTime" + id).hide();
			$("#spanRedLightAlertTime" + id).hide();
			$("#txtYelloLightAlertTime" + id).show();
			$("#txtRedLightAlertTime" + id).show();
			$("#spanSave" + id).hide();
			$("#spanSaveConfirm" + id).show();
		}
		
		function resetUpdate(id) {
			$("#spanYelloLightAlertTime" + id).show();
			$("#spanRedLightAlertTime" + id).show();
			$("#txtYelloLightAlertTime" + id).hide();
			$("#txtRedLightAlertTime" + id).hide();
			$("#spanUpdate" + id).show();
			$("#spanUpdateConfirm" + id).hide();
		}
		
		function resetSave(id) {
			$("#spanYelloLightAlertTime" + id).show();
			$("#spanRedLightAlertTime" + id).show();
			$("#txtYelloLightAlertTime" + id).hide();
			$("#txtRedLightAlertTime" + id).hide();
			$("#spanSave" + id).show();
			$("#spanSaveConfirm" + id).hide();
		}
		
		function doUpdateCustomAlertTime(id) {
			var reg1 = /^\d+$/;
			if($.trim($("#txtYelloLightAlertTime" + id).val()).length == 0) {
				alert("黄灯提醒时间不能为空");
			} else if ($.trim($("#txtRedLightAlertTime" + id).val()).length == 0) {
				alert("红灯提醒时间不能为空");
			} else if(!reg1.test($.trim($("#txtYelloLightAlertTime" + id).val()))) {
				alert("黄灯提醒时间只能输入小时整数");
			} else if(!reg1.test($.trim($("#txtRedLightAlertTime" + id).val()))) {
				alert("红灯提醒时间只能输入小时整数");
			} else {
				$.ajax({
					type: 'post',
					url: "customerService/admin/updateCustomAlertTime",
					dataType: 'json',
					data: {"id":id, "y":$.trim($("#txtYelloLightAlertTime" + id).val()), "r":$.trim($("#txtRedLightAlertTime" + id).val()), "sellerNick":getSeller()},
					success: function(msg) {
						if(msg.result=='true') {
							alert("售后状态提醒时间已修改");
							window.location.href = window.location.href; 
						} else {
							alert(msg.message);
						}
					}
				});
			}
		}
		
		function doSaveCustomAlertTime(id) {
			var reg1 = /^\d+$/;
			if($.trim($("#txtYelloLightAlertTime" + id).val()).length == 0) {
				alert("黄灯提醒时间不能为空");
			} else if ($.trim($("#txtRedLightAlertTime" + id).val()).length == 0) {
				alert("红灯提醒时间不能为空");
			} else if(!reg1.test($.trim($("#txtYelloLightAlertTime" + id).val()))) {
				alert("黄灯提醒时间只能输入小时整数");
			} else if(!reg1.test($.trim($("#txtRedLightAlertTime" + id).val()))) {
				alert("红灯提醒时间只能输入小时整数");
			} else {
				$.ajax({
					type: 'post',
					url: "customerService/admin/saveCustomAlertTime",
					dataType: 'json',
					data: {"id":id, "y":$.trim($("#txtYelloLightAlertTime" + id).val()), "r":$.trim($("#txtRedLightAlertTime" + id).val()), "sellerNick":getSeller()},
					success: function(msg) {
						if(msg.result=='true') {
							alert("售后状态提醒时间已设置");
							window.location.href = window.location.href; 
						} else {
							alert(msg.message);
						}
					}
				});
			}
		}
	</script>
</body>
</html>