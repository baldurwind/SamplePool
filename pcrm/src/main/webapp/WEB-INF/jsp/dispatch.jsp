<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<!--工作台 -->
<input  id="seller" value="${seller }" type="hidden"/>  <!-- stevemadden旗舰店 芳蕾玫瑰粉粉旗舰店 -->
<input  id="buyer" value="${buyer }" type="hidden"/>  <!-- 芳蕾玫瑰粉粉旗舰店 晶典珠宝11  wccctest01  3994226377  良无限_非洲菊-->
<input  id="numiid" value="${numiid }" type="hidden"/>
<input  id="nick" value="${nick }" type="hidden"/>
<input  id="tid" value="${tid }" type="hidden"/>
<input  id="username" value="${username }" type="hidden"/>
<input  id="password" value="${password }" type="hidden"/>
<script type="text/javascript">
$(document).ready(function () {
	var seller = $("#seller").val();
	var nick = $("#nick").val();
	var buyer = $("#buyer").val();
	var numiid = $("#numiid").val();
	var target = $("#target").val();
	var cookUser = $("#username").val();
	var md5Pwd = $("#password").val();
	form = $("<form></form>");
	form.attr('action',contextPath+"j_spring_security_check");
	form.attr('method','post');
	var input1 = $("<input type='hidden' name='seller' value='"+seller+"'/>");
	var input2 = $("<input type='hidden' name='nick' value='"+nick+"'/>");
	var input3 = $("<input type='hidden' name='buyer' value='"+buyer+"'/>");
	var input4 = $("<input type='hidden' name='numiid' value='"+numiid+"'/>");
	var input5 = $("<input type='hidden' name='username' value='"+cookUser+"'/>");
	var input8 = $("<input type='hidden' name='j_username' value='"+cookUser+","+nick+"'/>");
	var input6 = $("<input type='hidden' name='j_password' value='"+md5Pwd+"'/>");
	var input7 = $("<input id='_spring_security_remember_me' name='_spring_security_remember_me' type='checkbox' value='true'/> ");
	 
	form.append(input1);
	form.append(input2);
	form.append(input3);
	form.append(input4);
	form.append(input5);
	form.append(input6);
	form.append(input7);
	form.append(input8);
	form.css('display','none');
	form.appendTo("body");
	/* alert(seller);
	alert(buyer);
	alert(numiid); */
	form.submit();
	
});
	
</script>

