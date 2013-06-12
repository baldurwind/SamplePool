<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>淘个性-工作台</title>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<script src="${static_resource_host}/resource/script/knowledge/knowledgelist.js" type="text/javascript"></script>
<script type="text/javascript">
    var itemid = '0';
    var seller = null;
    $(document).ready(function () {
    	seller = getSeller();
    	itemid = $("#itemid").val();
    	knowledgelist.init();
    	knowledgelist.cleanPage();
    });
    
</script>
</head>

<body>
<!--[if !IE]>---导航 ----<![endif]-->
<jsp:include page="/WEB-INF/jsp/common/navigation.jsp"/>
<!--[if !IE]>---知识库----<![endif]-->
<input  id="itemid" value="${itemid}" type="hidden"/>
<div class="knowledge" id="maindiv">

	<ul class="knowledge_title clearfix">
	 <c:choose>
		<c:when test="${isitem==1}">
    		<li id="itemknow" class="blue">关联当前询问商品</li>
    	    <li id="generalknow">通用知识</li>
    	</c:when>
    	<c:when test="${isitem==0}">
       		<li id="itemknow" style="display:none">关联当前询问商品</li>
    	    <li id="generalknow" class="blue">通用知识</li>
        </c:when>
         </c:choose>
    </ul>
    
    <!--[if !IE]>---当前咨询商品----<![endif]-->
    <c:choose>
			<c:when test="${isitem==1}">
			    <div class="pro_List" id="iteminfo">
			         <p class="leftimg"><img src="<c:out value="${item[16]}"></c:out>"/></p>
			         <div class="rightInfo">
			            <h4 class="cptit"><c:out value="${item[3]}"></c:out></h4>
			            <ul class="parameterList">
			                 <li><span>价　　格：</span><strong class="ft-orange ft-14 left"><c:out value="${item[22]}"></c:out></strong><p class="left">促销: <img src="${static_resource_host}/resource/images/cu_r4_c2.jpg" /></p><c:choose><c:when test="${discount==1}"><strong class="ft-orange ft-14 left">在促销中</strong></c:when><c:when test="${discount==0}"><strong class="ft-orange ft-14 left">暂无促销</strong></c:when></c:choose>
			                 </li>
			            </ul>
			         </div>
			         <div class="clear"></div>
			         <p class="r_send"><span>发送</span></p>
			    </div>
    		</c:when>
    		<c:when test="${isitem==0}">
			    <div class="pro_List" id="iteminfo" style="display: none">
			         <p class="leftimg"><img src="<c:out value="${item[16]}"></c:out>"/></p>
			         <div class="rightInfo">
			            <h4 class="cptit"><c:out value="${item[3]}"></c:out></h4>
			            <ul class="parameterList">
			                 <li><span>价　　格：</span><strong class="ft-orange ft-14 left"><c:out value="${item[22]}"></c:out></strong><p class="left">促销: <img src="${static_resource_host}/resources/images/cu_r4_c2.jpg" /></p><strong class="ft-orange ft-14 left">23.00</strong>  库存: 40件</li>
			            </ul>
			         </div>
			         <div class="clear"></div>
			         <p class="r_send"><span>发送</span></p>
			    </div>
    		</c:when>
    </c:choose>
	<div class="knowledge_search">搜索：<input id="keywords" name="" type="text" /></div>
	
	<div class="knowledge_con" id="knowledge_con">
		 <c:choose>
		 	<c:when test="${isitem==1}">
		 		<c:choose>
		 		<c:when test="${haslist==0}">
				<div class="tips_wujilu">当前暂无记录</div>
				</c:when>
				<c:when test="${haslist==1}">
					<c:forEach var="obj" items="${knowledgelist.pageList}">
						<h3><a href='#'>${obj[1]}</a></h3>
				  		<div class='knowledge_condiv'><div class='fabu_btn'>发送</div>${obj[2]}
				  			<br>
				  			<br>
				  			<p>
				  			<c:choose>
				  				<c:when test="${obj[5]==1}">
				  				知识相关文档:    <a href="javascript:knowledgelist.downloadFile('${obj[3]}','${obj[4]}')">${obj[4]}</a>
								</c:when>
				  			</c:choose>
				  			</p>
								
				  		</div>
					</c:forEach>
				</c:when>
				</c:choose>
			</c:when>
		</c:choose>
		
    </div>
    <div class="knowledge_con" id="knowledge_gen" style="display:">
		<c:choose>
		 	<c:when test="${isitem==0}">
		 	<c:choose>
		 		<c:when test="${haslist==0}">
					<div class="tips_wujilu">当前暂无记录</div>
				</c:when>
				<c:when test="${haslist==1}">
					<c:forEach var="obj" items="${knowledgelist.pageList}">
						<h3><a href='#'>${obj[1]}</a></h3>
				  		<div class='knowledge_condiv'><div class='fabu_btn'>发送</div>${obj[2]}
				  			<br>
				  			<br>
				  			<p>
				  			<c:choose>
				  				<c:when test="${obj[5]==1}">
				  				知识相关文档:    <a href="javascript:knowledgelist.downloadFile('${obj[3]}','${obj[4]}')">${obj[4]}</a>
								</c:when>
				  			</c:choose>
				  			</p>
								
				  		</div>
					</c:forEach>
				</c:when>
				</c:choose>
			</c:when>
		</c:choose>
    </div>
    <div id="loading" class="tips_loading" style="display: none">查询中...</div>
    
</div>
<div id="item_pager" class="pager_index ui-widget wijmo-wijpager ui-helper-clearfix">
                  <ul id="itempagebarid">
	                  <c:choose>
						<c:when test="${haslist==1}">
		                  <c:if test="${!knowledgelist.firstPage}"><li><a href="javascript:knowledgelist.showLimitDiscountPage(0,'first')">首页</a></li> <li><a href="javascript:knowledgelist.showLimitDiscountPage(0,'previous')">上一页</a></li></c:if>
					  		<c:if test="${knowledgelist.firstPage}"><li><a>首页</a></li> <li><a>上一页</a></li></c:if>
					  		<c:if test="${!knowledgelist.lastPage}"><li><a href="javascript:knowledgelist.showLimitDiscountPage(0,'next')">下一页</a></li><li><a href="javascript:knowledgelist.showLimitDiscountPage(0,'last')">尾页</a></li></c:if>
					  		<c:if test="${knowledgelist.lastPage}"> <li><a>下一页</a></li>  <li><a>尾页</a></li></c:if>
	                  </c:when>
	                  </c:choose>
                  </ul>
 </div>
 <div id="gen_pager" class="pager_index ui-widget wijmo-wijpager ui-helper-clearfix" style="display:none">
 	 			<ul id="genpagebarid">
	                  <c:if test="${!knowledgelist.firstPage}"><li><a href="javascript:knowledgelist.showLimitDiscountPage(0,'first')">首页</a></li> <li><a href="javascript:knowledgelist.showLimitDiscountPage(0,'previous')">上一页</a></li></c:if>
				  		<c:if test="${knowledgelist.firstPage}"><li><a>首页</a></li> <li><a>上一页</a></li></c:if>
				  		<c:if test="${!knowledgelist.lastPage}"><li><a href="javascript:knowledgelist.showLimitDiscountPage(0,'next')">下一页</a></li><li><a href="javascript:knowledgelist.showLimitDiscountPage(0,'last')">尾页</a></li></c:if>
				  		<c:if test="${knowledgelist.lastPage}"> <li><a>下一页</a></li>  <li><a>尾页</a></li></c:if>
                  </ul>
 
 </div>
<!--[if !IE]>---工作台 ----<![endif]-->
<%@ include file="/WEB-INF/jsp/worktable/index.jsp" %>
</body>
</html>
	

