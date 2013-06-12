package com.chamago.pcrm.marketing.service;

import java.util.List;

import com.chamago.pcrm.crm.pojo.CrmItem;
import com.chamago.pcrm.marketing.pojo.LimitDiscount;
import com.chamago.pcrm.marketing.pojo.LimitDiscountDetail;
import com.chamago.pcrm.marketing.pojo.MActivity;
import com.chamago.pcrm.marketing.pojo.Meal;

/**
 *  优惠促销业务接口
 * @author James.wang
 */
public interface MarketingService {
	
	/**
	 * 查询当前店铺下所有优惠套餐信息
	 * @param sellernick　卖家昵称
	 * @return  店铺下的优惠套餐集合
	 */
	public List<Meal> findMeal(String sellernick);
	 
	/**
	 * 查询当前套餐详细信息
	 * @param mealid  套餐编号
	 * @return        套餐详细信息
	 */
	public List<CrmItem> findMealDetail(Long mealid); 
	 
	/**
	 * 获取店铺下的所有活动
	 * @param sellernick  卖家昵称
	 * @return            活动列表
	 */
	public List<MActivity> findActivity(String sellernick);
	 
	/**
	 * 获取店铺下所有限时打折优惠信息
	 * @param sellernick　卖家昵称
	 * @return            店铺下所有限时打折优惠信息列表
	 */
	public List<LimitDiscount> findLimitDiscount(String sellernick);
	 
	/**
	 * 查询当前限时打折详细信息
	 * @param id   限时打折编号
	 * @return     打折详细信息
	 */
	public List<LimitDiscountDetail> findLimitDiscountDetail(Long id);
	 
	/**
	 * 获取当前买家在当前店铺下所持有的优惠券信息
	 * @param sellernick  卖家昵称
	 * @param buyernick   买家昵称
	 * @return            优惠券信息列表
	 */
	List<Object[]> findBuyerCoupon(String sellernick,String buyernick);
	
	/**
	 * 查询当前店铺下的优惠券信息
	 * @param sellernick  卖家昵称
	 * @return            优惠券信息列表
	 */
	List<Object[]> findSellerCoupon(String sellernick);
	
	List<Long> findLimitDiscountItemIdForLucene();
}