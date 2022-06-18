package Services;

public class Login {
    String user;
    String password;

    public Login(){}

    public Login(String username, String password) {
        this.user = username;
        this.password = password;
    }

    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
