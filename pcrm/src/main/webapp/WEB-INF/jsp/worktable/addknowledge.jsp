<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>淘个性-工作台</title>
<script src="${static_resource_host}/resource/script/knowledge/knowledgemgt.js" type="text/javascript"></script>
<script type="text/javascript">
var clickNode;
var supportXhr;
var userid = '';
var seller = '良无限home';
var creator = "";
$(document).ready(function () {
	creator=$('#seller').val();
	seller = $('#seller').val();
	knowledge.init();
    knowledge.loadSubjectTrees(null,-1);
    knowledge.loadItem(0,'first');
});


function addsucc(){
	knowledge.cleanForm();
	knowledge.showMessge("新增成功");
}

function addfaile(){
	knowledge.showMessge("新增知识失败");
}

</script>
</head>

<body>
<!--[if !IE]>---导航 ----<![endif]-->
<jsp:include page="/WEB-INF/jsp/common/navigation.jsp"/>
<!--[if !IE]>---知识库----<![endif]-->
<div class="knowledge_add" id="knowledge_add">
	<ul>
        <li>
            <h1>Step 1</h1>
            选择主题</li>
        <li>
            <h1>Step 2</h1>
            关联商品</li>
        <li>
            <h1>Step 3</h1>
            新建知识</li>
    </ul>
	<div>
    	<!--[if !IE]>---主题tree----<![endif]-->
        <ul id="tree">
        	<div id="loading" style="padding: 20px 0; text-align: center;display: " >
            	<img src="${static_resource_host}/resource/images/loading.gif" width="50" height="50" style="margin: auto; "/>	
    		</div>
        </ul>
        <!-- Begin options markup -->
        <div class="knowledge_tips">
           新建主题名称 <input id="subjectname" type="text" value="" style=" width:160px; height:20px;" />
            <input id="addNode" type="button" value="点击添加" /> 
        </div>
        <!-- End options markup -->
    </div>
    <div>
    	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:12px;">
          <tr>
            <td width="21%" height="30">是否关联商品</td>
            <td width="21%">
            <label>
                <input type="radio" id="relatedyes" name="RadioGroup1" value="单选" id="RadioGroup1_0" />
                是</label>
            </td>
        <td width="58%"><label>
                <input type="radio" id="relatedno" name="RadioGroup1" value="单选" id="RadioGroup1_1" />
            否</label></td>
          </tr>
        </table>
        <div class="knowledge_tips ft-green line_gray">若不需要关联商品，可直接点击“next”进行下一步</div>
		<div class="knowledge_search">搜索：<input id="itemsearch" type="text" value="" /></div>
        <!--[if !IE]>---关联商品列表----<![endif]-->
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="knowledge_prolist" id="knowledge_prolist">
        	<thead>
              <tr>
                <th>序号</th><th>商品描述</th><th>操作</th>
              </tr>
           </thead>
        </table>
		<div id="pager" class="pager_index ui-widget wijmo-wijpager ui-helper-clearfix">
                  <ul id="pagebar">
                  </ul>
        </div>
  	</div>
    <div>
      <form id="MyForm" action="knowledgeservice/addknowledge" enctype="multipart/form-data" method="post" target="ds_postUri" >
		            <ul class="formdecorator">
		                <li> <span>主题</span><input id="subectname" type="text" value="" name="subject"/>
		                         <input id="subjectid" type="hidden" value="" name="subjectid"/>
		                         <input id="itemid" type="hidden" value="0" name="itemid"/>
		                          <input id="creatorid" type="hidden" value="" name="creator"/>
		                </li>
		               
		                <li>
		                     <span>标题</span><input id="textbox" type="text" name="title"/>
		                </li>
		                <li>
		                    <span>内容</span><textarea id="area" rows="10" name="content"></textarea>
		                </li>
		                <li style="padding-left:60px;"><input id="checkupload" type="checkbox" onClick="knowledge.ckuploadFS()" /><label for="checkupload" style="padding-left:8px;">附件上传</label> 
		                </li>
		                <li id="uploadtab" style="display: none;padding-left:60px;">
		                	
					       	<span>选择文件</span><input type='file'  id="uploadfile" name="uploadfileName" size='20'/>
					       	<input id="checkup" type="hidden" value="" name="checkup"/>
					       	 
		                </li>
		                <li style="padding-left:60px;"><button id="wijmo-button" onClick="javascript:return knowledge.checkForm()">提交</button>
		                </li>
		            </ul>
	            </form>
	            <iframe id="ds_postUri" name="ds_postUri" src="" style="display:none;height:100px;width:100%"></iframe>
    </div>
	<div id="dialog" title="系统消息">
            <p id="smsg">
            </p>
    </div>
	
</div>
<!--[if !IE]>---工作台 ----<![endif]-->
<%@ include file="/WEB-INF/jsp/worktable/index.jsp" %>
</body>
</html>
	

