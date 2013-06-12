/**
 * 
 */
package com.chamago.pcrm.worktable.performance.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.queryParser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.chamago.pcrm.common.utils.ObjectId;
import com.chamago.pcrm.common.utils.Utils;
import com.chamago.pcrm.worktable.performance.pojo.Performance;
import com.chamago.pcrm.worktable.performance.service.PerformanceService;

/**
 * 客服业绩指标统计任务
 * 
 * @author gavin.peng
 * 
 */
@Component("periodPerformanceQuartz")
public class PeriodPerfromanceStaiticsService {

	private static Logger logger = LoggerFactory
			.getLogger(PeriodPerfromanceStaiticsService.class);
	private static int PERIOD_DAY = 1;
	private static int PERIOD_WEEK = 2;
	private static int PERIOD_MONTH = 3;

	private static int WEEK_FIRST_DAY = 2;
	private static int MONTH_FIRST_DAY = 1;
	private static int DAY_MINUTS = 24 * 60;

	@Autowired
	private PerformanceService performanceService;

	public void processAllSellerPerformance() {
		logger.info("开始处理 客服业绩指标");
		List<Object[]> memList = performanceService.getMemberList();
		if (memList != null && memList.size() > 0) {
			Map<String, List<String>> gMap = new HashMap<String, List<String>>();
			List<String> sellerList = null;
			for (Object[] obj : memList) {
				String nick = obj[0].toString();
				long groupid = Long.parseLong(obj[1].toString());
				String groupName = obj[2].toString();
				String userid = obj[3].toString();
				String nick_group_key = nick + "_" + groupName + "_" + groupid;
				if (!gMap.containsKey(nick_group_key)) {
					sellerList = new ArrayList<String>();
					sellerList.add(userid);
					gMap.put(nick_group_key, sellerList);
				} else {
					sellerList.add(userid);
				}
			}
			processGroupPerformance(gMap);
		}

	}

	/**
	 * 统计分好组的客服的周期业绩
	 * 
	 * @param groupKey
	 * @param sellerList
	 * @param statisDate
	 */
	public void processPeriodByGroups(String groupKey, List<String> sellerList,
			Date statisDate) {
		try {
			if (sellerList != null && sellerList.size() > 0) {
				if (statisDate == null) {
					statisDate = new Date();
				} else {
					Calendar cal = Calendar.getInstance();
					cal.setTime(statisDate);
					cal.add(Calendar.MINUTE, DAY_MINUTS);
					statisDate = cal.getTime();
				}
				PeriodProcessTask task = new PeriodProcessTask(groupKey,
						sellerList, statisDate);
				task.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
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
				PeriodProcessTask task = new PeriodProcessTask(groupKey,
						sellerList, Utils.nextDate(DAY_MINUTS));
				task.start();

			}

		} catch (Exception e) {

		}

	}

	public class PeriodProcessTask extends Thread {

		private String groupKey;
		private List<String> sellerList;
		private Date statisDate;
		private boolean processFailed = true;
		private boolean isSleep = false;
		private int SLEEP_TIME = 2;

		public PeriodProcessTask(String groupKey, List<String> sellerList,
				Date statisDate) {
			this.groupKey = groupKey;
			this.sellerList = sellerList;
			this.statisDate = statisDate;
		}

		public void run() {

			while (processFailed) {
				try {
					logger.info("开始处理" + this.groupKey + "组的周期性业绩");
					if (this.sellerList != null && this.sellerList.size() > 0) {
						processPeriodPerformance();
					} else {
						logger.info(this.groupKey + "组没有客服人员");
					}
					processFailed = false;
					logger.info("完成处理" + this.groupKey + "组的周期性业绩");
				} catch (Exception e) {
					logger.error(this.groupKey + "客服人员业绩指标处理失败!系统将过"
							+ SLEEP_TIME + "分钟将重新处理");
					isSleep = true;
					e.printStackTrace();
				}
				if (this.isSleep) {
					try {
						Thread.sleep(1000 * 60 * SLEEP_TIME);
						isSleep = false;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}

		private void processPeriodPerformance() throws Exception {
			List<Performance> dayList = new ArrayList<Performance>();
			List<Performance> weekList = new ArrayList<Performance>();
			List<Performance> monthList = new ArrayList<Performance>();
			for (String userid : this.sellerList) {
				
				// 统计客服seller昨天的业绩;
				Date testDay = this.statisDate;
				Date[] da = getStartAndEndDate(testDay, PERIOD_DAY);
				computerPerformanceByUserAndDate(userid, da[0],
								da[1],PERIOD_DAY,dayList);
				
				// 在星期一运行 统计上一个星期的业绩
				if(checkTaskRun(testDay,PERIOD_WEEK)){
					da = getStartAndEndDate(testDay, PERIOD_WEEK);
					computerPerformanceByUserAndDate(userid, da[0],
							da[1],PERIOD_WEEK,weekList);
				}
				// 在每个月的第一天运行，统计上一个月的业绩
				if(checkTaskRun(testDay,PERIOD_MONTH)){
					da = getStartAndEndDate(testDay, PERIOD_MONTH);
					computerPerformanceByUserAndDate(userid, da[0],
							da[1],PERIOD_MONTH,monthList);
				}
			}

			sortPeriodPerformanceList(dayList);
			sortPeriodPerformanceList(weekList);
			sortPeriodPerformanceList(monthList);

			performanceService.batchInsertPeriodPerformance(dayList);
			performanceService.batchInsertPeriodPerformance(weekList);
			performanceService.batchInsertPeriodPerformance(monthList);
		}

		/**
		 * 计算某个客服某个时间段的业绩
		 * 
		 * @param userid
		 *            客服人员
		 * @param startDate
		 *            起始日期
		 * @param endDate
		 *            结束日期
		 * @param collection
		 *            所有客服的业绩
		 */
		private void computerPerformanceByUserAndDate(String userid,
				Date startDate, Date endDate,int periodType, List<Performance> collection)
				throws Exception {
			Performance monthPer = performanceService
					.getPeriodPerformanceListByUseridAndDate(userid, startDate,
							endDate);
			if (monthPer != null) {
				monthPer.setId(ObjectId.get().toString());
				monthPer.setUserId(userid);
				monthPer.setType(periodType);
				Date today = new Date();
				monthPer.setCreated(today);
				monthPer.setModified(today);
				collection.add(monthPer);
			}
		}

	}

	/**
	 * 判断日期是否是星期一或者一个月的第一天
	 * @param today 日期
	 * @param type 2 判断是否是星期一，3 判断是否是当月第一天
	 * @return 是 true 否 false
	 */
	private boolean checkTaskRun(Date today,int type){
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		if(type == PERIOD_WEEK){
			int weekDay = cal.get(Calendar.DAY_OF_WEEK);
			if (weekDay == WEEK_FIRST_DAY) {
				return true;
			}
			
		}else if(type == PERIOD_MONTH){
			int monthDay = cal.get(Calendar.DAY_OF_MONTH);
			if (monthDay == MONTH_FIRST_DAY) {
				return true;
			}
		}
		return false;
	}
	
	
	private Date[] getStartAndEndDate(Date somedate, int type) {
		Date[] arry = new Date[2];

		Date today = somedate;
		Date startDate = null;
		Date endDate = null;
		if (PERIOD_DAY == type) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(today);
			cal.add(Calendar.MINUTE, -1 * 24 * 60);
			startDate = cal.getTime();
			endDate = startDate;
		} else if (PERIOD_WEEK == type) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(today);
			int distance = cal.get(Calendar.DAY_OF_WEEK);
			if (distance == 1) {
				distance = 7 + 6;
			} else if (distance == 7) {
				distance = 6 + 6;
			} else {
				distance += 5;
			}
			cal.add(Calendar.MINUTE, -distance * 24 * 60);

			startDate = cal.getTime();
			cal.setTime(startDate);
			cal.add(Calendar.MINUTE, 6 * 24 * 60);
			endDate = cal.getTime();

		} else if (PERIOD_MONTH == type) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(today);
			// 当前第几个月 0 代表第一个月
			int months = cal.get(Calendar.MONTH);
			cal.set(Calendar.MONTH, months - 1);
			// 计算上一月的天数
			int predays = cal.getActualMaximum(Calendar.DATE);
			cal.setTime(today);
			// 计算当天为第几天
			int distance = cal.get(Calendar.DAY_OF_MONTH);
			cal.add(Calendar.MINUTE, -(distance - 1 + predays) * 24 * 60);
			startDate = cal.getTime();
			cal.setTime(startDate);
			cal.add(Calendar.MINUTE, (predays - 1) * 24 * 60);
			endDate = cal.getTime();

		}
		arry[0] = startDate;
		arry[1] = endDate;
		return arry;
	}

	/**
	 * 对业绩集合排序
	 * 
	 * @param periodList
	 */
	private void sortPeriodPerformanceList(List<Performance> periodList) {
		if (periodList != null && periodList.size() > 1) {
			Collections.sort(periodList);
			int ranking = 1;
			for (int index = periodList.size() - 1; index >= 0; index--) {
				Performance periodPM = periodList.get(index);
				periodPM.setRanking(ranking);
				if (!periodPM.isEqual()) {
					ranking++;
				}
			}
		}
	}

	public static void main(String ags[]) throws ParseException {
		ApplicationContext ac = Utils.getClassPathXMlApplication();
		PeriodPerfromanceStaiticsService service = ac
				.getBean(PeriodPerfromanceStaiticsService.class);
		try {
			service.processAllSellerPerformance();
			// service.delete("iamid");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
