<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<!--  0.tid, 1.post_fee,2.payment,3.created,4.status,5.has_post_fee ,6.seller_flag FROM trade  -->
<!-- 0.oid,1.tid,2.num_iid,3.seller_nick,4.buyer_nick, 5.title,6.price,7.pic_path,8.num,9.status,10.payment,11.created,12.sku_properties_name -->
 
    <ul class="piliang">
            <li onclick="batchChangePostage()">批量改运费</li>
            <li onclick="batchUpdateMemo()">批量修改备注</li>
            <li  ><input name="checktid" name="input" type="checkbox" onclick="selectAllTrade(this)"/>全选</li>
        </ul>
        <div class="clear"></div>
<c:choose>
<c:when test="${empty  tidList}"><div class="tips_wujilu">当前暂无记录</div></c:when>
<c:otherwise>
<input id="current_page" type="hidden" value="${current_page}"/>
	<!-- <ul class="orderlist_title piliang clearfix"><li>批量改运费</li><li>批量改备注</li></ul> -->
<c:forEach var="tid" items="${tidList.pageList}">
<c:set var="tid_flag" value="true"></c:set>
	<div class="publicOrderlist">
	 <div class="topinfo">
		<p class="left">
			<input name="checktid" name="input" type="checkbox"/><label>订单编号：<span>${tid[0] }</span></label><br/>
			 <label class="time">成交时间：${tid[3]}</label>
		</p>
		<ul class="right">
		<c:if test="${tid[4]=='WAIT_BUYER_CONFIRM_GOODS'||tid[4]=='TRADE_BUYER_SIGNED'||tid[4]=='TRADE_FINISHED'}">
			 <li><a href="javascript:trace(${tid[0]})" class="fahuo_time"  title="物流追踪"></a></li>
		</c:if>
		
		<c:if test="${tid[4]!='TRADE_NO_CREATE_PAY'&&tid[4]!='WAIT_BUYER_PAY'&&tid[4]!='TRADE_CLOSED_BY_TAOBAO'}">
			<li><a href="javascript:shipping(${tid[0]})" class="shuohuo_info" title="收货信息"> </a></li><!-- 全有 -->	
		</c:if>
		
            <li><a  id="memo_${tid[0]}" href="javascript:memo(${tid[0]})" class="beiwang_${tid[6]}" title="备忘"></a> </li> <!-- 全有 -->
    
            <c:if test="${tid[4]=='TRADE_CLOSED'||tid[4]=='TRADE_FINISHED'||tid[4]=='TRADE_BUYER_SIGNED'||tid[4]=='WAIT_BUYER_CONFIRM_GOODS'||tid[4]=='WAIT_SELLER_SEND_GOODS'}">
	            <li><a href="javascript:getLink1('customerService/index','${tid[0]}')" class="shouhou" title="售后问题"></a></li>
            </c:if>
		</ul>
		</div>
		<div class="clear"></div>
	<table class="publicOrderlist_table" border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td class="shangpin">
            <div class="list">
            <c:forEach var="oid" items="${oidList }">
				<c:if test="${tid[0]==oid[1]}">
					<p title="${oid[5]}" class="FB">
    					<c:choose>
    					  <c:when test="${fn:length(oid[5]) > 10}">${fn:substring(oid[5], 0, 25)}...</c:when> 
    					<c:otherwise>${oid[5]}</c:otherwise>
    					</c:choose>
					</p> 
					<c:if test="${not empty oid[12] }">
						<p class="cGray" title="${oid[12]}">
						<c:choose>
    					  <c:when test="${fn:length(oid[12]) > 25}">${fn:substring(oid[12], 0, 25)}...</c:when> 
    					<c:otherwise>${oid[12]}</c:otherwise>
    					</c:choose>
 					</p></c:if>
					<p>单价:${oid[6]},  数量: ${oid[8]}</p>
                 	 </c:if>
                 	</c:forEach>
			</div>
            </td>
            <td><p class="thisColor">${tid[2]}</p>
           	 <p id="postage_${tid[0]}">
            <c:if test="${tid[1]=='0.00'}">卖家包邮</c:if>
            <c:if test="${tid[1]!='0.00'}">(含快递${ tid[1]})</c:if></p>
            </td>
            <td><span class="cGreen FB">
<c:choose>
	<c:when test="${tid[4]=='TRADE_FINISHED' }"><p class="cGreen FB">交易成功 </p><br/></c:when>
	<c:when test="${tid[4]=='TRADE_CLOSED'}">交易关闭</c:when>
	<c:when test="${tid[4]=='TRADE_CLOSED_BY_TAOBAO'}">淘宝关闭交易</c:when>
	<c:when test="${tid[4]=='WAIT_BUYER_CONFIRM_GOODS'}">卖家已发货</c:when>
	<c:when test="${tid[4]=='WAIT_SELLER_SEND_GOODS'}">买家已付款  </c:when>
	
	<c:when test="${tid[4]=='WAIT_BUYER_PAY'}">等待买家付款
		<c:if test="${tid[1]!='0.00'}"><br/>
		  <a href="javascript:postage(${tid[0]},${tid[1]},${tid[1]!='0.00'})">修改运费</a></c:if>
 <br/><a href="javascript:close(${tid[0]})">关闭交易</a>
	</c:when>
	<c:when test="${tid[4]=='TRADE_BUYER_SIGNED'}">买家已签收</c:when>
	<c:when test="${tid[4]=='TRADE_NO_CREATE_PAY'}">
	<br/>
	未创建支付宝交易
	<c:if test="${tid[5]==0}"><br/>
	   <a href="javascript:postage(${tid[0]},${tid[1]},${tid[5]})">修改运费</a></c:if>
	<br/><a href="javascript:close(${tid[0]})">关闭交易</a> 
	</c:when>
</c:choose>

</span></td>
        </tr>
     </table>
	</div>
</c:forEach>
 <div class="ui-pager">
        <ul>
                <li class="ui-pager-step">第${tidList.page+1}页</li>
                <li class="ui-pager-count">共${tidList.pageCount }页</li>
                
                
             <c:if test="${!tidList.firstPage}"><li class="ui-pager-firstPage" onclick="tradePage('first')"></li><li class="ui-pager-prePage" onclick="tradePage('previous')"></li></c:if>
			<c:if test="${tidList.firstPage}"><li class="ui-pager-firstPage-disable"> <li><li class="ui-pager-prePage-disable"></li></c:if>
			<c:if test="${!tidList.lastPage}"><li class="ui-pager-nextPage" onclick="tradePage('next')"></li><li class="ui-pager-lastPage" onclick="tradePage('last')"></li></c:if>
			<c:if test="${tidList.lastPage}"><li class="ui-pager-nextPage-disable"></li>  <li class="ui-pager-lastPage-disable"> </li></c:if>
                <div class="clear"></div>
             </ul>
            <div class="clear"></div>
        </div>
</c:otherwise>
</c:choose>
