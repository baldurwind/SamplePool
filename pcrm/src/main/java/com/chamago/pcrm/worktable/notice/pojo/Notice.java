/**
 * 
 */
package com.chamago.pcrm.worktable.notice.pojo;

import java.util.Date;

import com.chamago.pcrm.common.pojo.BasePojo;

/**
 * 主管，掌柜通知
 * @author gavin.peng
 *
 */
public class Notice extends BasePojo {

	private static final long serialVersionUID = -6181479164275268635L;

	 
	private String id;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 通知内容
	 */
	private String content;
	
	/**
	 * 创建人
	 */
	private String creator;
	
	/**
	 * 创建时间
	 */
	private Date created;
	
	private Date modified;
	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
