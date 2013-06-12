/**
 * 
 */
package com.chamago.pcrm.worktable.performance.job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.queryParser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.chamago.pcrm.common.utils.ObjectId;
import com.chamago.pcrm.common.utils.Utils;
import com.chamago.pcrm.worktable.connect.pojo.LoginLog;
import com.chamago.pcrm.worktable.connect.service.SysMgtService;
import com.chamago.pcrm.worktable.performance.pojo.Performance;
import com.chamago.pcrm.worktable.performance.service.PerformanceService;

/**
 * 客服业绩指标统计任务
 * 默认统计前3天的数据
 * @author gavin.peng
 * 
 */
@Component("customerPerformanceQuartz")
public class PerfromanceProcessService {

	private static Logger logger = LoggerFactory
			.getLogger(PerfromanceProcessService.class);
	private static int DAY_MINUTS = -24 * 60;

	@Autowired
	private PerformanceService performanceService;

	
	@Autowired
	private PeriodPerfromanceStaiticsService periodPerformanceQuartz;
	@Autowired
	private SysMgtService sysMgtService;

	// 统计日期
	private List<Date> statisDates;
	
	final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
	final ThreadPoolExecutor executor = new ThreadPoolExecutor(9,10,30,TimeUnit.SECONDS,queue,Executors.defaultThreadFactory(),
																			new ThreadPoolExecutor.AbortPolicy());

	public List<Date> getStatisDate() {
		return statisDates;
	}
 
	public void setStatisDate(List<Date> statisDates) {
		this.statisDates = statisDates;
	}

	public void processAllSellerPerformance() {
		List<Object[]> memList = performanceService.getMemberList();
		if (memList != null && memList.size() > 0) {
			Map<String, List<String>> gMap = new HashMap<String, List<String>>();
			List<String> sellerList = null;
			for (Object[] obj : memList) {
				String nick = obj[0].toString();
				long groupid = Long.parseLong(obj[1].toString());
				String groupName = obj[2].toString();
				String seller = obj[3].toString();
				String nick_group_key = nick + "_" + groupName + "_" + groupid;
				if (!gMap.containsKey(nick_group_key)) {
					sellerList = new ArrayList<String>();
					sellerList.add(seller);
					gMap.put(nick_group_key, sellerList);
				} else {
					sellerList.add(seller);
				}
			}
			processGroupPerformance(gMap);
		}

	}

	/**
	 * 分组处理客服人员业绩指标
	 * 
	 * @param groupMap
	 */
	private void processGroupPerformance(Map<String, List<String>> groupMap) {

		try {
			Set<String> keySet = groupMap.keySet();
			Iterator<String> it = keySet.iterator();
			while (it.hasNext()) {
				String groupKey = it.next();
				List<String> sellerList = groupMap.get(groupKey);
				List<Date> statisDate = getStatisDate();
				List<Date> cloneDateList = null;
				if(statisDate!=null){
					cloneDateList = new ArrayList<Date>();
					for(Date sd:statisDate){
						cloneDateList.add((Date)sd.clone());
					}
				}
				ProcessTask task = new ProcessTask(groupKey, sellerList,
						cloneDateList);
				executor.execute(task);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public class ProcessTask implements Runnable {

		private String groupKey;
		private List<String> sellerList;
		private int statisDays = 3;
		private List<Date> grouStatisDates;
		private boolean processFailed = true;
		private boolean isSleep = false;
		private int SLEEP_TIME = 2;
		private int MAX_PROCESS_NUMS = 5;
		private Map<String,Date> periodDate = new HashMap<String,Date>();

		public ProcessTask(String groupKey, List<String> sellerList,
				List<Date> statisDates) {
			this.groupKey = groupKey;
			this.sellerList = sellerList;
			this.grouStatisDates = statisDates;
		}

		public void run() {
			int processNums = 0;
			// 统计日期,默认统计前3天的数据
			if(this.grouStatisDates == null){
				List<Date> statisDates = new ArrayList<Date>();
				for(int i=0;i<statisDays;i++){
					statisDates.add(Utils.nextDate(DAY_MINUTS*(statisDays-i)));
				}
				this.grouStatisDates = statisDates;
			}
			while (processFailed&&processNums<=MAX_PROCESS_NUMS) {
				try {
					if (this.sellerList != null && this.sellerList.size() > 0) {
						for(Date statisDate:this.grouStatisDates){
							// 统计昨天的业绩指标
							logger.info("开始统计"+ this.groupKey+"["+Utils.DateToString(statisDate, "yyyy-MM-dd")+"]日的业绩数据");
							List<String> sysUserList = processLastDayPerformance(this.sellerList,
									statisDate);
							// 进行周期性业绩统计
							//防止异常出现后，重复发起线程计算已经在处理的该天的周期性的业绩 
							if(!periodDate.containsKey(Utils.DateToString(statisDate, "yyyy-MM-dd"))){
								periodPerformanceQuartz.processPeriodByGroups(
										this.groupKey, sysUserList,
										(Date)statisDate.clone());
								periodDate.put(Utils.DateToString(statisDate, "yyyy-MM-dd"), statisDate);
							}
							// 统计客服组每个人员总的业绩
							processGroupAveragePerformance(sysUserList);
							this.grouStatisDates.remove(statisDate);
							logger.info("完成统计"+this.groupKey+"["+Utils.DateToString(statisDate, "yyyy-MM-dd")+"]日的数据");
						}
					}
					processFailed = false;

					logger.info("完成处理" + this.groupKey + "组的业绩");
				} catch (Exception e) {
					logger.error(this.groupKey + "客服人员业绩指标处理失败! 系统将重新处理");
					processNums++;
					isSleep = true;
					e.printStackTrace();
				}
				if(this.isSleep){
					try {
						Thread.sleep(1000*60*SLEEP_TIME);
						isSleep = false;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			if(processNums>MAX_PROCESS_NUMS){
				logger.info("系统努力处理了"+processNums+"次还是失败了");
			}
		}

		private void processSellerPerformance(String seller,
				List<Performance> perList, Date statisDate) throws Exception {

			Performance performance = new Performance();
			int receiveNums = performanceService.processReceiveNum(seller,
					statisDate);
			int noreplyNums = sysMgtService.getStaffNoreplyNums(seller,
					statisDate,statisDate);
			int chatpeerNums = sysMgtService.getChatpeersNums(seller,
					statisDate,statisDate);
			int avgResponseTimes = performanceService.processResponseTime(
					seller, statisDate);
			Object[] tradeInfo = performanceService.processAvgCusPrice(seller,
					statisDate);
			performance.setId(ObjectId.get().toString());
			performance.setUserId(seller);
			performance.setReceivenNum(receiveNums);
			performance.setAvgRespTime(avgResponseTimes);
			performance.setNoreplyNums(noreplyNums);
			performance.setChatpeerNums(chatpeerNums);
			if (tradeInfo != null) {
				performance.setTotalAmount(Float.parseFloat(tradeInfo[0]
						.toString()));
				performance.setTotalBuyers(Integer.parseInt(tradeInfo[1]
						.toString()));
			} else {
				performance.setTotalAmount(0);
				performance.setTotalBuyers(0);
			}
			performance
					.setDate(Utils.StringToDate(
							Utils.DateToString(statisDate, "yyyy-MM-dd"),
							"yyyy-MM-dd"));
			performance.setCreated(new Date());
			performance.setModified(new Date());
			perList.add(performance);

		}

		/**
		 * 处理一天的业绩
		 * 
		 * @param sellerList
		 * @throws Exception
		 */
		private List<String> processLastDayPerformance(List<String> sellerList,
				Date statisDate) throws Exception {
			
			List<String> sysUserList = new ArrayList<String>();
			List<Performance> perList = new ArrayList<Performance>();
			for (String seller : sellerList) {
				try{
					// 统计客服seller昨天的业绩;假如其中一个统计失败后，继续统计下一个.
					processSellerPerformance(seller, perList, statisDate);
					
				}catch(Exception e){
					logger.error("计算"+seller+"_"+Utils.DateToString(statisDate, "yyyy-MM-dd")+"出错");
					e.printStackTrace();
				}
			}
			String nick = sellerList.get(0);
			String seller = nick;
			if(nick!=null&&nick.contains(":")){
				seller = nick.split(":")[0];
			}
			Map<String,String> userMaps = new HashMap<String,String>();
			List<Object[]> userList = sysMgtService.getMembersBySeller(seller);
			if(userList!=null){
				for(Object[] obj:userList){
					userMaps.put(obj[1].toString(), obj[0].toString());
				}
			}
			
			for(Performance per:perList){
				//根据旺旺关联到ID
				try{
					String userId = userMaps.get(per.getUserId());
					if(userId!=null){
						per.setUserId(userId);
						sysUserList.add(userId);
						//开始入库，如果失败，继续下一个入库
						Performance oldPer = performanceService.getPerformanceBySellerAndDate(per.getUserId(), per.getDate(), per.getDate());
						if(oldPer!=null){
							logger.info("客服["+per.getUserId()+"]于["+per.getDate()+"]的业绩指标已经存在!");
							continue;
						}
						performanceService.insertPerformance(per);
					}
					//一个客服处理成功了，就从列表删除，在因为异常退出的时刻，就不会再处理成功的客服了。 
					//sellerList.remove(nick);
					
				}catch(Exception e){
					logger.error(seller+"_"+Utils.DateToString(statisDate, "yyyy-MM-dd")+"入库失败");
					e.printStackTrace();
				}
				
			}
			// 保存该客服组每人昨天的业绩
			//performanceService.batchInsertPerformance(perList);
			return sysUserList;
		}

		/**
		 * 计算团队最新的平均值
		 * 
		 * @param sellerList
		 * @throws Exception
		 */
		private void processGroupAveragePerformance(List<String> sellerList)
				throws Exception {
			List<Performance> groupList = new ArrayList<Performance>();
			List<Integer> sellerDayList = new ArrayList<Integer>();
			for (String seller : sellerList) {
				// 统计该客服seller总的的业绩;
				Object[] pcObject = performanceService.getGroupPerformanceBySeller(seller);
				if(pcObject!=null){
					Performance totalPer = (Performance)pcObject[0];
					groupList.add(totalPer);
					sellerDayList.add(Integer.parseInt(pcObject[1].toString()));
				}
				
			}
			int avgDays = 0;
			for(Integer day:sellerDayList){
				avgDays +=day.intValue();
			}
			
			// 计算团队各项指标总的平均值
			Performance groupPerformance = computerGroupAvgPerformance(groupList,avgDays);
			if (groupPerformance != null) {
				String[] info = this.groupKey.split("_");
				if (info != null && info.length > 0) {
					String nick = info[0];
					long groupId = Long.parseLong(info[info.length-1]);
					groupPerformance.setNick(nick);
					groupPerformance.setGroupId(groupId);
					groupPerformance.setModified(new Date());

					int rs = performanceService.findAvgPerformance(groupId);
					if (rs > 0) {
						// 已经计算过，则更新
						performanceService
								.updateAvgPerformance(groupPerformance);
					} else {
						// 新增
						groupPerformance.setCreated(new Date());
						performanceService
								.insertAvgPerformance(groupPerformance);
					}
				}
			}
		}

	}

	
	private void performanceSum(Performance src,Performance target){
		if(src == null || target == null){
			return ;
		}
		int	receiveNums = target.getReceivenNum() + src.getReceivenNum();
		int	avgRespTime = target.getAvgRespTime()+src.getAvgRespTime();
		double	totalAmount = target.getTotalAmount() + src.getTotalAmount();
		int	totalBuyers = target.getTotalBuyers() + src.getTotalBuyers();
		target.setReceivenNum(receiveNums);
		target.setAvgRespTime(avgRespTime/2);
		if (totalBuyers > 0) {
			float totalCusPrice = (float) totalAmount / totalBuyers;
			target.setCustomPrice(totalCusPrice);
		}
		if (receiveNums > 0) {
			float totalCusResult = (float) totalBuyers / receiveNums;
			target.setCustomResult((float) totalCusResult);
		}

	}
	
	private Performance computerGroupAvgPerformance(List<Performance> perList,int totalDays) {

		if (perList != null && perList.size() == 1) {
			return perList.get(0);
		} else if (perList != null && perList.size() > 1) {
			int groupSize = perList.size();
			Performance performance = new Performance();
			int receiveNums = 0;
			int avgRespTime = 0;
			double totalAmount = 0;
			int totalBuyers = 0;
			int noreplyNums = 0;
			int chatpeerNums = 0;
			for (Performance per : perList) {
				receiveNums += per.getReceivenNum();
				avgRespTime += per.getAvgRespTime();
				totalAmount += per.getTotalAmount();
				totalBuyers += per.getTotalBuyers();
				noreplyNums += per.getNoreplyNums();
				chatpeerNums += per.getChatpeerNums();
			}
			performance.setReceivenNum(receiveNums / groupSize);
			performance.setNoreplyNums(noreplyNums / groupSize);
			performance.setChatpeerNums(chatpeerNums / groupSize);
			performance.setAvgRespTime(avgRespTime / totalDays);
			if (totalBuyers > 0) {
				float totalCusPrice = (float) totalAmount / totalBuyers;
				performance.setCustomPrice((float) totalCusPrice);
			}
			if (receiveNums > 0) {
				float totalCusResult = (float) totalBuyers / receiveNums;
				performance.setCustomResult((float) totalCusResult);
			}

			return performance;

		}
		return null;

	}

	public static void main(String ags[]) throws ParseException {
		ApplicationContext ac = Utils.getClassPathXMlApplication();
		PerfromanceProcessService customerPerformanceQuartz = ac
				.getBean(PerfromanceProcessService.class);
		try {
			List<Date> statisDates = new ArrayList<Date>();
//			statisDates.add(Utils.StringToDate("2012-07-13", "yyyy-MM-dd"));
//			customerPerformanceQuartz.setStatisDate(statisDates);
			customerPerformanceQuartz.processAllSellerPerformance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
