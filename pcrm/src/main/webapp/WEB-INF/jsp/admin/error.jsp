<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<%@ include file="/WEB-INF/jsp/admin/common/header.jsp" %>
<%
String context = request.getContextPath();
String contextPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+context+"/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>提示页面</title>
<script type="text/javascript">
	setTimeout(function(){
		parent.location.href = "<%=contextPath%>admin";
	},5000);
	 
</script>
</head>
<body>
	<div class="login_div login_bg">
    	<div class="loginContiner">
        	<div class="login_logo"><span>CHAMAGO</span><b>淘个性</b><span>V2.0</span></div>
            <div class="error">
       	   		<img src="${static_resource_host}/resource/css/images/error-icon.gif" width="65" height="65" />
                <ul> 
                	<li><b>很抱歉！</b></li>
                    <li><b>您需要重新登录！</b></li>
                    <li>1.页面5秒后会自动跳转</li>
                    <li>2.您可以点这里直接跳转<a href="<%=contextPath%>admin">去登录</a></li>
                 </ul>
            </div>
            
            <div class="clear"></div>
            <div class="login_about"></div>
      	</div>
        
    </div>
    <div class="login_div">
    	<div class="loginContiner">
        	<div class="login_foot">
            	<img src="${static_resource_host}/resource/images/cmg-logo.gif" width="73" height="42" />
            	<ul>
                	<li style="margin-top:0px;">上海茶马古道电子商务有限公司</li>
                	<li class="color_g">Copyright 2009-2011 Chamago,All Rights Reserved.  沪B2-20100062</li>
                </ul>
            </div>
    	</div>
    </div>

</body>
</html>
