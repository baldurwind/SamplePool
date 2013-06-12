/**
 * 
 */
package com.chamago.pcrm.worktable.performance.web;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chamago.pcrm.common.utils.AopLogModule;
import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.common.utils.SessionAttribute;
import com.chamago.pcrm.common.utils.Utils;
import com.chamago.pcrm.worktable.performance.pojo.Performance;
import com.chamago.pcrm.worktable.performance.service.PerformanceService;
import com.mysql.jdbc.StringUtils;

/**
 * 客服业绩
 * 
 * @author gavin.peng
 *
 */
@Controller
@RequestMapping("worktable")
public class PerformanceController {
	
	
	@Autowired
	private PerformanceService performanceService;
	
	private static Logger logger = LoggerFactory.getLogger(PerformanceController.class);
	private static String DAY = "day";
	private static String  WEEK = "week";
	private static String  MONTH = "month";
	
	private static int DAY_TYPE = 1;
	private static int  WEEK_TYPE = 2;
	private static int  MONTH_TYPE = 3;
	//保留小数位数 
	private static int FLOAT_SIZE = 2;
	//
	private static int PARCENT_NUMS = 2;
	private static String SHOW = "1";
	private static String HIDE = "0";
	
	
	
	@RequestMapping("/index")
	@AopLogModule(name=C.LOG_MODULE_WORKTABLE,layer=C.LOG_LAYER_CONTROLLER)
	public String getWorktableData(ModelMap map, HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		
		return "/worktable/worktable";
	}
	
	
	@RequestMapping("/allperiod")
	@AopLogModule(name=C.LOG_MODULE_WORKTABLE,layer=C.LOG_LAYER_CONTROLLER)
	public String getAllWorktableData(ModelMap map, HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String nick = (String)request.getParameter("nick");
		if(nick!=null){
			nick = Utils.decodeUTF8(nick);
		}
		String userid  = request.getParameter("userid");
		StringBuilder json = new StringBuilder("");
		Map<String,String> param = new Hashtable<String, String>();
		try{
			long groupId = -1;
			//TODO根据用户找到所属组的ID
			groupId = performanceService.getGroupIdByMember(nick);
			Performance	allPerformance = performanceService.getPerformanceBySeller(userid);
			Object[] avgObj = performanceService.findGroupAvgPerformance(groupId);
			if(allPerformance!=null){
				float price = 0l;
				if(allPerformance.getTotalBuyers()>0){
					price = (float)allPerformance.getTotalAmount()/allPerformance.getTotalBuyers();
				}
				float result = 0l;
				if(allPerformance.getReceivenNum()>0){
					result = (float)allPerformance.getTotalBuyers()/allPerformance.getReceivenNum();
				}
				allPerformance.setCustomPrice(Float.parseFloat(doubleToString(price,FLOAT_SIZE,false)));
				allPerformance.setCustomResult(Double.parseDouble(doubleToString(result,FLOAT_SIZE,true)));
				if(avgObj!=null){
					int groupAvgReceNum = Integer.parseInt(avgObj[0]==null? "0":avgObj[0].toString());
					int groupAvgNoreplyNum = Integer.parseInt(avgObj[1]==null? "0":avgObj[1].toString());
					int groupAvgChatpeerNum = Integer.parseInt(avgObj[2]==null? "0":avgObj[2].toString());
					int groupAvgRespTime = Integer.parseInt(avgObj[3]==null? "0":avgObj[3].toString());
					float groupAvgCusPrice = Float.parseFloat(avgObj[4]==null? "0":avgObj[4].toString());
					float groupAvgSerResult = Float.parseFloat(avgObj[5]==null? "0":avgObj[5].toString());
					boolean flower = false;
					if(allPerformance.getReceivenNum()>groupAvgReceNum){
						param.put("avgnumshow", SHOW);
						int distance = allPerformance.getReceivenNum() - groupAvgReceNum;
						if(groupAvgReceNum>0){
							double parcent = (double)distance/groupAvgReceNum;
							param.put("avgnumdata", doubleToString(parcent,FLOAT_SIZE,true));
						}else{
							param.put("avgnumdata", "100");
						}
						flower = true; 
					}else{
						param.put("avgnumshow", HIDE);
					}
					
					if(allPerformance.getNoreplyNums()>groupAvgNoreplyNum){
						param.put("avgnoreplyshow", SHOW);
						int distance = allPerformance.getNoreplyNums() - groupAvgNoreplyNum;
						if(groupAvgNoreplyNum>0){
							double parcent = (double)distance/groupAvgNoreplyNum;
							param.put("avgnoreplydata", doubleToString(parcent,FLOAT_SIZE,true));
						}else{
							param.put("avgnoreplydata", "100");
						}
						flower = true; 
					}else{
						param.put("avgnoreplyshow", HIDE);
					}
					
					if(allPerformance.getChatpeerNums()>groupAvgChatpeerNum){
						param.put("avgchatpeershow", SHOW);
						int distance = allPerformance.getChatpeerNums() - groupAvgChatpeerNum;
						if(groupAvgChatpeerNum>0){
							double parcent = (double)distance/groupAvgChatpeerNum;
							param.put("avgchatpeerdata", doubleToString(parcent,FLOAT_SIZE,true));
						}else{
							param.put("avgchatpeerdata", "100");
						}
						flower = true; 
					}else{
						param.put("avgchatpeershow", HIDE);
					}
					
					if(allPerformance.getAvgRespTime()>groupAvgRespTime){
						param.put("avgtimeshow", SHOW);
						int distance = allPerformance.getAvgRespTime() - groupAvgRespTime;
						if(groupAvgRespTime>0){
							double parcent = (double)distance/groupAvgRespTime;
							param.put("avgtimedata",doubleToString(parcent,FLOAT_SIZE,true));
						}else{
							param.put("avgtimedata", "100");
						}
						
						flower = true; 
					}else{
						param.put("avgtimeshow", HIDE);
					}
					
					if(allPerformance.getTotalBuyers()>0){
						if(price>groupAvgCusPrice){
							param.put("avgpriceshow", SHOW);
							double distance = price - groupAvgCusPrice;
							param.put("avgpricedata", doubleToString(distance,FLOAT_SIZE,true));
							flower = true; 
						}else{
							param.put("avgpriceshow", HIDE);
						}
						
					}
					if(allPerformance.getReceivenNum()>0){
						if(result>groupAvgSerResult){
							param.put("avgresultshow", SHOW);
							double distance = result - groupAvgSerResult;
							param.put("avgresultdata", doubleToString(distance,FLOAT_SIZE,true));
							flower = true; 
						}else{
							param.put("avgresultshow", HIDE);
						}
						
					}
					if(flower){
						param.put("showflower", SHOW);
					}else{
						param.put("showflower", HIDE);
					}
				}
				param.put("receivenum", String.valueOf(allPerformance.getReceivenNum()));
				param.put("noreplynum", String.valueOf(allPerformance.getNoreplyNums()));
				param.put("chatpeernum", String.valueOf(allPerformance.getChatpeerNums()));
				param.put("customResult", String.valueOf(allPerformance.getCustomResult()));
				param.put("customPrice", String.valueOf(allPerformance.getCustomPrice()));
				param.put("avgRespTime", String.valueOf(allPerformance.getAvgRespTime()));
			}else{
				param.put("showflower", HIDE);
				param.put("receivenum", "0");
				param.put("noreplynum", "0");
				param.put("chatpeernum", "0");
				param.put("customResult", "0");
				param.put("customPrice", "0");
				param.put("avgRespTime", "0");
			}
			
			JSONObject jsp = JSONObject.fromObject(param);
			json.append(jsp.toString());
			response.getWriter().write(json.toString());
		}catch(Exception e){
			logger.error("计算用户["+userid+"]业绩指标失败!");
			e.printStackTrace();
		}
		
		return null;
	}
	
	@RequestMapping("/periodquery")
	@AopLogModule(name=C.LOG_MODULE_WORKTABLE,layer=C.LOG_LAYER_CONTROLLER)
	public String getPeriodPerformanceBy(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		
		response.setCharacterEncoding("UTF-8");
		response.setHeader("ContentType", "text/json");
		String userid  = request.getParameter("userid");
		//userid = "cntaobao良无限home:三叶草";
		String period = request.getParameter("period");
		int type = 0;
		if(DAY.equals(period)){
			type = DAY_TYPE;
		}else if(WEEK.equals(period)){
			type = WEEK_TYPE;
		}else if(MONTH.equals(period)){
			type = MONTH_TYPE;
		}
		StringBuilder json = new StringBuilder("");
		Map<String,String> param = new Hashtable<String, String>();
		try{
			Performance periodPerformance = performanceService.findPeriodPerformanceByUseridAndType(userid, type);
			if(periodPerformance!=null){
				param.put("receiveNum", String.valueOf(periodPerformance.getReceivenNum()));
				param.put("noreplyNum", String.valueOf(periodPerformance.getNoreplyNums()));
				param.put("chatpeerNum", String.valueOf(periodPerformance.getChatpeerNums()));
				param.put("avgRespTime", String.valueOf(periodPerformance.getAvgRespTime()));
				param.put("ranking", String.valueOf(periodPerformance.getRanking()));
				double cusResult = 0;
				if(periodPerformance.getTotalAmount()>0){
					cusResult = (float)periodPerformance.getTotalBuyers()/periodPerformance.getReceivenNum();
				}
				param.put("cusResult", doubleToString(cusResult,FLOAT_SIZE,false));
				double cusPrice = 0;
				if(periodPerformance.getTotalBuyers()>0){
					cusPrice = periodPerformance.getTotalAmount()/periodPerformance.getTotalBuyers();
				}
				param.put("cusPrice", doubleToString(cusPrice,FLOAT_SIZE,false));
				param.put("result", "true");
				JSONObject jsp = JSONObject.fromObject(param);
				json.append(jsp.toString());
			}else{
				json.append("{\"result\":\"false\"}");
			}
			response.getWriter().write(json.toString());
		}catch(Exception e){
			logger.error("计算用户["+userid+"]业绩指标失败!");
			e.printStackTrace();
		}finally{
			response.getWriter().close();
		}
		
		return null;
	}

	
	public static String doubleToString(double srcValue,int length,boolean parcent)
    {
        NumberFormat   formatter   =   NumberFormat.getNumberInstance();
        formatter.setMaximumFractionDigits(length);     //设置最大小数位
        String value = formatter.format(srcValue);
		if(parcent){
			return formatParcent(value);
		}
		return value;
        
    }
	
	
	public static String formatParcent(String value){
		if(value!=null){
			if(value.indexOf(".")<=0){
				return value + "00";
			}
			StringBuilder sb = new StringBuilder("");
			String[] xs = value.split("\\.");
			sb.append(xs[0]);
			String fp = xs[1];
			String bpv = "";
			if(fp.length()<=PARCENT_NUMS){
				sb.append(fp);
				if(fp.length()==1){
					sb.append("0");
				}
				return String.valueOf(Integer.parseInt(sb.toString()));
			}else{
				sb.append(fp.substring(0, PARCENT_NUMS));
				bpv = fp.substring(PARCENT_NUMS, fp.length());
			}
			int ff = Integer.parseInt(sb.toString());
			StringBuilder ffc = new StringBuilder("");
			ffc.append(ff);
			ffc.append(".");
			ffc.append(bpv);
			return ffc.toString();
		}
		return "";
	}
	
	
	
	
	
	private String getUserIdFromCookie(HttpServletRequest res){
		String userid = null;
		Cookie[] cookies = res.getCookies();   
	    if (cookies != null) {   
	        for (Cookie cookie : cookies) {   
	            if (SessionAttribute.USER.equals(cookie.getName())) { 
	                String userInfo = cookie.getValue();
	                if(!StringUtils.isNullOrEmpty(userInfo)){
	                	userid = userInfo.split(",")[0];
	                	break;
	                }
	            }   
	        }
	    }
	    return userid;
	}
	

}
