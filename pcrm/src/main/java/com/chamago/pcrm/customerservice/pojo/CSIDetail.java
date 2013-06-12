package com.chamago.pcrm.customerservice.pojo;

import java.util.Date;

import com.chamago.pcrm.common.pojo.BasePojo;

/**
 * 售后问题处理详细
 * @author James.wang
 */
public class CSIDetail extends BasePojo {
	
	private static final long serialVersionUID = -5402064065311411658L;

	//标识
	private String id;
	
	//所属售后问题
	private String csiId;
	
	//处理者昵称
	private String nick;
	
	//处理状态
	private String status;
	
	//备注
	private String remark;
	
	//图片地址
	private String filePath;
	
	//亮灯类型 1:绿灯 2：黄灯 3:红灯
	private Integer lightType;
	
	//处理时间
	private Date createTime;
	
	//修改时间
	private Date modifyTime;
	
	//黄灯亮灯时间
	private Date yellowLightTime;
	
	//红灯亮灯时间
	private Date redLightTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCsiId() {
		return csiId;
	}

	public void setCsiId(String csiId) {
		this.csiId = csiId;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public Integer getLightType() {
		return lightType;
	}

	public void setLightType(Integer lightType) {
		this.lightType = lightType;
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

	public Date getYellowLightTime() {
		return yellowLightTime;
	}

	public void setYellowLightTime(Date yellowLightTime) {
		this.yellowLightTime = yellowLightTime;
	}

	public Date getRedLightTime() {
		return redLightTime;
	}

	public void setRedLightTime(Date redLightTime) {
		this.redLightTime = redLightTime;
	}
}
