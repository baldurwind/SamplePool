/**
 * 知识列表JS
 */
var knowledgelist = {
		searchType:'subject',
		curSubjectId:null,
		lastTab:'know',
		genShowType:'knowTypeList',
		mySearch:'',
		knowSearch:'',
		addDefinedKnowShow:false,
		init:function(){
			
			if(itemid=="0"){
				//$("#knowledge_gen").css("display","");
				$("#iteminfo").css("display","none");
				$("#knowledge_con").css("display","none");
			}
			$("#knowledgediv").tabChange();
			$("#createknowledge").click(function(){
				knowledgelist.checkForm();
			});
			$("#resetknowledge").click(function(){
				knowledgelist.cleanForm();
			});
			$("#returnknowledge").click(function(){
				$("#addKnowledgediv").css("display","none");
				$("#searchknowledgediv").css("display","block");
//				if(knowledgelist.addDefinedKnowShow){
//					$("#adddefinedknowledge").css("display","block");
//				}
				$("#keywords").val('');
				$("#knowledge_gen").css("display","none");
				$("#knowledge_con").css("display","block");
				$("#pagediv").show();
				knowledgelist.loadDefinedKnowledge(0,'first');
			});
			
			$("#adddefinedknowledge").click(function(){
				$("#knowledge_con").css("display","none");
				$("#searchknowledgediv").css("display","none");
				$("#pagediv").hide();
				$("#addKnowledgediv").show();
			});
			$("ul#knowledgetab li").click(function(){
				var showid = $(this).attr("id");
				var showdiv = "item_pager";
        		var hidebar ="gen_pager";
        		$("#searchknowledgediv").css("display","");
        		
        		var keywords = $("#keywords").val();
        		
        		if(knowledgelist.lastTab == 'know'){
        			knowledgelist.knowSearch = keywords;
        		}else{
        			knowledgelist.mySearch = keywords;
        		}
//        		var classValue = $("#mydefinedknow").attr("class");
//        		if(classValue=='ui-tab-ul-li-open'){
//        			
//        		}else{
//        			
//        		}
        		
        		$("#pagediv").show();
				if(showid=='generalknow'){
					
					$("#keywords").val(knowledgelist.knowSearch);
					$("#addKnowledgediv").css("display","none");
					$("#knowledge_con").css("display","none");
					$("#knowledge_gen").css("display","none");
					if(knowledgelist.genShowType=='knowTypeList'){
						knowledgelist.curSubjectId = null;
						knowledgelist.searchType = 'subject';
						$("#knowTypeList").show();
						if($("#subjectlist").find("li").length==0){
							knowledgelist.loadSubjectList();
						}
					}else{
						knowledgelist.searchType = 'know';
						$("#knowledge_gen").css("display","");
					}
					knowledgelist.lastTab = 'know';
				}else if(showid=="itemknow"){
					$("#knowledge_con").css("display","none");
					$("#knowledge_gen").css("display","none");
					$("#addKnowledgediv").css("display","none");
					$("#iteminfo").css("display","");
					knowledgelist.loadItemKnowledge(0);
				}else{
					$("#keywords").val(knowledgelist.mySearch);
					//knowledgelist.curSubjectId = null;
					knowledgelist.searchType = 'know';
					$("#knowledge_con").show();
					$("#addKnowledgediv").css("display","none");
//					if(knowledgelist.addDefinedKnowShow){
//						$("#adddefinedknowledge").css("display","block");
//					}
					//$("#pagediv").show();
					$("#knowledge_gen").css("display","none");
					$("#knowTypeList").css("display","none");
					if($("#knowledge_con").find("h3").length==0){
						knowledgelist.loadDefinedKnowledge(0,'first');
					}else{
						$("#knowledge_con").show();
					}
					knowledgelist.lastTab = 'my';
				}
			});
			//手风琴效果
			knowledgelist.accordingInit();
			//绑定搜索
	        $("#gosearch").bind({
	        	click:function(events){
	        		knowledgelist.search();
	        	}
	        });
	        knowledgelist.loadSubjectList(0,'first');
		},
		loadItemKnowledge:function(page){
			var url = contextPath+"knowledge/itemknowledge/"+itemid;
	    	var params = "seller="+seller+"&cpage=0&page="+page;
	    	knowledgelist.loadData(url,params,"post","knowledge_con");
		},
		loadGeneranlKnowledge:function(subjectid,cpage,page){
			$("#knowledge_con").css("display","none");
			$("#addKnowledgediv").css("display","none");
			//$("#adddefinedknowledge").css("display","none");
			var url = contextPath+"knowledge/admin/subknowledge/"+subjectid;
			var params = "seller="+seller+"&cpage="+cpage+"&page="+page+"&pagesize=10";
	    	knowledgelist.loadData(url,params,"get","knowledge_gen","knowdetailslistid");
			
		},
		loadDefinedKnowledge:function(cpage,page){
			var url = contextPath+"knowledge/finddefinedknowledge";
			var params = "creatorid="+userid+"&cpage="+cpage+"&page="+page;
	    	knowledgelist.loadData(url,params,"post","knowledge_con","myknowlist");
		},
		loadData:function(url,params,method,divid,contentdiv){
			$("#"+contentdiv).html('<div id="loading" class="tips_loading" style="display:">查询中...</div>');
			$("#pagediv").hide();
			$.ajax({
	    		type:method,
	            dataType: "json",
	            data:params,
	            url: url,
	            error: function(){
	       	    }, 
	            success: function (json){
	            	//$("#loading").css("display","none");
	            	var showid = "knowledge_gen";
	        		var classValue = $("#mydefinedknow").attr("class");
	        		if(classValue=='ui-tab-ul-li-open'){
	        			showid = "knowledge_con";
	        		}
	        		//$("#"+divid).css("display","");
	            	//$("#"+divid).css("display","");
	            	//$("#pager").css("display","");
//	            	var pagebar = "itempagebarid";
//	            	var showdiv = "item_pager";
//            		var hidebar ="gen_pager";
//            		if(divid=="knowledge_gen"){
//            			pagebar ="genpagebarid";
//            			hidebar = "item_pager";
//            			showdiv = "gen_pager";
//            		}
            		
	            	if(json!=null){
	            		if(contentdiv=='subjectlist'){
	            			 var genSub = json.row;
	    		        	 var rownum = 1;
	    		        	 if(genSub.length>0){
	    		        		 var pagebar = knowledgelist.getpagehtml(json.page,json.pagecount);
	    		        		 var gensublist = "";
	    		        		 var imgArr = [];
	    		        		 for (var i = 0; i < genSub.length; i++) {
	    		 	         		var row = genSub[i];
	    		 	         		gensublist +="<li><a href='javascript:void(0)' onclick='knowledgelist.loadKnowledgeBySubjectId(\""+row.id+"\",\""+row.name+"\")'>"+row.name+"</a><span class='FB cRed'>（"+row.totalcount+"）</span></li>";
	    		        		 }
	    		        		 $("#"+contentdiv).html(gensublist);
	    		        		 $("#pagebar").html(pagebar);
	    		        	 }else{
	    		        		 $("#pagebar").html('');
	    		        		 $("#"+contentdiv).html("<div class='tips_wujilu'>当前暂无记录</div>");
	    		        	 }
	    		        	 
	            		}else{
	            			var rows = json.row;
	            			var pagebar = knowledgelist.getpagehtml(json.page,json.pagecount);
			            	var noticelist = "";
			            	if(rows!=null&&rows.length>0){
			            		
			            		var rindex = 0;
			            		var contId = rindex;
			            		for (var i = 0; i<rows.length; i++) {
			            			 contId = rindex;
			            			 if(contentdiv=='myknowlist'){
			            				 contId = contId +"_def";
				            		 }
			                    	 var n = rows[i];
			                    	 var delKnow = '';
			                    	 if(contentdiv =='myknowlist'){
			                    		 delKnow = "<div class='deleteKnowledge' id='deleteDefinedKnowledge' onclick='knowledgelist.deleteDefinedKnowledge(event,\""+n.id+"\")'></div>";
			                    	 }
			                    	 noticelist +="<div class='ui-accordion'><h3 class='ui-accordion-header'><span></span><a id='"+rindex+"_title'>"+n.title+"</a>"+delKnow+"</h3>"+
			    				   			 	   "<div class='ui-accordion-content knowledge_condiv'><div id='"+contId+"_cont'>"+n.content+
			                    	 				"</div><div class='fabu_btn' onclick='knowledgelist.sendKnowToWangWang(\""+contId+"\",\""+n.id+"\")'></div>";
			    				   	noticelist +="</div></div>";
			    				   	rindex ++;               
			    				   	
			                    } 
			            		//$("#"+showdiv).css("display","");
				            	//$("#"+hidebar).css("display","none");
			            		$("#pagebar").html(pagebar);
		            			$("#"+contentdiv).html(noticelist);
		            			ieMinHeightBug($(".knowledge_condiv"),60);
		            			knowledgelist.accordingInit();
			                    
			            	}else{
			            		//$("#"+showdiv).css("display","none");
				            	//$("#"+hidebar).css("display","none");
			            		$("#pagebar").html('');
			            		$("#"+contentdiv).html("<div class='tips_wujilu'>当前暂无记录</div>");
			            	}
	            		}
	            		$("#pagediv").show();
		            	
	            	}
	            }
	        });
		},
		deleteDefinedKnowledge:function(event,definedKnowledgeId){
			
			$.tooltip({"type":"confirm","content":"是否删除?","callback":function(result){
				   if(result){
					    showDisplay();
						var url = contextPath+"knowledge/deleteDefinedKnowledge";
						$.ajax({
							type:'post',
					        dataType: "json",
					        url: url,
					        data:"id="+definedKnowledgeId,
					        error: function(){
					        	
					   	    }, 
					        success: function (json){
					        	hideDisplay();
					        	if(json!=null){
					        		if(json.result == true || json.result =="true"){
					        			knowledgelist.search();
					        			knowledgelist.showMessge("删除成功");
					        		}else{
					        			knowledgelist.showMessge("系统错误，请重试!");
					        		}
					          }
					        }
					    });
				   }
				}
			});
			knowledgelist.stopEvent();
		},
		stopEvent:function(){ 
			 //阻止冒泡事件
			 //取消事件冒泡 
			 var e=arguments.callee.caller.arguments[0]||event; //若省略此句，下面的e改为event，IE运行可以，但是其他浏览器就不兼容
			 if (e && e.stopPropagation) { 
			  // this code is for Mozilla and Opera
			  e.stopPropagation(); 
			 } else if (window.event) { 
			  // this code is for IE 
			  window.event.cancelBubble = true; 
			 } 
		},
		checkButtonResource:function(resourceUrl){
			$.ajax({
				type:'get',
		        dataType: "json",
		        url: contextPath+'findResourceByUserAndPath',
		        data:'userid='+getNickId()+'&resourceUrl='+resourceUrl,
		        success: function (json){
		        	if(json!=null){
		        		//var resourceArr = json.resource;
		        		if(json.length>0){
		        			for(var i=0;i<json.length;i++){
		        				var resUrl = json[i].resourceUrl;
		        				var idx = resUrl.lastIndexOf("/");
		        				if(idx<resUrl.length){
		        					var buttonId = resUrl.substring(idx+1,resUrl.length);
		        					if(buttonId =='adddefinedknowledge'){
		        						$("#adddefinedknowledge").show();
		        						//knowledgelist.addDefinedKnowShow = true;
		        					}
		        				}
		        				
		        			}
		        		}
		        	}
		        }
		    });
		},
		getpagehtml:function(page,pagecount){
			var pagebar = "<li class='ui-pager-step'>第"+(parseInt(page)+1)+"页</li><li class='ui-pager-count'>共"+pagecount+"页</li>";
			if(pagecount>0){
				if(page==0&&pagecount>1){
					pagebar +="<li class='ui-pager-firstPage-disable'></li><li class='ui-pager-prePage-disable'></li><li class='ui-pager-nextPage' onclick=\"knowledgelist.showLimitDiscountPage("+page+",'next')\"></li><li class='ui-pager-lastPage' onclick=\"javascript:knowledgelist.showLimitDiscountPage("+page+",'last')\"></li>";
				}else if(page==0&&pagecount==1){
					pagebar +="<li class='ui-pager-firstPage-disable'></li><li class='ui-pager-prePage-disable'></li><li class='ui-pager-nextPage-disable'></li><li class='ui-pager-lastPage-disable'></li>";
				}else if(0<page&&page<parseInt(pagecount)-1){
					pagebar +="<li class='ui-pager-firstPage' onclick=\"javascript:knowledgelist.showLimitDiscountPage("+page+",'first')\"></li><li class='ui-pager-prePage' onclick=\"javascript:knowledgelist.showLimitDiscountPage("+page+",'previous')\"></li><li class='ui-pager-nextPage' onclick=\"javascript:knowledgelist.showLimitDiscountPage("+page+",'last')\"></li><li class='ui-pager-lastPage' onclick=\"javascript:knowledgelist.showLimitDiscountPage("+page+",'last')\"></li>";
				}else{
					pagebar +="<li class='ui-pager-firstPage' onclick=\"javascript:knowledgelist.showLimitDiscountPage("+page+",'first')\"></li><li class='ui-pager-prePage' onclick=\"javascript:knowledgelist.showLimitDiscountPage("+page+",'previous')\"></li><li class='ui-pager-nextPage-disable'></li><li class='ui-pager-lastPage-disable'></li>";
				}
			}
			//pagebar +="<div class='clear'></div>";
			return pagebar;
		},
		showLimitDiscountPage:function(cpage,page){
			var contentid = "subjectlist";
    		var classValue = $("#mydefinedknow").attr("class");
    		if(classValue=='ui-tab-ul-li-open'){
    			contentid = "myknowlist";
    		}
    		//var creator
    		var sitemid = -1;
    		if(contentid == "subjectlist"){
    			sitemid = 0;
			}
    		var keyword = $("#keywords").val();
    		if(keyword!=''&&keyword.length>0){
        		var url = contextPath+"knowledge/search";
        		var params = "type="+knowledgelist.searchType+"&seller="+seller+"&cpage="+cpage+"&page="+page+"&itemid="+sitemid+"&keywords="+encodeURIComponent(keyword);
        		if(contentid == "myknowlist"){
        			params +="&creator="+userid;
    			}
        		if(knowledgelist.curSubjectId&&contentid != "myknowlist"){
        			params +="&subjectid="+knowledgelist.curSubjectId;
        		}
        		if(contentid == 'subjectlist' && knowledgelist.searchType == 'know'){
        			contentid = 'knowdetailslistid';
        		}
        		knowledgelist.loadData(url,params,"get",null,contentid);
    		}else{
    			if(contentid == 'subjectlist' && knowledgelist.searchType == 'subject'){
    				knowledgelist.loadSubjectList(cpage,page);
        		}else if(contentid == 'subjectlist' && knowledgelist.searchType == 'know'){
        			knowledgelist.loadGeneranlKnowledge(knowledgelist.curSubjectId,cpage,page);
        		}else{
        			knowledgelist.loadDefinedKnowledge(cpage,page);
        		}
        		
    		}
			
		},
		loadSubjectList:function(cpage,page){
			$("#subjectlist").html('<div id="loading" class="tips_loading" style="display:">查询中...</div>');
			$("#pagediv").hide();
			var url = contextPath+"knowledge/subjectlist";
			$.ajax({
				type:'get',
		        dataType: "json",
		        url: url,
		        data:"seller="+seller+"&stye=1"+"&cpage="+cpage+"&page="+page,
		        error: function(){
		   	    }, 
		        success: function (json){
		        	if(json!=null){
		        	 var genSub = json.row;
		        	 var rownum = 1;
		        	 if(genSub.length>0){
		        		 var pagebar = knowledgelist.getpagehtml(json.page,json.pagecount);
		        		 var gensublist = "";
		        		 var imgArr = [];
		        		 for (var i = 0; i < genSub.length; i++) {
		 	         		var row = genSub[i];
		 	         		gensublist +="<li><a href='javascript:void(0)' onclick='knowledgelist.loadKnowledgeBySubjectId(\""+row.id+"\",\""+row.name+"\")'>"+row.name+"</a><span class='FB cRed'>（"+row.totalcount+"）</span></li>";
		        		 }
		        		 $("#subjectlist").html(gensublist);
		        		 $("#pagebar").html(pagebar);
		        		 
		        	 }else{
		        		 $("#pagebar").html('');
		        		 $("#subjectlist").html("<div class='tips_wujilu'>当前暂无记录</div>");
		        	 }
		        	 $("#pagediv").show();
		          }
		        }
		    });
		},
		loadKnowledgeBySubjectId:function(subjectid,subjectName){
			$("#knowTypeList").hide();
			$("#knowledge_gen").show();
			$("#curSujectName").html(subjectName);
			knowledgelist.searchType = 'know';
			knowledgelist.curSubjectId = subjectid;
			knowledgelist.genShowType = 'knowledge_gen';
			$("#pagediv").show();
			knowledgelist.search();
			//knowledgelist.loadGeneranlKnowledge(subjectid,0);
		},
		returnSubjectList:function(){
			$("#knowledge_gen").hide();
			$("#knowTypeList").show();
			knowledgelist.curSubjectId = null;
			knowledgelist.searchType = 'subject';
			knowledgelist.genShowType = 'knowTypeList';
			$("#pagediv").show();
			var keyword = $("#keywords").val();
			if(keyword ==''||keyword||keyword == " "){
				knowledgelist.search();
			}
			
		},
		//表单提交
		checkForm:function(){
			var title = $("#textbox").val();
			var cotitlentent = $("#area").val();
			var subname = $("#addknowledgesubjectlist").val();
			var selectItemId = $("#itemid").val();
			var submit = true;
//			if(subname=="-1"||subname==' '||subname == undefined){
//				knowledgelist.showMessge('请选择一个这主题!');
//				submit = false;
//				return false;
//			}
			
			if(title==''||title==' '||title == undefined){
				knowledgelist.showMessge('标题不能为空');
				submit = false;
				return false;
			}
			if(title.length>32){
				knowledgelist.showMessge('标题长度不能超过32!');
				return false;
			}
			
			if(cotitlentent==''||cotitlentent==' '||cotitlentent == undefined){
				knowledgelist.showMessge('内容不能为空');
				submit = false;
				return false;
			}
			if(cotitlentent.length>512){
				knowledgelist.showMessge('内容长度不能超过512!');
				return false;
			}
//			var subText = $("#addknowledgesubjectlist").find("option:selected").text();
//			if(subText.indexOf("通用")==-1){
//				if(selectItemId=="0"){
//					knowledgelist.showMessge('请选择关联的商品');
//					submit = false;
//					return false;
//				}
//			}
			showDisplay();
			$("#creatorid").val(userid);
			$("#sellerid").val($("#seller").val());
			$("#MyForm").attr('action',contextPath+"knowledge/admin/addknowledge");
			$("#MyForm").submit();
		},
		cleanForm:function(){
			 $("#textbox").val('');
			 $("#area").val('');
		},
		search:function(){
			var contentid = "subjectlist";
    		var classValue = $("#mydefinedknow").attr("class");
    		if(classValue=='ui-tab-ul-li-open'){
    			contentid = "myknowlist";
    		}
    		//var creator
    		var sitemid = -1;
    		if(contentid == "subjectlist"){
    			sitemid = 0;
			}
    		var keyword = $("#keywords").val();
    		if(keyword!=''&&keyword.length>0){
        		var url = contextPath+"knowledge/search";
        		var params = "type="+knowledgelist.searchType+"&seller="+seller+"&cpage=0&page=first&itemid="+sitemid+"&keywords="+encodeURIComponent(keyword);
        		if(contentid == "myknowlist"){
        			params +="&creator="+userid;
    			}
        		if(knowledgelist.curSubjectId&&contentid != "myknowlist"){
        			params +="&subjectid="+knowledgelist.curSubjectId;
        		}
        		if(contentid == 'subjectlist' && knowledgelist.searchType == 'know'){
        			contentid = 'knowdetailslistid';
        		}
        		knowledgelist.loadData(url,params,"get",null,contentid);
    		}else{
    			if(contentid == 'subjectlist' && knowledgelist.searchType == 'subject'){
    				$("#knowTypeList").show();
    				knowledgelist.loadSubjectList(0,'first');
        		}else if(contentid == 'subjectlist' && knowledgelist.searchType == 'know'){
        			knowledgelist.loadGeneranlKnowledge(knowledgelist.curSubjectId,0,'first');
        		}else{
        			knowledgelist.loadDefinedKnowledge(0,'first');
        		}
        		
    		}
		},
		accordingInit:function(){
			$(".ui-accordion").accordion();
			$("#first-ui-accordion").accordion({"extend":true});//页面第一个展开
			$(".ui-accordion-content").hover(function(){
				                      		$(this).addClass("publicHoverBgColor").find(".fabu_btn").show();
			                           },function(){
								      		$(this).removeClass("publicHoverBgColor").find(".fabu_btn").hide();;
									 });
		},
		updateKnowledgeSendNums:function(knowledgeid){
			var type = '2';
			var classValue = $("#mydefinedknow").attr("class");
    		if(classValue=='ui-tab-ul-li-open'){
    			type = '1';
    		}
			var url = contextPath+"knowledge/updateknowledgesendnums";
			$.ajax({
				type:'post',
		        dataType: "json",
		        url: url,
		        data:"id="+knowledgeid+"&type="+type,
		        error: function(){}, 
		        success: function (json){
		        	if(json!=null){
		            }
		        }
		    });
		},
		sendKnowToWangWang:function(index,knowledgeid){
			 var kc = $("#"+index+"_cont").html();
			 kc = kc.replaceAll("<BR>"," ");
			 ww_msg_send(kc);
			 knowledgelist.updateKnowledgeSendNums(knowledgeid);
		},
		knowledgeChange:function(){
			
		},
		downloadFile:function(filename,endpart){
			var fileName = filename+"_"+endpart;
			window.location.href="knowledge/downloadfile?fileName="+fileName;
		},
		showMessge:function(msg){
			showMessage(msg);
		},
		cleanPage:function(){
			$("#keywords").val("");
		}
		
}