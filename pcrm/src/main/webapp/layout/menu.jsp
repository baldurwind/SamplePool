<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<script type="text/javascript" charset="UTF-8">
	$(function() {
	
		$.fn.zTree.init($('#menuTree'), {
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
				onClick : function(event, treeId, treeNode, clickFlag) {
					sy.addTabFun({
						title : treeNode.name,
						src : treeNode.page
					});
			
				}
			}
		}, [ {
			id : 1,
			pId : 0,
			name : '首页',
			open : true,
			page : 'homeController?portal&seller='+$('#seller').val()
		}, {
			pId : 1,
			name : '子账号管理',
			icon:"http://static.chamago.cn/resource/jslib/jquery-easyui-1.2.5/themes/icons/person.png",
			page : 'userController?user&seller='+$('#seller').val()
		}, {
			pId : 1,
			name : '角色管理',
			icon:"http://static.chamago.cn/resource/jslib/jquery-easyui-1.2.5/themes/icons/person.png",
			page : 'roleController?role&seller='+$('#seller').val()
		}, {
			pId : 1,
			name : '资源管理',
			icon:"http://static.chamago.cn/resource/jslib/jquery-easyui-1.2.5/themes/icons/tip.png",
			page : 'resourceController?resource&seller='+$('#seller').val()
		}]);
		
      if($('#seller').val()!="chamagoseller"){
    	  $("#menuTree_4").hide();
      }
	});
</script>
<div class="easyui-panel" fit="true" border="false">

	<ul id="menuTree" class="ztree"></ul>
</div>