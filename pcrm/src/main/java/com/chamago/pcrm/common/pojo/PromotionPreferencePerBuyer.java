package com.chamago.pcrm.common.pojo;


public class PromotionPreferencePerBuyer  extends BasePojo{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5828997294837926384L;

	private String buyerNick;

    private String name;

    private Long promotionCount;

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick == null ? null : buyerNick.trim();
    }



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPromotionCount() {
        return promotionCount;
    }

    public void setPromotionCount(Long promotionCount) {
        this.promotionCount = promotionCount;
    }
}