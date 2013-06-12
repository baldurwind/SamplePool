/**
 * 
 */
package com.chamago.pcrm.worktable.advice.pojo;

import java.util.Date;

import com.chamago.pcrm.common.pojo.BasePojo;

/**
 * @author gavin.peng
 *
 */
public class Advice extends BasePojo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2547587865781172379L;
	private String id;
	private String title;
	private String content;
	private String creator;
	private String type;
	private float score;
	private int orientedType;
	private String sellerNick;
	private int status;
	private Date created;
	private Date modified;
	
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public int getOrientedType() {
		return orientedType;
	}
	public void setOrientedType(int orientedType) {
		this.orientedType = orientedType;
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
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
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
	
	public String getSellerNick() {
		return sellerNick;
	}
	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	

}
