package Service;

// TODO INSECURE
public class Login {
    String user;
    String password;

    public Login(String username, String password) {
        this.user = username;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
