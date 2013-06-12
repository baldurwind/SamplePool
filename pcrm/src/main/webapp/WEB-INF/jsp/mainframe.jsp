<!--<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<%! boolean master=false;%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath", basePath);
String indexPage = (String)request.getAttribute("indexPage");
String seller = (String)request.getAttribute("nick");
if(seller!=null){
	if(!seller.contains(":")){
		master=true;
	}
} 
request.setAttribute("master",master);

%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>淘个性-工作台</title>

<meta http-equiv="pragma" content="no-cache" />
<link rel="icon" href="${static_resource_host}/resource/newcss/images/favicon.ico" type="image/x-icon" /> 
<link rel="shortcut icon" href="${static_resource_host}/resource/newcss/images/favicon.ico" type="image/x-icon" /> 
<link rel="stylesheet" type="text/css" href="${static_resource_host}/resource/newcss/global.css"/>
<link rel="stylesheet" type="text/css" href="${static_resource_host}/resource/newcss/module.css"/>
<link rel="stylesheet" type="text/css" href="${static_resource_host}/resource/newcss/themes/classic/themeStyle.css"/>
<script src="${static_resource_host}/resource/script/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="${static_resource_host}/resource/script/jqueryAcookie.js" type="text/javascript"></script>
<script src="${static_resource_host}/resource/script/mainframe.js" type="text/javascript"></script>
<script type="text/javascript" src="${static_resource_host}/resource/script/comet/api.js"></script>
<script type="text/javascript">
var contextPath = "<%=basePath%>";
var staticResourcePath = 'http://static.chamago.com';
$(document).ready(function(){
		initPoll();
		bindNavEvent();
});
</script>
<script type="text/javascript" src="${static_resource_host}/resource/script/comet/js-pushlet-client.js"></script>
</head>
<body>
<input  id="seller" value="${seller }" type="hidden"/>  <!-- stevemadden旗舰店 芳蕾玫瑰粉粉旗舰店 -->
<input  id="buyer" value="${buyer}" type="hidden"/>  <!-- 芳蕾玫瑰粉粉旗舰店 晶典珠宝11  wccctest01  3994226377  良无限_非洲菊-->
<input  id="numiid" value="${numiid }" type="hidden"/>
<input  id="nick" value="${nick }" type="hidden"/>
<input  id="tid" value="${tid }" type="hidden"/>
  <div class="nav" id="topNav">
     <ul class="FL">
         <c:set  value="false" var="behavior"></c:set>
            <c:set  value="false" var="marketing"></c:set>
            	<c:set  value="false" var="coupon"></c:set>
            	<c:set  value="false" var="trade"></c:set>
            	   <c:set  value="false" var="customerService"></c:set>
            	   <c:set  value="false" var="leadsMemo"></c:set>
            	   <c:set  value="false" var="item"></c:set>
           <c:if test="${master}">
            	<c:set  value="true" var="behavior"></c:set>
                <c:set  value="true" var="marketing"></c:set>
            	<c:set  value="true" var="coupon"></c:set>
            	<c:set  value="true" var="trade"></c:set>
            	   <c:set  value="true" var="customerService"></c:set>
            	   <c:set  value="true" var="leadsMemo"></c:set>
            	   <c:set  value="true" var="item"></c:set>
           </c:if>
         <c:forEach items="${syresources}" var="res">
            <c:if test="${res.url eq '/behavior'}">
	             <c:set  value="true" var="behavior"></c:set>
	       	  </c:if>
	          <c:if test="${res.url eq '/marketing'}">
	             <c:set  value="true" var="marketing"></c:set>
	       	  </c:if>
			  <c:if test="${res.url eq '/marketing/coupon'}">
			  		<c:set  value="true" var="coupon"></c:set>
	       	  </c:if>
	       	  <c:if test="${res.url eq '/trade/index'}">
	             	<c:set  value="true" var="trade"></c:set>
	       	  </c:if>
	       	  <c:if test="${res.url eq '/customerService'}">
	              <c:set  value="true" var="customerService"></c:set>
	       	  </c:if>
	       	  <c:if test="${res.url eq '/leadsMemo'}">
	             	<c:set  value="true" var="leadsMemo"></c:set>
	       	  </c:if>
	       	  <c:if test="${res.url eq '/item'}">
	              <c:set  value="true" var="item"></c:set>
	       	  </c:if>
         </c:forEach>
         
         <c:if test="${behavior}">
	              <li><a href="<%=basePath%>/" title="服务台" class="ui-nav-fwt" curr="1" target="workFrame"></a></li>
	       	  </c:if>
          <c:if test="${marketing}">
	              <li><a href="<%=basePath%>marketing" title="活动" class="ui-nav-ht" curr="2" target="workFrame"></a></li>
	       	  </c:if>
         <c:if test="${coupon}">
              <li><a href="<%=basePath%>marketing/coupon/index" title="优惠" class="ui-nav-yh" curr="3" target="workFrame"></a></li>
       	 </c:if>
       	 <c:if test="${trade}">
	          <li><a href="<%=basePath%>trade/index" title="订单" class="ui-nav-dd" curr="4" target="workFrame"></a></li>
	     </c:if>
	     <c:if test="${customerService}">
	          <li><a href="<%=basePath%>customerService/index" title="售后" class="ui-nav-sh" curr="5" target="workFrame"></a></li>
	     </c:if>
     </ul>
     <ul class="FR">
          <c:if test="${leadsMemo}">
              <li><a href="<%=basePath%>leadsMemo/index" title="备注" class="ui-nav-bz" curr="6" target="workFrame"></a></li>
       	  </c:if>
		  <c:if test="${item}">
              <li><a href="<%=basePath%>item/toSearch" title="查找" class="ui-nav-cz" curr="7" target="workFrame"></a></li>
       	  </c:if>
     </ul>
     <div class="clear"></div>
 </div>	
 
  <iframe width="426" frameborder="0" id="workFrame" name="workFrame" scrolling="no" src="<%=indexPage%>"  ></iframe>
  
  <div class="nav foot" id="bottomNav">
    <ul class="FL">
    	<c:set  value="false" var="worktable"></c:set>
    	  <c:set  value="false" var="notice"></c:set>
    	    <c:set  value="false" var="knowledge"></c:set>
    	     <c:set  value="false" var="reminder"></c:set>
    	     
    	     
    	      <c:if test="${master}">
    	      <c:set  value="true" var="worktable"></c:set>
    	  <c:set  value="true" var="notice"></c:set>
    	    <c:set  value="true" var="knowledge"></c:set>
    	     <c:set  value="true" var="reminder"></c:set>
    	      </c:if>
       	  <c:forEach items="${syresources}" var="res">
	       	  <c:if test="${res.url eq '/worktable'}">
	       	  	<c:set  value="true" var="worktable"></c:set>
	       	  </c:if>
	       	  <c:if test="${res.url eq '/notice'}">
	              <c:set  value="true" var="notice"></c:set>
	       	  </c:if>
	       	  <c:if test="${res.url eq '/knowledge'}">
	               <c:set  value="true" var="knowledge"></c:set>
	       	  </c:if>
	       	  <c:if test="${res.url eq '/reminder'}">
	               <c:set  value="true" var="reminder"></c:set>
	       	  </c:if>
         </c:forEach>
         <c:if test="${worktable}">
	       	  	<li><a href="<%=basePath%>worktable/index" title="我的工作台" class="ui-nav-wdgzt ui-nav-wdgzt-choose" curr="8" target="workFrame"></a></li>
	     </c:if>
       	 <c:if test="${notice}">
              <li><a href="<%=basePath%>notice/index" title="通知"  class="ui-nav-tz" curr="9" target="workFrame"></a></li>
       	 </c:if>
       	 <c:if test="${reminder}">
              <li><a href="<%=basePath%>reminder/index" title="备忘" class="ui-nav-bw" curr="10" target="workFrame"></a></li>
       	 </c:if>
       	 <c:if test="${knowledge}">
               <li><a href="<%=basePath%>knowledge/index" title="知识库" class="ui-nav-zsk" curr="11" target="workFrame"></a></li>
       	 </c:if>
    </ul>
	<ul class="FR">
		<!-- 
  	    <li><a href="javascript:getLink('shortcut')" title="快捷键" class="ui-nav-xtsz" curr="12"></a></li>
  	    -->
    </ul>
    <div class="clear"></div>
    <div class="ui-tips tipsNotice_position" id="noreadnumsdiv" style="display:none">
    	<div class="ui-tips-left"></div>
        <div class="ui-tips-center" id="noreadnums"></div>
        <div class="ui-tips-right"></div>
    </div>
    <div class="ui-tips tipsNoteList_position" id="resultValuediv" style="display:none">
    	<div class="ui-tips-left"></div>
        <div class="ui-tips-center" id="resultValue"></div>
        <div class="ui-tips-right"></div>
    </div>
    
</div>
<script type="text/javascript">p_embed()</script>
</body> 
</html>
