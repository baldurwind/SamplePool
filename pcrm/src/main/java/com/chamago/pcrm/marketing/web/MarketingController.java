package com.chamago.pcrm.marketing.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chamago.pcrm.common.utils.AopLogModule;
import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.common.utils.Utils;
import com.chamago.pcrm.crm.pojo.CrmItem;
import com.chamago.pcrm.marketing.pojo.LimitDiscount;
import com.chamago.pcrm.marketing.pojo.LimitDiscountDetail;
import com.chamago.pcrm.marketing.pojo.Meal;
import com.chamago.pcrm.marketing.service.MarketingService;
import com.chamago.pcrm.taobao.top.service.TopService;
import com.taobao.api.domain.PromotionInItem;
import com.taobao.api.domain.PromotionInShop;
import com.taobao.api.response.UmpPromotionGetResponse;

/**
 * 优惠促销Controller
 * @author James.wang
 */
@Controller
@RequestMapping("/marketing")
public class MarketingController {
	
	
	
	@RequestMapping
	@AopLogModule(name=C.LOG_MODULE_MARKETING,layer=C.LOG_LAYER_CONTROLLER)
	public String marketing(){
		return "/marketing/index";
	}
	
	
	@RequestMapping("/onsale")
	@AopLogModule(name=C.LOG_MODULE_MARKETING,layer=C.LOG_LAYER_CONTROLLER)
	public String onsale(){
		return "/marketing/onsale";
	}
	
	@RequestMapping("/promotion")
	@AopLogModule(name=C.LOG_MODULE_MARKETING,layer=C.LOG_LAYER_CONTROLLER)
	public String promotion(){
		return "/marketing/promotion";
	}
	
	/**
	 * 加载店铺搭配套餐
	 */
	@RequestMapping("/meal")
	@AopLogModule(name=C.LOG_MODULE_MARKETING,layer=C.LOG_LAYER_CONTROLLER)
	public String meal( HttpServletRequest request, @RequestParam String seller) {
		List<Meal>  results=marketingService.findMeal(seller);
		PagedListHolder<Meal> pagination   =new PagedListHolder<Meal>(results);
		int	currentPage=0;
		try {
			currentPage= Integer.valueOf(request.getParameter("current_page_meal"));
		} catch (NumberFormatException e) {
		}
		pagination.setPage(currentPage);
		Utils.pagination(C.PAGINATION_SIZE,pagination, request);
		request.setAttribute("current_page_meal",pagination.getPage());
		request.setAttribute("meals",pagination);
		return "/marketing/meal/meal_index";
	}
	/**
	 * 加载限时打折信息
	 */
	@RequestMapping("/limitdiscount")
	@AopLogModule(name=C.LOG_MODULE_MARKETING,layer=C.LOG_LAYER_CONTROLLER)
	public String limitdiscount(HttpServletRequest request, @RequestParam String seller) {
		List<LimitDiscount>  results=marketingService.findLimitDiscount(seller);
		PagedListHolder<LimitDiscount>	pagination=new PagedListHolder<LimitDiscount>(results);
		int	currentPage =0;
		try {
			 currentPage = Integer.valueOf(request.getParameter("current_page_limitdiscount"));
		} catch (NumberFormatException e) {}
		pagination.setPage(currentPage);			
			
			
		Utils.pagination(C.PAGINATION_SIZE,pagination, request);
		request.setAttribute("current_page_limitdiscount",pagination.getPage());
		request.setAttribute("limitDiscounts",pagination);
		
		return "/marketing/limitdiscount/limitdiscount_index";
	}
	/**
	 * 获取限时打折详细
	 */
	@RequestMapping("/limitdiscount/detail")
	@AopLogModule(name=C.LOG_MODULE_MARKETING,layer=C.LOG_LAYER_CONTROLLER)
	public String limitdiscount( HttpServletRequest request, @RequestParam Long id){
		List<LimitDiscountDetail> lddList = marketingService.findLimitDiscountDetail(id);
			request.setAttribute("limitdiscounts_detail", lddList);
		return "/marketing/limitdiscount/limitdiscount_detail";
	}
	/**
	 * 获取套餐详细
	 */
	@RequestMapping(value="/meal/detail")
	@AopLogModule(name=C.LOG_MODULE_MARKETING,layer=C.LOG_LAYER_CONTROLLER)
	public String mealDetail(HttpServletRequest request, @RequestParam Long id) {
		List<CrmItem> crmItemList = marketingService.findMealDetail(id);
		request.setAttribute("meals_detail", crmItemList);
		
		return "/marketing/meal/meal_detail";
	}
	
	/**
	 * 跳转至优惠券首页
	 */
	@RequestMapping("/coupon/index")
	@AopLogModule(name=C.LOG_MODULE_MARKETING,layer=C.LOG_LAYER_CONTROLLER)
	public String toCouponIndex(ModelMap map, HttpServletRequest request) {
		
		return "/marketing/coupon/index";
	}
	/**
	 * 加载买家优惠券信息
	 */
	@RequestMapping(value="/coupon/buyer")
	@AopLogModule(name=C.LOG_MODULE_MARKETING,layer=C.LOG_LAYER_CONTROLLER)
	public String findBuyerCoupon(ModelMap map, HttpServletRequest request) {
		PagedListHolder<Object[]> pageList = null;
		List<Object[]> result = null;
		try {
			result = marketingService.findBuyerCoupon(request.getParameter("sellernick"), request.getParameter("buyernick"));
		} catch(Exception e) {
			result = new ArrayList<Object[]>();
		}
		int pageNo = 0;
		if(request.getParameter("page") != null) {pageNo = Integer.parseInt(request.getParameter("page").toString());
			if(pageNo < 0) {pageNo = 0;}
		}
		pageList = new PagedListHolder<Object[]>(result);
		pageList.setPageSize(4); 
		pageList.setPage(pageNo);
		if(pageList != null && pageList.getPageList().size() > 0) {
			map.put("coupon_buyer", pageList);
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		map.put("currentTime", format.format(new Date()));
		return "/marketing/coupon/buyer";
	}
	/**
	 * 加载店铺优惠卷信息
	 */
	@RequestMapping(value="/coupon/seller")
	@AopLogModule(name=C.LOG_MODULE_MARKETING,layer=C.LOG_LAYER_CONTROLLER)
	public String findSellerCoupon(ModelMap map, HttpServletRequest request,@RequestParam String sellernick) {
		List<Object[]> result = null;
		try {
			result = marketingService.findSellerCoupon(sellernick);
		} catch(Exception e) {
			result = new ArrayList<Object[]>();
		}
		if(result != null && result.size() > 0) {
			map.put("coupon_seller", result);
		}
		return "/marketing/coupon/seller";
	}
	
	@RequestMapping(value="/ump/promotion")
	@AopLogModule(name=C.LOG_MODULE_MARKETING,layer=C.LOG_LAYER_CONTROLLER)
	public   String getUmpPromotionGet(HttpServletRequest request,String seller,Long numiid) {
		try {
			UmpPromotionGetResponse res=topService.getUmpPromotionGet(seller, numiid);
			if(res.isSuccess()){
				if(res.getPromotions().getPromotionInShop()!=null){
					List<PromotionInShop> list=res.getPromotions().getPromotionInShop();
					List<String>  shops=new ArrayList<String>();
					for(PromotionInShop obj:list){
						if(StringUtils.isNotEmpty(obj.getPromotionDetailDesc()))
							shops.add(obj.getPromotionDetailDesc());
					}
					request.setAttribute("shops", shops);
				}
				if(res.getPromotions().getPromotionInItem()!=null){
					List<PromotionInItem> list=res.getPromotions().getPromotionInItem();
					List<String>  items=new ArrayList<String>();
					for(PromotionInItem obj:list){
						if(StringUtils.isNotEmpty(obj.getDesc()))
							items.add(obj.getDesc());
					}
					request.setAttribute("items", items);
				}
			}
			else
				request.setAttribute("msg", res.getSubMsg());
			
			request.setAttribute("msg","good");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/marketing/ump_promotion";
	}
	
	
	@Autowired
	private TopService topService;

	@Autowired
	private MarketingService  marketingService;
}