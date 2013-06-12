<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/include.jsp" %>
<script src="${static_resource_host}/resource/script/plug.js" type="text/javascript"></script>
<script type="text/javascript">
	function addNumId(numid) {
		$("#txtNumId").val(numid);
		document.getElementById('consultation_sku_price').innerHTML='';
		document.getElementById('consultation_sku_quantity').innerHTML='';
	}
	
	
</script>
<div class="proRegister" id="proRegister">
 	<!--<a href="##" class="btn default">查看缺货商品</a>-->
  	<h1 class="tit">事件登记</h1>
     <div id="reged_pro" style="padding:2px 0;">
     	<h3 class="head">登记商品</h3>
         <div style=" padding:3px 0 0 0 ">
             <div id="zhixunList" class="zixunList">
                 <div class="navlist">
                     <ul class="list">
                        <c:forEach var="entry" items="${mixEntryList}" varStatus="status">
                		  <li <c:if test="${status.index==0}">class="curr first" </c:if> id="${entry[0]}" onclick="addNumId('${entry[0]}')">
                				<a  id="item_${status.index}" href="javascript:showItemBasic('${entry[5]}','${entry[7]}')"><img src="${entry[2]}"  /></a> 
                			 <div class="zixun_tips thisColor">${entry[3]}</div> 
                		  </li>
               			 </c:forEach>
                     </ul>
                 </div>
                 <p class="ui-icon preBtn preS"></p>
                 <p class="ui-icon nextBtn nextS"></p>
               <div class="zixunList_Con">      <!--   <div id="sku_module"></div> -->
              <c:forEach var="entry" items="${mixEntryList}" varStatus="item_status"><!--  循环商品 -->
               	<c:set  var="props_name" value="${fn:split(entry[4],',')}"></c:set>
               	<div   name="cons_item"  class="con"     <c:if test="${item_status.index==0}"> style="display:block"</c:if>>
                	<h3 class="cptit"><span class="FL">${entry[1]}</span></h3>
                	 <div class="clear"></div>
                	 <c:if test="${entry[4]!=''}">
                	<ul id="cp_parameter" class="cp_parameter" >
                		<c:forEach var="props" items="${props_name}" varStatus="pname_status">   <!--  循环属性 -->
                			<c:set var="prop_name" value="${fn:split(props,'=')[0]}"></c:set>
                			<li>
                			<span name="${entry[0]}_sku_prop" >${prop_name}：</span> <!--  显示属性名 -->
                			<c:set var="str_values" value="${fn:split(props,'=')[1]}"/>
                			<c:set var="arr_values" value="${fn:split(str_values,'[-]')}"/>
							<c:forEach var="val" items="${arr_values}" >   <!--  循环属性值 -->
							<a  lang="${prop_name}_${val}"   onclick="querySku('${entry[0]}','${fn:length(props_name)}','${item_status.index}','${pname_status.index}',this)" href="javascript:" 
								 >${val}</a><!--显示属性值 -->
							</c:forEach>   		 
                		</li>
                		</c:forEach>
                		</ul>
                		</c:if>
                		
                		  <div class="clear"></div>
               	 </div>
              </c:forEach>
              <ul class="cp_parameter">
            	  <li><span>价格：</span><strong  id="consultation_sku_price"></strong><label>| </label>
		         	     库存：<b id="consultation_sku_quantity"></b> 
             			 <c:if test="${entry[6]=='Y'}"><span class="FL">促销：</span><span class="icon"></span></c:if>
               </li>
               </ul>
            </div>
             </div>
		</div>
         <div class="regForm">
         	<h3 class="bold">通知方式</h3>
            <form id="myForm" name="myForm">
            		 <input type="hidden" value="" id="txtNumId" name="numId">
            		 <input type="hidden" value="" id="txtSkuId" name="skuId">
		   			 <input type="hidden" value="${buyerNick}" id="txtBuyerNick" name="buyerNick"/>
		   			 <input type="hidden" id="txtWangwangNick" name="wangwangNick"/>
		   			 <input type="hidden" value="${sellerNick}" id="txtSellerNick" name="sellerNick"/>
		             <table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:12px;">
		             	<tr>
		                 	<td width="20%" align="center">期望价格：</td>
		                     <td><input class="textstyle" id="txtPrice" name="price" maxlength="10" /></td>
		                </tr>
		             	<tr>
		                 	<td width="20%" align="center">短信通知：</td>
		                     <td><input class="textstyle" id="txtMobile" name="mobile" maxlength="11"/></td>
		                 </tr>
		                 <tr>
		                 	<td width="20%" align="center">邮件通知：</td>
		                     <td><input class="textstyle" id="txtEmail" name="email" maxlength="30"/></td>
		                 </tr>
		                 <tr>
		                 	<td width="20%" align="center">通知截止：</td>
		                     <td><input class="textstyle" id="txtExpiredDate" name="expiredDate1" readonly="readonly"  onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'})"></td>
		                 </tr>
		                 <tr>
		                 	<td width="20%" align="center"></td>
		                     <td><input type="checkbox" style="vertical-align:middle" name="addReminder"/><label style="vertical-align:middle"> 新增到我的备忘录中，到货后提醒我与客户联系</label></td>
		                 </tr>
		                 <tr>
		                 	<td width="20%" align="center"></td>
		                     <td>
		                     	<p class="publicFormBtn" id="btnAddLeadsMemo"  onclick="saveLeadsMemo();">登记</p>
                            	<p class="publicFormBtn pfb-glay" id="btnAddReset" onclick="formReset();">重置</p>
                            	<p class="publicFormBtn  pfb-yellow" id="btnAddBack" onclick="toIndex();">返回</p>
		                     </td>
		                 </tr>
		            </table>
             </form>
         </div>
     </div>
 </div>
    
  <script type="text/javascript">
  	cons_item= new Array();
	$(function(){
		addNumId($(".curr").attr("id"));
		$("#zhixunList").tabChangeAuto({'lightedClass':'curr','listConClass':'zixunList_Con'});
		$("ul#cp_parameter li a").live('click',(function() {
			$(this).addClass('xuanzhong').siblings('a').removeClass('xuanzhong');
			document.getElementById('consultation_sku_price').innerHTML='更新中';
			document.getElementById('consultation_sku_quantity').innerHTML='更新中';
		}));
		temp=document.getElementsByName('cons_item');
		for(i=0;i< temp.length;i++){
			 cons_item[i]= new Array();
		}
	});
	
	function querySku(numiid,props_length,itemindex,pindex,obj){
		for(var j=0;j<props_length;j++){
			if(j==pindex) {
				if(null==cons_item[itemindex])
					cons_item[itemindex]=new Array();
					cons_item[itemindex][j]=obj.lang;
			}
		 }
		
		if(cons_item[itemindex].length==props_length){
			$.ajax({
				url:'item/sku/findSku?seller='+getSeller()+'&numiid='+numiid,
				dataType:"json",
				type:'post',
				data:{'props': JSON.stringify(cons_item[itemindex])},
				success: function(result){
					if(result.result=='nothing'){
						document.getElementById('consultation_sku_price').innerHTML="元";
						document.getElementById('consultation_sku_quantity').innerHTML='0 件';	
					}
					if(result.result=='true'){
						document.getElementById('consultation_sku_price').innerHTML=result.price+" 元";
						document.getElementById('consultation_sku_quantity').innerHTML=result.quantity+' 件';
						$("#txtSkuId").val(result.skuid);
					}
				} 
			});
		}
	}
	
	//保存缺货/促销事件登记
	function saveLeadsMemo() {  
		$("#txtWangwangNick").val(getCurUser());
		var numId = $.trim($("#txtNumId").val());
		if(numId.length == 0) {
			alert("请选择需登记的商品信息");
			return;
		}
		
		if($.trim($("#txtPrice").val()).length > 0) {
			//验证金额
			var reg = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
			if(!reg.test($("#txtPrice").val())) {
				showMessage("输入的金额格式不正确");
				return;
			}
		} 
		if($.trim($("#txtMobile").val()).length > 0) {
			//验证手机
			var reg = new RegExp("^(?:0[0-9]{2,3}[-\\s]{1}|\\(0[0-9]{2,4}\\))[0-9]{6,8}$|^[1-9]{1}[0-9]{5,7}$|^[1-9]{1}[0-9]{10}$");
			if(!reg.test($("#txtMobile").val())) {
				showMessage("手机号格式不正确");
				return;
			}
		} 
		if ($.trim($("#txtEmail").val()).length >0) {
			//验证邮箱
			var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
			if(!reg.test($("#txtEmail").val())) {
				showMessage("电子邮箱格式输入不正确");
				return;
			}
		} 
		if ($.trim($("#txtMobile").val()).length == 0 && $.trim($("#txtEmail").val()).length == 0) {
			showMessage("手机号码或电子邮箱请至少输入一项");
			return;
		}
		if($.trim($("#txtExpiredDate").val()).length == 0) {
			showMessage("请选择到期时间");
			return;
		}
		$("#btnAddLeadsMemo").attr("disabled",true);
		$("#btnAddReset").attr("disabled",true);
		$("#btnAddBack").attr("disabled",true);
	  	$.ajax({
	  		type: 'post',
	  		url: "leadsMemo/save",
	  		dataType: 'json',
	  		data: $("#myForm").serialize(),
	  		beforeSend: function() {
	  			showDisplay();
	  		},
	  		success: function(msg) {
	  			hideDisplay();
	  			if(msg.result=='true') {
	  				toIndex();	
	  				showMessage("添加成功");
	  			} else {
	  				showMessage("添加失败，请稍候重试");
	  			}
	  		},
	  		error: function() {
	  			hideDisplay();
	  		}
	  	});
	}
	
	function formReset() {
		$("#myForm")[0].reset();
	}
  </script>