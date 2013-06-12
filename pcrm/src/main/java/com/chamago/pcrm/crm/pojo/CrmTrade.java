package com.chamago.pcrm.crm.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.chamago.pcrm.common.pojo.BasePojo;


public class CrmTrade  extends BasePojo{
	
	private static final long serialVersionUID = -2939571844807684536L;

	private Long tid;

    private Long numIid;

    private String type;

    private String tradeFrom;

    private String status;

    private String buyerNick;

    private String sellerNick;

    private String title;

    private String picPath;

    private BigDecimal price;

    private Long num;

    private BigDecimal payment;

    private BigDecimal receivedPayment;

    private Date payTime;

    private BigDecimal availableConfirmFee;

    private BigDecimal totalFee;

    private Boolean hasPostFee;

    private BigDecimal postFee;

    private BigDecimal codFee;

    private String codStatus;

    private BigDecimal discountFee;

    private BigDecimal commissionFee;

    private BigDecimal adjustFee;

    private BigDecimal buyerCodFee;

    private BigDecimal sellerCodFee;

    private BigDecimal expressAgencyFee;

    private Long pointFee;

    private BigDecimal buyerObtainPointFee;

    private Long realPointFee;

    private String alipayNo;

    private String buyerAlipayNo;

    private String sellerAlipayNo;

    private String alipayUrl;

    private String alipayWarnMsg;

    private String shippingType;

    private Date consignTime;

    private String receiverName;

    private String receiverState;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;

    private String receiverZip;

    private String receiverMobile;

    private String receiverPhone;

    private String buyerEmail;

    private String sellerName;

    private String sellerMobile;

    private String sellerPhone;

    private String sellerEmail;

    private Boolean is3d;

    private String promotion;

    private String buyerMessage;

    private Long buyerFlag;

    private Long sellerFlag;

    private Boolean sellerRate;

    private Boolean buyerRate;

    private String invoiceName;

    private String snapshotUrl;

    private String snapshot;

    private Date timeoutActionTime;

    private Date endTime;

    private Date created;

    private Date modified;

    private String buyerMemo;

    private String sellerMemo;

    private String tradeMemo;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getTradeFrom() {
        return tradeFrom;
    }

    public void setTradeFrom(String tradeFrom) {
        this.tradeFrom = tradeFrom == null ? null : tradeFrom.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick == null ? null : buyerNick.trim();
    }

    public String getSellerNick() {
        return sellerNick;
    }

    public void setSellerNick(String sellerNick) {
        this.sellerNick = sellerNick == null ? null : sellerNick.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath == null ? null : picPath.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public BigDecimal getReceivedPayment() {
        return receivedPayment;
    }

    public void setReceivedPayment(BigDecimal receivedPayment) {
        this.receivedPayment = receivedPayment;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public BigDecimal getAvailableConfirmFee() {
        return availableConfirmFee;
    }

    public void setAvailableConfirmFee(BigDecimal availableConfirmFee) {
        this.availableConfirmFee = availableConfirmFee;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public Boolean getHasPostFee() {
        return hasPostFee;
    }

    public void setHasPostFee(Boolean hasPostFee) {
        this.hasPostFee = hasPostFee;
    }

    public BigDecimal getPostFee() {
        return postFee;
    }

    public void setPostFee(BigDecimal postFee) {
        this.postFee = postFee;
    }

    public BigDecimal getCodFee() {
        return codFee;
    }

    public void setCodFee(BigDecimal codFee) {
        this.codFee = codFee;
    }

    public String getCodStatus() {
        return codStatus;
    }

    public void setCodStatus(String codStatus) {
        this.codStatus = codStatus == null ? null : codStatus.trim();
    }

    public BigDecimal getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(BigDecimal discountFee) {
        this.discountFee = discountFee;
    }

    public BigDecimal getCommissionFee() {
        return commissionFee;
    }

    public void setCommissionFee(BigDecimal commissionFee) {
        this.commissionFee = commissionFee;
    }

    public BigDecimal getAdjustFee() {
        return adjustFee;
    }

    public void setAdjustFee(BigDecimal adjustFee) {
        this.adjustFee = adjustFee;
    }

    public BigDecimal getBuyerCodFee() {
        return buyerCodFee;
    }

    public void setBuyerCodFee(BigDecimal buyerCodFee) {
        this.buyerCodFee = buyerCodFee;
    }

    public BigDecimal getSellerCodFee() {
        return sellerCodFee;
    }

    public void setSellerCodFee(BigDecimal sellerCodFee) {
        this.sellerCodFee = sellerCodFee;
    }

    public BigDecimal getExpressAgencyFee() {
        return expressAgencyFee;
    }

    public void setExpressAgencyFee(BigDecimal expressAgencyFee) {
        this.expressAgencyFee = expressAgencyFee;
    }

    public Long getPointFee() {
        return pointFee;
    }

    public void setPointFee(Long pointFee) {
        this.pointFee = pointFee;
    }

    public BigDecimal getBuyerObtainPointFee() {
        return buyerObtainPointFee;
    }

    public void setBuyerObtainPointFee(BigDecimal buyerObtainPointFee) {
        this.buyerObtainPointFee = buyerObtainPointFee;
    }

    public Long getRealPointFee() {
        return realPointFee;
    }

    public void setRealPointFee(Long realPointFee) {
        this.realPointFee = realPointFee;
    }

    public String getAlipayNo() {
        return alipayNo;
    }

    public void setAlipayNo(String alipayNo) {
        this.alipayNo = alipayNo == null ? null : alipayNo.trim();
    }

    public String getBuyerAlipayNo() {
        return buyerAlipayNo;
    }

    public void setBuyerAlipayNo(String buyerAlipayNo) {
        this.buyerAlipayNo = buyerAlipayNo == null ? null : buyerAlipayNo.trim();
    }

    public String getSellerAlipayNo() {
        return sellerAlipayNo;
    }

    public void setSellerAlipayNo(String sellerAlipayNo) {
        this.sellerAlipayNo = sellerAlipayNo == null ? null : sellerAlipayNo.trim();
    }

    public String getAlipayUrl() {
        return alipayUrl;
    }

    public void setAlipayUrl(String alipayUrl) {
        this.alipayUrl = alipayUrl == null ? null : alipayUrl.trim();
    }

    public String getAlipayWarnMsg() {
        return alipayWarnMsg;
    }

    public void setAlipayWarnMsg(String alipayWarnMsg) {
        this.alipayWarnMsg = alipayWarnMsg == null ? null : alipayWarnMsg.trim();
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType == null ? null : shippingType.trim();
    }

    public Date getConsignTime() {
        return consignTime;
    }

    public void setConsignTime(Date consignTime) {
        this.consignTime = consignTime;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    public String getReceiverState() {
        return receiverState;
    }

    public void setReceiverState(String receiverState) {
        this.receiverState = receiverState == null ? null : receiverState.trim();
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity == null ? null : receiverCity.trim();
    }

    public String getReceiverDistrict() {
        return receiverDistrict;
    }

    public void setReceiverDistrict(String receiverDistrict) {
        this.receiverDistrict = receiverDistrict == null ? null : receiverDistrict.trim();
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress == null ? null : receiverAddress.trim();
    }

    public String getReceiverZip() {
        return receiverZip;
    }

    public void setReceiverZip(String receiverZip) {
        this.receiverZip = receiverZip == null ? null : receiverZip.trim();
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile == null ? null : receiverMobile.trim();
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone == null ? null : receiverPhone.trim();
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail == null ? null : buyerEmail.trim();
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName == null ? null : sellerName.trim();
    }

    public String getSellerMobile() {
        return sellerMobile;
    }

    public void setSellerMobile(String sellerMobile) {
        this.sellerMobile = sellerMobile == null ? null : sellerMobile.trim();
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone == null ? null : sellerPhone.trim();
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail == null ? null : sellerEmail.trim();
    }

    public Boolean getIs3d() {
        return is3d;
    }

    public void setIs3d(Boolean is3d) {
        this.is3d = is3d;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion == null ? null : promotion.trim();
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage == null ? null : buyerMessage.trim();
    }

    public Long getBuyerFlag() {
        return buyerFlag;
    }

    public void setBuyerFlag(Long buyerFlag) {
        this.buyerFlag = buyerFlag;
    }

    public Long getSellerFlag() {
        return sellerFlag;
    }

    public void setSellerFlag(Long sellerFlag) {
        this.sellerFlag = sellerFlag;
    }

    public Boolean getSellerRate() {
        return sellerRate;
    }

    public void setSellerRate(Boolean sellerRate) {
        this.sellerRate = sellerRate;
    }

    public Boolean getBuyerRate() {
        return buyerRate;
    }

    public void setBuyerRate(Boolean buyerRate) {
        this.buyerRate = buyerRate;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName == null ? null : invoiceName.trim();
    }

    public String getSnapshotUrl() {
        return snapshotUrl;
    }

    public void setSnapshotUrl(String snapshotUrl) {
        this.snapshotUrl = snapshotUrl == null ? null : snapshotUrl.trim();
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot == null ? null : snapshot.trim();
    }

    public Date getTimeoutActionTime() {
        return timeoutActionTime;
    }

    public void setTimeoutActionTime(Date timeoutActionTime) {
        this.timeoutActionTime = timeoutActionTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public String getBuyerMemo() {
        return buyerMemo;
    }

    public void setBuyerMemo(String buyerMemo) {
        this.buyerMemo = buyerMemo == null ? null : buyerMemo.trim();
    }

    public String getSellerMemo() {
        return sellerMemo;
    }

    public void setSellerMemo(String sellerMemo) {
        this.sellerMemo = sellerMemo == null ? null : sellerMemo.trim();
    }

    public String getTradeMemo() {
        return tradeMemo;
    }

    public void setTradeMemo(String tradeMemo) {
        this.tradeMemo = tradeMemo == null ? null : tradeMemo.trim();
    }
}