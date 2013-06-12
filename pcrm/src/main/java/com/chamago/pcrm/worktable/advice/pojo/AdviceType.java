/**
 * 
 */
package com.chamago.pcrm.worktable.advice.pojo;

import java.util.Date;

import com.chamago.pcrm.common.pojo.BasePojo;

/**
 * 建议分类
 * @author gavin.peng
 *
 */
public class AdviceType extends BasePojo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3885791857082191254L;
	private String id;
	private String name;
	private String creator;
	private String sellerNick;
	private Date created;
	private Date modified;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	
	public String getSellerNick() {
		return sellerNick;
	}
	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}
	
}
