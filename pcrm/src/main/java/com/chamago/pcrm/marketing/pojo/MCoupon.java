package com.chamago.pcrm.marketing.pojo;

import java.util.Date;

import com.chamago.pcrm.common.pojo.BasePojo;

/**
 * 优惠券
 * 
 * @author James.wang
 */
public class MCoupon extends BasePojo {

	private static final long serialVersionUID = -798717772831916237L;

	private Long id;

	private Long condition;

	private String createChannel;

	private Date createTime;

	private Long denominations;

	private Date endTime;

	private String sellerNick;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCondition() {
		return condition;
	}

	public void setCondition(Long condition) {
		this.condition = condition;
	}

	public String getCreateChannel() {
		return createChannel;
	}

	public void setCreateChannel(String createChannel) {
		this.createChannel = createChannel == null ? null : createChannel
				.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getDenominations() {
		return denominations;
	}

	public void setDenominations(Long denominations) {
		this.denominations = denominations;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick == null ? null : sellerNick.trim();
	}
}