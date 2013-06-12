package com.chamago.pcrm.deprecated;

import java.util.List;

import com.chamago.pcrm.common.pojo.BasePojo;


public class BatchInitShellSchedule extends BasePojo {

	
	private static final long serialVersionUID = 1L;
	private String appKey;
	private String sellernick;
	private List<Integer> shellId;
	
	
	
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
	public List<Integer> getShellId() {
		return shellId;
	}
	public void setShellId(List<Integer> shellId) {
		this.shellId = shellId;
	}
	
	
}
