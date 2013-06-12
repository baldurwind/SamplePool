<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/include.jsp" %>
	<!--添加备忘-->
<div class="addBeiwang">
	 <h3 class="head">新增备忘提醒</h3>
     <div class="addbeiwangform">
     <form name="myForm" id="myForm">
     	<input type="hidden" value="${sellerNick}" name="sellerNick"/>
     	<input type="hidden" value="${nick}" name="nick"/>
     	 <table border="0" cellpadding="0" cellspacing="0" width="100%">
         	<tr>
            	<td width="45%"><input type="radio" value="0" name="tipType" checked="checked" style="vertical-align:middle"/><label  style="vertical-align:middle">&nbsp;到期提醒的备忘</label></td>
                <td width="65%"><!--<input type="checkbox" style="vertical-align:middle"/> <label  style="vertical-align:middle">&nbsp;按条件提醒的备忘</label> --></td>
            </tr>
            <tr>
            	<td colspan="2"><span class="bold">提醒时间：</span>
            		<input type="text" id="txtTipTime" name="tipTime1" readonly="readonly" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'})"/>
            	</td>
            </tr>
            <tr>
            	<td colspan="2">
                   <input type="checkbox" value="${buyerNick}" id="ckbuyerNick" name="buyerNick" style="vertical-align:middle"/><label  style="vertical-align:middle">&nbsp;关联当前客户&nbsp;&nbsp;&nbsp;&nbsp;</label><span class="wwid">旺旺ID:${buyerNick}</span>
                </td>
            </tr>
            <tr>
            	<td colspan="2">
                	<span class="bold">提醒内容：</span><br />
                	<textarea rows="3" cols="35" id="txtContent" name="content"></textarea>
                </td>
            </tr>
            <tr>
            	<td colspan="2"><p class="publicFormBtn" id="btnAddReminder" onclick="saveReminder();">保存</p><p class="publicFormBtn pfb-glay" id="btnAddReset" onclick="resetForm();">重置</p><p class="publicFormBtn  pfb-yellow" id="btnAddBack" onclick="toIndex();">返回</p></td>
            </tr>
         </table>
      </form>
     </div>
</div>

<script type="text/javascript">
	  //保存客服备忘
	  function saveReminder() {
		if($.trim($("#txtTipTime").val()).length == 0) {
			showMessage("请选择提醒时间");
		} else if ($.trim($("#txtContent").val()).length == 0) {
			showMessage("请输入提醒内容");
		} else if($.trim($("#txtContent").val()).length >= 1000) {
			showMessage("提醒内容信息输入过长");
		} else {
			$("#btnAddReminder").attr("disabled",true);
			$("#btnAddReset").attr("disabled",true);
			$("#btnAddBack").attr("disabled",true);
		  	$.ajax({
		  		type: 'post',
		  		url: "reminder/save",
		  		dataType: 'json',
		  		data: $("#myForm").serialize(),
		  		beforeSend: function() {
		  			showDisplay();
		  		},
		  		success: function(msg) {
		  			hideDisplay();
		  			if(msg.result=='true') {
		  				showMessage("添加成功");
		  				toIndex();
		  			} else {
		  				$("#btnAddReminder").attr("disabled",false);
		  				$("#btnAddReset").attr("disabled",false);
		  				$("#btnAddBack").attr("disabled",false);
		  				showMessage("添加失败，请稍候重试");
		  			}
		  		},
		  		error: function() {
		  			hideDisplay();
		  		}
		  	});
		  }
	  }
	  
	  function resetForm() {
		  $("#myForm")[0].reset();
	  }
  </script>