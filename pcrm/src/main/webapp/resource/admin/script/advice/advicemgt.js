
/**
 * 建议管理类js
 */
var advicemgt = {
		pagesize:10,
		pagecount:0,
		editAdviceTypeId:null,
		deleteAdviceTypeId:null,
		curPanel:null,
		init:function(){
			var adviceTypeId = $("#cus-leftNav div.cus-leftNav-list").find("ul").children("li.curr").children("a").attr("id");
			if($.browser.msie&&($.browser.version == "6.0")&&!$.support.style){
				$(".backend_right").width(document.body.clientWidth-205);
				$(window).resize(function(){ 
				 var w = document.body.clientWidth;
					 if(w-205<900){
						$(".backend_right").width(900);
					 } 
					  
				});
			};
			$("#cus-leftNav .cus-leftNav-list h3.navTit").live('click',function(){
				$(this).toggleClass('choose').siblings('ul').toggle();
	   		});
			//打分
			//全选
			$("#allChecked").click(function(){
				var checked = $("#allChecked").attr("checked");
				if(checked == "checked"){
					$("#dataContiner").find("input:checkbox").attr('checked','');
				}else{
					$("#dataContiner").find("input:checkbox").removeAttr('checked');
				}
				
			});
			
			//添加建议类型
			$("#addAdviceType").click(function(event){
				advicemgt.addRolePanel();
				event.stopPropagation();
			});
			$("#deleteadvice").click(function(){
				var ids = advicemgt.getSelectRows();
				if(ids.length<=0){
					advicemgt.showMessge('选择一条或多条建议');
					return ;
				}
				advicemgt.deleteById(ids);
			});
			$("#translate").click(function(){
				var targetType = $("#advicetypelist").val();
				if(targetType=='-1'){
					advicemgt.showMessge('选择一个类型');
					return ;
				}
				var ids = advicemgt.getSelectRows();
				if(ids.length<=0){
					advicemgt.showMessge('选择一条或多条建议');
					return ;
				}
				advicemgt.translateByIds(targetType,ids);
			});
			$("#publicLeftNav h2.menu_fl").live("click",function(){
				 $(this).next("ul.menu_zl").slideToggle();
			});
			$("#allstatusadvice").click(function(){
				$("#allstatusadvice").addClass("suggestscreen_choose");
				$("#noread").removeClass('suggestscreen_choose');
				$("#aleardread").removeClass('suggestscreen_choose');
				var cadviceType = $(".curr").find('a').attr("id");
				if(cadviceType==undefined){
					cadviceType = "all";
				}else{
					cadviceType = cadviceType.split("_")[0];
				}
				if(cadviceType==undefined){
					cadviceType = "all";
				}
				var params = "seller="+seller+"&page=first&cpage=0&pagesize="+advicemgt.pagesize+"&orientedtype=1&type="+cadviceType+"&status=all&userid="+userid;
				advicemgt.loadAdviceList(params);
			});
			
			$("#noread").click(function(){
				$("#allstatusadvice").removeClass("suggestscreen_choose")
				$("#noread").addClass('suggestscreen_choose');
				$("#aleardread").removeClass('suggestscreen_choose');
				
				var cadviceType = $(".curr").find('a').attr("id");
				if(cadviceType==undefined){
					cadviceType = "all";
				}else{
					cadviceType = cadviceType.split("_")[0];
				}
				if(cadviceType==undefined){
					cadviceType = "all";
				}
				var params = "seller="+seller+"&page=first&cpage=0&pagesize="+advicemgt.pagesize+"&orientedtype=1&type="+cadviceType+"&status=0&userid="+userid;
				advicemgt.loadAdviceList(params);
			});
			$("#aleardread").click(function(){
				$("#allstatusadvice").removeClass("suggestscreen_choose")
				$("#noread").removeClass('suggestscreen_choose');
				$("#aleardread").addClass('suggestscreen_choose');
				var cadviceType = $(".curr").find('a').attr("id");
				if(cadviceType==undefined){
					cadviceType = "all";
				}else{
					cadviceType = cadviceType.split("_")[0];
				}
				if(cadviceType==undefined){
					cadviceType = "all";
				}
				var params = "seller="+seller+"&page=first&cpage=0&pagesize="+advicemgt.pagesize+"&orientedtype=1&type="+cadviceType+"&status=1&userid="+userid;
				advicemgt.loadAdviceList(params);
			});
			
			var params = "seller="+seller+"&orientedtype=1&type=all&status=all&cpage=0&page=first&pagesize="+advicemgt.pagesize;
			advicemgt.loadAdviceTypeList("");
			advicemgt.loadAdviceList(params);
			
		},
		loadAdviceList:function(params){
			var url = "adviceservice/admin/advicepage";
			$("#advicelisttable tr.head").siblings('tr').remove();
			var before ='<div style=" width:200px; margin:150px auto;"><img style="vertical-align:middle" src="'+staticResourcePath+'/resource/images/beforeSuccess.gif"/><span>正在加载中。。</span></div>';
			$("#loadingdata").html(before);
			$("#pagebar").html("");
			$.ajax({
				type:'post',
		        dataType: "json",
		        url: url,
		        data:params,
		        error: function(){
		   	    }, 
		        success: function (json){
		        	if(json!=null){
		        		var rows = json.row;
		        		var pagebar = advicemgt.getpagehtml(json.page,json.pagecount);
		        		advicemgt.pagecount = json.pagecount;
		        		var noticelist = "";
		        		var th = "<tr class='head' >"+
			                    	"<td width='1%'></td>"+
			                    	"<td width='10%'>ID</td>"+
			                        "<td width='39%'>建议标题</td>"+
			                        "<td width='20%'>提交时间</td>"+
			                        "<td width='20%'>状态</td>"+
			                        "<td width='10%'>操作</td>"+
			                    "</tr>";
		        		var trs = "";
		        		var curpage = parseInt(json.page);
	            		var rownum = curpage*10+1;
	            		if(rows.length >0){
			                for (var i = 0; i < rows.length; i++) {
			                	 var n = rows[i];
			                	 var status = "";
			                	 var trclass = "list-head";
			                	 if(n.status==0){
			                		 trclass = "list-head unread";
			                	 }
			                	 var sid = rownum+"aid";
			                	 trs +="<tr class='"+trclass+"'>"+
			                    	"<td><input name='"+rownum+"' type='checkbox' value='' /><input type='hidden' value='"+n.id+"' id='"+sid+"'/></td>"+
			                    	"<td>"+rownum+"</td>"+
			                        "<td>"+n.title+"</td>"+
			                        "<td>"+n.datetime+"</td>";
			                        var acestatus = "<td>(未读)</b></td>";
			                        if(n.status == 0){
			                        	acestatus = "<td>(未读)</b></td>";
			                        }else if(n.status == 1){
			                        	acestatus ="<td>已读<b>(未采纳)</b></td>"
			                        }else if(n.status == 2){
			                        	acestatus ="<td>已采纳<b>(未评分)</b></td>"
			                        }else if(n.status == 3){
			                        	acestatus ="<td>已采纳<b>(已评分)</b></td>"
			                        }
			                        trs +=acestatus;
			                        var advicestatus = n.id+"_"+n.status;
			                        var optername = n.id+"_chakan";
			                        trs += "<td class='dataContinerOperate'><a href='javascript:void(0)' " +
			                        		"class='detailLink' id='"+advicestatus+"' name='"+optername+"' onclick='advicemgt.readAdviceById(\""+n.id+"\",\""+rownum+"\",\""+n.status+"\")'>查看</a>";
			                        				
			                        if(n.status<2){
			                        	trs += "<a href='javascript:void(0)' onclick='advicemgt.deleteById(\""+n.id+"\")'>删除</a></td>";
			                        }
			                        				
			                        trs += "</tr>";
			                	    trs += "<tr class='suggestDetail' style='display:none'>"+
					                    	"<td colspan='2' style='display: table-cell;'>"+
					                        	"<img class='suggestDetail_img' src='"+staticResourcePath+"/resource/images/test.jpg'/>"+
					                        "</td>"+
					                        "<td colspan='4' style='display: table-cell;'>"+
					                           "<div class='suggestDetail_r'>"+
					                            	"<div class='suggestDetail_nr'>"+n.content+"</div>"+
					                                "<ul class='suggestDetail_message'>"+
					                                	"<li>提交人："+n.creator+"</li>"+
					                                    "<li>售前：前台客服</li>"; 
			                	    					
			                	    					var isscore = false;
			                	    					var isaccept = false;
					                                    if(n.status >= 2){
								                        	trs+= "<li>已采纳</li>";
								                        	isaccept = true;
								                        }else{
								                        	trs +="<li><input name='' type='checkbox' value='' onclick='advicemgt.showrating(this,\""+rownum+"\")'/>采纳</li>";
								                        }
					                                    
					                                    var ratingid = "showscore_"+rownum;
					                                    var configratingid = "configrating_"+rownum;
					                                    var ratedid = "rated_"+rownum;
					                                    var ratingSelectid = "ratingSelect_"+rownum;
					                                    if(n.status == 3){
					                                    	isscore = true;
					                                    	trs += "<li>得分:"+n.score+"</li>";
					                                    }else{
					                                    	var ratingshow = "display:none";
					                                    	 if(isaccept&&!isscore){
					                                    		 trs +="<li><input name='' type='checkbox' value='' onclick='advicemgt.showrating(this,\""+rownum+"\")'/>评分 </li>";
					                                    	 }
					                                    	 
					                                    	 trs +="<li id='"+ratingid+"' style='"+ratingshow+"'><span class='jypf'>建议评分：</span>"+
						                                    	"<select id='"+ratingSelectid+"'>"+
						                                            "<option value='1'></option>"+
						                                            "<option value='2'></option>"+
						                                            "<option value='3'></option>"+
						                                            "<option value='4'></option>"+
						                                            "<option value='5'></option>"+
						                                    	"</select>"+
						                                        "<label id='"+ratedid+"'></label>"+
						                                    "</li>"+
						                                    " <li><a href='javascript:void(0)' id='"+configratingid+"' style='"+ratingshow+"' onclick='advicemgt.acceptAdviceById(\""+n.id+"\",\""+rownum+"\",\""+n.status+"\")'>确定</a></li>";
					                                    }
					                                trs +="</ul>"+
					                            "</div>"+
					                        "</td>"+
					                   "</tr>";
			                	 rownum++;
							                       
			                }
			                $("#advicelisttable").html(th+trs);
			                $("#loadingdata").html('');
			                inittable_tr();
			                $("#pagebar").html(pagebar);
			                $("#pagesize").val(json.pagesize);
			                advicemgt.initListenerEvent();
			                for(var m=1;m<=rownum;m++){
			                	 $("#ratingSelect_"+m).wijrating({
										split: 2,
										rated: function (e, args) {
											var rsid = $(this).attr("id");
											var index = rsid.split("_")[1];
											$("#rated_"+index).html(args.value);
										},
										reset: function(e) {
											var rsid = $(this).attr("id");
											var index = rsid.split("_")[1];
											$("#rated_"+index).html("");
										} 
					                });
			                }
			               
	            		}else{
	            			$("#loadingdata").html('');
	            			$("#pagebar").html("");
	            			var norecord = "<tr><td colspan='6' align='center'>当前暂无记录</td></tr>";
	 		                $("#advicelisttable").html(th+norecord);
	            		}
		               
		                
		        	}
		        }
		    }).then(function(){
		    	$("#dataContiner table tr.list-head a.detailLink").toggle(function(){
		    		 var oname =  $(this).attr("name");
		    		 var aid = oname.split("_")[0];
	   				 $(this).addClass('dataContinerOperate_choose').text('收回');
	   				 $(this).attr("name",aid+"_shouhui");
	   				 var td = $(this).parent();
	   				 td.parent("tr").removeClass("unread").addClass("read").next("tr").show();
	   				 
	   			},function(){
	   			    var oname =  $(this).attr("name");
	    		    var aid = oname.split("_")[0];
	   				 $(this).removeClass('dataContinerOperate_choose').text('查看');
	   				 $(this).attr("name",aid+"_chakan");
	   				 var td = $(this).parent();
	   				 td.parent("tr").removeClass("read").next("tr").hide();
	   			});
		    });
		},
		loadAdviceTypeList:function(selecttypeid,oldAdviceTypeId){
			var url = "adviceservice/admin/advicetypelist";
			$.ajax({
				type:'post',
		        dataType: "json",
		        url: url,
		        data:"seller="+seller+"&orientedtype="+orientedtype,
		        error: function(){
		   	    }, 
		        success: function (json){
		        	if(json!=null){
		        		if(json.length>0){
		        			var navadvicetypelist = "";
		        			var selectadvicetypelist = "<option value='-1'>选择分类</option>";
		        			var rownum = 1;
		        			 var imgArr = [];
		        			for (var i = 0; i < json.length; i++) {
			                	var row = json[i];
			                	var adviceId = row.id+"_"+rownum+"img";
			 	         		var imgId = rownum+"img";
			 	         		selectadvicetypelist +="<option value='"+row.id+"'>"+row.name+"</option>";
			 	         		var classCss = "";
			 	         		var md = "<img id='"+imgId+"_m' src='"+staticResourcePath+"/resource/images/Write.png' class='menu_edit' width='16' height='16' alt='修改' style='display: none'/><img id='"+imgId+"_d' src='"+staticResourcePath+"/resource/images/delete.png' width='16' height='16' class='menu_delete' style='display: none'/>";
			                	var classCss = "";
			                	if(selecttypeid==row.id){
			                		classCss = "class='curr'";
			                	}
			                	navadvicetypelist +="<li "+classCss+"><a href='javascript:void(0)' id='"+adviceId+"' onclick='advicemgt.findAdviceByType(this)'>"+row.name+"("+row.totalcount+")</a>"+md+"</li>";
			                	rownum ++;
			                    imgArr.push(imgId);
		        			}
		        			$("#advicetypelist").html(selectadvicetypelist);
		        			$("#advicetypenav").html(navadvicetypelist);
		        			for(var i=0;i<imgArr.length;i++){
			        			var imgDx = imgArr[i];
			        			$("#"+imgDx+"_m").click(function(){
			        				var adviceTypeid = $(this).parent('li').children('a').attr('id');
			        				advicemgt.editkPanel(adviceTypeid);
			        			});
			        			$("#"+imgDx+"_d").click(function(){
			        				var adviceTypeid = $(this).parent('li').children('a').attr('id');
			        				advicemgt.deletekPanel(adviceTypeid.split("_")[0]);
			        			});
		        		    }
		        			
		        			if(oldAdviceTypeId){
		        				var menuObj = $("#"+oldAdviceTypeId);
		        				var imgid = oldAdviceTypeId.split("_")[1];
		        				$("#"+imgid+"_m").css("display","");
		        				$("#"+imgid+"_d").css("display","");
		        				$(menuObj).parent('li').siblings('li').children('img').css('display','none');
		        			}
		        		}
		                
		        	}
		        }
		    });
		},
		getSelectRows:function(){
			var ids = "";
			$("table :checkbox").each(function (index, domEle) { 
					 var checked =  $(domEle).attr("checked");  
					 if(checked == "checked"){
						 var cname =  $(domEle).attr("name");
						 if(ids.length>0){
							 ids +=",";
						 }
						 ids += $("#"+cname+"aid").val();
					 }
			});
			return ids;

		},
		findAdviceByType:function(dom){
			
			var advicetypeid = $(dom).attr("id");
			var imgid = advicetypeid.split("_")[1];
			advicetypeid = advicetypeid.split("_")[0];
			$("#"+imgid+"_m").css("display","");
			$("#"+imgid+"_d").css("display","");
			$(dom).parent('li').siblings('li').children('img').css('display','none');
			var advicename = $(dom).text();
			var index = advicename.indexOf("(");
			advicename = advicename.substring(0,index);
			$(dom).parent('li').addClass("curr").siblings('li').removeClass('curr');
			var cadviceStatus = $(".suggestscreen_choose").attr("id");
			var cstatus = "all";
			if(cadviceStatus=="noread"){
				cstatus = "0";
			}else if(cadviceStatus=="aleardread"){
				cstatus = "1";
			}
			$("#curadvicetype").html(advicename);
			var params = "seller="+seller+"&userid="+userid+"&orientedtype=1&type="+advicetypeid+"&status="+cstatus+"&cpage=0&page=first&pagesize="+advicemgt.pagesize;
			advicemgt.loadAdviceTypeList(advicetypeid,advicetypeid+"_"+imgid);
			advicemgt.loadAdviceList(params);
		},
		addAdviceType:function(name){
			name = name.replaceAll(" ","");
			if(name ==''||name==' '||name == undefined){
				advicemgt.showMessge('名称不能为空');
				return false;
			}
			if(name.length>8){
				advicemgt.showMessge('名称长度不能超过8');
				return false;
			}
			var url = "adviceservice/admin/advicetypeadd";
			$.ajax({
				type:'post',
		        dataType: "json",
		        url: url,
		        data:"seller="+seller+"&creator="+creator+"&name="+name,
		        error: function(){
		   	    }, 
		        success: function (json){
		        	if(json!=null){
		        		if(json.result == true || json.result =="true"){
		        			advicemgt.loadAdviceTypeList("");
		        			$.closeMyPannel(curPanel);
		        			advicemgt.showMessge("新建成功");
		        			return true;
		        		}else if(json.result =="-1"){
		        			advicemgt.showMessge("该名称已经存在");
		        			return false;
		        		}else{
		        			advicemgt.showMessge("新建失败");
		        			return false;
		        		}
		        	}
		        }
		    });
			
		},
		deleteById:function(adviceid){
			var url = "adviceservice/admin/deleteadvices";
			$.tooltip({"type":"confirm","content":"是否删除","callback":function(result){
				   if(result){
					   $.ajax({
							type:'post',
					        dataType: "json",
					        url: url,
					        data:'ids='+adviceid,
					        error: function(){
					   	    }, 
					        success: function (json){
					        	if(json!=null){
					        		if(json.result == true || json.result =="true"){
					        			advicemgt.reloadData(0,'first');
					        			advicemgt.showMessge("删除成功");
					        		}else if(json.result == "-1"){
					        			advicemgt.showMessge("已经采纳过的意见不能删除!");
					        		}else{
					        			advicemgt.showMessge("系统错误，请重试!");
					        		}
					        	}
					        }
					    });
				   }
			   	}
			});
			
		},
		translateByIds:function(type,adviceid){
			var url = "adviceservice/admin/updateadvices";
			$.tooltip({"type":"confirm","content":"是否确认转移","callback":function(result){
				   if(result){
					   $.ajax({
							type:'post',
					        dataType: "json",
					        url: url,
					        data:'ids='+adviceid+"&targettype="+type,
					        error: function(){
					   	    }, 
					        success: function (json){
					        	if(json!=null){
					        		if(json.result == true || json.result =="true"){
					        			advicemgt.reloadData(0,'first');
					        			advicemgt.showMessge("转移成功");
					        		}else{
					        			advicemgt.showMessge("转移失败");
					        		}
					        	}
					        }
					    });
				   }
			}
		 });
			
		},
		updateStatusById:function(adviceid,status,score){
			var url = "adviceservice/admin/updatestatus";
			$.ajax({
				type:'post',
		        dataType: "json",
		        url: url,
		        data:'id='+adviceid+"&targetstatus="+status+"&score="+score,
		        error: function(){
		   	    }, 
		        success: function (json){
		        	if(json!=null){
		        		if(json.result == true || json.result =="true"){
		        			advicemgt.reloadData(0,'first');
		        			if(status=="2"){
		        				advicemgt.showMessge("采纳成功");
		        			}else if(status=="3"){
		        				advicemgt.showMessge("评分成功");
		        			}
		        		}else{
		        			//advicemgt.showMessge("删除失败");
		        		}
		        	}
		        }
		    });
		},
		acceptAdviceById:function(adviceid,rownum,status){
			var rateing = $("#rated_"+rownum).html();
			var destStatus = status;
			destStatus = 2;
//			if(status==1){
//				destStatus = parseInt(status)+1;
//			}
			destStatus = parseInt(destStatus);
			if(rateing!=""&&rateing.length>0){
				destStatus = 3;
			}else{
				if(status == "2"){
					advicemgt.showMessge("您未评分!");
					return false;
				}
				rateing = "0";
			}
			advicemgt.updateStatusById(adviceid,destStatus,rateing);
		},
		readAdviceById:function(adviceid,rownum,status){
			var readButtonId = adviceid+"_"+status;
			var ctext = $("#"+readButtonId).attr("name");
			var css = ctext.split("_")[1];
			if(css == "shouhui"){
				if(status=="0"){
	   					advicemgt.updateStatusById(adviceid,1,"0");
	   			}
			}
		},
		showrating:function(obj,rownum){
			
			var checked = $(obj).attr("checked");
			if(checked == "checked"){
				$("#showscore_"+rownum).css("display","");
				$("#configrating_"+rownum).css("display","");
			}else{
				$("#showscore_"+rownum).css("display","none");
				$("#configrating_"+rownum).css("display","none");
			}
		},
		showLimitDiscountPage:function(cur_page,page){
			var data = 'seller='+seller+'&userid='+userid+'&cpage='+cur_page+'&page='+page+"&pagesize="+advicemgt.pagesize;
			var cadviceType = $(".curr").find('a').attr("id");
			if(cadviceType==undefined){
				cadviceType = "all";
			}else{
				cadviceType = cadviceType.split("_")[0];
			}
			if(cadviceType==undefined){
				cadviceType = "all";
			}
			var cadviceStatus = $(".suggestscreen_choose").attr("id");
			var cstatus = "all";
			if(cadviceStatus=="noread"){
				cstatus = "0";
			}else if(cadviceStatus=="aleardread"){
				cstatus = "1";
			}
			var params = data+"&orientedtype=1&type="+cadviceType+"&status="+cstatus;
			advicemgt.loadAdviceList(params);
		},
		reloadData:function(cpage,page){
			var cadviceType = $(".curr").attr("id");
			if(cadviceType==undefined){
				cadviceType = "all";
			}
			var cadviceStatus = $(".suggestscreen_choose").attr("id");
			var cstatus = "all";
			if(cadviceStatus=="noread"){
				cstatus = "0";
			}else if(cadviceStatus=="aleardread"){
				cstatus = "1";
			}
			var params = "seller="+seller+"&page="+page+"&cpage="+cpage+"&pagesize="+advicemgt.pagesize+"&orientedtype=1&type="+cadviceType+"&status="+cstatus+"&userid="+userid;
			advicemgt.loadAdviceList(params);
		},
		//编辑建议类型
		editAdviceType:function(adviceTypeId,name){
			name = name.replaceAll(" ","");
			if(name==''||name.length<=0||name==undefined){
				advicemgt.showMessge('主题不能为空  ');
				return false;
			}
			if(name.length>8){
				advicemgt.showMessge('主题长度不能超过8');
				return false;
			}
			$.ajax({
	    		type:'post',
	            dataType: "json",
	            url: contextPath+"adviceservice/admin/updateadvicetype",
	            data:"seller="+seller+"&advicetypeid="+adviceTypeId+"&name="+name,
	            error: function(){
	       	    }, 
	            success: function (json){
	            	if(json.result == true || json.result =="true"){
	            		advicemgt.loadAdviceTypeList("");
	            		advicemgt.showMessge('修改成功!');
	            		$.closeMyPannel(curPanel);
	            		return true;
	            	}else if(json.result =="-1"){
	            		advicemgt.showMessge("该类型已经存在");
	        			return false;
	        		}else{
	            		advicemgt.showMessge('修改主题失败!');
	            		return false;
	            	}

	            }
	        });
		},
		deleteAdviceTypeById:function(adviceTypeId){
			var url = contextPath+"adviceservice/admin/deleteadvicetype";
			$.ajax({
				type:'post',
		        dataType: "json",
		        url: url,
		        data:'id='+adviceTypeId,
		        error: function(){
		   	    }, 
		        success: function (json){
		        	if(json!=null){
		        		if(json.result == true || json.result =="true"){
		        			advicemgt.loadAdviceTypeList("");
		        			advicemgt.showMessge("删除成功");
		        			$.closeMyPannel(curPanel);
		        		}else{
		        			advicemgt.showMessge("系统错误，请重试!");
		        		}
		        	}
		        }
		    });
		},
		getGoPage:function(){
			var cpage = $("#topage").val();
			if(cpage==''||cpage == " "){
				advicemgt.showMessge('请输入页码');
				return false;
			}
			if(parseInt(cpage)>parseInt(advicemgt.pagecount)||parseInt(cpage)<=0){
				advicemgt.showMessge('页码不对');
				$("#topage").val("");
				return false;
			}
			var page = 'next';
			if(cpage==1){
				page = 'first';
			}else if(0<parseInt(cpage)&&parseInt(cpage)<parseInt(advicemgt.pagecount)){
				page = 'next';
			}else{
				page = 'last';
			}
			advicemgt.reloadData(parseInt(cpage)-2,page);
		},
		initListenerEvent:function(){
			$("#pagesize").bind({
				change:function(){
					advicemgt.pagesize = $("#pagesize").val();
					advicemgt.reloadData(0,'first');
				}
				
			});
			$("#gopage").bind({
				click:function(){
					advicemgt.getGoPage();
				}
				
			});
			$("#topage").bind({
				keyup:function(){
					this.value = this.value.replace(/\D/g,'');
				}
				
			});
		},
		getpagehtml:function(page,pagecount){
			
			var pagebar = "";
			//var pagebar = "<li class='ui-pager-step'>第"+(parseInt(page)+1)+"页</li><li class='ui-pager-count'>共"+pagecount+"页</li>";
			if(pagecount>0){
				pagebar = '<li class="ui-pager2-step" id="pagesizepart">每页<select id="pagesize"><option value="10">10</option><option value="20">20</option><option value="30">30</option><option value="50">50</option></select>条</li>';
				pagebar += "<li class='ui-pager2-step'>第"+(parseInt(page)+1)+"页</li><li class='ui-pager2-count'>共"+pagecount+"页</li>";
				if(page==0&&pagecount>1){
					pagebar +="<li class='ui-pager2-firstPage-disable'>首页</li><li class='ui-pager2-prePage-disable'>上一页</li><li class='ui-pager2-nextPage' onclick=\"advicemgt.showLimitDiscountPage("+page+",'next')\">下一页</li><li class='ui-pager2-lastPage' onclick=\"javascript:advicemgt.showLimitDiscountPage("+page+",'last')\">尾页</li>";
				}else if(page==0&&pagecount==1){
					pagebar +="<li class='ui-pager2-firstPage-disable'>首页</li><li class='ui-pager2-prePage-disable'>上一页</li><li class='ui-pager2-nextPage-disable'>下一页</li><li class='ui-pager2-lastPage-disable'>尾页</li>";
				}else if(0<page&&page<parseInt(pagecount)-1){
					pagebar +="<li class='ui-pager2-firstPage' onclick=\"javascript:advicemgt.showLimitDiscountPage("+page+",'first')\">首页</li><li class='ui-pager2-prePage' onclick=\"javascript:advicemgt.showLimitDiscountPage("+page+",'previous')\">上一页</li><li class='ui-pager2-nextPage' onclick=\"javascript:advicemgt.showLimitDiscountPage("+page+",'next')\">下一页</li><li class='ui-pager2-lastPage' onclick=\"javascript:advicemgt.showLimitDiscountPage("+page+",'last')\">尾页</li>";
				}else{
					pagebar +="<li class='ui-pager2-firstPage' onclick=\"javascript:advicemgt.showLimitDiscountPage("+page+",'first')\">首页</li><li class='ui-pager2-prePage' onclick=\"javascript:advicemgt.showLimitDiscountPage("+page+",'previous')\">上一页</li><li class='ui-pager2-nextPage-disable'>下一页</li><li class='ui-pager2-lastPage-disable'>尾页</li>";
				}
				
				pagebar += '<li class="ui-pager2-go"><input id="topage" class="pagenum"/><input type="button" class="go" id="gopage"/></li>';
				pagebar+=  "<div class='clear'></div>";
			}
			return pagebar;
		},
		showMessge:function(msg){
			showMessage(msg);
//			$("#smsg").html(msg);
//			$('#dialog').wijdialog('open');
		},
		addRolePanel:function(){
			var newRole_panel = $.openMyPannel();
			curPanel = newRole_panel;
			newRole_panel.load("resource/admin/panel.html #addsuggest_panel",function(){
				 $("#closeBtnTRBtn").click(function(){$.closeMyPannel(newRole_panel);});
				 $("#saveadvicetype").click(function(){
					 	var name = $("#advicetypename").val();
					 	if(advicemgt.addAdviceType(name)){
					 		$.closeMyPannel(newRole_panel);
					 	}
				 	});
			});
			$.pannelBgClose(newRole_panel);
		},
		editkPanel:function(adviceTypeId){
			editAdviceTypeId = adviceTypeId.split("_")[0];
			var newRole_panel = $.openMyPannel();
			curPanel = newRole_panel;
			newRole_panel.load(contextPath+"resource/admin/knowledge_panel.html #editadvicetype_panel",function(){
				 var atype = $("#"+adviceTypeId).text();
				 var index = atype.indexOf("(");
				 var typeName = atype.substring(0,index);
				 $("#editadvicetypename").val(typeName);
				 $("#closeBtnTRBtn").click(function(){$.closeMyPannel(newRole_panel);});
				 $("#editadvicetype").click(function(){
					 	var name = $("#editadvicetypename").val();
					 	if(advicemgt.editAdviceType(editAdviceTypeId,name)){
					 		$.closeMyPannel(newRole_panel);
					 	}
				 });
			});
			$.pannelBgClose(newRole_panel);
		},
		deletekPanel:function(subjectid){
			deleteAdviceTypeId = subjectid;
			var newRole_panel = $.openMyPannel();
			curPanel = newRole_panel;
			newRole_panel.load(contextPath+"resource/admin/knowledge_panel.html #deleteadvicetype_panel",function(){
				 $("#closeBtnTRBtn").click(function(){$.closeMyPannel(newRole_panel);});
				 $("#deleteadvicetype").click(function(){
					 	if(advicemgt.deleteAdviceTypeById(deleteAdviceTypeId)){
					 		$.closeMyPannel(newRole_panel);
					 	}
				 });
				 $("#canceldeleteadvicetype").click(function(){
					 		$.closeMyPannel(newRole_panel);
				 });
			});
			$.pannelBgClose(newRole_panel);
		}
		

		
}