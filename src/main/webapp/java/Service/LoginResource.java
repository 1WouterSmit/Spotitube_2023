package Service;

import DataAccess.DataAccessFacade;
import Domain.Song;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("login")
public class LoginResource {
    LoginService loginService;

    @Inject
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @POST
    //@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Token attemptLogin(/*Login login*/) {
        //Token token = new Token("1234-1234-1234-1234", "Meron Brouwer");
        Login login = new Login("Meron", "password");
        return loginService.attemptLogin(login.getUser(), login.getPassword());
    }

    @GET
    public String tryConnection() {
        String[] args = null;
        //SQLDatabaseConnection.main(args);
        DataAccessFacade DAF = new DataAccessFacade();
        Song song = DAF.getSong(1);
        //return "I'm baaaack!";
        return song.getTitle()+ " - " +song.getPerformer();
    }
}
