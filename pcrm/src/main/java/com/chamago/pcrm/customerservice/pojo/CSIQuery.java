package com.chamago.pcrm.customerservice.pojo;

import com.chamago.pcrm.common.pojo.BasePojo;

/**
 * 售后问题查询实体
 * @author James.wang
 */
public class CSIQuery extends BasePojo {
	
	private static final long serialVersionUID = 3524540553231420073L;

	//交易编号
	private String tradeId;
	
	//售后问题类型
	private String type;
	
	//售后问题紧急程度
	private String priority;
	
	//售后问题当前状态
	private String status;
	
	//售后问题所属处理者
	private String nick;
	
	//店铺名称
	private String sellerNick;
	
	//当前买家
	private String buyer;

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}
}
