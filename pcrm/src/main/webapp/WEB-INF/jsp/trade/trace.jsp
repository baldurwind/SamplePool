<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<head></head>
<body>
<c:choose>
	<c:when test="${empty traceList}"><div class='tips_wujilu'>${msg}</div></c:when>
	<c:otherwise>
   	  <div class="order_mianban_con">快递公司:
   	   <strong>${company}</strong><br />                         
		运单号码: <strong>${tid}</strong>  
      	<p id="trace_text" class="total_text">快件跟踪：以下信息由物流公司提供<br />
		<c:forEach var="trace" items="${traceList}">${trace.statusTime} ${trace.statusDesc} <br /></c:forEach>
		</p>
      </div>
	</c:otherwise>
</c:choose>
 <input id="ww_msg_plugin" type="hidden" value="" />	
</body>
<script>
function ww_msg_send_1(){
	 var msg = arguments[0];
	 msg = msg.toLowerCase().replaceAll("<br>"," ");
	 document.getElementById("ww_msg_plugin").setAttribute("value",msg);
	 document.getElementById("ww_msg_plugin").click();
}


function IPlugin( type , msg ){
if( type == "id")
  return "ww_msg_plugin";
else if (type == "msg")
    return (document.getElementById("ww_msg_plugin").getAttribute("value"));
else 
    return ("other --- type:"+type+",msg:"+msg);
}
</script>
</html>