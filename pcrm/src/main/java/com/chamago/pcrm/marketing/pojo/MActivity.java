package com.chamago.pcrm.marketing.pojo;

import com.chamago.pcrm.common.pojo.BasePojo;

/**
 * 店内活动
 * 
 * @author James.wang
 */
public class MActivity extends BasePojo {

	private static final long serialVersionUID = 8140196164896336873L;

	private Long id;

	private String url;

	private Long appliedCount;

	private Long couponId;

	private String createUser;

	private Long personLimitCount;

	private String status;

	private Long totalCount;

	private String sellerNick;

	private MCoupon coupon;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	public Long getAppliedCount() {
		return appliedCount;
	}

	public void setAppliedCount(Long appliedCount) {
		this.appliedCount = appliedCount;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser == null ? null : createUser.trim();
	}

	public Long getPersonLimitCount() {
		return personLimitCount;
	}

	public void setPersonLimitCount(Long personLimitCount) {
		this.personLimitCount = personLimitCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick == null ? null : sellerNick.trim();
	}

	public MCoupon getCoupon() {
		return coupon;
	}

	public void setCoupon(MCoupon coupon) {
		this.coupon = coupon;
	}

}