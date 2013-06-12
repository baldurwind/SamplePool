<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!--工作台 -->
<div id="bottomNav" class="nav">
	<ul >
    	<li class="left"><a href="javascript:getLink('worktable/index')" title="我的工作台" curr="8" >我的工作台</a></li>
		<li class="left"><a href="javascript:getLink('notice/index')" curr="9" title="通知">通知</a></li>
		<li class="left"><a href="javascript:getLink('reminder/index')" title="备忘" curr="10">备忘</a></li>
		<li class="left"><a href="javascript:getLink('knowledge/index/0')" title="知识库"curr="11">知识库</a></li>
		<li class="right"><a href="shortcut" curr='12' title="快捷键">？系统设置</a></li>
		<li class="right"><a href="javascript:getLink('admin')" title="后台登陆" curr="13">后台登陆</a></li>
    </ul>
    <div class="clear"></div>
    <div id="noreadnums" class="info_tipsUI tipsFoot_position" style="display:none;"></div>
    <div id="resultValue" class="info_tipsUI tipsFoot_position-note" style="display:none;"></div>
</div>
<script type="text/javascript" src="${static_resource_host}/resource/script/comet/js-pushlet-client.js"></script>
<script type="text/javascript" src="${static_resource_host}/resource/script/comet/api.js"></script>
<script type="text/javascript">p_embed()</script>
<script type="text/javascript">
	$(document).ready(function () {
		$("#bottomNav  ul.left li").live('click',function(){
			 $(this).addClass("current").siblings('li').removeClass("current");
		});
		$("#bottomNav ul.right li").live('click',function(){
			 $(this).parent().siblings('ul.left').find('li').removeClass("current");
		});
		initPoll();
		bindCookieEvent();
		
	});
	
	
	function bindCookieEvent(){
	 	$("#bottomNav").find('a').live('click',function(){
			var _temp = $(this).attr('curr').toString();
			$.cookie('currNav',null,{expires: null, path: '/'});
			$.cookie('bottonCurrNav',_temp,{expires: null, path: '/'});
		});
	    setCurrNav($.cookie('bottonCurrNav'));
	 }
	 
	function setCurrNav(v){
		if(v){
			$(".nav a").each(function(index, element) {
				 if(index+1 == v)
					 $(element).addClass("current");
				else
					$(element).removeClass("current");
			 });
		}else
			return ;
	} 
	
	function initPoll(){ 
		p_join_listen('NOTICE,REMIND');
	}

	// onData method called by pushlet frame
	function onData(pushletEvent) {
		var ns = pushletEvent.get("noticecount");
		var rs = pushletEvent.get("remindcount");
		
		if(ns==null||ns==''||ns==undefined){
			if(rs==null||rs==''||rs==undefined){
				$("#noreadnums").css("display","none");
			}
			
		}else{
			if(ns=="0"){
				$("#noreadnums").css("display","none");
			}else{
				$("#noreadnums").css("display","");
				$("#noreadnums").html(ns);
			}
			
		}
		if(rs==null||rs==''||rs==undefined){
			if(ns==null||ns==''||ns==undefined){
				$("#resultValue").css("display","none");
			}
		}else{
			if(rs=="0"){
				$("#resultValue").css("display","none");
			}else{
				$("#resultValue").css("display","block");
				$("#resultValue").html(rs);
			}
			
		}
			
	}
</script>
<!-- 
<script>
	$(document).ready(function() {
		reloadReminder();
		timer=setInterval("reloadReminder()",60*10*1000);
		
		  $("#bottomNav  ul.left li").live('click',function(){
				 $(this).addClass("current").siblings('li').removeClass("current");
			});
			$("#bottomNav ul.right li").live('click',function(){
				 $(this).parent().siblings('ul.left').find('li').removeClass("current");
			});
		bindCookieBottomEvent();
	});
	//PL._init();
	//PL.joinListen('/source/event');  
	function onData(event) {
	    document.getElementById("noreadnums").innerText=event.get("msg");
	}	
	function bindCookieBottomEvent(){
		 	$("#bottomNav").find('a').live('click',function(){
				var _temp = $(this).attr('curr').toString();
				$.cookie('currbottomNav',_temp,{expires: null, path: '/'});
			});
		 	setCurrBottomNav($.cookie('currbottomNav'));
		 }
		 
		function setCurrBottomNav(v){
			if(v){
				$(".nav a").each(function(index, element) {
					 if(index+1 == v)
						 $(element).addClass("current");
					else
						$(element).removeClass("current");
				 });
			}else
				return ;
		} 
	
	function reloadReminder(){
		var url = 'reminder/loadNotReminderCount?nick=' + $("#nick").val();
		$('#comet-frame-reminder')[0].src = url;
	}
	
	
	function updateReminder(data) {
		$('#resultValue').html(data);
	}
</script>
<iframe id="comet-frame-reminder" style="display: none;"></iframe> -->