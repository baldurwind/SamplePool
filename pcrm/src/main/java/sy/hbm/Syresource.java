package sy.hbm;

import java.util.HashSet;
import java.util.Set;

/**
 * Syresource entity. @author MyEclipse Persistence Tools
 */

public class Syresource implements java.io.Serializable {

	// Fields
	private String id;
	private Syresource syresource;
	private String name;
	private String url;
	private String comments;
	private String onoff;
	private Set syroleSyreources = new HashSet(0);
	private Set syresources = new HashSet(0);

	// Constructors

	/** default constructor */
	public Syresource() {
	}

	/** minimal constructor */
	public Syresource(String id, String name, String url, String onoff) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.onoff = onoff;
	}

	/** full constructor */
	public Syresource(String id, Syresource syresource, String name, String url, String comments, String onoff, Set syroleSyreources, Set syresources) {
		this.id = id;
		this.syresource = syresource;
		this.name = name;
		this.url = url;
		this.comments = comments;
		this.onoff = onoff;
		this.syroleSyreources = syroleSyreources;
		this.syresources = syresources;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Syresource getSyresource() {
		return this.syresource;
	}

	public void setSyresource(Syresource syresource) {
		this.syresource = syresource;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getOnoff() {
		return this.onoff;
	}

	public void setOnoff(String onoff) {
		this.onoff = onoff;
	}

	public Set getSyroleSyreources() {
		return this.syroleSyreources;
	}

	public void setSyroleSyreources(Set syroleSyreources) {
		this.syroleSyreources = syroleSyreources;
	}

	public Set getSyresources() {
		return this.syresources;
	}

	public void setSyresources(Set syresources) {
		this.syresources = syresources;
	}

}