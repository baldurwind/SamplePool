package com.chamago.pcrm.crm.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.chamago.pcrm.common.pojo.BasePojo;


public class CrmTradeOrder extends BasePojo {
    /**
	 * 
	 */
	private static final long serialVersionUID = -924611377623040999L;

	private Long oid;

    private Long cid;

    private Long tid;

    private Long numIid;

    private Long skuId;

    private String skuPropertiesName;

    private String outerIid;

    private String outerSkuId;

    private String sellerNick;

    private String buyerNick;

    private String title;

    private BigDecimal price;

    private String picPath;

    private Long num;

    private String status;

    private BigDecimal totalFee;

    private BigDecimal discountFee;

    private BigDecimal adjustFee;

    private BigDecimal payment;

    private String snapshot;

    private String snapshotUrl;

    private Long itemMealId;

    private String itemMealName;

    private Long refundId;

    private String refundStatus;

    private Date timeoutActionTime;

    private Boolean buyerRate;

    private Boolean sellerRate;

    private String sellerType;

    private Boolean isOversold;

    private Date created;

    private Date modified;

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Long getNumIid() {
        return numIid;
    }

    public void setNumIid(Long numIid) {
        this.numIid = numIid;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuPropertiesName() {
        return skuPropertiesName;
    }

    public void setSkuPropertiesName(String skuPropertiesName) {
        this.skuPropertiesName = skuPropertiesName == null ? null : skuPropertiesName.trim();
    }

    public String getOuterIid() {
        return outerIid;
    }

    public void setOuterIid(String outerIid) {
        this.outerIid = outerIid == null ? null : outerIid.trim();
    }

    public String getOuterSkuId() {
        return outerSkuId;
    }

    public void setOuterSkuId(String outerSkuId) {
        this.outerSkuId = outerSkuId == null ? null : outerSkuId.trim();
    }

    public String getSellerNick() {
        return sellerNick;
    }

    public void setSellerNick(String sellerNick) {
        this.sellerNick = sellerNick == null ? null : sellerNick.trim();
    }

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick == null ? null : buyerNick.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath == null ? null : picPath.trim();
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public BigDecimal getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(BigDecimal discountFee) {
        this.discountFee = discountFee;
    }

    public BigDecimal getAdjustFee() {
        return adjustFee;
    }

    public void setAdjustFee(BigDecimal adjustFee) {
        this.adjustFee = adjustFee;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot == null ? null : snapshot.trim();
    }

    public String getSnapshotUrl() {
        return snapshotUrl;
    }

    public void setSnapshotUrl(String snapshotUrl) {
        this.snapshotUrl = snapshotUrl == null ? null : snapshotUrl.trim();
    }

    public Long getItemMealId() {
        return itemMealId;
    }

    public void setItemMealId(Long itemMealId) {
        this.itemMealId = itemMealId;
    }

    public String getItemMealName() {
        return itemMealName;
    }

    public void setItemMealName(String itemMealName) {
        this.itemMealName = itemMealName == null ? null : itemMealName.trim();
    }

    public Long getRefundId() {
        return refundId;
    }

    public void setRefundId(Long refundId) {
        this.refundId = refundId;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus == null ? null : refundStatus.trim();
    }

    public Date getTimeoutActionTime() {
        return timeoutActionTime;
    }

    public void setTimeoutActionTime(Date timeoutActionTime) {
        this.timeoutActionTime = timeoutActionTime;
    }

    public Boolean getBuyerRate() {
        return buyerRate;
    }

    public void setBuyerRate(Boolean buyerRate) {
        this.buyerRate = buyerRate;
    }

    public Boolean getSellerRate() {
        return sellerRate;
    }

    public void setSellerRate(Boolean sellerRate) {
        this.sellerRate = sellerRate;
    }

    public String getSellerType() {
        return sellerType;
    }

    public void setSellerType(String sellerType) {
        this.sellerType = sellerType == null ? null : sellerType.trim();
    }

    public Boolean getIsOversold() {
        return isOversold;
    }

	public void setIsOversold(Boolean isOversold) {
        this.isOversold = isOversold;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
    @Override
	public String toString() {
		return "CrmTradeOrder [adjustFee=" + adjustFee + ", buyerNick="
				+ buyerNick + ", buyerRate=" + buyerRate + ", cid=" + cid
				+ ", created=" + created + ", discountFee=" + discountFee
				+ ", isOversold=" + isOversold + ", itemMealId=" + itemMealId
				+ ", itemMealName=" + itemMealName + ", modified=" + modified
				+ ", num=" + num + ", numIid=" + numIid + ", oid=" + oid
				+ ", outerIid=" + outerIid + ", outerSkuId=" + outerSkuId
				+ ", payment=" + payment + ", picPath=" + picPath + ", price="
				+ price + ", refundId=" + refundId + ", refundStatus="
				+ refundStatus + ", sellerNick=" + sellerNick + ", sellerRate="
				+ sellerRate + ", sellerType=" + sellerType + ", skuId="
				+ skuId + ", skuPropertiesName=" + skuPropertiesName
				+ ", snapshot=" + snapshot + ", snapshotUrl=" + snapshotUrl
				+ ", status=" + status + ", tid=" + tid
				+ ", timeoutActionTime=" + timeoutActionTime + ", title="
				+ title + ", totalFee=" + totalFee + "]";
	}
}