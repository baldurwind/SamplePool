<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/admin/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/admin/common/include.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理后台-知识库</title>
<script src="${static_resource_host}/resource/admin/script/knowledge/knowledgemgt.js" type="text/javascript"></script>
<script type="text/javascript">
	var userid = '';
	var seller = null;
	var creator = "良无限home";
	var orientedtype = "1";
	$(document).ready(function () {
		seller = getSeller();
		creator = getCurUser();
		knowledgemgt.init();
	});


	function addsucc(){
		knowledgemgt.cleanForm();
		knowledgemgt.showMessge("保存成功");
		knowledgemgt.reloadData(0,'first');
		$.closeMyPannel(curPanel);
	}
	
	function addfaile(){
		knowledgemgt.showMessge("保存失败");
	}
	
</script>
</head>

<body>
	<!-- 
  <div class=" backend_left" id="publicLeftNav">
    	<div class="alluser"><a href="javascript:void(0)" id="allknowledge">所有知识</a></div>
        <h2 class="menu_fl menu_fl_active"><a href="javascript:void(0)">通用知识</a></h2>
        <ul id="genernalsubjectlist" style="display:block" class="menu_zl">
        </ul>
        <a class="menu_add" href="javascript:void(0)" id="addgensubject">+添加知识主题</a>
        <h2 class="menu_fl menu_fl_active"><a href="javascript:void(0)">关联商品知识</a></h2>
        <ul id="itemsubjectlist" style="display:block" class="menu_zl">
        </ul>
        <a class="menu_add" href="javascript:void(0)" id="additemsubject">+添加知识主题</a>
    </div>
     -->
   <div class="cus-left">
	   <div class="cus-leftnav-tit">所有知识</div>
	   <div class="cus-leftNav" id="cus-leftNav">
	         	<div class="cus-leftNav-list">
	            	<h3 class="navTit  choose">通用知识 <img src='${static_resource_host}/resource/images/edit_add.png' id="addgensubject"/></h3>
	                <ul id="genernalsubjectlist">
	                </ul>
	                <div class="clear"></div>
	            </div>
	            <div class="cus-leftNav-list">
	            	<h3 class="navTit  choose">关联商品知识 <img src='${static_resource_host}/resource/images/edit_add.png' id="additemsubject"/></h3>
	                <ul id="itemsubjectlist">
	                </ul>
	                <div class="clear"></div>
	            </div>
	     </div> 
  </div>  
<!--右侧内容-->
	<div class="backend_right">
    	<div class="rightSuggestManage">
            <div class="suggestPanel">
            	<div class="suggestoperate">
            		<input name="" type="checkbox" value=""  id="allChecked"  />
                    <label style="padding-right:20px; cursor:pointer">全选</label>
                    <a href="javascript:void(0)" id="deleteknowledge">删除</a>
                    <!-- 
                    <span><a href="javascript:void(0)" id="translate">转移</a>:</span>
                    <select name="" id="translationsubjectlist"><option>选择分类</option><option>产品价格</option><option>产品描述</option><option>产品质量</option></select>
                	 -->
                </div>
                <div class="knowledge_jia"><input name="" type="button" value=" 新增知识 "  id="addknowledge_btn" /></div>
          </div>
            <div class="dataContiner" id="dataContiner">
            	<table id="knowledgelisttable" width="100%" cellpadding="0" cellspacing="0" border="0" class="publicDataTable">
                	<tr class="head" >
                    	<td width="30">&nbsp;</td>
                    	<td>ID</td>
                        <td>知识标题</td>
                        <td>所属主题</td>
                        <td>提交时间</td>
                        <td>操作</td>
                    </tr>
                </table>
                 <div id="loadingdata"></div>
                 <div class="ui-pager2-continer">
			         <div class="ui-pager2 right">
					               <ul id="pagebar">
					               </ul>
					  </div>
         			 <div class="clear">
         			</div>
   				 </div>
            </div>
        </div>
    </div>
    <div class="clear"></div>
   
<!--底部-->
	<div></div>
	<div id="dialog" title="系统消息">
            <p id="smsg">
            </p>
    </div>
</body>
</html>
