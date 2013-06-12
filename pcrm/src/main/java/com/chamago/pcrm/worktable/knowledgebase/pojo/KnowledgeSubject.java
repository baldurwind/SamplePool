package com.chamago.pcrm.worktable.knowledgebase.pojo;

import java.util.Date;

import com.chamago.pcrm.common.pojo.BasePojo;
/**
 * 知识主题 
 * @author gavin.peng
 *
 */
public class KnowledgeSubject extends BasePojo{

	private static final long serialVersionUID = 3165560760820494285L;
	/**
	 * 唯一标识
	 */
	private String id;
	/**
	 * 主题父ID
	 */
	private String parentId;
	
	/**
	 * 是否是叶子节点
	 */
	private int leaf;
	
	

	/**
	 * 主题名称
	 */
	private String subject;
	
	/**
	 * 创建人  
	 */
	private String creator;
	
	/**
	 * 创建日期 
	 */
	private Date created;
	
	/**
	 * 修改日期
	 */
	private Date modified;
	
	private String sellerNick;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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
	public int getLeaf() {
		return leaf;
	}

	public void setLeaf(int leaf) {
		this.leaf = leaf;
	}

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}
	
	
}
