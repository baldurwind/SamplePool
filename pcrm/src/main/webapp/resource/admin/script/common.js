/*****************
jqurey 基本扩展 
******************/

$.extend({
//    
//    //获取页面名称
    getPageName:function(){
	   var fname=window.location.href.match(/.+\/([^?]*)/)[1];
	   return fname.substring(0,fname.indexOf("."));
    },
    
    //获取页面参数
    getParameter:function(valueName){
        try{
            var str=document.location.href;
            var a=str.split("?");
            a=a[1].split("&");
            for(var i=0;i<a.length;i++){
                var b=a[i].split("=");
                if(b[0].toUpperCase()==valueName.toUpperCase())return b[1].replace('#','');
            }
            return "";
        }catch(e){
            return "";
        }
    }, 

    //返回 定长字符串 中文算一个 英文算半个
    getLengthStr : function (str,len){
        var i,sum;    
        sum=0;
        var str1='';
        for(i=0;i<str.length;i++){    
            if ((str.charCodeAt(i)>=0) && (str.charCodeAt(i)<=255)){
			sum=sum+1;
		}else{sum=sum+2;}
		if(sum>(len*2))break;
		str1+=str.charAt(i);
        }
        return str1;
    },
    // js css 动态导入
    includePath:'',
    include: function(file){
        var files = typeof(file)== "string"?[file]:file;
        for (var i = 0; i < files.length; i++){
            var name = files[i].replace(/^\s|\s$/g, "");
            var name=files[i];
            var att = name.split('.');
            var ext = att[att.length - 1].toLowerCase();
            if(ext=='js'){
                var fileref=document.createElement('script');//创建标签 
                fileref.setAttribute("type","text/javascript");//定义属性type的值为text/javascript 
                fileref.setAttribute("src", $.includePath + name);//文件的地址
                document.getElementsByTagName("head")[0].appendChild(fileref);
            }else if(ext=='css'){
                var fileref=document.createElement("link"); 
                fileref.setAttribute("rel", "stylesheet"); 
                fileref.setAttribute("type", "text/css");  
                fileref.setAttribute("href", $.includePath + name); 
                document.getElementsByTagName("head")[0].appendChild(fileref);
            }
        }
        
    },
    // 弹出面板
    openMyPannel:function (){
        var w=0;
        if (arguments[0])w=arguments[0];
	       else w=300;
        var t=0;
        if (arguments[1])t=arguments[1];
	       else t=70+$(window).scrollTop();
        var zindex=2000;
        if (arguments[2])zindex=arguments[2];
        
        var jqueryObj=$('<div></div>');
        jqueryObj.appendTo($('Body'));
        jqueryObj.html('');
        jqueryObj.css({
		  'position': 'absolute','top': t+'px',
		  'left': Math.round(($(window).width() - w) / 2)+'px',
		  'z-index': zindex
	    })
        
        var bgObj=$('<div></div>');
        bgObj.appendTo($('Body'));
        if(arguments[2]){bgObj.css({
            'position': 'absolute','background': '#000',
            'opacity': '0.0','-moz-opacity': '0.0',
            '-khtml-opacity': '0.0','filter': 'alpha(opacity=0)',
            'width': '100%',
            'height': $(window).height()>$(document).height()?$(window).height():$(document).height()+'px',
            'top': '0','left':'0','z-index': zindex-1
        })}else{
            bgObj.css({
                'position': 'absolute','background': '#000',
                'opacity': '0.4','-moz-opacity': '0.4',
                '-khtml-opacity': '0.4','filter': 'alpha(opacity=40)',
                'width': '100%',
                'height': $(window).height()>$(document).height()?$(window).height():$(document).height()+'px',
                'top': '0','left':'0','z-index': zindex-1
            })    
        }
        
        jqueryObj.data('bg',bgObj);
        return jqueryObj;
    },
    closeMyPannel:function (jqueryObj){
        var bgObj=jqueryObj.data('bg');
        bgObj.remove();
        jqueryObj.remove();
        //if($('#myPannelBox').size()>0)$('#myPannelBox').remove();
        //if($('#myPannelBoxBg').size()>0)$('#myPannelBoxBg').remove();
    },
    pannelBgClose:function(jqueryObj){
        var bgObj=jqueryObj.data('bg');
        bgObj.click(function(){
            bgObj.remove();
            jqueryObj.remove();
        })
    },
    //重新设置 背景尺寸 在面板 尺寸改变后
    pannelBgResize:function(jqueryObj){
        var bgObj=jqueryObj.data('bg');
        bgObj.css({'height':$(window).height()>$(document).height()?$(window).height():$(document).height()+'px'})
    },
    /** 小 面板 打开
     * 面板背景为透明 点击面板背景 关闭面板 top left 为 按钮 位置 参数 2 3为 偏移
    *  参数1 为 传入 button 的 辨别 字符串
		参数2 selfClose 是否 点自己 关闭 
     */
    
    openLittlePannel:function (button,selfClose){
        if(!button)return;
        var offset = $(button).offset();
        
        var l=offset.left;
        if (arguments[1])l=offset.left+arguments[1];
        
        var t=offset.top;
        if (arguments[2])t=offset.top+arguments[2];
	       
        var zindex=100;
        if (arguments[3])zindex=arguments[3];
        
        var jqueryObj=$('<div></div>');
        jqueryObj.appendTo($('Body'));
        jqueryObj.html('');
        jqueryObj.css({
		  'position': 'absolute','top': t+'px',
		  'left': l+'px',
		  'z-index': zindex
	    })
        
        var bgObj=$('<div></div>');
        bgObj.appendTo($('Body'));
        bgObj.css({
            'position': 'absolute','background': '#000',
            'opacity': '0.0','-moz-opacity': '0.0',
            '-khtml-opacity': '0.0','filter': 'alpha(opacity=0)',
            'width': '100%',
            'height': $(window).height()>$(document).height()?$(window).height():$(document).height()+'px',
            'top': '0','left':'0','z-index': zindex-1
        })
        bgObj.click(function(){
            bgObj.remove(); jqueryObj.remove()   
        })
        if(selfClose){
			jqueryObj.click(function(){bgObj.remove(); jqueryObj.remove();})
		}
		jqueryObj.data('bg',bgObj);
        return jqueryObj;
        
     },
     "tooltip":function (options){
		  options=$.extend({
				  type:"info", 
				  content:"操作成功",
				  callback:function(){}
		},options);
		var isEverTooltipPanel = $(document).data('tooltipPanel')
		if(isEverTooltipPanel){
			 isEverTooltipPanel.remove();
		} 
		this.result = false;
		var timeout = 2000; 
		var tooltipPanel = $('<div style=" padding:20px; border:1px solid #febe8f; background:#fff8d9; display:block; position:absolute;top:-100px; box-shadow:1px 1px 2px #333;border-radius:5px;left:200px;z-index:5000">'
							+'<div id="tooltipCon"></div>'
							+'</div>').appendTo($("body"))
		$(document).data('tooltipPanel',tooltipPanel);
		var outTooltip = function  (){
			tooltipPanel.animate({'top':$(window).scrollTop()+'px'},timeout/3,function(){})
		}
		$(window).scroll(function(){ tooltipPanel.animate({'top':$(window).scrollTop()+'px'},1,function(){})})
		var hiddenTooltip = function(t){
			 setTimeout(function(){ tooltipPanel.animate({'top':'-100px'},t,function(){ tooltipPanel.remove();})},t);
		 }
		
		if(options.type=="info"){
			 $(tooltipPanel).find("#tooltipCon").text(options.content);
			 outTooltip();
			 hiddenTooltip(timeout);
			 return false;
		}else if(options.type=="error"){
			 $(tooltipPanel).find("#tooltipCon").append('<span style="color:red; line-height:25px">'+options.content+'<span>');
			 outTooltip();
			 hiddenTooltip(timeout);
			 return false;
		}else if(options.type=="confirm"){
			 $(tooltipPanel).find("#tooltipCon").append('<span style="color:red;line-height:20px">'+options.content+'<span>')
			 								    .append('<input type="button" value="确认"  /><input type="button"   value="关闭"/>');
			 outTooltip();
			 $(tooltipPanel).find("input").eq(0).click(function(){ hiddenTooltip(200); result=true; options.callback(result);});
			 $(tooltipPanel).find("input").eq(1).click(function(){ hiddenTooltip(200); result=false; options.callback(result);});
		}else if(options.type=="beforeAjaxSend"){
		   var bgObj =$('<div></div>');
			   bgObj.appendTo($('body'));
			   bgObj.css({
					  'position': 'absolute','background': '#000',
					  'opacity' : 0.5,
					  'filter': 'alpha(opacity=20)',
					  'width': '100%',
					  'height': $(window).height()>$(document).height()?$(window).height():$(document).height()+'px',
					  'top': '0','left':'0','z-index': 1100
			  })
		   var status = $('<div style="width:40px; color:#fff; background:#fff; border:2px solid #333; padding:5px 20px; margin:100px auto; border-radius:3px; "><img src="resource/js/img/beforeSuccess.gif" style="margin:auto;display:block"/></div>').appendTo(bgObj);
		   $(document).data('bg',bgObj);
		}
    },
	 "tooltipCloseAjax":function(){
	 	var bgobj = $(document).data('bg');
		//console.log(bgobj);
		bgobj.remove();
	 }
});

     
jQuery.cookie = function (key, value, options) {

    // key and at least value given, set cookie...
    if (arguments.length > 1 && String(value) !== "[object Object]") {
        options = jQuery.extend({}, options);

        if (value === null || value === undefined) {
            options.expires = -1;
        }

        if (typeof options.expires === 'number') {
            var days = options.expires, t = options.expires = new Date();
            t.setDate(t.getDate() + days);
        }

        value = String(value);

        return (document.cookie = [
            encodeURIComponent(key), '=',
            options.raw ? value : encodeURIComponent(value),
            options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
            options.path ? '; path=' + options.path : '',
            options.domain ? '; domain=' + options.domain : '',
            options.secure ? '; secure' : ''
        ].join(''));
    }

    // key and possibly options given, get cookie...
    options = value || {};
    var result, decode = options.raw ? function (s) { return s; } : decodeURIComponent;
    return (result = new RegExp('(?:^|; )' + encodeURIComponent(key) + '=([^;]*)').exec(document.cookie)) ? decode(result[1]) : null;
};

///////////////////////////////////////////////////////////
function inittable_tr(){
	$(".publicDataTable tr:visible:odd").addClass('PUB_oddTR');
}
 