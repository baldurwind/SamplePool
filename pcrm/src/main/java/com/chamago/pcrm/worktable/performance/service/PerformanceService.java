package com.chamago.pcrm.worktable.performance.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chamago.pcrm.worktable.performance.pojo.Performance;


public interface PerformanceService {
	
	/**
	 * 计算一天内客服接待客户数
	 * @param seller 客服ID
	 * @param today  日期
	 * return 接待的客户数 
	 */
	int processReceiveNum(String seller,Date today) throws Exception ;
	
	/**
	 * 计算一天内客服平均响应时间
	 * @param seller 客服ID
	 * @param today  日期
	 * return 平均响应时间
	 */
	int processResponseTime(String seller,Date today) throws Exception;
	
	/**
	 * 统计一天内客服的交易量：金额，和买家的总数
	 * @param seller 客服ID
	 * @param today  日期
	 * return 数组[],[0] : 一天交易总额，[1]:交易的用户数 
	 */
	Object[] processAvgCusPrice(String seller,Date today) throws Exception;
	
	/**
	 * 取客服人员列表 
	 * @return 
	 */
	List<Object[]> getMemberList();
	
	void processServiceTransform(String seller,Date today) throws Exception;
	
	
	int batchInsertPerformance(List<Performance> list);
	
	
	/**
	 * 新增业绩
	 * @param performance
	 * @return
	 */
	int insertPerformance(Performance performance);
	
	
	
	
	
	
	
	/**
	 * 计算Performance 在List中的排名
	 * @param performance
	 * @param list
	 * @return
	 */
	int computerPerformanceLanking(Performance performance,List<Performance> list);
	
	
	/**
	 * 根据客服ID和日期查该客服的这段时间的排名业绩 
	 * @param seller 客服ID
	 * @param startDate 起始日期
	 * @param endDate 截至日期
	 * @return
	 */
	Performance  getPerformanceBySellerAndDate(String seller,Date startDate,Date endDate) throws ParseException ;
	
	/**
	 * 根据客服ID和日期查该客服的这段时间的业绩 
	 * @param seller 客服ID
	 * @param startDate 起始日期
	 * @param endDate 截至日期
	 * @return Performance
	 * @throws ParseException
	 */
	public Performance getPeriodPerformanceListByUseridAndDate(String userid,
			Date startDate, Date endDate) throws ParseException;
	
	
	
	/**
	 * 查找用户id，周期类型type的业绩	
	 * @param userid 用户ID
	 * @param type 周期类型
	 * @return 
	 */
	Performance findPeriodPerformanceByUseridAndType(String userid,long type);
	
	/**
	 * 根据客服ID查该客服的业绩 
	 * @param seller 客服ID
	 * @return
	 */
	Performance  getPerformanceBySeller(String seller) throws ParseException ;
	
	/**
	 * 根据客服ID查该客服的业绩和天数 
	 * @param seller 客服ID
	 * @return
	 */
	Object[]  getGroupPerformanceBySeller(String seller) throws ParseException ;
	/**
	 * 根据客服ID查该客服的每天业绩
	 * @param seller 客服ID
	 * @return
	 */
	List<Performance>  getPerformanceListBySeller(String seller) throws ParseException ;
	
	/**
	 * 计算团队平均值
	 */
	void computerGroupAvgPerformance(String nick,long groupId,int groupSize,Performance groupPerformance);
	
	/**
	 * 保存团队业绩的平均值
	 * @param performanceMap
	 * @return
	 */
	int insertAvgPerformance(Performance performance);
	
	
	/**
	 * 更新团队平均业绩 
	 * @param performance
	 * @return
	 */
	int updateAvgPerformance(Performance performance);
	
	
	/**
	 * 根据店铺团队ID查找团队平均业绩是否存在 
	 * @param nick 店铺名
	 * @param groupId 分组Id
	 * @return 存在大于0 否则小于0
	 */
	int findAvgPerformance(long groupId);
	
	
	/**
	 * 根据店铺团队ID查找团队平均业绩 
	 * @param groupId 团队id
	 * @return 平均成绩
	 */
	Object[] findGroupAvgPerformance(long groupId);
	
	/**
	 * 删除用户id，周期类型type的业绩	
	 * @param userid 用户ID
	 * @param type 周期类型
	 * @return 
	 */
	int deleteHisPeriodPerformance(String userid,long type);
	
	/**
	 * 保存客服周期业绩
	 * @param performanceMap
	 * @return
	 */
	int insertPeriodPerformance(Performance performance);
	
	
	/**
	 * 批量保存客服周期业绩
	 * @param performanceMap
	 * @return
	 */
	int batchInsertPeriodPerformance(List<Performance> performanceList) throws Exception;
	
	
	/**
	 * 取客服member所属组号group_id
	 * @param member 客服
	 * @return 组id
	 */
	long getGroupIdByMember(String member);


}
