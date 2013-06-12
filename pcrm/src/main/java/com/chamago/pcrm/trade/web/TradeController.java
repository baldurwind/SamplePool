
package com.chamago.pcrm.trade.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chamago.pcrm.common.utils.AopLogModule;
import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.common.utils.Utils;
import com.chamago.pcrm.taobao.top.service.TopService;
import com.chamago.pcrm.trade.mapper.TradeMapper;
import com.chamago.pcrm.trade.service.TradeService;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Shipping;
import com.taobao.api.domain.Trade;
import com.taobao.api.domain.TransitStepInfo;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.response.LogisticsOrdersDetailGetResponse;
import com.taobao.api.response.LogisticsTraceSearchResponse;
import com.taobao.api.response.TradeCloseResponse;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.taobao.api.response.TradeMemoUpdateResponse;
import com.taobao.api.response.TradePostageUpdateResponse;
/**
 index page :http://127.0.0.1:8080/pcrm/trade/index?seller=&buyer=
  tid :104688434015646  not exist  
 * */
@Controller
@RequestMapping("/trade")
public class TradeController     {
	@Autowired
	private TradeService tradeService;
	@Autowired
	private TopService topService;
	
	Logger logger =Logger.getLogger(TradeController.class);
/*TOP:交易主页*/
	
	@RequestMapping("/index")
	@AopLogModule(name=C.LOG_MODULE_TRADE,layer=C.LOG_LAYER_CONTROLLER)
	public String index(HttpServletRequest request,String tradeStatus,String tradeWith){
		request.setAttribute("tradeStatus",tradeStatus);
		request.setAttribute("tradeWith",tradeWith);
		return  "/trade/index";
	}
	@RequestMapping("/tradeWithRefund")
	@AopLogModule(name=C.LOG_MODULE_TRADE,layer=C.LOG_LAYER_CONTROLLER)
	public  String tradeWithRefund(HttpServletRequest req,@RequestParam String seller,@RequestParam String buyer ){
		List<Long> tmp=tradeService.findRefundNum(seller,buyer);
		if(tmp.size()<1)
			return "/trade/trade";
		List<Object[]>  tidList=tradeService.findTradeByTids(tmp);
		if(tidList.size()<1)
			return "/trade/trade";
		
		PagedListHolder<Object[]>  pagination=new PagedListHolder<Object[]>(tidList);

		int currentPage=0;
		try {
			currentPage = Integer.valueOf(req.getParameter("current_page"));
		} catch (NumberFormatException e) {
		}
		
		pagination.setPage(currentPage);
		Utils.pagination(5,pagination, req);
		List<Object[]> oidList=tradeService.findOrderByTidList(tmp);
		
		req.setAttribute("current_page",pagination.getPage());
		req.setAttribute("oidList", oidList);
		req.setAttribute("tidList", pagination);

		return "/trade/trade";
	}
	@RequestMapping
	@AopLogModule(name=C.LOG_MODULE_TRADE,layer=C.LOG_LAYER_CONTROLLER)
	public  String trade(HttpServletRequest req,@RequestParam String seller,@RequestParam String buyer,String tradeStatus ){
		seller=req.getParameter("seller");
		
		List list=null;
		if(!StringUtils.isEmpty(tradeStatus)){
			list=new ArrayList();
			for(String str:tradeStatus.split(","))
				list.add(str);
		}
		
		List<Object[]>  tidList=tradeService.findTradeBySellerBuyer(seller, buyer,list);
		if(tidList.size()<1)
			return "/trade/trade";
		PagedListHolder<Object[]>  pagination=new PagedListHolder<Object[]>(tidList);
		
		int currentPage=Integer.valueOf(req.getParameter("current_page"));
		
		pagination.setPage(currentPage);
		Utils.pagination(5,pagination, req);
		List<Object> tids=new ArrayList<Object>();
		for(Object[] tid:pagination.getPageList()){
			tids.add(tid[0]);
		}
		List<Object[]> oidList=tradeService.findOrderByTidList(tids);
		
		req.setAttribute("tradeStatus",tradeStatus);
		req.setAttribute("current_page",pagination.getPage());
		req.setAttribute("oidList", oidList);
		req.setAttribute("tidList", pagination);

		return "/trade/trade";
	}
/*TOP:快递状态追踪*/	
	@RequestMapping("/trace")
	@AopLogModule(name=C.LOG_MODULE_TRADE,layer=C.LOG_LAYER_CONTROLLER)
	public String trace(HttpServletRequest req,@RequestParam String seller,@RequestParam Long tid){
		try {
			LogisticsOrdersDetailGetResponse res=topService.getLogisticDetail(seller, tid);
			
			if(res.isSuccess()){
				Shipping shipping=res.getShippings().get(0);
				req.setAttribute("company", shipping.getCompanyName());
				 LogisticsTraceSearchResponse res1=topService.getLogisticTrace(seller, tid);
				 if(res1.isSuccess())
					 req.setAttribute("traceList",res1.getTraceList());
				 else
					req.setAttribute("msg", res1.getSubMsg());
				 req.setAttribute("tid", tid);
			}
			else
				req.setAttribute("msg", res.getSubMsg());
		
		} catch (ApiException e) {
			//com.taobao.api.ApiException: java.net.UnknownHostException: gw.api.taobao.com
			logger.error(e.getErrCode(), e);
			req.setAttribute("msg", e.getErrMsg());
		
		}
		return "/trade/trace";
	}
	
/*TOP:收货信息*/
	@RequestMapping("/shipping")
	@AopLogModule(name=C.LOG_MODULE_TRADE,layer=C.LOG_LAYER_CONTROLLER)
	public String shipping(HttpServletRequest req,@RequestParam String seller,@RequestParam Long tid)   {
		try {
			LogisticsOrdersDetailGetResponse res=topService.getLogisticDetail(seller, tid);
			if(res.isSuccess()){
				Shipping shipping=res.getShippings().get(0);
				req.setAttribute("shipping", shipping);
			}else
				req.setAttribute("msg", res.getSubMsg());
		} catch (ApiException e) {
			logger.error(e.getErrCode(), e);
		}
		return  "/trade/shipping";
	}

/*TOP:关闭交易*/
	@RequestMapping("/close")
	@AopLogModule(name=C.LOG_MODULE_TRADE,layer=C.LOG_LAYER_CONTROLLER)
	public @ResponseBody  JSONObject closeTrade(HttpServletRequest req,   @RequestParam String seller,@RequestParam Long tid,String reason)  throws IOException {
		JSONObject json=new JSONObject();
		try {
			TradeCloseResponse res = topService.closeTrade(seller, tid, reason);
			if(res.isSuccess()){
				tradeService.updateTradeStatus(tid, C.ITEM_STAUTS_TRADE_CLOSED);
				tradeService.updateOrderStatus(tid,C.ITEM_STAUTS_TRADE_CLOSED);
			}
			
			json.put("result",res.isSuccess() );
			json.put("msg",res.getSubMsg());
			
		} catch (ApiException e) {
			e.printStackTrace();
			json.put("result", false);
			json.put("error",e.getMessage());
		}
		return json;
	}
	
/*TOP:更新备注信息*/	
	@RequestMapping("/memo/update")
	@AopLogModule(name=C.LOG_MODULE_TRADE,layer=C.LOG_LAYER_CONTROLLER)
	public @ResponseBody JSONObject updateMemo(HttpServletRequest req,    @RequestParam String seller,@RequestParam Long tid, @RequestParam Long flag,@RequestParam String memo) throws IOException   {
		JSONObject json=new JSONObject();
		try {
			TradeMemoUpdateResponse res= topService.updateMemo(tid, flag, memo, seller);
			if(res.isSuccess()){
				tradeService.updateSellerFlag(String.valueOf(flag), tid);
			}
			else
				json.put("msg", res.getSubMsg());
			json.put("result", res.isSuccess());
		} catch (ApiException e) {
			e.printStackTrace();
			logger.error(e.getErrCode(), e);
		}
		return json ;
	}
	
	
/**
 * 获得备注信息	
 * @param req
 * @param tid
 * @param seller
 * @return
 */
	@RequestMapping("/memo/get")
	@AopLogModule(name=C.LOG_MODULE_TRADE,layer=C.LOG_LAYER_CONTROLLER)
	public  String getMemo(HttpServletRequest req,@RequestParam Long tid,@RequestParam String seller)   {
		if("1".equals(req.getParameter("isbatch"))){
			 return "/trade/memo";
		}
		
		try {
			TradeFullinfoGetResponse res=topService.getTradeFullInfo(seller, tid);
			Trade trade=res.getTrade();
			if(null!=trade){
				req.setAttribute("seller_memo",trade.getSellerMemo());
			 	req.setAttribute("seller_flag",trade.getSellerFlag());
			}else{
				req.setAttribute("msg",res.getSubMsg());
			}
		} catch (ApiException e) {
			e.printStackTrace();
			req.setAttribute("msg",e.getMessage());
		}
		 return "/trade/memo";
	}
/*TOP:修改邮费*/
	@RequestMapping("/postage/update")
	@AopLogModule(name=C.LOG_MODULE_TRADE,layer=C.LOG_LAYER_CONTROLLER)
	public @ResponseBody JSONObject updatePostage(HttpServletRequest req,@RequestParam String seller,@RequestParam Long tid, @RequestParam String fee)    {
		JSONObject json=new JSONObject();
		try {
			TradePostageUpdateResponse res= topService.updatePostage(seller, tid, fee);
			if(res.isSuccess())
				tradeService.updatePostage(tid, fee);
			
			json.put("result", res.isSuccess());
			json.put("msg", res.getSubMsg());
		} catch (ApiException e) {
			logger.error(e.getErrCode(), e);
			json.put("msg", e.getMessage());
		}
		 return json;
	}
	/*TOP:批量更新备注*/
	@RequestMapping("/memo/batch/update")
	@AopLogModule(name=C.LOG_MODULE_TRADE,layer=C.LOG_LAYER_CONTROLLER)
	public @ResponseBody JSONObject batchUpdateMemo(HttpServletRequest req,  @RequestParam String seller,@RequestParam String tids, @RequestParam Long flag,@RequestParam String memo) throws IOException   {
		JSONObject json=new JSONObject();
		String arr[]=tids.split(",");
		String err_tids="";
		String succ_tids="";
		List<Long> list=new ArrayList<Long>();
		TradeMemoUpdateResponse res=null;
		tids=tids.substring(2);
		for(String str:arr){
			if(StringUtils.isEmpty(str))
				continue;
			Long tid=Long.valueOf(str);
			try {
				res= topService.updateMemo(tid, flag, memo, seller);
				if(res.isSuccess()){
					succ_tids+=tid+";";
					list.add(tid);
				}
					
				else{
					json.put("msg", res.getSubMsg()+":"+tid);
					err_tids+=tid+";";
				}
			} catch (ApiException e) {
				e.printStackTrace();
				logger.error(e.getErrCode(), e);
				err_tids+=tid+";";
			}
		}
		if(list.size()>0)
			tradeService.batchUpdateSellerFlag(String.valueOf(flag), list);
		if(StringUtils.isEmpty(err_tids)) 
			json.put("result", true);
		else{
			json.put("err_tids", err_tids);
			json.put("succ_tids",succ_tids);
			json.put("result", false);
		}
		return json ;
	}
	/*TOP:批量修改邮费*/
	@RequestMapping("/postage/batch/update")
	@AopLogModule(name=C.LOG_MODULE_TRADE,layer=C.LOG_LAYER_CONTROLLER)
	public @ResponseBody JSONObject batchUpdatePostage(HttpServletRequest req,    @RequestParam String seller,@RequestParam String tids, @RequestParam String fee) throws IOException   {
		JSONObject json=new JSONObject();
		String arr[]=tids.split(",");
		String err_tids="";
		List<Long> successList=new ArrayList<Long>();
		TradePostageUpdateResponse res=null;
		tids=tids.substring(1);
		for(String str:arr){
			if(StringUtils.isEmpty(str))
				continue;
			Long tid=Long.valueOf(str);
			try {
				  res= topService.updatePostage(seller, tid, fee);
				
				if(res.isSuccess())
					successList.add(tid);
				else{
					json.put("msg", res.getSubMsg());
					err_tids+=tid+";";
				}
			} catch (ApiException e) {
				logger.error(e.getErrCode(), e);
				err_tids+=tid+";";
			}
			catch(Exception e){
				System.out.println(e.getMessage());
				err_tids+=tid+";";
			}
		}
		if(successList.size()>0)
			tradeService.batchUpdatePostage(successList, fee);
		if(StringUtils.isEmpty(err_tids))
			json.put("result", true);
		else{
			json.put("err_tids", err_tids);
			json.put("result", false);
		}
			
		return json ;
	}
/*	 public static String decodeUTF8(String value){
			if(value==null){
				return null;
			} 
			try {
				String os = System.getProperty("os.name");
				if(os!=null){
					os = os.toUpperCase();
					if(os.contains("WIN")){
						value = java.net.URLDecoder.decode(value, "GBK");
						value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
				}else {
						value = java.net.URLDecoder.decode(value, "UTF-8");
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return value;
	 }
*/}

