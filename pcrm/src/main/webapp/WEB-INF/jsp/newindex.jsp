<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>淘个性-工作台</title>
</head>

<body>
<!---nav头部导航----->
    <jsp:include page="common/navigation.jsp"/>
<!---end_nav头部导航----->
<!--主体-->
<div class="wrapper">
    <!---状态----->
    <div class="index_status" id="member_status">
        <ul>
          <li class="thisColor">等待发货（<a  class="thisLink"  href="##">3</a>）</li>
          <li class="thisColor">申请退款（<a  class="thisLink"  href="##">3</a>）</li>
          <li>售后处理中（<a href="#">3</a>）</li>
        </ul>
        <div class="clear"></div>
    </div>
    <!--交易数据-->
    <div  class="index_tradeData" id="crm_member">
    	 <div class="index_tradeData_top">
             <table width="100%">
                  <tr>
                    <td height="30">本店消费次数</td>
                    <td>总消费金额 </td>
                    <td>平均客单价</td>
                    <td>上次消费</td>
                  </tr>
                  <tr>
                    <td class="shuju thisColor">23</td>
                    <td class="shuju thisColor">3256</td>
                    <td class="shuju thisColor">45</td>
                    <td class="shuju nobored thisColor">10天前</td>
                  </tr>
              </table>
              <h3>
                	<strong class="FL mgr5">店内等级：</strong>
                	<label class="FL ui-icon ui-icon-vip0 mgr5"></label> 
              	    <span class="FL mgr5">VIP会员</span><em class="FL mgr5 thisColor">8折</em>
                    <em class="FL">再消费</em><em class="FL thisColor">301</em><font class="FL">元即可升级为</font><span class="FL">高级VIP会员</span>
         	  </h3>
              <div class="clear"></div>
          </div>
		  <div class="index_tradeData_foot">
              <h4 class="thisColor"><span class="ui-icon"></span>该买家在淘宝全网的信息</h4>
              <p>
                  过去三个月全网消费次数<b class="thisColor">12</b>次，平均客单价: <b class="thisColor">122</b><br />
                  最常消费的类目是: <label>女装类</label>&nbsp;&nbsp;&nbsp;&nbsp;在<label>女鞋类</label>目中的消费金额最多哦
              </p>
          </div>
          
    </div>
    <!--咨询记录-->
    <div class="ui-accordion" id="zxunjilu">
    	 <h3 class="ui-accordion-header"><span></span><a>咨询记录</a>
         	<p class="xsdqjl"><input type="checkbox" id="xsdqjl"/><label>只显示卖家提醒</label></p>
         </h3>
         <div class="ui-accordion-content">
         	<div id="zhixunList" class="zixunList">
                    <div class="navlist">
                        <ul class="list">
                            <li class="curr first"><a href="#"><img src="${static_resource_host}/resource/css/themes/classic/images/test.gif" /></a>
                                <div class="zixun_tips ui-icon thisColor">当前咨询</div>
                            </li>
                            <li><a href="javascript:void(0)"><img src="resource/css/themes/classic/images/test.gif" /></a><div class="zixun_tips ui-icon thisColor">1小时前</div></li>
                            <li><a href="#"><img src="${static_resource_host}/resource/css/themes/classic/images/test.gif" /></a><div class="zixun_tips ui-icon thisColor">1天前</div></li>
                            <li><a href="#"><img src="${static_resource_host}/resource/css/themes/classic/images/test.gif" /></a></li>
                            <li><a href="#"><img src="${static_resource_host}/resource/css/themes/classic/images/test.gif" /></a></li>
                            <li><a href="#"><img src="${static_resource_host}/resource/css/themes/classic/images/test.gif" /></a></li>
                            <li><a href="#"><img src="${static_resource_host}/resource/css/themes/classic/images/test.gif" /></a></li>
                            <li><a href="#"><img src="${static_resource_host}/resource/css/themes/classic/images/test.gif" /></a></li>
                            <li><a href="#"><img src="${static_resource_host}/resource/css/themes/classic/images/test.gif" /></a></li>
                        </ul>
                    </div>
                    <p class="ui-icon preBtn"></p>
                    <p class="ui-icon nextBtn "></p>
                    <div class="zixunList_Con">
                    	 <div class="con" style="display:block">
                            <h3 class="cptit">
                               <span class="FL">包邮2011新款女冬装圣诞女孩韩版格子连帽牛角扣长款翻领新款女冬装圣诞女孩韩版格子连帽牛角扣长款翻领
                                呢子外套呢子外套<em class="ui-icon"></em></span>
                           </h3>
                           <div class="clear"></div>
                           <ul id="cp_parameter" class="cp_parameter">
                                <li>
                                	<span>尺码：</span>
                                        <a href="javascript:void(0)" class="xuanzhong"  >S</a>
                                        <a href="javascript:void(0)">M</a>
                                        <a href="javascript:void(0)">L</a>
                                </li>
                                <li>
                                	<span>颜色分类：</span>
                                        <a href="javascript:void(0)">黑灰</a>
                                        <a href="javascript:void(0)" class="xuanzhong">大红</a>
                                        <a href="javascript:void(0)">玫红</a>
                                </li>
                                <li>
                                	<span>价格：</span><strong class="FL">27.00</strong><label>|</label>
                                    <span class="FL">促销:</span><span class="icon"></span>
                                    <b class="thisColor FL">23.00</b><label>|</label>库存: <b>40</b>件
                                </li>
                           </ul>
                           <div class="clear"></div>
                           <div class="youhui_tips">
                            	<label class="ui-icon"></label>
                                <ul><li>当前消费金额接近：满<b>200</b>包邮的活动</li><li>通用特惠:收藏减<b>1</b>元</li></ul></div>
                           <div class="clear"></div> 
                        </div> 
                        <div class="con">2</div>
                        <div class="con">3</div>
                        <div class="con">4</div>
                        <div class="con">5</div>
                        <div class="con">6</div>
                        <div class="con">7</div>
                        <div class="con">8</div>
                        <div class="con">9</div>
                    </div>
                </div>
         </div>
   </div>
   <!--买家最近搜索的关键字-->
   <div class="ui-accordion" id="Behavior_consultation">
    	 <h3 class="ui-accordion-header"><span></span><a>买家最近搜索的关键字</a></h3>
         <div class="ui-accordion-content" > 
                <div class="tips_wujilu">当前暂无记录</div>
                <div class="tips_loading">查询中...</div>
                <p class="ui-keywords">
                    <span>iphone</span><span>后背保护贴</span><span>iphone4</span><span>手机套</span><span>手机保护</span><span>后背保护贴</span><span>真皮</span><span>牛皮</span><span>iphone真皮保护套</span><span>黑色</span>
                </p>
                <div class="clear"></div>
         </div>
   </div>
   <!--数据猜测-->
   <div class="ui-tab" id="behavior_item_relation">      		
        <ul class="ui-tab-ul">
            <li class="ui-tab-ul-li-open"><span></span><a>猜他喜欢</a></li>
            <li><span></span><a>曾经浏览过的产品</a></li>
            <li><span></span><a>曾经购买过的产品</a></li>
            <span class="clear" style="_clear:none"></span>
        </ul>
        <div class="ui-tab-con">
        	<div class="behavior_item_list">
                <img src="${static_resource_host}/resource/css/themes/classic/images/test.gif" />
                <ul>
                    <li><div>包邮2011新款女冬装圣诞女孩韩版格子连帽牛角扣长款翻领呢子外套<div></li>
                    <li><font class="FL">价格: </font><span class="FL">27.00</span><em class="FL">|</em><font class="FL">促销:</font><label class="FL">VIP折扣价</label><font class="thisColor FL">￥23.00</font> </li>
                    <li>库存: <b class="thisColor">40</b>件</li>
                    <li><div class="ui-icon ui-wsc"></div><strong class="thisColor">最近2天浏览6次</strong></li>
                </ul>
                <div class="fabu_btn FR"></div>
                <div class="clear"></div>
            </div>
            <div class="behavior_item_list ">
                <img src="${static_resource_host}/resource/css/themes/classic/images/test.gif" />
                <ul>
                    <li><div>包邮2011新款女冬装圣诞女孩韩版格子连帽牛角扣长款翻领呢子外套<div></li>
                    <li><font class="FL">价格: </font><span class="FL">27.00</span><em class="FL">|</em><font class="FL">促销:</font><label class="FL">VIP折扣价</label><font class="thisColor FL">￥23.00</font> </li>
                    <li>库存: <b class="thisColor">40</b>件</li>
                    <li><div class="ui-icon ui-ysc"></div><strong class="thisColor">最近2天浏览6次</strong></li>
                </ul>
                <div class="fabu_btn FR"></div>
                <div class="clear"></div>
            </div>
            <div class="behavior_item_list">
                <img src="${static_resource_host}/resource/css/themes/classic/images/test.gif" />
                <ul>
                    <li><div>包邮2011新款女冬装圣诞女孩韩版格子连帽牛角扣长款翻领呢子外套<div></li>
                    <li><font class="FL">价格: </font><span class="FL">27.00</span><em class="FL">|</em><font class="FL">促销:</font><label class="FL">VIP折扣价</label><font class="thisColor FL">￥23.00</font> </li>
                    <li>库存: <b class="thisColor">40</b>件</li>
                    <li><div class="ui-icon ui-wsc"></div><strong class="thisColor">最近2天浏览6次</strong></li>
                </ul>
                <div class="fabu_btn FR"></div>
                <div class="clear"></div>
            </div>
        </div>
        <div class="ui-tab-con">
        	<div class="behavior_item_list">
                <img src="${static_resource_host}/resource/css/themes/classic/images/test.gif" />
                <ul>
                    <li><div>包邮2011新款女冬装圣诞女孩韩版格子连帽牛角扣长款翻领呢子外套<div></li>
                    <li><font class="FL">价格: </font><span class="FL">27.00</span><em class="FL">|</em><font class="FL">促销:</font><label class="FL">VIP折扣价</label><font class="thisColor FL">￥23.00</font> </li>
                    <li>库存: <b class="thisColor">40</b>件</li>
                    <li><div class="ui-icon ui-wsc"></div><strong class="thisColor">最近2天浏览6次</strong></li>
                </ul>
                <div class="fabu_btn FR"></div>
                <div class="clear"></div>
            </div>
            <div class="behavior_item_list ">
                <img src="${static_resource_host}/resource/css/themes/classic/images/test.gif" />
                <ul>
                    <li><div>包邮2011新款女冬装圣诞女孩韩版格子连帽牛角扣长款翻领呢子外套<div></li>
                    <li><font class="FL">价格: </font><span class="FL">27.00</span><em class="FL">|</em><font class="FL">促销:</font><label class="FL">VIP折扣价</label><font class="thisColor FL">￥23.00</font> </li>
                    <li>库存: <b class="thisColor">40</b>件</li>
                    <li><div class="ui-icon ui-ysc"></div><strong class="thisColor">最近2天浏览6次</strong></li>
                </ul>
                <div class="fabu_btn FR"></div>
                <div class="clear"></div>
            </div>
            <div class="behavior_item_list">
                <img src="${static_resource_host}/resource/css/themes/classic/images/test.gif" />
                <ul>
                    <li><div>包邮2011新款女冬装圣诞女孩韩版格子连帽牛角扣长款翻领呢子外套<div></li>
                    <li><font class="FL">价格: </font><span class="FL">27.00</span><em class="FL">|</em><font class="FL">促销:</font><label class="FL">VIP折扣价</label><font class="thisColor FL">￥23.00</font> </li>
                    <li>库存: <b class="thisColor">40</b>件</li>
                    <li><div class="ui-icon ui-wsc"></div><strong class="thisColor">最近2天浏览6次</strong></li>
                </ul>
                <div class="fabu_btn FR"></div>
                <div class="clear"></div>
            </div>
        </div>
        <div class="ui-tab-con">
        	<div class="behavior_item_list">
                <img src="${static_resource_host}/resource/css/themes/classic/images/test.gif" />
                <ul>
                    <li><div>包邮2011新款女冬装圣诞女孩韩版格子连帽牛角扣长款翻领呢子外套<div></li>
                    <li><font class="FL">价格: </font><span class="FL">27.00</span><em class="FL">|</em><font class="FL">促销:</font><label class="FL">VIP折扣价</label><font class="thisColor FL">￥23.00</font> </li>
                    <li>库存: <b class="thisColor">40</b>件</li>
                    <li><div class="ui-icon ui-wsc"></div><strong class="thisColor">最近2天浏览6次</strong></li>
                </ul>
                <div class="fabu_btn FR"></div>
                <div class="clear"></div>
            </div>
            <div class="behavior_item_list ">
                <img src="${static_resource_host}/resource/css/themes/classic/images/test.gif" />
                <ul>
                    <li><div>包邮2011新款女冬装圣诞女孩韩版格子连帽牛角扣长款翻领呢子外套<div></li>
                    <li><font class="FL">价格: </font><span class="FL">27.00</span><em class="FL">|</em><font class="FL">促销:</font><label class="FL">VIP折扣价</label><font class="thisColor FL">￥23.00</font> </li>
                    <li>库存: <b class="thisColor">40</b>件</li>
                    <li><div class="ui-icon ui-ysc"></div><strong class="thisColor">最近2天浏览6次</strong></li>
                </ul>
                <div class="fabu_btn FR"></div>
                <div class="clear"></div>
            </div>
            <div class="behavior_item_list">
                <img src="${static_resource_host}/resource/css/themes/classic/images/test.gif" />
                <ul>
                    <li><div>包邮2011新款女冬装圣诞女孩韩版格子连帽牛角扣长款翻领呢子外套<div></li>
                    <li><font class="FL">价格: </font><span class="FL">27.00</span><em class="FL">|</em><font class="FL">促销:</font><label class="FL">VIP折扣价</label><font class="thisColor FL">￥23.00</font> </li>
                    <li>库存: <b class="thisColor">40</b>件</li>
                    <li><div class="ui-icon ui-wsc"></div><strong class="thisColor">最近2天浏览6次</strong></li>
                </ul>
                <div class="fabu_btn FR"></div>
                <div class="clear"></div>
            </div>
        </div>
        <div class="ui-pager">
        	 <ul>
                <li class="ui-pager-step">第1页</li>
                <li class="ui-pager-count">共10页</li>
                <li class="ui-pager-firstPage-disable"></li>
                <li class="ui-pager-prePage-disable"></li>
                <li class="ui-pager-nextPage"></li>
                <li class="ui-pager-lastPage"></li>
                <div class="clear"></div>
            </ul>
            <div class="clear"></div>
        </div>
   </div>
</div>
<!--end主体wrap-->
<!---nav底部部导航----->
<jsp:include page="common/footer.jsp"/>
</body>
</html>
<script type="text/javascript">
	$(function(){
		 $("#xsdqjl").click(function(event){event.stopPropagation()});
		 $("#zhixunList").tabChangeAuto({'lightedClass':'curr','listConClass':'zixunList_Con'});//在询商品滚动展示
		 //在询商品参数选中效果
		 $("ul#cp_parameter li a").click(function(){
			$(this).addClass('xuanzhong').siblings('a').removeClass('xuanzhong');
		 });
         $("#Behavior_consultation").accordion({"extend":true});
		 $("#zxunjilu").accordion({"extend":true});
		 $("#behavior_item_relation").tabChange();
		
		 $(".ui-tab-con").find("div.behavior_item_list").hover(function(){ 
				$(this).addClass("line_gray").find("div.fabu_btn").show();
			},function(){
				$(this).removeClass("line_gray").find("div.fabu_btn").hide();
			});
	  })
 	 
</script>
<!--[if IE 6]>
<script type="text/javascript" src="resource/script/DD_belatedPNG.js"></script>
<script type="text/javascript">
DD_belatedPNG.fix('.ui-tips-left , .ui-tips-center , .ui-tips-right , .ui-icon');
</script>
<![endif]-->