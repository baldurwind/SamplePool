<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/include.jsp"%>
<h1 class="childModuleTit">事件登记<p class="cmt-btn addEventbtn" onclick="toSavePromotion();" id="toSave"></p></h1>
<div class="proRegister" id="proRegister">
	<h3 class="head">已登记事件</h3>
	<div class="ui-tab">
		<ul class="ui-tab-ul">
			<p class="eventSort">
				<label class="cWhite" style="font-family: Tahoma">排序：</label><select><option>--按到期时间--</option></select>
			</p>
			<li class="ui-tab-ul-li-open" id="0"><span></span><a>未提醒(<span
					class="num thisColor" id="noBeginCount">${noBeginCount}</span>)
			</a></li>
			<li id="1"><span></span><a>已提醒(<span class="num" id="overdueCount">${successCount}</span>)
			</a></li>
			<li id="2"><span></span><a>已取消(<span class="num" id="successCount">${overdueCount}</span>)
			</a></li>
			<span class="clear" style="_clear: none"></span>
		</ul>
		<div class="ui-tab-con" style="display: block" id="divPromotionList">
			
		</div>
	</div>
</div>
<!--end_事件登记-->
<script type="text/javascript">
<!--
$(function(){
 	$(".ui-tab").tabChangeEasy({"callback":function(element){
		    $(element).find("span").addClass("thisColor")
					  .end().siblings("li").find("span.num").removeClass("thisColor");
		$("#txtState").val($(element).attr("id"));
		loadPromotionList('1');
	}});
 	$("#txtState").val('0');
 	loadPromotionList('1');
 	authButtonResource("/leadsMemo");
});

function loadPromotionList(page) {
	$("#divPromotionList").html("<div class='tips_loading'>查询中...</div>");
	$("#divPromotionList").load("leadsMemo/promotionList_detail?buyerNick=" + getBuyer() + "&sellerNick=" + getSeller() + "&status=" + $("#txtState").val() + "&pageNo=" + page);
}
//-->
</script>
