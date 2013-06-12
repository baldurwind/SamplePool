<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<%@ include file="/WEB-INF/jsp/admin/common/header.jsp" %>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>欢迎您</title>

<script type="text/javascript">
	var seller = null;
	
	$(function(){
		//$("#sellerid").val(seller);
		$('#saveBtn').click(function(){
			checkVerCode();
		});
		
		
	});
	var flag=null;
	function login(){
		var seller = $('#seller').val();
		seller = seller.replaceAll(" ","");
		var username = $('#username').val();
		username = username.replaceAll(" ","");
		var password = $('#password').val();
		var varCode = $('#yzm').val();
		if(seller.length<1){
			$("#slerror").html('店铺名不能为空!');
			return false;
		}
		$("#slerror").html('');
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
		$('#authImg').attr('src',"${basePath}admin/getserailnumber?now="+new Date());
	}
	function checkVerCode(){
		var verCode=$('#yzm').val();
		if(login()){
			$.ajax({
				type : "get",
				dataType: "json",
				data:"verCode="+verCode,
				url : "admin/checkserailnumber",
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
</head>
<body>
	<div class="login_div login_bg">
    	<div class="loginContiner">
        	<div class="login_logo"><img src="${static_resource_host}/resource/css/images/tgx-logo.jpg" width="83" height="86" /><span>CHAMAGO</span><b>淘个性</b><span>V2.0</span></div>
            
            <div class="login_center">
            	<a class="login_gw" href="http://www.chamago.com/" target="_blank"></a>
                <div class="loginForm" style="display:none">
                	<form id="loginform" action="admin/login" method="post">
                		<h1>用户登录</h1>
                  		<div class="login_k">
                  			<!--  
                  			<div class="login_sr"><span>店铺名：</span><input name="seller" type="text" id="seller" value="<c:out value="${seller}"></c:out>"/></div>
                        	-->
                        	<div class="login_sr"><span>店铺名：</span><input name="seller" type="text" id="seller" value=""/></div>
                   
                        	<div class="login_erro"><span id="slerror"><c:out value="${slerror}"></c:out></span></div>
                        	<div class="login_sr"><span>用户名：</span><input name="username" type="text" id="username" value="<c:out value="${username}"></c:out>"/></div>
                            <div class="login_erro"><span id="unerror"><c:out value="${unerror}"></c:out></span></div>
                            <div class="login_sr"><span>密码：</span><input name="password" type="password" id="password" /></div>
                            <div class="login_erro" ><span id="pwderror"><c:out value="${pwderror}"></c:out></span></div>
                            <div class="login_sr"><span>验证码：</span><input name="" type="text" id="yzm" /><div id="yzmerror"></div></div>
                            <div class="login_yzm_prompt">请输入下图内容</div>
                            <div class="login_yzm"><img src="${basePath}admin/getserailnumber" id="authImg" onclick="refresh()" /><span>看不清？</span><label onclick="refresh()">换一换</label></div>
                      		<div class="login_password_choose"><input name="cookie" type="checkbox" value="save" /><span>记住密码</span></div>
                            <div class="login_password_prompt"><img src="${static_resource_host}/resource/css/images/icon-img.gif" width="21" height="21" /><span>记住密码两周内自动登录</span></div>
                            <div class="login_btn"><input class="btn" type="button" value="" style="margin:0" id="saveBtn"/></div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="clear"></div>
            <div class="login_about"></div>
      	</div>
        
    </div>
    <div class="login_div">
    	<div class="loginContiner">
        	<div class="login_foot">
            	<img src="${static_resource_host}/resource/css/images/cmg-logo.gif" width="73" height="42" />
            	<ul>
                	<li style="margin-top:0px;">上海茶马古道电子商务有限公司</li>
                	<li class="color_g">Copyright 2009-2011 Chamago,All Rights Reserved.  沪B2-20100062</li>
                </ul>
            </div>
    	</div>
    </div>
</body>
</html>

