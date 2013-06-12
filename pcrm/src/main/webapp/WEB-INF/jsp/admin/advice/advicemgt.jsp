<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/admin/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/admin/common/include.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理后台-客服建议</title>
<script src="${static_resource_host}/resource/admin/script/advice/advicemgt.js" type="text/javascript"></script>
<script type="text/javascript">
	var userid = null;
	var seller = null;
	var creator = null;
	var orientedtype = "1";
	$(document).ready(function () {
		seller =getSeller();
		creator = getCurUser();
		advicemgt.init();
	});


	function addsucc(){
		advicemgt.cleanForm();
		advicemgt.showMessge("新增成功");
	}
	
	function addfaile(){
		advicemgt.showMessge("新增知识失败");
	}

</script>
</head>

<body>
    <div class="cus-left">
	    <div class="cus-leftnav-tit">客服建议  </div>
	    <div class="cus-leftNav" id="cus-leftNav">
	         	<div class="cus-leftNav-list">
	            	<h3 class="navTit  choose">建议分类  <img src='http://static.chamago.cn/resource/images/edit_add.png' id="addAdviceType"/></h3>
	                <ul id="advicetypenav">
	                </ul>
	                <div class="clear"></div>
	            </div>
	            
	     </div>
	     
    </div>
	<div class="backend_right">
    	<div class="rightSuggestManage">
            <div class="suggestPanel">
            	<div class="suggestoperate">
                	<input name="" type="checkbox" value=""  id="allChecked"  />
                    <label style="padding-right:20px; cursor:pointer">全选</label>
                    <a href="javascript:void(0)" id="deleteadvice">删除</a>
                    <span><a href="javascript:void(0)" id="translate">转移</a>:</span>
                    <select id="advicetypelist"><option value='-1'>选择分类</option></select>
                </div>
                <ul class="suggestscreen">
                	<li><a href="javascript:void(0)" class="suggestscreen_choose" id="allstatusadvice">全部</a></li>
                	<li>|</li>
                    <li><a href="javascript:void(0)" id="noread">未读</a></li>
                    <li>|</li>
                    <li><a href="javascript:void(0)" id="aleardread">已读</a></li>
                </ul>
            </div>
            <div class="dataContiner" id="dataContiner">
            	<table id="advicelisttable" width="100%" cellpadding="0" cellspacing="0" border="0" class="publicDataTable">
                	<tr class="head" >
                    	<td width="1%"></td>
                    	<td width="10%">ID</td>
                        <td width="39%">建议标题</td>
                        <td width="20%">提交时间</td>
                        <td width="20%">状态</td>
                        <td width="10%">操作</td>
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
	<div>
	
	</div>
	<div id="dialog" title="系统消息">
            <p id="smsg">
            </p>
    </div>
</body>
</html>
