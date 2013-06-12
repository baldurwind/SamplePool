/**
 * js扩展工具类
 * 
 * 徐浩亮
 */

var sy = $.extend({}, sy);/* 全局对象 */

sy.pngFun = function() {
	var imgArr = document.getElementsByTagName("IMG");
	for ( var i = 0; i < imgArr.length; i++) {
		if (imgArr[i].src.toLowerCase().lastIndexOf(".png") != -1) {
			imgArr[i].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + imgArr[i].src + "', sizingMethod='auto')";
			imgArr[i].src = "images/spacer.gif";
		}
		if (imgArr[i].currentStyle.backgroundImage.lastIndexOf(".png") != -1) {
			var img = imgArr[i].currentStyle.backgroundImage.substring(5, imgArr[i].currentStyle.backgroundImage.length - 2);
			imgArr[i].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + img + "', sizingMethod='crop')";
			imgArr[i].style.backgroundImage = "url('images/spacer.gif')";
		}
	}
};
sy.bgPngFun = function(bgElements) {
	for ( var i = 0; i < bgElements.length; i++) {
		if (bgElements[i].currentStyle.backgroundImage.lastIndexOf(".png") != -1) {
			var img = bgElements[i].currentStyle.backgroundImage.substring(5, bgElements[i].currentStyle.backgroundImage.length - 2);
			bgElements[i].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + img + "', sizingMethod='crop')";
			bgElements[i].style.backgroundImage = "url('images/spacer.gif')";
		}
	}
};

$.parser.onComplete = function() {/* 页面所有easyui组件渲染成功后，隐藏等待信息 */
	if ($.browser.msie && $.browser.version < 7) {/* 解决IE6的PNG背景不透明BUG */
		sy.pngFun();
		sy.bgPngFun($('span'));
	}
	window.setTimeout(function() {
		$.messager.progress('close');
	}, 1000);
};

$.fn.panel.defaults.onBeforeDestroy = function() {/* tab关闭时回收内存 */
	var frame = $('iframe', this);
	if (frame.length > 0) {
		frame[0].contentWindow.document.write('');
		frame[0].contentWindow.close();
		frame.remove();
		if ($.browser.msie) {
			CollectGarbage();
		}
	}
};

$.fn.panel.defaults.loadingMessage = '数据加载中，请稍候。。。。';

$.fn.datagrid.defaults.onLoadError = function(XMLHttpRequest) {
	$.messager.progress('close');
	alert(XMLHttpRequest.responseText);
};

$.fn.combogrid.defaults.onLoadError = function(XMLHttpRequest) {
	$.messager.progress('close');
	alert(XMLHttpRequest.responseText);
};

$.fn.combobox.defaults.onLoadError = function(XMLHttpRequest) {
	$.messager.progress('close');
	alert(XMLHttpRequest.responseText);
};

$.fn.form.defaults = {
	onSubmit : function() {/* 由于本项目没有使用easyui的form提交，只是使用easyui的表单验证，所以不需要onSubmit方法，屏蔽此方法可以解决当表单里只有一个input和一个textarea，并且input是validate的，点击回车按钮会发生空提交事件的bug */
		return false;
	}
};

$.extend($.fn.validatebox.defaults.rules, {
	eqPassword : {/* 扩展验证两次密码 */
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '密码不一致！'
	}
});

/**
 * 增加formatString功能
 * 
 * 使用方法：sy.fs('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 */
sy.fs = function(str) {
	for ( var i = 0; i < arguments.length - 1; i++) {
		str = str.replace("{" + i + "}", arguments[i + 1]);
	}
	return str;
};

/**
 * 增加命名空间功能
 * 
 * 使用方法：sy.ns('jQuery.bbb.ccc','jQuery.eee.fff');
 */
sy.ns = function() {
	var o = {}, d;
	for ( var i = 0; i < arguments.length; i++) {
		d = arguments[i].split(".");
		o = window[d[0]] = window[d[0]] || {};
		for ( var k = 0; k < d.slice(1).length; k++) {
			o = o[d[k + 1]] = o[d[k + 1]] || {};
		}
	}
	return o;
};

/**
 * 获得项目根路径
 * 
 * 使用方法：sy.bp();
 */
sy.bp = function() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
};

/**
 * 生成UUID
 */
sy.UUID = function() {
	var s = [], itoh = '0123456789ABCDEF';
	for ( var i = 0; i < 36; i++)
		s[i] = Math.floor(Math.random() * 0x10);
	s[14] = 4;
	s[19] = (s[19] & 0x3) | 0x8;
	for ( var i = 0; i < 36; i++)
		s[i] = itoh[s[i]];
	s[8] = s[13] = s[18] = s[23] = '-';
	return s.join('');
};

/**
 * 获得URL参数
 */
sy.getUrlParam = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
};

/**
 * 用于火狐浏览器调试
 */
sy.debug = function(o) {
	if (navigator.userAgent.indexOf('Firefox') > -1) {
		console.info(o);/* 正式项目将此行注释即可 */
	}
};

/**
 * 调试DOM文档是否有重复ID
 */
sy.debugIdLength = function() {
	var t = '';
	$.each($('[id]'), function(index, item) {
		if (item.id && item.id != 'undefined') {
			var i = $('[id="' + item.id + '"]').length;
			if (i > 1 && item.id != t) {
				sy.debug('查到重复ID，ID名称【' + item.id + '】，重复【' + i + '】次！');
				t = item.id;
			}
		}
	});
	if (t == '') {
		sy.debug('没有重复ID！');
	}
};

/**
 * 查看DOM节点数目
 */
sy.debugDomLength = function() {
	sy.debug('有【' + $('div').length + '】个DIV');
};

$.ajaxSetup({
	type : "POST",
	error : function(XMLHttpRequest, textStatus, errorThrown) {/* 扩展AJAX出现错误的提示 */
		$.messager.progress('close');
		alert(XMLHttpRequest.responseText);
	}
});

/**
 * 显示AJAX开始时的提示信息
 */
sy.showLoadingDiv = function() {
	var loadingDiv = $('#_AJAXLOADINGDIV_');
	if (loadingDiv.length < 1) {
		$('body').append('<div id="_AJAXLOADINGDIV_" style="z-index: 9999999; position: absolute; top: 0px; right: 0px; background-color:yellow; color:#8F5700; padding:5px;"><div>异步数据请求中....</div></div>');
	} else {
		loadingDiv.show();
	}
};
/**
 * AJAX结束时隐藏提示信息
 */
sy.hideLoadingDiv = function() {
	$('#_AJAXLOADINGDIV_').fadeOut(500);
};
/**
 * 为jQuery的ajax提供等待提示
 */
$(document).ajaxStart(sy.showLoadingDiv).ajaxStop(sy.hideLoadingDiv);
