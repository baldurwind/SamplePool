<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/include.jsp" %>
<c:choose>
	<c:when test="${promotions == null}">
		<div class="tips_wujilu">当前暂无记录</div>
	</c:when>	
	<c:otherwise>
		 <ul class="eventList">
		 	 <c:forEach items="${promotions.list}" var="prom">
				 <li>
                     <h3>${prom.goodsName}</h3>
                     <div class="info">
                     	<p>
                     		<c:choose>
			             		<c:when test="${prom.status == 0}">
			             			<c:choose>
					             		<c:when test="${prom.type == 1}">
					             			<span class="icon-tz thisColor">降价优惠通知</span>
					             		</c:when>
					             		<c:otherwise>
					             			<span class="icon-tz thisColor">缺货登记</span>
					             		</c:otherwise>
					             	</c:choose>	
			             		</c:when>
			             		<c:when test="${prom.status == 1}">
			             			<span class="ft-gray">已提醒</span>
			             		</c:when>
			             		<c:when test="${prom.status == 2}">
			             			<span class="ft-gray">已取消</span>
			             		</c:when>
		             		</c:choose>
                     		<span><fmt:formatDate value="${prom.expiredDate}" pattern="yyyy-MM-dd HH:mm:ss" type="both"/></span></p>
                         <p><span>客服：${prom.wangwangNick}</span></p>
                     </div>
                     <div class="mehtodPanel" style=" text-align:right;">
                     	<c:if test="${prom.status == 0}">
                     		<img class="mp-zx" name="updateStatus" style="display:none;" title="完成" onclick="javascript:updatePromotionStatus('${prom.id}', '1');"  src="${static_resource_host}/resource/newcss/themes/classic/images/ui-smail-icon.png" />
                            <img class="mp-sc" name="updateStatus" style="display:none;" title="取消" src="${static_resource_host}/resource/newcss/themes/classic/images/ui-smail-icon.png"  onclick="javascript:updatePromotionStatus('${prom.id}', '2');"/>
                            <img class="mp-bj" name="toUpdate"   style="display:none;" title="编辑" src="${static_resource_host}/resource/newcss/themes/classic/images/ui-smail-icon.png"  onclick="toUpdatePromotion('${prom.id}');"/>
                        </c:if>
                        <div class="clear"></div>
                     </div>   
	             </li>
			</c:forEach>
		</ul>
		
		<div class="ui-pager">
	        <ul>
	        	<li class="ui-pager-step">第${promotions.pageNo}页</li>
				<li class="ui-pager-count">共${promotions.totalPages}页</li>
	        	<c:choose>
	  				<c:when test="${promotions != null}">
	  					<input type="hidden" value="${promotions.pageNo}" id="txtPageNo"/>
			  			<c:choose>
			  				<c:when test="${promotions.pageNo == 1}"><li class="ui-pager-firstPage-disable"></li><li class="ui-pager-prePage-disable"></li></c:when>
			  				<c:otherwise>
			  					<li class="ui-pager-firstPage" onclick="loadPromotionList('1')"></li><li class="ui-pager-prePage" onclick="loadPromotionList('${promotions.pageNo - 1}')"></li>
			  				</c:otherwise>
			  			</c:choose>
		  			   <c:choose>
		                   <c:when test="${promotions.pageNo == promotions.totalPages || promotions.totalPages == 0}"><li class="ui-pager-nextPage-disable"></li><li class="ui-pager-lastPage-disable"></li></c:when>
		                   <c:otherwise>
		                   		<li class="ui-pager-nextPage" onclick="loadPromotionList('${promotions.pageNo + 1}')"></li>
								<li class="ui-pager-lastPage" onclick="loadPromotionList('${promotions.totalPages}')"></li>
		                   </c:otherwise>              
		               </c:choose>
	              		</c:when>
	            </c:choose>
	        </ul>
	        <div class="clear"></div>
		</div>
	</c:otherwise>
</c:choose>

<script>
$(function(){
	authButtonResourceByName("/leadsMemo");
});
</script>