package com.chamago.pcrm.customerservice.pojo;

import java.util.Date;
import java.util.List;

import com.chamago.pcrm.common.pojo.BasePojo;

/**
 * 售后问题
 * @author James.wang
 */
public class CustomerServiceIssues extends BasePojo {
	
	private static final long serialVersionUID = 8822138846778296679L;
	
	//标识
	private String id;
	
	//淘宝交易编号
	private String tradeId;
		
	//买家昵称
	private String buyerNick;
	
	//卖家昵称
	private String sellerNick;
	
	//售后问题类型
	private String type;
	
	//售后问题的紧急程度     0: 非常紧急   1: 紧急   2: 不紧急
	private String priority;
	
	//问题标题
	private String title;
	
	//问题描述
	private String content;
	
	//附件地址
	private String filePath;
	
	//创建时间
	private Date createTime;
	
	//提交售后问题的客服昵称
	private String nick;
	
	//处理售后问题的客服编号
	private String csNick;
	
	//最后一次的问题状态
	private String status;
	
	//修改时间
	private Date modifyTime;
	
	//当前售后所属Trade
	private Object[] trade;
	
	//Trade 所属orderList
	private List<Object[]> tradeOrder;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	
	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public String getCsNick() {
		return csNick;
	}

	public void setCsNick(String csNick) {
		this.csNick = csNick;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Object[] getTrade() {
		return trade;
	}

	public void setTrade(Object[] trade) {
		this.trade = trade;
	}

	public List<Object[]> getTradeOrder() {
		return tradeOrder;
	}

	public void setTradeOrder(List<Object[]> tradeOrder) {
		this.tradeOrder = tradeOrder;
	}
}
