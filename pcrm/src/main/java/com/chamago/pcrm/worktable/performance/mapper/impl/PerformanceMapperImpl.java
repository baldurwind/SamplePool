/**
 * 
 */
package com.chamago.pcrm.worktable.performance.mapper.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.util.Assert;

import com.chamago.pcrm.worktable.performance.mapper.PerformanceMapper;
import com.chamago.pcrm.worktable.performance.pojo.Performance;
import com.mysql.jdbc.StringUtils;

/**
 * @author gavin.peng
 *
 */
public class PerformanceMapperImpl extends SqlSessionDaoSupport implements PerformanceMapper {

	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.performance.mapper.PerformanceMapper#insertPerformance(com.chamago.pcrm.worktable.performance.pojo.Performance)
	 */
	public int insertPerformance(Performance performance) {
		// TODO Auto-generated method stub
		Assert.notNull(performance);
		return getSqlSession().insert("PerformanceMapper.insertPerformance", performance);
	}

	/* (non-Javadoc)
	 * @see com.chamago.pcrm.worktable.performance.mapper.PerformanceMapper#getPerformanceBySellerAndDate(java.lang.String, java.util.Date)
	 */
	public List<Performance> getPerformanceBySellerAndDate(String seller,
			Date startDate,Date endDate) {
		// TODO Auto-generated method stub
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userid", seller);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return getSqlSession().selectList("PerformanceMapper.findPerformanceByUseridAndDate", params);
	}

	public List<Performance> getPerformanceBySeller(String seller) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("PerformanceMapper.findPerformanceByUserid", seller);
	}

	public int insertAvgPerformance(Performance performance) {

		return getSqlSession().insert("PerformanceMapper.insertAvgPerformance", performance);
	}

	public int insertPeriodPerformance(Performance performance) {
		// TODO Auto-generated method stub
		Assert.notNull(performance);
		return getSqlSession().insert("PerformanceMapper.insertPeriodPerformance", performance);
	}

	public int updateAvgPerformance(Performance performance) {
		// TODO Auto-generated method stub
		return getSqlSession().update("PerformanceMapper.updateAvgPerformance", performance);
	}

	public int findAvgPerformance(long groupId) {
		if(groupId>0){
			Object obj = getSqlSession().selectOne("PerformanceMapper.findAvgPerformanceByGroupId", groupId);
			if(obj!=null){
				return Integer.parseInt(obj.toString());
			}
		}
		return 0;
	}

	public int deleteHisPeriodPerformance(String userid,long type) {
		// TODO Auto-generated method stub
		Assert.notNull(userid);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userid", userid);
		map.put("type", type);
		return getSqlSession().delete("PerformanceMapper.deletePeriodPerformanceByUseridAndType", map);
		
	}

	public List<Performance> findPeriodPerformanceByUseridAndType(String userid, long type) {
		// TODO Auto-generated method stub
		Assert.notNull(userid);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userid", userid);
		map.put("type", type);
		return getSqlSession().selectList("PerformanceMapper.findPeriodPerformanceByUseridAndType", map);
	}

	public List<Object[]> findGroupAvgPerformance(long groupId) {
		if(groupId>0){
			return getSqlSession().selectList("PerformanceMapper.findGroupAvgPerformanceByGroupId", groupId);
		}
		return null;
	}

}
