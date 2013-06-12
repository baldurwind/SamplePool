<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<div class="order_mianban">
   	  <div class="order_mianban_con">
   	    <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="24%">关闭交易理由：</td>
                <td width="76%">
                  <select name="reason" id="reason" style="width:180px;">
                   <option value="买家不想买了">买家不想买了</option>
					<option value="信息填写错误 重新拍">信息填写错误 重新拍</option>
					<option value="卖家缺货">卖家缺货</option>
					<option value="同城见面交易">同城见面交易</option>
					<option value="其他原因">其他原因</option>
                  </select>
                </td>
              </tr>
            </table>
		</div>
	</div>
<script>
function close(tid){
	$('#close').dialog({autoOpen: true,modal: true,title:"订单号："+tid,width: 300,height:200,resizable:false,
		buttons: {
		"提交": function() { 
		$.ajax({
			url:'trade/close.json?seller='+$('#seller').val()+"&tid="+tid+"&reason="+$('#reason').val(),
			dataType:'json',
			beforeSend: function(XMLHttpRequest) {
				   showDiv('status_div','提交中...','关闭订单');
			    	   
			},
			success: function(result){
				if(result.result==false){
					$('#status_div').html("<div class='ips_loading'>订单"+tid+" 关闭失败,"+result.msg+"</div>");		
				}

				else{
					$('#status_div').html("<div class='ips_loading'>订单"+tid+"关闭成功。</div>");
					$('#close').dialog('close');
				}
					
			}
		});
		},
		"关闭": function() {$(this).dialog("close");}}
	});
}
</script>