<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/baseparam.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>欢迎您</title>
</head>
<body style="background: #b5defe">
<!--导航 -->
<div class="login_center">
    	<div class="loginForm">
    	<h1>用户登录</h1>
        	<!-- <form id="loginform" action="loginservice/login" method="post"> -->
        	 <form id="loginform" action="j_spring_security_check" method="post">
        	   
        	 	<div class="login_k">
        	 			<input type="hidden" id="sellerid" name="seller"/>
	    				<input type="hidden" id="nickid" name="nick"/>	
	    					<input type="hidden" id="j_username" name="j_username" />	
                       	<div class="login_sr"><span>用户名：</span><input name="username" type="text" id="username" value="admin<%-- <c:out value="${username}"></c:out> --%>"/></div>
                        <div class="login_erro"><span id="unerror"><c:out value="${unerror}"></c:out></span></div>
                        <div class="login_sr"><span>密码：</span><input name="j_password" type="password" id="j_password" value="admin" /></div>
                        <div class="login_erro" ><span id="pwderror"><c:out value="${pwderror}"></c:out></span></div>
                        <div class="login_sr"><span>验证码：</span><input name="" type="text" id="verCode" /><div id="yzmerror"></div></div>
                        <div class="login_yzm_prompt">请输入下图内容</div>
                        <div class="login_yzm"><img src="<%=basePath%>loginservice/getserailnumber" id="authImg" onclick="refresh()" /><span>看不清？</span><label onclick="refresh()">换一换</label></div>
      <!--             		<div class="login_password_choose"><input name="cookie" type="checkbox" value="save" /><span>记住密码</span></div> -->
                        <!-- <div class="login_password_prompt"><img src="resource/css/images/icon-img.gif" width="21" height="21" /><span>记住密码两周内自动登录</span></div> -->
                        
                        <div class="login_password_prompt">
                        
                        <input id="_spring_security_remember_me" name="_spring_security_remember_me" type="hidden" value="true" />  
                        <!-- 
                        <label for="_spring_security_remember_me" type="hidden">记住密码</label>  
                         -->
                        </div>
                        
                        
                        <div class="login_btn"><input class="btn" type="button" value="" style="margin:0" id="saveBtn"/></div>
	             </div>
            </form>
    </div>
</div>
<script type="text/javascript">

	var nick = null;
	var seller = null;
	$(function(){
		nick = $("#nick").val();
		seller = $("#seller").val();
		$("#sellerid").val(seller);
		$("#nickid").val(nick);
		$('#saveBtn').click(function(){
			checkVerCode();
		});
	});
	var flag=null;
	function login(){
		var username = $('#username').val();
		var password = $('#j_password').val();
		var varCode = $('#verCode').val();
		$('#j_username').val("");
		$('#j_username').val(username+","+nick);
		if(username.length<1){
			$("#unerror").html('用户名不能为空!');
			return false;
		}
		$("#unerror").html('');
		if(password.length<1){
			$("#pwderror").html('密码不能为空!');
			return false;
		}
		$("#pwderror").html('');
		if(varCode.length<1){
			$("#yzmerror").html('验证码不能为空!');
			return false;
		}
		$("#yzmerror").html('');
		
		return true;
	}
				
	function loadimage(){ 
		document.getElementById("randImage").src = "image.jsp?"+Math.random(); 
	}
	
	function refresh(){
		$('#authImg').attr('src',"<%=request.getContextPath()%>/loginservice/getserailnumber?now="+new Date());
	}
	function checkVerCode(){
		
		var verCode=$('#verCode').val();
		if(login()){
			$.ajax({
				type : "get",
				dataType: "json",
				data:"verCode="+verCode,
				url : "loginservice/checkserailnumber",
				success : function(data){
					if(data.result=="yes"){
						$('#loginform').submit();
					}else{
						refresh();
						$("#yzmerror").html('验证码错误!');
					}
				}
			});
		}
		
	}
	
		

</script>
<!--工作台 -->
</body>
</html>
