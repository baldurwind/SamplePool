<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/include.jsp" %>
<script type="text/javascript" src="${bathPath}resource/script/ajaxfileupload.js"></script>

<div class="order_mianban">
   	  <h3 class="orderlist_title">提交售后问题</h3>
	 <div class="addServiceInfo">
	 <form method="post" id="myForm" name="myForm">
		  	<input type="hidden" name="tradeId" id="txtTradeId" value="${tradeId}"/>
	 		<input type="hidden" name="filePath" id="txtFilePath">
	 		<input type="hidden" name="sellerNick" id="txtSellerNick"/>
	 		<input type="hidden" name="nick" id="txtNick"/>
	 		<input type="hidden" name="buyerNick" id="txtBuyer">
		   <table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:12px;">
	         <tr>
	           <td width="25%">售后类型：</td>
	           <td width="75%">
	             <select name="type" id="txtType" style="width:180px;">
	                <c:forEach items="${CSITypeList}" var="type">
						<option value="${type.id}">${type.name}</option>
					</c:forEach>
	             </select>
	             <select name="priority" id="txtPriority" style="width:119px;">
	                 <option value="0">紧急程度高</option>
	                 <option value="1">紧急程度中</option>
	                 <option value="2">紧急程度低</option>
	             </select>
	             </td>
	           </tr>
	           <tr>
	           	<td>问题标题：</td>
	           	<td height="36"><input type="text" style="width:303px;" name="title" id="txtTitle" maxlength="100"/></td>
	           </tr>
	           <tr>
	             <td valign="top">问题描述：</td>
	             <td><label>
	             <textarea name="content" id="txtContent" cols="30" rows="5" maxlength="200"></textarea>
	             </label>            </td>
	          </tr>
	          <tr>
	            <td>添加附件：</td>
	             <td height="50">
	             <input type="file"  id="uploada" name="uploada" />&nbsp;<input type="button" style="width: 5em" id="btnUploadFile" onclick="uploadImages('uploada')" value='上传'/>
	             </td>
	          </tr>
	           <tr>
	             <td>&nbsp;</td>
	             <td height="50">
	             	<p class="publicFormBtn" id="btnSaveCS" onclick="saveCustomerServiceIssue();">保存</p><p class="publicFormBtn pfb-glay" id="btnResetSave"  onclick="formReset();">重置</p>
	             </td>
	           </tr>
	         </table>
         </form>
	</div>
</div>

<script type="text/javascript">
	//保存售后问题
	function saveCustomerServiceIssue() {
	  	if($.trim($("#txtType").val()).length == 0) {
	  		showMessage("请选择售后问题类型");
	  	} else if ($.trim($("#txtPriority").val()).length == 0) {
	  		showMessage("请选择售后问题的紧急程度");
	  	} else if ($.trim($("#txtTitle").val()).length == 0) {
	  		showMessage("请输入售后问题标题");
	  	} else if ($.trim($("#txtContent").val()).length == 0) {
	  		showMessage("请输入售后问题描述");
	  	} else if ($.trim($("#txtContent").val()).length >= 150){
	  		
	  	} else {
	  		$("#txtSellerNick").val($('#seller').val());
	  		$("#txtNick").val($('#nick').val());
	  		$("#txtBuyer").val($('#buyer').val());
	  		$("#btnSaveCS").attr("disabled",true);
	  		$("#btnResetSave").attr("disabled",true);
	  		$.ajax({
	  			type: 'post',
	  			url: "customerService/save",
	  			dataType: 'json',
	  			data: $("#myForm").serialize(),
	  			beforeSend: function() {
	  				showDisplay();
	  			},
	  			success: function(msg) {
	  				hideDisplay();
	  				if(msg.result=='true') {
	  					showMessage("添加成功");
	  					detail(msg.id);
	  				} else {
	  					$("#btnSaveCS").attr("disabled", false);
	  			  		$("#btnResetSave").attr("disabled", false);
	  			  		showMessage("添加失败，请稍候重试");
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
	         	$("#btnUploadFile").val("已上传");
	         	$("#btnUploadFile").attr("disabled",true);
	         	showMessage("附件已上传");
				$("#txtFilePath").val(data.fileName);				
	         },
	         error: function (data, status, e){  
	        	 showMessage('上传失败、请稍候重试');
	         }
	    });
	}
</script>