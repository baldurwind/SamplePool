<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
String context = request.getContextPath();
String contextPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+context+"/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>提示页面</title>
<script type="text/javascript">
		parent.location.href = "<%=contextPath%>admin/error";
	 
</script>
</head>
<body>
</body>
</html>
