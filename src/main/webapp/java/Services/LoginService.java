package Services;

import DataAccess.Mapper.UserMapper;
import Domain.User;
import Exceptions.AuthenticationException;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.UUID;
// 7a670e2f-b074-4082-aca9-86b9e41d498f
public class LoginService {
    private UserMapper userMapper;
    @Inject
    public void setUserMapper(UserMapper UM) {
        userMapper = UM;
    }

    public Token attemptLogin(Login login) throws AuthenticationException, SQLException {
        Long userID = userMapper.checkCredentials(login.getUser(), login.getPassword());
        User user = userMapper.find(login.getUser());

        String tokenUUID = UUID.randomUUID().toString();
        Token token = new Token(tokenUUID, user.getName());
        userMapper.insertToken(userID, tokenUUID);

        return token;
    }

    public Long checkToken(String token) throws SQLException, AuthenticationException {
        return userMapper.validateToken(token);
    }
}
