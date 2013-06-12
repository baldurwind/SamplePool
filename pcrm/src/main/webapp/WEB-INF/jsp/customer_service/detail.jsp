<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/include.jsp"%>
<script type="text/javascript" src="${static_resource_host}/resource/script/ajaxfileupload.js"></script>
<div class="serviceInfo">
	<div class="pubOrderDetial">
		<div class="topInfo">
			<h4 class="block FB">${customerServiceIssuesDetail[3]}</h4>
			<p class="section">
				<strong>订单ID：</strong><span class="thisColor">${customerServiceIssuesDetail[0]}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<strong>类型：</strong>${customerServiceIssuesDetail[1]}
			</p>
			<p class="section">
				<strong>状态：</strong>${customerServiceIssuesDetail[7]}
				<c:choose>
					<c:when test="${customerServiceIssuesDetail[9] == 1}">
						<i class="left greenico"></i>
					</c:when>
					<c:when test="${customerServiceIssuesDetail[9] == 2}">
						<i class="left yellowico"></i>
					</c:when>
					<c:when test="${customerServiceIssuesDetail[9] == 3}">
						<i class="left redico"></i>
					</c:when>
				</c:choose>
			</p>
		</div>
		<div class="bottomTable">
			<h4 class="block FB">问题描述：</h4>
			<p class="problemDetial">
				${customerServiceIssuesDetail[4]}&nbsp;
				<c:if
					test="${customerServiceIssuesDetail[6] != null && customerServiceIssuesDetail[6] != ''}">
					<a
						href="customerService/downloadFile?fileName=${customerServiceIssuesDetail[6]}"><img
						src="${static_resource_host}/resource/images/uploader.gif" alt="附件" /></a>
				</c:if>
			</p>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="order_mianban_table">
				<tr class="head">
					<td width="18%" height="20" align="center" class="1"><strong>处理人</strong></td>
					<td width="24%" align="center"><strong>结果</strong></td>
					<td width="58%" align="center"><strong>备注</strong></td>
				</tr>
				<c:forEach items="${csiDetailList}" var="detail" varStatus="idx">
					<tr>
						<td class="l">${detail[2]}&nbsp;</td>
						<td><span class="FL">${detail[3]}</span> <c:choose>
								<c:when test="${detail[8] == 1}">
									<i class="right greenico"></i>
								</c:when>
								<c:when test="${detail[8] == 2}">
									<i class="right yellowico"></i>
								</c:when>
								<c:when test="${detail[8] == 3}">
									<i class="right redico"></i>
								</c:when>
							</c:choose></td>
						<td>${detail[4]} <c:if test="${detail[5] != null}">
								<a href="customerService/downloadFile?fileName=${detail[5]}"><img
									src="${static_resource_host}/resource/images/uploader.gif" /></a>
							</c:if> <c:if test="${detail[7] == 4}">
								<c:if test="${fn:length(csiDetailList) == idx.index + 1}">
									<c:if test="${customerServiceIssuesDetail[13] == userId}">
										<p class="publicFormBtn" onclick="chongxinkaishi();">重新开启</p>
									</c:if>
								</c:if>
							</c:if>&nbsp;
						</td>
					</tr>
				</c:forEach>
			</table>
				<div class="submitForm" id="serviceChuliForm">
					<ul>
						<li class="click" id="btnAdmeasure">分配</li>
						<li id="btnDisposal">处理</li>
					</ul>
					<span class="clear"></span>
					<div class="serChuliFrorm FL" id="divAdmeasure"
						style="width: 386px;display: none" >
							<form name="admeasureForm" id="admeasureForm">
								<input type="hidden" name="id"
									value="${customerServiceIssuesDetail[15]}" />
								<table>
									<tr>
										<td>分配给：</td>
										<td><select style="width: 200px;" id="txtGroup" name="csNick">
												<option value="">请选择客服</option>
												<c:forEach items="${userList}" var="user">
													<option value="${user[0]}">${user[1]}</option>
												</c:forEach>
										</select></td>
									</tr>
								</table>
							</form>
							<div class="clear"></div>
							<p class="publicFormBtn FL" id="btnAdmeasure2"
								style="margin-top: 15px;" onclick="doUserAdmeasure();">分配</p>
						</div>
					<div class="clear"></div>
					<div class="serChuliFrorm" style="display: none" id="divDisposal">
							<form id="myForm" name="myForm">
								<input type="hidden" value="${customerServiceIssuesDetail[15]}"
									name="csiId" id="txtCsiId" /> <input type="hidden"
									name="filePath" id="txtFilePath"> <input type="hidden"
									name="nick" id="txtNick2">
								     <table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="20%" height="30">处理状态：</td>
										<td width="80%"><select id="txtCsiStatus" name="status"
											style="width: 180px;">
												<c:forEach items="${statusList}" var="status">
													<option value="${status.id}">${status.name}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<td width="20%" height="30" valign="top">描述：</td>
										<td width="80%"><textarea
												style="width: 100%; height: 100px;" name="remark"
												id="txtRemark" maxlength="150"></textarea></td>
									</tr>
									<tr>
										<td width="20%" height="30" valign="top"></td>
										<td width="80%"><input type="file" id="uploada"
											name="uploada" />&nbsp;<input type="button" style="width: 5em"
											id="btnUploadFile" onclick="uploadImages('uploada')" value='上传' />
										</td>
									</tr>
									<tr>
										<td width="20%" height="30" valign="top">&nbsp;</td>
										<td width="80%">
											 <p class="publicFormBtn" id="btnDisposal2" onclick="saveDisposal();">提交</p>
		                                     <p class="publicFormBtn pfb-glay" id="btnResetDisposal2" onclick="formReset();">重置</p>
									</tr>
								</table>
							</form>
					</div>
				</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(function(){
		$("#serviceChuliForm ul li").live('click',function(){
			var _index = $(this).index();
			$(this).addClass("click").siblings("li").removeClass("click");
			$("#serviceChuliForm div.serChuliFrorm").eq(_index).show().siblings("div.serChuliFrorm").hide();
		});
		authButtonResource("/customerService");
	});
	
	//执行售后问题分配(用户)
  function doUserAdmeasure() {
  	var csId = $.trim($("#txtGroup").val());
  	if(csId.length == 0) {
  		showMessage("请选择客服");
  	} else {
  		$("#btnAdmeasure2").attr("disabled", true);
  		$.ajax({
  			type: 'post',
  			url: "customerService/saveAdmeasure",
  			dataType: 'json',
  			data: $("#admeasureForm").serialize(),
  			beforeSend: function() {
  				showDisplay();
  			},
  			success: function(msg) {
  				hideDisplay();
  				if(msg.result=='true') {
  					detail('${customerServiceIssuesDetail[15]}');
  					showMessage("分配成功");
  				} else {
  					$("#btnAdmeasure2").attr("disabled", false);
  					showMessage("分配失败");
  				}
  			},
  			error: function() {
  				hideDisplay();
  			}
  		});
  	}
  }
	
	//ajax上传图片附件
	function uploadImages(type) {
		if($.trim($("#uploada").val()).length == 0) {
			showMessage("请选择要上传的附件");
			return false;
		} 
		var postfix = myForm.uploada.value.match(/^(.*)(\.)(.{1,8})$/)[3].toLowerCase();
		if (!(postfix == "bmp" || postfix == "gif" || postfix == "jpg" || postfix == "png")) {
			showMessage("附件只能上传图片");
			return false;
		} 
		$("#btnUploadFile").val("正在上传...");
     	$("#btnUploadFile").attr("disabled",true);
	  	$.ajaxFileUpload({
           url:'customerService/uploadImg?updateP='+type,             //需要链接到服务器地址
           secureuri:false,
           fileElementId:''+type+'',                     //文件选择框的id属性（必须）
           dataType: 'text',  
           success: function (data, status){  
           	var data = eval("("+data+")");
           	$("#btnUploadFile").attr("disabled",true);
  			$("#txtFilePath").val(data.fileName);
           	$("#btnUploadFile").val("已上传");
           	showMessage("附件已上传");				
           },
           error: function (data, status, e){  
        	   showMessage('上传失败、请稍后重试');
           }
	    });
	 }
	
	//保处售后问题处理
	function saveDisposal() {
		$("#txtNick2").val(getCurUser());
		if($.trim($("#txtCsiStatus").val()).length == 0) {
			showMessage("请选择处理状态");
		} else if($.trim($("#txtRemark").val()).length >= 150) {
			showMessage("描述信息过长");
		} else {
			$("#btnDisposal2").attr("disabled",true);
			$("#btnResetDisposal2").attr("disabled",true);
			$.ajax({
				type: 'post',
				url: "customerService/saveDisposal",
				dataType: 'json',
				data: $("#myForm").serialize(),
				beforeSend: function() {
					showDisplay();
				},
				success: function(msg) {
					hideDisplay();
					if(msg.result=='true') {
						detail('${customerServiceIssuesDetail[15]}');
						showMessage("售后问题已处理");
					} else {
						$("#btnDisposal2").attr("disabled", false);
						$("#btnResetDisposal2").attr("disabled", false);
						showMessage("售后问题处理失败");
					}
				},
				error: function() {
					hideDisplay();
				}
			});
		}
	}
	
	function chongxinkaishi() {
		$("#btnAdmeasure").removeClass("click");
		$("#btnDisposal").addClass("click");
		$("#divAdmeasure").hide();
		$("#divDisposal").show();
		$("#txtCsiStatus").val('5');
	}
</script>