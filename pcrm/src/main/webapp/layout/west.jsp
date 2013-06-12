<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<script type="text/javascript" charset="UTF-8">
	$(function() {

		sy.addTabFun = function(opts) {
			var t = '#centerTabs';
			$.messager.progress({
				text : '页面加载中....',
				interval : 100
			});
			var options = $.extend({
				title : '',
				content : '<iframe src="' + opts.src + '" frameborder="0" style="border:0;width:100%;height:99.2%;"></iframe>',
				closable : true,
				iconCls : ''
			}, opts);
			if ($(t).tabs('exists', options.title)) {
				$(t).tabs('close', options.title);
			}
			$(t).tabs('add', options);
		};

	});
</script>
<div class="easyui-accordion" fit="true" style="overflow: hidden;">
	<div title="菜单" href="layout/menu.jsp" style="overflow: hidden;"></div>
</div>