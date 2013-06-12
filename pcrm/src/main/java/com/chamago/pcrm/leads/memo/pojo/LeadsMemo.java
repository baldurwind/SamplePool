package com.chamago.pcrm.leads.memo.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.chamago.pcrm.common.pojo.BasePojo;

/**
 * 缺货/减价事件登记 
 * @author James.wang
 */
public class LeadsMemo extends BasePojo {
	
	private static final long serialVersionUID = 508240199540300685L;

	//标识
	private String id;
	
	//买家昵称
	private String buyerNick;
	
	//店铺昵称
	private String sellerNick;
	
	//客服昵称
	private String wangwangNick;
	
	//商品itemId
	private Long numId;
	
	//商品skuId
	private Long skuId;
	
	//商品名称
	private String goodsName;
	
	//客户期望价格
	private BigDecimal price;
	
	//手机号
	private String mobile;
	
	//电子邮件
	private String email;
	
	//提示类型 0:缺货  1:减价
	private Integer type;
	
	//到期时间
	private Date expiredDate;
	
	//状态 0:未提醒  1:已提醒
	private Integer status;
	
	//创建时间
	private Date createDate;
	
	//修改时间
	private Date modifyDate;

	//是否添加客服备注
	private boolean addReminder;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getWangwangNick() {
		return wangwangNick;
	}

	public void setWangwangNick(String wangwangNick) {
		this.wangwangNick = wangwangNick;
	}

	public Long getNumId() {
		return numId;
	}

	public void setNumId(Long numId) {
		this.numId = numId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public boolean isAddReminder() {
		return addReminder;
	}

	public void setAddReminder(boolean addReminder) {
		this.addReminder = addReminder;
	}
}
