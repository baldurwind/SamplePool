<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath", basePath);
%>

<head>
<script type="text/javascript">
	var contextPath = "<%=basePath%>";
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${basePath}resource/newcss/global.css">
<link rel="stylesheet" type="text/css" href="${basePath}resource/newcss/module.css">
<link rel="stylesheet" type="text/css" href="${basePath}resource/newcss/themes/classic/themeStyle.css">
<script src="${basePath}resource/script/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="${basePath}resource/script/plug_1.js" type="text/javascript"></script>
</head>

<body  bgcolor="#6198d0" >
   <div class="wrapper" style="border:none">
   		 <div class="logo_info_img ">
         	 <div class="t-c">
         		 <img src="${static_resource_host}/resource/newcss/themes/classic/images/thelp_logo.png" width="199" height="74" style="margin:50px auto"/>
             </div>
             <div class="logo_info">
             	${msg}
             </div>
         </div>
         
   </div>
</body>
</html>


