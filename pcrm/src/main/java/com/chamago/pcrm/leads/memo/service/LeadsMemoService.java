package com.chamago.pcrm.leads.memo.service;

import com.chamago.pcrm.common.utils.PageModel;
import com.chamago.pcrm.leads.memo.pojo.LeadsMemo;

/**
 * 缺货/减价事件登记业务接口
 * @author James.wang
 */
public interface LeadsMemoService {
	
	/**
	 * 保存缺货/减价事件登记信息
	 * @param notice  缺货/减价事件登记信息
	 * @return　影响行数
	 */
	public void saveLeadsMemo(LeadsMemo leadsMemo) throws Exception;
	
	/**
	 * 修改缺货/促销事件登记信息
	 * @param notice  缺货/促销事件登记信息实体
	 */
	public void updateLeadsMemo(LeadsMemo leadsMemo) throws Exception;
	
	/**
	 * 获取当前客户的事件登记信息集合
	 * @param pageModel 分页组件
	 * @param customerNick 客户昵称
	 * @param status 信息状态
	 */
	public void getLeadsMemoByParam(PageModel pageModel, String customerNick, String sellerNick, String status) throws Exception;
	
	/**
	 * 获取当前缺货/促销事件登记信息
	 * @param id　信息标识
	 * @return    缺货/促销事件登记信息
	 */
	public LeadsMemo getLeadsMemoById(String id) throws Exception;
	
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
