package com.chamago.pcrm.user.pojo;

import java.sql.Date;

import com.chamago.pcrm.common.pojo.BasePojo;

/**
 * 用户
 * @author James.wang
 */
public class User extends BasePojo {
	
	private static final long serialVersionUID = -8471779857145857469L;

	//标识
	private String id;
	
	//用户名
	private String name;
	
	//所属店铺
	private String sellerNick;
	
	//员工号
	private String employeeNum;
	
	//手机号
	private String mobile;
	
	//邮箱
	private String email;
	
	//密码
	private String password;
	
	//员工状态
	private String status;
	
	//创建时间
	private Date createTime;
	
	//修改时间
	private Date modifyTime;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getEmployeeNum() {
		return employeeNum;
	}

	public void setEmployeeNum(String employeeNum) {
		this.employeeNum = employeeNum;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
