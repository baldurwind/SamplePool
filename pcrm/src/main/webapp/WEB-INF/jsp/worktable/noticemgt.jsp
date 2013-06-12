<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>淘个性-工作台</title>
<script src="${static_resource_host}/resource/script/notice/noticemgt.js" type="text/javascript"></script>
<script type="text/javascript">

	var creator = null;
	var seller = null;
    $(document).ready(function () {
    	seller = $("#seller").val();
    	var userid = getCurUser();
    	creator = userid;
    	if(userid==null||userid == undefined){
    		creator = $("#nick").val();
    	}
        noticemgt.init();
    });
    
    
    function addsucc(){
    	noticemgt.cleanForm();
    	noticemgt.showMessge('新增通知成功');
    }

    function addfail(){
    	noticemgt.showMessge('新增通知失败');
    }
</script>
</head>
<style>
	.wijmo-wijtree
{
    width: 300px;
    padding: 0;
}
</style>
<body>
<!--导航 -->
<jsp:include page="/WEB-INF/jsp/common/navigation.jsp"/>
<div class="wrapper">

<div class="addBeiwang" id="addNoticePanel">
	 <h3 class="head">新增系统通知</h3>
     <div class="addnoticeform">
     <form id="MyForm" action="notice/add" enctype="multipart/form-data" method="post" target="ds_postUri" >
     	 <table border="0" cellpadding="0" cellspacing="0" width="100%">
         	<tr>
         		
            	<td width="15%" align="left">标 题：</td>
                <td><input id="textbox" type="text" class="text" name="title"/></td>
            </tr>
            <tr>
            	<td width="15%" align="left" valign="top">内 容：</td>
                <td><textarea id="area" name="content"></textarea></td>
            </tr>  
            <tr>
            	<td width="15%" align="left" valign="top">分 配：</td>
                <td>
                	<div id="treeshow" style=" width:300px;  display: none">
                    	<ul id="tree" >
                         </ul>
                    </div>
                    <div id="loading" class="tips_loading" style="display: none">查询中...</div>
                    <input id="userids" type="hidden" name="userids" value=""/>
                </td>
            </tr>    
            
            <tr>
            	<td></td>
            	<td ><input id="savenotice" type="button" value=" 保 存 "/><input id="resetnotice" type="reset" value=" 重 置 "/></td>
            	<!-- 
            	<td><p class="publicFormBtn" id="savenotice">保存</p><p class="publicFormBtn pfb-glay" id="resetnotice">重置</p></td>
            	 -->
            </tr>
         </table>
          <input id="creatorid" type="hidden" value="" name="creator"/>
      </form>
      <iframe id="ds_postUri" name="ds_postUri" src="" style="display:none;height:100px;width:100%"></iframe>
     </div>
     </div>
     <div id="dialog" title="系统消息">
            <p id="smsg">
            </p>
    </div>
</div>
<!--工作台 -->
<%@ include file="/WEB-INF/jsp/worktable/index.jsp" %>

<script type="text/javascript">
	$(function(){
	 	$("ul#noticeList li").find('h3').live('click',function(){
			  $(this).parent().siblings('div.con').show();
		});
		
		$("#noticeList").find("p.shouqi").live('click',function(){
			  $(this).parent().hide().siblings().remove('p.weidu');
		})
		
		 
	})
</script>
</body>
</html>
	

