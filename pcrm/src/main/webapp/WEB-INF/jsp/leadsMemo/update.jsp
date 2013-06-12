<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/include.jsp" %>
<script src="${static_resource_host}/resource/script/plug.js" type="text/javascript"></script>
<div class="proRegister" id="proRegister">
 	<!--<a href="##" class="btn default">查看缺货商品</a>-->
  	<h1 class="tit">事件登记</h1>
     <div id="reged_pro">
     	<h3 class="head">登记商品</h3>
       		<div class="editEventForm">
            	<p class="leftImg"> <img  src="${itemSearch.picUrl}" width="70" height="70"/> </p>
                <div class="rightCpInfo">
                	<h3 class="bold">${itemSearch.title}</h3>
                	<c:forEach items="${current_props}" var="props">
                		<p>${props.key}：${props.value}</p>
                	</c:forEach>
                </div>
             <div class="clear"></div>   
            </div>
         <div class="regForm">
         	<h3 class="bold">到货通知方式</h3>
            <form id="myForm" name="myForm">
            		 <input type="hidden" value="${leadsMemo.numId}" id="txtNumId" name="numId">
            		 <input type="hidden" value="${leadsMemo.skuId}" id="txtSkuId" name="skuId"> 
		   			 <input type="hidden" id="txtId" name="id" value="${leadsMemo.id}"/>
		   			 <input type="hidden" value="${leadsMemo.sellerNick}" id="txtSellerNick" name="sellerNick"/>
		             <table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:12px;">
		             	<tr>
		                 	<td width="20%" align="center">期望价格：</td>
		                     <td><input class="textstyle" id="txtPrice" name="price" value="${leadsMemo.price}" maxlength="10"/></td>
		                </tr>
		             	<tr>
		                 	<td width="20%" align="center">短信通知：</td>
		                     <td><input class="textstyle" id="txtMobile" name="mobile" value="${leadsMemo.mobile}" maxlength="11"/></td>
		                 </tr>
		                 <tr>
		                 	<td width="20%" align="center">邮件通知：</td>
		                     <td><input class="textstyle" id="txtEmail" name="email" value="${leadsMemo.email}" maxlength="30"/></td>
		                 </tr>
		                 <tr>
		                 	<td width="20%" align="center">通知截止：</td>
		                     <td><input class="textstyle" id="txtExpiredDate" name="expiredDate1" value="<fmt:formatDate value="${leadsMemo.expiredDate}" pattern="yyyy-MM-dd HH:mm:ss" type="both"/>" readonly="readonly"  class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',disabledDates:['%y-%M-%d {%H-1}\:..\:..','%y-%M-%d {%H+1}\:..\:..']})"/></td>
		                 </tr>
		                 <tr>
		                 	<td width="20%" align="center"></td>
		                     <td><input type="checkbox" style="vertical-align:middle" name="addReminder" <c:if test="${leadsMemo.addReminder == true}">checked="checked"</c:if>/><label style="vertical-align:middle"> 新增到我的备忘录中，到货后提醒我与客户联系</label></td>
		                 </tr>
		                 <tr>
		                 	<td width="20%" align="center"></td>
		                     <td>
		                     <p class="publicFormBtn" id="btnUpdateLeadsMemo" onclick="updateLeadsMemo();">修改</p>
		                     <p class="publicFormBtn  pfb-yellow" id="btnUpdateBack" onclick="toIndex();">返回</p>
		                 </tr>
		            </table>
             </form>
     </div>
 </div>
 </div>
 <script type="text/javascript">	 
	  //修改缺货/促销事件登记
	  function updateLeadsMemo() {
		  if($.trim($("#txtPrice").val()).length > 0) {
				//验证金额
				var reg = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
				if(!reg.test($("#txtPrice").val())) {
					showMessage("输入的金额格式不正确");
					return;
				}
			} 
			if($.trim($("#txtMobile").val()).length > 0) {
				//验证手机
				var reg = new RegExp("^(?:0[0-9]{2,3}[-\\s]{1}|\\(0[0-9]{2,4}\\))[0-9]{6,8}$|^[1-9]{1}[0-9]{5,7}$|^[1-9]{1}[0-9]{10}$");
				if(!reg.test($("#txtMobile").val())) {
					showMessage("手机号格式不正确");
					return;
				}
			} 
			if ($.trim($("#txtEmail").val()).length >0) {
				//验证邮箱
				var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
				if(!reg.test($("#txtEmail").val())) {
					showMessage("电子邮箱格式输入不正确");
					return;
				}
			} 
			if ($.trim($("#txtMobile").val()).length == 0 && $.trim($("#txtEmail").val()).length == 0) {
				showMessage("手机号码或电子邮箱请至少输入一项");
				return;
			}
			if($.trim($("#txtExpiredDate").val()).length == 0) {
				showMessage("请选择到期时间");
				return;
			}  
			$("#btnUpdateLeadsMemo").attr("disabled",true);
			$("#btnUpdateBack").attr("disabled",true);
		  	$.ajax({
		  		type: 'post',
		  		url: "leadsMemo/update",
		  		dataType: 'json',
		  		data: $("#myForm").serialize(),
		  		beforeSend: function() {
		  			showDisplay();
		  		},
		  		success: function(msg) {
		  			hideDisplay();
		  			if(msg.result=='true') {
		  				promotionList();
		  				showMessage("修改成功");
		  			} else {
		  				$("#btnUpdateLeadsMemo").attr("disabled",false);
		  				$("#btnUpdateBack").attr("disabled",false);
		  				showMessage("修改失败、请稍后重试");
		  			}
		  		},
		  		error: function() {
		  			hideDisplay();
		  		}
		  	});
	  }
  </script>