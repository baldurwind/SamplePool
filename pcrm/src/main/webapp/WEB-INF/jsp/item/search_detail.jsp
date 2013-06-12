<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../common/include.jsp" %>
<style>
	.ui-dialog .ui-dialog-content{ padding:0 !important;}
</style>
<c:choose>
	<c:when test="${itemSearch == null}">
		<div class="tips_wujilu">当前暂无记录</div>
	</c:when>
	<c:otherwise>
		<c:forEach items="${itemSearch.pageList}" var="item">
		 	<div class="behavior_item_list">
                 <img src="${item.picUrl}" />
                 <ul>
                     <li><div>${item.title}<em class="ui-small-icon ui-si-folder" onclick="itemKnowledge.relationKnowledge('${item.numid}')"></em></div></li>
                     <li><font class="FL">价格: </font>&nbsp;${item.price}</li>
                     <li>库存: <b class="thisColor">${item.num}</b>件</li>
                 </ul>
                 <div class="fabu_btn FR" onclick="ww_msg_send('http://item.taobao.com/item.htm?id=${item.numid}');"></div>
                 <div class="clear"></div>
         	</div>
		</c:forEach>
		<c:if test="${itemSearch != null}">
			<div class="ui-pager">
	        	 <ul>
	                <li class="ui-pager-step">第${itemSearch.page + 1}页</li>
	                <li class="ui-pager-count">共${itemSearch.pageCount}页</li>
	                <c:if test="${!itemSearch.firstPage}">
	                	<li class="ui-pager-firstPage" onclick="search('0');"></li>
	                	<li class="ui-pager-prePage" onclick="search('${itemSearch.page - 1}');"></li>
	                </c:if>
	                <c:if test="${itemSearch.firstPage}">
	                	<li class="ui-pager-firstPage-disable"></li>
	                	<li class="ui-pager-prePage-disable"></li>
	                </c:if>
	                <c:if test="${!itemSearch.lastPage}">
	                	 <li class="ui-pager-nextPage" onclick="search('${itemSearch.page + 1}');"></li>
	                	 <li class="ui-pager-lastPage" onclick="search('${itemSearch.pageCount}');"></li>
	                </c:if>
	                <c:if test="${itemSearch.lastPage}">
	                	<li class="ui-pager-nextPage-disable"></li>
		                <li class="ui-pager-lastPage-disable"></li>
	                </c:if>
	                <div class="clear"></div>
	            </ul>
	            <div class="clear"></div>
        	</div>
        </c:if>		
		<script type="text/javascript">		 
			$(function(){
				$(".resultList").find("div.behavior_item_list").hover(function(){ 
					$(this).find("div.fabu_btn").show();
				},function(){
					$(this).find("div.fabu_btn").hide();
				});
				$(".behavior_item_list").hover(function(){
					$(this).addClass("line_gray");
				},function(){
					$(this).removeClass("line_gray");
				});
				$("ul#searchWay li").click(function(){
					$(this).addClass("click").siblings('li').removeClass('click');
				    $(this).siblings('li').removeClass("click2")
					
				});
				$("#keywords").bind({
		        	change:function(events){
		        		itemKnowledge.search();
		        	}
	        	});
				$("ul#searchWay li").toggle(function(){$(this).addClass("click2")},function(){$(this).removeClass("click2")})
			});
		</script>
	</c:otherwise>
</c:choose>
 <input id="ww_msg_plugin" type="hidden" value="" />
      <div class="knowledge" id="knowledge" style="display:none">
        <div class="knowledge_search">商品知识搜索：<input id="keywords" name="" type="text" /></div>
        <div class="knowledge_con" id="knowledge_con">
            <div id="loading" class="tips_loading" style="display: block">查询中...</div>
        </div>
     </div>
<script>

//////////////////////////商品关联知识/////////////////////////////////

var itemKnowledge = {
		curitemid:"-1",
		relationKnowledge:function(itemid){
			$("#knowledge").dialog({
				 width:300,
				 height:350,
				 title:'商品关联知识库'
			});
			$("#knowledge").show();
			$("#knowledge").val("");
			curitemid = itemid;
			itemKnowledge.loadItemKnowledge(curitemid);
		},
		loadItemKnowledge:function(itemid){
			var url = contextPath+"knowledge/itemknowledge/"+itemid;
			var params = "seller="+$("#seller").val()+"&cpage=0&page=first";
			itemKnowledge.loadData(url,params,"post");
		},
		loadData:function(url,params,method){
			$.ajax({
	    		type:method,
	            dataType: "json",
	            data:params,
	            url: url,
	            error: function(){
	       	    }, 
	            success: function (json){
	            	if(json!=null){
		            	var rows = json.row;
		            	var knowlist = "";
		            	if(rows!=null&&rows.length>0){
		            		var rindex = 0;
		            		for (var i = 0; i<rows.length; i++) {
		                    	 var n = rows[i];
		                    	 knowlist +="<div class='ui-accordion'><h3 class='ui-accordion-header'><span></span><a id='"+rindex+"_title'>"+n.title+"</a></h3>"+
		    				   			 	   "<div class='ui-accordion-content knowledge_condiv'><div id='"+rindex+"_cont'>"+n.content+
		                    	 				"</div><div class='fabu_btn' onclick='sendWangWangMsg(\""+rindex+"\")'></div>";
		                    	 				knowlist +="</div></div>";
		    				   	rindex ++;               
		                    } 
	            			$("#knowledge_con").html(knowlist);
	            			ieMinHeightBug($(".knowledge_condiv"),60);
	            			itemKnowledge.accordingInit();
		            	}else{
		            		$("#knowledge_con").html("<div class='tips_wujilu'>当前暂无记录</div>");
		            	}
	            	}
	            }
	        });
		},
		accordingInit:function(){
			$("#knowledge .ui-accordion").accordion({"extend":true});
			$("#knowledge .ui-accordion-content").hover(function(){
	      		$(this).addClass("publicHoverBgColor").find(".fabu_btn").show();
	        },function(){
		      	$(this).removeClass("publicHoverBgColor").find(".fabu_btn").hide();;
			});
		},
		search:function(){
			var keyword = $("#keywords").val();
			if(keyword!=''&&keyword.length>0){
	    		var url = contextPath+"knowledge/search";
	    		var params = "seller="+encodeURIComponent($("#seller").val())+"&cpage=0&page=first&itemid="+curitemid+"&keywords="+encodeURIComponent(keyword);
	    		itemKnowledge.loadData(url,params,"get");
			}else{
				var url = contextPath+"knowledge/itemknowledge/"+curitemid;
		    	var params = "seller="+$("#seller").val()+"&cpage=0&page=first";
		    	itemKnowledge.loadData(url,params,"post");
			}
		}
};

</script>