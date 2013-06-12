package com.chamago.pcrm.leads.memo.pojo;

import com.chamago.pcrm.common.pojo.BasePojo;

/**
 * 缺货/减价事件登记与客服备忘关联实体 
 * @author James.wang
 *
 */
public class LeadsMemoReminderLKP extends BasePojo {
	
	private static final long serialVersionUID = -9204693802093771152L;

	//标识
	private String id;
	
	//缺货/减价事件登记标识
	private String lid;
	
	//客服备忘标识
	private String rid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}
}
