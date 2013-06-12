<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/admin/common/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/jsp/admin/common/header.jsp" %>
	</head>
	<body>
	<!-- 添加售后问题类型 -->
	<form id="addTypeForm" name="addTypeForm">
		<div class="addroles prelative" id="addsevice_panel">
	    	<p class="closeBtnTR" id="closeBtnTRBtn" title="关闭">关闭</p>
	    	<h2>添加售后问题类型</h2>
	        <p>请输入售后问题类型名称</p>
	        <div class="addroles_input"><input name="name" id="txtName" maxlength="6" type="text" /></div>
	        <div class="addroles_btn"><input id="btnSaveType" onclick="saveCSIType();" type="button" value="创建" /><input name=""  onclick="csiTypeReset();" type="button" value="重置" /></div>
	    </div> 
    </form>
<!-- 修改售后问题类型 -->
	<form action="" id="updateTypeForm" name="updateTypeForm">
		<div class="addroles prelative" id="editsevice_panel">
	    	<p class="closeBtnTR" id="closeBtnTRBtn" title="关闭">关闭</p>
	    	<h2>修改售后问题类型</h2>
	        <p>请输入新的售后问题类型名称</p>
	        <input type="hidden" name="id" value="${csiType.id}" id="txtCaiTypeId"/>
	        <div class="addroles_input"><input name="name" id="txtUpdateName" maxlength="6" type="text" value="${csiType.name}"/></div>
	        <div class="addroles_btn"><input id="btnUpdateType" onclick="updateCSIType();" type="button" value="修改" /></div>
	    </div>
    </form>
</html>