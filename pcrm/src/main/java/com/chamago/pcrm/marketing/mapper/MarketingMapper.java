package com.chamago.pcrm.marketing.mapper;

import java.util.List;

import com.chamago.pcrm.common.pojo.PromotionLkp;
import com.chamago.pcrm.marketing.pojo.LimitDiscount;
import com.chamago.pcrm.marketing.pojo.LimitDiscountDetail;
import com.chamago.pcrm.marketing.pojo.MActivity;
import com.chamago.pcrm.marketing.pojo.Meal;

/**
 * 优惠促销数据访问接口
 * @author James.wang
 */
public interface MarketingMapper {
	
	/**
	 * 获取店内套餐列表
	 * @param seller  店铺昵称
	 * @return 当前店内的套餐列表
	 */
	List<Meal> findMeal(String seller);
	
	/**
	 * 获取套餐详细
	 * @param mealid  套餐标识
	 * @return  获取当前套餐详细
	 */
	List<String> findMealDetail(Long mealid);
	
	/**
	 * 获取店内活动列表
	 * @param seller 店铺昵称
	 * @return  当前店内的活动列表
	 */
	List<MActivity> findActivity(String seller);
	
	List<PromotionLkp> findAllPromotion() ;
		
	/**
	 * 获取店内限时打折列表
	 * @param seller  店铺昵称
	 * @return 当前店内限时打折列表
	 */
	
	List<LimitDiscount> findLimitDiscount(String seller);
	
	/**
	 * 获取限时打折详细
	 * @param id  限时打折标识
	 * @return    当前限时打折详细
	 */
	List<LimitDiscountDetail> findLimitDiscountDetail(Long id);
	
	/**
	 * 获取店内可用的优惠券
	 * @param sellernick  店铺昵称
	 * @param currentdate 当前时间
	 * @return  当前店内可用的优惠券
	 */
	List<Object[]> findSellerCoupon(String sellernick,String currentdate);
	
	/**
	 * 获取买家拥有店铺内优惠券的列表
	 * @param sellernick 　店铺昵称
	 * @param buyernick　　买家昵称
	 * @return 当前买家拥有店铺内优惠券的列表
	 */
	List<Object[]> findBuyerCoupon(String sellernick,String buyernick);
	
	List<Long> findLimitDiscountItemIdForLucene();
}
