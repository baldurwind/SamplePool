package com.chamago.pcrm.common.pojo;


public class TotalPageView extends BasePojo{
	
	private static final long serialVersionUID = -6087460950725760487L;

	private String nick;

    private String buyerNick;

    private Long totalPageview;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick == null ? null : nick.trim();
    }

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick == null ? null : buyerNick.trim();
    }

    public Long getTotalPageview() {
        return totalPageview;
    }

    public void setTotalPageview(Long totalPageview) {
        this.totalPageview = totalPageview;
    }
}