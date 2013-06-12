package com.chamago.pcrm.customerservice.service.impl;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chamago.pcrm.common.utils.PageModel;
import com.chamago.pcrm.customerservice.mapper.CustomerServiceMapper;
import com.chamago.pcrm.customerservice.pojo.CSICustomAlertTime;
import com.chamago.pcrm.customerservice.pojo.CSIDetail;
import com.chamago.pcrm.customerservice.pojo.CSIQuery;
import com.chamago.pcrm.customerservice.pojo.CSIStatus;
import com.chamago.pcrm.customerservice.pojo.CSIType;
import com.chamago.pcrm.customerservice.pojo.CustomerService;
import com.chamago.pcrm.customerservice.pojo.CustomerServiceIssues;
import com.chamago.pcrm.customerservice.service.CustomerServiceService;
import com.chamago.pcrm.worktable.connect.mapper.SysMgtMapper;
import com.chamago.pcrm.worktable.performance.mapper.EserviceMapper;

/**
 * 售后业务实现
 * @author James
 */
@Service
public class CustomerServiceServiceImpl implements CustomerServiceService {
	
	@Autowired
	private CustomerServiceMapper customerServiceMapper;
	
	@Autowired
	private EserviceMapper eserviceMapper;
	
	@Autowired
	private SysMgtMapper  sysMgtMapper;

	/**
	 * 获取当前店铺下所有的售后类型
	 * @param sellerNick  店铺名称
	 * @return  当前店铺下的售后类型集合 
	 */
	public List<CSIType> getCSITypeListBySellerNick(String sellerNick) {
		return getCustomerServiceMapper().getCSITypeListBySellerNick(sellerNick);
	}
	
	/**
	 * 保存售后问题
	 * 1、判断淘宝交易编号所属售后问题是否已存在
	 * 2、如果不存在，则保存售后问题
	 * @param customerServiceIssues  售后问题实体
	 */
	public void saveCustomerServiceIssueInfo (
			CustomerServiceIssues customerServiceIssues) throws Exception {
		try {
			//判断淘宝交易编号所属售后问题是否已存在
			String id = this.getCustomerServiceIssueCountByTradeId(customerServiceIssues.getTradeId());
			if(null == id || id.trim().length() <=0) {
				//如果不存在，则新建保存
				getCustomerServiceMapper().saveCustomerServiceIssueInfo(customerServiceIssues);
				CSIDetail csiDetail = new CSIDetail();
				csiDetail.setCsiId(customerServiceIssues.getId());
				csiDetail.setNick(customerServiceIssues.getNick());
				csiDetail.setStatus(customerServiceIssues.getStatus());
				csiDetail.setRemark("");
				getCustomerServiceMapper().saveCSIDetailInfo(csiDetail);
			}
		} catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * 获取当前售后问题的处理详细列表
	 * @param csiId  售后问题标识
	 * @return  处理详细列表
	 */
	public List<Object[]> getCSIDetailByCSIId(String csiId) {
		return getCustomerServiceMapper().getCSIDetailByCSIId(csiId);
	}

	/**
	 * 获取当前淘宝交易编号的售后问题
	 * @param tradeId  淘宝交易编号
	 * @return　当前淘宝交易编号的售后问题
	 */
	public Object[] getCustomerServiceIssueByCSIId(String CSIId) {
		return getCustomerServiceMapper().getCustomerServiceIssueByCSIId(CSIId);
	}

	/**
	 * 获取当前交易编号是否存在售后问题
	 * @param tradeId  交易编号
	 * @return  当前交易编号的售后问题个数
	 */
	public String getCustomerServiceIssueCountByTradeId(String tradeId) {
		return getCustomerServiceMapper().getCustomerServiceIssueCountByTradeId(tradeId);
	}
	
	/**
	 * 查询所有售后问题处理状态列表
	 * @return  售后问题处理状态列表
	 */
	public List<CSIStatus> queryCSIStatusList() {
		return getCustomerServiceMapper().queryCSIStatusList();
	}
	
	/**
	 * 保存售后问题处理详细
	 * 1、修改售后问题当前操作时间
	 * 2、保存售后问题处理
	 * 3、如果当前处理状态为重新开启，则清空售后问题主表所属处理者，新建等待处理状态
	 * 4、修改售后问题主表，跟进售后问题状态
	 * @param csiDetail  售后问题处理详细实体
	 */
	public void saveCSIDetailInfo(CSIDetail csiDetail) throws Exception {
		try {
			//修改售后问题当前操作时间
			this.updateEndCSIDetailOperatediSposalTimeByCSIId(csiDetail.getCsiId());
			if(csiDetail.getFilePath() != null && csiDetail.getFilePath().trim().length() == 0) {
				csiDetail.setFilePath(null);
			}
			//保存售后问题处理
			getCustomerServiceMapper().saveCSIDetailInfo(csiDetail);
			//如果当前处理状态为重新开启，则清空售后问题主表所属处理者，新建等待处理状态
			if("5".equals(csiDetail.getStatus().toString())) {
				getCustomerServiceMapper().updateCSIDisposalNickISNUll(csiDetail.getCsiId());
				CSIDetail csiDetail2 = new CSIDetail();
				csiDetail2.setCsiId(csiDetail.getCsiId());
				csiDetail2.setNick(csiDetail.getNick());
				csiDetail2.setStatus("1");
				csiDetail2.setRemark("");
				getCustomerServiceMapper().saveCSIDetailInfo(csiDetail2);
				csiDetail.setStatus("1");
			} else if ("4".equals(csiDetail.getStatus().toString())) {
				getCustomerServiceMapper().updateCSIDisposalNickISNUll(csiDetail.getCsiId());
			}
			Map<String, String> param = new Hashtable<String, String>();
			param.put("id", csiDetail.getCsiId().toString());
			param.put("status", csiDetail.getStatus().toString());
			//修改售后问题主表，跟进售后问题状态
			getCustomerServiceMapper().updateCSIStatus(param);
		} catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * 查询特定类型客服的信息
	 * @param type  客服类型
	 * @return      客服信息集合
	 */
	public List<CustomerService> queryCustomerServiceByType(int type) {
		return getCustomerServiceMapper().queryCustomerServiceByType(type);
	}
	
	/**
	 * 根据客服昵称获取相应编号
	 * @param nick　客服昵称  
	 * @return      客服编号
	 */
	public Integer getCSIdByCSNick(String nick) {
		return getCustomerServiceMapper().getCSIdByCSNick(nick);
	}
	
	/**
	 * 当前用户查询所处理的售后问题信息
	 * @param csiQuery 　售后问题查询实体
	 * @return           售后问题集合
	 */
	public void findByCurrentNick(PageModel pageModel, CSIQuery csiQuery) {
		Map<String, Object> param = new Hashtable<String, Object>();
		if(csiQuery != null) {
			if(null != csiQuery.getTradeId() && csiQuery.getTradeId().trim().length() > 0) {
				param.put("tradeId", csiQuery.getTradeId());
			}
			if(null != csiQuery.getType() && csiQuery.getType().trim().length() > 0) {
				param.put("type", csiQuery.getType());
			}
			if(null != csiQuery.getPriority() && csiQuery.getPriority().trim().length() > 0) {
				param.put("priority", csiQuery.getPriority());
			}
			if(null != csiQuery.getStatus() && csiQuery.getStatus().trim().length() > 0) {
				param.put("status", csiQuery.getStatus());
			}
			if(null != csiQuery.getBuyer() && csiQuery.getBuyer().trim().length() > 0) {
				param.put("buyer", csiQuery.getBuyer());
			}
			if(null != csiQuery.getNick() && csiQuery.getNick().trim().length() > 0) {
				param.put("nick", csiQuery.getNick());
			} 
			if(null != csiQuery.getSellerNick() && csiQuery.getSellerNick().trim().length() > 0) {
				param.put("sellerNick", csiQuery.getSellerNick());
			}
		}
		getCustomerServiceMapper().queryCustomerServiceIssuesList(pageModel, param);
	}
	
	/**
	 * 保存售后问题分配
	 * 1、修改售后问题当前操作时间
	 * 2、保存售后问题分配
	 * 3、保存售后问题处理详细
	 * @param csiId  售后问题标识
	 * @param csNick 客服昵称
	 */
	public void saveAdmeasure(String csiId, String csNick) throws Exception {
		try {
			Map<String, String> param = new Hashtable<String, String>();
			param.put("id", csiId);
			param.put("csNick", csNick);
			
			CSIDetail csiDetail = new CSIDetail();
			csiDetail.setCsiId(csiId);
			csiDetail.setNick(csNick);
			csiDetail.setStatus("2");
			//修改售后问题当前操作时间
			this.updateEndCSIDetailOperatediSposalTimeByCSIId(csiId);
			//保存售后问题分
			getCustomerServiceMapper().saveAdmeasure(param);
			//保存售后问题处理详细
			getCustomerServiceMapper().saveCSIDetailInfo(csiDetail);
		} catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * 修改当前售后问题详细的最后一次操作时间
	 * @param csiId  售后问题ID
	 */
	public void updateEndCSIDetailOperatediSposalTimeByCSIId(String csiId) throws Exception {
		try {
			String id = getCustomerServiceMapper().getEndCSIDetailOperateByCSIId(csiId);
			if(id != null) {
				getCustomerServiceMapper().updateCSIDetailDisposalTime(id);
			}
		} catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * 售后问题处理超时设置
	 * 1、获取系统时间提醒超时设置,并存入Map<Integer, CSIStatus>
	 * 2、获取当前售后问题最后一条处理详细信息
	 * 3、判断类型及亮灯超时，修改信息
	 * @param csiId 售后问题标识
	 */
	public void csiHandlingTimeoutSetting(String csiId, String sellerNick) throws Exception {
		try {
			//获取系统时间提醒超时设置,并存入Map<Integer, CSIStatus>
			List<CSIStatus> systemStatusList = getCustomerServiceMapper().queryCSIStatusByTipNOTNull();
			Map<String, CSIStatus> statusMap = new Hashtable<String, CSIStatus>();
			for(int i = 0; i < systemStatusList.size(); i++) {
				CSIStatus status = systemStatusList.get(i);
				statusMap.put(status.getId(), status);
			}
			
			//获取当前店铺自设提醒时间
			List<CSIStatus> customAlertTimeList = getCustomerServiceMapper().loadCustomAlertTimeBySellerNick(sellerNick);
			for(int i = 0; i < customAlertTimeList.size(); i++) {
				CSIStatus status = customAlertTimeList.get(i);
				statusMap.put(status.getId(), status);
			}
			
			//获取当前售后问题最后一条处理详细信息
			List<CSIDetail> detailList = getCustomerServiceMapper().queryEndCSIDetailByCSIIds(csiId.toString());
			for(int i = 0; i < detailList.size(); i++) {
				CSIDetail detail = detailList.get(i);
				if(statusMap.get(detail.getStatus()) != null) {
					CSIStatus status = statusMap.get(detail.getStatus());
					Calendar calendar = Calendar.getInstance();
					//判断当前detail是绿灯，还是黄灯
					if(detail.getLightType() == 1) { 
						Calendar currentDate = Calendar.getInstance();
						currentDate.setTime(detail.getCreateTime());
						calendar.add(Calendar.HOUR, 0 - status.getYellowLightAlertTime());
						if(calendar.after(currentDate)) {
							//修改为黄灯
							getCustomerServiceMapper().updateCSIDetailLightType(detail.getId(), 2);
						}
					} else if(detail.getLightType() == 2) {
						Calendar currentDate = Calendar.getInstance();
						currentDate.setTime(detail.getYellowLightTime());
						calendar.add(Calendar.HOUR, 0 - status.getRedLightAlertTime());
						if(calendar.after(currentDate)) {
							//修改红灯
							getCustomerServiceMapper().updateCSIDetailLightType(detail.getId(), 3);
						}
					} else if (detail.getLightType() == 0) {
						getCustomerServiceMapper().updateCSIDetailLightType(detail.getId(), 1);
					}
				} else {
					getCustomerServiceMapper().updateCSIDetailLightType(detail.getId(), 0);
				}
			}
		} catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * 获取当前客服未处理的售后问题总数
	 * @param nick 客服昵称
	 * @param buyer　买家昵称
	 * @param 未处理的售后问题总数
	 */
	public Integer getUntreatedCustomerServiceByNick(String nick, String buyer) {
		return getCustomerServiceMapper().getUntreatedCustomerServiceByNick(nick, buyer);
	}
	
	/**
	 * 获取当前状态后续可操作状态
	 * @param id　当前状态
	 */
	public List<CSIStatus> getCSIStatusOperatSeqListById(String id) {
		CSIStatus csiStatus = getCustomerServiceMapper().getCSIStatusById(id);
		List<CSIStatus> csiStatusList = getCustomerServiceMapper().getCSIStatusByOperatSeq(csiStatus.getOperatSeq());
		return csiStatusList;
	}
	
	/**
	 * 删除售后问题信息
	 * @param tradeId  订单编号
	 */
	public void deleteCustomerService(String tradeId) throws Exception {
		getCustomerServiceMapper().deleteCustomerService(tradeId);
	}
	
	/**
	 * 添加售后类型
	 * @param csiType 售后类型实体
	 */
	public void saveType(CSIType csiType) throws Exception {
		getCustomerServiceMapper().saveType(csiType);
	}
	
	/**
	 * 获取售后类型名称数据个数
	 * @param name 售后类型名称
	 * @return     存在个数
	 */
	public Integer getTypeCountbyName(String name, String sellerNick) {
		return getCustomerServiceMapper().getTypeCountbyName(name, sellerNick);
	}
	
	/**
	 * 删除售后类型
	 * @param id  售后类型标识
	 */
	public void deleteType(String id) throws Exception {
		getCustomerServiceMapper().deleteType(id);
	}
	
	/**
	 * 获取售后紧急程度数据个数
	 * @param name 售后紧急程度名称
	 * @param sellerNick 店铺昵称
	 * @return 当然名称的个数
	 */
	public Integer getPriorityCountByName(String name, String sellerNick) {
		return getCustomerServiceMapper().getPriorityCountByName(name, sellerNick);
	}
	
	/**
	 * 删除售后紧急程度
	 * @param  售后紧急程度标识
	 */
	public void deletePriority(String id) throws Exception {
		getCustomerServiceMapper().deletePriority(id);
	}
	
	/**
	 * 获取处理状态系统亮灯设置
	 * @return   获取处理状态系统亮灯设置列表
	 */
	public List<CSIStatus> getCSIStatusListByLight() {
		return getCustomerServiceMapper().getCSIStatusListByLight();
	}
	
	/**
	 * 保存客服自设提醒设置
	 * @param csiCustomAlertTime 客服自设提醒设置信息
	 */
	public void saveCustomAlertTime(CSICustomAlertTime csiCustomAlertTime) throws Exception {
		getCustomerServiceMapper().saveCustomAlertTime(csiCustomAlertTime);
	}
	
	/**
	 * 获取当前店铺下处理状态提醒时间自设列表
	 * @param sellerNick  店铺昵称
	 * @return 当前店铺下处理状态提醒时间自设列表           
	 */
	public List<Object[]> getCustomAlertTimeBySellerNick(String sellerNick) {
		return getCustomerServiceMapper().getCustomAlertTimeBySellerNick(sellerNick);
	}
	
	/**
	 * 获取当前售后处理状态在设置中存在的个数
	 * @param id   售后处理状态 
	 * @param sellerNick  店铺昵称
	 * @return  当前售后处理状态在设置中存在的个数
	 */
	public Integer getCustomAlertTimeCountByStatusId(String id, String sellerNick) {
		return getCustomerServiceMapper().getCustomAlertTimeCountByStatusId(id, sellerNick);
	}
	
	/**
	 * 删除客服自设提醒设置
	 * @param id 标识
	 */
	public void deleteCustomAlertTime(String id) throws Exception {
		getCustomerServiceMapper().deleteCustomAlertTime(id);
	}
	
	public List<Object[]> getCustomerBySellerNick(String nick) {
		//获取当前店铺下所有的分组
		List<Object[]> groups = getEserviceMapper().getGroupsByNick(nick);
		String str = "";
		for(Object[] o : groups) {
			if("".equals(o[0].toString())) {
				str += o[0].toString();
			} else {
				 str += "," + o[0].toString();
			}
		}
		return getEserviceMapper().getMembersByGroupS(str);
	}
	
	/**
	 * 获取当前店铺下的客服分组
	 */
	public List<Object[]> getGroupsByNick(String nick) {
		
		return getEserviceMapper().getGroupsByNick(nick);
	}
	
	/**
	 * 获取当前组下所有的客服列表
	 * @param group　售服组标识
	 * @return　　当前组下所有的客服列表
	 */
	public List<Object[]> getCSListByGroup(String group) {
		return getEserviceMapper().getMembersByGroupId(group);
	}
	
	/**
	 * 获取当前当前店铺下售后处理状态，系统默认时间和用户自设时间
	 * @param sellerNick  店铺昵称
	 * @return  系统默认时间和用户自设时间          
	 */
	public List<CSICustomAlertTime> getCsiStatusBySellerNick(String sellerNick) {
		//获取系统时间提醒超时设置,并存入Map<Integer, CSIStatus>
		List<CSIStatus> systemStatusList = getCustomerServiceMapper().queryCSIStatusByTipNOTNull();
		Map<String, CSIStatus> statusMap = new Hashtable<String, CSIStatus>();
		for(int i = 0; i < systemStatusList.size(); i++) {
			CSIStatus status = systemStatusList.get(i);
			statusMap.put(status.getId(), status);
		}
		
		//获取当前店铺自设提醒时间
		List<CSICustomAlertTime> customAlertTimeList = getCustomerServiceMapper().loadCustomAlertTimeBySellerNick2(sellerNick);
		for(int i = 0; i < customAlertTimeList.size(); i++) {
			CSICustomAlertTime csiCustomAlertTime = customAlertTimeList.get(i);
			if(statusMap.get(csiCustomAlertTime.getStatusId()) != null) {
				statusMap.remove(csiCustomAlertTime.getStatusId());
			}
		}
		
		Set<String> keys = statusMap.keySet();
        for (Iterator<String> it = keys.iterator(); it.hasNext();) {
            String key = it.next();
            CSIStatus csiStatus = statusMap.get(key);
            CSICustomAlertTime csiCustomAlertTime = new CSICustomAlertTime();
            csiCustomAlertTime.setStatusId(csiStatus.getId());
            csiCustomAlertTime.setName(csiStatus.getName());
            csiCustomAlertTime.setYelloLightAlertTime(csiStatus.getYellowLightAlertTime());
            csiCustomAlertTime.setRedLightAlertTime(csiStatus.getRedLightAlertTime());
            customAlertTimeList.add(csiCustomAlertTime);
        }
        
        return customAlertTimeList;
	}
	
	/**
	 * 修改客服提醒设置
	 * @param sellerNick 店铺名称
	 * @param id         状态标识
	 * @param y          黄灯提醒时间
	 * @param r          红灯提醒时间
	 */
	public void UpdateCustomAlertTime(String sellerNick, String id, Integer y, Integer r) throws Exception {
		getCustomerServiceMapper().UpdateCustomAlertTime(sellerNick, id, y, r);
	}

	/**
	 * 获取当前店铺下买家正在处理的售后问题列表
	 * @param buyerNick   买家昵称
	 * @param sellerNick  店铺昵称
	 * @return 当前店铺下买家正在处理的售后问题列表
	 */
	public List<CustomerServiceIssues> getCustomerServiceIssuesByPresent(String buyerNick, String sellerNick) {
		
		return getCustomerServiceMapper().getCustomerServiceIssuesByPresent(buyerNick, sellerNick);
	}
	
	/**
	 * 获取当前店铺下买家未处理的售后问题列表
	 * @param buyerNick   买家昵称
	 * @param sellerNick  店铺昵称
	 * @return 当前店铺下买家未处理的售后问题列表
	 */
	public List<CustomerServiceIssues> getCustomerServiceIssuesByUndisposed(String buyerNick, String sellerNick) {
		
		return getCustomerServiceMapper().getCustomerServiceIssuesByUndisposed(buyerNick, sellerNick);
	}
	
	/**
	 * 获得当前店铺下的用户列表
	 * @param sellerNick 店铺名称
	 */
	public List<Object[]> getUserListBySellerNick(String sellerNick) {
		return getSysMgtMapper().getMembersBySeller(sellerNick);
	}
	
	/**
	 * 获取当前买家在当前店铺下的售后个数
	 */
	public Integer getCustomerServiceCountByBuyerNick(String buyerNick, String sellerNick) {
		
		return getCustomerServiceMapper().getCustomerServiceCountByBuyerNick(buyerNick, sellerNick);
	}
	
	/**
	 * 获取当前店铺下所有售后个数
	 * @param sellerNick  店铺名称
	 * @return 当前店铺下所有售后个数
	 */
	public Integer getCustomerServiceCountBySellerNick(String sellerNick) {
		return getCustomerServiceMapper().getCustomerServiceCountBySellerNick(sellerNick);
	}
	
	/**
	 * 删除售后问题类型
	 * @param id  售后问题类型标识
	 */
	public void deleteCSType(String id) {
		getCustomerServiceMapper().deleteCSType(id);
	}
	
	/**
	 * 修改售后问题类型
	 * @param csiType 售后问题类型  
	 */
	public void updateCSIType(CSIType csiType) {
		getCustomerServiceMapper().updateCSIType(csiType);
	}
	
	/**
	 * 获取当前售后问题类型详细信息
	 * @param id  标识
	 * @return 当前售后问题类型详细信息
	 */
	public CSIType getCsiTypeById(String id) {
		return getCustomerServiceMapper().getCsiTypeById(id);
	}
	
	public CustomerServiceMapper getCustomerServiceMapper() {
		return customerServiceMapper;
	}

	public void setCustomerServiceMapper(CustomerServiceMapper customerServiceMapper) {
		this.customerServiceMapper = customerServiceMapper;
	}

	public EserviceMapper getEserviceMapper() {
		return eserviceMapper;
	}

	public void setEserviceMapper(EserviceMapper eserviceMapper) {
		this.eserviceMapper = eserviceMapper;
	}

	public SysMgtMapper getSysMgtMapper() {
		return sysMgtMapper;
	}

	public void setSysMgtMapper(SysMgtMapper sysMgtMapper) {
		this.sysMgtMapper = sysMgtMapper;
	}
}
