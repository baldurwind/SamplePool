package com.chamago.pcrm.customerservice.mapper.impl;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.chamago.pcrm.common.utils.ObjectId;
import com.chamago.pcrm.common.utils.PageModel;
import com.chamago.pcrm.common.utils.Utils;
import com.chamago.pcrm.customerservice.mapper.CustomerServiceMapper;
import com.chamago.pcrm.customerservice.pojo.CSICustomAlertTime;
import com.chamago.pcrm.customerservice.pojo.CSIDetail;
import com.chamago.pcrm.customerservice.pojo.CSIStatus;
import com.chamago.pcrm.customerservice.pojo.CSIType;
import com.chamago.pcrm.customerservice.pojo.CustomerService;
import com.chamago.pcrm.customerservice.pojo.CustomerServiceIssues;

/**
 * 售后数据访问实现
 * @author James.wang
 */
public class CustomerServiceMapperImpl extends SqlSessionDaoSupport implements CustomerServiceMapper {
		
	/**
	 * 获取当前店铺下所有的售后类型
	 * @param sellerNick  店铺名称
	 * @return  当前店铺下的售后类型集合 
	 */
	
	@SuppressWarnings("unchecked")
	public List<CSIType> getCSITypeListBySellerNick(String sellerNick) {
		return (List<CSIType>)getSqlSession().selectList("CustomerServiceMapper.getCSITypeListBySellerNick", sellerNick);
	}

	/**
	 * 保存售后问题
	 * @param customerServiceIssues  售后问题实体
	 */
	public void saveCustomerServiceIssueInfo(CustomerServiceIssues customerServiceIssues) throws Exception {
		//过滤script, html, style　代码
		customerServiceIssues.setTitle(Utils.getNoHTMLString(customerServiceIssues.getTitle()));
		customerServiceIssues.setContent(Utils.getNoHTMLString(customerServiceIssues.getContent()));
		getSqlSession().insert("CustomerServiceMapper.saveCustomerServiceIssueInfo", customerServiceIssues);
	}
	
	/**
	 * 保存售后问题处理详细
	 * @param csiDetail  售后问题处理详细实体
	 */
	public void saveCSIDetailInfo(CSIDetail csiDetail) throws Exception {
		csiDetail.setId(ObjectId.get().toString());
		//过滤script, html, style　代码
		if(csiDetail.getRemark() != null) {
			csiDetail.setRemark(Utils.getNoHTMLString(csiDetail.getRemark()));
		}
		getSqlSession().insert("CustomerServiceMapper.saveCSIDetailInfo", csiDetail);
	}

	/**
	 * 获取当前淘宝交易编号的售后问题
	 * @param tradeId  淘宝交易编号
	 * @return　当前淘宝交易编号的售后问题
	 */
	public Object[] getCustomerServiceIssueByCSIId(String CSIId) {
		return (Object[])getSqlSession().selectOne("CustomerServiceMapper.getCustomerServiceIssueByCSIId", CSIId);
	}

	/**
	 * 获取当前交易编号是否存在售后问题
	 * @param tradeId  交易编号
	 * @return  当前交易编号的售后问题个数
	 */
	public String getCustomerServiceIssueCountByTradeId(String tradeId) {
		return (String)getSqlSession().selectOne("CustomerServiceMapper.getCustomerServiceIssueCountByTradeId", tradeId);
	}
	
	/**
	 * 获取当前售后问题的处理详细列表
	 * @param csiId  售后问题标识
	 * @return  处理详细列表
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getCSIDetailByCSIId(String csiId) {
		return (List<Object[]>)getSqlSession().selectList("CustomerServiceMapper.getCSIDetailByCSIId", csiId);
	}
	
	/**
	 * 根据条件查询售后问题列表
	 * @param param  查询条件集合　
	 * @return   售后问题列表
	 */
	@SuppressWarnings("unchecked")
	public void queryCustomerServiceIssuesList(PageModel pageModel, Map<String, Object> param) {
		Integer totalRecords  = (Integer)getSqlSession().selectOne("CustomerServiceMapper.queryCustomerServiceIssuesCountList", param);
		pageModel.setTotalRecords(totalRecords);
		param.put("start", (pageModel.getPageNo() - 1) * pageModel.getPageSize());
		param.put("end", pageModel.getPageSize());
		pageModel.setList((List<Object[]>)getSqlSession().selectList("CustomerServiceMapper.queryCustomerServiceIssuesList", param));
	}

	/**
	 * 查询所有售后问题处理状态列表
	 * @return  售后问题处理状态列表
	 */
	@SuppressWarnings("unchecked")
	public List<CSIStatus> queryCSIStatusList() {
		return getSqlSession().selectList("CustomerServiceMapper.queryCSIStatusList");
	}
	
	/**
	 * 修改售后问题当前的处理状态
	 */
	public void updateCSIStatus(Map<String, String> param) throws Exception {
		getSqlSession().update("CustomerServiceMapper.updateCSIIssuesStatus", param);
	}
	
	/**
	 * 查询特定类型客服的信息
	 * @param type  客服类型
	 * @return      客服信息集合
	 */
	@SuppressWarnings("unchecked")
	public List<CustomerService> queryCustomerServiceByType(int type) {
		return (List<CustomerService>)getSqlSession().selectList("CustomerServiceMapper.queryCustomerServiceByType", type);
	}
	
	/**
	 * 根据客服昵称获取相应编号
	 * @param nick　客服昵称  
	 * @return      客服编号
	 */
	public Integer getCSIdByCSNick(String nick) {
		return (Integer)getSqlSession().selectOne("CustomerServiceMapper.getCSIdByCSNick", nick);
	}
	
	/**
	 * 保存售后问题分配
	 * @param csiId  售后问题标识
	 * @param csId   客服标识
	 */
	public void saveAdmeasure(Map<String, String> param) throws Exception {
		getSqlSession().update("CustomerServiceMapper.saveAdmeasure", param);
	}
	
	/**
	 * 获取当前售后问题最后一步操作信息的ID
	 * @param csiId  售后问题标识
	 * @return       售后问题操作详细ID
	 */
	public String getEndCSIDetailOperateByCSIId(String csiId) {
		return (String)getSqlSession().selectOne("CustomerServiceMapper.getEndCSIDetailOperateByCSIId", csiId);
	}
	
	/**
	 * 修改当前售后问题详细的处理时间
	 * @param id  售后问题详细标识
	 * @return    影响行数
	 */
	public void updateCSIDetailDisposalTime(String id) throws Exception {
		getSqlSession().update("CustomerServiceMapper.updateCSIDetailDisposalTime", id);
	}
	
	/**
	 * 设置当前售后问题处理者为空
	 * @param csiId 售后问题标识
	 * @return    	影响行数
	 */
	public void updateCSIDisposalNickISNUll(String csiId) throws Exception {
		getSqlSession().update("CustomerServiceMapper.updateCSIDisposalNickISNUll", csiId);
	}
	
	/**
	 * 查找需要黄灯与红灯提醒的处理状态
	 * @return  需提示的处理状态集合
	 */
	@SuppressWarnings("unchecked")
	public List<CSIStatus> queryCSIStatusByTipNOTNull() {
		return getSqlSession().selectList("CustomerServiceMapper.queryCSIStatusByTipNOTNull");
	}
	
	/**
	 * 获取售后问题最后一条处理详细信息
	 * @param csiIDs  售后问题标识集合
	 * @return        当前售后最后一条处理详细信息
	 */
	@SuppressWarnings("unchecked")
	public List<CSIDetail> queryEndCSIDetailByCSIIds(String csiIDs) {
		return (List<CSIDetail>)getSqlSession().selectList("CustomerServiceMapper.queryEndCSIDetailByCSIIds", csiIDs);
	}

	/**
	 * 修改售后问题详细亮灯类型
	 * @param id 详细ID
	 * @param lightType 亮灯类型
	 * @return 影响行数
	 */
	public void updateCSIDetailLightType(String id, Integer lightType) throws Exception {
		Map<String, String> param = new Hashtable<String, String>();
		param.put("id", id);
		param.put("lightType", lightType.toString());
		getSqlSession().update("CustomerServiceMapper.updateCSIDetailLightType", param);
	}
	
	/**
	 * 获取CSIDetail 信息
	 * @param id CSIDetail id  
	 * @return   CSIDetail 信息 
	 */
	public CSIDetail getCsiDetailByCSId(String id) {
		return (CSIDetail) getSqlSession().selectOne("CustomerServiceMapper.getCsiDetailByCSId", id);
	}
	
	/**
	 * 获取当前客服未处理的售后问题总数
	 * @param nick 客服昵称
	 * @param 未处理的售后问题总数
	 */
	public Integer getUntreatedCustomerServiceByNick(String nick, String buyer) {
		Map map=new HashMap();
		map.put("nick",nick);
		map.put("buyer", buyer);
		return (Integer) getSqlSession().selectOne("CustomerServiceMapper.getUntreatedCustomerServiceByNick", map);
	}

	/**
	 * 获取CsiStatus详细
	 */
	public CSIStatus getCSIStatusById(String id) {
		return (CSIStatus) getSqlSession().selectOne("CustomerServiceMapper.getCSIStatusById", id);
	}
	
	/**
	 * 删除售后问题信息
	 * @param tradeId  订单编号
	 */
	public void deleteCustomerService(String tradeId) throws Exception {
		getSqlSession().update("CustomerServiceMapper.deleteCustomerService", tradeId);
	}
	
	/**
	 * 添加售后类型
	 * @param csiType 售后类型实体
	 */
	public void saveType(CSIType csiType) throws Exception {
		csiType.setId(ObjectId.get().toString());
		getSqlSession().insert("CustomerServiceMapper.saveType", csiType);
	}
	
	/**
	 * 获取售后类型名称数据个数
	 * @param name 售后类型名称
	 * @return     存在个数
	 */
	public Integer getTypeCountbyName(String name, String sellerNick) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sellerNick", sellerNick);
		map.put("name", name);
		return (Integer)getSqlSession().selectOne("CustomerServiceMapper.getTypeCountbyName", map);
	}

	/**
	 * 删除售后类型
	 * @param id  售后类型标识
	 */
	public void deleteType(String id) throws Exception {
		getSqlSession().update("CustomerServiceMapper.deleteType", id);
	}
	
	/**
	 * 获取售后紧急程度数据个数
	 * @param name 售后紧急程度名称
	 * @param sellerNick 店铺昵称
	 * @return 当然名称的个数
	 */
	public Integer getPriorityCountByName(String name, String sellerNick) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		map.put("sellerNick", sellerNick);
		return (Integer) getSqlSession().selectOne("CustomerServiceMapper.getPriorityCountByName", map);
	}
	
	/**
	 * 删除售后紧急程度
	 * @param  售后紧急程度标识
	 */
	public void deletePriority(String id) throws Exception {
		getSqlSession().update("CustomerServiceMapper.deletePriority", id);
	}
	
	/**
	 * 获取处理状态系统亮灯设置
	 * @return   获取处理状态系统亮灯设置列表
	 */
	@SuppressWarnings("unchecked")
	public List<CSIStatus> getCSIStatusListByLight() {
		return (List<CSIStatus>)getSqlSession().selectList("CustomerServiceMapper.getCSIStatusListByLight");
	}
	
	/**
	 * 保存客服自设提醒设置
	 * @param csiCustomAlertTime 客服自设提醒设置信息
	 */
	public void saveCustomAlertTime(CSICustomAlertTime csiCustomAlertTime) throws Exception {
		csiCustomAlertTime.setId(ObjectId.get().toString());
		getSqlSession().insert("CustomerServiceMapper.saveCustomAlertTime", csiCustomAlertTime);
	}
	
	/**
	 * 获取当前店铺下处理状态提醒时间自设列表
	 * @param sellerNick  店铺昵称
	 * @return 当前店铺下处理状态提醒时间自设列表           
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getCustomAlertTimeBySellerNick(String sellerNick) {
		return (List<Object[]>) getSqlSession().selectList("CustomerServiceMapper.getCustomAlertTimeBySellerNick", sellerNick);
	}
	
	/**
	 * 获取当前售后处理状态在设置中存在的个数
	 * @param id   售后处理状态 
	 * @param sellerNick  店铺昵称
	 * @return  当前售后处理状态在设置中存在的个数
	 */
	public Integer getCustomAlertTimeCountByStatusId(String id, String sellerNick) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("id", id);
		param.put("sellerNick", sellerNick);
		return (Integer) getSqlSession().selectOne("CustomerServiceMapper.getCustomAlertTimeCountByStatusId", param);
	}
	
	/**
	 * 删除客服自设提醒设置
	 * @param id 标识
	 */
	public void deleteCustomAlertTime(String id) throws Exception {
		getSqlSession().update("CustomerServiceMapper.deleteCustomAlertTime", id);
	}
	
	/**
	 * 获取当前店铺提醒时间自设
	 * @param sellerNick  店铺昵称  
	 * @return   店铺提醒时间自设
	 */
	@SuppressWarnings("unchecked")
	public List<CSIStatus> loadCustomAlertTimeBySellerNick(String sellerNick) {
		return (List<CSIStatus>)getSqlSession().selectList("CustomerServiceMapper.loadCustomAlertTimeBySellerNick", sellerNick);
	}
	
	/**
	 * 获取当前类型下售后的个数　
	 * @param type　类型标识 
	 * @return      售后的个数
	 */
	public Integer getCustomerServiceCountByType(String type) {
		return (Integer) getSqlSession().selectOne("CustomerServiceMapper.getCustomerServiceCountByType", type);
	}
	
	/**
	 * 获取当前店铺提醒时间自设
	 * @param sellerNick  店铺昵称  
	 * @return   店铺提醒时间自设
	 */
	@SuppressWarnings("unchecked")
	public List<CSICustomAlertTime> loadCustomAlertTimeBySellerNick2(String sellerNick) {
		return (List<CSICustomAlertTime>) getSqlSession().selectList("CustomerServiceMapper.loadCustomAlertTimeBySellerNick2", sellerNick);
	}
	
	/**
	 * 修改客服提醒设置
	 * @param sellerNick 店铺名称
	 * @param id         状态标识
	 * @param y          黄灯提醒时间
	 * @param r          红灯提醒时间
	 */
	public void UpdateCustomAlertTime(String sellerNick, String id, Integer y, Integer r) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sellerNick", sellerNick);
		param.put("id", id);
		param.put("y", y);
		param.put("r", r);
		getSqlSession().update("CustomerServiceMapper.UpdateCustomAlertTime", param);
	}
	
	/**
	 * 获取当前店铺下买家正在处理的售后问题列表
	 * @param buyerNick   买家昵称
	 * @param sellerNick  店铺昵称
	 * @return 当前店铺下买家正在处理的售后问题列表
	 */
	@SuppressWarnings("unchecked")
	public List<CustomerServiceIssues> getCustomerServiceIssuesByPresent(String buyerNick, String sellerNick) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("buyer", buyerNick);
		param.put("seller", sellerNick);
		return (List<CustomerServiceIssues>) getSqlSession().selectList("CustomerServiceMapper.getCustomerServiceIssuesByPresent", param);
	}
	
	/**
	 * 获取当前店铺下买家未处理的售后问题列表
	 * @param buyerNick   买家昵称
	 * @param sellerNick  店铺昵称
	 * @return 当前店铺下买家未处理的售后问题列表
	 */
	@SuppressWarnings("unchecked")
	public List<CustomerServiceIssues> getCustomerServiceIssuesByUndisposed(String buyerNick, String sellerNick) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("buyer", buyerNick);
		param.put("seller", sellerNick);
		return (List<CustomerServiceIssues>) getSqlSession().selectList("CustomerServiceMapper.getCustomerServiceIssuesByUndisposed", param);
	}
	
	/**
	 * 根据处理集合，获取处理状态
	 * @param operatSql  处理集合
	 * @return   处理状态集合
	 */
	@SuppressWarnings("unchecked")
	public List<CSIStatus> getCSIStatusByOperatSeq(String operatSql) {
		return (List<CSIStatus>)getSqlSession().selectList("CustomerServiceMapper.getCSIStatusByOperatSeq", operatSql.split(","));
	}
	
	/**
	 * 获得当前店铺下的用户列表
	 * @param sellerNick 店铺名称
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getUserListBySellerNick(String sellerNick) {
		return (List<Object[]>) getSqlSession().selectList("CustomerServiceMapper.getUserListBySellerNick", sellerNick);
	}
	
	/**
	 * 获取当前买家在当前店铺下的售后个数
	 */
	public Integer getCustomerServiceCountByBuyerNick(String buyerNick, String sellerNick) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("buyerNick", buyerNick);
		map.put("sellerNick", sellerNick);
		return (Integer) getSqlSession().selectOne("CustomerServiceMapper.getCustomerServiceCountByBuyerNick", map);
	}
	
	/**
	 * 获取当前店铺下所有售后个数
	 * @param sellerNick  店铺名称
	 * @return 当前店铺下所有售后个数
	 */
	public Integer getCustomerServiceCountBySellerNick(String sellerNick) {
		return (Integer) getSqlSession().selectOne("CustomerServiceMapper.getCustomerServiceCountBySellerNick", sellerNick);
	}
	
	/**
	 * 删除售后问题类型
	 * @param id  售后问题类型标识
	 */
	public void deleteCSType(String id) {
		getSqlSession().update("CustomerServiceMapper.deleteCSType", id);
	}
	
	/**
	 * 修改售后问题类型
	 * @param csiType 售后问题类型  
	 */
	public void updateCSIType(CSIType csiType) {
		getSqlSession().update("CustomerServiceMapper.updateCSIType", csiType);
	}
	
	/**
	 * 获取当前售后问题类型详细信息
	 * @param id  标识
	 * @return 当前售后问题类型详细信息
	 */
	public CSIType getCsiTypeById(String id) {
		return (CSIType)getSqlSession().selectOne("CustomerServiceMapper.getCsiTypeById", id);
	}
}