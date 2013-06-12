<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<head>
<title>淘个性-工作台</title>
<script src="${static_resource_host}/resource/script/notice/noticelist.js" type="text/javascript"></script>
<script type="text/javascript">

	var userid = null;
    $(document).ready(function () {
    	userid = getCurUser();
    	notice.init();
        
    });
    
   
    
</script>
</head>

<body>
<!--导航 -->
<jsp:include page="/WEB-INF/jsp/common/navigation.jsp"/>
<!--通知-->
<div class="lead_remark" id="notice">
	<div class="proRegister" id="proRegister">
    	<a href="javascript:getLink('notice/show')" class="btn1 default" id="addnotice">发布通知</a>
     	<h1 class="tit">您的通知</h1>
        <ul class="noticeList" id="noticeList">
        	<div id="loading" class="tips_loading" style="display:block">查询中...</div>
          </ul>
          <div id="pager" class="pager_index ui-widget wijmo-wijpager ui-helper-clearfix">
                  <ul id="pagebar">
                  </ul>
          </div>
     </div>
</div>
<!--添加备忘-->
<div class="addBeiwang" id="addNoticePanel" style="display:none">
	 <h3 class="head">新增系统通知</h3>
     <div class="addnoticeform">
     <form>
     	 <table border="0" cellpadding="0" cellspacing="0" width="100%">
         	<tr>
            	<td width="15%" align="left">标 题：</td>
                <td><input type="text" class="text"/></td>
            </tr>
            <tr>
            	<td width="15%" align="left" valign="top">内 容：</td>
                <td><textarea></textarea></td>
            </tr>
            <tr>
            	<td></td>
            	<td ><input type="submit" value=" 保 存 "/><input type="submit" value=" 重 置 "/></td>
            </tr>
         </table>
      </form>
     </div>
</div>
<!--工作台 -->
<%@ include file="/WEB-INF/jsp/worktable/index.jsp" %>
<iframe id="ds_postUri" name="ds_postUri" src="" style="display:none;height:100px;width:100%"></iframe>
</body>
</html>
	

