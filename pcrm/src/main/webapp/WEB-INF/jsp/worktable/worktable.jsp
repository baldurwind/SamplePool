<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>淘个性-工作台</title>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<script src="${static_resource_host}/resource/script/performance/newperformance.js" type="text/javascript"></script>
<!--打分插件-->
<script type="text/javascript" src="${static_resource_host}/resource/script/plug/jquery.raty-2.1.0/js/jquery.raty.js"></script>
<script type="text/javascript">
	var creator = null;
	var nick = '';
	var seller = null;
	var userid = null;
	$(document).ready(function () {
		 //userid = getCurUser();
		 //creator = getCurUser();
		 seller = getNoEncodeSeller();
		 nick =  getNoEncodeNick();
	     performance.init();
	    
	     performance.loadAllPerformance();
	     performance.loadPeriodPerformance('day',0);
	     performance.loadTakedAdviceNums();
	     performance.loadAdviceTypeList();
    	 if(nick.split(":").length>1){
    		authButtonResource("/worktable");
    	 }else{
    		$("#show_suggest").css("display","block");
    	 }
	    
	});
   
    function addsucc(){
    	performance.cleanForm();
    	performance.showMessge('新增建议成功');
    	performance.hideDisplay();
    }

    function addfail(){
    	
    	performance.showMessge('新增建议失败');
    	performance.hideDisplay();
    }
    
    function gologin(){
    	//var path = "<%=request.getContextPath()%>";
    	getLink("loginservice");
    	//window.location.href = "<%=request.getContextPath()%>/loginservice?seller="+getSeller()+"&nick="+getNick();
    }
    
</script>
</head>

<body>
<!--导航 -->
<div class="wrapper"> 
<!--搜索-->
<div class="myWorkState" id="myWorkState">
 	 <div class="workState">
     	<div class="ws_top">
            <div class="leftPerson">
                <div class="touxiang"><img src="${static_resource_host}/resource/images/test.jpg" width="70" height="70"/></div>
                <div class="loginpanel">
                	<!-- 
                    <p class="FB" style="display none"><span id="username">您还未打卡</span></p>
                    <p class="FB"><input type="text" id="loginusername" style="width:120px;color:#ccc" value="我的员工号" title="请输入员工号"/></p>
                     -->
                    <p class="wwid">旺旺ID：<span id="wangwangid"></span></p>
                    <p class="FB">今天聊天人数:<span id="today_chatpeernums">0</span></p>
                    <!-- 
                    <input type="hidden" id="isonline" value="nologin"/>
                    <P class="zxdl" id="show_logout" lang="logout">&nbsp;&nbsp;上班: 登录</P>
                     -->
                </div>
            </div>
            <div class="rightInfo">
            	<p class="jy" id="show_suggest" lang="suggest" style="display: none">我要提建议</p>
                <div class="score" >
                	<div id="ratingDiv"></div>
                </div>
                <p class="left" >已采纳<span id="takedadvicenums"></span>条建议</p>
            </div>
            <div class="clear"></div>
        </div>
        <div class="suggest" id="suggest" style="display:none;">
        	<form id="MyForm" action="${basePath}adviceservice/add" method="post" target="ds_postUri" >
        	<h3><img src="${static_resource_host}/resource/images/linght-icon.gif" width="12" height="16" />您的建议一经采纳，将帮助您提升业绩</h3>
            <ul>
            	<li><b>选择建议对象：</b></li>
                <li><select name="orientedtype"><option value="1">运营部</option><option value="2">客服主管</option></select></li>
            	<li><b>选择建议分类：</b></li>
                <li><select id="advicetypelist" name="type"></select></li>
                <li><b>标题：</b></li>
                <li><input id="textbox" class="suggest_title" name="title" type="text" value="英伦金属装饰邮差包好多亲询问呢~" /></li>
                <li><b>内容：</b></li>
                <li><textarea id="area" name="content" cols="" rows="">我这边已经收到四五个买家想要这款包了呢</textarea></li>
                <li style="padding: 5px 0;">
	                <p class="publicFormBtn" id="saveadvice"">保存</p><p class="publicFormBtn pfb-glay" id="resetadvice">重置</p>
	                <p style="margin-top: 4px">
	                	<input id="anonymity" style=" vertical-align: middle;font-family:tahoma;" type="checkbox" value="" />
	                	<label style="vertical-align: middle;">匿名提交</label>
	                </p>
                </li>
            </ul>
            <input id="creatorid" type="hidden" value="" name="creator"/>
            <input id="sellerid" type="hidden" value="" name="seller"/>
            </form>
      		<iframe id="ds_postUri" name="ds_postUri" src="" style="display:none;height:100px;width:100%"></iframe>
        </div>
    	<div class="logout" id="logout" style="display:none;">
    		<form id="logoutForm" action="${basePath}userservice/logout" method="post">
    		<input type="hidden" id="logoutnick" value="" name="nick"/>
    		<input type="hidden" id="logoutsysuser" value="" name="sysuser"/>
        	<h3><img src="${static_resource_host}/resource/images/question-icon.gif" width="14" height="14" />确定要注销吗？</h3>
            <ul>
            	<li><span>今日您的登录时间为：</span><b id="onlinehourse">0</b>小时<b id="onlineminters">0</b>分</li>
			</ul>
            <div><input id="logoutsys" name="" type="button" value="注销" /><input id="cancellogout" name="" type="button" value="取消"  lang="logout"/></div>
       		</form>
        </div>
        <div class="section helpUsDoBetter">
        	<h3 class="childModuleTit">帮助我做的更好</h3>
            <ul id="allperioddata">
    			<li id="allreceivenums"></li>
    			<li id="allnoreplynums"></li>
            	<li id="allchatpeernums"></li>
                <li id="allserviceresult"></li>
                <li id="allprice"></li> 
                <li id="allrestime"></li>
				<p id="myflower" class="flower_gray"></p>
                
            </ul>
            <div id="allloading" class="tips_loading" style="display: none">查询中...</div>
        </div>
        <div class="section yeji ui-tab" id="yejiData">
        	<ul class="ui-tab-ul" id="yejiNav">
       			<li id="day" class="ui-tab-ul-li-open"><span></span><a>昨天业绩</a></li>
                <li id="week"><span></span><a>上周业绩</a></li>
                <li id="month"><span></span><a>上个月业绩</a></li>
                <div style="clear:both"></div>
            </ul>
            <div class="yejiData ui-tab-con" style="display: none;">
            	   <table width="100%" cellpadding="0" cellspacing="0" border="0">
            	   		<tr>
                        	<td width="35%">接待客户数：<span class="day_receivenums"></span></td>
                        	<td width="65%">平均客单价：<span class="day_avgprice"></span></td>
                            
                        </tr>
                        <tr>
                        	<td>未回复数：<span class="day_noreplynums"></span></td>
                        	<td>服务转化数：<span class="day_cusresult"></span></td>
                        </tr>
                        <tr>
                        	<td>聊天人数：<span class="day_chatpeernums"></span></td>
                            <td>平均响应时间：<span class="day_avgresptime"></span></td>
                        </tr>
                        <tr>
                            <td>服务排名：<span class="day_ranking"></span></td>
                        </tr>
                   </table>                   
            </div>
            <div class="yejiData ui-tab-con" style="display: none">
            	    <table width="100%" cellpadding="0" cellspacing="0" border="0">
                   		<tr>
                        	<td width="35%">接待客户数：<span class="week_receivenums"></span></td>
                        	<td width="65%">平均客单价：<span class="week_avgprice"></span></td>
                        </tr>
                        <tr>
                        	<td>未回复数：<span class="week_noreplynums"></span></td>
                        	<td>服务转化数：<span class="week_cusresult"></span></td>
                        </tr>
                        <tr>
                        	<td>聊天人数：<span class="week_chatpeernums"></span></td>
                            <td>平均响应时间：<span class="week_avgresptime"></span></td>
                        </tr>
                        <tr>
                            <td>服务排名：<span class="week_ranking"></span></td>
                        </tr>
                   </table>                  
            </div>
            <div class="yejiData ui-tab-con" style="display: none">
            	    <table width="100%" cellpadding="0" cellspacing="0" border="0">
                   		<tr>
                        	<td width="35%">接待客户数：<span class="month_receivenums"></span></td>
                        	<td width="65%">平均客单价：<span class="month_avgprice"></span></td>
                            
                        </tr>
                        <tr>
                        	<td>未回复数：<span class="month_noreplynums"></span></td>
                        	<td>服务转化数：<span class="month_cusresult"></span></td>
                        </tr>
                        <tr>
                        	<td >聊天人数：<span class="month_chatpeernums"></span></td>
                            <td >平均响应时间：<span class="month_avgresptime"></span></td>
                        </tr>
                        <tr>
                            <td >服务排名：<span class="month_ranking"></span></td>
                        </tr>
                   </table>           
            </div>
            <div id="loading" class="tips_loading" style="display: none">查询中...</div>
        </div>
     </div>   
</div>
</div><!-- end wrap -->
<!--工作台 -->
</body>
</html>
	

