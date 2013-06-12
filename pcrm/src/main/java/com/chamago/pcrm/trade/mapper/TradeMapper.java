package com.chamago.pcrm.trade.mapper;

import java.util.List;
import java.util.Map;

public interface TradeMapper {

	
	public List<Object[]> findTradeBySellerBuyer(String seller,String buyer,List tradeStatus);
	
	public  Object[] findTradeByTid(Long tidList);
	
	public List<Object[]> findOrderByTidList(List  oidList);
	
	public List<Object []> findOrderByTid(String tid);
	
	public List<Long> findRefundNum(String seller,String buyer);
	
	public List<Long> findWaitingSendGoodsTradeId(String seller,String buyer);
	
	public List<Object[]> findPerformanceParamsBySeller(Map<String,Object> params);
	
	
	public int batchUpdateSellerFlag(String flag,List<Long> tids);
	public int updateSellerFlag(String flag,Long tids);
	

	public int updateTradeStatus(Long tid,String status);
	
	public int updateOrderStatus(Long tid,String status);
	
	public int updatePostage(Long tid,String postage);
	
	public int batchUpdatePostage(List<Long> tid,String postage);
	
	public int batchUpdateTradeStatus(List<Long> tids,String status);
	
	public int batchUpdateOrderStatus(List<Long> tids,String status);
	
	public List<Object[]> findTradeByTids(List tids);
}
