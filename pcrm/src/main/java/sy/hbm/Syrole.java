package sy.hbm;

import java.util.HashSet;
import java.util.Set;

/**
 * Syrole entity. @author MyEclipse Persistence Tools
 */

public class Syrole implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String comments;
	private Set<SyuserSyrole> syuserSyroles = new HashSet(0);
	private Set<SyroleSyreource> syroleSyreources = new HashSet(0);
    private String sellernick;
	// Constructors

	public String getSellernick() {
		return sellernick;
	}

	public void setSellernick(String sellernick) {
		this.sellernick = sellernick;
	}

	/** default constructor */
	public Syrole() {
	}

	/** minimal constructor */
	public Syrole(String id, String name) {
		this.id = id;
		this.name = name;
	}

	/** full constructor */
	public Syrole(String id, String name, String comments, Set syuserSyroles, Set syroleSyreources) {
		this.id = id;
		this.name = name;
		this.comments = comments;
		this.syuserSyroles = syuserSyroles;
		this.syroleSyreources = syroleSyreources;
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

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Set<SyuserSyrole> getSyuserSyroles() {
		return this.syuserSyroles;
	}

	public void setSyuserSyroles(Set<SyuserSyrole> syuserSyroles) {
		this.syuserSyroles = syuserSyroles;
	}

	public Set<SyroleSyreource> getSyroleSyreources() {
		return this.syroleSyreources;
	}

	public void setSyroleSyreources(Set<SyroleSyreource> syroleSyreources) {
		this.syroleSyreources = syroleSyreources;
	}

}