<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>淘个性-工作台</title>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<script src="${static_resource_host}/resource/script/knowledge/knowledge.js" type="text/javascript"></script>
<script type="text/javascript">
	var itemid = '0';
	var seller = null;
	var userid = null;
	$(document).ready(function () {
		seller = getSeller();
		//alert(parent.getCommSeller());
		itemid = $("#itemid").val();
		userid = getCurUser();
		//
		knowledgelist.init();
		knowledgelist.cleanPage();
		var nick = getNoEncodeNick();
    	if(nick.split(":").length>1){
    		knowledgelist.checkButtonResource("/knowledge");
    	}else{
    		$("#adddefinedknowledge").css("display","block");
    	}
	});
   
	function addsucc(){
		hideDisplay();
		knowledgelist.cleanForm();
		knowledgelist.showMessge("保存成功");
	}
	
	function addfaile(){
		hideDisplay();
		knowledgelist.showMessge("保存失败");
	}
</script>
</head>

<body style="height: 500px">
<div class="wrapper" style="background: #fff">
	<input id="ww_msg_plugin" type="hidden" value="" />
	<div class="knowledge" id="maindiv" >
		<input type="hidden" id="itemid" value="${itemid}"/>
		<div class="fixedKnowTopform">
			     <div class="ui-tab" id="knowledgediv">
						<ul class="ui-tab-ul" id="knowledgetab">
					         <li id="generalknow" class="ui-tab-ul-li-open"><span></span><a>通用知识</a></li>
					          <li id="mydefinedknow"><span></span><a>我的知识</a></li>
					          <span class="clear" style="_clear:none"></span>
					    </ul>
				 </div>
			    <div class="clear"></div>
			    <div class="searchForm" id="searchknowledgediv" >
		                  <p class="left"><input id="keywords" class="searchInput"  type="text"/></p>
		                  <div class="right" id="gosearch"></div>
		                  <div class="clear"></div>
		        </div>
		</div>
		<div class="fixedKnowList">
		        <div class="knowledge_con" id="knowTypeList">
		             <h4 class="title">所有知识库</h4>
		             <div class="knowTypeList">
		             	<ul id="subjectlist">
		             		<div id="loading" class="tips_loading" style="display: ">查询中...</div>
		                </ul>
		             </div>
		        </div>
		        <div class="newKnow-ui">
			        <div class="knowledge_con" id="knowledge_gen" style="display:none;">
			            <h4 class="title"><span class="FL">当前主题：</span><span class="FL thisColor" id="curSujectName">关于特价活动</span><a href="javascript:void(0)" class="FR back" onclick="knowledgelist.returnSubjectList()">&lt;&lt;返回</a><div class="clear"></div></h4>
			            <div class="knowdetailsList">
			             	<ul id="knowdetailslistid">
			             		<div id="loading" class="tips_loading" style="display: ">查询中...</div>
			                </ul>
			             </div>
			        </div>
		        </div>
				<div class="newKnow-ui">
					<div class="knowledge_con" id="knowledge_con" style="display:none">
						 <h4 class="title"><span class="FL">我的知识</span> <span style="float: right;display:none" id="adddefinedknowledge"><img style="margin: 3px 3px 0 0 ;" src="${static_resource_host}/resource/newcss/themes/classic/images/addMyKnoledge.png"></img></span><span class="clear"></span></h4>
			             <div class="knowTypeList">
			             	<ul id="myknowlist">
			             		<div id="loading" class="tips_loading" style="display:">查询中...</div>
			                </ul>
			             </div>
				    </div>
				</div>
				
	    </div>
	    <div class="ui-pager" id="pagediv" style="margin-top: 84px;_margin-top:0px">
		              <ul id="pagebar" class="FR">
		              </ul>
		              <div class="clear"></div>
		 </div>
	    <div class="add_knowledge" id="addKnowledgediv" style="display:none">
	   		<h4 class="childModuleTit">添加我的知识</h4>
	    	<form id="MyForm" action="/knowledge/admin/addknowledge" enctype="multipart/form-data" method="post" target="ds_postUri" >
	        	<table border="0" cellpadding="0" cellspacing="0" width="100%" style="margin-top: 10px;" class="addKnowTable">
	            	<!-- 
	            	<tr>
	                	<td align="right">类型:</td>
	                    <td><select id="addknowledgesubjectlist" name="subjectid"></select></td>    
	                </tr>
	                 -->
	            	<tr>
	                	<td align="right" width="20%" class="FB">标   题:</td>
	                    <td width="80%"><input id="textbox" type="text" name="title" class="text"/></td>    
	                </tr>
	                <tr>
	                	<td align="right" style="vertical-align: top" class="FB">内    容:</td>
	                    <td><textarea id="area" rows="10" name="content"></textarea></td>
	                </tr>
	                 <tr>
	                 	<td></td>
	                	<td >
	                        <div class="btnContiner" style="margin: 10px 0;">
	                          <p class="publicFormBtn" id="createknowledge">确认</p>
	                          <p class="publicFormBtn pfb-glay" id="resetknowledge">重置</p>
	                          <p class="publicFormBtn" id="returnknowledge">返回</p>
	                          <div class="clear"></div>
	                       </div>
	                   </td>
	                </tr>
	            </table>
	            <input id="knowledgeid" type="hidden" value="0" name="id"/>
	            <input id="creatorid" type="hidden" value="" name="creator"/>
	            <input id="itemid" type="hidden" value="-1" name="itemid"/>
	            <input id="subjectid" type="hidden" value="-1" name="subjectid"/>
		        <input id="sellerid" type="hidden" value="" name="seller"/>
           </form>
        </div>
        
		<iframe id="ds_postUri" name="ds_postUri" src="" style="display:none;height:100px;width:100%"></iframe>
	</div>
 </div>
 
</body>
</html>
	

