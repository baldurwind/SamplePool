package com.chamago.pcrm.customerservice.pojo;

import java.util.Date;

import com.chamago.pcrm.common.pojo.BasePojo;

/**
 * 售后问题类型
 * @author James.wang
 */
public class CSIType extends BasePojo {
	
	private static final long serialVersionUID = 6672152459096121096L;

	//标识
	private String id;
	
	//类型名称
	private String name;
	
	//所属店铺
	private String sellerNick;
	
	//创建时间
	private Date createTime;
	
	//当前类型下的售后个数
	private Integer count;
	
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

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
