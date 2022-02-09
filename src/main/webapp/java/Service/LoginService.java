package Service;


import java.util.UUID;

public class LoginService {
    public Token attemptLogin(String username, String password) {
        String tokenUUID = UUID.randomUUID().toString();
        Token token = new Token(tokenUUID, "Meron"+" "+"Brouwer");
        return token;
    }
}
