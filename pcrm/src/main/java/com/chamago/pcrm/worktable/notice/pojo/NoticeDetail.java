/**
 * 
 */
package com.chamago.pcrm.worktable.notice.pojo;

import java.util.Date;

import com.chamago.pcrm.common.pojo.BasePojo;

/**
 * 
 * 客服通知
 * @author gavin.peng
 *
 */
public class NoticeDetail extends BasePojo {

	private static final long serialVersionUID = -56446813468147109L;
	
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 通知ID
	 */
	private String noticeId;
	
	/**
	 * 是否读，0 未读 1 已读 
	 */
	private Integer read;
	/**
	 * 创建日期
	 */
	private Date created;
	/**
	 * 修改日期
	 */
	private Date modified;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	public Integer getRead() {
		return read;
	}
	public void setRead(Integer read) {
		this.read = read;
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

}
