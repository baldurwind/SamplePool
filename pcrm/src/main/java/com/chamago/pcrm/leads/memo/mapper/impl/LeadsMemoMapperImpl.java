package com.chamago.pcrm.leads.memo.mapper.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.chamago.pcrm.common.utils.ObjectId;
import com.chamago.pcrm.common.utils.PageModel;
import com.chamago.pcrm.leads.memo.mapper.LeadsMemoMapper;
import com.chamago.pcrm.leads.memo.pojo.LeadsMemo;
import com.chamago.pcrm.leads.memo.pojo.LeadsMemoReminderLKP;

/**
 * 缺货/减价事件登记数据访问实现
 * @author James.wang
 */
public class LeadsMemoMapperImpl extends SqlSessionDaoSupport implements LeadsMemoMapper {

	/**
	 * 保存缺货/减价事件登记信息
	 * @param leadsMemo  缺货/减价事件登记信息
	 * @return　影响行数
	 */
	public void saveLeadsMemo(LeadsMemo leadsMemo) throws Exception {
		leadsMemo.setId(ObjectId.get().toString());
		getSqlSession().insert("LeadsMemoMapper.saveLeadsMemo", leadsMemo);
	}
	
	/**
	 * 缺货/减价事件登记与客服备忘关联信息
	 * @param leadsMemoReminderLKP 缺货/减价事件登记与客服备忘关联实体
	 */
	public void saveLeadsMemoReminderLKP(LeadsMemoReminderLKP leadsMemoReminderLKP) throws Exception {
		leadsMemoReminderLKP.setId(ObjectId.get().toString());
		getSqlSession().insert("LeadsMemoMapper.saveLeadsMemoReminderLKP", leadsMemoReminderLKP);
	}
	
	/**
	 * 修改缺货/促销事件登记信息
	 * @param leadsMemo  缺货/促销事件登记信息实体
	 */
	public void updateLeadsMemo(LeadsMemo leadsMemo) throws Exception {
		getSqlSession().update("LeadsMemoMapper.updateLeadsMemo", leadsMemo);
	}
	
	/**
	 * 根据缺货/促销事件登记信息标识获取客服备注信息标识
	 * @param nid　根据缺货/促销事件登记信息标识
	 * @return 客服备注信息标识
	 */
	public String getReminderIdByLeadsMemoId(String lid) {
		return (String)getSqlSession().selectOne("LeadsMemoMapper.getReminderIdByLeadsMemoId", lid);
	}
	
	/**
	 * 获取当前客户的事件登记信息集合
	 * @return 事件登记信息集合
	 */
	@SuppressWarnings("unchecked")
	public void getLeadsMemoByParam(PageModel pageModel, String customerNick, String sellerNick, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("buyerNick", customerNick);
		map.put("sellerNick", sellerNick);
		map.put("status", status);
		int totalRecords = (Integer)getSqlSession().selectOne("LeadsMemoMapper.getLeadsMemoCountByParam", map);
		pageModel.setTotalRecords(totalRecords);
		map.put("start", (pageModel.getPageNo() - 1) * pageModel.getPageSize());
		map.put("end", pageModel.getPageSize());
		pageModel.setList((List<LeadsMemo>) getSqlSession().selectList("LeadsMemoMapper.getLeadsMemoByParam", map));
	}
	
	/**
	 * 获取当前缺货/促销事件登记信息
	 * @param id　信息标识
	 * @return    缺货/促销事件登记信息
	 */
	public LeadsMemo getLeadsMemoById(String id) {
		return (LeadsMemo) getSqlSession().selectOne("LeadsMemoMapper.getLeadsMemoById", id);
	}
	
	/**
	 * 修改当前信息状态
	 * @param id　标识
	 * @param status　状态
	 */
	public void updateStatus(String id, Integer status) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("status", status.toString());
		getSqlSession().update("LeadsMemoMapper.updateStatus", map);
	}

	/**
	 * 获取当前买家缺货/促销事件登记信息个数
	 * @param buyerNick 买家昵称
	 * @param status    信息状态
	 * @return          当前买家缺货/促销事件登记信息个数
	 */
	public Integer getLeadsMemoCountByStatus(String buyerNick, String sellerNick, String status) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("buyerNick", buyerNick);
		map.put("sellerNick", sellerNick);
		map.put("status", status);
		return (Integer)getSqlSession().selectOne("LeadsMemoMapper.getLeadsMemoCountByStatus", map);
	}
}