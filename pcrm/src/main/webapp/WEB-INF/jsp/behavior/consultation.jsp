<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<style>
	.ui-dialog .ui-dialog-content{ padding:0 !important;}
</style>
<h3 class="ui-accordion-header"><span></span><a>咨询记录</a></h3>
 <div class="ui-accordion-content">
<div id="zhixunList" class="zixunList">
<c:choose>
<c:when test="${ empty mixEntryList}"><div class="tips_wujilu">当前暂无记录</div></c:when>
<c:otherwise>
	<div class="navlist">
	<ul class="list">
		<c:forEach var="entry" items="${mixEntryList}" varStatus="status">
			<li    <c:if test="${status.index==0}">class="curr first"</c:if> >
					<a  id="item_${status.index}" href="javascript:showItemBasic('${entry[0]}','${entry[5]}','${entry[7]}')"><img src="${entry[2]}"  /></a> 
			<div class="zixun_tips thisColor">${entry[3]}</div> 
			</li>
		</c:forEach></ul>
	</div>
	<p class="ui-icon preBtn preS"></p>
	<p class="ui-icon nextBtn nextS"></p>
         <div class="zixunList_Con">      <!--   <div id="sku_module"></div> -->
              <c:forEach var="entry" items="${mixEntryList}" varStatus="item_status"><!--  循环商品 -->
               	<c:set  var="props_name" value="${fn:split(entry[4],',')}"></c:set>
               	<div   name="cons_item"  class="con"     <c:if test="${item_status.index==0}"> style="display:block"</c:if>>
                	<h3 class="cptit">
                	<span class="FL">${entry[1]}<em class="ui-small-icon ui-si-folder" onclick="itemKnowledge.relationKnowledge('${entry[0]}')"></em></span>
                	</h3>
                	 <div class="clear"></div>
                	 <c:if test="${entry[4]!=''}">
                	<ul id="cp_parameter" class="cp_parameter" >
                		<c:forEach var="props" items="${props_name}" varStatus="pname_status">   <!--  循环属性 -->
                			<c:set var="prop_name" value="${fn:split(props,'=')[0]}"></c:set>
                			<li>
                			<span name="${entry[0]}_sku_prop" >${prop_name}：</span> <!--  显示属性名 -->
                			<c:set var="str_values" value="${fn:split(props,'=')[1]}"/>
                			<c:set var="arr_values" value="${fn:split(str_values,'[-]')}"/>
							<c:forEach var="val" items="${arr_values}" >   <!--  循环属性值 -->
							<a  lang="${prop_name}_${val}"   onclick="querySku('${entry[0]}','${fn:length(props_name)}','${item_status.index}','${pname_status.index}',this)" href="javascript:" 
								 >${val}</a><!--显示属性值 -->
							</c:forEach>   	
								 
                		</li>
                		</c:forEach>
                		</ul>
                		</c:if>
                		
                		  <div class="clear"></div>
               	 </div>
              </c:forEach>
               
	              <ul class="cp_parameter">
	            	  <li><span>价格：</span><strong  id="consultation_sku_price"></strong><label>| </label>
			         	     库存：<b id="consultation_sku_quantity"></b> 
	               	  </li>
	               </ul>
	              <span class="FL">促销:</span> <div id="ump_promotion"></div>
	              
              
            </div>
	</c:otherwise>
</c:choose>
        </div>
      <input id="ww_msg_plugin" type="hidden" value="" />
      <div class="knowledge" id="knowledge" style="display:none">
        <div class="knowledge_search">商品知识搜索：<input id="keywords" name="" type="text" /></div>
        <div class="knowledge_con" id="knowledge_con">
            <div id="loading" class="tips_loading" style="display: block">查询中...</div>
        </div>
     </div>
   </div>              
<script>
$(document).ready(function () {
		$("ul#cp_parameter li a").live('click',(function(){
			$(this).addClass('xuanzhong').siblings('a').removeClass('xuanzhong');
			$('#consultation_sku_price').html("<img  height='18' width='18' src='"+staticResourcePath+"/resource/newcss/images/ajax-loader.gif'/>");
			$('#consultation_sku_quantity').html("<img height='18' width='18'  src='"+staticResourcePath+"/resource/newcss/images/ajax-loader.gif'/>");
		}));
		 $("#keywords").bind({
	        	change:function(events){
	        		itemKnowledge.search();
	        	}
	     });
		//document.getElementById("item_0").click();
		//$(".navlist ul.list li").eq(0).find("a").click();
	    //setTimeout(function(){alert(1)},5000);
	});

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
