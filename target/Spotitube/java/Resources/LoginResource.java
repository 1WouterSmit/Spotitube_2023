package Resources;

import Exceptions.AuthenticationException;
import Services.Login;
import Services.LoginService;
import Services.Token;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("login")
public class LoginResource {
    LoginService loginService;

    @Inject
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response attemptLogin(Login login) {
        try {
            Token token = loginService.attemptLogin(login);
            return Response.ok(Response.Status.OK)
                    .entity(token)
                    .build();
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
