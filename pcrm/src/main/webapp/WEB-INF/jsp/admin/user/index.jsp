<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/admin/common/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/jsp/admin/common/header.jsp" %>
		<title>管理后台-员工管理</title>
		<script type="text/javascript">
		</script>
	</head>
	<body>
		<!--左侧功能栏-->   
		<div class="cus-left">
    		<div class="cus-leftnav-tit">员工管理</div>
        	 	<div class="cus-leftNav" id="cus-leftNav">
        	 	<div class="cus-leftNav-list">
        	 	<h3 class="navTit">员工管理</h3>
	        	<ul id="loadCSTypeDiv">
	        	</ul>
	        	<div class="clear"></div>
        	</div>       
    	</div>
    	</div>
	   <!--右侧内容-->
		<div class="backend_right">
	    	<div class="rightSeviceManage">
	    		<h2>员工管理</h2>
	            <div class="sevicePanel">
	            	<div class="seviceoperate">
	                    <span> 员工姓名：</span>
	                    <input type="text" name="name" id="txtName"/>
	                    <span> 员工编号：</span>
	                    <input type="text" name="employeeNum" id="txtEmployeeNum"/>
	                    <input type="button" value="搜索" onclick="find('1');"/>
	                    <input type="button" value="员工信息添加" onclick="addUserPanel();"/>
	                </div>
	            </div>
	            <div class="dataContiner" id="dataContiner">
	            	
	            </div>
	        </div>
	    </div>
		<div class="clear"></div>
		<!--底部-->
		<div></div>
	</body>
	<script>
	$(function(){
		find('1');
	});
	
	function saveUser() {
		var re = /^[1-9]\d*$/;	
		var name = $.trim($("#txtUserName").val());
		var emp = $.trim($("#txtUserEmployeeNum").val());
		var mobile = $.trim($("#txtUserMobile").val());
		var email = $.trim($("#txtUserEmail").val());
		var reg = /^[0-9a-zA-Z_\.\-]+@([0-9a-zA-Z_\-\.])+[\.]+[a-z]{2,4}$/;
		var zzMobile = new RegExp("^(?:0[0-9]{2,3}[-\\s]{1}|\\(0[0-9]{2,4}\\))[0-9]{6,8}$|^[1-9]{1}[0-9]{5,7}$|^[1-9]{1}[0-9]{10}$");
		if(emp.length <= 0) {
			alert("请输入员工编号");
		} else if (!re.test(emp)) {
			alert("员工编号只能输入正整数");
		} else if (name.length <= 0) {
			alert("请输入员工姓名");
		} else if (mobile.length <= 0) {
			alert("请输入员工手机号码");
		} else if (mobile.length != 11) { 
			alert("请输入11位手机号码");
		} else if(!zzMobile.test(mobile)) {
			alert("手机号码格式输入有误");
		} else if (email.length <= 0) {
			alert("请输入员工电子邮箱");
		} else if (!reg.test(email)) {
			alert("电子邮箱格式输入有误");
		} else {
			$.ajax({
				type: 'post',
				url: "usermanage/admin/saveuser",
				dataType: 'json',
				data: $("#myUserForm").serialize() ,
				success: function(msg) {
					if(msg.result=='true') {
						$.closeMyPannel($(document).data("addUserPanel"));
						find('1');
						alert("员工信息已添加");
					} else {
						alert(msg.message);
					}
				}
			});
		}
	}
	
	function addUserPanel() {
		var type_panel = $.openMyPannel();
		type_panel.load("usermanage/admin/tosaveuser?seller=" + encodeURIComponent("${seller}") + " #addUser_panel",function(){
			 $("#closeBtnTRBtn").click(function(){$.closeMyPannel(type_panel);});
			 $(document).data("addUserPanel", type_panel);
			 $("#btnSaveType").click(function(){
				 saveUser();
			 });
		});
		$.pannelBgClose(type_panel);
	};
	
	function updateUserPanel(id) {
		var type_panel = $.openMyPannel();
		type_panel.load("usermanage/admin/toupdateuser?id=" + id + " #updateUser_panel",function(){
			 $("#closeBtnTRBtn").click(function(){$.closeMyPannel(type_panel);});
			 $(document).data("updateUserPanel", type_panel);
			 $("#btnUpdateUser").click(function(){
				 updateUser();
			 });
		});
		$.pannelBgClose(type_panel);
	};
	
	function find(page) {
		$("#dataContiner").html("正在加载...");
		$("#dataContiner").load("usermanage/admin/tousermanageindex?pageNo=" + page + "&seller=" + encodeURIComponent('${seller}') + "&name=" + encodeURIComponent($("#txtName").val()) + "&employee=" + encodeURIComponent($("#txtEmployeeNum").val()));
	}
	
	function updateUser() {
		var re = /^[1-9]\d*$/;	
		var name = $.trim($("#txtUserName").val());
		var emp = $.trim($("#txtUserEmployeeNum").val());
		var mobile = $.trim($("#txtUserMobile").val());
		var email = $.trim($("#txtUserEmail").val());
		var reg = /^[0-9a-zA-Z_\.\-]+@([0-9a-zA-Z_\-\.])+[\.]+[a-z]{2,4}$/;
		var zzMobile = new RegExp("^(?:0[0-9]{2,3}[-\\s]{1}|\\(0[0-9]{2,4}\\))[0-9]{6,8}$|^[1-9]{1}[0-9]{5,7}$|^[1-9]{1}[0-9]{10}$");
		if(emp.length <= 0) {
			alert("请输入员工编号");
		} else if (!re.test(emp)) {
			alert("员工编号只能输入正整数");
		} else if (name.length <= 0) {
			alert("请输入员工姓名");
		} else if (mobile.length <= 0) {
			alert("请输入员工手机号码");
		} else if (mobile.length != 11) { 
			alert("请输入11位手机号码");
		} else if(!zzMobile.test(mobile)) {
			alert("手机号码格式输入有误");
		} else if (email.length <= 0) {
			alert("请输入员工电子邮箱");
		} else if (!reg.test(email)) {
			alert("电子邮箱格式输入有误");
		} else {
			$.ajax({
				type: 'post',
				url: "usermanage/admin/updateuser",
				dataType: 'json',
				data: $("#updateUserForm").serialize() ,
				success: function(msg) {
					if(msg.result=='true') {
						$.closeMyPannel($(document).data("updateUserPanel"));
						find('1');
						alert("员工信息已修改");
					} else {
						alert(msg.message);
					}
				}
			});
		}
	}
	
	function deleteUser(id, status) {
		if(confirm("是否确认？")) {
			$.ajax({
				type: 'post',
				url: "usermanage/admin/updatestatus",
				dataType: 'json',
				data: {"id":id, "status": status} ,
				success: function(msg) {
					if(msg.result=='true') {
						find($("#txtPageNo").val());
						alert("员工信息状态已注销");
					} else {
						alert("员工信息状态注销失败");
					}
				}
			});
		}
	}
	
	function restoreUser(id, status) {
		if(confirm("是否确认？")) {
			$.ajax({
				type: 'post',
				url: "usermanage/admin/updatestatus",
				dataType: 'json',
				data: {"id":id, "status": status} ,
				success: function(msg) {
					if(msg.result=='true') {
						find($("#txtPageNo").val());
						alert("员工信息状态已恢复");
					} else {
						alert("员工信息状态恢复失败");
					}
				}
			});
		}
	}
</script>
</html>