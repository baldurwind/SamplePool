package com.chamago.pcrm.crm.pojo;

import java.util.Date;

import com.chamago.pcrm.common.pojo.BasePojo;


public class CrmGradePromotion  extends BasePojo {
    /**
	 * 
	 */
	private static final long serialVersionUID = 757109393358521691L;

	private Long id;

    private String curGrade;

    private String sellerNick;

    private Integer curGradeName;

    private Long discount;

    private Long nextUpgradeAmount;

    private Date creTime;

    private Date uptTime;

    private Integer nextUpgradeCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurGrade() {
        return curGrade;
    }

    public void setCurGrade(String curGrade) {
        this.curGrade = curGrade == null ? null : curGrade.trim();
    }

    public String getSellerNick() {
        return sellerNick;
    }

    public void setSellerNick(String sellerNick) {
        this.sellerNick = sellerNick == null ? null : sellerNick.trim();
    }

    public Integer getCurGradeName() {
        return curGradeName;
    }

    public void setCurGradeName(Integer curGradeName) {
        this.curGradeName = curGradeName;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Long getNextUpgradeAmount() {
        return nextUpgradeAmount;
    }

    public void setNextUpgradeAmount(Long nextUpgradeAmount) {
        this.nextUpgradeAmount = nextUpgradeAmount;
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public Date getUptTime() {
        return uptTime;
    }

    public void setUptTime(Date uptTime) {
        this.uptTime = uptTime;
    }

    public Integer getNextUpgradeCount() {
        return nextUpgradeCount;
    }

    public void setNextUpgradeCount(Integer nextUpgradeCount) {
        this.nextUpgradeCount = nextUpgradeCount;
    }
}