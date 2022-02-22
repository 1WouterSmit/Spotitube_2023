package Service;

// TODO INSECURE
public class Login {
    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String user;
    String password;

    public Login() {

    }
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
