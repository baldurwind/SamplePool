package com.chamago.pcrm.crm.web;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chamago.pcrm.common.utils.AopLogModule;
import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.common.utils.SessionAttribute;
import com.chamago.pcrm.common.utils.Utils;
import com.chamago.pcrm.customerservice.service.CustomerServiceService;
import com.chamago.pcrm.taobao.top.service.TopService;
import com.chamago.pcrm.trade.service.TradeService;
import com.taobao.api.ApiException;
import com.taobao.api.domain.BasicMember;
import com.taobao.api.domain.CrmMember;
import com.taobao.api.response.CrmMembersGetResponse;

@Controller
@RequestMapping("/crm")
public class CrmController {

	
	@Autowired
	private TradeService tradeService;
	@Autowired
	private TopService topService;
	
	@Autowired
	CustomerServiceService customerServiceService;

	private final static Logger logger=Logger.getLogger(CrmController.class);
	
	
	@RequestMapping("/summary")
	@AopLogModule(name=C.LOG_MODULE_PCRM,layer=C.LOG_LAYER_CONTROLLER)
	public String findCrmTradeSummary(HttpServletRequest request,HttpServletResponse response) throws ApiException, IOException{
		String seller = request.getParameter("seller");
		String buyer = request.getParameter("buyer");
		try {
			CrmMembersGetResponse res = topService.getCrmMembers(seller, buyer);
			if(res==null||res.getMembers().size()<=0||!res.isSuccess()){
				response.getWriter().write("{\"code\":0,\"msg\":\"" + res.getSubMsg() + "\"}");
			}else{
				BasicMember bm = res.getMembers().get(0);
				StringBuffer json = new StringBuffer("");
				json.append("{\"code\":1," +
						"\"tradeCount\":\"" + bm.getTradeCount() + "\"," +
						"\"tradeAmount\":\"" + bm.getTradeAmount() + "\",");
				if(bm.getTradeCount()==0){
					json.append("\"tradeAvg\":\"" + 0 + "\",");
				}else{
					json.append("\"tradeAvg\":\"" +doubleToString(Float.parseFloat(bm.getTradeAmount())/Float.parseFloat(bm.getTradeAmount()),2) + "\",");
				}
				json.append("\"lastTradeDate\":\"" + Utils.DateToString(bm.getLastTradeTime(), "yyyy-MM-dd") + "\"");
				json.append("}");
				response.getWriter().write(json.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("{\"code\":\"-1\"}");
		} finally {
			response.getWriter().close();
		}
			
			
			
		return null;
	}
	
	@RequestMapping("/waitintsendgoods")
	@AopLogModule(name=C.LOG_MODULE_PCRM,layer=C.LOG_LAYER_CONTROLLER)
	public String findCrmWaitingSendGoods(HttpServletRequest request,HttpServletResponse response) throws ApiException, IOException{
		String seller = request.getParameter("seller");
		String buyer = request.getParameter("buyer");

		try {
			List<Long> waitingSendGoodsTradeList =tradeService.findWaitingSendGoodsTradeId(seller, buyer);
			if(waitingSendGoodsTradeList!=null&&waitingSendGoodsTradeList.size()>0){
				response.getWriter().write("{\"waitsgts\":\"" + waitingSendGoodsTradeList.size() + "\"}");
			}else{
				response.getWriter().write("{\"waitsgts\":\"" + 0+ "\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("{\"waitsgts\":\"" + 0+ "\"}");
		} finally {
			response.getWriter().close();
		}
		return null;
	}
	
	@RequestMapping("/refundnum")
	@AopLogModule(name=C.LOG_MODULE_PCRM,layer=C.LOG_LAYER_CONTROLLER)
	public String findCrmRefundNum(HttpServletRequest request,HttpServletResponse response) throws ApiException, IOException{
		String seller = request.getParameter("seller");
		String buyer = request.getParameter("buyer");
		
		try {
			List<Long> refundOidList=tradeService.findRefundNum(seller, buyer);
			if(refundOidList!=null&&refundOidList.size()>0){
				response.getWriter().write("{\"refundnum\":\"" + refundOidList.size() + "\"}");
			}else{
				response.getWriter().write("{\"refundnum\":\"" + 0+ "\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("{\"refundnum\":\"" + 0+ "\"}");
		} finally {
			response.getWriter().close();
		}
		
		return null;
	}
	
	
	@RequestMapping("/csissue")
	@AopLogModule(name=C.LOG_MODULE_PCRM,layer=C.LOG_LAYER_CONTROLLER)
	public String findCrmCsissue(HttpServletRequest request,HttpServletResponse response) throws ApiException, IOException{
		String userid = request.getParameter("userid");
		String buyer = request.getParameter("buyer");
		
		try {
			int csi=customerServiceService.getUntreatedCustomerServiceByNick(userid,buyer);
			response.getWriter().write("{\"csissue\":\"" + csi + "\"}");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("{\"csissue\":\"" + 0+ "\"}");
		} finally {
			response.getWriter().close();
		}
			
		return null;
	}
	
	
	public String doubleToString(double srcValue,int length)
    {
        NumberFormat   formatter   =   NumberFormat.getNumberInstance();
        formatter.setMaximumFractionDigits(length);     //设置最大小数位
        String value = formatter.format(srcValue);
		return value;
        
    }
}
