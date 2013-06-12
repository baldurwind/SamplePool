/**
 * 
 */
package com.chamago.pcrm.worktable.knowledgebase.pojo;

import java.util.Date;

import com.chamago.pcrm.common.pojo.BasePojo;
import com.chamago.pcrm.worktable.performance.pojo.Performance;

/**
 * 知识库
 * @author gavin.peng
 * 
 */
public class Knowledge extends BasePojo implements Comparable<Knowledge>{

	private static final long serialVersionUID = 1625656389034559119L;
	/**
	 * 唯一标识 
	 */
	private String id;
	 
	/**
	 * 主题ID
	 */
	private String subjectId;
	
	private String subjectName;
	
		/**
	 * 关联产品Id
	 */
	private Long itemId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 关联文件id
	 */
	private String fileId;
	
	/**
	 * 创建人
	 */
	private String creator;
	/**
	 * 创建时间 
	 */
	private Date  created;
	/**
	 * 修改时间
	 */
	private Date modified;
	
	private String sellerNick;
	/**
	 * 使用次数
	 */
	private int sendNums;
	
	public int getSendNums() {
		return sendNums;
	}
	public void setSendNums(int sendNums) {
		this.sendNums = sendNums;
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
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
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
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getSellerNick() {
		return sellerNick;
	}
	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}
	
	@Override
	public int compareTo(Knowledge o) {
		if(o == null){
			return -1;
		}
		return this.getSendNums()>o.getSendNums()?-1:1;
	}
	
}
