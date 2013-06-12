package com.chamago.pcrm.marketing.pojo;

import java.util.Date;

import com.chamago.pcrm.common.pojo.BasePojo;

/**
 * 限时打折
 * @author James.wang
 */
public class LimitDiscount extends BasePojo {

	private static final long serialVersionUID = 2333633723408455854L;
	
	private Long limitDiscountId;
	private String name;
	private Date startTime;
	private Long itemNum;
	private String sellerNick;
	private Date endTime;
	private String limitDiscountName;

	public Long getLimitDiscountId() {
		return limitDiscountId;
	}

	public void setLimitDiscountId(Long limitDiscountId) {
		this.limitDiscountId = limitDiscountId;
	}

	public String getLimitDiscountName() {
		return limitDiscountName;
	}

	public void setLimitDiscountName(String limitDiscountName) {
		this.limitDiscountName = limitDiscountName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Long getItemNum() {
		return itemNum;
	}

	public void setItemNum(Long itemNum) {
		this.itemNum = itemNum;
	}

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick == null ? null : sellerNick.trim();
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}