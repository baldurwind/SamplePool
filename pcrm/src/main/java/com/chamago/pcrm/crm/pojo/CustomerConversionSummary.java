package com.chamago.pcrm.crm.pojo;

import com.chamago.pcrm.common.pojo.BasePojo;


public class CustomerConversionSummary  extends BasePojo {

	private static final long serialVersionUID = -2741643582479775302L;
	private String month;
	private Integer monthlyPageView;
	private Integer monthlySession;
	private Integer tradeCount;
	private Double totalTradePayment;
	
	
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Integer getMonthlyPageView() {
		return monthlyPageView;
	}
	public void setMonthlyPageView(Integer monthlyPageView) {
		this.monthlyPageView = monthlyPageView;
	}
	public Integer getMonthlySession() {
		return monthlySession;
	}
	public void setMonthlySession(Integer monthlySession) {
		this.monthlySession = monthlySession;
	}
	public Integer getTradeCount() {
		return tradeCount;
	}
	public void setTradeCount(Integer tradeCount) {
		this.tradeCount = tradeCount;
	}
	public Double getTotalTradePayment() {
		return totalTradePayment;
	}
	public void setTotalTradePayment(Double totalTradePayment) {
		this.totalTradePayment = totalTradePayment;
	}
	
}
