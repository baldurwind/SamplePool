<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script src="../resource/script/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<base href="<%=basePath%>">

<link rel="stylesheet" type="text/css" href="resource/style/global.css">
<link rel="stylesheet" type="text/css" href="resource/style/mystyle.css">
<!--Theme-->
<link href="resource/themes/aristo/jquery-wijmo.css" rel="stylesheet" type="text/css" title="rocket-jqueryui" />
<link href="resource/themes/taogexing/taogexing.css" rel="stylesheet" type="text/css">
<!--Wijmo Widgets CSS-->
<link href="resource/style/jquery.wijmo-complete.all.2.0.0.min.css" rel="stylesheet" type="text/css" />
<!--Wijmo Widgets JavaScript-->
<script src="resource/script/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="resource/script/jquery-ui.min-1.8.17.js" type="text/javascript"></script>
<script src="resource/script/jquery.hotkeys.js" type="text/javascript"></script>
<script src="resource/script/json.min.js" type="text/javascript"></script>
<SCRIPT src="resource/script/comet/ajax-pushlet-client.js"  type="text/javascript"></SCRIPT>
<script src="resource/script/jquery.wijmo-open.all.2.0.0.min.js" type="text/javascript"></script>
<script src="resource/script/jquery.wijmo-complete.all.2.0.0.min.js" type="text/javascript"></script>
<script src="resource/script/plug.js" type="text/javascript"></script>
<SCRIPT src="resource/script/myscript.js"  type="text/javascript"></SCRIPT>
<SCRIPT src="resource/script/trade.js"  type="text/javascript"></SCRIPT>
<script type="text/javascript">
var contextPath = "<%=basePath%>";
</script>
