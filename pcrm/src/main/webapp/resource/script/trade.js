function initTrade(){
	var tradeWith=$('#tradeWith').val();
	 
	$('#trade_module').html('正在查询中..<img height="18" width="18"  src="http://static.chamago.cn/resource/newcss/images/ajax-loader.gif"/>');
	if(tradeWith=='status')
		$('#trade_module').load("trade?tradeStatus="+getTradeStatus()+"&current_page=0&nick="+getNick()+"&seller="+getSeller()+"&buyer="+getBuyer());
	else if(tradeWith=='refund') 
		$('#trade_module').load("trade/tradeWithRefund?current_page=0&nick="+getNick()+"&seller="+getSeller()+"&buyer="+getBuyer());
	else
		$('#trade_module').load("trade?tradeStatus="+getTradeStatus()+"&current_page=0&nick="+getNick()+"&seller="+getSeller()+"&buyer="+getBuyer());
}

function getTradeStatus(){
	return encodeURIComponent($('#tradeStatus').val());
}

function tradePage(tag){
	var cur=$('#current_page').val();
	var tradeStatus=$('#tradeStatus').val();
	$('#trade_module').html('正在查询中..<img height="18" width="18"  src="http://static.chamago.cn/resource/newcss/images/ajax-loader.gif"/>');
	$('#trade_module').load("trade?tradeStatus="+tradeStatus+"&current_page="+cur+"&nick="+getNick()+"&seller="+getSeller()+"&buyer="+getBuyer()+"&page="+tag);
}

function trace(tid){
	$('#trace').html('正在查询中..<img height="18" width="18"  src="http://static.chamago.cn/resource/newcss/images/ajax-loader.gif"/>');
	 $('#trace').load("trade/trace?seller="+getSeller()+"&tid="+tid,function(){
		 $('#trace').dialog({autoOpen: true,modal: true, resizable:false,width:400,
			 buttons:{"发送":function(){  ww_msg_send_1($('#trace_text').html());},"关闭": function() {$(this).dialog("close");}}
			});
	 });
	 //$('#trace_text').html()
	
}
function shipping(tid){
	$('#shipping').html('正在查询中..<img height="18" width="18"  src="http://static.chamago.cn/resource/newcss/images/ajax-loader.gif"/>'); 
	$('#shipping').load("trade/shipping?seller="+getSeller()+"&tid="+tid);
	 $('#shipping').dialog({autoOpen: true,modal: true, resizable:false,width:400,height:300,
		 buttons:{"关闭": function() {$(this).dialog("close");}}
		});
}
function memo(tid){
	$('#memo').load("trade/memo/get?seller="+getSeller()+"&tid="+tid); 
	showDiv('memo','正在查询中..<img height="18" width="18"  src="http://static.chamago.cn/resource/newcss/images/ajax-loader.gif"/>','更改备忘');
	 $('#memo').dialog({autoOpen: true,modal: true, resizable:false,width:400,height:300,
		 buttons:{
			 "保存": function() { 
				 	var seller_flags = document.getElementsByName("seller_flag");
				    var flag='';
				    for(var i=0;i<seller_flags.length;i++){
				   if(seller_flags[i].checked){
					   	flag=seller_flags[i].value;
				   	  	break;
				    }
				  }
				    if(flag==''){
				    	 showDiv('status_div','标记不能为空','更改备忘');
				    	return;
				    }
				$.ajax({
					url:'trade/memo/update?seller='+getSeller()+"&tid="+tid+"&flag="+flag+"&memo="+encodeURIComponent($('#seller_memo').val()),
					dataType:'json',
					beforeSend: function() {
					    showDiv('status_div','正在提交备注..<img height="18" width="18"  src="http://static.chamago.cn/resource/newcss/images/ajax-loader.gif"/>','更改备忘');
					    
					},
					success: function(result){
						if(result.result){
							$('#status_div').html("订单"+tid+"，备注修改成功。");
							$('#memo').dialog('close');
							 
						 	$('#memo_'+tid).removeAttr('class').addClass("beiwang_"+flag);
							}
						else
							$('#status_div').html("订单"+tid+"， 备注修改失败,"+result.msg);
					} 
				});
				},
			 "关闭": function() {$(this).dialog("close");}
		 }
		});
	   $('.ui-dialog').css({'left':"10px"});
}
function postage(tid,origin_fee	,has_post_fee){
	if(!has_post_fee){
		showDiv('status_div','卖家已包邮。','修改邮费');
		return;
	}
 	$('#postage').dialog({autoOpen: true,modal: true,title:"修改运费  订单号："+tid+" 运费:"+origin_fee,width:400,resizable:false,
		buttons: {
		"提交": function() { 
		var fee=$('#fee').val();
		if(!validatePostage(fee))
			return;
		
		$.ajax({
			url:'trade/postage/update?seller='+getSeller()+'&tid='+tid+'&fee='+fee,
			beforeSend: function() {
			    showDiv('status_div','正在修改运费..<img height="18" width="18"  src="http://static.chamago.cn/resource/newcss/images/ajax-loader.gif"/>','修改邮费');
			},
			success: function(result){
				if(result.result){
					$('#status_div').html("运费修改成功。");
					$('#postage').dialog('close');
					$('#postage_'+tid).html("(含快递"+returnFloat(fee)+")");
				}
				else
					$('#status_div').html("运费修改失败，"+result.msg);
			} 
		});
		},
		"关闭": function() {$(this).dialog("close");}}
	});
}

function batchUpdateMemo(){
	
	var tids="";
	$("input[name='checktid']:checkbox:checked").each(function(index,element){
		tids+=","+$(this).next("label").children("span").text();
	});
	if(tids==""){
		 showDiv('status_div','请至少选中一个订单号。','批量修改备注。');
		 return ;
	}
	
	
 	
	 $('#memo').dialog({autoOpen: true,modal: true, resizable:false,width:400,height:300,
		 buttons:{
			 "保存": function() { 
				    seller_flags = document.getElementsByName("seller_flag");
				   var  flag='';
				    for(var i=0;i<seller_flags.length;i++){
				   if(seller_flags[i].checked){
					   	flag=seller_flags[i].value;
				   	  	break;
				    }
				  }
				    if(flag==''){
				    	showDiv('status_div','标记不能为空','批量关闭订单');
				    	return;
				    }
				    
				    $.ajax({
						url:'trade/memo/batch/update?seller='+getSeller()+"&tids="+tids+"&flag="+flag+"&memo="+encodeURIComponent($('#seller_memo').val()),
						dataType:'json',
						beforeSend: function() {
						    showDiv('status_div','正在批量提交备注..<img height="18" width="18"  src="http://static.chamago.cn/resource/newcss/images/ajax-loader.gif"/>','批量关闭订单');
						},
						success: function(result){
							if(result.result){
								$('#status_div').html("订单批量备注修改成功。");
								getLink("trade/index");
								$('#memo').dialog('close');
								}
							else{
								$('#status_div').html('<div style="width:250px; overflow:hidden" >订单:<br/>'+result.succ_tids_tids.replaceAll(";",",<br/>")+'修改成功.<br/>'+result.err_tids.replaceAll(";",",<br/>")+'修改失败.</div>');
							}
						},
						failure:function(){
							$('#status_div').html("批量备注修改失败。");
							$('#postage').dialog('close');
						}
					});
				},
			 "关闭": function() {$(this).dialog("close");}
		 }
		});
	 showDiv('memo','正在加载..<img height="18" width="18"  src="http://static.chamago.cn/resource/newcss/images/ajax-loader.gif"/>','批量更改备忘');
	 $('#memo').load("trade/memo/get?seller=&tid=&isbatch=1");
	
}
function batchChangePostage(){
	var tids="";
	$("input[name='checktid']:checkbox:checked").each(function(index,element){
		tids+=","+$(this).next("label").children("span").text();
	});
	if(tids==""){
		 showDiv('status_div','请至少选中一个订单号.','批量修改运费');
		return ;
	}
 	$('#postage').dialog({autoOpen: true,modal: true,title:"批量修改运费",width:400,resizable:false,
		buttons: {
		"提交": function() { 
		var fee=$('#fee').val();
		if(!validatePostage(fee))
			return;
		$.ajax({
			url:'trade/postage/batch/update?seller='+getSeller()+'&tids='+tids+'&fee='+fee,
			beforeSend: function() {
			    showDiv('status_div','正在修改运费..<img height="18" width="18"  src="http://static.chamago.cn/resource/newcss/images/ajax-loader.gif"/>','批量修改运费');
			},
			success: function(result){
				if(result.result){
					$('#status_div').html("批量运费修改成功。");
					$('#postage').dialog('close');
				}
				else
					$('#status_div').html("订单号:<br/>"+result.err_tids.replaceAll(";","<br/>")+"批量运费修改失败.");
			},
			failure:function(){
				$('#status_div').html("批量运费修改失败。");
				$('#postage').dialog('close');
			}
		});
		},
		"关闭": function() {$(this).dialog("close");}}
	});
	
} 

function selectAllTrade(o){
		if(o.checked){
			$("input[name='checktid']").attr("checked",true);
		}else{
			$("input[name='checktid']").attr("checked",false);
		}

}
function validatePostage(price){
	if(price.length > 0) {
		var reg = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
		if(!reg.test(price)) {
			alert("输入的金额格式不正确");
			return false;
		}
	} 
	return true;
}
function returnFloat(value){  //保留两位小数点
    value = Math.round(parseFloat(value) * 100) / 100;
    if (value.toString().indexOf(".") < 0) {
     value = value.toString() + ".00";
    }
    return value;
   }
