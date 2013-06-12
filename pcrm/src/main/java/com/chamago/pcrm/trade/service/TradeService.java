package com.chamago.pcrm.trade.service;

import java.util.List;

import com.chamago.pcrm.trade.mapper.TradeMapper;

public interface TradeService {
	
	public List<Object[]> findOrderByTidList(List tids) ;
	
	public List<Object[]> findTradeBySellerBuyer(String seller, String buyer,List tradeStatus) ;
	
	public Object[] findTradeByTid(Long tid);
	
	public List<Object []> findOrderByTid(String tid);
	
	public List<Long> findWaitingSendGoodsTradeId(String seller,String buyer);
	
	public List<Long> findRefundNum(String seller,String buyer);

	public int updateTradeStatus(Long tid,String status);
	
	public int updateOrderStatus(Long tid,String status);
	
	public int updatePostage(Long tid,String postage);
	
	public int batchUpdatePostage(List<Long> tid,String postage);
	
	public int batchUpdateTradeStatus(List<Long> tids,String status);
	
	public int batchUpdateOrderStatus(List<Long> tids,String status);
	public int batchUpdateSellerFlag(String flag,List<Long> tids);
	
	public int updateSellerFlag(String flag,Long tids);
	
	public List<Object[]> findTradeByTids(List tids);
	
}
