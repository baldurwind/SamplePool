<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/admin/common/include.jsp" %>
<table width="100%" cellpadding="0" cellspacing="0" border="0" class="publicDataTable">
   	<tr class="head" >
       	<td width="20%">员工编号</td>
        <td width="20%">员工姓名</td>
        <td width="20%">手机号码</td>
        <td width="20%">电子邮箱</td>
        <td width="10%">员工状态</td>
        <td>操作</td>
     </tr>

	<c:forEach items="${users.list}" var="user">
		<tr class="list-head">
			<td>${user.employeeNum}</td>
			<td>${user.name}</td>
			<td>${user.mobile}</td>
			<td>${user.email}</td>
			<td>
				<c:choose>
					<c:when test="${user.status == '0'}">正常</c:when>
					<c:when test="${user.status == '1'}">已注销</c:when>
					<c:otherwise>未知</c:otherwise>
				</c:choose>
			</td>
			<td class="dataContinerOperate"><a href="javascript:void(0);" onclick="updateUserPanel('${user.id}');" class="detailLink">修改</a>
				<c:choose>
					<c:when test="${user.status == '0' }">
						<a name="delete" href="javascript:void(0);" onclick="deleteUser('${user.id}', '1');">注销</a>
					</c:when>
					<c:otherwise>
						<a name="delete" href="javascript:void(0);" onclick="restoreUser('${user.id}', '0');">恢复</a>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</c:forEach>
</table>
  <c:if test="${users != null}">
	<div class="back_pubPager">
		<span class='left'>${users.pageNo}/${users.totalPages}</span>
		<c:choose>
			<c:when test="${users != null}">
				<input type="hidden" value="${users.pageNo}" id="txtPageNo"/>
				<c:choose>
					<c:when test="${users.pageNo == 1}"><p class='disable'>上一页</p></c:when>
					<c:otherwise>
						<p style="cursor: pointer;" onclick="find('${users.pageNo - 1}')">上一页</p>
					</c:otherwise>
				</c:choose>
			   	<c:choose>
	            	<c:when test="${users.pageNo == users.totalPages || users.totalPages == 0}">
	            		<p class='disable'>下一页 </p></c:when>
	            		<c:otherwise>
	                		<p style="cursor: pointer;" onclick="find('${users.pageNo + 1}')">下一页</p>  
	            		</c:otherwise>              
	        	</c:choose>
    		</c:when>
		</c:choose>
		<div class='clear'></div>
	</div>
  </c:if>