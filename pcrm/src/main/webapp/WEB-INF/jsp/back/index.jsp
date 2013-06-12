<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../common/include.jsp"%>
<html>
<head>
<%@ include file="../common/header.jsp"%>
<title>售后面板</title>
</head>
<body>
	<!--主体-->
	<div class="wrapper">
		<!--当前客户信息-->
		<div class="childModuleTit">
			<span class="F12">当前客户信息：${buyer}</span>&nbsp;&nbsp;<span> <!-- 
			<strong class="FL mgr5">店内等级：</strong><label
			class="FL ui-icon ui-icon-vip0 mgr5"></label> <span class="FL mgr5">VIP会员</span><em
			class="FL mgr5 thisColor">8折</em>
			 -->
			</span>
		</div>
		<div class="clear"></div>
		<!--客户消费信息-->
		<div class="currCustomerInfo"  >
			<table width="100%" style="font-size: 12px;" cellspacing="0" cellpadding="0">
				<tr class="head">
					<td>本店售后数</td>
					<td>退款次数</td>
					<td>总消费次数</td>
				</tr>
				<tr class="thisColor FB">
					<td>${csCount}</td>
					<td>
						<c:choose>
							<c:when test="${refundCount != null}">
							 	${refundCount}
							</c:when>
							<c:otherwise>
								&nbsp;
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${tradeCount != null}">
								${tradeCount}
							</c:when>
							<c:otherwise>
								&nbsp;
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</table>
		</div>
		<div class="ui-tab">
			<ul class="ui-tab-ul">
				<li class="ui-tab-ul-li-open"><a>处理中的订单(<span
						class="thisColor">${fn:length(customerServiceIssuess)}</span>)
				</a></li>
				<li><a>待处理订单(${fn:length(customerServiceIssuess2)})</a></li>
			</ul>
			<div class="ui-tab-con">
			<c:forEach items="${customerServiceIssuess}" var="cs">
				<div class="publicOrderlist">
					
						<div class="topinfo">
							<p class="left">
								<label>订单编号：${cs.trade[0]}</label><br />
								<label class="time"><fmt:parseDate var="time" value="${cs.trade[3]}" type="DATE" pattern="yyyy-MM-dd HH:mm:ss"/> 
									<span class="cGray">成交时间：<fmt:formatDate value="${time}" pattern="yyyy-MM-dd HH:mm:ss" type="both"/></span>
								</label>
							</p>
							<ul class="right">
								<c:if
									test="${cs.trade[4]=='WAIT_BUYER_CONFIRM_GOODS'||cs.trade[4]=='TRADE_BUYER_SIGNED'||cs.trade[4]=='TRADE_FINISHED'}">
									<li><a href="javascript:trace(${cs.trade[0]})"
										class="fahuo_time" title="物流追踪"></a></li>
								</c:if>
								<li><a href="javascript:shipping(${cs.trade[0]})"
									class="shuohuo_info" title="收货信息"> </a></li>
							</ul>
						</div>
							<div class="clear"></div>
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="publicOrderlist_table">
								<tr>
									<td class="shangpin">
										<c:forEach var="oid" items="${cs.tradeOrder}">
										 	<div class="list">
										 	
	                                            <p title="${oid[5]}" class="FB">${fn:substring(oid[5],0, 15)}...</p> 
	                                            <p> 单价:${oid[6]},  数量: ${oid[8]}</p>
                                       		 </div>
                                        </c:forEach>
									</td>
									<td>
										<p class="thisColor">${cs.trade[2]}</p>
										<p>
											<c:if test="${cs.trade[5]==1}">(卖家包邮${ cs.trade[1]})</c:if>
											<c:if test="${cs.trade[5]==0}">(含快递${ cs.trade[1]})</c:if>
										</p> <!-- <p class="tmar12"><a href="#">修改价格</a></p> --></td>
									<td>
										<span class="cGreen FB">
										<c:choose>
											<c:when test="${cs.trade[4]=='TRADE_FINISHED' }">交易成功 <br /></c:when>
											<c:when test="${cs.trade[4]=='TRADE_CLOSED'}">交易关闭</c:when>
											<c:when test="${cs.trade[4]=='TRADE_CLOSED_BY_TAOBAO'}">淘宝关闭交易</c:when>
											<c:when test="${cs.trade[4]=='WAIT_BUYER_CONFIRM_GOODS'}">卖家已发货</c:when>
											<c:when test="${cs.trade[4]=='WAIT_SELLER_SEND_GOODS'}">买家已付款  </c:when>
											<c:when test="${cs.trade[4]=='WAIT_BUYER_PAY'}">等待买家付款</c:when>
											<c:when test="${cs.trade[4]=='TRADE_BUYER_SIGNED'}">买家已签收,货到付款专用</c:when>
											<c:when test="${cs.trade[4]=='TRADE_NO_CREATE_PAY'}">没有创建支付宝交易</c:when>
										</c:choose>
									</span></td>
								</tr>
							</table>
						</div>	
						<div class="order_mianban_top order_mianban_top_open">
							<span>${cs.title}</span><label><a href="javascript:void(0);" onclick="checkPucher('${cs.id}', this);">展开</a></label>
						</div>
						<div  id="divCSDetail${cs.id}">
														
						</div>
				</c:forEach>
			</div>
			<div class="ui-tab-con">
					<c:forEach items="${customerServiceIssuess2}" var="cs">
					<div class="publicOrderlist">
						<div class="topinfo">
							<p class="left">
								<label>订单编号：${cs.trade[0]}</label><br />
								<label class="time"><fmt:parseDate var="time" value="${cs.trade[3]}" type="DATE" pattern="yyyy-MM-dd HH:mm:ss"/> 
									<span class="cGray">成交时间：<fmt:formatDate value="${time}" pattern="yyyy-MM-dd HH:mm:ss" type="both"/></span>
								</label>
							</p>
							<ul class="right">
								<c:if
									test="${cs.trade[4]=='WAIT_BUYER_CONFIRM_GOODS'||cs.trade[4]=='TRADE_BUYER_SIGNED'||cs.trade[4]=='TRADE_FINISHED'}">
									<li><a href="javascript:trace(${cs.trade[0]})"
										class="fahuo_time" title="物流追踪"></a></li>
								</c:if>
								<li><a href="javascript:shipping(${cs.trade[0]})"
									class="shuohuo_info" title="收货信息"> </a></li>
							</ul>
						</div>
							<div class="clear"></div>
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="publicOrderlist_table">
								<tr>
									<td class="shangpin">
										 <div class="list">
										 	<c:forEach var="oid" items="${cs.tradeOrder}">
	                                            <p title="${oid[5]}" class="FB">${fn:substring(oid[5],0, 15)}...</p> 
	                                            <p> 单价:${oid[6]},  数量: ${oid[8]}</p>
                                            </c:forEach>
                                        </div>
									</td>
									<td>
										<p class="thisColor">${cs.trade[2]}</p>
										<p>
											<c:if test="${cs.trade[5]==1}">(卖家包邮${ cs.trade[1]})</c:if>
											<c:if test="${cs.trade[5]==0}">(含快递${ cs.trade[1]})</c:if>
										</p> <!-- <p class="tmar12"><a href="#">修改价格</a></p> --></td>
									<td>
										<span class="cGreen FB">
										<c:choose>
											<c:when test="${cs.trade[4]=='TRADE_FINISHED' }">交易成功 <br /></c:when>
											<c:when test="${cs.trade[4]=='TRADE_CLOSED'}">交易关闭</c:when>
											<c:when test="${cs.trade[4]=='TRADE_CLOSED_BY_TAOBAO'}">淘宝关闭交易</c:when>
											<c:when test="${cs.trade[4]=='WAIT_BUYER_CONFIRM_GOODS'}">卖家已发货</c:when>
											<c:when test="${cs.trade[4]=='WAIT_SELLER_SEND_GOODS'}">买家已付款  </c:when>
											<c:when test="${cs.trade[4]=='WAIT_BUYER_PAY'}">等待买家付款</c:when>
											<c:when test="${cs.trade[4]=='TRADE_BUYER_SIGNED'}">买家已签收,货到付款专用</c:when>
											<c:when test="${cs.trade[4]=='TRADE_NO_CREATE_PAY'}">没有创建支付宝交易</c:when>
										</c:choose>
									</span></td>
								</tr>
							</table>
							</div>
						<div class="order_mianban_top order_mianban_top_open">
							<span>${cs.title}</span><label><a href="javascript:void(0);" onclick="checkPucher('${cs.id}', this);">展开</a></label>
						</div>
						<div class="pubOrderDetial" id="divCSDetail${cs.id}">
														
						</div>
					</c:forEach>
			</div>
		</div>
	</div>
	<div class="order_mianban" id="memo" style="display:none;" title="备忘"></div>
	<div class="order_mianban" id="trace"  style="display:none"   title="快递跟踪"   ></div>
	<div class="order_mianban" id="shipping" style="display:none;" title="收货信息"></div>
</body>
</html>
<script type="text/javascript">
	$(function(){
		$(".ui-tab").tabChange();
	});
	
	function checkPucher(id, aid) {
		var atext = $(aid).text();
		if(atext == "展开") {
			$("#divCSDetail" + id).html("正在加载...");
			$("#divCSDetail" + id).load("customerService/back/detail?id=" + id + "&sellerNick=" + encodeURIComponent("${sellerNick}"));
			$(aid).text("收起");
		} else {
			$("#divCSDetail" + id).html("");
			$(aid).text("展开");	
		}
	}
	
	function trace(tid){
		$('#trace').html("<div class='tips_loading'>查询中...</div>"); 
		$('#trace').load("trade/trace?seller="+getSeller()+"&tid="+tid);
		 $('#trace').dialog('open');
		 $('#trace').dialog({autoOpen: true,modal: true, resizable:false,width:400,
			 buttons:{"关闭": function() {$(this).dialog("close");}}
			});
	}
	
	function shipping(tid){
		 $('#shipping').load("trade/shipping?seller="+getSeller()+"&tid="+tid);
		 $('#shipping').html("<div class='tips_loading'>查询中...</div>");
		 $('#shipping').dialog('open');
		 $('#shipping').dialog({autoOpen: true,modal: true, resizable:false,width:400,height:300,
			 buttons:{"关闭": function() {$(this).dialog("close");}}
			});
	}
</script>