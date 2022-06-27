package Resources;

import Exceptions.AuthenticationException;
import Resources.LoginResource;
import Services.Login;
import Services.LoginService;
import Services.Token;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;

import javax.ws.rs.core.Response;
import java.sql.SQLException;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginResourceTest {

	String username = "steve";
	String password = "ThisIsNotSteve";
	String validToken = "123-123-123-123";
	//LoginService loginService;
	LoginResource loginRes;

	@BeforeAll
	public void setup() {
		LoginService loginService = new LoginService() {
			@Override
			public Token attemptLogin(Login login) throws AuthenticationException, SQLException {
				if(username.equals(login.getUser()) && password.equals(login.getPassword())) {
					return new Token(validToken, username);
				} else if(login.getUser().equals("DBFAIL")) {
					throw new SQLException();
				} else {
					throw new AuthenticationException();
				}
			}
		};
		loginRes = new LoginResource();
		loginRes.setLoginService(loginService);
	}

	@Test
	public void attemptGoodLogin() {
		Login login = new Login(username, password);
		Response r = loginRes.attemptLogin(login);
		Assertions.assertEquals(Response.Status.OK, r.getStatusInfo());

		Object entity = r.getEntity();
		Assertions.assertInstanceOf(Token.class, entity);

		Token token = (Token) entity;
		Assertions.assertEquals("steve", token.getUser());
		Assertions.assertEquals("123-123-123-123", token.getToken());
	}

	@Test
	public void attemptBadNameLogin() {
		Login login = new Login("Schmeve", password);
		Response r = loginRes.attemptLogin(login);
		Assertions.assertEquals(Response.Status.UNAUTHORIZED, r.getStatusInfo());
	}

	@Test
	public void attemptBadPassLogin() {
		Login login = new Login(username, "ThisIsDefinitelySteve");
		Response r = loginRes.attemptLogin(login);
		Assertions.assertEquals(Response.Status.UNAUTHORIZED, r.getStatusInfo());
	}

	@Test
	public void attemptLoginWithInternalError() {
		Login login = new Login("DBFAIL", "");
		Response r = loginRes.attemptLogin(login);
		Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, r.getStatusInfo());

	}
}