package com.chamago.pcrm.customerservice.pojo;

import com.chamago.pcrm.common.pojo.BasePojo;

/**
 * 客服信息
 * @author James.wang
 */
public class CustomerService extends BasePojo {
	
	private static final long serialVersionUID = -6756607717792537872L;

	//标识
	private Integer id;
	
	//客服名称
	private String nick;
	
	//客服类型 　0:售前　1:售后
	private Integer type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
