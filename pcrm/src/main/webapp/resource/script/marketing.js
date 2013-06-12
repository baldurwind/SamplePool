function initMeal(){
	$('#marketing_meal').html("<div class='tips_loading'>查询中...</div>");
	$('#marketing_meal').load("marketing/meal?current_page_meal=0&seller="+getSeller());
}
function initLimitDiscount(){
	$('#marketing_limitdiscount').html("<div class='tips_loading'>查询中...</div>");
	$('#marketing_limitdiscount').load("marketing/limitdiscount?current_page_limitdiscount=0&seller="+getSeller());
}

function mealPage(tag){
	var cp=$('#current_page_meal').val();
	$('#marketing_meal').html("<div class='tips_loading'>查询中...</div>");
	$('#marketing_meal').load("marketing/meal?seller="+getSeller()+"&page="+tag+"&current_page_meal="+cp);
}

function limitDiscountPage(tag){
	var cp=$('#current_page_limitdiscount').val();
	$('#marketing_limitdiscount').html("<div class='tips_loading'>查询中...</div>");
	$('#marketing_limitdiscount').load("marketing/limitdiscount?seller="+getSeller()+"&page="+tag+"&current_page_limitdiscount="+cp);
}



function limitDiscountDetail(id){
	$('#limitdiscount_detail_module_'+id).html("<div class='tips_loading'>查询中...</div>");
	$('#limitdiscount_detail_module_'+id).load("marketing/limitdiscount/detail?id="+id);
}
function mealDetail(id){
	$('#meal_detail_module_'+id).html("<div class='tips_loading'>查询中...</div>");
	$('#meal_detail_module_'+id).load("marketing/meal/detail?id="+id);
}

