// JavaScript Document
;(function($){
		$.fn.extend({
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
				 })
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