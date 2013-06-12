package com.chamago.pcrm.marketing.pojo;

import com.chamago.pcrm.common.pojo.BasePojo;

/**
 * 套餐
 * 
 * @author James.wang
 */
public class Meal extends BasePojo {

	private static final long serialVersionUID = -1355747522716858341L;

	private Long mealId;
	private String sellerNick;
	private String mealName;
	private Long mealPrice;
	private String typePostage;
	private String postageId;
	private String status;
	private String mealMemo;

	public Long getMealId() {
		return mealId;
	}

	public void setMealId(Long mealId) {
		this.mealId = mealId;
	}

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	public String getMealName() {
		return mealName;
	}

	public void setMealName(String mealName) {
		this.mealName = mealName;
	}

	public Long getMealPrice() {
		return mealPrice;
	}

	public void setMealPrice(Long mealPrice) {
		this.mealPrice = mealPrice;
	}

	public String getTypePostage() {
		return typePostage;
	}

	public void setTypePostage(String typePostage) {
		this.typePostage = typePostage;
	}

	public String getPostageId() {
		return postageId;
	}

	public void setPostageId(String postageId) {
		this.postageId = postageId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMealMemo() {
		return mealMemo;
	}

	public void setMealMemo(String mealMemo) {
		this.mealMemo = mealMemo;
	}

}