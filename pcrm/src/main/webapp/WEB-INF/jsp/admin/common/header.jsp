<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
request.setAttribute("basePath", basePath);
%>
<base href="<%=basePath%>">
<script type="text/javascript">
var contextPath = "<%=basePath%>";
var staticResourcePath = 'http://static.chamago.cn';
</script>

<!--Theme-->
<link rel="stylesheet" type="text/css" href="${static_resource_host}/resource/css/comm.css"/>
<link rel="stylesheet" type="text/css" href="${static_resource_host}/resource/css/taogexing.css"/>
<!--Wijmo Widgets CSS-->
<link href="${static_resource_host}/resource/css/jquery-wijmo.css" rel="stylesheet" type="text/css" />
<link href="${static_resource_host}/resource/css/jquery.wijmo-complete.all.2.0.0.min.css" rel="stylesheet" type="text/css" />
<!--Wijmo Widgets JavaScript-->
<script src="${static_resource_host}/resource/script/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="${static_resource_host}/resource/script/jquery-ui.min-1.8.17.js" type="text/javascript"></script>
<script src="${static_resource_host}/resource/script/jquery.wijmo-open.all.2.0.0.min.js" type="text/javascript"></script>
<script src="${static_resource_host}/resource/script/jquery.wijmo-complete.all.2.0.0.min.js" type="text/javascript"></script>
<script src="${static_resource_host}/resource/script/plug_1.js" type="text/javascript"></script>
<script src="${static_resource_host}/resource/admin/script/common.js" type="text/javascript"></script>

<script type="text/javascript">

/**
 * 给js加一个replaceAll函数
 * @param s1 要替换的字符
 * @param s2 目标字符
 * @returns
 */
String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
}

function getCurUser(){
	var userinfo =	$.cookie('adminUserInfo');
	if(userinfo==null||userinfo==undefined){
		var userinfo =	$.cookie('userInfo');
		if(userinfo==null||userinfo==undefined){
			return null;
		}
		userinfo = userinfo.substring(1,userinfo.length-2+1);
		return userinfo.split(",")[0];
	}
	return userinfo;
	
}
function getSeller(){
	return $("#seller").val();
}

function authButtonResource(resourceUrl){
	$.ajax({
		type:'post',
        dataType: "json",
        url: 'findResourceByUserAndPath',
        data:'userid='+getCurUser()+'&resourceUrl='+resourceUrl,
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

function authButtonResource2(resourceUrl){
	$.ajax({
		type:'post',
        dataType: "json",
        url: 'findResourceByUserAndPath',
        data:'userid='+getCurUser()+'&resourceUrl='+resourceUrl,
        success: function (json){
        	if(json!=null){
        		//var resourceArr = json.resource;
        		if(json.length>0){
        			for(var i=0;i<json.length;i++){
        				var resUrl = json[i].resourceUrl;
        				var idx = resUrl.lastIndexOf("/");
        				if(idx<resUrl.length){
        					var buttonname = resUrl.substring(idx+1,resUrl.length);
        					$("input[name='" + buttonname +"']").css("display","block");
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

/**
 * 确认提示
 * @param msg
 */
function confirmMessage(msg,func){
	$.tooltip({"type":"confirm","content":msg,"callback":function(result){
		   if(result){
			   func();
		   }
	   	}
	});
}


</script>