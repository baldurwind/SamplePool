<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/include.jsp"%>
<%@ include file="../common/header.jsp"%>
<html>
<script src="${static_resource_host}/resource/script/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<input type="hidden" id="txtState" value="0" />
<!--导航 -->
<!--主体-->
<div class="wrapper">
 <div class="lead_remark" id="notice">
 <!--我的备忘录-->
	<h1 class="childModuleTit">
		我的备忘录
		<p class="cmt-btn addNotebtn" onclick="toSave();"></p>
	</h1>
	<div class="proRegister" id="proRegister">
		<div class="ui-tab">
			<ul class="ui-tab-ul">
				<p class="eventSort">
					<label class="cWhite" style="font-family: Tahoma">排序：</label><select><option>--按到期时间--</option></select>
				</p>
				<li id="0" class="ui-tab-ul-li-open"><span></span><a id="noBeginCount">未完成(<span class="num thisColor">0</span>)</a></li>
				<li id="1" ><span></span><a id="successCount">已完成(<span class="num">0</span>)</a></li>
				<li id="2"><span></span><a id="overdueCount">已取消(<span class="num">0</span>)</a></li>
				<span class="clear" style="_clear: none"></span>
			</ul>
			<div class="ui-tab-con" style="display: block" id="divListDetail">
			
			</div>
		</div>
	</div>
 </div>
</div>
<!--工作台 -->
</body>
<script type="text/javascript">
	
	function toSave() {
		$("#notice").load("reminder/toSave?buyerNick=" + getBuyer() + "&sellerNick=" + getSeller() + "&nick=" + getCurUser());
	}
	
	function toIndex() {
		window.location.href = contextPath+"reminder/index";
	}
	
	function toUpdate(id) {
		$("#notice").load("reminder/toUpdate?id=" + id + "&buyerNick=" + getBuyer());
	}
	
	$(function(){
		   $(".ui-tab").tabChangeEasy({"callback":function(element){
				    $(element).find("span").addClass("thisColor")
							  .end().siblings("li").find("span.num").removeClass("thisColor");
				    $("#txtState").val($(element).attr("id"));
				    getList($(element).attr("id"));
			}});
		   loadRemindStatusCount("noBeginCount",0,"未完成");
		   getList('0');
		   loadRemindStatusCount("overdueCount",2,"已取消");
		   loadRemindStatusCount("successCount",1,"已完成");
		  
	});
		
	function getList(type) {
		$("#divListDetail").html("<div class='tips_loading'>查询中...</div>");
		$("#divListDetail").load("reminder/listDetail?type=" + type + "&nick=" + getCurUser());
	}
	
	function loadRemindStatusCount(mark,type,info) {
			$.ajax({
		  		type: 'post',
		  		url: "reminder/loadStatusCount",
		  		dataType: 'json',
		  		data: {"nick":getCurUser(), "type":type},
		  		success: function(msg) {
		  			if(msg.result=='true') {
		  				$("#" + mark + "").text("" + info + "(" + msg.message + ")");
		  			}
		  		},
		  		error: function() {
		  		}
		  	});					
	}
  	
</script>
</html>