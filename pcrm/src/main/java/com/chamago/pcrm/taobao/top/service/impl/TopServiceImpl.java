package com.chamago.pcrm.taobao.top.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import 		com.taobao.api.request.UserGetRequest;
import com.chamago.pcrm.common.pojo.App;
import com.chamago.pcrm.common.pojo.Subscriber;
import com.chamago.pcrm.common.service.LkpService;
import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.common.utils.MemCached;
import com.chamago.pcrm.taobao.top.service.TopService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.TaobaoResponse;
import com.taobao.api.domain.Coupon;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Meal;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.Promotion;
import com.taobao.api.domain.Sku;
import com.taobao.api.domain.Trade;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.request.CrmMembersGetRequest;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.request.ItemSkuGetRequest;
import com.taobao.api.request.LogisticsOrdersDetailGetRequest;
import com.taobao.api.request.LogisticsTraceSearchRequest;
import com.taobao.api.request.MarketingPromotionsGetRequest;
import com.taobao.api.request.PromotionCouponsGetRequest;
import com.taobao.api.request.PromotionMealGetRequest;
import com.taobao.api.request.RefundsApplyGetRequest;
import com.taobao.api.request.TradeCloseRequest;
import com.taobao.api.request.TradeFullinfoGetRequest;
import com.taobao.api.request.TradeGetRequest;
import com.taobao.api.request.TradeMemoUpdateRequest;
import com.taobao.api.request.TradePostageUpdateRequest;
import com.taobao.api.request.TradeShippingaddressUpdateRequest;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.request.UmpPromotionGetRequest;
import com.taobao.api.request.UsersGetRequest;
import com.taobao.api.response.CrmMembersGetResponse;
import com.taobao.api.response.ItemGetResponse;
import com.taobao.api.response.ItemSkuGetResponse;
import com.taobao.api.response.LogisticsOrdersDetailGetResponse;
import com.taobao.api.response.LogisticsTraceSearchResponse;
import com.taobao.api.response.MarketingPromotionsGetResponse;
import com.taobao.api.response.PromotionCouponsGetResponse;
import com.taobao.api.response.PromotionMealGetResponse;
import com.taobao.api.response.RefundsApplyGetResponse;
import com.taobao.api.response.TradeCloseResponse;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.taobao.api.response.TradeGetResponse;
import com.taobao.api.response.TradeMemoUpdateResponse;
import com.taobao.api.response.TradePostageUpdateResponse;
import com.taobao.api.response.TradeShippingaddressUpdateResponse;
import com.taobao.api.response.TradesSoldGetResponse;
import com.taobao.api.response.UmpPromotionGetResponse;
import com.taobao.api.response.UserGetResponse;
@Service
public class TopServiceImpl implements TopService {
	
	@Autowired
	private   LkpService lkp;
	
	
	public  Item getItem (Long numiid,String sellernick) throws  ApiException {
		App  app=lkp.getApps().get(C.PCRM_APPKEY);
		Subscriber sub=lkp.getSubscribers().get(C.PCRM_APPKEY+"_"+sellernick);
		TaobaoClient client=new DefaultTaobaoClient(C.TOP_SERVER_URL, app.getAppKey(), app.getAppSecret());
		
		ItemGetRequest req=new ItemGetRequest();
			req.setFields("nick,num_iid,price,detail_url,title,pic_url,freight_payer,num,product_id");
			req.setNumIid(numiid);
		ItemGetResponse response = client.execute(req , sub.getSessionKey());
		
		throwApiExceptionIfError(response);
		return response.getItem();
	}
	
	
	@Override
	public UserGetResponse getUserInfo(String nick) throws ApiException {
		App  app=lkp.getApps().get(C.PCRM_APPKEY);
		TaobaoClient client=new DefaultTaobaoClient(C.TOP_SERVER_URL, app.getAppKey(), app.getAppSecret());
		
		UserGetRequest req=new  UserGetRequest();
		req.setFields("user_id");
		req.setNick(nick);
		return 	client.execute(req );
	}




	//@MemCached(expiredmins="1440")
	public  CrmMembersGetResponse  getCrmMembers(String sellerNick,String buyerNick) throws ApiException{
		App  app=lkp.getApps().get(C.PCRM_APPKEY);
		
		Subscriber sub=lkp.getSubscribers().get(C.PCRM_APPKEY+"_"+sellerNick);
		if(sub==null){
			CrmMembersGetResponse response =new CrmMembersGetResponse();
			response.setErrorCode("no found seller in db");
			return response;
		}
		
		
		TaobaoClient client=new DefaultTaobaoClient(C.TOP_SERVER_URL, app.getAppKey(),app.getAppSecret());
		CrmMembersGetRequest req=new CrmMembersGetRequest();
			req.setCurrentPage(1L);
			req.setBuyerNick(buyerNick);
			
			 
		return client.execute(req , sub.getSessionKey());
	}
	@MemCached 
	public  Trade getTrade (Long tid,String sellernick) throws  ApiException{
		App  app=lkp.getApps().get(C.PCRM_APPKEY);
		Subscriber sub=lkp.getSubscribers().get(C.PCRM_APPKEY+"_"+sellernick);
		TaobaoClient client=new DefaultTaobaoClient(C.TOP_SERVER_URL, app.getAppKey(),app.getAppSecret());
		TradeGetRequest req=new TradeGetRequest();
				req.setFields("orders.num_iid");
				req.setTid(tid);
		TradeGetResponse response = client.execute(req , sub.getSessionKey());
		throwApiExceptionIfError(response);
	
		return response.getTrade();
	}
	
	@MemCached(expiredmins="1")
	public Trade getTradesSold(String buyernick,String seller) throws  ApiException  {
		App  app=lkp.getApps().get(C.PCRM_APPKEY);
		Subscriber sub=lkp.getSubscribers().get(C.PCRM_APPKEY+"_"+seller);
		TaobaoClient client=new DefaultTaobaoClient(C.TOP_SERVER_URL, app.getAppKey(),app.getAppSecret());
		TradesSoldGetRequest req=new TradesSoldGetRequest();//tid,status,modified
				//req.setFields("seller_nick,buyer_nick,title,type,created,sid,tid,seller_rate,buyer_rate,status,payment,discount_fee,adjust_fee,post_fee,total_fee,pay_time,end_time,modified,consign_time,buyer_obtain_point_fee,point_fee,real_point_fee,received_payment,commission_fee,pic_path,num_iid,num_iid,num,price,cod_fee,cod_status,shipping_type,receiver_name,receiver_state,receiver_city,receiver_district,receiver_address,receiver_zip,receiver_mobile,receiver_phone,orders.title,orders.pic_path,orders.price,orders.num,orders.iid,orders.num_iid,orders.sku_id,orders.refund_status,orders.status,orders.oid,orders.total_fee,orders.payment,orders.discount_fee,orders.adjust_fee,orders.sku_properties_name,orders.item_meal_name,orders.buyer_rate,orders.seller_rate,orders.outer_iid,orders.outer_sku_id,orders.refund_id,orders.seller_type");
			req.setFields("modified,status,tid,orders");
			req.setBuyerNick(buyernick);
			
		TradesSoldGetResponse response = client.execute(req , sub.getSessionKey());
			throwApiExceptionIfError(response);
		Collections.sort(response.getTrades(), new Comparator<Trade>(){
				public int compare(Trade a,Trade b){
					Trade x=(Trade)a;
					Trade y=(Trade)b;
					return	x.getModified().before(y.getModified())?-1:1;
				}
		});
		return response.getTrades().get(0);
	}
	
	public Order findOrderByNumiidInTrade(Trade trade,Long numiid)throws  ApiException   {
		for(Order order:trade.getOrders()){
			if(order.getNumIid()!=numiid)
				return order;
		}
		return null;
	}
	
	public List<Coupon> getPromotionCoupon(Long coupon_id,String sellernick)throws  ApiException{
		App  app=lkp.getApps().get(C.PCRM_APPKEY);
		Subscriber sub=lkp.getSubscribers().get(C.PCRM_APPKEY+"_"+sellernick);
		TaobaoClient client=new DefaultTaobaoClient(C.TOP_SERVER_URL, app.getAppKey(),app.getAppSecret());
		PromotionCouponsGetRequest req=new PromotionCouponsGetRequest();
			req.setCouponId(coupon_id);
		PromotionCouponsGetResponse response = client.execute(req , sub.getSessionKey());
			
		
		throwApiExceptionIfError(response);
		return response.getCoupons();
	}
	
	//req.setMealId(1972617L);
	public List<Meal> getMeal(String sellernick)  throws  ApiException {
		App  app=lkp.getApps().get(C.PCRM_APPKEY);
		Subscriber sub=lkp.getSubscribers().get(C.PCRM_APPKEY+"_"+sellernick);
		TaobaoClient client=new DefaultTaobaoClient(C.TOP_SERVER_URL, app.getAppKey(),app.getAppSecret());
		PromotionMealGetRequest req=new PromotionMealGetRequest();
		PromotionMealGetResponse response = client.execute(req , sub.getSessionKey());
		throwApiExceptionIfError(response);		
		
		return response.getMealList();
	}
	
	@MemCached(expiredmins="300")
	public List<Promotion> getPromotions(Long numiid,String sellernick)throws ApiException{
		App  app=lkp.getApps().get(C.PCRM_APPKEY);
		Subscriber sub=lkp.getSubscribers().get(C.PCRM_APPKEY+"_"+sellernick);
		TaobaoClient client=new DefaultTaobaoClient(C.TOP_SERVER_URL, app.getAppKey(),app.getAppSecret());
		MarketingPromotionsGetRequest req=new MarketingPromotionsGetRequest();
			req.setFields("promotion_id ,promotion_title,promotion_desc,decrease_num,num_iid,discount_value,discount_type ,start_date ,end_date ,tag_id ,status");
		MarketingPromotionsGetResponse response = client.execute(req , sub.getSessionKey());
		
		throwApiExceptionIfError(response);
		return response.getPromotions();
	}

	public TradeMemoUpdateResponse updateMemo(Long tid, Long flag, String memo,String seller)throws ApiException {
		App  app=lkp.getApps().get(C.PCRM_APPKEY);
		Subscriber sub=lkp.getSubscribers().get(C.PCRM_APPKEY+"_"+seller);
		TaobaoClient client=new DefaultTaobaoClient(C.TOP_SERVER_URL, app.getAppKey(),app.getAppSecret());
		TradeMemoUpdateRequest  req=new TradeMemoUpdateRequest ();
			req.setTid(tid);
			req.setFlag(flag);
			req.setMemo(memo);
			
			if(StringUtils.isEmpty(memo))
				req.setReset(true);
			else
				req.setReset(false);
			TradeMemoUpdateResponse res=	client.execute(req , sub.getSessionKey());
		return res;
	}
	
	public TradeCloseResponse closeTrade(String seller,Long tid,String reason)throws ApiException{
		App  app=lkp.getApps().get(C.PCRM_APPKEY);
		Subscriber sub=lkp.getSubscribers().get(C.PCRM_APPKEY+"_"+seller);
		
		TaobaoClient client=new DefaultTaobaoClient(C.TOP_SERVER_URL, app.getAppKey(),app.getAppSecret());
		TradeCloseRequest req=new TradeCloseRequest();
		req.setCloseReason(reason);
			req.setTid(tid);
		return client.execute(req , sub.getSessionKey());
	}
	
	public Boolean updateShippingAddress(String sellernick,Long tid,String receiverCity,String receiverDistrict,String receiverAddress,String receiverMobile,String receiverName,String receiverPhone,String receiverState,String receiverZip)throws ApiException{
		App  app=lkp.getApps().get(C.PCRM_APPKEY);
		Subscriber sub=lkp.getSubscribers().get(C.PCRM_APPKEY+"_"+sellernick);
		TaobaoClient client=new DefaultTaobaoClient(C.TOP_SERVER_URL, app.getAppKey(),app.getAppSecret());
		
		TradeShippingaddressUpdateRequest req=new TradeShippingaddressUpdateRequest();
			req.setReceiverAddress(receiverAddress);
			req.setReceiverCity(receiverCity);
			req.setReceiverDistrict(receiverDistrict);
			req.setReceiverMobile(receiverMobile);
			req.setReceiverName(receiverName);
			req.setReceiverPhone(receiverPhone);
			req.setReceiverState(receiverState);
			req.setReceiverZip(receiverZip);
			req.setTid(tid);
			
		TradeShippingaddressUpdateResponse response = client.execute(req , sub.getSessionKey());
		return response.isSuccess();
	}
	
	//query logistic trace 
	public  LogisticsTraceSearchResponse getLogisticTrace(String seller,Long tid)throws ApiException{
		App  app=lkp.getApps().get(C.PCRM_APPKEY);
		TaobaoClient client=new DefaultTaobaoClient(C.TOP_SERVER_URL, app.getAppKey(),app.getAppSecret());
		LogisticsTraceSearchRequest req=new LogisticsTraceSearchRequest();
			req.setTid(tid);
			req.setSellerNick(seller);
		return client.execute(req);
	}
	
	public TradePostageUpdateResponse updatePostage (String seller,Long tid,String fee) throws ApiException{
		App  app=lkp.getApps().get(C.PCRM_APPKEY);
		Subscriber sub=lkp.getSubscribers().get(C.PCRM_APPKEY+"_"+seller);
		TaobaoClient client=new DefaultTaobaoClient(C.TOP_SERVER_URL, app.getAppKey(),app.getAppSecret());
		TradePostageUpdateRequest req=new TradePostageUpdateRequest();
		req.setTid(tid);
		req.setPostFee(fee);
		
		return client.execute(req , sub.getSessionKey());
	}
	public TradeFullinfoGetResponse getTradeFullInfo (String seller,Long tid)throws ApiException{
		App  app=lkp.getApps().get(C.PCRM_APPKEY);
		Subscriber sub=lkp.getSubscribers().get(C.PCRM_APPKEY+"_"+seller);
		TaobaoClient client=new DefaultTaobaoClient(C.TOP_SERVER_URL, app.getAppKey(),app.getAppSecret());
		TradeFullinfoGetRequest  req=new TradeFullinfoGetRequest ();
			req.setFields("adjust_fee,alipay_no,available_confirm_fee,buyer_alipay_no,buyer_cod_fee,buyer_email,buyer_flag,buyer_memo,buyer_message,buyer_nick,buyer_obtain_point_fee,buyer_rate,cod_fee,cod_status,commission_fee,consign_time,created,discount_fee,end_time,express_agency_fee,has_post_fee,invoice_name,is_3D,modified,num,num_iid,orders.adjust_fee,orders.buyer_nick,orders.buyer_rate,orders.cid,orders.discount_fee,orders.iid,orders.is_oversold,orders.item_meal_id,orders.item_meal_name,orders.modified,orders.num,orders.num_iid,orders.oid,orders.outer_iid,orders.outer_sku_id,orders.payment,orders.pic_path,orders.price,orders.refund_id,orders.refund_status,orders.seller_nick,orders.seller_rate,orders.seller_type,orders.sku_id,orders.sku_properties_name,orders.snapshot,orders.snapshot_url,orders.status,orders.timeout_action_time,orders.title,orders.total_fee,pay_time,payment,pic_path,point_fee,post_fee,price,promotion,promotion_details,real_point_fee,received_payment,receiver_address,receiver_city,receiver_district,receiver_mobile,receiver_name,receiver_phone,receiver_state,receiver_zip,seller_alipay_no,seller_cod_fee,seller_email,seller_flag,seller_memo,seller_mobile,seller_name,seller_nick,seller_phone,seller_rate,shipping_type,snapshot_url,status,tid,timeout_action_time,title,total_fee,trade_from,trade_memo,type");
			req.setTid(tid);
		return  client.execute(req , sub.getSessionKey());
	}
	
	public LogisticsOrdersDetailGetResponse getLogisticDetail(String seller,Long tid)throws ApiException{
		App  app=lkp.getApps().get(C.PCRM_APPKEY);
		Subscriber sub=lkp.getSubscribers().get(C.PCRM_APPKEY+"_"+seller);
		TaobaoClient client=new DefaultTaobaoClient(C.TOP_SERVER_URL, app.getAppKey(),app.getAppSecret());
		LogisticsOrdersDetailGetRequest req=new LogisticsOrdersDetailGetRequest();
		req.setFields("company_name,receiver_name,receiver_phone,receiver_mobile,receiver_location");
		req.setTid(tid);
		return client.execute(req , sub.getSessionKey());
	}
	
	private static void throwApiExceptionIfError(TaobaoResponse response)throws ApiException{
		if(response.getErrorCode()!=null)
			throw new ApiException(response.getErrorCode(),response.getMsg());
	}

	public Sku getSku(String seller,Long  numiid, Long skuid) throws ApiException {
		App  app=lkp.getApps().get(C.PCRM_APPKEY);
		Subscriber sub=lkp.getSubscribers().get(C.PCRM_APPKEY+"_"+seller);
		TaobaoClient client=new DefaultTaobaoClient(C.TOP_SERVER_URL, app.getAppKey(),app.getAppSecret());
		ItemSkuGetRequest req=new ItemSkuGetRequest();
		req.setNumIid(numiid);
		req.setSkuId(skuid);
		req.setFields("sku_id,num_iid,properties,quantity,price,outer_id,created,modified,status");
		ItemSkuGetResponse response = client.execute(req);
		return response.getSku();
	}

	public RefundsApplyGetResponse getBuyerRefundsCount(String sellerNick, String buyerNick) throws ApiException {
		App app = lkp.getApps().get(C.PCRM_APPKEY);
		Subscriber sub = lkp.getSubscribers().get(C.PCRM_APPKEY + "_" + sellerNick);
		TaobaoClient client=new DefaultTaobaoClient(C.TOP_SERVER_URL, app.getAppKey(),app.getAppSecret());
		RefundsApplyGetRequest req=new RefundsApplyGetRequest();
		req.setFields("tid");
		req.setSellerNick(sellerNick);
		req.setStatus("SUCCESS"); 
		return client.execute(req, sub.getSessionKey());
	}
	
	
	public UmpPromotionGetResponse  getUmpPromotionGet(String seller,Long numiid) throws ApiException{
		App app = lkp.getApps().get(C.PCRM_APPKEY);
		Subscriber sub = lkp.getSubscribers().get(C.PCRM_APPKEY + "_" + seller);
		TaobaoClient client=new DefaultTaobaoClient(C.TOP_SERVER_URL, app.getAppKey(),app.getAppSecret());
		UmpPromotionGetRequest req=new UmpPromotionGetRequest();
		req.setItemId(numiid);
		return client.execute(req,sub.getSessionKey());
	}
	 
		
	/*	public void vasSubscribeGet(String articleId,String nick)  throws  ApiException {
	TaobaoClient client=new DefaultTaobaoClient(C.TOP_SERVER_URL, app.getAppKey(),app.getAppSecret());
	
	
	VasSubscribeGetRequest req=new VasSubscribeGetRequest();
		req.setNick(nick);
		req.setArticleCode(articleId);
	VasSubscribeGetResponse response = client.execute(req);
	if(response.getErrorCode()!=null)
		throw new ApiException(response.getErrorCode(),response.getMsg());
}
*/


/*
public boolean addTradeMemo(Long tid, String rank, String memo,String sellernick)throws ApiException {
	App  app=lkp.getApps().get(C.PCRM_APPKEY);
	Subscriber sub=lkp.getSubscribers().get(C.PCRM_APPKEY+"_"+sellernick);
	TradeMemoAddRequest  req=new TradeMemoAddRequest ();
	TradeMemoAddResponse  res=new TradeMemoAddResponse ();
	return res.isSuccess();
}*/

}
