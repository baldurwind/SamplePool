package com.chamago.pcrm.behavior.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chamago.pcrm.behavior.service.BehaviorService;
import com.chamago.pcrm.common.utils.AopLogModule;
import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.common.utils.Utils;
import com.chamago.pcrm.item.service.ItemLuceneService;
@Controller
@RequestMapping("/behavior")
public class BehaviorController {
	@Autowired
	private BehaviorService behaviorServce;
	
	@Autowired
	private ItemLuceneService itemLuceneService;
	
	private final static Logger logger=Logger.getLogger(BehaviorController.class);
	
	
	@RequestMapping("test")
	public String test(){
		return "/behavior/json";
	}
	
	
	@RequestMapping("/pageview")
	@AopLogModule(name=C.LOG_MODULE_BEHAVIOR,layer=C.LOG_LAYER_CONTROLLER)
	public String findPageView(HttpServletRequest request,@RequestParam String seller,@RequestParam String buyer){
		Integer pageview=behaviorServce.findPageView(seller, buyer);
		request.setAttribute("pageview", pageview);
		return "/behavior/pageview";
	}
	//关键字
	
	@RequestMapping("/keyword")
	@AopLogModule(name=C.LOG_MODULE_BEHAVIOR,layer=C.LOG_LAYER_CONTROLLER)
	public  String findKeyword(HttpServletRequest request,@RequestParam String seller,@RequestParam String buyer){
		List<Object[]>  result=behaviorServce.findKeyword(seller, buyer);
		request.setAttribute("keywords", result);
		return "/behavior/keyword";
	}
	//曾经游览过的商品
	@RequestMapping("/visitinghistory")
	@AopLogModule(name=C.LOG_MODULE_BEHAVIOR,layer=C.LOG_LAYER_CONTROLLER)
	public String findVisitingHistory(HttpServletRequest request,@RequestParam String seller,@RequestParam String buyer){
		List<Object[]>  results=behaviorServce.findVisitingHistory(seller, buyer);
		PagedListHolder<Object[]>  pagination=new PagedListHolder<Object[]>(results);
		
		int	currentPage =0;
		try {
			 currentPage = Integer.valueOf(request.getParameter("current_page"));
		} catch (NumberFormatException e) {}
		pagination.setPage(currentPage);			
		
		request.setAttribute("current_page",pagination.getPage());
		request.setAttribute("visitinghistory",pagination);
		
		return "/behavior/visiting_history";
	}
	//曾经购买过的商品
	@RequestMapping("/boughtitem")
	@AopLogModule(name=C.LOG_MODULE_BEHAVIOR,layer=C.LOG_LAYER_CONTROLLER)
	public String  findBoughtItem(HttpServletRequest request,@RequestParam String seller,@RequestParam String buyer){
		List<Object[]>  results=behaviorServce.findBoughtItem(seller, buyer);
		PagedListHolder<Object[]>  pagination=new PagedListHolder<Object[]>(results);
		int	currentPage =0;
		try {
			 currentPage = Integer.valueOf(request.getParameter("current_page"));
		} catch (NumberFormatException e) {}
		pagination.setPage(currentPage);	
		
		Utils.pagination(C.PAGINATION_SIZE,pagination, request);
		request.setAttribute("current_page",pagination.getPage());
		request.setAttribute("boughtitem",pagination);
		return "/behavior/bought_item";
	}
	
	//猜他喜欢
		@RequestMapping("/itemrelation")
		@AopLogModule(name=C.LOG_MODULE_BEHAVIOR,layer=C.LOG_LAYER_CONTROLLER)
		public String findItemRelation(HttpServletRequest request,@RequestParam String seller,@RequestParam String buyer,@RequestParam Long numiid){
			List<Object[]> results=behaviorServce.findItemRelation( numiid,seller);
			
			PagedListHolder<Object[]>  pagination=new PagedListHolder<Object[]>(results);
			int	currentPage =0;
			try {
				 currentPage = Integer.valueOf(request.getParameter("current_page"));
			} catch (NumberFormatException e) {}
			pagination.setPage(currentPage);	
		
			Utils.pagination(C.PAGINATION_SIZE,pagination, request);
			request.setAttribute("current_page",pagination.getPage());
			request.setAttribute("itemrelations", pagination);
			return "/behavior/item_relation";
		}
		
		
		
	//曾经咨询商品
	@RequestMapping("/consultation")
	@AopLogModule(name=C.LOG_MODULE_BEHAVIOR,layer=C.LOG_LAYER_CONTROLLER)
	public String findConsultationHistory(HttpServletRequest request,HttpServletResponse response,@RequestParam String seller,@RequestParam String buyer,@RequestParam Long numiid){
		try {
			System.out.println("consultation");
			List<Object[]> entryFromTableList=behaviorServce.findConsultationHistory(seller, buyer); //包含咨询时间  object[numiid,created] 并且根据咨询时间排序
			List<Document> entryFromLuceneList=itemLuceneService.searchItem(seller, entryFromTableList);  //随机排序
			List<Object[]> mixEntryList=new ArrayList<Object[]>(); // 必须按照咨询时间先后排序
			for(Object[] entryFromTable:entryFromTableList){
				Object[] obj=new Object[8];
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
				obj[3]=consultDateTips(entryFromTable[1]);  //把咨询时间赋给lucene查出来的数据
			}
			request.setAttribute("mixEntryList", mixEntryList);
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
		} catch (CorruptIndexException e) {
			logger.error(e.getMessage());
		} catch (ParseException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return "/behavior/consultation";
	}
	@RequestMapping("/jsonconsultation")
	public @ResponseBody JSONObject findJSONConsultationHistory(HttpServletRequest request,HttpServletResponse response,@RequestParam String seller,@RequestParam String buyer,@RequestParam Long numiid){
		JSONObject json=new  JSONObject();
		try {
			List<Object[]> entryFromTableList=behaviorServce.findConsultationHistory(seller, buyer); //包含咨询时间  object[numiid,created] 并且根据咨询时间排序
			List<Document> entryFromLuceneList=itemLuceneService.searchItem(seller, entryFromTableList);  //随机排序
			List<Object[]> mixEntryList=new ArrayList<Object[]>(); // 必须按照咨询时间先后排序
			for(Object[] entryFromTable:entryFromTableList){
				Object[] obj=new Object[8];
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
				obj[3]=consultDateTips(entryFromTable[1]);  //把咨询时间赋给lucene查出来的数据
			}
			
			json.put("mixEntryList", mixEntryList);
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
		} catch (CorruptIndexException e) {
			logger.error(e.getMessage());
		} catch (ParseException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return json;
	}
	
	
	public static void main(String gasg[]){
		ApplicationContext app=Utils.getClassPathXMlApplication();
		BehaviorService behaviorServce= app.getBean(BehaviorService.class);
		System.out.println(behaviorServce.findItemRelation( 14573214760L,"良无限home").size());
	}
	 private static String consultDateTips(Object date)   {  
	    	try {
	    		
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				  Calendar now=Calendar.getInstance();
				  Calendar old=Calendar.getInstance();
				  String tmp=(String)date;
				  if(tmp.length()==21)	{
					  tmp=tmp.substring(0, 19);
				  }
				  old.setTime(sdf.parse(tmp)) ;
				  
				  int oday=old.get(Calendar.DATE);
				  int nday=now.get(Calendar.DATE);
				  
				  
				  int ndayOfYear=now.get( Calendar.DAY_OF_YEAR);
				  int odayOfYear=old.get( Calendar.DAY_OF_YEAR);
				
				  if(ndayOfYear-odayOfYear>0&&ndayOfYear-odayOfYear<30)
					  return (ndayOfYear-odayOfYear)+"天前";
				  
				  //now.setTime(sdf.parse(date.toString()));
				  int oYear=old.get(Calendar.YEAR);
				  int nYear=now.get(Calendar.YEAR);
				  if(nYear-oYear>1)
					  return (nYear-oYear)+"年前";
				  int oMonth=old.get(Calendar.MONTH);
				  int nMonth=now.get(Calendar.MONTH);
				  if(nMonth-oMonth>1)
					  return (nMonth-oMonth-1)+"个月前";
				
				
				  if(nday-oday>0)
					  return (nday-oday)+"天前";

				  int oHour=old.get(Calendar.HOUR_OF_DAY);
				  int nHour=now.get(Calendar.HOUR_OF_DAY);
				  
				  if(nHour-oHour>0)
					  return (nHour-oHour)+"小时前";
				  
				  int oMins=old.get(Calendar.MINUTE);
				  int nMins=now.get(Calendar.MINUTE);
				  if(nMins-oMins>0)
					  return (nMins-oMins)+"分钟前";
			} catch (java.text.ParseException e) {
					logger.error(e.getMessage());
			}
			//  return "时间错误";
	    	return "1分钟前"; //可能淘宝服务器的时间比服务器的时间快
	}
	
}
