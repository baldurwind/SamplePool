<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<div class="order_mianban_con">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td height="30">标记：</td>
<td><table width="100%">
<tr>
<td><label>
<input name="seller_flag" type="radio"   id="RadioGroup1_0"  value="1" <c:out value="${seller_flag==1?'checked':'' }"/>  />
	<img src="${static_resource_host}/resource/images/flag5.png" width="18" height="18" />              
</label></td>
<td><label>
	<input name="seller_flag" type="radio" id="RadioGroup1_1" value="2"  <c:out value="${seller_flag==2?'checked':'' }"/>/>
<img src="${static_resource_host}/resource/images/flag4.png" alt="" width="18" height="18" /></label></td>
<td><label>
	<input name="seller_flag"  type="radio"   id="RadioGroup1_2"  value="3" <c:out value="${seller_flag==3?'checked':'' }"/>    />
<img src="${static_resource_host}/resource/images/flag3.png" alt="" width="18" height="18" /></label></td>
<td><label>
	<input  name="seller_flag" type="radio" id="RadioGroup1_3"  value="4" <c:out value="${seller_flag==4?'checked':'' }"/>    />
<img src="${static_resource_host}/resource/images/flag2.png" alt="" width="18" height="18" /></label></td>
<td><label>
	<input name="seller_flag"  type="radio"   id="RadioGroup1_4"  value="5"  <c:out value="${seller_flag==5?'checked':'' }"/>    />
<img src="${static_resource_host}/resource/images/flag1.png" alt="" width="18" height="18" /></label></td>
</tr>
</table>
</td>
</tr>
<tr>
<td valign="top">备忘信息：</td>
	<td><label><textarea name="textarea" id="seller_memo" cols="45" rows="5">${seller_memo }</textarea></label></td>
</tr>
</table>
</div>
