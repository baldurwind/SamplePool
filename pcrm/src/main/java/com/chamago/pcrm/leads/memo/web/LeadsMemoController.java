package com.chamago.pcrm.leads.memo.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chamago.pcrm.behavior.service.BehaviorService;
import com.chamago.pcrm.common.utils.AopLogModule;
import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.common.utils.PageModel;
import com.chamago.pcrm.common.utils.Utils;
import com.chamago.pcrm.item.pojo.ItemSearch;
import com.chamago.pcrm.item.service.ItemLuceneService;
import com.chamago.pcrm.leads.memo.pojo.LeadsMemo;
import com.chamago.pcrm.leads.memo.service.LeadsMemoService;

/**
 * 缺货/促销事件登记Controller
 * @author James.wang
 */
@Controller
@RequestMapping("/leadsMemo")
public class LeadsMemoController {
	
	//缺货/减价事件登记业务接口
	@Autowired
	private LeadsMemoService leadsMemoService;
	
	@Autowired
	private BehaviorService behaviorServce;
	
	@Autowired
	private ItemLuceneService itemLuceneService;
	
	static final Logger logger = Logger.getLogger(LeadsMemoController.class);
	
	/**
	 * 跳转至缺货/减价事件登记首页
	 */
	@RequestMapping("/index")
	@AopLogModule(name=C.LOG_MODULE_LEADSMEMO,layer=C.LOG_LAYER_CONTROLLER)
	public String index(ModelMap map, HttpServletRequest request) {
		return "/leadsMemo/index";
	}
	
	/**
	 * 跳转至缺货/促销事件登记页
	 */
	@RequestMapping("/toSave")
	@AopLogModule(name=C.LOG_MODULE_LEADSMEMO,layer=C.LOG_LAYER_CONTROLLER)
	public String toSave(ModelMap map, HttpServletRequest request) {
		try {
			String buyerNick = request.getParameter("buyerNick");
			String sellerNick = request.getParameter("sellerNick");
			String wangwangNick = request.getParameter("wangwangNick");
			List<Object[]> entryFromTableList=behaviorServce.findConsultationHistory(sellerNick, buyerNick); //包含咨询时间  object[numiid,created] 并且根据咨询时间排序
			List<Document> entryFromLuceneList=itemLuceneService.searchItem(sellerNick, entryFromTableList);  //随机排序
			List<Object[]> mixEntryList=new ArrayList<Object[]>(); // 必须按照咨询时间先后排序
			for(Object[] entryFromTable:entryFromTableList){
				Object[] obj=new Object[8];
				obj[3]=consultDateTips(entryFromTable[1]);  //把咨询时间赋给lucene查出来的数据
				
				for(Document entryFromLucene:entryFromLuceneList){
					if(entryFromTable[0].equals(entryFromLucene.get(C.LUCENE_FIELD_ITEM_NUMIID))){
						obj[0]=entryFromLucene.get(C.LUCENE_FIELD_ITEM_NUMIID);
						obj[1]=entryFromLucene.get(C.LUCENE_FIELD_ITEM_TITLE);
						obj[2]=entryFromLucene.get(C.LUCENE_FIELD_ITEM_PICURL);
						obj[4]=entryFromLucene.get(C.LUCENE_FIELD_ITEM_PROPS);
						obj[5]=entryFromLucene.get(C.LUCENE_FIELD_ITEM_NUM);
						obj[6]=entryFromLucene.get(C.LUCENE_FIELD_ITEM_HAS_DISCOUNT);
						obj[7]=entryFromLucene.get(C.LUCENE_FIELD_ITEM_PRICE);
						mixEntryList.add(obj);
						break;
					}
				}
			}
			request.setAttribute("mixEntryList", mixEntryList);
			map.put("buyerNick", buyerNick);
			map.put("sellerNick", sellerNick);
			map.put("wangwangNick", wangwangNick);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "/leadsMemo/save";
	}
	
	/**
	 * 添加缺货/促销事件登记
	 */
	@RequestMapping("/save")
	@AopLogModule(name=C.LOG_MODULE_LEADSMEMO,layer=C.LOG_LAYER_CONTROLLER)
	public String save(@ModelAttribute("leadsMemo") LeadsMemo leadsMemo, ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> param = new HashMap<String, String>();
		try {
			String expiredDate = request.getParameter("expiredDate1");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			leadsMemo.setExpiredDate(format.parse(expiredDate));
			getLeadsMemoService().saveLeadsMemo(leadsMemo);
			param.put("result", "true");
		} catch(Exception e) {
			e.printStackTrace();
			param.put("result", "false");
		}
		
		JSONObject obj = JSONObject.fromObject(param);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(obj.toString());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 跳转至跳转至缺货/促销事件修改页
	 */
	@RequestMapping("/toUpdate")
	@AopLogModule(name=C.LOG_MODULE_LEADSMEMO,layer=C.LOG_LAYER_CONTROLLER)
	public String toUpdate(ModelMap map, HttpServletRequest request) {
		try {
			String lid = request.getParameter("lid");
			LeadsMemo leadsMemo = getLeadsMemoService().getLeadsMemoById(lid);
			
			if(leadsMemo != null && leadsMemo.getNumId() != null) {
				List<Document> items = itemLuceneService.searchItem(leadsMemo.getSellerNick(), new Term(C.LUCENE_FIELD_ITEM_NUMIID,String.valueOf(leadsMemo.getNumId())));
				if(items.size() > 0) {
					ItemSearch itemSearch = new ItemSearch();
					itemSearch.setTitle(items.get(0).get(C.LUCENE_FIELD_ITEM_TITLE));
					itemSearch.setPicUrl(items.get(0).get(C.LUCENE_FIELD_ITEM_PICURL));
					map.put("itemSearch", itemSearch);
				}
			}
			
			if(leadsMemo != null && leadsMemo.getSkuId() != null) {
				Document sku = itemLuceneService.searchSkuById(leadsMemo.getSellerNick(), leadsMemo.getSkuId());
				String p = sku.get(C.LUCENE_FIELD_SKU_PROPS);
				Map<String, String> props = new HashMap<String, String>();
				String []prop = p.split(";");
				for(int i = 0; i < prop.length; i++) {
					String[] prop2 = prop[i].split(":");
					props.put(prop2[0], prop2[1]);
				}
				map.put("current_props", props);
			}
			map.put("leadsMemo", leadsMemo);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "/leadsMemo/update";
	}
	
	/**
	 * 修改缺货/促销事件登记
	 */
	@RequestMapping("/update")
	@AopLogModule(name=C.LOG_MODULE_LEADSMEMO,layer=C.LOG_LAYER_CONTROLLER)
	public String update(@ModelAttribute("leadsMemo") LeadsMemo leadsMemo, ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> param  = new HashMap<String, String>();
		try {
			String expiredDate = request.getParameter("expiredDate1");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			leadsMemo.setExpiredDate(format.parse(expiredDate));
			getLeadsMemoService().updateLeadsMemo(leadsMemo);
			param.put("result", "true");
		} catch(Exception e) {
			e.printStackTrace();
			param.put("result", "false");
		}
		
		JSONObject obj = JSONObject.fromObject(param);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(obj.toString());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加载当前买家相关缺货等记信息
	 */
	@RequestMapping("/promotionList")
	@AopLogModule(name=C.LOG_MODULE_LEADSMEMO,layer=C.LOG_LAYER_CONTROLLER)
	public String promotionList(ModelMap map, HttpServletRequest reuest, @RequestParam String buyerNick, @RequestParam String sellerNick) {
		//未处理
		map.put("noBeginCount", getLeadsMemoService().getLeadsMemoCountByStatus(buyerNick, sellerNick, "0"));
		//已完成
		map.put("successCount", getLeadsMemoService().getLeadsMemoCountByStatus(buyerNick, sellerNick, "1"));
		//已取消
		map.put("overdueCount", getLeadsMemoService().getLeadsMemoCountByStatus(buyerNick, sellerNick, "2"));
		return "/leadsMemo/promotionList";
	}
	
	/**
	 * 加载当前买家的促销事件列表
	 */
	@RequestMapping("/promotionList_detail")
	@AopLogModule(name=C.LOG_MODULE_LEADSMEMO,layer=C.LOG_LAYER_CONTROLLER)
	public String promotionListDetail(ModelMap map, HttpServletRequest request) {
		try {
			PageModel pageModel = new PageModel();
			pageModel.setPageSize(5);
			int pageNo = 1;
			if(request.getParameter("pageNo") != null) {
				pageNo = Integer.parseInt(request.getParameter("pageNo").toString());
				if(pageNo <= 0) {
					pageNo = 1;
				}
			}
			pageModel.setPageNo(pageNo);
			String sellerNick = request.getParameter("sellerNick");
			String buyerNick = request.getParameter("buyerNick");
			String status = request.getParameter("status");
			getLeadsMemoService().getLeadsMemoByParam(pageModel, buyerNick, sellerNick, status);
			if(pageModel.getList().size() > 0) {
				map.put("promotions", pageModel);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "/leadsMemo/promotionList_detail";
	}
	
	/**
	 * 修改当前信息状态
	 */
	@RequestMapping("/updateStatus")
	@AopLogModule(name=C.LOG_MODULE_LEADSMEMO,layer=C.LOG_LAYER_CONTROLLER)
	public String updateStatus(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> param = new HashMap<String, String>();
		try {
			getLeadsMemoService().updateStatus(request.getParameter("id"), Integer.parseInt(request.getParameter("status")));
			param.put("result", "true");
		} catch(Exception e) {
			e.printStackTrace();
			param.put("result", "false");
		}
		
		JSONObject obj = JSONObject.fromObject(param);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(obj.toString());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 加载信息状态个数
	 */
	@RequestMapping("/loadStatusCount")
	@AopLogModule(name=C.LOG_MODULE_LEADSMEMO,layer=C.LOG_LAYER_CONTROLLER)
	public String loadStatusCount(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> param = new HashMap<String, String>();
		try {
			String buyerNick = request.getParameter("buyerNick");
			String sellerNick = request.getParameter("sellerNick");
			String type = request.getParameter("type");
			//未提醒的个数
			param.put("message",  getLeadsMemoService().getLeadsMemoCountByStatus(buyerNick, sellerNick, type).toString());
			param.put("result", "true");
		} catch(Exception e) {
			param.put("result", "false");
		}
		Utils.outJsonString(param, response);
		return null;
	}
	
	private static String consultDateTips(Object date)   {  
	    	try {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				  Calendar now=Calendar.getInstance();
				  Calendar old=Calendar.getInstance();
				  	old.setTime(sdf.parse((String)date)) ;
				
				  	
				  int oYear=old.get(Calendar.YEAR);
				  int nYear=now.get(Calendar.YEAR);
				  if(nYear-oYear>0)
					  return (nYear-oYear)+"年前";
				  int oMonth=old.get(Calendar.MONTH);
				  int nMonth=now.get(Calendar.MONTH);
				  if(nMonth-oMonth>0)
					  return (nMonth-oMonth)+"个月前";
				  int oday=old.get(Calendar.DATE);
				  int nday=now.get(Calendar.DATE);
				  
				  if(nday-oday>0)
					  return (nday-oday)+"天前";

				  int oHour=old.get(Calendar.HOUR);
				  int nHour=now.get(Calendar.HOUR);
				  if(nHour-oHour>0)
					  return (nHour-oHour)+"小时前";
				  
				  int oMins=old.get(Calendar.MINUTE);
				  int nMins=now.get(Calendar.MINUTE);
				  if(nMins-oMins>0)
					  return (nMins-oMins)+"分钟前";
			} catch (java.text.ParseException e) {
				logger.error(e.getMessage());
			}
			  return "时间错误";
	}
	
	public LeadsMemoService getLeadsMemoService() {
		return leadsMemoService;
	}

	public void setLeadsMemoService(LeadsMemoService leadsMemoService) {
		this.leadsMemoService = leadsMemoService;
	}
}