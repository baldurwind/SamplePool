<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/include.jsp" %>
<!--添加备忘-->
<div class="addBeiwang">
 <h3 class="head">新增备忘提醒</h3>
    <div class="addbeiwangform">
    <form name="myForm" id="myForm">
    	<input type="hidden" value="${reminder.id}" name="id"/>
    	 <table border="0" cellpadding="0" cellspacing="0" width="100%">
        	<tr>
           	<td width="45%"><input type="radio" value="0" name="tipType" checked="checked" style="vertical-align:middle"/><label  style="vertical-align:middle">&nbsp;到期提醒的备忘</label></td>
               <td width="65%"><!--<input type="checkbox" style="vertical-align:middle"/> <label  style="vertical-align:middle">&nbsp;按条件提醒的备忘</label> --></td>
           </tr>
           <tr>
           	<td colspan="2"><span class="bold">提醒时间：</span>
           		<input type="text" id="txtTipTime" name="tipTime1" value="<fmt:formatDate value="${reminder.tipTime}" pattern="yyyy-MM-dd HH:mm:ss" type="both"/>" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',disabledDates:['%y-%M-%d {%H-1}\:..\:..','%y-%M-%d {%H+1}\:..\:..']})" readonly="readonly"/>
           	</td>
           </tr>
           <tr>
           	<td colspan="2">
           		  <c:choose>
           		  	<c:when test="${reminder.buyerNick != null && reminder.buyerNick != ''}">
           		  		<input type="checkbox" checked="checked"  value="${reminder.buyerNick}" id="ckCurrentCustomerNick" name="buyerNick" style="vertical-align:middle"/><label  style="vertical-align:middle">&nbsp;关联当前客户&nbsp;&nbsp;&nbsp;&nbsp;</label><span class="wwid">旺旺ID:${reminder.buyerNick}</span>
           		  	</c:when>
                    <c:otherwise>
                    	<input type="checkbox" value="${buyerNick}" id="ckCurrentCustomerNick" name="buyerNick" style="vertical-align:middle"/><label  style="vertical-align:middle">&nbsp;关联当前客户&nbsp;&nbsp;&nbsp;&nbsp;</label><span class="wwid">旺旺ID:${buyerNick}</span>
                    </c:otherwise>
                  </c:choose>
               </td>
           </tr>
           <tr>
           	<td colspan="2">
               	<span class="bold">提醒内容：</span><br />
               <textarea rows="3" cols="35" id="txtContent" name="content">${reminder.content}</textarea>
               </td>
           </tr>
           <tr>
           	<td colspan="2"><p class="publicFormBtn" id="btnUpdateReminder" onclick="updateReminder();">修改</p><p class="publicFormBtn pfb-glay" id="btnUpdateBack" onclick="toIndex();">返回</p></td>
           </tr>
        </table>
     </form>
    </div>
</div>
  
<script type="text/javascript">
  //修改客户备忘信息
  function updateReminder() {
	if($.trim($("#txtTipTime").val()).length == 0) {
		showMessage("请选择提醒时间");
	} else if ($.trim($("#txtContent").val()).length == 0) {
		showMessage("请输入提醒内容");
	} else if($.trim($("#txtContent").val()).length >= 1000) {
		showMessage("提醒内容信息输入过长");
	} else {
		$("#btnUpdateReminder").attr("disabled",true);
		$("#btnUpdateBack").attr("disabled",true);
	  	$.ajax({
	  		type: 'post',
	  		url: "reminder/update",
	  		dataType: 'json',
	  		data: $("#myForm").serialize(),
	  		beforeSend: function() {
	  			showDisplay();
	  		},
	  		success: function(msg) {
	  			hideDisplay();
	  			if(msg.result=='true') {
	  				showMessage("修改成功");
	  				toIndex();
	  			} else {
	  				$("#btnUpdateReminder").attr("disabled",false);
	  				$("#btnUpdateBack").attr("disabled",false);
	  				showMessage("修改失败，请稍候重试");
	  			}
	  		},
	  		error: function() {
	  			hideDisplay();
	  		}
	  	});
  	}
  }
</script>
</html>
