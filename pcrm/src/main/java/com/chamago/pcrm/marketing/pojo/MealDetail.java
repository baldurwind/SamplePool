package com.chamago.pcrm.marketing.pojo;

import com.chamago.pcrm.common.pojo.BasePojo;

/**
 * 套餐详细
 * 
 * @author James.wnag
 */
public class MealDetail extends BasePojo {

	private static final long serialVersionUID = -3159381688261054765L;
	private Long itemId;
	private Long mealId;
	private String itemShowName;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getMealId() {
		return mealId;
	}

	public void setMealId(Long mealId) {
		this.mealId = mealId;
	}

	public String getItemShowName() {
		return itemShowName;
	}

	public void setItemShowName(String itemShowName) {
		this.itemShowName = itemShowName;
	}

}
