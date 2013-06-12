 /**
 * 
 */
package com.chamago.pcrm.worktable.performance.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chamago.pcrm.worktable.performance.pojo.Performance;


/**
 * @author gavin.peng
 *
 */
public interface PerformanceMapper {
	
	/**
	 * 新增业绩 
	 * @param performance
	 * @return
	 */
	int insertPerformance(Performance performance);
	
	/**
	 * 新增团队平均业绩 
	 * @param performance
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
	 * @param groupId 团队id
	 * @return 如果存在 返回 >0 不存在 ：<=0
	 */
	int findAvgPerformance(long groupId);
	
	/**
	 * 根据店铺团队ID查找团队平均业绩 
	 * @param nick 店铺名
	 * @param groupId 团队id
	 * @return 平均成绩
	 */
	List<Object[]> findGroupAvgPerformance(long groupId);
	
	/**
	 * 用户id，周期类型type的业绩	
	 * @param userid 用户ID
	 * @param type 周期类型
	 * @return 
	 */
	List<Performance> findPeriodPerformanceByUseridAndType(String userid,long type);
	
	
	/**
	 * 删除用户id，周期类型type的业绩	
	 * @param userid 用户ID
	 * @param type 周期类型
	 * @return 
	 */
	int deleteHisPeriodPerformance(String userid,long type);
	
	
	/**
	 * 新增个人周期业绩 
	 * @param performance
	 * @return
	 */
	int insertPeriodPerformance(Performance performance);
	
	/**
	 * 根据客服ID和日期查该客服的这段时间的业绩 
	 * @param seller 客服ID
	 * @param startDate 起始日期
	 * @param endDate 截至日期
	 * @return
	 */
	List<Performance>  getPerformanceBySellerAndDate(String seller,Date startDate,Date endDate);
	
	
	/**
	 * 根据客服ID查该客服的业绩 
	 * @param seller 客服ID
	 * @return
	 */
	List<Performance>  getPerformanceBySeller(String seller);


}
