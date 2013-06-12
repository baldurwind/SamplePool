<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="../../common/include.jsp" %>
<script>
	function xiangcha(startTime, endTime, id) {
		var date1 = StringToDate(startTime);
		var date2 = StringToDate(endTime);
		var date3=date2.getTime()-date1.getTime();  //时间差的毫秒数
		//计算出相差天数
		var days=Math.floor(date3/(24*3600*1000));
		//计算出小时数
		var leave1=date3%(24*3600*1000);    //计算天数后剩余的毫秒数
		var hours=Math.floor(leave1/(3600*1000));
		//计算相差分钟数
		var leave2=leave1%(3600*1000);        //计算小时数后剩余的毫秒数
		var minutes=Math.floor(leave2/(60*1000));
		//计算相差秒数
		var leave3=leave2%(60*1000);      //计算分钟数后剩余的毫秒数
		var seconds=Math.round(leave3/1000);
		var msg = "abc";
		if(days >= 0  && hours >= 0 && minutes >= 0 && seconds >=0) {
			if(days > 0 ) {
				msg = days+"天";
			} else if (hours > 0) {
				msg = hours+"小时";
			} else if (minutes > 0) {
				msg = minutes+"分钟";
			} else if (seconds > 0) {
				msg = seconds+"秒";
			}
		} else {
			msg = "-";
		}
		$("#spanShowDate" + id).text(msg);
	}
	
	function StringToDate(string){   
	    return new Date(Date.parse(string.replace(/-/g, "/")));   
	}
</script>
	<c:choose>
		<c:when test="${coupon_buyer == null}">
			<div class="tips_wujilu">当前暂无记录</div>
		</c:when>
		<c:otherwise>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" id="usableList">
				<tr class="head">
                    <td width="25%" >优惠券</td>
                    <td width="15%" >金额</td>
                    <td width="20%" >剩余时间</td>
                    <td width="25%" >使用条件</td>
                    <td width="15%">状态</td>
                </tr>
				<c:forEach var="obj" items="${coupon_buyer.pageList}">
		            <tr <c:if test="${obj[4] == 'unused'}">class="zt_wy"</c:if>>
		                <td>满就送优惠</td>
		                <td><c:choose><c:when test="${obj[4] == 'unused'}"><span class="price">${obj[1]/100}元</span></c:when><c:otherwise>${obj[1]/100}元</c:otherwise></c:choose></td>
		                <td>
		                	<fmt:parseDate value="${obj[3]}" var="date" pattern="yyyy-MM-dd HH:mm:ss" />
							<span id="spanShowDate${obj[0]}"></span>
							<script>
								xiangcha('${currentTime}', '${date}', '${obj[0]}');
							</script>
		                </td>
		                <td>满${obj[2]/100}元可用</td>
		                <td>
		                	<c:choose>
		                		<c:when test="${obj[4] == 'unused'}">未使用</c:when>
		                		<c:when test="${obj[4] == 'using'}">使用中</c:when>
		                		<c:when test="${obj[4] == 'used'}">已使用</c:when>
		                		<c:when test="${obj[4] == 'overdue'}">已过期</c:when>
		                		<c:when test="${obj[4] == 'transfered'}">已转发</c:when>
		                		<c:otherwise>-</c:otherwise>
		                	</c:choose>
		                </td>
		            </tr>   
   				</c:forEach>
              <tr>
              	<td colspan="5">
              		<div class="ui-pager">
                        <ul>
                            <li class="ui-pager-step">第${coupon_buyer.page + 1}页</li>
                            <li class="ui-pager-count">共${coupon_buyer.pageCount}页</li>
                            <c:if test="${!coupon_buyer.firstPage}">
                            	<li class="ui-pager-firstPage" onclick="showBuyerCouponList('0')"></li>
                            	<li class="ui-pager-prePage" onclick="showBuyerCouponList('${coupon_buyer.page - 1}')"></li>
                            </c:if>
                            <c:if test="${coupon_buyer.firstPage}">
                            	<li class="ui-pager-firstPage-disable"></li>
                            	<li class="ui-pager-prePage-disable"></li>
                            </c:if>
                            <c:if test="${!coupon_buyer.lastPage}">
                            	<li class="ui-pager-nextPage" onclick="showBuyerCouponList('${coupon_buyer.page + 1}')"></li>
                            	<li class="ui-pager-lastPage" onclick="showBuyerCouponList('${coupon_buyer.pageCount}')"></li>
                            </c:if>
                            <c:if test="${coupon_buyer.lastPage}">
                            	<li class="ui-pager-nextPage-disable"></li>
                            	<li class="ui-pager-lastPage-disable"></li>
                            </c:if>
                        </ul>
                        <div class="clear"></div>
             		</div>
              	</td>
              </tr>
        	</table>
		</c:otherwise>
	</c:choose>