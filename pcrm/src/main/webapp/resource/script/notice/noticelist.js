
/**
 * 通知JS
 */
var notice = {
		init:function(){
			$("ul#noticeList li").find('h3').live('click',function(){
				  $(this).parent().siblings('div.con').show();
			});
			
			$("#noticeList").find("p.shouqi").live('click',function(){
				  var noticeid = $(this).attr("id");
				  if($(this).parent().parent().find("p.weidu").length>0){
					  notice.updateNoticeDetail(userid,noticeid);
				  }
				  $(this).parent().hide().siblings().remove('p.weidu');
			});
			
			notice.showLimitDiscountPage(0,"first");
		},
		showMessge:function(msg){
			$("#smsg").html(msg);
			$('#dialog').wijdialog('open');
		},
		showLimitDiscountPage:function(cur_page,page){
			var url = "notice/noticepage";
			$.ajax({
				type:'post',
		        dataType: "json",
		        url: url,
		        data:'userid='+userid+'&type=0&cpage='+cur_page+'&page='+page,
		        error:   function(){
		            alert(arguments[1]);
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
							                          "<p class='shouqi' id='"+n.id+"'>收 起</p>"+
							                     	"</div>";
			                	 if(n.read==0){
			                		 noticelist +="<p class='weidu'>未读</p>";
			                	 }
			                	 noticelist+="</li>";
			                }
			                $("#noticeList").html(noticelist);
			                $("#pagebar").html(pagebar);
		        		}else{
		        			 $("#noticeList").html("<div class='tips_wujilu'>当前暂无记录</div>");
		        		}
		                
		                
		        	}
		        	

		        }
		    });
		},
		updateNoticeDetail:function(userid,noticeid){
			var url = "notice/updatedetail";
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
		        		
		        	}
		        }
		    });
		},
		getpagehtml:function(page,pagecount){
			var pagebar = "";
			if(pagecount>0){
				if(page==0&&pagecount>1){
					pagebar +="<li title='首页'><a>首页</a></li><li><a>上一页</a></li><li title='首页'><a href=\"javascript:notice.showLimitDiscountPage("+page+",'next')\">下一页</a></li><li><a href=\"javascript:notice.showLimitDiscountPage("+page+",'last')\">尾页</a></li>";
				}else if(page==0&&pagecount==1){
					pagebar +="<li><a>首页</a></li><li><a>上一页</a></li><li><a>下一页</a></li><li><a>尾页</a></li>";
				}else if(0<page&&page<parseInt(pagecount)-1){
					pagebar +="<li><a href=\"javascript:notice.showLimitDiscountPage("+page+",'first')\">首页</a></li><li><a href=\"javascript:notice.showLimitDiscountPage("+page+",'previous')\">上一页</a></li><li><a href=\"javascript:notice.showLimitDiscountPage("+page+",'next')\">下一页</a></li> <li><a href=\"javascript:notice.showLimitDiscountPage("+page+",'last')\">尾页</a></li>";
				}else{
					pagebar +="<li><a href=\"javascript:notice.showLimitDiscountPage("+page+",'first')\">首页</a></li><li><a href=\"javascript:notice.showLimitDiscountPage("+page+",'previous')\">上一页</a></li><li><a>下一页</a></li><li><a>尾页</a></li>";
				}
			}
			return pagebar;
		}
};


