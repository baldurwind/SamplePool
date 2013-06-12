package com.chamago.pcrm.customerservice.pojo;

import com.chamago.pcrm.common.pojo.BasePojo;

/**
 * 处理状态
 * @author James.wang
 */
public class CSIStatus extends BasePojo {

	private static final long serialVersionUID = 3984018758362945969L;

	//标识
	private String id;
	
	//状态名称
	private String name;
	
	//黄灯提醒时间
	private Integer yellowLightAlertTime;
	
	//红灯提醒时间
	private Integer redLightAlertTime;
	
	//后续操作标识
	private String operatSeq;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getYellowLightAlertTime() {
		return yellowLightAlertTime;
	}

	public void setYellowLightAlertTime(Integer yellowLightAlertTime) {
		this.yellowLightAlertTime = yellowLightAlertTime;
	}

	public Integer getRedLightAlertTime() {
		return redLightAlertTime;
	}

	public void setRedLightAlertTime(Integer redLightAlertTime) {
		this.redLightAlertTime = redLightAlertTime;
	}

	public String getOperatSeq() {
		return operatSeq;
	}

	public void setOperatSeq(String operatSeq) {
		this.operatSeq = operatSeq;
	}
}
