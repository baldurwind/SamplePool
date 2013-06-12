<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<head>
<script type="text/javascript">
function submit(){
	var seller=encodeURIComponent('cntaobao'+$('#seller').val());
	var buyer=encodeURIComponent('cntaobao'+$('#buyer').val());
	var numiid=encodeURIComponent($('#numiid').val());
	var tid=encodeURIComponent($('#tid').val());
	
	var url= contextPath+"dispatcher?seller="+seller+"&buyer="+buyer+"&numiid="+numiid+"&tid="+tid;
	//window.location.target="_blank";
	 window.location.href=url;
	//window.open(url,   "about:blank");
}
</script>
</head>
 
<body>
seller:<input  value="良无限home:迎春花" type="text" id="seller"/>
<br/>
buyer:<input  value="mike中尉" type="text"id="buyer"/>
<br/>
numid:<input  value="10490908515" type="text" id="numiid"/>
<br/>
tid:<input  value="34434444" type="text" id="tid"/>
<br/>
<a href="javascript:submit()"    onclick="submit()"/>Test</a>
</body>

</html>
