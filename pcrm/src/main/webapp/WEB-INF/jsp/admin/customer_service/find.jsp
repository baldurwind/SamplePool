<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/admin/common/include.jsp" %>
<table width="100%" cellpadding="0" cellspacing="0" border="0" class="publicDataTable">
   	<tr class="head" >
       	<td>交易编号</td>
        <td>卖家昵称</td>
        <td>售后类型</td>
        <td width="70">紧急程度</td>
        <td>售后标题</td>
        <td>提交时间</td>
        <td>状态</td>
        <td>操作</td>
     </tr>

	<c:forEach items="${customerLists.list}" var="cs">
		<tr class="list-head">
			<td>${cs[0]}</td>
			<td>${cs[7]}</td>
			<td>${cs[1]}</td>
			<td>
				<c:choose>
					<c:when test="${cs[2] == '0'}">
						高
					</c:when>
					<c:when test="${cs[2] == '1'}">
						中
					</c:when>
					<c:when test="${cs[2] == '2'}">
						低
					</c:when>
				</c:choose>
			</td>
			<td>${cs[3]}</td>
			<td>
				<fmt:parseDate var="createTime" value="${cs[5]}" type="DATE" pattern="yyyy-MM-dd HH:mm:ss"/>
				<fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss" type="both"/>
			</td>
			<td>
				<b><span id="spanCsiStatus${cs[0]}">${cs[9]}</span></b><c:choose>
					<c:when test="${cs[10] == 1}">
						<i class="greenico" id="iCsiStatus${cs[0]}"></i>
					</c:when>
					<c:when test="${cs[10] == 2}">
						<i class="yellowico" id="iCsiStatus${cs[0]}"></i>
					</c:when>
					<c:when test="${cs[10] == 3}">
						<i class="redico" id="iCsiStatus${cs[0]}"></i>
					</c:when>
				</c:choose>
			</td>
			<td class="dataContinerOperate"><a href="javascript:void(0);" id="${cs[8]}" class="detailLink">查看</a><a name="delete" href="javascript:void(0);" onclick="deleteCustomerService('${cs[0]}');">删除</a></td>
		</tr>
        <tr class="seviceDetail" style="display:none;">
     	  	<td colspan="8" valign="top">
        		<div id="divShowCustomerServiceDetail${cs[8]}">
        			
        		</div>		
      		</td>
      	</tr>
	</c:forEach>
</table>
  <c:if test="${customerLists != null}">
	<div class="back_pubPager">
		<span class='left'>${customerLists.pageNo}/${customerLists.totalPages}</span>
		<c:choose>
			<c:when test="${customerLists != null}">
				<input type="hidden" value="${customerLists.pageNo}" id="txtPageNo"/>
				<c:choose>
					<c:when test="${customerLists.pageNo == 1}"><p class='disable'>上一页</p></c:when>
					<c:otherwise>
						<p style="cursor: pointer;" onclick="find('${customerLists.pageNo - 1}')">上一页</p>
					</c:otherwise>
				</c:choose>
			   	<c:choose>
	            	<c:when test="${customerLists.pageNo == customerLists.totalPages || customerLists.totalPages == 0}">
	            		<p class='disable'>下一页 </p></c:when>
	            		<c:otherwise>
	                		<p style="cursor: pointer;" onclick="find('${customerLists.pageNo + 1}')">下一页</p>  
	            		</c:otherwise>              
	        	</c:choose>
    		</c:when>
		</c:choose>
		<div class='clear'></div>
	</div>
  </c:if>
<script>
	$(function(){
		$.ajaxSetup({
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			cache: false 
		});
		$("#dataContiner table tr.list-head a.detailLink").toggle(function(){
			 $(this).addClass('dataContinerOperate_choose').text('收回');
			 var td = $(this).parent();
			 getCsDetail($(this).attr("id"));
			 td.parent("tr").removeClass("unread").addClass("read").next("tr").show();
		},function(){
			 $(this).removeClass('dataContinerOperate_choose').text('查看');
			 var td = $(this).parent();
			 td.parent("tr").removeClass("read").next("tr").hide();
		});
		inittable_tr();
		authButtonResource2("/customerService/admin");
		
	});
	
	function getCsDetail(id) {
		$("#divShowCustomerServiceDetail" + id).html("加载中...");
		$("#divShowCustomerServiceDetail" + id).load("customerService/admin/detail?id=" + id + "&sellerNick=" + encodeURIComponent("${sellerNick}") + "&userid=" + getCurUser());
	}
	function deleteCustomerService(tradeId) {
		if(window.confirm("删除当前售后问题？")) {
			$.ajax({
				type: 'post',
				url: "customerService/admin/delete",
				dataType: 'json',
				data: {"tradeId":tradeId},
				beforeSend: function() {
				},
				success: function(msg) {
					if(msg.result=='true') {
						find($("#txtPageNo").val());
						loadCSType();
						alert("售后问题已删除");
					} else {
						alert("售后问题删除失败");
					}
				},
				error: function() {}
			});
		}
	}
	
	function changeStatus(tradeId, id) {
		$("#spanCsiStatus" + tradeId).text($("#txtCsiStatus" + id).find("option:selected").text());
		$("#iCsiStatus" + tradeId).removeClass("yellowico");
		$("#iCsiStatus" + tradeId).removeClass("redico");
		$("#iCsiStatus" + tradeId).removeClass("greenico");
		$("#iCsiStatus" + tradeId).addClass("greenico");
	}
</script>