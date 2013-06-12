/**
 * 
 */
package com.chamago.pcrm.common.utils.polling;

import java.util.Date;

/**
 * @author Kevin
 *
 */
public class MsgEventSource  {

	/**
	 * 
	 */
	
	private String userId;
	private int msgCount;
	private Date createTime;
	

	public MsgEventSource(String userId,int msgCount) {
		this.msgCount = msgCount;
		this.userId = userId;
		this.createTime = new Date();
		// TODO Auto-generated constructor stub
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(int msgCount) {
		this.msgCount = msgCount;
	}


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	

}
