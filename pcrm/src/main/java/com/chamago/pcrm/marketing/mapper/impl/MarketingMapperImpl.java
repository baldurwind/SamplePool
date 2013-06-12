package com.chamago.pcrm.marketing.mapper.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.chamago.pcrm.common.pojo.PromotionLkp;
import com.chamago.pcrm.common.utils.MemCached;
import com.chamago.pcrm.common.utils.Utils;
import com.chamago.pcrm.marketing.mapper.MarketingMapper;
import com.chamago.pcrm.marketing.pojo.LimitDiscount;
import com.chamago.pcrm.marketing.pojo.LimitDiscountDetail;
import com.chamago.pcrm.marketing.pojo.MActivity;
import com.chamago.pcrm.marketing.pojo.Meal;

/**
 * 优惠促销数据访问实现
 * @author James.wang
 */
@SuppressWarnings("unchecked")
public class MarketingMapperImpl  extends SqlSessionDaoSupport implements MarketingMapper {

	/**
	 * 获取店内活动列表
	 * @param seller 店铺昵称
	 * @return  当前店内的活动列表
	 */
	@MemCached 
	public List<MActivity> findActivity(String seller) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("seller", seller);
		map.put("current", Utils.getCurrentDateTime());
		return getSqlSession().selectList("MarketingMapper.findActivity", map);
	}
	
	/**
	 * 获取店内套餐列表
	 * @param seller  店铺昵称
	 * @return 当前店内的套餐列表
	 */
	@MemCached
	public List<Meal> findMeal(String seller) {
		return getSqlSession().selectList("MarketingMapper.findMeal", seller);
	}
	
	/**
	 * 获取套餐详细
	 * @param mealid  套餐标识
	 * @return  获取当前套餐详细
	 */
	@MemCached 
	public List<String> findMealDetail(Long meal_id){
		return getSqlSession().selectList("MarketingMapper.findMealDetail", meal_id);
	}
	
	
	@MemCached 
	public List<PromotionLkp> findAllPromotion() {
		return getSqlSession().selectList("MarketingMapper.findAllPromotion");
	}
	
	/**
	 * 获取店内限时打折列表
	 * @param seller  店铺昵称
	 * @return 当前店内限时打折列表
	 */
	//@MemCached 
	public List<LimitDiscount> findLimitDiscount(String sellernick) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sellernick", sellernick);
		map.put("current",Utils.getCurrentDateTime());
		return getSqlSession().selectList("MarketingMapper.findLimitDiscount",map);
	}
	
	/**
	 * 获取限时打折详细
	 * @param id  限时打折标识
	 * @return    当前限时打折详细
	 */
	public List<LimitDiscountDetail> findLimitDiscountDetail(Long limitDiscountId){
		return getSqlSession().selectList("MarketingMapper.findLimitDiscountDetail",limitDiscountId);
	}
	
	/**
	 * 获取买家拥有店铺内优惠券的列表
	 * @param sellernick 　店铺昵称
	 * @param buyernick　　买家昵称
	 * @return 当前买家拥有店铺内优惠券的列表
	 */
	public List<Object[]> findBuyerCoupon(String sellernick,String buyernick){
		Map<String, String> map = new HashMap<String, String>();
		map.put("sellernick", sellernick);
		map.put("buyernick", buyernick);
		return getSqlSession().selectList("MarketingMapper.findBuyerCoupon", map);
	}
	
	/**
	 * 获取店内可用的优惠券
	 * @param sellernick  店铺昵称
	 * @param currentdate 当前时间
	 * @return  当前店内可用的优惠券
	 */
	@MemCached public List<Object[]> findSellerCoupon(String sellernick,String currentdate) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sellernick", sellernick);
		map.put("currentdate", currentdate);
		return getSqlSession().selectList("MarketingMapper.findSellerCoupon", map);
	}
	
	public List<Long> findLimitDiscountItemIdForLucene(){
		return getSqlSession().selectList("MarketingMapper.findLimitDiscountItemIdForLucene");
	}
}
