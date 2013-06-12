
/**
 * 通知JS
 */
var notice = {
		curType:0,
		init:function(){
			$("ul#noticeList li").find('h3').live('click',function(){
				  $(this).parent().siblings('div.con').show();
			});
			
			$("ul#readednoticeList li").find('h3').live('click',function(){
				  $(this).parent().siblings('div.con').show();
			});
			
			$("#noticeList").find("p.shouqi").live('click',function(){
				  var noticeid = $(this).attr("id");
				  if($(this).parent().parent().siblings("p.weidu").length>0){
					  notice.updateNoticeDetail(userid,noticeid);
				  }
				  $(this).parent().parent().hide().siblings().remove('p.weidu');
			});
			
			$("#readednoticeList").find("p.shouqi").live('click',function(){
				  var noticeid = $(this).attr("id");
				  if($(this).parent().parent().siblings("p.weidu").length>0){
					  notice.updateNoticeDetail(userid,noticeid);
				  }
				  $(this).parent().parent().hide().siblings().remove('p.weidu');
			});
			$("#nrnoticetab").click(function(){
				notice.curType = 0;
				notice.showLimitDiscountPage(0,"first");
			});
			$("#arnoticetab").click(function(){
				notice.curType = 1;
				notice.showLimitDiscountPage(0,"first");
			});
			$(".ui-tab").tabChange();
			notice.showLimitDiscountPage(0,"first");
		},
		showMessge:function(msg){
			$("#smsg").html(msg);
			$('#dialog').wijdialog('open');
		},
		showLimitDiscountPage:function(cur_page,page){
			var url = contextPath+"notice/noticepage";
			if(notice.curType==0){
			    $("#noticeList").html("<div class='tips_loading'>查询中...</div>");
			    $("#pagebar").html("");
			}else{
				 $("#readedpagebar").html("");
				$("#readednoticeList").html("<div class='tips_loading'>查询中...</div>");
			}
			$.ajax({
				type:'post',
		        dataType: "json",
		        url: url,
		        data:'userid='+userid+'&type='+notice.curType+'&cpage='+cur_page+'&page='+page,
		        error:   function(){
		   	    }, 
		        success: function (json){
		        	if(json!=null){
		        		var rows = json.row;
		        		var pagebar = notice.getpagehtml(json.page,json.pagecount);
		        		var noticelist = "";
		        		if(rows.length>0){
		        			for (var i = 0; i < rows.length; i++){
			                	 var n = rows[i];
			                	 var status = "";
			                	 noticelist += "<li>"+
							                        "<div class='title'>"+
							                        "<h3><a href='javascript:void(0)'>"+n.title+"</a></h3>"+
							                    	"<p><span>发起人&nbsp;&nbsp;&nbsp;"+n.creator+"&nbsp;&nbsp;&nbsp;"+n.datetime+"&nbsp;&nbsp;</span></p>"+
							                        "</div>"+
							                        "<div class='con'>"+
							                     	   "<p>"+n.content+"</p>"+
							                     	   "<div class='t-r'><p class='publicFormBtn pfb-glay shouqi' id='"+n.id+"'>收 起</p></div>"+
							                     	"</div>";
			                	 if(n.read==0){
			                		 noticelist +="<p class='weidu'>未读</p>";
			                	 }
			                	 noticelist+="<div class='clear'></div></li>";
			                }
		        			if(notice.curType==0){
		        				  $("#noticeList").html(noticelist);
					              $("#pagebar").html(pagebar);
		        			}else{
		        				$("#readednoticeList").html(noticelist);
					            $("#readedpagebar").html(pagebar);
		        			}
			              
		        		}else{
		        			if(notice.curType==0){
		        			    $("#noticeList").html("<div class='tips_wujilu'>当前暂无记录</div>");
		        			}else{
		        				$("#readednoticeList").html("<div class='tips_wujilu'>当前暂无记录</div>");
		        			}
		        		}
		                
		                
		        	}
		        	

		        }
		    });
		},
		updateNoticeDetail:function(userid,noticeid){
			var url = contextPath+"notice/updatedetail";
			$.ajax({
				type:'post',
		        dataType: "json",
		        url: url,
		        data:'userid='+userid+'&noticeid='+noticeid,
		        error: function(){
		            alert(arguments[1]);
		   	    }, 
		        success: function (json){
		        	if(json!=null){
		        		//notice.showLimitDiscountPage(0,"first");
		        	}
		        }
		    });
		},
		getpagehtml:function(page,pagecount){
			
			var pagebar = "<li class='ui-pager-step'>第"+(parseInt(page)+1)+"页</li><li class='ui-pager-count'>共"+pagecount+"页</li>";
			if(pagecount>0){
				if(page==0&&pagecount>1){
					pagebar +="<li class='ui-pager-firstPage-disable'></li><li class='ui-pager-prePage-disable'></li><li class='ui-pager-nextPage' onclick=\"notice.showLimitDiscountPage("+page+",'next')\"></li><li class='ui-pager-lastPage' onclick=\"javascript:notice.showLimitDiscountPage("+page+",'last')\"></li>";
				}else if(page==0&&pagecount==1){
					pagebar +="<li class='ui-pager-firstPage-disable'><li class='ui-pager-prePage-disable'><li class='ui-pager-nextPage-disable'></li><li class='ui-pager-lastPage-disable'></li>";
				}else if(0<page&&page<parseInt(pagecount)-1){
					pagebar +="<li class='ui-pager-firstPage' onclick=\"javascript:notice.showLimitDiscountPage("+page+",'first')\"></li><li class='ui-pager-prePage' onclick=\"javascript:notice.showLimitDiscountPage("+page+",'previous')\"></li><li class='ui-pager-nextPage' onclick=\"javascript:notice.showLimitDiscountPage("+page+",'last')\"></li><li class='ui-pager-lastPage' onclick=\"javascript:notice.showLimitDiscountPage("+page+",'last')\"></li>";
				}else{
					pagebar +="<li class='ui-pager-firstPage' onclick=\"javascript:notice.showLimitDiscountPage("+page+",'first')\"></li><li class='ui-pager-prePage' onclick=\"javascript:notice.showLimitDiscountPage("+page+",'previous')\"></li><li class='ui-pager-nextPage-disable'></li><li class='ui-pager-lastPage-disable'></li>";
				}
			}
			//pagebar +="<div class='clear'></div>";
			return pagebar;
		}
};


