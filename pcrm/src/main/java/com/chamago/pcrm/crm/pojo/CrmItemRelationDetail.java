package com.chamago.pcrm.crm.pojo;

import com.chamago.pcrm.common.pojo.BasePojo;


public class CrmItemRelationDetail  extends BasePojo{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5314640135121097387L;
	private Long numiid;
	private String picUrl;
	private String title;
	private Long frequency;
	
	
	public Long getNumiid() {
		return numiid;
	}
	public void setNumiid(Long numiid) {
		this.numiid = numiid;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getFrequency() {
		return frequency;
	}
	public void setFrequency(Long frequency) {
		this.frequency = frequency;
	}
	
	
	
}
