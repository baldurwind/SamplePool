package com.chamago.pcrm.leads.memo.mapper;

import com.chamago.pcrm.common.utils.PageModel;
import com.chamago.pcrm.leads.memo.pojo.LeadsMemo;
import com.chamago.pcrm.leads.memo.pojo.LeadsMemoReminderLKP;

/**
 * 缺货/促销事件登记数据访问接口
 * @author James.wang
 */
public interface LeadsMemoMapper {
	
	/**
	 * 保存缺货/促销事件登记信息
	 * @param notice  缺货/促销事件登记信息
	 * @return　影响行数
	 */
	public void saveLeadsMemo(LeadsMemo leadsMemo) throws Exception;
	
	/**
	 * 缺货/促销事件登记与客服备忘关联信息
	 * @param noticeReminderLKP 缺货/促销事件登记与客服备忘关联实体
	 */
	public void saveLeadsMemoReminderLKP(LeadsMemoReminderLKP leadsMemoReminderLKP) throws Exception;
	
	/**
	 * 修改缺货/促销事件登记信息
	 * @param notice  缺货/促销事件登记信息实体
	 */
	public void updateLeadsMemo(LeadsMemo leadsMemo) throws Exception;
	
	/**
	 * 根据缺货/促销事件登记信息标识获取客服备注信息标识
	 * @param nid　根据缺货/促销事件登记信息标识
	 * @return 客服备注信息标识
	 */
	public String getReminderIdByLeadsMemoId(String nid);
	
	/**
	 * 获取当前客户的事件登记信息集合
	 * @param pageModel 分页组件
	 * @param customerNick 客户昵称
	 * @param status 信息状态
	 */
	public void getLeadsMemoByParam(PageModel pageModel, String customerNick, String sellerNick, String status);
	
	/**
	 * 获取当前缺货/促销事件登记信息
	 * @param id　信息标识
	 * @return    缺货/促销事件登记信息
	 */
	public LeadsMemo getLeadsMemoById(String id);
	
	/**
	 * 修改当前信息状态
	 * @param id　标识
	 * @param status　状态
	 */
	public void updateStatus(String id, Integer status) throws Exception;
	
	/**
	 * 获取当前买家缺货/促销事件登记信息个数
	 * @param buyerNick 买家昵称
	 * @param sellerNick 卖家昵称
	 * @param status    信息状态
	 * @return          当前买家缺货/促销事件登记信息个数
	 */
	public Integer getLeadsMemoCountByStatus(String buyerNick, String sellerNick, String status);
}
