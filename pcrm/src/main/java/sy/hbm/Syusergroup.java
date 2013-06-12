package sy.hbm;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Syusergroup entity. @author MyEclipse Persistence Tools
 */

public class Syusergroup implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private Date createdatetime;
	private Date modifydatetime;
    private String sellernick;





	// Constructors

	public String getSellernick() {
		return sellernick;
	}

	public void setSellernick(String sellernick) {
		this.sellernick = sellernick;
	}

	/** default constructor */
	public Syusergroup() {
	}

	/** minimal constructor */
	public Syusergroup(String id, String name, String password) {
		this.id = id;
		this.name = name;
	}

	/** full constructor */
	public Syusergroup(String id, String name, String password, Date createdatetime, Date modifydatetime, Set syuserSyroles) {
		this.id = id;
		this.name = name;
		this.createdatetime = createdatetime;
		this.modifydatetime = modifydatetime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public Date getCreatedatetime() {
		return this.createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	public Date getModifydatetime() {
		return this.modifydatetime;
	}

	public void setModifydatetime(Date modifydatetime) {
		this.modifydatetime = modifydatetime;
	}

	

}