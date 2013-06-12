package com.chamago.pcrm.customerservice.pojo;

import java.util.Date;

import com.chamago.pcrm.common.pojo.BasePojo;

/**
 * 客服自设提醒时间
 * @author James.wang
 */
public class CSICustomAlertTime extends BasePojo {
	
	private static final long serialVersionUID = 1475269901977438269L;

	//标识
	private String id;
	
	//所属店铺
	private String sellerNick;
	
	//状态标识
	private String statusId;
	
	//状态名称
	private String name;
	
	//黄灯提醒时间
	private Integer yelloLightAlertTime;
	
	//红灯提醒时间
	private Integer redLightAlertTime;
	
	//信息状态 0:正常   1：已删除
	private Integer state;
	
	//信息新增时间
	private Date createTime;
	
	//信息修改时间
	private Date modifyTime;
	
	//信息删除时间
	private Date deleteTime;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}
	
	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public Integer getYelloLightAlertTime() {
		return yelloLightAlertTime;
	}

	public void setYelloLightAlertTime(Integer yelloLightAlertTime) {
		this.yelloLightAlertTime = yelloLightAlertTime;
	}

	public Integer getRedLightAlertTime() {
		return redLightAlertTime;
	}

	public void setRedLightAlertTime(Integer redLightAlertTime) {
		this.redLightAlertTime = redLightAlertTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
