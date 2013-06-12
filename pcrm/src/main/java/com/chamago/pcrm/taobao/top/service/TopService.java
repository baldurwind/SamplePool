package com.chamago.pcrm.taobao.top.service;

import java.util.List;

import com.taobao.api.ApiException;
import com.taobao.api.domain.BasicMember;
import com.taobao.api.domain.Coupon;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Meal;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.Shipping;
import com.taobao.api.domain.Sku;
import com.taobao.api.domain.Trade;
import com.taobao.api.domain.TransitStepInfo;
import com.taobao.api.response.CrmMembersGetResponse;
import com.taobao.api.response.LogisticsOrdersDetailGetResponse;
import com.taobao.api.response.LogisticsTraceSearchResponse;
import com.taobao.api.response.RefundsApplyGetResponse;
import com.taobao.api.response.TradeCloseResponse;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.taobao.api.response.TradeMemoUpdateResponse;
import com.taobao.api.response.TradePostageUpdateResponse;
import com.taobao.api.response.UmpPromotionGetResponse;
import com.taobao.api.response.UserGetResponse;

public interface TopService {

	
	public  TradeFullinfoGetResponse  getTradeFullInfo(String seller,Long tid)throws ApiException;
	
	public  Trade  getTrade(Long tid,String sellernick)throws  ApiException;
	
	public  Trade  getTradesSold(String buyer,String seller)throws  ApiException;
	
	public  LogisticsTraceSearchResponse  getLogisticTrace(String seller,Long tid)throws ApiException;
	
	public  Boolean updateShippingAddress (String sellernick,Long tid,String receiverCity,String receiverDistrict,String receiverAddress,String receiverMobile,String receiverName,String receiverPhone,String receiverState,String receiverZip)throws ApiException;
	
	public  TradeCloseResponse  closeTrade(String sellernick,Long tid,String closeReason)throws ApiException;
	
	public  TradePostageUpdateResponse  updatePostage(String seller,Long tid,String fee) throws ApiException;
	
	public  TradeMemoUpdateResponse updateMemo(Long tid,Long flag,String memo,String seller)throws ApiException;
	
	public Order findOrderByNumiidInTrade(Trade trade,Long numiid)throws  ApiException;
	
	public LogisticsOrdersDetailGetResponse getLogisticDetail(String seller,Long tid)throws ApiException;
	
	public  Item getItem(Long numiid,String sellernick)throws  ApiException;
	
	public Sku getSku(String seller,Long numiid, Long skuid)throws ApiException;
	
	public  List<Coupon>   getPromotionCoupon(Long coupon_id,String seller)throws  ApiException;
	
	public  List<Meal>  getMeal(String seller)throws  ApiException;
	
	public  CrmMembersGetResponse   getCrmMembers (String sellerNick,String buyerNick) throws ApiException ;	
	
	public RefundsApplyGetResponse getBuyerRefundsCount(String sellerNick, String buyerNick) throws ApiException;
	
	public UserGetResponse getUserInfo(String nick)throws ApiException;
	
	public UmpPromotionGetResponse  getUmpPromotionGet(String seller,Long numiid) throws ApiException;
	
}