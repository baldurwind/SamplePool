package com.chamago.pcrm.marketing.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chamago.pcrm.crm.pojo.CrmItem;
import com.chamago.pcrm.item.mapper.ItemMapper;
import com.chamago.pcrm.marketing.mapper.MarketingMapper;
import com.chamago.pcrm.marketing.pojo.LimitDiscount;
import com.chamago.pcrm.marketing.pojo.LimitDiscountDetail;
import com.chamago.pcrm.marketing.pojo.MActivity;
import com.chamago.pcrm.marketing.pojo.Meal;
import com.chamago.pcrm.marketing.service.MarketingService;

/**
 *  优惠促销业务实现
 * @author James.wang
 */
@Service
public class MarketingServiceImpl implements MarketingService {

	@Autowired
	private MarketingMapper marketingMapper;
	
	@Autowired
	private ItemMapper itemMapper;
	
	/**
	 * 查询当前店铺下所有优惠套餐信息
	 * @param sellernick　卖家昵称
	 * @return  店铺下的优惠套餐集合
	 */
	public List<Meal> findMeal(String sellernick){
		return marketingMapper.findMeal(sellernick);
	}
	
	/**
	 * 查询当前套餐详细信息
	 * @param mealid  套餐编号
	 * @return        套餐详细信息
	 */
	public List<CrmItem> findMealDetail(Long id){
		List<String> numids = marketingMapper.findMealDetail(id);
		List<CrmItem> items = itemMapper.findItemsDetail(numids);
		return items;
	}
	
	/**
	 * 获取店铺下的所有活动
	 * @param sellernick  卖家昵称
	 * @return            活动列表
	 */
	public List<MActivity> findActivity(String sellernick){
		List<MActivity> list = marketingMapper.findActivity(sellernick);
		return list;
	}
	
	/**
	 * 获取店铺下所有限时打折优惠信息
	 * @param sellernick　卖家昵称
	 * @return            店铺下所有限时打折优惠信息列表
	 */
	public List<LimitDiscount> findLimitDiscount(String sellernick){
		List<LimitDiscount> list = marketingMapper.findLimitDiscount(sellernick);
		return list;
	}
	
	/**
	 * 查询当前限时打折详细信息
	 * @param id   限时打折编号
	 * @return     打折详细信息
	 */
	public List<LimitDiscountDetail> findLimitDiscountDetail(Long limitDiscountId){
		List<LimitDiscountDetail> list = marketingMapper.findLimitDiscountDetail(limitDiscountId);
		return list;
	}

	/**
	 * 获取当前买家在当前店铺下所持有的优惠券信息
	 * @param sellernick  卖家昵称
	 * @param buyernick   买家昵称
	 * @return            优惠券信息列表
	 */
	public List<Object[]> findBuyerCoupon(String sellernick, String buyernick) {
		return marketingMapper.findBuyerCoupon(sellernick, buyernick);
	}
	
	/**
	 * 查询当前店铺下的优惠券信息
	 * @param sellernick  卖家昵称
	 * @return            优惠券信息列表
	 */
	public List<Object[]> findSellerCoupon(String sellernick) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		return marketingMapper.findSellerCoupon(sellernick, sdf.format(new Date()));
	}
	public List<Long> findLimitDiscountItemIdForLucene(){
		return marketingMapper.findLimitDiscountItemIdForLucene();
	}
}