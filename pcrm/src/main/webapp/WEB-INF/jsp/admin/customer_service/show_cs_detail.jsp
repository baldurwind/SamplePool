<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/admin/common/include.jsp"%>
<script type="text/javascript" src="${bathPath}resource/script/ajaxfileupload.js"></script>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="2"><strong>问题描述</strong>：${customerServiceIssuesDetail[3]}
			<c:if
				test="${customerServiceIssuesDetail[6] != null && customerServiceIssuesDetail[6] != ''}">	
		 		&nbsp;&nbsp;<a
					href="customerService/admin/downloadFile?fileName=${customerServiceIssuesDetail[6]}&seller=${seller}"><img
					src="${static_resource_host}/resource/images/uploader.gif" alt="下载附件" width="15"
					height="17" /></a>
			</c:if></td>
	</tr>
	<tr>
		<td valign="top">
			<!--售后处理过程 -->
			<table width="100%" cellspacing="0" cellpadding="0" border="0"
				class="seviceDetail_table">
				<tbody>
					<tr>
						<td width="18%" height="30" class="sevice_title"><strong>处理人</strong></td>
						<td width="24%" class="sevice_title"><strong>结果</strong></td>
						<td width="58%" class="sevice_title"><strong>备注</strong></td>
					</tr>
					<c:forEach items="${csiDetailList}" var="detail" varStatus="idx">
						<tr>
							<td>${detail[2]}</td>
							<td>${detail[3]} <c:choose>
									<c:when test="${detail[8] == 1}">
										<i class="right greenico"></i>
									</c:when>
									<c:when test="${detail[8] == 2}">
										<i class="right yellowico"></i>
									</c:when>
									<c:when test="${detail[8] == 3}">
										<i class="right redico"></i>
									</c:when>
								</c:choose>
							</td>
							<td>${detail[4]} <c:if
									test="${detail[5] != null}">
									<a href="customerService/downloadFile?fileName=${detail[5]}"><img
										src="${static_resource_host}/resource/images/uploader.gif" /></a>
								</c:if> <c:if test="${detail[7] == 4}">
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</td>
		<td valign="top">
			<c:if test="${customerServiceIssuesDetail[13] == userId}">
				<!--售后问题的分配和处理 start-->
				<div class="submitForm serviceChuliForm">
					<ul>
						<li class="click">处理</li>
						<li>分配</li>
					</ul>
					<span style="clear: both; margin: 0"></span>
					<div class="serChuliFrorm">
						<form id="myForm${customerServiceIssuesDetail[15]}" name="myForm">
							<input type="hidden" value="${customerServiceIssuesDetail[15]}"
								name="csiId" id="txtCsiId" /> <input type="hidden"
								name="filePath"
								id="txtFilePath${customerServiceIssuesDetail[15]}"> <input
								type="hidden" name="nick" id="txtNick2${customerServiceIssuesDetail[15]}" value="">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="20%" height="30">售后结果：</td>
									<td width="80%"><select
										id="txtCsiStatus${customerServiceIssuesDetail[15]}"
										name="status" style="width: 180px;">
											<c:forEach items="${statusList}" var="status">
												<option value="${status.id}">${status.name}</option>
											</c:forEach>
									</select></td>
								</tr>
								<tr>
									<td width="20%" height="30" valign="top">问题描述：</td>
									<td width="80%"><textarea
											style="width: 100%; height: 100px;" name="remark"
											id="txtRemark${customerServiceIssuesDetail[15]}"></textarea></td>
								</tr>
								<tr>
									<td width="20%" height="30" valign="top"></td>
									<td width="80%"><input type="file"
										id="uploada${customerServiceIssuesDetail[15]}"
										name="uploada${customerServiceIssuesDetail[15]}" />&nbsp;<input
										type="button" style="width: 5em"
										id="btnUploadFile${customerServiceIssuesDetail[15]}"
										onclick="uploadImages('uploada${customerServiceIssuesDetail[15]}', '${customerServiceIssuesDetail[15]}')"
										value='上传' /></td>
								</tr>
								<tr>
									<td width="20%" height="30" valign="top"></td>
									<td width="80%"><input type="button" id="btnDisposal2${customerServiceIssuesDetail[15]}"
										class="btn" value="提交"
										onclick="saveDisposal('${customerServiceIssuesDetail[15]}');" />&nbsp;&nbsp;
										<input type="button" class="btn" value="重置" id="btnFormReset${customerServiceIssuesDetail[15]}"
										onclick="formReset('${customerServiceIssuesDetail[15]}');" /></td>
								</tr>
							</table>
						</form>
					</div>
					<div class="serChuliFrorm" style="display: none">
						<form name="admeasureForm" id="admeasureForm${customerServiceIssuesDetail[15]}">
							<input type="hidden" name="id" value="${customerServiceIssuesDetail[15]}" />
							<p>
								分配给：<select style="width: 120px;" id="txtCsUser${customerServiceIssuesDetail[15]}" name="csNick">
											<option value="">请选择客服</option>
											<c:forEach items="${users}" var="user">
												<option value="${user[0]}">${user[1]}</option>
											</c:forEach>		
										</select>
							</p>
							<p>
								<input type="button" id="btnAdmeasure2${customerServiceIssuesDetail[15]}" class="btn" onclick="doUserAdmeasure('${customerServiceIssuesDetail[15]}');" value="提交"/>&nbsp;&nbsp;
							</p>
						</form>
					</div>
				</div> <!--售后问题的分配和处理 end-->
			</c:if>
		</td>
	</tr>
</table>

<script>
	//ajax上传图片附件
	function uploadImages(type, id) {
		if ($.trim($("#uploada" + id).val()).length == 0) {
			alert("请选择要上传的附件");
			return false;
		}
		var picUrl = $.trim($("#uploada" + id).val());
		var postfix = picUrl.match(/^(.*)(\.)(.{1,8})$/)[3].toLowerCase();
		if (!(postfix == "bmp" || postfix == "gif" || postfix == "jpg" || postfix == "png")) {
			alert("附件只能上传图片");
			return false;
		}
		$.ajaxFileUpload({
			url : 'customerService/admin/uploadImg?updateP=' + type, //需要链接到服务器地址
			secureuri : false,
			fileElementId : '' + type + '', //文件选择框的id属性（必须）
			dataType : 'text',
			success : function(data, status) {
				var data = eval("(" + data + ")");
				$("#btnUploadFile" + id).attr("disabled", true);
				$("#txtFilePath" + id).val(data.fileName);
				$("#btnUploadFile" + id).val("已上传");
				alert("附件已上传");
			},
			error : function(data, status, e) {
				alert('上传失败');
			}
		});
	}

	//保处售后问题处理
	function saveDisposal(id) {
		$("#txtNick2" + id).val(getCurUser());
		if ($.trim($("#txtCsiStatus" + id).val()).length == 0) {
			alert("请选择处理状态");
		} else if ($.trim($("#txtRemark" + id).val()).length >= 150) {
			alert("描述信息过长");
		} else {
			$("#btnDisposal2" + id).attr("disabled", true);
			$("#btnFormReset" + id).attr("disabled", true);
			$.ajax({
				type : 'post',
				url : "customerService/admin/saveDisposal",
				dataType : 'json',
				data : $("#myForm" + id).serialize(),
				beforeSend : function() {
				},
				success : function(msg) {
					if (msg.result == 'true') {
						changeStatus('${customerServiceIssuesDetail[0]}', '${customerServiceIssuesDetail[15]}');
						getCsDetail('${customerServiceIssuesDetail[15]}');
						alert("售后问题已处理");
					} else {
						$("#btnDisposal2" + id).attr("disabled", false);
						$("#btnFormReset" + id).attr("disabled", false);
						alert("售后问题处理失败");
					}
				},
				error : function() {
				}
			});
		}
	}

	function formReset(id) {
		$("#myForm" + id)[0].reset();
		$("#btnUploadFile" + id).attr("disabled", false);
		$("#btnUploadFile" + id).val("上传");
		$("#uploada" + id).val("");
		if ($.trim($("#txtFilePath" + id).val()).length > 0) {
			deleteUploadFile($("#txtFilePath" + id).val());
		}
		$("#txtFilePath" + id).val("");
	}

	function deleteUploadFile(fileName) {
		$.ajax({
			type : 'post',
			url : "customerService/deleteFile",
			dataType : 'json',
			data : {
				"fileName" : fileName
			}
		});
	}

	function loadCSListByGroup(id) {
		var group = $("#txtGroup" + id).val();
		if ($.trim(group).length > 0) {
			$.ajax({
				type : 'post',
				url : "customerService/admin/loadCSListByGroup",
				dataType : 'json',
				data : {
					"group" : group
				},
				beforeSend : function() {
				},
				success : function(msg) {
					$("#txtCsList" + id).empty();
					if (msg.result == 'true') {
						$(msg.message).appendTo("#txtCsList" + id);
					}
				},
				error : function() {
				}
			});
		} else {
			$("#txtCsList" + id).empty();
		}
	}
	
	//执行售后问题分配(用户)
	function doUserAdmeasure(id) {
	  	var csId = $.trim($("#txtCsUser" + id).val());
	  	if(csId.length == 0) {
	  		alert("请选择客服");
	  	} else {
	  		$("#btnAdmeasure2" + id).attr("disabled", true);
	  		$.ajax({
	  			type: 'post',
	  			url: "customerService/admin/saveAdmeasure",
	  			dataType: 'json',
	  			data: $("#admeasureForm" + id).serialize(),
	  			beforeSend: function() {
	  			},
	  			success: function(msg) {
	  				if(msg.result=='true') {
	  					getCsDetail('${customerServiceIssuesDetail[15]}');
	  					alert("分配成功");
	  				} else {
	  					$("#btnAdmeasure2").attr("disabled", false);
	  					alert("分配失败");
	  				}
	  			},
	  			error: function() { }
	  		});
	  	}
	}
</script>