package com.chamago.pcrm.trade.mapper.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.common.utils.MemCached;
import com.chamago.pcrm.common.utils.Utils;
import com.chamago.pcrm.trade.mapper.TradeMapper;


public class TradeMapperImpl extends SqlSessionDaoSupport implements TradeMapper{
	
	@SuppressWarnings("unchecked")
	public List<Object[]> findOrderByTidList(List  tidList) {
		return    getSqlSession().selectList("TradeMapper.findOrderByTidList", tidList);
	}


	@SuppressWarnings("unchecked")
	public List<Object[]> findTradeBySellerBuyer(String seller, String buyer,List tradeStatus) {
		Map<String,Object> map=new HashMap<String,Object>();
			map.put("seller", seller);
			map.put("buyer", buyer);
			map.put("tradeStatus", tradeStatus);
		return   getSqlSession().selectList("TradeMapper.findTradeBySellerBuyer", map);
	}
	
	public  Object[]  findTradeByTid(Long tid){
		return (Object[])getSqlSession().selectOne("TradeMapper.findTradeByTid",tid);
	}
	
	public List<Object[]> findOrderByTid(String tid) {
		return getSqlSession().selectList("TradeMapper.findOrderByTid", tid);
	}
	
	public List<Long> findRefundNum(String seller,String buyer){
		Map<String,String> map=new HashMap<String,String>();
		map.put("seller", seller);
		map.put("buyer", buyer);
		return  getSqlSession().selectList("TradeMapper.findRefundNum",map);
	}
	
	@MemCached
	public List<Long> findWaitingSendGoodsTradeId(String seller,String buyer){
		Map<String,String> map=new HashMap<String,String>();
		map.put("seller", seller);
		map.put("buyer", buyer);
		return  getSqlSession().selectList("TradeMapper.findWaitingSendGoodsTradeId",map);
	}

	public List<Object[]> findPerformanceParamsBySeller(Map<String,Object> params) {
		// TODO Auto-generated method stub
		Assert.notNull(params);
		return getSqlSession().selectList("TradeMapper.findPerformanceByDate", params);
	}
	public int updateTradeStatus(Long tid,String status){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("tid",tid);
		map.put("status", status);
		return  getSqlSession().update("TradeMapper.updateTradeStatus",map);
	}
	public int updateOrderStatus(Long tid,String status){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("tid",tid);
		map.put("status", status);
		return  getSqlSession().update("TradeMapper.updateOrderStatus",map);
	}

	@Override
	public int updatePostage(Long tid, String postage) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("tid",tid);
		map.put("postage", postage);
		return  getSqlSession().update("TradeMapper.updatePostage",map);
	}

	@Override
	public int batchUpdatePostage(List<Long> tids, String postage) {
		Map<String,Object> map=new HashMap<String,Object>();
			map.put("tids",tids);
			map.put("postage", postage);
		return  getSqlSession().update("TradeMapper.batchUpdatePostage",map);
	}

	@Override
	public int batchUpdateTradeStatus(List<Long> tids, String status) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("tids",tids);
		map.put("status", status);
		return  getSqlSession().update("TradeMapper.batchUpdateTradeStatus",map);
	}
	
	@Override
	public int batchUpdateOrderStatus(List<Long> tids, String status) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("tids",tids);
		map.put("status", status);
		return  getSqlSession().update("TradeMapper.batchUpdateOrderStatus",map);
	}


	public int batchUpdateSellerFlag(String flag, List<Long> tids) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("tids",tids);
		map.put("flag", flag);
		return  getSqlSession().update("TradeMapper.batchUpdateSellerFlag",map);
	}

	public int updateSellerFlag(String flag, Long tid) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("tid",tid);
		map.put("flag", flag);
		return  getSqlSession().update("TradeMapper.updateSellerFlag",map);
	}
	
	public List<Object[]> findTradeByTids(List tids){
		return  getSqlSession().selectList("TradeMapper.findTradeByTids",tids);
	}
	
	
}
