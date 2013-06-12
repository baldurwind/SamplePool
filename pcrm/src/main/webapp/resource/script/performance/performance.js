
/**
 * 工作台业绩指标JS
 * gavin.peng 
 * 2012-02-29
 */
var performance = {
		weekload:false,
		monthload:false,
		init:function(){
			//$("#yejiData").tabChangeEasy();
			$("ul#yejiNav li").live('click',function(){
				var _index = $(this).index();
				$(this).addClass("click").siblings("li").removeClass('click');
				$("#yejiData").find('div.yejiData').eq(_index).show().siblings('div.yejiData').hide();
				var showid = $(this).attr("id");
				if(_index==0){
					$("#yejiData").find('div.yejiData').eq(_index).show().siblings('div.yejiData').hide();
				}else if(_index==1){
					if(!performance.weekload){
						performance.loadPeriodPerformance(showid,_index);
					}else{
						$("#yejiData").find('div.yejiData').eq(_index).show().siblings('div.yejiData').hide();
					}
				}else{
					if(!performance.monthload){
						performance.loadPeriodPerformance(showid,_index);
					}else{
						$("#yejiData").find('div.yejiData').eq(_index).show().siblings('div.yejiData').hide();
					}
				}
				
			});
			$("#dialog").wijdialog({
		         autoOpen: false,
		         captionButtons: {
		             refresh: { visible: false }
		         }
		     });
			$('#show_suggest').click(function () {
				//$("#suggest").slideToggle();
				performance.divShowHide(this.lang);
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
	         if(userName==null||userName == undefined){
	        	 $("#isonline").val("nologin");
	        	 $("#username").html("您还未登录");
	        	 $("#show_logout").html("&nbsp;&nbsp;上班: 登录");
	         }else{
	        	 $("#username").html("用户:"+userName);
	        	 $("#isonline").val("yeslogin");
	        	 $("#show_logout").html("&nbsp;&nbsp;下班：注销登录");
	         }
	         var wangwang = $("#nick").val();
	         if(wangwang!=null&&wangwang.indexOf(":")>0){
	        	 wangwang = wangwang.split(":")[1];
	         }
	         $("#wangwangid").html(wangwang);
	         performance.cleanForm();
			
		},
		loadPeriodPerformance:function(period,_index){
			var url = "worktable/periodquery";
			$("#yejiData").find('div.yejiData').hide();
			$("#loading").css("display","");
			$.ajax({
				type:'post',
		        dataType: "json",
		        url: url,
		        data:'userid='+userid+'&period='+period,
		        error:   function(){
		            alert(arguments[1]);
		   	    }, 
		        success: function (json){
		        	$("#loading").css("display","none");
		        	if(json!=null){
		        		
		        		if(period=="week"){
		        			performance.weekload = true;
		        		}else if(period =="month"){
		        			performance.monthload = true;
		        		}
		        		var rNum = 0;
		        		var ast = 0;
		        		var ranking = 0;
		        		var cusresult = 0;
		        		var cusprice = 0;
		        		var pd = json.result;
		        		if(pd==true||pd=="true"){
		        			rNum = json.receiveNum;
			        		 ast = json.avgRespTime;
			        		 ranking  = json.ranking;
			        		 cusresult = json.cusResult;
			        		 cusprice = json.cusPrice;
		        		}
		        		
		                $("."+period+"_receivenums").html(rNum);
		                $("."+period+"_avgprice").html(cusprice);
		                $("."+period+"_ranking").html(ranking);
		                $("."+period+"_cusresult").html(cusresult);
		                $("."+period+"_avgresptime").html(ast);
		                $("#yejiData").find('div.yejiData').eq(_index).show().siblings('div.yejiData').hide();
		                
		        	}
		        }
		    });
		},
		loadNoReadNoticeNums:function(){
			var url = "notice/getnoreadcount";
			$.ajax({
				type:'post',
		        dataType: "json",
		        url: url,
		        data:"userid="+userid,
		        error:   function(){
		            alert(arguments[1]);
		   	    }, 
		        success: function (json){
		        	if(json!=null){
		        		var nums = json.count;
		        		if(parseInt(nums)>0){
		        			$("#noreadnums").html(nums);
		        		}
		        	}
		        }
		    });
		},
		loadAdviceTypeList:function(){
			var url = "adviceservice/advicetypelist";
			$.ajax({
				type:'post',
		        dataType: "json",
		        url: url,
		        data:"seller="+seller+"&orientedtype=1",
		        error: function(){
		            alert(arguments[1]);
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
			var url = "adviceservice/gettakedadvicecount";
			$.ajax({
				type:'post',
		        dataType: "json",
		        url: url,
		        data:"creator="+creator,
		        error: function(){
		        	performance.showMessge(arguments[1]);
		   	    }, 
		        success: function (json){
		        	var nums = 0;
		        	var myscore = 0;
		        	if(json!=null){
		        		var nums = json.count;
		        		myscore = json.score;
		        	}
		        	
		        	var startCount = parseInt(myscore/10);
		        	
		        	if(startCount<1){
		        		startCount = 1;
		        	}
		        	var totalValue = startCount*10;
		        	performance.ratingShow(startCount, totalValue,myscore);
		        	$("#takedadvicenums").html(nums);
		        }
		    });
		},
		computeOnlineTime:function(obj){
			var islogin = $("#isonline").val();
			if(islogin == "yeslogin"){
				var url = "userservice/getonlinetime";
				$.ajax({
					type:'post',
			        dataType: "json",
			        url: url,
			        data:"nick="+nick+"&sysuser="+userid,
			        error: function(){
			        	performance.showMessge(arguments[1]);
			   	    }, 
			        success: function (json){
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
				gologin();
			}
			
		},
		logout:function(obj){
			$("#logoutnick").val(nick);
			$("#logoutsysuser").val(userid);
			$("#logoutForm").submit();
		},
		checkForm:function(){
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
				$("#creatorid").val(creator);
			}
			$("#MyForm").submit();
		},
		ratingShow:function(startCount,totalValue,scoreValue){
//			 $('#ratingDiv').raty({
//				  readOnly: true,
//				  start:    startCount
//			 });
			$("#ratingDiv").wijrating();
			var o = $.extend(true, {}, $('#ratingDiv').data("wijrating").options);
			o.count = startCount;
			o.totalValue = totalValue;
			o.value = scoreValue;
			o.disabled = true;
			o.hint.disabled = true;
			o.resetButton.disabled = true;
			$('#ratingDiv').wijrating(o);
			
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
			$("#smsg").html(msg);
			$('#dialog').wijdialog('open');
		},
		cleanForm:function(){
			 $("#textbox").val("");
			 $("#area").val("");
			 $("#creatorid").val("");
			 $("#anonymity").attr("checked",false);
		}
		
		
}