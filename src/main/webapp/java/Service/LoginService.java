package Service;


import DataAccess.DataAccessFacade;

import java.util.UUID;

public class LoginService {
    public Token attemptLogin(Login login) {
        String tokenUUID = UUID.randomUUID().toString();
        if(DataAccessFacade.getInstance().checkCredentials(login)) {
            return new Token(tokenUUID, "Meron" + " " + "Brouwer");
        }
        return null;
    }
}
