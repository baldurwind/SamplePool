/**
 * 知识管理JS
 */
var knowledge = {
	init:function(){
		 $("#itemid").val("0");
		 $("#checkupload").attr("checked",false);
		 $('#dialog').wijdialog({
	         autoOpen: false,
	         captionButtons: {
	             refresh: { visible: false }
	         }
	     });

		$("#knowledge_add").wijwizard();
		$(":input[type='text'],textarea").wijtextbox();
		$("table#knowledge_prolist tr td a").live('click',function(){
			var itemid = $(this).attr("id");
			$("#itemid").val(itemid);
			$(this).addClass("sp_desbtn2").parent().parent().siblings('tr').find('a').removeClass('sp_desbtn2');
		}); 
		var progressbar = $("#progressbar");
	    $("#tree").wijtree({
	    	showCheckBoxes: true,
	    	nodeExpanding: function (events, data) {
	            node = data.node,
	            o = node.options;
	            if (o.hasChildren && node.element.find("li:wijmo-wijtreenode").length == 0) {
	            	knowledge.loadSubjectTrees(node,o.params.id);
	            }                   
	        },
	        nodeCheckChanged:function(events, data){
	        	var o = data.options;
	        	if(o.checked){
	        		$("#subjectid").val(o.params);
	        		$("#subectname").val(o.text);
	        		knowledge.cleanCheckedNode(data);
	        	}else{
	        		$("#subjectid").val("");
	        		$("#subectname").val("");
	        	}
	       },
	       nodeClick:function(e, data){
	    	   clickNode = data;
	       }

	    });
		
	    $('#addNode').click(function () {
	    	knowledge.addSubjectNode();
	    });
	    //绑定搜索
        $("#itemsearch").bind({
        	keyup:function(events){
        		var keyword = $("#itemsearch").val();
        		var params = 'userid='+userid+'&cpage=0&page=first';
        		if(keyword!=''&&keyword.length>0){
        			params += "&title="+keyword+"&type=aa&p=5";
        			knowledge.searchItem(params);
        		}else{
        			knowledge.loadItem(0,'first');
        		}
        			
        	}
        });
	    $('#relatedyes').click(function (){
			$("isrelated").val("1");
	    });
	    $('#relatedno').click(function (){
	    	$("isrelated").val("0");
	    });
	    
	},
	//添加主题
	addSubjectNode:function(){
		var parentid = "-1";
		if(clickNode!=null){
			parentid = clickNode.options.params;
		}
		var subject = $("#subjectname").val();
		if(subject==''||subject.length<=0||subject==undefined){
			knowledge.showMessge('主题不能为空  ');
			return false;
		}
		$.ajax({
    		type:'post',
            dataType: "json",
            url: "knowledgeservice/addsubject",
            data:"parentid="+parentid+"&subject="+subject,
            error: function(){
            	knowledge.showMessge('连接出错!');
       	    }, 
            success: function (json){
            	if(json.result){
            		var addNode = {};
            		addNode.text = subject;
            		addNode.params=json.id;
            		addNode.url = "#";
            		
            		if(clickNode!=null){
            			clickNode.element.wijtreenode("add", addNode);
            			clickNode.options.hasChildren = true;
            		}else{
            			$("#tree").wijtree("add",addNode);
            		}
            		knowledge.showMessge('添加主题成功!');
            	}else{
            		knowledge.showMessge('添加主题失败!');
            	}
            	if(clickNode!=null){
	            	clickNode.element.wijtreenode({selected: false});
	            	clickNode = null;
            	}

            }
        });
	},
	//加载主题树
	loadSubjectTrees:function(node,parentid){
		var url = "knowledgeservice/subjectlist";
		$.ajax({
			type:'get',
	        dataType: "json",
	        url: url,
	        data:"parentid="+parentid,
	        error: function(){
	        	
	   	    }, 
	        success: function (json){
	        	$("#loading").css("display","none");
	        	if(json!=null&&json.length>0){
	        	 for (var i = 0; i < json.length; i++) {
	         		var row = json[i];
	         		var addNode = {}; 
	         		var nleaf = false;
	         		if(row.leaf=="true"){
	         			nleaf = true;
	         		}
	         		
	        		addNode.text = row.name;
	        		addNode.params={'id':row.id,'parentid':parentid};
	        		addNode.url = "#";
	        		addNode.hasChildren = nleaf;
	         		if(node!=null){
	         			 node.add(addNode);
	         		}else{
	         			 $("#tree").wijtree("add", addNode);
	         		}
		               
	            }
	        	if(node!=null&&node.options.checked==true){
	        		var cn = node.element.wijtreenode("getNodes");
					for(var m=0;m<cn.length;m++){
						var childNode = cn[m];
						childNode.element.wijtreenode({checked: true});
					}
	        	}
	        	
	          }
	        }
	    });
	},
	//加载商品
	loadItem:function(cur_page,page){
		var url = "knowledgeservice/itemlist";
		$.ajax({
			type:'post',
	        dataType: "json",
	        url: url,
	        data:'seller='+seller+'&userid='+userid+'&cpage='+cur_page+'&page='+page,
	        error: function(){
	            alert(arguments[1]);
	   	    }, 
	        success: function (json){
	        	if(json!=null){
	            	var rows = json.row;
	        		var pagebar = knowledge.getpagehtml(json.page,json.pagecount);
	            	var itemtrs = "<thead>"+
						              "<tr>"+
						                "<th>序号</th><th>商品描述</th><th>操作</th>"+
						              "</tr>"+
						          "</thead>";
	            	if(rows!=null&&rows.length>0){
	            		var curpage = parseInt(json.page);
	            		var rownum = curpage*10+1;
	            		for (var i = 1; i<=rows.length; i++){
	                    	 var n = rows[i-1];
	                    	 itemtrs += "<tr>"+
				                         "<td>"+rownum+"</td>"+
				                           "<td class='sp_des'><img src='"+n.image+"' />"+
				                         "<p>"+n.name+"</p></td>"+
				                         "<td><a href='javascript:void(0)' class='sp_desbtn' id='"+n.id+"'>关联此商品</a></td>"+
				                       "</tr>";
	                    	 rownum ++;
	                    }
	            	}
	            	$("#knowledge_prolist").html(itemtrs);
	            	$("#pagebar").html(pagebar);
	        	}
	        }
	    });
	},
	//搜索商品
	searchItem:function(params){
		
		var url = "knowledgeservice/itemsearch";
		$.ajax({
			type:'post',
	        dataType: "json",
	        url: url,
	        data:params,
	        error: function(){
	            alert(arguments[1]);
	   	    }, 
	        success: function (json){
	        	if(json!=null){
	            	var rows = json.row;
	        		var pagebar = knowledge.getpagehtml(json.page,json.pagecount);
	            	var itemtrs = "<thead>"+
						              "<tr>"+
						                "<th>序号</th><th>商品描述</th><th>操作</th>"+
						              "</tr>"+
						          "</thead>";
	            	if(rows!=null&&rows.length>0){
	            		var curpage = parseInt(json.page);
	            		var rownum = curpage*5+1;
	            		for (var i = 1; i<=rows.length; i++){
	                    	 var n = rows[i-1];
	                    	 itemtrs += "<tr>"+
				                         "<td>"+rownum+"</td>"+
				                           "<td class='sp_des'><img src='"+n.image+"' />"+
				                         "<p>"+n.name+"</p></td>"+
				                         "<td><a href='javascript:void(0)' class='sp_desbtn' id='"+n.id+"'>关联此商品</a></td>"+
				                       "</tr>";
	                    	 rownum ++;
	                    }
	            		$("#knowledge_prolist").html(itemtrs);
		            	$("#pagebar").html(pagebar);
	            		
	            	}else{
	            		itemtrs +="<tr align='center'><td colspan='3'>没有搜索到相关的商品</td><tr>";
	            		$("#knowledge_prolist").html(itemtrs);
		            	$("#pagebar").html("");
	            	}
	            	
	        	}
	        }
	    });
	},
	//分页
	showLimitDiscountPage:function(cpage,page){
		var keyword = $("#itemsearch").val();
		var params = 'userid='+userid+'&cpage='+cpage+'&page='+page;
		if(keyword!=''&&keyword.length>1){
			params += "&title="+keyword+"&type=aa&p=5";
			knowledge.searchItem(params);
		}else{
			knowledge.loadItem(cpage,page);
		}
		
	},
	//表单提交
	checkForm:function(){
		var title = $("#textbox").val();
		var cotitlentent = $("#area").val();
		var subname = $("#subectname").val();
		var submit = true;
		if(subname==''||subname==' '||subname == undefined){
			knowledge.showMessge('请选择一个这主题!');
			submit = false;
			return false;
		}
		
		if(title==''||title==' '||title == undefined){
			knowledge.showMessge('标题不能为空');
			submit = false;
			return false;
		}
		if(title.length>10){
			knowledge.showMessge('标题长度不能超过10!');
			return false;
		}
		
		if(cotitlentent==''||cotitlentent==' '||cotitlentent == undefined){
			knowledge.showMessge('内容不能为空');
			submit = false;
			return false;
		}
		if(cotitlentent.length>64){
			knowledge.showMessge('内容长度不能超过64!');
			return false;
		}
		
		$("#creatorid").val(creator);
		var checkup = $("#checkup").val();
		if(checkup=="uploadfileName"){
			var filename = $("#uploadfile").val();
			if(filename==''||filename==" "||filename==undefined){
				knowledge.showMessge('请选择要上传的附件!');
				return false;
			}
		}
		$("#MyForm").submit();
		
	},
	//根据text获取节点
	getNode:function(text){
		return $("#tree").wijtree("findNodeByText",text);
	},
	ckuploadFS:function(){
		   var ck = $("#checkupload").attr("checked");
		   if(ck=="checked"){
			   $("#uploadtab").css("display","");
			   $("#checkup").val("uploadfileName");
		   }else{
			   $("#uploadtab").css("display","none"); 
			   $("#checkup").val("");
		   }
	},
	getSelectNodes:function(){
		var ids = "";
    	var nodes = $("#tree").wijtree("getNodes");
    	for (var i = 0; i < nodes.length; i++) { 
    		var node = nodes[i];
    		var co = nodes[i].options;
			if(co.hasChildren){
				var cn = node.element.wijtreenode("getNodes");
				for(var m=0;m<cn.length;m++){
					if(cn[m].options.checked == true ){
						if(ids.length>0){
							ids +=",";
						}
						ids += cn[m].options.params;
					}
				}
			}else{
				if(co.checked){
					if(ids.length>0){
						ids +=",";
					}
					ids += co.params;
				}
			}
		}
    	return ids;
	},
	showMessge:function(msg){
		$("#smsg").html(msg);
		$('#dialog').wijdialog('open');
	},
	getpagehtml:function(page,pagecount){
		var pagebar = "";
		if(pagecount>0){
			if(page==0&&pagecount>1){
				pagebar +="<li title='首页'><a>首页</a></li><li><a>上一页</a></li><li title='首页'><a href=\"javascript:knowledge.showLimitDiscountPage("+page+",'next')\">下一页</a></li><li><a href=\"javascript:knowledge.showLimitDiscountPage("+page+",'last')\">尾页</a></li>";
			}else if(page==0&&pagecount==1){
				pagebar +="<li><a>首页</a></li><li><a>上一页</a></li><li><a>下一页</a></li><li><a>尾页</a></li>";
			}else if(0<page&&page<parseInt(pagecount)-1){
				pagebar +="<li><a href=\"javascript:knowledge.showLimitDiscountPage("+page+",'first')\">首页</a></li><li><a href=\"javascript:knowledge.showLimitDiscountPage("+page+",'previous')\">上一页</a></li><li><a href=\"javascript:knowledge.showLimitDiscountPage("+page+",'next')\">下一页</a></li> <li><a href=\"javascript:knowledge.showLimitDiscountPage("+page+",'last')\">尾页</a></li>";
			}else{
				pagebar +="<li><a href=\"javascript:knowledge.showLimitDiscountPage("+page+",'first')\">首页</a></li><li><a href=\"javascript:knowledge.showLimitDiscountPage("+page+",'previous')\">上一页</a></li><li><a>下一页</a></li><li><a>尾页</a></li>";
			}
		}
		return pagebar;
	},
	cleanForm:function(){
		$("#textbox").val("");
		$("#area").val("");
		$("#subectname").val("");
		$("#subectid").val("");
		$("#itemid").val("");
		$("#checkupload").attr("checked",false);
		$("#uploadfile").val("");
	},
	cleanCheckedNode:function(cnode){
    	var nodes = $("#tree").wijtree("getNodes");
    	for (var i = 0; i < nodes.length; i++) { 
    		knowledge.onlyCheckBox(nodes[i],cnode);
		}
    	for (var i = 0; i < nodes.length; i++) { 
    		knowledge.checkMyNode(nodes[i],cnode);
		}
    	
	},
	onlyCheckBox:function(node,cnode){
		var co = node.options;
		var p = co.params;
		if(co.params.id!=cnode.options.params.id){
			if(co.hasChildren){
				var cn = node.element.wijtreenode("getNodes");
				for(var m=0;m<cn.length;m++){
					knowledge.onlyCheckBox(cn[m],cnode);
				}
				if(co.params.id!=cnode.options.params.parentid){
					node.element.wijtreenode({checked: false});
				}
			}else{
				if(co.checked == true && cnode.options.params != co.params ){
					node.element.wijtreenode({checked: false});
				}
			}
			
		}
	},
	checkMyNode:function(node,cnode){
		var sid = cnode.options.params.parentid;
		var co = node.options;
		var p = co.params;
		if(co.params.id==sid){
			node.element.wijtreenode({checked: true});
		}else{
			if(co.hasChildren){
				var cn = node.element.wijtreenode("getNodes");
				for(var m=0;m<cn.length;m++){
					knowledge.checkMyNode(cn[m],cnode);
				}
				if(co.params.id==sid){
					node.element.wijtreenode({checked: true});
				}
			}
		}
	}
	
};

