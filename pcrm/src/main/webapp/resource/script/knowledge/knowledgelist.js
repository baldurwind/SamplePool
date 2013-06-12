/**
 * 知识列表JS
 */
var knowledgelist = {
		init:function(){
			
			$("#knowledge_con").wijaccordion();
			$("#knowledge_gen").wijaccordion();
	    	//商品知识和通用知识切换
			$("ul.knowledge_title li").click(function(){
				$(this).addClass("blue").siblings('li').removeClass('blue');
				var showid = $(this).attr("id");
				var showdiv = "item_pager";
        		var hidebar ="gen_pager";
				if(showid=='generalknow'){
					if($("#knowledge_gen").find("h3").length==0){
						knowledgelist.loadGeneranlKnowledge(0);
					}
					$("#knowledge_con").css("display","none");
					$("#knowledge_gen").css("display","");
					$("#"+showdiv).css("display","none");
	            	$("#"+hidebar).css("display","");
					$("#iteminfo").css("display","none");
				}else{
//					if($("#knowledge_con").find("h3").length==0){
						knowledgelist.loadItemKnowledge(0);
//					}
					$("#"+showdiv).css("display","");
	            	$("#"+hidebar).css("display","none");
					$("#knowledge_con").css("display","");
					$("#knowledge_gen").css("display","none");
					$("#iteminfo").css("display","");
				}
			});//
			//绑定搜索
	        $("#keywords").bind({
	        	keyup:function(events){
	        		knowledgelist.search();
	        	}
	        });
		},
		loadItemKnowledge:function(page){
			var url = "knowledge/itemknowledge/"+itemid;
	    	var params = "seller="+seller+"&cpage=0&page="+page;
	    	knowledgelist.loadData(url,params,"post","knowledge_con");
		},
		loadGeneranlKnowledge:function(page){
			var url = "knowledge/itemknowledge/0";
			var params = "seller="+seller+"&cpage=0&page="+page;
	    	knowledgelist.loadData(url,params,"post","knowledge_gen");
		},
		loadData:function(url,params,method,divid){
			
			$(".tips_wujilu").css("display","none");
			$("#"+divid).css("display","none");
			$("#loading").css("display","");
			$("#pager").css("display","none");
			$.ajax({
	    		type:method,
	            dataType: "json",
	            data:params,
	            url: url,
	            error: function(){
	                alert(arguments[1]);
	       	    }, 
	            success: function (json){
	            	$("#loading").css("display","none");
	            	$("#"+divid).css("display","");
	            	$("#pager").css("display","");
	            	var pagebar = "itempagebarid";
	            	var showdiv = "item_pager";
            		var hidebar ="gen_pager";
            		if(divid=="knowledge_gen"){
            			pagebar ="genpagebarid";
            			hidebar = "item_pager";
            			showdiv = "gen_pager";
            		}
            		
	            	if(json!=null){
		            	var rows = json.row;
		        		var pagehtml = knowledgelist.getpagehtml(json.page,json.pagecount);
		            	var noticelist = "";
		            	if(rows!=null&&rows.length>0){
		            		for (var i = 0; i<rows.length; i++) {
		                    	 var n = rows[i];
		                    	 noticelist +="<h3><a href='#'>"+n.title+"</a></h3>"+
		    				   			 	   "<div class='knowledge_condiv'><div class='fabu_btn'>发送</div>"+n.content;
		    				   			 	   if(n.isfile=="1"){
		    				   			 		noticelist +="<br><br><p>知识相关文档:    <a href=\"javascript:knowledgelist.downloadFile('"+n.firstpart+"','"+n.lastpart+"')\">"+n.lastpart+"</a></p>";
		    				   			 	   }
		    				   			 	noticelist +="</div>";
		    				                   
		                    } 
		            		$("#"+showdiv).css("display","");
			            	$("#"+hidebar).css("display","none");
		            		$("#"+pagebar).html(pagehtml);
		            		$("#"+divid).remove();
		            		$("#maindiv").append("<div class='knowledge_con' id='"+divid+"' style='display:'></div");
	            			$("#"+divid).html(noticelist);
	 	                    $("#"+divid).wijaccordion({
	 	              			// requireOpenedPane: false
	 	     				});
		                    
		            	}else{
		            		$("#"+showdiv).css("display","none");
			            	$("#"+hidebar).css("display","none");
		            		$("#"+divid).html("<div class='tips_wujilu'>当前暂无记录</div>");
		            		$(".tips_wujilu").css("display","");
		            	}
	            	}
	            }
	        });
		},
		getpagehtml:function(page,pagecount){
			var pagebar = "";
			if(pagecount>0){
				if(page==0&&pagecount>1){
					pagebar +="<li title='首页'><a>首页</a></li><li><a>上一页</a></li><li title='首页'><a href=\"javascript:knowledgelist.showLimitDiscountPage("+page+",'next')\">下一页</a></li><li><a href=\"javascript:knowledgelist.showLimitDiscountPage("+page+",'last')\">尾页</a></li>";
				}else if(page==0&&pagecount==1){
					pagebar +="<li><a>首页</a></li><li><a>上一页</a></li><li><a>下一页</a></li><li><a>尾页</a></li>";
				}else if(0<page&&page<parseInt(pagecount)-1){
					pagebar +="<li><a href=\"javascript:knowledgelist.showLimitDiscountPage("+page+",'first')\">首页</a></li><li><a href=\"javascript:knowledgelist.showLimitDiscountPage("+page+",'previous')\">上一页</a></li><li><a href=\"javascript:knowledgelist.showLimitDiscountPage("+page+",'next')\">下一页</a></li> <li><a href=\"javascript:knowledgelist.showLimitDiscountPage("+page+",'last')\">尾页</a></li>";
				}else{
					pagebar +="<li><a href=\"javascript:knowledgelist.showLimitDiscountPage("+page+",'first')\">首页</a></li><li><a href=\"javascript:knowledgelist.showLimitDiscountPage("+page+",'previous')\">上一页</a></li><li><a>下一页</a></li><li><a>尾页</a></li>";
				}
			}
			return pagebar;
		},
		showLimitDiscountPage:function(cpage,page){
			var showid = "knowledge_gen";
    		var classValue = $("#itemknow").attr("class");
    		if(classValue=='blue'){
    			showid = "knowledge_con";
    		}
			if(showid == "knowledge_gen"){
				itemid = 0;
			}
			var keyword = $("#keywords").val();
    		if(keyword!=''&&keyword.length>0){
        		var url = "knowledge/search";
        		var params = "seller="+seller+"&cpage="+cpage+"&page="+page+"&itemid="+itemid+"&keywords="+keyword;
        		knowledgelist.loadData(url,params,"post",showid);
    		}else{
    			var url = "knowledge/itemknowledge/"+itemid;
    	    	var params = "seller="+seller+"&cpage="+cpage+"&page="+page;
    	    	knowledgelist.loadData(url,params,"post",showid);
    		}
			
		},
		search:function(){
			var showid = "knowledge_gen";
    		var classValue = $("#itemknow").attr("class");
    		if(classValue=='blue'){
    			showid = "knowledge_con";
    		}
    		var sitemid = itemid;
    		if(showid == "knowledge_gen"){
    			sitemid = 0;
			}
    		var keyword = $("#keywords").val();
    		if(keyword!=''&&keyword.length>0){
    			
        		var url = "knowledge/search";
        		var params = "seller="+seller+"&cpage=0&page=first&itemid="+sitemid+"&keywords="+keyword;
        		knowledgelist.loadData(url,params,"post",showid);
    		}else{
    			var url = "knowledge/itemknowledge/"+sitemid;
    	    	var params = "seller="+seller+"&cpage=0&page=first";
    	    	knowledgelist.loadData(url,params,"post",showid);
    		}
		},
		knowledgeChange:function(){
			
		},
		downloadFile:function(filename,endpart){
			var fileName = filename+"_"+endpart;
			window.location.href="knowledge/downloadfile?fileName="+fileName;
		},
		cleanPage:function(){
			$("#keywords").val("");
		}
		
}