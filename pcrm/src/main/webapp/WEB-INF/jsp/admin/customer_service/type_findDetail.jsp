<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
   	<%@ include file="/WEB-INF/jsp/admin/common/include.jsp" %>
   	<table>
   		<tr>
   			<td width="120px;">售后类型名称</td>
   			<td width="120px;">创建时间</td>
   			<td>操作</td>
   		</tr>
   		<c:forEach items="${CSITypeList}" var="type">
   			<tr>
   				<td>${type.name}</td>
   				<td><fmt:formatDate value="${type.createTime}" pattern="yyyy-MM-dd HH:mm:ss" type="both"/></td>
   				<td><a href="javascript:void(0);" onclick="deleteType('${type.id}')" >删除</a></td>
   			</tr>
   		</c:forEach>
   	</table>
