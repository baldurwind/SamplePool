/**
 * 
 */
package com.chamago.pcrm.worktable.connect.pojo;

import java.util.Date;

import com.chamago.pcrm.common.pojo.BasePojo;

/**
 * @author gavin.peng
 *
 */
public class LoginLog extends BasePojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3980857168037156725L;
	
	
	private String id;
	private String nick;
	private String sysUser;
	private Date startTime;
	private Date endTime;
	private Date created;
	private Date modified;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getSysUser() {
		return sysUser;
	}
	public void setSysUser(String sysUser) {
		this.sysUser = sysUser;
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
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	

}
