<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/admin/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<%! boolean master=false;%>
<%
String context = request.getContextPath();
String contextPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+context;
String nick = (String)request.getAttribute("nick");
if(!nick.contains(":")){
	master=true;
}
request.setAttribute("master",master);
String seller = (String)request.getAttribute("seller");
String sellerNick = seller;
seller = java.net.URLEncoder.encode(seller,"UTF-8");
String permissContextPath  = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/sypro"+"/userController.do?login&sellernick="+seller;

%>
 
<div class="topContiner">
	<div class="con"><div class="cus-logo"></div></div>
    <ul class="nav">
    

    <c:set  value="false" var="adviceservice"></c:set>
    <c:set  value="false" var="customerServices"></c:set>
    <c:set  value="false" var="knowledges"></c:set>
    <c:set  value="false" var="usermanager"></c:set>
    <c:set  value="false" var="pcrm"></c:set>
    
        <c:if test="${master}">
        
        <c:set  value="true" var="adviceservice"></c:set>
    <c:set  value="true" var="customerServices"></c:set>
    <c:set  value="true" var="knowledges"></c:set>
    <c:set  value="true" var="usermanager"></c:set>
    <c:set  value="true" var="pcrm"></c:set>
        </c:if>
    
      	<c:forEach items="${resadmin}" var="res">
      		<c:if test="${res.url eq '/userController?login'}">
            	<c:set  value="true" var="pcrm"></c:set>
      	    </c:if>
          	<c:if test="${res.url eq '/adviceservice/admin/index'}">
            	<c:set  value="true" var="adviceservice"></c:set>
      	    </c:if>
      	    <c:if test="${res.url eq '/knowledge/admin/index'}">
            	<c:set  value="true" var="knowledges"></c:set>
      	    </c:if>
      	    <c:if test="${res.url eq '/customerService/admin/index'}">
            	<c:set  value="true" var="customerServices"></c:set>
      	    </c:if>
      	     <c:if test="${res.url eq '/usermanage'}">
            	<c:set  value="true" var="usermanager"></c:set>
      	    </c:if>
      	 </c:forEach>
      	 
      	 
      	 
		<li><a href="<%=contextPath%>admin/index" target="mainFrame" class="nav_choose">首页</a></li>
		 <c:if test="${pcrm}">
		<li><a href="<%=contextPath%>userController?login&sellernick=<%=seller%>" id="permission" target="mainFrame">子账号管理</a></li>
		</c:if>
	    <c:if test="${adviceservice}">
	    <li><a href="<%=contextPath%>adviceservice/admin/index?seller=<%=seller%>" target="mainFrame">客服建议</a></li>
	    </c:if>
	    <c:if test="${knowledges}">
	    <li><a href="<%=contextPath%>knowledge/admin/index?seller=<%=seller%>" target="mainFrame">知识库</a></li>
	    </c:if>
	    <c:if test="${customerServices}">
	    	<li><a href="<%=contextPath%>customerService/admin/index?seller=<%=seller%>" target="mainFrame">售后管理</a></li>
	    </c:if>
	    <!-- 
	    <c:if test="${usermanager}">
	    	//<li><a href="<%=contextPath%>usermanage/admin/index?seller=<%=seller%>" target="mainFrame">员工管理</a></li>
	    </c:if>
	     -->
	    <li class="fr"><a href="javascript:void(0)" id="logout">退出</a></li>
	</ul><div class="clear"></div>
</div>
<div class="clear"></div>
<script type="text/javascript">
	var sysPath =  "<%=permissContextPath%>";
	$(function(){
		$("ul.nav li a").click(function(){
			$(this).addClass("nav_choose").parent().siblings('li').find('a').removeClass("nav_choose");
		});
		$("#logout").click(function(){
			logout();
		});
	});
	
	function logout(){
		parent.location.href = "<%=contextPath%>admin/logout";
	}
	
	function goSyspro(){
		
		   
		   var sellernic = "<%=sellerNick%>";
		   var username = getCurUser();
		   form = $("<form></form>");
	       form.attr('action',sysPath);
	       form.attr('method','post');
	       var input1 = $("<input type='hidden' name='sellernick' value='"+sellernic+"'/>");
	       var input2 = $("<input type='hidden' name='username' value='"+username+"'/>");
	       form.append(input1);
	       form.append(input2);
	       form.css('display','none');
	       form.appendTo("body");
	       form.submit();
	}
	
</script>