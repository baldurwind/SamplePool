<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<!--猜ta喜欢-->
<div id="behavior_item_relation" class="ui-tab">
      <ul class="ui-tab-ul">
              <li class="ui-tab-ul-li-open"  onclick="itemrelation()"><span></span><a>猜他喜欢</a></li>
            <li onclick="visitinghistory()"><span></span><a>曾经浏览过的产品</a></li> 
            <li   onclick="boughtitem()"><span></span><a>曾经购买过的产品</a></li>
            <span class="clear" style="_clear:none"></span>
        </ul>
    <div class="ui-tab-con" id="item_module">
    </div>
</div>
<script>
$(document).ready(function(){
	itemrelation();
});


</script>

