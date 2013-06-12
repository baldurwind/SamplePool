<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/admin/common/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/jsp/admin/common/header.jsp" %>
	</head>
	<body>
	<!-- 添加员工信息 -->
		<div class="addroles prelative" id="addUser_panel">
		<form id="myUserForm" name="myUserForm">
			<input type="hidden" value="${seller}" name="sellerNick"/>
	    	<p class="closeBtnTR" id="closeBtnTRBtn" title="关闭">关闭</p>
	    	<h2>添加员工信息</h2>
	        <table cellpadding="0" cellspacing="0" border="0" class="publicTableStyle" width="100%">
	        	<tr>
	        		<td width="30%" align="right">员工编号：</td>
	        		<td width="70%"><input type="text" id="txtUserEmployeeNum" name="employeeNum" maxlength="10"/></td>
	        	</tr>
	        	<tr>
	        		<td align="right">员工姓名：</td>
	        		<td><input type="text" id="txtUserName" name="name" maxlength="10"/></td>
	        	</tr>
	        	<tr>
	        		<td align="right">手机号码：</td>
	        		<td><input type="text" id="txtUserMobile" name="mobile" maxlength="11"/></td>
	        	</tr>
	        	<tr>
	        		<td align="right">电子邮箱：</td>
	        		<td><input type="text" id="txtUserEmail" name="email" maxlength="40"/></td>
	        	</tr>
	        </table>
	        <div class="addroles_btn"><input id="btnSaveType" type="button" value="创建" /><input name=""  type="reset" value="重置" /></div>
	        </form>
	    </div> 
    
    
    
    
<!-- 修改售后问题类型 -->
		<div class="addroles prelative" id="updateUser_panel">
			<form action="" id="updateUserForm" name="updateUserForm">
	    	<p class="closeBtnTR" id="closeBtnTRBtn" title="关闭">关闭</p>
	    	<input type="hidden" value="${user.id}" name="id"/>
	    	<input type="hidden" value="${user.sellerNick}" name="sellerNick"/>
	    	<h2>修改员工信息</h2>
	        <table cellpadding="0" cellspacing="0" border="0" class="publicTableStyle" width="100%">
	        	<tr>
	        		<td width="30%" align="right">员工编号：</td>
	        		<td width="70%"><input type="text" id="txtUserEmployeeNum" name="employeeNum" value="${user.employeeNum}" maxlength="10"/></td>
	        	</tr>
	        	<tr>
	        		<td align="right">员工姓名：</td>
	        		<td><input type="text" id="txtUserName" name="name" value="${user.name}" maxlength="10"/></td>
	        	</tr>
	        	<tr>
	        		<td align="right">手机号码：</td>
	        		<td><input type="text" id="txtUserMobile" name="mobile" value="${user.mobile}" maxlength="11"/></td>
	        	</tr>
	        	<tr>
	        		<td align="right">电子邮箱：</td>
	        		<td><input type="text" id="txtUserEmail" name="email" value="${user.email}" maxlength="40"/></td>
	        	</tr>
	        </table>
	        <div class="addroles_btn"><input id="btnUpdateUser"  type="button" value="修改" /></div>
	        </form>
	    </div>
</html>