package sy.model;

public class Role {

	private String id;
	private String name;
	private String comments;
    private String sellernick;
	public String getSellernick() {
		return sellernick;
	}

	public void setSellernick(String sellernick) {
		this.sellernick = sellernick;
	}

	private String resourceIds;

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
