package sy.hbm;

/**
 * SyroleSyreource entity. @author MyEclipse Persistence Tools
 */

public class SyroleSyreource implements java.io.Serializable {

	// Fields

	private String id;
	private Syrole syrole;
	private Syresource syresource;

	// Constructors
	/** default constructor */
	public SyroleSyreource() {
	}

	/** full constructor */
	public SyroleSyreource(String id, Syrole syrole, Syresource syresource) {
		this.id = id;
		this.syrole = syrole;
		this.syresource = syresource;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Syrole getSyrole() {
		return this.syrole;
	}

	public void setSyrole(Syrole syrole) {
		this.syrole = syrole;
	}

	public Syresource getSyresource() {
		return this.syresource;
	}

	public void setSyresource(Syresource syresource) {
		this.syresource = syresource;
	}

}