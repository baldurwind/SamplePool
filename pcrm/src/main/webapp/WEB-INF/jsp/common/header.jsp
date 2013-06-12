<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
String path = request.getContextPath();

String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
request.setAttribute("basePath", basePath);
%>
<script type="text/javascript">
var contextPath = "<%=basePath%>";
var staticResourcePath = 'http://static.chamago.cn';
</script>
<meta http-equiv="pragma" content="no-cache" />
<link rel="icon" href="${static_resource_host}/resource/newcss/images/favicon.ico" type="image/x-icon" /> 
<link rel="shortcut icon" href="${static_resource_host}/resource/newcss/images/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${static_resource_host}/resource/newcss/global.css"/>
<link rel="stylesheet" type="text/css" href="${static_resource_host}/resource/newcss/module.css"/>
<link rel="stylesheet" type="text/css" href="${static_resource_host}/resource/newcss/themes/classic/themeStyle.css"/>
<link rel="stylesheet" type="text/css" href="${static_resource_host}/resource/jquery-ui/css/ui-lightness/jquery-ui-1.8.18.custom.css"/>
<script src="${static_resource_host}/resource/script/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="${static_resource_host}/resource/jquery-ui/js/jquery-ui-1.8.18.custom.min.js"  type="text/javascript"></script>
<script src="${static_resource_host}/resource/script/jquery.hotkeys.js" type="text/javascript"></script>
<script src="${static_resource_host}/resource/script/json.min.js" type="text/javascript"></script>
<script src="${static_resource_host}/resource/script/plug_1.js" type="text/javascript"></script>
<script src="${static_resource_host}/resource/script/myscript.js"  type="text/javascript"></script>
<base href="<%=basePath%>">
