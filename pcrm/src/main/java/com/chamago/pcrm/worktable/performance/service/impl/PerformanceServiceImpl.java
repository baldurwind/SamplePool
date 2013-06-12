package com.chamago.pcrm.worktable.performance.service.impl;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.chamago.pcrm.common.utils.Utils;
import com.chamago.pcrm.trade.mapper.TradeMapper;
import com.chamago.pcrm.worktable.connect.service.SysMgtService;
import com.chamago.pcrm.worktable.performance.mapper.EserviceMapper;
import com.chamago.pcrm.worktable.performance.mapper.PerformanceMapper;
import com.chamago.pcrm.worktable.performance.pojo.Performance;
import com.chamago.pcrm.worktable.performance.service.PerformanceService;
import com.mysql.jdbc.StringUtils;

@Service
public class PerformanceServiceImpl implements PerformanceService {
	
	private static Logger logger = LoggerFactory.getLogger(PerformanceServiceImpl.class);
	@Autowired
	private EserviceMapper eserviceMapper;
	@Autowired
	private TradeMapper  tradeMapper;
	@Autowired
	private PerformanceMapper performanceMapper;
	
	@Autowired
	private SysMgtService sysMgtService;
	
	public int processReceiveNum(String seller, Date today) throws Exception {
		 
		if(StringUtils.isNullOrEmpty(seller)){
			throw new Exception("参数客服seller为空!");
		}
		if(null==today){
			throw new Exception("参数today为空!");
		}
		try{
			List<Object[]> list = eserviceMapper.getReceiveNums("", seller, today);
			if(list!=null&&list.size()>0){
				Object[] obj = list.get(0);
				return Integer.parseInt(obj[0].toString());
			}
		}catch(Exception e){
			logger.error("计算客服["+seller+"]于["+Utils.parseDate(today)+"]的接待客户数失败");
			e.printStackTrace();
		}
		return 0;

	}

	public int processResponseTime(String seller, Date today)throws Exception  {
		if(StringUtils.isNullOrEmpty(seller)){
			throw new Exception("参数客服seller为空!");
		}
		if(null==today){
			throw new Exception("参数today为空!");
		}
		try{
			List<Object[]> list = eserviceMapper.getResponseTime("", seller, today);
			if(list!=null&&list.size()>0){
				Object[] obj = list.get(0);
				return Integer.parseInt(obj[0].toString());
			}
		}catch(Exception e){
			logger.error("计算客服["+seller+"]于["+Utils.parseDate(today)+"]的平均响应时间失败");
			e.printStackTrace();
		}
		return 0;
	}

	public Object[] processAvgCusPrice(String seller, Date today)throws Exception {
		if(StringUtils.isNullOrEmpty(seller)){
			throw new Exception("参数客服seller为空!");
		}
		if(null==today){
			throw new Exception("参数today为空!");
		}
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			String curDate = Utils.DateToString(today, "yyyy-MM-dd");
			String startTime = Utils.DateToString(today, "yyyy-MM-dd")+" 00:00:00";
			String endTime = Utils.DateToString(today, "yyyy-MM-dd")+" 23:59:59";
			//取客服上班时间产生的订单
			//TODO 目前订单的时间不考虑在用户上班的时间内,绩效和旺旺关联，以后继续和用户关联需要查询用户打卡的起始时间
			String sellerNick = seller.split(":")[0];
			params.put("seller", sellerNick);
			params.put("nick", seller);
			params.put("startTime", startTime);
			params.put("endTime", endTime);
			params.put("curDate", curDate);
			
			if(seller.equals("良无限home:向日葵")){
				System.out.println(seller);
			}
			
			//object[] 包过created,pay_time,payment,buyer_nick的值
			//查找该日期店铺sellerNick和seller有聊过天的卖家下的单
			List<Object[]> list = tradeMapper.findPerformanceParamsBySeller(params);
			if(list!=null&&list.size()>0){
				Object[] rs = new Object[2];
				float totalAmount = 0;
				int totalBuyers = 0;
//					String todayTime = Utils.DateToString(new Date(), "yyyy-MM-dd");
//					Date startDate = Utils.formatDate(today+" 00:00:00");
				for(Object[] obj:list){
					Date created = Utils.StringToDate(obj[0].toString(), "yyyy-MM-dd HH:mm:ss");
					Date paytime = obj[1]==null? null:Utils.StringToDate(obj[1].toString(), "yyyy-MM-dd HH:mm:ss");
					String buyer = obj[3]==null? null:obj[3].toString();
					//该订单买家下单前聊天次数最多的客服
					String preCnick = null;
//						int preCChatNums = 0;'
					//该订单买家付款前聊天次数最多的客服
					String prePnick = null;
//						int prePChatNums = 0;
					//跟据订单的创建日期去查找该日期的买家聊天日志记录，以免昨天下单，今天付款的没有统计到
					String orderCreateDate = Utils.DateToString(created, "yyyy-MM-dd");
					String chatLogStartTime = orderCreateDate + " 00:00:00";
					String chatLogEndTime = orderCreateDate + " 23:59:59";
					Map<String,Object> mmaps = new HashMap<String,Object>();
					mmaps.put("buyer", buyer);
					mmaps.put("statusTime", created);
					mmaps.put("startDate", chatLogStartTime);
					mmaps.put("endDate", chatLogEndTime);
					mmaps.put("interval", 0);
					//先按创建时间
					List<Object[]> chatlist = eserviceMapper.getNickFromChatRecordDetailByBuyerAndChattime(mmaps);
					//如果chatlist为空，则说明该买家下单前没有跟客服聊过。则看付款的时间前后的聊天记录
					if(chatlist!=null&&chatlist.size()>0){
						preCnick = chatlist.get(0)[0].toString();
						//preCChatNums = Integer.parseInt(chatlist.get(0)[1].toString());
					}
					if(paytime!=null){
						mmaps.put("statusTime", paytime);
						chatlist = eserviceMapper.getNickFromChatRecordDetailByBuyerAndChattime(mmaps);
						if(chatlist!=null&&chatlist.size()>0){
							prePnick = chatlist.get(0)[0].toString();
							//prePChatNums = Integer.parseInt(chatlist.get(0)[1].toString());
						}
					}
					if(preCnick==null&&prePnick==null){
						continue;
					}else if(preCnick==null&&prePnick!=null&&prePnick.equals(seller)){
						//下单前没有聊天记录，下单后付款前有聊天记录。而且是当前客服seller，则该笔金额算到付款前有过聊天的客服。
						totalAmount += Float.parseFloat(obj[2].toString());
						totalBuyers ++;
					}else if(preCnick!=null&&prePnick!=null){
						//下单前有聊天记录，下单后付款有聊天记录。
						if(preCnick.equals(prePnick)&&prePnick.equals(seller)){
							//是和同一个人聊天,而且是当前客服seller
							totalAmount += Float.parseFloat(obj[2].toString());
							totalBuyers ++;
						}else{
							//当前客服seller在该买家下单前或者下单后付款前聊天最多，金额算一半,如果是付款后才有聊天则不算。
							if(preCnick.equals(seller)||prePnick.equals(seller)){
								totalAmount += Float.parseFloat(obj[2].toString())/2;
								totalBuyers ++;
							}
						}
						
					}
					
				}
				rs[0] = totalAmount;
				rs[1] = totalBuyers;
				return rs;
			}
			
		}catch(Exception e){
			logger.error("计算客服["+seller+"]于["+Utils.parseDate(today)+"]的交易金额和客户数失败");
			e.printStackTrace();
		}
		return null;
	}

	public void processServiceTransform(String seller, Date today) throws Exception {
		// TODO Auto-generated method stub

	}

	public List<Object[]> getMemberList() {
		return eserviceMapper.getCustomMemeberList();
	}

	public int batchInsertPerformance(List<Performance> list) {
		for(Performance obj:list){
			try{
				if(obj!=null){
					List<Performance> recordlist = performanceMapper.getPerformanceBySellerAndDate(obj.getUserId(), obj.getDate(), obj.getDate());
					if(recordlist!=null&&recordlist.size()>0){
						logger.info("客服["+obj.getUserId()+"]于["+obj.getDate()+"]的业绩指标已经存在!");
						continue;
					}
					performanceMapper.insertPerformance(obj);
				}
			}catch(Exception e){
				e.printStackTrace();
				return -1;
			}
		}
		
		return 0;
	}

	
	
	public Performance getPerformanceBySellerAndType(String seller,
			int type) throws ParseException {
	    List<Performance> perList = performanceMapper.findPeriodPerformanceByUseridAndType(seller, type);
	    if(perList!=null&&perList.size()>0){
		  return perList.get(0);
	    }
		
		return null;
	}
	
	public Performance getPeriodPerformanceListByUseridAndDate(String userid,
			Date startDate, Date endDate) throws ParseException {
		
		  startDate = Utils.StringToDate(Utils.parseDate(startDate), "yyyy-MM-dd");
		  endDate = Utils.StringToDate(Utils.parseDate(endDate), "yyyy-MM-dd");
		  List<Performance> perList = performanceMapper.getPerformanceBySellerAndDate(userid, startDate, endDate);
		  if(perList!=null&&perList.size()>0){
			  return statisPerformance(perList);
		  }
		
		return null;
	}
	
	
	
	private Performance statisPerformance(List<Performance> perList){
		
		if(perList!=null&&perList.size()==1){
			return perList.get(0);
		}else if(perList!=null&&perList.size()>1){
			Performance performance = new Performance();
			int receiveNums = 0;
			int avgRespTime = 0; 
			double totalAmount = 0;
			int totalBuyers = 0;
			int noreplyNums = 0;
			int chatpeerNums = 0;
			for(Performance per:perList){
				receiveNums += per.getReceivenNum();
				avgRespTime += per.getAvgRespTime();
				totalAmount += per.getTotalAmount();
				totalBuyers += per.getTotalBuyers();
				noreplyNums += per.getNoreplyNums();
				chatpeerNums += per.getChatpeerNums();
			}
			performance.setReceivenNum(receiveNums);
			performance.setNoreplyNums(noreplyNums);
			performance.setChatpeerNums(chatpeerNums);
			performance.setAvgRespTime(avgRespTime/perList.size());
			performance.setTotalAmount(totalAmount);
			performance.setTotalBuyers(totalBuyers);
			return performance;
		}
		return null;
		
	}
	
	private Performance statisGroupPerformance(List<Performance> perList){
		
		if(perList!=null&&perList.size()==1){
			return perList.get(0);

		}else if(perList!=null&&perList.size()>1){
			Performance performance = new Performance();
			int receiveNums = 0;
			int avgRespTime = 0; 
			float totalAmount = 0;
			int totalBuyers = 0;
			for(Performance per:perList){
				receiveNums += per.getReceivenNum();
				avgRespTime += per.getAvgRespTime();
				totalAmount += per.getTotalAmount();
				totalBuyers += per.getTotalBuyers();
			}
			performance.setReceivenNum(receiveNums);
			performance.setAvgRespTime(avgRespTime);
			performance.setTotalAmount(totalAmount);
			performance.setTotalBuyers(totalBuyers);
			return performance;
		}
		return null;
		
	}

	public int computerPerformanceLanking(Performance performance,
			List<Performance> list) {
		
		if(list!=null&&list.size()>0){
			Collections.sort(list);
			Map<String,Performance> lankMap = new HashMap<String,Performance>();
			int rank = 1;
			for(Performance per:list){
				per.setRanking(rank);
				lankMap.put(per.getUserId(), per);
				rank ++;
			}
			Performance perf = lankMap.get(performance.getUserId());
			if(perf!=null){
				return perf.getRanking();
			}
		}
		
		return 0;
	}

	public Performance getPerformanceBySeller(String seller)
			throws ParseException {
		List<Performance> perList = performanceMapper.getPerformanceBySeller(seller);
		if(perList!=null&&perList.size()>0){
			return statisPerformance(perList);
		}
		return null;
		
	}

	public Object[] getGroupPerformanceBySeller(String seller)
		throws ParseException {
			Object[] obj = null;
			List<Performance> perList = performanceMapper.getPerformanceBySeller(seller);
			if(perList!=null&&perList.size()>0){
				obj = new Object[2];
				obj[0] = statisGroupPerformance(perList);
				obj[1] = perList.size();
			}
			return obj;
			
	}


	public void computerGroupAvgPerformance(String nick,long groupId,int groupSize,Performance groupPerformance) {
		if(groupPerformance!=null){
			
		}
		
		
	}

	public int insertPeriodPerformance(Performance performance) {
		return performanceMapper.insertPeriodPerformance(performance);
	}

	public int insertAvgPerformance(Performance performance) {
		return performanceMapper.insertAvgPerformance(performance);
	}

	public int updateAvgPerformance(Performance performance) {
		return performanceMapper.updateAvgPerformance(performance);
	}

	public int findAvgPerformance(long groupId) {
		
		return performanceMapper.findAvgPerformance(groupId);
	}

	public int batchInsertPeriodPerformance(List<Performance> performanceList)
			throws Exception {
		if(performanceList!=null&&performanceList.size()>0){
			for(Performance per:performanceList){
					this.deleteHisPeriodPerformance(per.getUserId(), per.getType());
					this.insertPeriodPerformance(per);
			}
		}
		return 0;
	}

	public int deleteHisPeriodPerformance(String userid, long type) {
		
			return performanceMapper.deleteHisPeriodPerformance(userid, type);
		
	}

	public Performance getPerformanceBySellerAndDate(String seller,
			Date startDate, Date endDate) throws ParseException {
		List<Performance> list =  performanceMapper.getPerformanceBySellerAndDate(seller, startDate, endDate);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public Performance findPeriodPerformanceByUseridAndType(
			String userid, long type) {
		
		if(StringUtils.isNullOrEmpty(userid)){
			return null;
		}
		List<Performance> list = performanceMapper.findPeriodPerformanceByUseridAndType(userid, type);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public Object[] findGroupAvgPerformance(long groupId) {

		List<Object[]> list = performanceMapper.findGroupAvgPerformance(groupId);
		if(list!=null&&list. size()>0){
			return list.get(0);
		}
		return null;
	}

	public long getGroupIdByMember(String member) {
		if(StringUtils.isNullOrEmpty(member)){
			return 0l;
		}
		return eserviceMapper.getGroupIdByMember(member);
	}

	public List<Performance> getPerformanceListBySeller(String seller)
			throws ParseException {
		if(StringUtils.isNullOrEmpty(seller)){
			return null;
		}
		return performanceMapper.getPerformanceBySeller(seller);
	}

	@Override
	public int insertPerformance(Performance performance) {
		Assert.notNull(performance);
		return performanceMapper.insertPerformance(performance);
	}
	

}
