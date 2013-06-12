// JavaScript Document
;(function($){
		$.fn.extend({
			//基本网页选项卡
			/*选项卡布局基本架构
				<div id="ui-tab">      		
							<ul class="ui-tab-ul">
								<li class="ui-tab-ul-li-open">1</li>
								<li>2</li>
								<li>3</li>
							</ul>
							<div class="ui-tab-con"> ... </div>
							<div class="ui-tab-con"> ...  </div>
							<div class="ui-tab-con"> ...  </div> 
				</div>
				$("#maintab").tabChange({"lightedClass":"current"});
			*/  
			"tabChange":function(options){ 
				 options=$.extend({
						lightedClass:"ui-tab-ul-li-open",
						auto:false,
						time:2000  
				 },options);
				 var $con = $(this);
				 var $ul = $con.find("ul.ui-tab-ul").first();
				 var timer;
				 var len  =$ul.children("li").length;
				 var $div = $ul.siblings("div.ui-tab-con");
				 var index = 0;
				 $div.eq(0).show();
				 $ul.children("li").click(function (){
	  				 	 var index = $ul.find("li").index($(this));
						 $(this).addClass(options.lightedClass).siblings().removeClass(options.lightedClass)
						 $div.eq(index).show().siblings("div.ui-tab-con").hide();
	             });
				 	
				 function showCon(value){
				    $ul.find("li").eq(value).addClass(options.lightedClass).siblings().removeClass(options.lightedClass)
								  .parent().siblings("div").eq(value).show()
					 			  .siblings("div").hide()
								  
				 }
				 
				 if(options.auto){
					 $con.hover(
							function(){
								clearInterval(timer)
							},function(){
								timer = setInterval(function(){
										   showCon(index);
										   index++;
										   if(index==len){index=0;}
										},options.time); 
							}
					 ).trigger("mouseleave");
				 };
			 },
			//基本广告选项卡
			/*基本广告选项卡布局基本架构
				<div id="maintab">      		
							<ul>
								<li class="current">1</li>
								<li>2</li>
								<li>3</li>
							</ul>
							<div><img/><img/><img/></div>
				</div>
				$("#maintab").tabChange({"lightedClass":"current"});
			*/  
			"tabChange_ImgAdd":function(options){
				 options=$.extend({
						lightedClass:"ui-tab-ul-li-open" //此参数为鼠标放在选项卡上面出现的样式，默认为空
				 },options);
				 var $ul = $(this).find("ul").eq(0);
				 var $img = $ul.siblings("div").find("img");
				 $ul.children("li").mouseover(function (){
	  				 	 var index = $ul.find("li").index($(this));
						 $(this).addClass(options.lightedClass).siblings().removeClass(options.lightedClass)
						 $img.eq(index).show().siblings("img").hide();
	             });		     
			},
			"tabChangeEasy":function(options){
				 options=$.extend({
						lightedClass:"ui-tab-ul-li-open", //此参数为鼠标放在选项卡上面出现的样式，默认为空
						callback:function(){}
				 },options);
				 var $ul = $(this).find("ul.ui-tab-ul").eq(0);
				 $ul.children("li").click(function (){
	  				 	 var index = $ul.find("li").index($(this));
						 $(this).addClass(options.lightedClass).siblings().removeClass(options.lightedClass)
						 options.callback($(this));
	             });		     
			},
			/*网页手风琴
			  <div id="accordion">
			  	  <div>
				  	 <h3 class="header"><span>图标</span><a>标题</a></h3>
					 <div class="content"></div>
				  </div>
				  <div>
				  	 <h3 class="header"><span>图标</span><a>标题</a></h3>
					 <div class="content"></div>
				  </div>
				  <div>
				  	 <h3 class="header"><span>图标</span><a>标题</a></h3>
					 <div class="content"></div>
				  </div>
			  </div>
			
			"accordion":function(options){
				options = $.extend({
						"header":"h3",//标题栏类型,h3
						"hover":"hover",//标题栏悬浮样式
						"current":"current"//标题栏点击后样式
					},options);
				var _this = $(this);
				var _cons = $(this).children('div');	
			    var _header = $(this).children('div').children('h3');
				_header.each(function(index, element) {
                    $(this).live('click',function(){
						_header.eq(index).addClass(options.current).siblings("div").slideDown()
						       .parent().siblings().children("h3").removeClass(options.current).siblings("div").slideUp();
					});
					$(this).hover(function(){ $(this).toggleClass(options.hover)});
                });
			},*/
			/*网页手风
			  <div id="ui-accordion">
			       <h3 class="ui-accordion-header ui-accordion-header-hover ui-accordion-header-open"><span>图标</span><a>标题</a></h3>
				   <div class="ui-accordion-content"></div>
			  </div>
			琴*/
			"accordion":function(options){
				options = $.extend({
						header:"h3",//标题栏类型,h3
						hover:"ui-accordion-header-hover",//标题栏悬浮样式
						open:"ui-accordion-header-open",//标题栏点击后样式
						extend:false
					},options);
				var _this = $(this);
				var _cons = $(this).children('div.ui-accordion-content');	
			    var _header = $(this).children('h3.ui-accordion-header');
				if(options.extend){
					_header.addClass(options.open).siblings("div").show();
					$(_header).toggle(function(){
					 	$(this).removeClass(options.open).siblings("div").slideUp();
					},function(){
						$(this).addClass(options.open).siblings("div").slideDown();	
					});
				}else{
					$(_header).toggle(function(){
					 	$(this).addClass(options.open).siblings("div").slideDown();
					},function(){
						$(this).removeClass(options.open).siblings("div").slideUp();	
					});
				}
				$(_header).hover(function(){ $(this).toggleClass(options.hover)});
                
			},
			"tabChangeAuto":function(options){
				 options=$.extend({
						lightedClass:"", 
						listConClass:"",
						preBtn:'preBtn',
						nextBtn:'nextBtn'
				 },options);
				 var $this = $(this);
				 var $ul = $this.find("ul").first();
				 var timer;
				 var len  =$ul.children("li").length;
				 var index = 0;
				 var clickIndex_temp = 0;
				 $ul.children("li").click(function (){
						index  =   $ul.children("li").index($(this));
						$(this).addClass(options.lightedClass).siblings().removeClass(options.lightedClass)
						.parent().parent().siblings('.'+options.listConClass).children('div.con').eq(index).show()
						.siblings("div.con").hide();
				 });
				 function showCon(value){
				    $ul.find("li").eq(value).addClass(options.lightedClass).siblings().removeClass(options.lightedClass)
								  .parent().parent().siblings('.'+options.listConClass).children('div.con').eq(value).show()
					 			  .siblings("div").hide()
				 };
				 function bindhover(obj){
				 	 obj.hover(
						function(){clearInterval(timer)},
						function(){ timer = setInterval(function(){
												   showCon(index)
												   index++;
												   if(index==len){index=0;}
												},2000); 
						}
				 	).trigger("mouseleave");
				 };
				 function scrollpre(){
					$ul.animate({'left':($ul.position().left+$ul.find('li').outerWidth()+4)+'px'},200);
				 };
				 function scrollnext(){
					$ul.animate({'left':($ul.position().left-$ul.find('li').outerWidth()-4)+'px'},200);
				 };
				 $this.find("p.preBtn").live('click',function(){
					 if($(this).hasClass('predisable')) return ;
					  scrollpre();
					  clickIndex_temp--;
					  p_n_listener();
				});	 
				 $this.find('p.nextBtn').live('click',function(){
					 if($(this).hasClass('nextdisable')) return ;
				      scrollnext();
				      clickIndex_temp++;
					  p_n_listener();
				 });
				 function p_n_listener(){
					 if(len <= 6){ $this.find("p.preBtn").addClass('predisable'); $this.find("p.nextBtn").addClass('nextdisable');}
				 	 else if(clickIndex_temp<=0 ){ $this.find("p.preBtn").addClass('predisable');}
					 else if(clickIndex_temp >= 0 && clickIndex_temp <len-6){ $this.find("p.nextBtn").removeClass('nextdisable');$this.find("p.preBtn").removeClass('predisable') ;}
					 else if(clickIndex_temp >= len-6){ $this.find("p.nextBtn").addClass('nextdisable');$this.find("p.preBtn").removeClass('predisable') }
				 }
				 p_n_listener();
			}
		 });	 	 
})(jQuery);

/*JQUERY COOKIE插件
	$.cookie('abc',value,{expires: null, path: '/'})
*/
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