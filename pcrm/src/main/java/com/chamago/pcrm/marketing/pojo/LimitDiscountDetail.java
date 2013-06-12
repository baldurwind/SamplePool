package com.chamago.pcrm.marketing.pojo;

import com.chamago.pcrm.common.pojo.BasePojo;

/**
 * 限时打折详细
 * @author James.wang
 */
public class LimitDiscountDetail extends BasePojo {

	private static final long serialVersionUID = 6283815237946130142L;

	private Long limitNum;
	private Integer itemDiscount;
	private Long numIid;
	private String picUrl;
	private Long price;
	private String title;

	public Long getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(Long limitNum) {
		this.limitNum = limitNum;
	}

	public Integer getItemDiscount() {
		return itemDiscount;
	}

	public void setItemDiscount(Integer itemDiscount) {
		this.itemDiscount = itemDiscount;
	}

	public Long getNumIid() {
		return numIid;
	}

	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
