package Services;

import Services.IMappers.UserMapper;
import Domain.User;
import Exceptions.AuthenticationException;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.UUID;

public class LoginService {
    private UserMapper userMapper;

    @Inject
    public void setUserMapper(UserMapper UM) {
        userMapper = UM;
    }

    public Token attemptLogin(Login login) throws AuthenticationException, SQLException {
        Long userID = userMapper.checkCredentials(login.getUser(), login.getPassword());
        User user = userMapper.getUser(login.getUser());

        String tokenUUID = UUID.randomUUID().toString();
        Token token = new Token(tokenUUID, user.getName());
        userMapper.updateToken(userID, tokenUUID);

        return token;
    }

    public Long checkToken(String token) throws SQLException, AuthenticationException {
        return userMapper.validateToken(token);
    }
}
