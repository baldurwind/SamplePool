package sy.model;

import java.io.Serializable;

public class SessionInfo implements Serializable {

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
