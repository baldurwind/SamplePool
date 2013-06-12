package com.chamago.pcrm.crm.pojo;

import java.util.Date;

import com.chamago.pcrm.common.pojo.BasePojo;


public class CrmCustomer extends BasePojo {


	private static final long serialVersionUID = 102580885034547876L;

	private Long id;

    private String sellerNick;

    private String buyerNick;

    private String status;

    private Date creTime;

    private Date updTime;

    private Long relationSource;

    private Integer gender;

    private Date birthday;

    private String style;

    private String frequency;

    private String groupIds;

    private Integer provinceId;

    private Integer cityId;

    private String district;

    private String zip;

    private String alipayNo;

    private Long tradeCount;

    private Long tradeAmount;

    private Long closeTradeAmount;

    private Long closeTradeCount;

    private Long itemNum;

    private Long itemCloseCount;

    private Long bizOrderId;

    private Date lastTradeTime;

    private Long grade;

    private Long address;

    private Double avgPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    public Long getRelationSource() {
        return relationSource;
    }

    public void setRelationSource(Long relationSource) {
        this.relationSource = relationSource;
    }


    public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style == null ? null : style.trim();
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency == null ? null : frequency.trim();
    }

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds == null ? null : groupIds.trim();
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip == null ? null : zip.trim();
    }

    public String getAlipayNo() {
        return alipayNo;
    }

    public void setAlipayNo(String alipayNo) {
        this.alipayNo = alipayNo == null ? null : alipayNo.trim();
    }

    public Long getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(Long tradeCount) {
        this.tradeCount = tradeCount;
    }

    public Long getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(Long tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public Long getCloseTradeAmount() {
        return closeTradeAmount;
    }

    public void setCloseTradeAmount(Long closeTradeAmount) {
        this.closeTradeAmount = closeTradeAmount;
    }

    public Long getCloseTradeCount() {
        return closeTradeCount;
    }

    public void setCloseTradeCount(Long closeTradeCount) {
        this.closeTradeCount = closeTradeCount;
    }

    public Long getItemNum() {
        return itemNum;
    }

    public void setItemNum(Long itemNum) {
        this.itemNum = itemNum;
    }

    public Long getItemCloseCount() {
        return itemCloseCount;
    }

    public void setItemCloseCount(Long itemCloseCount) {
        this.itemCloseCount = itemCloseCount;
    }

    public Long getBizOrderId() {
        return bizOrderId;
    }

    public void setBizOrderId(Long bizOrderId) {
        this.bizOrderId = bizOrderId;
    }

    public Date getLastTradeTime() {
        return lastTradeTime;
    }

    public void setLastTradeTime(Date lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }

    public Long getAddress() {
        return address;
    }

    public void setAddress(Long address) {
        this.address = address;
    }

    public Double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Double avgPrice) {
        this.avgPrice = avgPrice;
    }
}