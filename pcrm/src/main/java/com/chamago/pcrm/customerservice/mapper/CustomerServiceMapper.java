package com.chamago.pcrm.customerservice.mapper;

import java.util.List;
import java.util.Map;

import com.chamago.pcrm.common.utils.PageModel;
import com.chamago.pcrm.customerservice.pojo.CSICustomAlertTime;
import com.chamago.pcrm.customerservice.pojo.CSIDetail;
import com.chamago.pcrm.customerservice.pojo.CSIStatus;
import com.chamago.pcrm.customerservice.pojo.CSIType;
import com.chamago.pcrm.customerservice.pojo.CustomerService;
import com.chamago.pcrm.customerservice.pojo.CustomerServiceIssues;

/**
 * 售后数据访问接口
 * @author James.wang
 */
public interface CustomerServiceMapper {
	
	/**
	 * 获取当前店铺下所有的售后类型
	 * @param sellerNick  店铺名称
	 * @return  当前店铺下的售后类型集合 
	 */
	public List<CSIType> getCSITypeListBySellerNick(String sellerNick);
	
	/**
	 * 保存售后问题
	 * @param customerServiceIssues  售后问题实体
	 */
	public void saveCustomerServiceIssueInfo(CustomerServiceIssues customerServiceIssues) throws Exception;
	
	/**
	 * 保存售后问题处理详细
	 * @param csiDetail  售后问题处理详细实体
	 */
	public void saveCSIDetailInfo(CSIDetail csiDetail) throws Exception;
	
	/**
	 * 获取当前淘宝交易编号的售后问题
	 * @param tradeId  淘宝交易编号
	 * @return　当前淘宝交易编号的售后问题
	 */
	public Object[] getCustomerServiceIssueByCSIId(String CSIId);
	
	/**
	 * 获取当前交易编号是否存在售后问题
	 * @param tradeId  交易编号
	 * @return  当前交易编号的售后问题个数
	 */
	public String getCustomerServiceIssueCountByTradeId(String tradeId);
	
	/**
	 * 获取当前售后问题的处理详细列表
	 * @param csiId  售后问题标识
	 * @return  处理详细列表
	 */
	public List<Object[]> getCSIDetailByCSIId(String csiId);
	
	/**
	 * 根据条件查询售后问题列表
	 * @param param  查询条件集合　
	 * @return   售后问题列表
	 */
	public void queryCustomerServiceIssuesList(PageModel pageModel, Map<String, Object> param);
	
	/**
	 * 查询所有售后问题处理状态列表
	 * @return  售后问题处理状态列表
	 */
	public List<CSIStatus> queryCSIStatusList();
	
	/**
	 * 修改售后问题当前的处理状态
	 */
	public void updateCSIStatus(Map<String, String> param) throws Exception;
	
	/**
	 * 查询特定类型客服的信息
	 * @param type  客服类型
	 * @return      客服信息集合
	 */
	public List<CustomerService> queryCustomerServiceByType(int type);
	
	/**
	 * 根据客服昵称获取相应编号
	 * @param nick　客服昵称  
	 * @return      客服编号
	 */
	public Integer getCSIdByCSNick(String nick);
	
	/**
	 * 保存售后问题分配
	 * @param csiId  售后问题标识
	 * @param csId   客服标识
	 */
	public void saveAdmeasure(Map<String, String> param) throws Exception;
	
	/**
	 * 获取当前售后问题最后一步操作信息的ID
	 * @param csiId  售后问题标识
	 * @return       售后问题操作详细ID
	 */
	public String getEndCSIDetailOperateByCSIId(String csiId);
	
	/**
	 * 修改当前售后问题详细的处理时间
	 * @param id  售后问题详细标识
	 */
	public void updateCSIDetailDisposalTime(String id) throws Exception;
	
	/**
	 * 设置当前售后问题处理者为空
	 * @param csiId 售后问题标识
	 */
	public void updateCSIDisposalNickISNUll(String csiId) throws Exception;
	
	/**
	 * 查找需要黄灯与红灯提醒的处理状态
	 * @return  需提示的处理状态集合
	 */
	public List<CSIStatus> queryCSIStatusByTipNOTNull();
	
	/**
	 * 获取售后问题最后一条处理详细信息
	 * @param csiIDs  售后问题标识集合
	 * @return        当前售后最后一条处理详细信息
	 */
	public List<CSIDetail> queryEndCSIDetailByCSIIds(String csiIDs);
	
	/**
	 * 修改售后问题详细亮灯类型
	 * @param id 详细ID
	 * @param lightType 亮灯类型
	 */
	public void updateCSIDetailLightType(String id, Integer lightType) throws Exception;

	/**
	 * 获取CSIDetail 信息
	 * @param id CSIDetail id  
	 * @return   CSIDetail 信息 
	 */
	public CSIDetail getCsiDetailByCSId(String id);
	
	/**
	 * 获取当前客服未处理的售后问题总数
	 * @param nick 　客服昵称
	 * @param buyer  买家昵称　　
	 * @param 未处理的售后问题总数
	 */
	public Integer getUntreatedCustomerServiceByNick(String nick, String buyer);
	
	/**
	 * 获取CsiStatus详细
	 */
	public CSIStatus getCSIStatusById(String id);
	
	/**
	 * 根据处理集合，获取处理状态
	 * @param operatSql  处理集合
	 * @return   处理状态集合
	 */
	public List<CSIStatus> getCSIStatusByOperatSeq(String operatSql);
	
	/**
	 * 删除售后问题信息
	 * @param tradeId  订单编号
	 */
	public void deleteCustomerService(String tradeId) throws Exception;
	
	/**
	 * 添加售后类型
	 * @param csiType 售后类型实体
	 */
	public void saveType(CSIType csiType) throws Exception;
	
	/**
	 * 获取售后类型名称数据个数
	 * @param name 售后类型名称
	 * @return     存在个数
	 */
	public Integer getTypeCountbyName(String name, String sellerNick);
	
	/**
	 * 删除售后类型
	 * @param id  售后类型标识
	 */
	public void deleteType(String id) throws Exception;
	
	/**
	 * 获取售后紧急程度数据个数
	 * @param name 售后紧急程度名称
	 * @param sellerNick 店铺昵称
	 * @return 当然名称的个数
	 */
	public Integer getPriorityCountByName(String name, String sellerNick);
	
	/**
	 * 删除售后紧急程度
	 * @param  售后紧急程度标识
	 */
	public void deletePriority(String id) throws Exception;
	
	/**
	 * 获取处理状态系统亮灯设置
	 * @return   获取处理状态系统亮灯设置列表
	 */
	public List<CSIStatus> getCSIStatusListByLight();
	
	/**
	 * 保存客服自设提醒设置
	 * @param csiCustomAlertTime 客服自设提醒设置信息
	 */
	public void saveCustomAlertTime(CSICustomAlertTime csiCustomAlertTime) throws Exception;
	
	/**
	 * 获取当前店铺下处理状态提醒时间自设列表
	 * @param sellerNick  店铺昵称
	 * @return 当前店铺下处理状态提醒时间自设列表           
	 */
	public List<Object[]> getCustomAlertTimeBySellerNick(String sellerNick);
	
	/**
	 * 获取当前售后处理状态在设置中存在的个数
	 * @param id   售后处理状态 
	 * @param sellerNick  店铺昵称
	 * @return  当前售后处理状态在设置中存在的个数
	 */
	public Integer getCustomAlertTimeCountByStatusId(String id, String sellerNick);
	
	/**
	 * 删除客服自设提醒设置
	 * @param id 标识
	 */
	public void deleteCustomAlertTime(String id) throws Exception;
	
	/**
	 * 获取当前店铺提醒时间自设
	 * @param sellerNick  店铺昵称  
	 * @return   店铺提醒时间自设
	 */
	public List<CSIStatus> loadCustomAlertTimeBySellerNick(String sellerNick);
	
	/**
	 * 获取当前类型下售后的个数　
	 * @param type　类型标识 
	 * @return      售后的个数
	 */
	public Integer getCustomerServiceCountByType(String type);
	
	/**
	 * 获取当前店铺提醒时间自设
	 * @param sellerNick  店铺昵称  
	 * @return   店铺提醒时间自设
	 */
	public List<CSICustomAlertTime> loadCustomAlertTimeBySellerNick2(String sellerNick);
	
	/**
	 * 修改客服提醒设置
	 * @param sellerNick 店铺名称
	 * @param id         状态标识
	 * @param y          黄灯提醒时间
	 * @param r          红灯提醒时间
	 */
	public void UpdateCustomAlertTime(String sellerNick, String id, Integer y, Integer r) throws Exception;
	
	/**
	 * 获取当前店铺下买家正在处理的售后问题列表
	 * @param buyerNick   买家昵称
	 * @param sellerNick  店铺昵称
	 * @return 当前店铺下买家正在处理的售后问题列表
	 */
	public List<CustomerServiceIssues> getCustomerServiceIssuesByPresent(String buyerNick, String sellerNick);
	
	/**
	 * 获取当前店铺下买家未处理的售后问题列表
	 * @param buyerNick   买家昵称
	 * @param sellerNick  店铺昵称
	 * @return 当前店铺下买家未处理的售后问题列表
	 */
	public List<CustomerServiceIssues> getCustomerServiceIssuesByUndisposed(String buyerNick, String sellerNick);
	
	/**
	 * 获得当前店铺下的用户列表
	 * @param sellerNick 店铺名称
	 */
	public List<Object[]> getUserListBySellerNick(String sellerNick);
	
	/**
	 * 获取当前买家在当前店铺下的售后个数
	 */
	public Integer getCustomerServiceCountByBuyerNick(String buyerNick, String sellerNick);
	
	/**
	 * 获取当前店铺下所有售后个数
	 * @param sellerNick  店铺名称
	 * @return 当前店铺下所有售后个数
	 */
	public Integer getCustomerServiceCountBySellerNick(String sellerNick);
	
	/**
	 * 删除售后问题类型
	 * @param id  售后问题类型标识
	 */
	public void deleteCSType(String id);
	
	/**
	 * 修改售后问题类型
	 * @param csiType 售后问题类型  
	 */
	public void updateCSIType(CSIType csiType);
	
	/**
	 * 获取当前售后问题类型详细信息
	 * @param id  标识
	 * @return 当前售后问题类型详细信息
	 */
	public CSIType getCsiTypeById(String id);
}