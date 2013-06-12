package com.chamago.pcrm.trade.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.chamago.pcrm.common.utils.Utils;
import com.chamago.pcrm.trade.mapper.TradeMapper;
import com.chamago.pcrm.trade.service.TradeService;

@Service
public class TradeServiceImpl  implements TradeService {

	@Autowired
	private TradeMapper tradeMapper;
	
	public List<Object[]> findOrderByTidList(List tidList) {
		return tradeMapper.findOrderByTidList(tidList);
	}
	
	public List<Object[]> findTradeBySellerBuyer(String seller, String buyer,List tradeStatus) {
		return tradeMapper.findTradeBySellerBuyer(seller, buyer,tradeStatus);
	}
	
	public List<Object []> findOrderByTid(String tid) {
		return tradeMapper.findOrderByTid(tid);
	}
	
	public Object[] findTradeByTid(Long tid){
		return tradeMapper.findTradeByTid(tid);
	}
	public List<Long> findWaitingSendGoodsTradeId(String seller,String buyer){
		return tradeMapper.findWaitingSendGoodsTradeId(seller,buyer);
	}
	public List<Long> findRefundNum(String seller,String buyer){
		return tradeMapper.findRefundNum(seller,buyer);
	}
	
	
	public int updatePostage(Long tid,String postage){
		return tradeMapper.updatePostage(tid,postage);
	}
	public int updateTradeStatus(Long tid,String status){
		return tradeMapper.updateTradeStatus(tid, status);
	}
	public int updateOrderStatus(Long tid,String status){
		return tradeMapper.updateOrderStatus(tid, status);
	}
	
	public int batchUpdatePostage(List<Long> tid,String postage){
		return tradeMapper.batchUpdatePostage(tid,postage);
	}
	public int batchUpdateTradeStatus(List<Long> tids,String status){
		return tradeMapper.batchUpdateTradeStatus(tids,status);
	}
	public int batchUpdateOrderStatus(List<Long> tids,String status){
		return tradeMapper.batchUpdateOrderStatus(tids,status);
	}

	public int batchUpdateSellerFlag(String flag, List<Long> tids) {
		return tradeMapper.batchUpdateSellerFlag(flag,tids);
	}

	public int updateSellerFlag(String flag, Long tids) {
		return tradeMapper.updateSellerFlag(flag,tids);
	}
	
	
	public List<Object[]> findTradeByTids(List tids){
		return tradeMapper.findTradeByTids(tids);
	}
	
	
	
}
