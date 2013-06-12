
/**
 * 工作台业绩指标JS
 * gavin.peng 
 * 2012-02-29
 */
var performance = {
		lastday:false,
		weekload:false,
		monthload:false,
		init:function(){
			//$("#isonline").val("nologin");
			$("#yejiData").tabChangeEasy();
			$("ul#yejiNav li").live('click',function(){
				var _index = $(this).index();
				$("#yejiData").find('div.yejiData').eq(_index).show().siblings('div.yejiData').hide();
				var showid = $(this).attr("id");
				if(_index==0){
					if(!performance.lastday){
						performance.loadPeriodPerformance(showid,_index);
					}
						
				}else if(_index==1){
					if(!performance.weekload){
						performance.loadPeriodPerformance(showid,_index);
					}else{
						//$("#yejiData").find('div.yejiData').eq(_index).show().siblings('div.yejiData').hide();
					}
				}				
				else{
					if(!performance.monthload){
						performance.loadPeriodPerformance(showid,_index);
					}else{
						//$("#yejiData").find('div.yejiData').eq(_index).show().siblings('div.yejiData').hide();
					}
				}
				
			});
			$("#loginusername").focus(function(){
	 			if($(this).val()=='我的员工号'){
	 				$(this).val('');
	 			}
 	   	    }).focusout(function(){
	 	    	if($(this).val()==''){
	 				$(this).val('我的员工号');
	 			}
 	   	    });
			$('#show_suggest').click(function () {
				$("#suggest").slideToggle();
				//performance.divShowHide(this.lang);
	        });
			$('#show_logout').click(function () {
				
				performance.computeOnlineTime(this.lang);
	        });
			$('#cancellogout').click(function () {
				performance.divShowHide(this.lang);
	        });
			$('#logoutsys').click(function () {
				performance.logout(this.lang);
	        });
			
			 $("#saveadvice").click(function(){
				 performance.checkForm();
	         });
	         $("#resetadvice").click(function(){
	        	 performance.cleanForm();
	         });
	         var userName = getCurUserName();
	         var employeeName = getEmployeeName();
	         var islogin = $("#isonline").val();
			 /*if(employeeName != null){
				 $("#loginusername").hide();
				 $("#username").html("用户:"+userName);
				 $("#show_logout").html("&nbsp;&nbsp;下班：注销打卡");
			 }else{
				 $("#isonline").val("nologin");
				 $("#username").html("您还未打卡");
	        	 $("#show_logout").html("&nbsp;&nbsp;上班: 打卡");
			 }*/
	         var wangwang = $("#nick").val();
	         if(wangwang!=null&&wangwang.indexOf(":")>0){
	        	 wangwang = wangwang.split(":")[1];
	         }
	         $("#wangwangid").html(wangwang);
	         performance.cleanForm();
			
		},
		loadTodayEservicePerformance:function(_index){
			var url = contextPath+"userservice/eservice/wangwang";
			$("#yejiData").find('div.yejiData').hide();
			$("#loading").css("display","");
			$.ajax({
				type:'post',
		        dataType: "json",
		        url: url,
		        data:'userid='+getCurUser(),
		        error:function(){
		   	    }, 
		        success: function (json){
		        	$("#loading").css("display","none");
		        	if(json!=null){
		                $(".today_chatpeernums").html(json.chatpeerNums);
		                $(".today_noreplynums").html(json.noreplyNums);
		        	}
		        	$("#yejiData").find('div.yejiData').eq(_index).show().siblings('div.yejiData').hide();
		        }
		    });
		},
		loadPeriodPerformance:function(period,_index){
			var url = contextPath+"worktable/periodquery";
			$("#yejiData").find('div.yejiData').hide();
			$("#loading").css("display","");
			$.ajax({
				type:'post',
		        dataType: "json",
		        url: url,
		        data:'userid='+getCurUser()+'&period='+period,
		        error:   function(){
		   	    }, 
		        success: function (json){
		        	$("#loading").css("display","none");
		        	if(json!=null){
		        		
		        		if(period=="week"){
		        			performance.weekload = true;
		        		}else if(period =="month"){
		        			performance.monthload = true;
		        		}else if(period =="day"){
		        			performance.lastday = true;
		        		}
		        		var rNum = 0;
		        		var noreplyNum = 0;
		        		var chatpeerNum = 0;
		        		var ast = 0;
		        		var ranking = 0;
		        		var cusresult = 0;
		        		var cusprice = 0;
		        		var pd = json.result;
		        		if(pd==true||pd=="true"){
		        			rNum = json.receiveNum;
		        			noreplyNum = json.noreplyNum;
		        			chatpeerNum = json.chatpeerNum;
			        		ast = json.avgRespTime;
			        		ranking  = json.ranking;
			        		cusresult = json.cusResult;
			        		cusprice = json.cusPrice;
		        		}
		        		
		                $("."+period+"_receivenums").html(rNum);
		                $("."+period+"_noreplynums").html(noreplyNum);
		                $("."+period+"_chatpeernums").html(chatpeerNum);
		                $("."+period+"_avgprice").html(cusprice);
		                $("."+period+"_ranking").html(ranking);
		                $("."+period+"_cusresult").html(cusresult);
		                $("."+period+"_avgresptime").html(ast);
		                $("#yejiData").find('div.yejiData').eq(_index).show().siblings('div.yejiData').hide();
		                
		        	}
		        }
		    });
		},
		loadAllPerformance:function(){
			var url = contextPath+"worktable/allperiod";
			$("#allperioddata").hide();
			$("#allloading").show();
			$.ajax({
				type:'post',
		        dataType: "json",
		        url: url,
		        data:'nick='+nick+"&userid="+getNickId(),
		        error:function(){
		   	    }, 
		        success: function (json){
		        	$("#allperioddata").show();
					$("#allloading").hide();
		        	if(json!=null){
		        		var receMsg = "<b>客户接待数:</b>"+json.receivenum+"位";
		        		if(json.avgnumshow==1){
		        			receMsg += "（高于团队平均值"+json.avgnumdata+"%）";
		        		}
		        		$("#allreceivenums").html(receMsg);
		        		  
		        		var noreplyMsg = "<b>未回复客户数:</b>"+json.noreplynum+"位";
		        		if(json.avgnoreplyshow==1){
		        			noreplyMsg += "（高于团队平均值"+json.avgnoreplydata+"%）";
		        		}
		        		$("#allnoreplynums").html(noreplyMsg);
		        		
		        		var chatpeerMsg = "<b>聊天客户数:</b>"+json.chatpeernum+"位";
		        		if(json.avgchatpeershow==1){
		        			chatpeerMsg += "（高于团队平均值"+json.avgchatpeerdata+"%）";
		        		}
		        		$("#allchatpeernums").html(chatpeerMsg);
		        		
		        		var resMsg = "<b>服务转化率:</b>"+json.customResult+"%";
		        		if(json.avgresultshow==1){
		        			receMsg += "（高于团队平均值"+json.avgresultdata+"%）";
		        		}
		        		$("#allserviceresult").html(resMsg);
		        		var pricMsg = "<b>平均客单价:</b>"+json.customPrice+"";
		        		if(json.avgpriceshow==1){
		        			pricMsg += "（高于团队平均值"+json.avgpricedata+"%）";
		        		}
		        		$("#allprice").html(pricMsg);
		        		var timeMsg = "<b>平均响应时间:</b>"+json.avgRespTime+"秒";
		        		if(json.avgtimeshow==1){
		        			timeMsg += "（高于团队平均值"+json.avgtimedata+"%）";
		        		}
		        		$("#allrestime").html(timeMsg);
		        		if(json.showflower=="1"){
		        			$("#myflower").removeClass("flower_gray");
		        			$("#myflower").addClass("flower");
		        		}else{
		        			$("#myflower").removeClass("flower");
		        			$("#myflower").addClass("flower_gray");
		        		}
		        	}
		        }
		    });
		},
		loadAdviceTypeList:function(){
			var url = contextPath+"adviceservice/advicetypelist";
			$.ajax({
				type:'get',
		        dataType: "json",
		        url: url,
		        data:"seller="+encodeURIComponent(seller)+"&orientedtype=1",
		        error: function(){
		   	    }, 
		        success: function (json){
		        	if(json!=null){
		        		if(json.length>0){
		        			var advicetypelist = "";
		        			for (var i = 0; i < json.length; i++) {
			                	var row = json[i];
			                	advicetypelist +="<option value='"+row.id+"'>"+row.name+"</option>";
			                }
		        			$("#advicetypelist").html(advicetypelist);
		        		}
		                
		        	}
		        }
		    });
		},
		loadTakedAdviceNums:function(){
			var url = contextPath+"adviceservice/gettakedadvicecount";
			$.ajax({
				type:'post',
		        dataType: "json",
		        url: url,
		        data:"creator="+getCurUser(),
		        error: function(){
		        	//performance.showMessge(arguments[1]);
		   	    }, 
		        success: function (json){
		        	var nums = 0;
		        	var myscore = 0;
		        	if(json!=null){
		        		var nums = json.count;
		        		myscore = json.score;
		        	}
		        	
		        	var startCount = parseInt(myscore/20);
		        	var startNums = startCount;
		        	if(startNums<1){
		        		startNums = 1;
		        	}
		        	var totalValue = startCount*20;
		        	performance.ratingShow(startCount,startNums, totalValue,myscore);
		        	$("#takedadvicenums").html(nums);
		        }
		    });
		},
		computeOnlineTime:function(obj){
			var islogin = $("#isonline").val();
			 var employeeName = getEmployeeName();
			if(employeeName != null){
				var url = contextPath+"userservice/getonlinetime";
				performance.showDisplay();
				$.ajax({
					type:'post',
			        dataType: "json",
			        url: url,
			        data:"nick="+nick+"&sysuser="+getEmployeeID(),
			        error: function(){
			        	//performance.showMessge(arguments[1]);
			   	    }, 
			        success: function (json){
			        	performance.hideDisplay();
			        	if(json!=null){
			        		if(json.result == true || json.result =="true"){
			        			$("#onlinehourse").html(json.onlinehourse);
			        			$("#onlineminters").html(json.onlineminiter);
			        			performance.divShowHide(obj);
			        		}else if(json.result =="-1"){
			        			performance.showMessge("参数错误");
			        		}else if(json.result == "norecord"){
			        			performance.showMessge("不存在登录记录,不能交接班");
			        		}
			        	}
			        }
			    });
			}else{
				var url = contextPath+"loginservice/login";
				var usernumid = $("#loginusername").val();
				usernumid = usernumid.replaceAll("我的员工号","");
				usernumid = usernumid.replaceAll(" ","");
				if(usernumid.length<=0){
					performance.showMessge('请收入你的员工号');
					return false;
				}
				performance.showDisplay();
				$.ajax({
					type:'post',
			        dataType: "json",
			        url: url,
			        data:"seller="+seller+"&nick="+nick+"&username="+$("#loginusername").val(),
			        error: function(){
			   	    }, 
			        success: function (json){
			        	 performance.hideDisplay();
			        	if(json!=null){
			        		if(json.result == true || json.result =="true"){
			        			 $("#username").html("用户:"+json.userName);
			    	        	 $("#isonline").val("yeslogin");
			    	        	 $("#loginusername").hide();
			    	        	 performance.loadAllPerformance();
			    	        	 performance.loadPeriodPerformance('day',0);
			    	        	 $("#show_logout").html("&nbsp;&nbsp;下班：注销打卡");
//			        			$("#onlinehourse").html(json.onlinehourse);
//			        			$("#onlineminters").html(json.onlineminiter);
//			        			performance.divShowHide(obj);
			        		}else if(json.result =="-1"){
			        			performance.showMessge("参数错误");
			        		}else if(json.result == false || json.result =="false"){
			        			performance.showMessge("您输入的员工号不对!");
			        		}
			        	}
			        }
			    });
				//gologin();
			}
			
		},
		logout:function(obj){
			var url = contextPath+"userservice/logout";
			performance.showDisplay();
			$("#allperioddata").hide();
			$("#allloading").show();
			$.ajax({
				type:'post',
		        dataType: "json",
		        url: url,
		        data:"nick="+nick+"&sysuser="+getEmployeeID(),
		        error: function(){
		   	    }, 
		        success: function (json){
		        	$("#logout").hide();
		        	$("#allperioddata").show();
					$("#allloading").hide();
		        	if(json!=null){
		        		if(json.result == true || json.result =="true"){
		        			 performance.hideDisplay();
		        			 $("#isonline").val("nologin");
		    				 $("#username").html("您还未登录");
		    				 $("#loginusername").val('我的员工号');
		    	        	 $("#show_logout").html("&nbsp;&nbsp;上班: 登录");
		    	        	 $("#loginusername").show();
		    	        	 performance.loadAllPerformance();
		    	        	 performance.loadPeriodPerformance('day',0);
		    	        	 performance.showMessge("下班成功");
		        		}else if(json.result =="-1"){
		        			performance.showMessge("参数错误");
		        		}else if(json.result == false || json.result =="false"){
		        			performance.showMessge("系统错误!");
		        		}
		        	}
		        }
		    });
			//$("#logoutForm").submit();
		},
		checkForm:function(){
			var adviceType = $("#advicetypelist").val();
			if(adviceType ==''||adviceType ==' '||adviceType == undefined){
				performance.showMessge('建议类型不能为空');
				return false;
			} 
			
			var title = $("#textbox").val();
			title = title.replaceAll(" ","");
			if(title ==''||title ==' '||title == undefined){
				performance.showMessge('标题不能为空');
				return false;
			}
			if(title.length>20){
				performance.showMessge('标题不能超过20个字');
				return false;
			}
			
			var content = $("#area").val();
			content = content.replaceAll(" ","");
			if(content ==''||content==' '||content == undefined){
				performance.showMessge('内容不能为空');
				return false;
			}
			if(content.length>100){
				performance.showMessge('内容不能超过100个字');
				return false;
			}
			
			$("#sellerid").val(seller);
			var isanony = $("#anonymity").attr("checked");
			if(isanony=="checked"){
				$("#creatorid").val("");
			}else{
				$("#creatorid").val(getCurUser());
			}
			performance.showDisplay();
			$("#MyForm").submit();
			
		},
		ratingShow:function(startCount,startNums,totalValue,scoreValue){
			 $('#ratingDiv').raty({
				  readOnly: true,
				  noRatedMsg:'',
				  number:   startNums,
				  start:startCount
			 });
//			$("#ratingDiv").wijrating();
//			var o = $.extend(true, {}, $('#ratingDiv').data("wijrating").options);
//			o.count = startCount;
//			o.totalValue = totalValue;
//			o.value = scoreValue;
//			o.disabled = true;
//			o.hint.disabled = true;
//			o.resetButton.disabled = true;
//			$('#ratingDiv').wijrating(o);
			
		},
		divShowHide:function (name) {
			if ($("#" + name).css("display") == "none") {
				
				$("div[class='logout']").hide();
				$("div[class='suggest']").hide();
				$("#" + name).show(500);
			}
			else if ($("#" + name).css("display") == "block") {
				$("#" + name).hide(500);
			}
		},
		showMessge:function(msg){
			showMessage(msg);
//			$("#smsg").html(msg);
//			$('#dialog').wijdialog('open');
		},
		cleanForm:function(){
			 $("#textbox").val("");
			 $("#area").val("");
			 $("#creatorid").val("");
			 $("#anonymity").attr("checked",false);
		},
		showDisplay:function(){
			$.tooltip({"type":"beforeAjaxSend","callback":function(result){
		 		  
			   }
			 });
		},
		hideDisplay:function(){
			$.tooltipCloseAjax();
		}
		
		
}