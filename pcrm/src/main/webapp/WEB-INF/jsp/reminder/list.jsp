<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/include.jsp"%>
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
				<li id="0" class="ui-tab-ul-li-open"><span></span><a id="noBeginCount">未完成(<span class="num thisColor">${noBeginCount}</span>)</a></li>
				<li id="1" ><span></span><a id="successCount">已完成(<span class="num">${successCount}</span>)</a></li>
				<li id="2"><span></span><a id="overdueCount">已取消(<span class="num">${overdueCount}</span>)</a></li>
				<span class="clear" style="_clear: none"></span>
			</ul>
			<div class="ui-tab-con" style="display: block" id="divListDetail">
			
			</div>
		</div>
	</div>
	
	<script>
	$(function(){
	   $(".ui-tab").tabChangeEasy({"callback":function(element){
			    $(element).find("span").addClass("thisColor")
						  .end().siblings("li").find("span.num").removeClass("thisColor");
			    $("#txtState").val($(element).attr("id"));
			    getList($(element).attr("id"));
		}});
	   getList('0');
	});
	
	function getList(type) {
		$("#divListDetail").html("<div class='tips_loading'>查询中...</div>");
		$("#divListDetail").load("reminder/listDetail?type=" + type + "&nick=" + getCurUser());
	}
	</script>