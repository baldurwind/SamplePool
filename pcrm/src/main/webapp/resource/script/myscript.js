$.ajaxSetup({
	contentType: "application/x-www-form-urlencoded; charset=utf-8",
	cache: false 
});

String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
};


if($.cookie('has_shortcut')==null)
	$.cookie('has_shortcut','checked',{expires: null, path: '/'});
else
	$.cookie('has_shortcut')=='checked'?bindShortcut():unbindShortcut();

	
var cons_item= new Array();

function closeDiv(div) {
	$('#'+div).dialog('close');
}
function showDiv(div) {
//	$('#'+div).dialog('open');
	   $('#'+div).dialog({autoOpen: true,resizable:false});
}
function showDiv(div,text,ititle) {
	   $('#'+div).html("<div class='ips_loading'>"+text+"</div>");
	   $('#'+div).dialog({autoOpen: true,resizable:false,title:ititle});
} 	 
function selectShortcut(){
	if($('#shortcut').attr('checked')=='checked'){
		$.cookie('has_shortcut','checked',{expires: null, path: '/'});
		bindShortcut();
	}
	else{
		$.cookie('has_shortcut','',{expires: null, path: '/'});
		unbindShortcut();
	}
}

function bindShortcut(){
	 try{
		 $.hotkeys.add('ctrl+h', function(){ $.cookie('currNav',1,{expires: null, path: '/'}); getLink("");});
		 $.hotkeys.add('ctrl+k', function(){ $.cookie('currNav',2,{expires: null, path: '/'}); getLink("marketing");});
		 $.hotkeys.add('ctrl+y', function(){ $.cookie('currNav',3,{expires: null, path: '/'}); getLink("marketing/coupon/index");});
		 $.hotkeys.add('ctrl+d', function(){ $.cookie('currNav',4,{expires: null, path: '/'}); getLink("trade/index");});
		 $.hotkeys.add('ctrl+b', function(){ $.cookie('currNav',6,{expires: null, path: '/'}); getLink("reminder/index");});
		 $.hotkeys.add('ctrl+s', function(){ $.cookie('currNav',7,{expires: null, path: '/'}); getLink("item/toSearch");});	
	}
	catch(e){
		alert(e);
	} 
	

}
function unbindShortcut(){
 	try{
		$.hotkeys.remove('ctrl+h');
		$.hotkeys.remove('ctrl+k');
		$.hotkeys.remove('ctrl+y');
		$.hotkeys.remove('ctrl+d');
		$.hotkeys.remove('ctrl+b');
		$.hotkeys.remove('ctrl+s');	
	}catch(e){
		alert(e);
	} 
}
function showItemBasic(numiid,store,price){
	$('#consultation_sku_price').html(price+" 元");
	$('#consultation_sku_quantity').html(store+' 件');
	
	$('#ump_promotion').html('查询中..<img style="vertical-align:middle" height="18" width="18"  src="resource/newcss/images/ajax-loader.gif"/>');
	$('#ump_promotion').load(contextPath+'marketing/ump/promotion?seller='+getSeller()+'&numiid='+numiid);
}

function pcrmInit(){
	 var url="behavior/keyword?seller="+getSeller()+"&buyer="+getBuyer()+"&nick="+getNick();
	 $("#keyword_module").accordion({"extend":true});
	 $("#keyword_content_module").accordion({"extend":true});
	 $('#keyword_content_module').load(url);
	 $('#crm_module').load("crm/summary?seller="+getSeller()+"&buyer="+getBuyer()+"&nick="+getNick());
	  
	 
	 //在询商品参数选中效果
	 $("ul#cp_parameter li a").click(function(){
		$(this).addClass('xuanzhong').siblings('a').removeClass('xuanzhong');
	 });
	
	 $("#behavior_item_relation").tabChange();
	
	 $(".ui-tab-con").find("div.behavior_item_list").hover(function(){ 
			$(this).addClass("line_gray").find("div.fabu_btn").show();
		},function(){
			$(this).removeClass("line_gray").find("div.fabu_btn").hide();
	});
	 
	 $('#zxunjilu').html('正在查询..<img height="18" width="18"  src="'+contextPath+'resource/newcss/images/ajax-loader.gif"/>');
	 
		var url="behavior/consultation?seller="+getSeller()+"&buyer="+getBuyer()+"&numiid="+getNumiid()+"&nick="+getNick();
		$('#zxunjilu').load(url,
					function(){
			$("#zhixunList").tabChangeAuto({'lightedClass':'curr','listConClass':'zixunList_Con'});
			$("#zxunjilu").accordion({"extend":true});
			 
				$('#consultation_sku_price').html('');
				$('#consultation_sku_quantity').html('');
				 var temp=document.getElementsByName('cons_item');
				  for(var i=0;i< temp.length;i++){
					 cons_item[i]= new Array();
				  }
			});
}

function showItemRelationPage(tag){
	var cp=$('#current_page').val();
	if(cp==null)
		cp=0;
	$('#item_module').html('正在查询..<img height="18" width="18"  src="resource/newcss/images/ajax-loader.gif"/>');
	var url ="behavior/itemrelation?nick="+getNick()+"&seller="+getSeller()+"&buyer="+getBuyer()+'&numiid='+getNumiid()+"&page="+tag+"&current_page="+cp;
	$('#item_module').load(url);
}
function showVisitingHistoryPage(tag){
	var cp=$('#current_page').val();
	if(cp==null)
		cp=0;
	$('#item_module').html('正在查询..<img height="18" width="18"  src="resource/newcss/images/ajax-loader.gif"/>');
	var	url ="behavior/visitinghistory?nick="+getNick()+"&seller="+getSeller()+"&buyer="+getBuyer()+"&page="+tag+"&current_page="+cp;
	$('#item_module').load(url);
}
function showBoughtItemPage(tag){
	var cp=$('#current_page').val();
	if(cp==null)
		cp=0;
	$('#item_module').html('正在查询..<img height="18" width="18"  src="resource/newcss/images/ajax-loader.gif"/>');
	var url ="behavior/boughtitem?nick="+getNick()+"seller="+getSeller()+"&buyer="+getBuyer()+"&page="+tag+"&current_page="+cp;
	$('#item_module').load(url);
}

function  visitinghistory(){
	$('#item_module').html('正在查询..<img height="18" width="18"  src="resource/newcss/images/ajax-loader.gif"/>');
	var url ="behavior/visitinghistory?nick="+getNick()+"&seller="+getSeller()+"&buyer="+getBuyer();
	$('#item_module').load(url);
}
function boughtitem(){
	$('#item_module').html('正在查询..<img height="18" width="18"  src="resource/newcss/images/ajax-loader.gif"/>');
	var url ="behavior/boughtitem?nick="+getNick()+"&seller="+getSeller()+"&buyer="+getBuyer();
	$('#item_module').load(url);
}
function itemrelation(){
	$('#item_module').html('正在查询..<img height="18" width="18"  src="resource/newcss/images/ajax-loader.gif"/>');
	var url ="behavior/itemrelation?nick="+getNick()+"&seller="+getSeller()+"&buyer="+getBuyer()+'&numiid='+$('#numiid').val()+"&current_page=0";
	$('#item_module').load(url);
}

function querySku(numiid,props_length,itemindex,pindex,obj){
	for(var j=0;j<props_length;j++){
		if(j==pindex) {
			if(null==cons_item[itemindex])
				cons_item[itemindex]=new Array();
				cons_item[itemindex][j]=obj.lang;
		}
	 }
	
	if(cons_item[itemindex].length==props_length){
		$.ajax({
			url:'item/sku/findSku?nick='+getNick()+'&seller='+getSeller()+'&numiid='+numiid,
			dataType:"json",
			type:'post',
			data:{'props': JSON.stringify(cons_item[itemindex])},
			success: function(result){
				if(result.result=='nothing'){
					$('#consultation_sku_price').html("无信息");
					$('#consultation_sku_quantity').html("无信息");
				}
				if(result.result=='true'){
					$('#consultation_sku_price').html(result.price+" 元");
					$('#consultation_sku_quantity').html(result.quantity+' 件');
				}
			} 
		});
	}
}
function queryTradeByStatus(tradeStatus){
	window.location.href="trade/index?nick="+getNick()+"&seller="+getSeller()+"&buyer="+getBuyer()+"&numiid="+getNumiid()+"&tradeStatus="+tradeStatus+"&tradeWith=status";
}
function queryTradeWithRefund(){
	window.location.href="trade/index?nick="+getNick()+"&seller="+getSeller()+"&buyer="+getBuyer()+"&numiid="+getNumiid()+"&tradeWith=refund&tradeStatus=";
}



function queryAfterService(tids){
	var url="customerService/index?nick="+getNick()+"&seller="+getSeller()+"&tids="+tids+"&userid="+getCurUser()+"&buyer="+getBuyer()+"&numiid="+getNumiid();
	window.location.href=url;
}

function getSeller(){
	return encodeURIComponent($('#seller').val());
}
function getBuyer(){
	return	encodeURIComponent($('#buyer').val());
}


function getperformance(){
	window.location.href=basePath+"worktableservice/show?userid="+encodeURIComponent($('#nick').val());
}
/**
 * 给js加一个replaceAll函数
 * @param s1 要替换的字符
 * @param s2 目标字符
 * @returns
 */
String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
}
function getNick(){
	return	encodeURIComponent($('#nick').val());
}
function getNumiid(){
	return	encodeURIComponent($('#numiid').val());
}

function getCurUser(){
//	var userinfo =	$.cookie('employeeInfo');
//	if(userinfo==null||userinfo==undefined){
//		var userinfo =	$.cookie('userInfo');
//	}
	var userinfo =	$.cookie('userInfo');
	if(userinfo==null||userinfo==undefined){
		return null;
	}
	if(userinfo.indexOf('"')>=0){
		userinfo = userinfo.substring(1,userinfo.length-2+1);
	}
	return userinfo.split(",")[0];
}
function getLink1(module,tradeId){
	   var seller = $("#seller").val();
	   var nick = $("#nick").val();
	   var buyer = $("#buyer").val();
	   var numiid = $("#numiid").val();
	   form = $("<form></form>");
    form.attr('action',contextPath+module);
    form.attr('method','get');
    var input1 = $("<input type='hidden' name='seller' value='"+seller+"'/>");
    var input2 = $("<input type='hidden' name='nick' value='"+nick+"'/>");
    var input3 = $("<input type='hidden' name='buyer' value='"+buyer+"'/>");
    var input4 = $("<input type='hidden' name='numiid' value='"+numiid+"'/>");
    var input5 = $("<input type='hidden' name='tradeId' value='"+tradeId+"'/>");
  
    form.append(input1);
    form.append(input2);
    form.append(input3);
    form.append(input4);
    form.append(input5);
    form.css('display','none');
    form.appendTo("body");
    form.submit();
	   
}



function getLink(module){
	   
	   var seller = $("#seller").val();
	   var nick = $("#nick").val();
	   var buyer = $("#buyer").val();
	   var numiid = $("#numiid").val();
	   form = $("<form></form>");
       form.attr('action',contextPath+module);
       form.attr('method','get');
       var input1 = $("<input type='hidden' name='seller' value='"+seller+"'/>");
       var input2 = $("<input type='hidden' name='nick' value='"+nick+"'/>");
       var input3 = $("<input type='hidden' name='buyer' value='"+buyer+"'/>");
       var input4 = $("<input type='hidden' name='numiid' value='"+numiid+"'/>");
       form.append(input1);
       form.append(input2);
       form.append(input3);
       form.append(input4);
       form.css('display','none');
       form.appendTo("body");
       form.submit();
	   
    // window.location.href=contextPath+module+"?seller="+getSeller()+"&buyer="+getBuyer()+"&nick="+getNick()+"&numiid="+getNumiid();
}

function getCurUserName(){
	var userinfo =	$.cookie('employeeInfo');
	if(userinfo==null||userinfo==undefined){
		var userinfo =	$.cookie('userInfo');
	}
	
	if(userinfo==null||userinfo==undefined){
		return null;
	}
	if(userinfo.indexOf('"')>=0){
		userinfo = userinfo.substring(1,userinfo.length-2+1);
	}
	return userinfo.split(",")[1];
}

function getNickId(){
	var userinfo =	$.cookie('userInfo');
	if(userinfo==null||userinfo==undefined){
		return null;
	}
	if(userinfo.indexOf('"')>=0){
		userinfo = userinfo.substring(1,userinfo.length-2+1);
	}
	return userinfo.split(",")[0];
}

function getEmployeeName(){
	var userinfo =	$.cookie('employeeInfo');
	if(userinfo==null||userinfo==undefined){
		return null;
	}
	if(userinfo.indexOf('"')>=0){
		userinfo = userinfo.substring(1,userinfo.length-2+1);
	}
	return userinfo.split(",")[1];
}

function getEmployeeID(){
	var userinfo =	$.cookie('employeeInfo');
	if(userinfo==null||userinfo==undefined){
		return null;
	}
	if(userinfo.indexOf('"')>=0){
		userinfo = userinfo.substring(1,userinfo.length-2+1);
	}
	return userinfo.split(",")[0];
}

function getEmployeeOrNickID(){
	var userinfo =	$.cookie('employeeInfo');
	if(userinfo==null||userinfo==undefined){
		userinfo =	$.cookie('userInfo');
	}
	if(userinfo==null||userinfo==undefined){
		return null;
	}
	if(userinfo.indexOf('"')>=0){
		userinfo = userinfo.substring(1,userinfo.length-2+1);
	}
	return userinfo.split(",")[0];
}


/***
 * 验证按钮权限
 * @param resourceUrl 页面url
 */
function authButtonResource(resourceUrl){
	$.ajax({
		type:'post',
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
        					$("#"+buttonId).css("display","block");
        				}
        				
        			}
        		}
        	}
        }
    });
}

/***
 * 验证按钮权限
 * @param resourceUrl 页面url
 */
function authButtonResourceByName(resourceUrl){
	$.ajax({
		type:'post',
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
        					$("img[name='" + buttonId + "']").show();
        				}
        				
        			}
        		}
        	}
        }
    });
}


/**
 * 消息提示
 * @param msg
 */
function showMessage(msg){
	$.tooltip({"type":"info","content":msg,"callback":function(result){
		   return result;
	   	}
	});
}


function showDisplay(){
	$.tooltip({"type":"beforeAjaxSend","callback":function(result){
 		  
	   }
	 });
}

function hideDisplay(){
	$.tooltipCloseAjax();
} 

function ww_msg_send(){
	 var msg = arguments[0];
	 msg = msg.replaceAll("<br>"," ");
	 document.getElementById("ww_msg_plugin").setAttribute("value",msg);
	 document.getElementById("ww_msg_plugin").click();
}

function IPlugin( type , msg ){
 if( type == "id")
   return "ww_msg_plugin";
 else if (type == "msg")
     return (document.getElementById("ww_msg_plugin").getAttribute("value"));
 else 
     return ("other --- type:"+type+",msg:"+msg);
}

function sendWangWangMsg(index){
// var kt = $("#"+index+"_title").html();
 var kc = $("#"+index+"_cont").html();
 kc = kc.replaceAll("<br>"," ");
 ww_msg_send(kc);
}
function test(){
	alert(1);
}