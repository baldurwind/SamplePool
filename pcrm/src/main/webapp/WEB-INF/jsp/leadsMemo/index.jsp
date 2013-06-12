<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/include.jsp" %>
<%@ include file="../common/header.jsp" %>
<script type="text/javascript" src="${static_resource_host}/resource/script/My97DatePicker/WdatePicker.js"></script>
<input type="hidden" id="txtState"/>
<html>
  <head>
    <title>promotionList</title>
  </head>
  <body>
	<div class="wrapper">
	  	<div class="lead_remark" id="lead_remark">
	  		<h1 class="childModuleTit">事件登记<p class="cmt-btn addEventbtn" onclick="toSavePromotion();" id="toSave"></p></h1>
			<div class="proRegister" id="proRegister">
				<h3 class="head">已登记事件</h3>
				<div class="ui-tab">
					<ul class="ui-tab-ul">
						<p class="eventSort">
							<label class="cWhite" style="font-family: Tahoma">排序：</label><select><option>--按到期时间--</option></select>
						</p>
						<li class="ui-tab-ul-li-open" id="0"><span></span><a>未提醒(<span
								class="num thisColor" id="noBeginCount">0</span>)
						</a></li>
						<li id="1"><span></span><a>已提醒(<span class="num" id="overdueCount">0</span>)
						</a></li>
						<li id="2"><span></span><a>已取消(<span class="num" id="successCount">0</span>)
						</a></li>
						<span class="clear" style="_clear: none"></span>
					</ul>
					<div class="ui-tab-con" style="display: block" id="divPromotionList">
					</div>
				</div>
			</div>
	  	</div>
  	</div>
  </body>
  </html>
  
  <script>
  	 function promotionList() {
  		$("#lead_remark").load("leadsMemo/promotionList?buyerNick=" + getBuyer() + "&sellerNick=" + getSeller());
  	 }
  	 function toSavePromotion() {
		 $("#lead_remark").load("leadsMemo/toSave?buyerNick=" + getBuyer() + "&sellerNick=" + getSeller() + "&wangwangNick=" + getNick());
  	 }
  	 function toUpdatePromotion(id) {
  		$("#lead_remark").load("leadsMemo/toUpdate?lid=" + id);
  	 }
  	//修改缺货/促销事件状态
 	function updatePromotionStatus(id, status) {
 		$.tooltip({"type":"confirm","content":"您确定吗？","callback":function(result) {
 			if(result == true) {
 				$.ajax({
 					type: 'post',
 					url: contextPath+"leadsMemo/updateStatus",
 					dataType: 'json',
 					data: {"id":id, "status":status},
 					beforeSend: function() {
 						showDisplay();
 					},
 					success: function(msg) {
 						hideDisplay();
 						if(msg.result=='true') {
 							//未处理
 					 	 	loadStatusCount('noBeginCount',0);
 					 	 	//已取消
 					 	 	loadStatusCount('overdueCount',1);
 					 	 	//已完成
 					 	 	loadStatusCount('successCount',2);
 							loadPromotionList('1');
 							showMessage("当前事件状态已修改");
 						} else {
 							showMessage("修改失败，请稍候重试");
 						}
 					},
 					error: function() {
 						hideDisplay();
 					}
 				});
 			}
 		}});
 	}
 	
 	function loadStatusCount(mark,type) {
 			$.ajax({
	 	  		type: 'post',
	 	  		url: contextPath+"leadsMemo/loadStatusCount",
	 	  		dataType: 'json',
	 	  		data: {"buyerNick":getNoEncodeBuyer(), "sellerNick":getNoEncodeSeller(), "type":type},
	 	  		success: function(msg) {
	 	  			if(msg.result=='true') {
	 	  				$("#" + mark + "").text(msg.message);
	 	  			}
	 	  		},
	 	  		error: function() {
	 	  		}
 	  		});
 	 }
 	
 	$(function(){
 	 	$(".ui-tab").tabChangeEasy({"callback":function(element){
 			    $(element).find("span").addClass("thisColor")
 						  .end().siblings("li").find("span.num").removeClass("thisColor");
 			$("#txtState").val($(element).attr("id"));
 			loadPromotionList('1');
 		}});
 	 	$("#txtState").val('0');
 	 	//未处理
 	 	loadStatusCount('noBeginCount',0);
 	 	//已取消
 	 	loadStatusCount('overdueCount',1);
 	 	//已完成
 	 	loadStatusCount('successCount',2);
 	 	loadPromotionList('1');
 	 	authButtonResource("/leadsMemo");
 	});

 	function loadPromotionList(page) {
 		$("#divPromotionList").html("<div class='tips_loading'>查询中...</div>");
 		$("#divPromotionList").load("leadsMemo/promotionList_detail?buyerNick=" + getBuyer() + "&sellerNick=" + getSeller() + "&status=" + $("#txtState").val() + "&pageNo=" + page);
 	}
  	
 	function toIndex() {
		window.location.href = contextPath+"leadsMemo/index";
	}
  </script>