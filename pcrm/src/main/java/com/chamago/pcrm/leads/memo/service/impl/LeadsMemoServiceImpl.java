package com.chamago.pcrm.leads.memo.service.impl;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chamago.pcrm.common.utils.C;
import com.chamago.pcrm.common.utils.PageModel;
import com.chamago.pcrm.item.service.ItemLuceneService;
import com.chamago.pcrm.leads.memo.mapper.LeadsMemoMapper;
import com.chamago.pcrm.leads.memo.pojo.LeadsMemo;
import com.chamago.pcrm.leads.memo.pojo.LeadsMemoReminderLKP;
import com.chamago.pcrm.leads.memo.service.LeadsMemoService;
import com.chamago.pcrm.worktable.reminder.mapper.ReminderMapper;
import com.chamago.pcrm.worktable.reminder.pojo.Reminder;

/**
 * 缺货/减价事件登记业务实现
 * @author James.wang
 */
@Service
public class LeadsMemoServiceImpl implements LeadsMemoService {
	
	//缺货/减价事件登记数据访问接口
	@Autowired
	private LeadsMemoMapper leadsMemoMapper;
	
	//客户备忘数据访问接口
	@Autowired
	private ReminderMapper reminderMapper;
	
	//lucene　查询接口
	@Autowired
	private ItemLuceneService itemLuceneService;
	
	/**
	 * 保存缺货/减价事件登记信息
	 * @param leadsMemo  缺货/减价事件登记信息
	 */
	@Transactional
	public void saveLeadsMemo(LeadsMemo leadsMemo) throws Exception {
		try {
			//保存缺货/减价事件登记信息
			leadsMemo.setStatus(0);
			if(leadsMemo.getPrice() != null && leadsMemo.getPrice().intValue() > 0) {
				leadsMemo.setType(1);
			} else {
				leadsMemo.setType(0);
			}
			getLeadsMemoMapper().saveLeadsMemo(leadsMemo);
			if(leadsMemo.isAddReminder()) {
	 			//根据numid/skuid 获取商品详细信息
				StringBuffer str = new StringBuffer();
				List<Document> items = itemLuceneService.searchItem(leadsMemo.getSellerNick(), new Term(C.LUCENE_FIELD_ITEM_NUMIID,String.valueOf(leadsMemo.getNumId())));
				if(items.size() > 0) {
					str.append(items.get(0).get(C.LUCENE_FIELD_ITEM_TITLE));
				}
				Document sku = itemLuceneService.searchSkuById(leadsMemo.getSellerNick(), leadsMemo.getSkuId());
				if(sku != null) {
					str.append("  ");
					str.append(sku.get(C.LUCENE_FIELD_SKU_PROPS));
				}
				str.append(" 到货，通知  " + leadsMemo.getBuyerNick());
				
				//添加客服备忘
				Reminder reminder = new Reminder();
				reminder.setContent(str.toString());
				reminder.setBuyerNick(leadsMemo.getBuyerNick());
				reminder.setNick(leadsMemo.getWangwangNick());
				reminder.setStatus(0);
				reminder.setTipTime(leadsMemo.getExpiredDate());
				reminder.setTipType(0);
				reminder.setSellerNick(leadsMemo.getSellerNick());
				
				getReminderMapper().saveReminder(reminder);
				//添加缺货/减价事件登记与客服备忘信息关联
				LeadsMemoReminderLKP leadsMemoReminderLKP = new LeadsMemoReminderLKP();
				leadsMemoReminderLKP.setLid(leadsMemo.getId());
				leadsMemoReminderLKP.setRid(reminder.getId());
				getLeadsMemoMapper().saveLeadsMemoReminderLKP(leadsMemoReminderLKP);
			}
		} catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * 修改缺货/促销事件登记信息
	 * @param leadsMemo  缺货/促销事件登记信息实体
	 */
	@Transactional
	public void updateLeadsMemo(LeadsMemo leadsMemo) throws Exception  {
		try {
			if(leadsMemo.getPrice() != null && leadsMemo.getPrice().intValue() > 0) {
				leadsMemo.setType(1);
			} else {
				leadsMemo.setType(0);
			}
			//修改缺货/促销事件登记信息
			getLeadsMemoMapper().updateLeadsMemo(leadsMemo);
			if(leadsMemo.isAddReminder()) {
				String rid = getLeadsMemoMapper().getReminderIdByLeadsMemoId(leadsMemo.getId());
				if(rid != null) {
					//修改客服备注信息状态
					getReminderMapper().updateReminderStatus(rid, 0);
				} else {
					//根据numid/skuid 获取商品详细信息
					StringBuffer str = new StringBuffer();
					List<Document> items = itemLuceneService.searchItem(leadsMemo.getSellerNick(), new Term(C.LUCENE_FIELD_ITEM_NUMIID,String.valueOf(leadsMemo.getNumId())));
					if(items.size() > 0) {
						str.append(items.get(0).get(C.LUCENE_FIELD_ITEM_TITLE));
					}
					Document sku = itemLuceneService.searchSkuById(leadsMemo.getSellerNick(), leadsMemo.getSkuId());
					if(sku != null) {
						str.append("  ");
						str.append(sku.get(C.LUCENE_FIELD_SKU_PROPS));
					}
					str.append(" 到货，通知  " + leadsMemo.getBuyerNick());
					
					//添加客服备忘
					Reminder reminder = new Reminder();
					reminder.setContent(str.toString());
					reminder.setBuyerNick(leadsMemo.getBuyerNick());
					reminder.setNick(leadsMemo.getWangwangNick());
					reminder.setStatus(0);
					reminder.setTipTime(leadsMemo.getExpiredDate());
					reminder.setTipType(0);
					getReminderMapper().saveReminder(reminder);
					//添加缺货/减价事件登记与客服备忘信息关联
					LeadsMemoReminderLKP leadsMemoReminderLKP = new LeadsMemoReminderLKP();
					leadsMemoReminderLKP.setLid(leadsMemo.getId());
					leadsMemoReminderLKP.setRid(reminder.getId());
					getLeadsMemoMapper().saveLeadsMemoReminderLKP(leadsMemoReminderLKP);
				}
			} else {
				String rid = getLeadsMemoMapper().getReminderIdByLeadsMemoId(leadsMemo.getId());
				if(rid != null) {
					//修改客服备注信息状态
					getReminderMapper().updateReminderStatus(rid, 2);
				}
			}
		} catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * 获取当前客户的事件登记信息集合
	 * @param pageModel  分页组件
	 * @param customerNick 客户昵称
	 * @param status 信息状态
	 * @return 事件登记信息集合
	 */
	public void getLeadsMemoByParam(PageModel pageModel, String customerNick, String sellerNick,
			String status) throws Exception {
		//根据当前客户特定状态的事件登记信息集合
		getLeadsMemoMapper().getLeadsMemoByParam(pageModel, customerNick, sellerNick, status);
		//循环信息，根据numid/skuid从luence获取商品名称
		List<LeadsMemo> leadsMemos = (List<LeadsMemo>)pageModel.getList(); 
		for(int i = 0; i < leadsMemos.size(); i++) {
			LeadsMemo leadsMemo = leadsMemos.get(i);
			List<Document> items = itemLuceneService.searchItem(sellerNick, new Term(C.LUCENE_FIELD_ITEM_NUMIID,String.valueOf(leadsMemo.getNumId())));
			if(items.size() > 0) {
				leadsMemos.get(i).setGoodsName(items.get(0).get(C.LUCENE_FIELD_ITEM_TITLE));
			}
		}
		pageModel.setList(leadsMemos);
	}
	
	/**
	 * 获取当前缺货/促销事件登记信息
	 * @param id　信息标识
	 * @return    缺货/促销事件登记信息
	 */
	public LeadsMemo getLeadsMemoById(String id) throws Exception {
		LeadsMemo leadsMemo = getLeadsMemoMapper().getLeadsMemoById(id);
		try {
			String rid = getLeadsMemoMapper().getReminderIdByLeadsMemoId(leadsMemo.getId());
			Reminder reminder = getReminderMapper().getReminderById(rid);
			if(reminder != null) {
				leadsMemo.setAddReminder(reminder.getStatus() == 0 ? true : reminder.getStatus() == 1 ? true : false );
			}
		} catch(Exception e) {
			e.printStackTrace();
			leadsMemo.setAddReminder(false);
		}
		return leadsMemo;
	}
	
	/**
	 * 修改当前信息状态
	 * @param id　标识
	 * @param status　状态
	 */
	public void updateStatus(String id, Integer status) throws Exception {
		try {
			getLeadsMemoMapper().updateStatus(id, status);
			String rid = getLeadsMemoMapper().getReminderIdByLeadsMemoId(id);
			if(rid != null) {
				getReminderMapper().updateReminderStatus(rid, status);
			}
		} catch(Exception e) {
			throw e;
		}
	}

	/**
	 * 获取当前买家缺货/促销事件登记信息个数
	 * @param buyerNick 买家昵称
	 * @param sellerNick 卖家昵称
	 * @param status    信息状态
	 * @return          当前买家缺货/促销事件登记信息个数
	 */
	public Integer getLeadsMemoCountByStatus(String buyerNick, String sellerNick, String status) {
		return getLeadsMemoMapper().getLeadsMemoCountByStatus(buyerNick, sellerNick, status);
	}
	
	public LeadsMemoMapper getLeadsMemoMapper() {
		return leadsMemoMapper;
	}

	public void setLeadsMemoMapper(LeadsMemoMapper leadsMemoMapper) {
		this.leadsMemoMapper = leadsMemoMapper;
	}

	public ReminderMapper getReminderMapper() {
		return reminderMapper;
	}

	public void setReminderMapper(ReminderMapper reminderMapper) {
		this.reminderMapper = reminderMapper;
	}

	public ItemLuceneService getItemLuceneService() {
		return itemLuceneService;
	}

	public void setItemLuceneService(ItemLuceneService itemLuceneService) {
		this.itemLuceneService = itemLuceneService;
	}
}