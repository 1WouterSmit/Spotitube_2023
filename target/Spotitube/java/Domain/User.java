package Domain;

public class User {
	Long id;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
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
