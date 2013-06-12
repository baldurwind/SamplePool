<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>淘个性-工作台</title>
<link rel="stylesheet" type="text/css" href="${static_resource_host}/resource/script/plug/ztree/css/zTreeStyle/zTreeStyle.css" />
<script src="${static_resource_host}/resource/script/notice/newnoticemgt.js" type="text/javascript"></script>
<script src="${static_resource_host}/resource/script/plug/ztree/js/jquery.ztree.all-3.1.min.js" type="text/javascript"></script>
<script type="text/javascript">

	var creator = null;
	var seller = null;
    $(document).ready(function () {
    	seller = getNoEncodeSeller();
    	var userid = getCurUser();
    	creator = userid;
    	if(userid==null||userid == undefined){
    		creator = $("#nick").val();
    	}
    	
        noticemgt.init();
       
    });
    
    
    function addsucc(){
    	noticemgt.cleanForm();
    	hideDisplay();
    	noticemgt.showMessge('新增通知成功');
    }

    function addfaile(){
    	noticemgt.showMessge('新增通知失败');
    	hideDisplay();
    }
</script>
</head>
<body>
<!--导航 -->
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
                	<div id="treeshow" style=" width:300px;  display: none; border: 1px solid #ccc">
                    	<ul id="tree" class="ztree">
                         </ul>
                         <!-- 
                         <p class="publicFormBtn" id="allchecked">全选</p><p class="publicFormBtn" id="nochecked">反选</p>
                    	 -->
                    	 <div class="DivH20">
	                    	 <input id="allchecked" type="checkbox"   style="vertical-align: middle; margin-left: 20px;"/>
	                    	 <label style="vertical-align: middle; font-weight: bold">全选</label>
	                     </div>
                    </div>
                    <div class="t-c" id="loading" class="tips_loading" style="display: none"><img  style="margin:10px auto;" width="32" height="32" src="${static_resource_host}/resource/newcss/themes/classic/images/Loading.gif"  title="数据正在加载中。。。" />
                    </div>
                    
                    <input id="userids" type="hidden" name="userids" value=""/>
                </td>
            </tr>    
            
            <tr>
            	<td></td>
            	<!-- 
            	<td ><input id="savenotice" type="button" value=" 保 存 "/><input id="resetnotice" type="reset" value=" 重 置 "/></td>
            	 -->
            	<td>
            		<div class="DivH20"></div>
            		<p class="publicFormBtn" id="savenotice">保存</p><p class="publicFormBtn pfb-glay" id="resetnotice">重置</p></td>
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
	

