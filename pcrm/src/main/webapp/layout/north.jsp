<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<script type="text/javascript" charset="UTF-8">
	$(function() {

		sy.logoutFun = function(b) {
			$.post('userController.do?logout', function() {
				if (b) {
					location.replace(sy.bp());
				} else {
					sy.$loginDialog.dialog('open');
				}
			});
		};

	});
</script>
<div style="position: absolute; right: 0px; bottom: 0px; ">
	<!-- <a href="javascript:void(0)" class="easyui-menubutton" menu="#layout_north_kzmbMenu" iconCls="icon-edit">控制面板</a> --><!-- <a href="javascript:void(0)" class="easyui-menubutton" menu="#layout_north_zxMenu" iconCls="icon-help">注销</a> -->
</div>
<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	<!-- <div onclick="sy.addTabFun({title:'个人信息',src:'userController.do?info'});">个人信息</div> -->
	<div class="menu-sep"></div>
	<div>
		<span>更换主题</span>
		<div style="width: 100px;">
			<div onclick="changeThemeFun('default');">蓝色</div>
			<div onclick="changeThemeFun('gray');">灰色</div>
		</div>
	</div>
</div>
<!-- <div id="layout_north_zxMenu" style="width: 100px; display: none;">
	<div onclick="sy.logoutFun();">锁定窗口</div>
	<div class="menu-sep"></div>
	<div onclick="sy.logoutFun();">重新登录</div>
	<div onclick="sy.logoutFun(true);">退出系统</div>
</div> -->