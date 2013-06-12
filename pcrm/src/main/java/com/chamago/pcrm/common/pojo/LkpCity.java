package com.chamago.pcrm.common.pojo;


public class LkpCity extends BasePojo {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5295778010091625951L;
	private Integer id;
    private String name;
    private Short tbId;
    private Short weiboId;

    
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
    public Short getTbId() {
        return tbId;
    }
    public void setTbId(Short tbId) {
        this.tbId = tbId;
    }
    public Short getWeiboId() {
        return weiboId;
    }
    public void setWeiboId(Short weiboId) {
        this.weiboId = weiboId;
    }
}