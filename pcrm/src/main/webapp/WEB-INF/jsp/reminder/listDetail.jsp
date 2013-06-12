<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ include file="../common/include.jsp" %>

<c:choose>
	<c:when test="${reminders == null}">
		<div class="tips_wujilu">当前暂无记录</div>
	</c:when>
	<c:otherwise>
		<ul class="eventList">	
			<c:forEach items="${reminders.list}" var="reminder">
				 <li>
                      <h3>${reminder.content}
                      <c:if test="${reminder.buyerNick != null}">
                      	关联客户：${reminder.buyerNick}
                      </c:if>
                      </h3>
                      <div class="info">
                          <p class="beiwanginfo">
                          	<c:choose>
	                       		<c:when test="${reminder.status == 0}">
									<c:choose>
										<c:when test="${reminder.dateDifferentMin > 0 && reminder.dateDifferentMin < 30}">
											<span class=" icon-tz cRed">${reminder.dateDifferentMsg}后到期</span>	
										</c:when>
										<c:when test="${reminder.dateDifferentMin > 0}">
											<span class=" icon-tz cRed">${reminder.dateDifferentMsg}后到期</span>
										</c:when>
										<c:when test="${reminder.dateDifferentMin < 0}">
											<span class="cGray">已过期${reminder.dateDifferentMsg}</span>
										</c:when>
										<c:otherwise>
											<span class="cGray">已到期</span>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:when test="${reminder.status == 1}">
									<span class="cGray">已成功</span>
								</c:when>
								<c:when test="${reminder.status == 2}">
									<span class="cGray">已取消</span>　
								</c:when>
							</c:choose>
                          </p>
                          <p class="beiwangtime"><span><fmt:formatDate value="${reminder.tipTime}" pattern="yyyy-MM-dd HH:mm" type="both"/></span></p>
                      </div>
                      <div class="mehtodPanel">
                           <c:if test="${reminder.status == 0}">
				            	<img class="mp-zx" title="完成" onclick="updateReminderStatus('${reminder.id}', '1');"  src="${static_resource_host}/resource/newcss/themes/classic/images/ui-smail-icon.png" />
								<img class="mp-sc" title="取消" onclick="updateReminderStatus('${reminder.id}', '2');"  src="${static_resource_host}/resource/newcss/themes/classic/images/ui-smail-icon.png" />
								<img class="mp-bj" title="编辑"   onclick="toUpdate('${reminder.id}');"  src="${static_resource_host}/resource/newcss/themes/classic/images/ui-smail-icon.png" />
			            	</c:if>
                       </div>   
						<div class="clear"></div>
                  </li>
			</c:forEach>
		</ul>
		<c:if test="${reminders != null}">
			<div class="ui-pager">
	              <ul>
	                 <li class="ui-pager-step">第${reminders.pageNo}页</li>
	                 <li class="ui-pager-count">共${reminders.totalPages}页</li>
	                 <c:choose>
						<c:when test="${reminders != null}">
							<input type="hidden" value="${reminders.pageNo}" id="txtPageNo"/>
							<c:choose>
								<c:when test="${reminders.pageNo == 1}"> <li class="ui-pager-firstPage-disable"></li><li class="ui-pager-prePage-disable"></li></c:when>
								<c:otherwise>
									 <li class="ui-pager-firstPage" onclick="getRemindersList('1')"></li>
									 <li class="ui-pager-prePage" onclick="getRemindersList('${reminders.pageNo - 1}')"></li>
								</c:otherwise>
							</c:choose>
				   		<c:choose>
		            	<c:when test="${reminders.pageNo == reminders.totalPages || reminders.totalPages == 0}"> <li class="ui-pager-nextPage-disable"></li><li class="ui-pager-lastPage-disable"></li></c:when>
		            		<c:otherwise>
		                		<li class="ui-pager-nextPage" onclick="getRemindersList('${reminders.pageNo + 1}')"></li> 
		                		<li class="ui-pager-lastPage" onclick="getRemindersList('${reminders.totalPages}')"></li> 
		            		</c:otherwise>              
		        		</c:choose>
		    			</c:when>
					 </c:choose>
	                 <div class="clear"></div>
	             </ul>
	             <div class="clear"></div>
	         </div>
         </c:if>
		<script>
			function getRemindersList(pageNo) {
				$("#divListDetail").html("<div class='tips_loading'>查询中...</div>");
				$("#divListDetail").load("reminder/listDetail?type=" + $("#txtState").val() +"&nick=" + getCurUser() + "&pageNo=" + pageNo);
			}
			
			
			//修改客服备忘状态
			function updateReminderStatus(id, status) {
				$.tooltip({"type":"confirm","content":"您确定吗？","callback":function(result) {
						if(result == true) {
							$.ajax({
						  		type: 'post',
						  		url: "reminder/updateStatus",
						  		dataType: 'json',
						  		data: {"id":id, "status":status, "userid":getCurUser()},
						  		beforeSend: function() {
						  			showDisplay();
						  		},
						  		success: function(msg) {
						  			hideDisplay();
						  			if(msg.result=='true') {
						  				loadRemindStatusCount("noBeginCount",0,"未完成");
						  			    loadRemindStatusCount("overdueCount",2,"已取消");
						  			    loadRemindStatusCount("successCount",1,"已完成");
						  				getRemindersList($("#txtPageNo").val());
						  				showMessage("修改成功");
						  			} else {
						  				showMessage("修改失败");
						  			}
						  		},
						  		error: function() {
						  			hideDisplay();
						  		}
						  	});
						}
					}
				});
			}
			
			
		</script>
	</c:otherwise>
</c:choose>