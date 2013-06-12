/**
 * 通知管理JS
 */
var noticemgt = {
		init:function(){
			$('#dialog').wijdialog({
		         autoOpen: false,
		         captionButtons: {
		             refresh: { visible: false }
		         }
		     });
			 $("#tree").wijtree({
	            	showCheckBoxes: true,
	            	nodeExpanding: function (events, data) {
//	                    node = data.node,
//	                    o = node.options;
//	                    if (o.hasChildren && node.element.find("li:wijmo-wijtreenode").length == 0) {
//	                    	noticemgt.loadGroupTrees(node,o.params);
//	                    }                   
	                },
	                nodeCheckChanged:function(events, data){
	                	var o = data.options;
	                	if(o.checked){
	                		$("#subectid").val(o.params);
	                		$("#subectname").val(o.text);
	                	}else{
	                		$("#subectid").val("");
	                		$("#subectname").val("");
	                	}
	               }
	          });
			  noticemgt.loadGroupTrees(null,0);
	          $("#tree").wijtree({ nodeClick: function (e, data) {
	                clickNode = data;
	          }});
	          $('#addNode').click(function () {
	        	  noticemgt.addNode();
	           });

	          $("#savenotice").click(function(){
	        	  noticemgt.checkForm();
	          });
	          $("#resetnotice").click(function(){
	        	  noticemgt.cleanForm();
	          });
		},
		addNode:function(){
			var parentid = "-1";
    		if(clickNode!=null){
    			parentid = clickNode.options.params;
    		}
    		var subject = $("#addIndex").val();
    		if(subject==''||subject.length<=0){
    			alert('主题不能为空  ');
    			return false;
    		}
    		$.ajax({
        		type:'post',
                dataType: "json",
                url: "http://localhost:8080/pcrm/knowledgeservice/addsubject",
                data:"parentid="+parentid+"&subject="+subject,
                error: function(){
                    alert(arguments[1]);
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
                	}else{
                		alert('添加主题失败!');
                	}
                	
                	clickNode = null;

                }
            });
		},
		loadGroupTrees:function(node,parentid){
			var url = "notice/memberlist";
			$("#loading").css("display","");
			$("#treeshow").css("display","none");
			
        	$.ajax({
        		type:'post',
                dataType: "json",
                url: url,
                data:"seller="+seller,
                error: function(){
           	    }, 
                success: function (json){
                	$("#loading").css("display","none");
                	if(json!=null&&json.length>0){
	                	 for (var i = 0; i < json.length; i++) {
	                 		var row = json[i];
	                 		var addNode = {}; 
	//                 		var nleaf = false;
	//                 		if(row.leaf=="true"){
	//                 			nleaf = true;
	//                 		}
	                		addNode.text = row.name;
	                		addNode.params=row.id;
	                		addNode.url = "#";
	                		addNode.hasChildren = false;
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
	                	$("#treeshow").css("display","");
	                	
                	
                  }else{
                	  $("#treeshow").html("没有找到用户");
                	  $("#treeshow").css("display","");
                  }
                	
                }
            });
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
		checkForm:function(){
			var title = $("#textbox").val();
			if(title ==''||title == undefined){
				noticemgt.showMessge('标题不能为空');
				return false;
			}
			if(title.length>10){
				noticemgt.showMessge('标题不能超过10个字');
				return false;
			}
			var content = $("#area").val();
			if(content ==''||content==' '||content == undefined){
				noticemgt.showMessge('内容不能为空');
				return false;
			}
			if(content.length>100){
				noticemgt.showMessge('内容不能超过100个字');
				return false;
			}
			ids = noticemgt.getSelectNodes();
			if(ids==''||ids.length<=0){
				noticemgt.showMessge('请选择通知的用户');
				return false;
			}
			$("#creatorid").val(creator);
			$("#userids").val(ids);
			$("#MyForm").submit();
		},
		showMessge:function(msg){
			$("#smsg").html(msg);
			$('#dialog').wijdialog('open');
		},
		cleanForm:function(){
			 $("#textbox").val("");
			 $("#area").val("");
			 noticemgt.cleanCheckedNode();
		},
		cleanCheckedNode:function(){
	    	var nodes = $("#tree").wijtree("getNodes");
	    	for (var i = 0; i < nodes.length; i++) { 
	    		noticemgt.allUnCheckBox(nodes[i]);
			}
	    	
		},
		allUnCheckBox:function(node){
			var co = node.options;
			if(co.hasChildren){
				var cn = node.element.wijtreenode("getNodes");
				for(var m=0;m<cn.length;m++){
					noticemgt.allUnCheckBox(cn[m]);
				}
				node.element.wijtreenode({checked: false});
			}else{
				node.element.wijtreenode({checked: false});
			}
				
		}
		
};





