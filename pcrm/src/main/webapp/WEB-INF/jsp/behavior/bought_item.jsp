<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<input id="current_page" type="hidden" value="${current_page}"/>
 <input id="ww_msg_plugin" type="hidden" value="" />
    <c:choose>
    <c:when test="${not empty boughtitem.pageList }">
    <c:forEach var="obj" items="${boughtitem.pageList}">
    <div class="behavior_item_list">
    	<img src="${obj[3]}" />
        <ul>
        	<li>${obj[2]}</li>
            <li><font class="FL">价格:</font><font class="thisColor FL">${obj[4] }</font><em class="FL">|</em><font class="FL">库存:</font>  <font class="FL">${obj[5] }件</font></li>
            <li >购买时间:<b>${fn:substring(obj[6], 0, 16)}</b> | </b>购买次数<b>${obj[1]}</b>次</li>
        </ul>
       <div class="fabu_btn FR" onclick='ww_msg_send("http://item.taobao.com/item.htm?id=${obj[0]}")'></div>
      <div class="clear"></div>
    </div>
    </c:forEach>
         <div class="ui-pager">
        	 <ul>
	        	 <li class="ui-pager-step">第${boughtitem.page+1}页</li>
				<li class="ui-pager-count">共${boughtitem.pageCount }页</li>
        	 <c:if test="${!boughtitem.firstPage}"><li class="ui-pager-firstPage" onclick="showBoughtItemPage('first')"></li> <li class="ui-pager-prePage" onclick="showBoughtItemPage('previous')"></li></c:if>
			 <c:if test="${boughtitem.firstPage}"><li class="ui-pager-firstPage-disable"></li><li class="ui-pager-prePage-disable"></li></c:if>
			 <c:if test="${!boughtitem.lastPage}"><li class="ui-pager-nextPage" onclick="showBoughtItemPage('next')"></li><li  class="ui-pager-lastPage" onclick="showBoughtItemPage('last')"></li></c:if>
			 <c:if test="${boughtitem.lastPage}"> <li class="ui-pager-nextPage-disable"></li> <li class="ui-pager-lastPage-disable"></li></c:if>
            </ul>
              <div class="clear"></div>
  </div>
    </c:when>
    <c:otherwise><div class="tips_wujilu">当前暂无记录</div></c:otherwise>
    </c:choose>
  <script>
 $(".ui-tab-con").find("div.behavior_item_list").hover(function(){ 
		$(this).addClass("line_gray").find("div.fabu_btn").show();
	},function(){
		$(this).removeClass("line_gray").find("div.fabu_btn").hide();
	});
 </script>   
    
