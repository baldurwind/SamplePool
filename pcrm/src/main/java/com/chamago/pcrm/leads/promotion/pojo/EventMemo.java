package com.chamago.pcrm.leads.promotion.pojo;

import java.util.Date;

import com.chamago.pcrm.common.pojo.BasePojo;


public class EventMemo extends BasePojo {

	private Integer id;
	private String type;
	private String subject;
	private String csId;
	private Date updTime;
	private Date creTime;
	private boolean isClosed;
	private String seller;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getCsId() {
		return csId;
	}
	public void setCsId(String csId) {
		this.csId = csId;
	}
	public Date getUpdTime() {
		return updTime;
	}
	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	public Date getCreTime() {
		return creTime;
	}
	public void setCreTime(Date creTime) {
		this.creTime = creTime;
	}
	public boolean isClosed() {
		return isClosed;
	}
	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	
	
	
}
