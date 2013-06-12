<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/admin/common/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/jsp/admin/common/header.jsp" %>
		<title>管理后台-售后管理</title>
		<script type="text/javascript">
		</script>
		<script type="text/javascript" src="${static_resource_host}/resource/script/ajaxfileupload.js"></script>
	</head>
	<body>
		<!--左侧功能栏-->   
		<div class="cus-left">
    		<div class="cus-leftnav-tit">售后</div>
        	 	<div class="cus-leftNav" id="cus-leftNav">
        	 	<div class="cus-leftNav-list">
        	 	<h3 class="navTit">售后问题分类<img src='${static_resource_host}/resource/images/edit_add.png' id="type"/></h3>
	        	<ul id="loadCSTypeDiv">
	        	</ul>
	        	<div class="clear"></div>
        	</div>       
    	</div>
    	</div>
	   <!--右侧内容-->
		<div class="backend_right">
	    	<div class="rightSeviceManage">
	    		<h2>售后问题</h2>
	            <div class="sevicePanel">
	            	<div class="seviceoperate">
	                    <span> 交易编号：</span>
	                    <input type="text" name="tradeId" id="txtTradeId"/>
	                    <select style="width:110px; display: none;" name="type" id="txtType">
							<option value="">所有类型</option>
							<c:forEach items="${CSITypeList}" var="type">
								<option value="${type.id}">${type.name}</option>
							</c:forEach>
						</select>
	                    <span> 待处理人：</span>
	                    <select style="width:200px;" name="csNick" id="txtCsNick">
							<option value="">所有</option>
							<c:forEach items="${customerServiceList}" var="customerService">
								<option value="${customerService[0]}">${customerService[1]}</option>
		 					</c:forEach>
						</select>
	                    <input type="button" value="搜索" onclick="find('1');"/>
	                </div>
	                <ul class="sevicescreen">  <!-- class="sevicescreen_choose" -->
	                	<li><a href="javascript:priorityFind('');" >全部</a></li>
	                	<li>|</li>
	                    <li><a href="javascript:priorityFind('0');">紧急度高</a></li>
	                    <li>|</li>
	                    <li><a href="javascript:priorityFind('1');">紧急度中</a></li>
	                    <li>|</li>
	                    <li><a href="javascript:priorityFind('2');">紧急度低</a></li>
	                </ul>
	            </div>
	            <div class="dataContiner" id="dataContiner">
	            	
	            </div>
	        </div>
	    </div>
		<div class="clear"></div>
		<!--底部-->
		<div></div>
		<script type="text/javascript">
			var prioritySearch = "";
			function loadCSType() {
				$("#loadCSTypeDiv").load("customerService/admin/loadcsType?sellerNick=" + encodeURIComponent("${sellerNick}"));
			}
			function find(pageNo) {
				$("#dataContiner").html("搜索中...");
				$("#dataContiner").load("customerService/admin/find?sellerNick=" + encodeURIComponent("${sellerNick}")  + "&tradeId=" + encodeURIComponent($("#txtTradeId").val()) + "&type=" + encodeURIComponent($("#txtType").val()) + "&csNick=" + encodeURIComponent($("#txtCsNick").val()) + "&pageNo=" + pageNo + "&priority=" + prioritySearch);
			}
			
			function priorityFind(priority) {
				if($.trim(priority).length > 0) {
					prioritySearch = priority;
				} else {
					prioritySearch = "";
				}
				find('1');
			}
			loadCSType();
			find('1');
			$(function(){
				if($.browser.msie&&($.browser.version == "6.0")&&!$.support.style){
					$(".backend_right").width(document.body.clientWidth-205);
					$(window).resize(function(){ 
					 var w = document.body.clientWidth;
						 if(w-205<900){
							$(".backend_right").width(900);
						 } 
						  
					});
				};
				//列表效果
				/*$("#dataContiner table tr.list-head ").hover(function(){
					 $(this).children().addClass("td-hover");
				},function(){
					  if($(this).data('unread')){
					 	$(this).addClass('unread');
					 }
				 	 $(this).children().removeClass("td-hover");
				 })*/
				//添加售后问题类型
				$("#type").click(function(event){
					addTypePanel();
					stopBubble(event);
				});
				
				function addTypePanel(){
					var type_panel = $.openMyPannel();
					type_panel.load("customerService/admin/type #addsevice_panel",function(){
						 $("#closeBtnTRBtn").click(function(){$.closeMyPannel(type_panel);});
						 $(document).data("saveCsTypePannel", type_panel);
					});
					$.pannelBgClose(type_panel);
				};
				
				//修改售后问题类型
				$(".menu_edit").click(function(){
					editRolePanel();
				});
				
				function editRolePanel(){
					var newRole_panel = $.openMyPannel();
					newRole_panel.load("sevice_panel.html #editsevice_panel",function(){
						 $("#closeBtnTRBtn").click(function(){$.closeMyPannel(newRole_panel);})
					});
					$.pannelBgClose(newRole_panel);
				};
				
				//删除售后问题类型
				$(".menu_delete").click(function(){
					deleteRolePanel();
				});
				
				function deleteRolePanel(){
					var newRole_panel = $.openMyPannel();
					newRole_panel.load("sevice_panel.html #deletesevice_panel",function(){
						 $("#closeBtnTRBtn").click(function(){$.closeMyPannel(newRole_panel);})
					});
					$.pannelBgClose(newRole_panel);
				};
				
				//处理和分配面板的切换
				$(".serviceChuliForm ul li").live('click',function(){
					var _index = $(this).index();
					$(this).addClass("click").siblings("li").removeClass("click");
					$(this).parent().parent().find("div.serChuliFrorm").eq(_index).show().siblings("div.serChuliFrorm").hide();
				});
			});
			
			function saveCSIType() {
				if($.trim($("#txtName").val()).length == 0) {
					alert("请输入售后类型名称");
				} else {
					$("#btnSaveType").attr("disabled",true);
					$.ajax({
						type: 'post',
						url: "customerService/admin/saveType",
						dataType: 'json',
						data: {"name":$("#txtName").val(), "sellerNick":"${sellerNick}"} ,
						beforeSend: function() {
						},
						success: function(msg) {
							$("#btnSaveType").attr("disabled",false);
							if(msg.result=='true') {
								$("#txtName").val("");
								loadCSITypeList();
								loadCSType();
								$.closeMyPannel($(document).data("saveCsTypePannel"));
								alert("售后类型已添加");
							} else {
								alert(msg.message);
							}
						}
					});
				}
			}
			
			function updateCSIType() {
				if($.trim($("#txtUpdateName").val()).length == 0) {
					alert("请输入售后类型名称");
				} else {
					$("#btnUpdateType").attr("disabled",true);
					$.ajax({
						type: 'post',
						url: "customerService/admin/updateCSType",
						dataType: 'json',
						data: {"id":$("#txtCaiTypeId").val(), "name":$("#txtUpdateName").val()},
						success: function(msg) {
							if(msg.result=='true') {
								loadCSITypeList();
								loadCSType();
								$.closeMyPannel($(document).data("updateTypePanel"));
								alert("售后类型已修改");
							} else {
								alert("售后类型修改失败");
							}
						}
					});
				}
			}
			
			function csiTypeReset() {
				$("#txtName").val("");
			}
			
			function loadCSITypeList() {
				$.ajax({
					type : 'post',
					url : "customerService/admin/loadCSITypeList",
					dataType : 'json',
					data: {"sellerNick":"${sellerNick}"},
					success : function(msg) {
						$("#txtType").empty();
						$("<option value=''>所有类型</option>").appendTo("#txtType");
						if (msg.result == 'true') {
							$(msg.message).appendTo("#txtType");
						}
					}
				});
			}
			
			function findCustomerServiceIssueByType(typeId) {
				$("#txtType").val(typeId);
				find('1');
			}
			
			function deleteCSType(id) {
				if(confirm("是否删除当前售后问题类型？")) {
					$.ajax({
						type: 'post',
						url: "customerService/admin/deleteCSType",
						dataType: 'json',
						data: {"id":id} ,
						success: function(msg) {
							if(msg.result=='true') {
								loadCSITypeList();
								loadCSType();
								alert("售后问题类型已删除");
							} else {
								alert("售后问题类型删除失败");
							}
						}
					});
				}
			}
			authButtonResource('/customerService/admin');
		    function stopBubble(e) {
		        //如果提供了事件对象，则这是一个非IE浏览器
		        if ( e && e.stopPropagation )
		            //因此它支持W3C的stopPropagation()方法
		            e.stopPropagation();
		        else
		            //否则，我们需要使用IE的方式来取消事件冒泡
		            window.event.cancelBubble = true;
		    }
		    
		</script>    
	</body>
</html>