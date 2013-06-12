package com.chamago.pcrm.common.pojo;


public class LkpProvince extends BasePojo {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3058047793890415658L;

	private Integer id;

    private String name;

    private Short weiboId;

    private Short tbId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Short getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(Short weiboId) {
        this.weiboId = weiboId;
    }

    public Short getTbId() {
        return tbId;
    }

    public void setTbId(Short tbId) {
        this.tbId = tbId;
    }
}