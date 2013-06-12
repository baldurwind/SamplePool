package com.chamago.pcrm.crm.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.chamago.pcrm.common.pojo.BasePojo;


public class CrmItem extends BasePojo {
  

	private static final long serialVersionUID = -8216342324477437092L;

	private Long numIid;

    private Long cid;

    private String nick;

    private String title;

    private String type;

    private String detailUrl;

    private String propsName;

    private String promotedService;

    private Boolean isLightningConsignment;

    private Long isFenxiao;

    private Long templateId;

    private String sellerCids;

    private String props;

    private String inputPids;

    private String inputStr;

    private String picUrl;

    private Long num;

    private Long validThru;

    private Date listTime;

    private Date delistTime;

    private String stuffStatus;

    private BigDecimal price;

    private BigDecimal postFee;

    private BigDecimal expressFee;

    private BigDecimal emsFee;

    private Boolean hasDiscount;

    private String freightPayer;

    private Boolean hasInvoice;

    private Boolean hasWarranty;

    private Boolean hasShowcase;

    private String increment;

    private String approveStatus;

    private Long postageId;

    private Long productId;

    private Long auctionPoint;

    private String propertyAlias;

    private String outerId;

    private Boolean isVirtual;

    private Boolean isTaobao;

    private Boolean isEx;

    private Boolean isTiming;

    private Boolean is3d;

    private Long score;

    private Long volume;

    private Boolean oneStation;

    private String secondKill;

    private String autoFill;

    private Boolean violation;

    private Boolean isPrepay;

    private Boolean wwStatus;

    private String wapDetailUrl;

    private Long afterSaleId;

    private Long codPostageId;

    private Boolean sellPromise;

    private Date created;

    private Date modified;

    private String city;

    private String state;

    private String desc;

    private String wapDesc;

    public Long getNumIid() {
        return numIid;
    }

    public void setNumIid(Long numIid) {
        this.numIid = numIid;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick == null ? null : nick.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl == null ? null : detailUrl.trim();
    }

    public String getPropsName() {
        return propsName;
    }

    public void setPropsName(String propsName) {
        this.propsName = propsName == null ? null : propsName.trim();
    }

    public String getPromotedService() {
        return promotedService;
    }

    public void setPromotedService(String promotedService) {
        this.promotedService = promotedService == null ? null : promotedService.trim();
    }

    public Boolean getIsLightningConsignment() {
        return isLightningConsignment;
    }

    public void setIsLightningConsignment(Boolean isLightningConsignment) {
        this.isLightningConsignment = isLightningConsignment;
    }

    public Long getIsFenxiao() {
        return isFenxiao;
    }

    public void setIsFenxiao(Long isFenxiao) {
        this.isFenxiao = isFenxiao;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getSellerCids() {
        return sellerCids;
    }

    public void setSellerCids(String sellerCids) {
        this.sellerCids = sellerCids == null ? null : sellerCids.trim();
    }

    public String getProps() {
        return props;
    }

    public void setProps(String props) {
        this.props = props == null ? null : props.trim();
    }

    public String getInputPids() {
        return inputPids;
    }

    public void setInputPids(String inputPids) {
        this.inputPids = inputPids == null ? null : inputPids.trim();
    }

    public String getInputStr() {
        return inputStr;
    }

    public void setInputStr(String inputStr) {
        this.inputStr = inputStr == null ? null : inputStr.trim();
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Long getValidThru() {
        return validThru;
    }

    public void setValidThru(Long validThru) {
        this.validThru = validThru;
    }

    public Date getListTime() {
        return listTime;
    }

    public void setListTime(Date listTime) {
        this.listTime = listTime;
    }

    public Date getDelistTime() {
        return delistTime;
    }

    public void setDelistTime(Date delistTime) {
        this.delistTime = delistTime;
    }

    public String getStuffStatus() {
        return stuffStatus;
    }

    public void setStuffStatus(String stuffStatus) {
        this.stuffStatus = stuffStatus == null ? null : stuffStatus.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPostFee() {
        return postFee;
    }

    public void setPostFee(BigDecimal postFee) {
        this.postFee = postFee;
    }

    public BigDecimal getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(BigDecimal expressFee) {
        this.expressFee = expressFee;
    }

    public BigDecimal getEmsFee() {
        return emsFee;
    }

    public void setEmsFee(BigDecimal emsFee) {
        this.emsFee = emsFee;
    }

    public Boolean getHasDiscount() {
        return hasDiscount;
    }

    public void setHasDiscount(Boolean hasDiscount) {
        this.hasDiscount = hasDiscount;
    }

    public String getFreightPayer() {
        return freightPayer;
    }

    public void setFreightPayer(String freightPayer) {
        this.freightPayer = freightPayer == null ? null : freightPayer.trim();
    }

    public Boolean getHasInvoice() {
        return hasInvoice;
    }

    public void setHasInvoice(Boolean hasInvoice) {
        this.hasInvoice = hasInvoice;
    }

    public Boolean getHasWarranty() {
        return hasWarranty;
    }

    public void setHasWarranty(Boolean hasWarranty) {
        this.hasWarranty = hasWarranty;
    }

    public Boolean getHasShowcase() {
        return hasShowcase;
    }

    public void setHasShowcase(Boolean hasShowcase) {
        this.hasShowcase = hasShowcase;
    }

    public String getIncrement() {
        return increment;
    }

    public void setIncrement(String increment) {
        this.increment = increment == null ? null : increment.trim();
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus == null ? null : approveStatus.trim();
    }

    public Long getPostageId() {
        return postageId;
    }

    public void setPostageId(Long postageId) {
        this.postageId = postageId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getAuctionPoint() {
        return auctionPoint;
    }

    public void setAuctionPoint(Long auctionPoint) {
        this.auctionPoint = auctionPoint;
    }

    public String getPropertyAlias() {
        return propertyAlias;
    }

    public void setPropertyAlias(String propertyAlias) {
        this.propertyAlias = propertyAlias == null ? null : propertyAlias.trim();
    }

    public String getOuterId() {
        return outerId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId == null ? null : outerId.trim();
    }

    public Boolean getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(Boolean isVirtual) {
        this.isVirtual = isVirtual;
    }

    public Boolean getIsTaobao() {
        return isTaobao;
    }

    public void setIsTaobao(Boolean isTaobao) {
        this.isTaobao = isTaobao;
    }

    public Boolean getIsEx() {
        return isEx;
    }

    public void setIsEx(Boolean isEx) {
        this.isEx = isEx;
    }

    public Boolean getIsTiming() {
        return isTiming;
    }

    public void setIsTiming(Boolean isTiming) {
        this.isTiming = isTiming;
    }

    public Boolean getIs3d() {
        return is3d;
    }

    public void setIs3d(Boolean is3d) {
        this.is3d = is3d;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Boolean getOneStation() {
        return oneStation;
    }

    public void setOneStation(Boolean oneStation) {
        this.oneStation = oneStation;
    }

    public String getSecondKill() {
        return secondKill;
    }

    public void setSecondKill(String secondKill) {
        this.secondKill = secondKill == null ? null : secondKill.trim();
    }

    public String getAutoFill() {
        return autoFill;
    }

    public void setAutoFill(String autoFill) {
        this.autoFill = autoFill == null ? null : autoFill.trim();
    }

    public Boolean getViolation() {
        return violation;
    }

    public void setViolation(Boolean violation) {
        this.violation = violation;
    }

    public Boolean getIsPrepay() {
        return isPrepay;
    }

    public void setIsPrepay(Boolean isPrepay) {
        this.isPrepay = isPrepay;
    }

    public Boolean getWwStatus() {
        return wwStatus;
    }

    public void setWwStatus(Boolean wwStatus) {
        this.wwStatus = wwStatus;
    }

    public String getWapDetailUrl() {
        return wapDetailUrl;
    }

    public void setWapDetailUrl(String wapDetailUrl) {
        this.wapDetailUrl = wapDetailUrl == null ? null : wapDetailUrl.trim();
    }

    public Long getAfterSaleId() {
        return afterSaleId;
    }

    public void setAfterSaleId(Long afterSaleId) {
        this.afterSaleId = afterSaleId;
    }

    public Long getCodPostageId() {
        return codPostageId;
    }

    public void setCodPostageId(Long codPostageId) {
        this.codPostageId = codPostageId;
    }

    public Boolean getSellPromise() {
        return sellPromise;
    }

    public void setSellPromise(Boolean sellPromise) {
        this.sellPromise = sellPromise;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public String getWapDesc() {
        return wapDesc;
    }

    public void setWapDesc(String wapDesc) {
        this.wapDesc = wapDesc == null ? null : wapDesc.trim();
    }
}