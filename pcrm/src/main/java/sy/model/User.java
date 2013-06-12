package sy.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import sy.util.JsonDateSerializer;

public class User implements Serializable {

	private String id;
	private String name;
	private String password;
	 private String sellernick;   
    public String getSellernick() {
		return sellernick;
	}

	public void setSellernick(String sellernick) {
		this.sellernick = sellernick;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

private String access;
	private Date createdatetime;
	private Date modifydatetime;
	private Date createdatetimeStart;
	private Date modifydatetimeStart;
	private Date createdatetimeEnd;

	private Date modifydatetimeEnd;
	private String ip;
	private String oldPassword;
	private String roleNames;
	private String resourceNames;
	private String roleIds;

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getResourceNames() {
		return resourceNames;
	}

	public void setResourceNames(String resourceNames) {
		this.resourceNames = resourceNames;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getCreatedatetimeStart() {
		return createdatetimeStart;
	}

	public void setCreatedatetimeStart(Date createdatetimeStart) {
		this.createdatetimeStart = createdatetimeStart;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getModifydatetimeStart() {
		return modifydatetimeStart;
	}

	public void setModifydatetimeStart(Date modifydatetimeStart) {
		this.modifydatetimeStart = modifydatetimeStart;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getCreatedatetimeEnd() {
		return createdatetimeEnd;
	}

	public void setCreatedatetimeEnd(Date createdatetimeEnd) {
		this.createdatetimeEnd = createdatetimeEnd;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getModifydatetimeEnd() {
		return modifydatetimeEnd;
	}

	public void setModifydatetimeEnd(Date modifydatetimeEnd) {
		this.modifydatetimeEnd = modifydatetimeEnd;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getCreatedatetime() {
		return createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getModifydatetime() {
		return modifydatetime;
	}

	public void setModifydatetime(Date modifydatetime) {
		this.modifydatetime = modifydatetime;
	}

}
