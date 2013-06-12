package com.chamago.pcrm.worktable.reminder.pojo;

import java.util.Date;

import com.chamago.pcrm.common.pojo.BasePojo;

/**
 * 客服备忘信息实体
 * @author James.wang
 */
public class Reminder extends BasePojo {
	
	private static final long serialVersionUID = 6279370005940910735L;

	//标识
	private String id;
	
	//提醒方式   0:时间提醒  　1:条件提醒
	private Integer tipType;
	
	//提醒时间
	private Date tipTime;
	
	//客户昵称
	private String nick;
	
	//店铺昵称
	private String sellerNick;
	
	//关联的客户昵称
	private String buyerNick;
	
	//提醒内容
	private String content;
	
	//备忘信息状态
	private Integer status;
	
	//创建时间
	private Date createTime;
	
	//修改时间
	private Date modifyTime;

	//时间相差的分钟数   大于0为未过期　　小于0为已过期
	private Long dateDifferentMin;
	
	//时间相差的信息(天数，小时数，分钟数)
	private String dateDifferentMsg;

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

	public Integer getTipType() {
		return tipType;
	}

	public void setTipType(Integer tipType) {
		this.tipType = tipType;
	}
	
	public Date getTipTime() {
		return tipTime;
	}

	public void setTipTime(Date tipTime) {
		this.tipTime = tipTime;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Long getDateDifferentMin() {
		return dateDifferentMin;
	}

	public void setDateDifferentMin(Long dateDifferentMin) {
		this.dateDifferentMin = dateDifferentMin;
	}

	public String getDateDifferentMsg() {
		return dateDifferentMsg;
	}

	public void setDateDifferentMsg(String dateDifferentMsg) {
		this.dateDifferentMsg = dateDifferentMsg;
	}
}
