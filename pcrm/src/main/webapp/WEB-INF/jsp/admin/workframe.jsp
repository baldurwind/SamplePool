<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
String context = request.getContextPath();
String contextPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+context+"/";
String nick = (String)request.getAttribute("nick");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>淘个性后台管理系统</title>
</head>
<frameset rows="95,90%">
  <frame src="<%=contextPath%>admin/top?nick=<%=nick%>" name="topFrame" frameborder="0" scrolling="no" noresize="noresize"/>
  <frame src="<%=contextPath%>admin/index" name="mainFrame" frameborder="0" scrolling="auto" noresize="noresize"/>
</frameset>
</html>
