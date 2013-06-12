<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<c:forEach items="${limitdiscounts_detail}" var="pojo">
	<dl class="cplist">
		<dt><a  onclick="ww_msg_send('http://item.taobao.com/item.htm?id=${pojo.numIid}')"><img  src="${pojo.picUrl}" width="82" title="${pojo.title} 折扣：${pojo.itemDiscount/100}折  限购：${pojo.limitNum}" height="82" />
		</a><p class="fs"><span>点击发送</span></p></dt>
	</dl>
</c:forEach>
<script>
$("dl.cplist dt").hover(function(){ $(this).find("p.fs").toggle()});
</script>
<%-- <a target="_blank" href="http://item.taobao.com/item.htm?id=${pojo.numIid}" > --%>   
