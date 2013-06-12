package com.chamago.pcrm.common.pojo;

import java.util.Date;


public class Subscriber extends BasePojo{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5628353973609752768L;

	private Integer id;

    private String appKey;

    private String nick;

    private String sessionKey;

    private String status;
    
    private Date created;
    private Date modified;
    private Date deadline;
    
    private int isInit;
    
    private Long userid;

	public int getIsInit() {
		return isInit;
	}

	public void setIsInit(int isInit) {
		this.isInit = isInit;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey == null ? null : appKey.trim();
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick == null ? null : nick.trim();
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey == null ? null : sessionKey.trim();
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}
    
}