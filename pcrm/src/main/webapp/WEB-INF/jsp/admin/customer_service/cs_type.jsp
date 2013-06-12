<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/admin/common/include.jsp"%>
<ul>
	<li id="xxxxddd"><a href="javascript:void(0);">所有类型（${allCount}）</a></li>
	<c:forEach items="${CSITypeList}" var="type">
		<li id="${type.id}"><a href="javascript:void(0);">${type.name}（${type.count}）&nbsp;<img src='${static_resource_host}/resource/images/Write.png' class='menu_edit' width='16' height='16' alt='修改' onclick="toUpdateTypePanel('${type.id}');" /><img src='${static_resource_host}/resource/images/delete.png' onclick="deleteCSType('${type.id}');" width='16' height='16' class='menu_delete' /></a>
	</c:forEach>
</ul>

<script>
	$(function(){
		$("#cus-leftNav div.cus-leftNav-list").each(function(index, element) {
			$(this).find("ul").children("li").click(function(){
				var _this = $(this);
				$(this).addClass("curr").siblings("li").removeClass("curr");
				findCustomerServiceIssueByType(_this.attr("id"));
			}); 
		  });
		  $("#cus-leftNav .cus-leftNav-list h3.navTit").live('click',function(){
			$(this).toggleClass('choose').siblings('ul').toggle();
   	  });
	});
	
	function toUpdateTypePanel(id) {
		updateTypePanel(id);
		stopBubble(event);
	}
	
	function updateTypePanel(id) {
		var type_panel = $.openMyPannel();
		type_panel.load("customerService/admin/toUpdateCsiType?id=" + id + " #editsevice_panel",function(){
			 $("#closeBtnTRBtn").click(function(){$.closeMyPannel(type_panel);});
			 $(document).data("updateTypePanel", type_panel);
		});
		$.pannelBgClose(type_panel);
	};
</script>