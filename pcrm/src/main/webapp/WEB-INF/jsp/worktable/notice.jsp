<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<head>
<title>淘个性-工作台</title>
<style>
.body{margin:0}
</style>
<script src="${static_resource_host}/resource/script/notice/notice.js" type="text/javascript"></script>
<script type="text/javascript">

	var userid = null;
    $(document).ready(function () {
    	userid = getCurUser();
    	notice.init();
    	var nick = getNoEncodeNick();
    	if(nick.split(":").length>1){
    		authButtonResource("/notice");
    	}else{
    		$("#addnotice").css("display","block");
    	}
    	
    	
    });
    
   
    
</script>
</head>

<body>
<!--导航 -->
<!--通知-->
<div class="wrapper">
<div class="lead_remark" id="notice">
	<h1 class="childModuleTit">您的通知<a href="javascript:getLink('notice/create')"><p class="cmt-btn fbtz_btn" id="addnotice" style="display:none"></p></a></h1>
	<div class="proRegister" id="proRegister">
		<div class="ui-tab">
              	 <ul class="ui-tab-ul">
                      <li class="ui-tab-ul-li-open" id="nrnoticetab"><span></span><a>未读</a></li>
                      <li id="arnoticetab"><span></span><a>已读</a></li>
                 </ul>
                 <div class="ui-tab-con" style="display:block">
			        <ul class="noticeList" id="noticeList">
			        	<div id="loading" class="tips_loading" style="display: block">查询中...</div>
			        </ul>
			        <div class="ui-pager">
			              <ul id="pagebar">
			              </ul>
			              <div class="clear"></div>
			       	</div>
			     </div>
			     <div class="ui-tab-con" style="display:none">
			        <ul class="noticeList" id="readednoticeList">
			        	<div id="loading" class="tips_loading" style="display: block">查询中...</div>
			        </ul>
			        <div class="ui-pager">
			              <ul id="readedpagebar">
			              </ul>
			              <div class="clear"></div>
			       	</div>
			     </div>
			 </div>
     </div>
</div>
</div>
<!--工作台 -->
<!-- 

-->
<!-- 
<iframe id="ds_postUri" name="ds_postUri" src="" style="display:none;height:100px;width:100%"></iframe>
 -->
</body>
</html>
	

