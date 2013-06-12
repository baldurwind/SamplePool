<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ include file="../common/include.jsp" %>
    
    <c:choose>
    	<c:when test="${customerLists == null}">
    		<div class="tips_wujilu">当前暂无记录</div>
    	</c:when>
    	<c:otherwise>
    		<table width="100%" border="0" cellspacing="0" cellpadding="0" id="serviceResultList-table">
			   	<tr class="head">
			      	<td width="20%">分类</td>
			          <td width="30%">订单号</td>
			          <td width="35%">问题标题</td>
			          <td width="15%">操作</td>
			      </tr>
			      <c:forEach items="${customerLists.list}" var="csi">
			       <tr> 
			       	<td>${csi[1]}</td>
			           <td><span class="thisColor">${csi[0]}</span></td>
			           <td>${csi[3]}</td>
			           <td><a href="javascript:void(0);" onclick="detail('${csi[8]}');">详情</a></td>
			       </tr>
			      </c:forEach>
			  </table>
    	</c:otherwise>
    </c:choose>
  <c:if test="${customerLists != null}">
  	<div class="ui-pager">
        <ul>
        	 <li class="ui-pager-step">第${customerLists.pageNo}页</li>
             <li class="ui-pager-count">共${customerLists.totalPages}页</li>
           <c:choose>
		<c:when test="${customerLists != null}">
			<input type="hidden" value="${customerLists.pageNo}" id="txtPageNo"/>
			<c:choose>
				<c:when test="${customerLists.pageNo == 1}">
					<li class="ui-pager-firstPage-disable"></li>
                    <li class="ui-pager-prePage-disable"></li>
				</c:when>
				<c:otherwise>
					<li class="ui-pager-firstPage"  onclick="searchCustomerService('1')"></li>
                    <li class="ui-pager-prePage" onclick="searchCustomerService('${customerLists.pageNo - 1}')"></li>
				</c:otherwise>
			</c:choose>
		   <c:choose>
            <c:when test="${customerLists.pageNo == customerLists.totalPages || customerLists.totalPages == 0}">
            	 <li class="ui-pager-nextPage-disable"></li>
                 <li class="ui-pager-lastPage-disable"></li>
            </c:when>
            <c:otherwise>
            	<li class="ui-pager-nextPage" onclick="searchCustomerService('${customerLists.pageNo + 1}')"></li>
                <li class="ui-pager-lastPage" onclick="searchCustomerService('${customerLists.totalPages}')"></li>
            </c:otherwise>              
        </c:choose>
    		</c:when>
		</c:choose>
        </ul>
	</div>
</c:if>