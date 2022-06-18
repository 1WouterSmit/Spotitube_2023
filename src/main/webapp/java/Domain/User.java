package Domain;

public class User implements DomainObject {
	Long id;

	public Long getId() {
		return id;
	}

	public void nullPassword() {
		this.password = null;
	}

	public String getUsername() {
		return username;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getToken() {
		return token;
	}

	String username, name, password, token;

	public User(Long  id, String username, String name, String password, String token) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.password = password;
		this.token = token;
	}
}
