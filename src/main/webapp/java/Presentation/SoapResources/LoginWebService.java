package Presentation.SoapResources;

import DataAccess.DB;
import DataAccess.MapperMySQL.UserMapperImpl;
import Services.Exceptions.AuthenticationException;
import Services.Login;
import Services.LoginService;
import Services.Token;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.sql.SQLException;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class LoginWebService {

    private LoginService loginService;

    public LoginWebService() {
        this.loginService = new LoginService();
        UserMapperImpl userMapper = new UserMapperImpl();
        userMapper.setDatabase(DB.getInstance());
        loginService.setUserMapper(userMapper);
    }
    @WebMethod
    public Token attemptLogin( @WebParam( name = "username" ) String username,
                               @WebParam( name = "password" ) String password )
                            throws AuthenticationException, SQLException {

        if( username == null || username.isEmpty() || password == null || password.isEmpty() ) {
            throw new AuthenticationException("Missing username and/or password");
        }

        Token token = loginService.attemptLogin(new Login(username, password));
        return token;
    }
}
