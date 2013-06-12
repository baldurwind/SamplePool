package com.chamago.pcrm.common.pojo;



public class InitShellSchedule extends BasePojo {
	
	
	private static final long serialVersionUID = 1L;
	
	private String appKey;
	private String sellernick;
	private String status;
	private Integer shellId;
	
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getSellernick() {
		return sellernick;
	}
	public void setSellernick(String sellernick) {
		this.sellernick = sellernick;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getShellId() {
		return shellId;
	}
	public void setShellId(Integer shellId) {
		this.shellId = shellId;
	}
	
	
}
