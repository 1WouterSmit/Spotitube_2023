package Presentation.Resources;

import Services.Exceptions.AuthenticationException;
import Services.Login;
import Services.LoginService;
import Services.Token;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;

import javax.ws.rs.core.Response;
import java.sql.SQLException;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginResourceTest {

	Login goodLogin, badNameLogin, badPassLogin, dbFailLogin;
	LoginResource loginRes;

	@BeforeAll
	public void setup() throws AuthenticationException, SQLException {
		/*LoginService loginService = new LoginService() {
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
		};*/

		String username = "steve";
		String password = "ThisIsNotSteve";
		String validToken = "123-123-123-123";
		goodLogin = new Login(username, password);
		badNameLogin = new Login("Schmeve", password);
		badPassLogin = new Login(username, "TrustMeImDefinetelySteve");
		dbFailLogin = new Login("DbFail", "DbFail");

		LoginService loginService = mock(LoginService.class);
		Token token = new Token(validToken, username);
		Mockito.when(loginService.attemptLogin(goodLogin))
				.thenReturn(token);
		Mockito.when(loginService.attemptLogin(dbFailLogin))
				.thenThrow(SQLException.class);
		Mockito.when(loginService.attemptLogin(badPassLogin))
				.thenThrow(AuthenticationException.class);
		Mockito.when(loginService.attemptLogin(badNameLogin))
				.thenThrow(AuthenticationException.class);

		loginRes = new LoginResource();
		loginRes.setLoginService(loginService);
	}

	@Test
	public void attemptGoodLogin() {
		Response r = loginRes.attemptLogin(goodLogin);
		Assertions.assertEquals(Response.Status.OK, r.getStatusInfo());

		Object entity = r.getEntity();
		Assertions.assertInstanceOf(Token.class, entity);

		Token token = (Token) entity;
		Assertions.assertEquals("steve", token.getUser());
		Assertions.assertEquals("123-123-123-123", token.getToken());
	}

	@Test
	public void attemptBadNameLogin() {
		Response r = loginRes.attemptLogin(badNameLogin);
		Assertions.assertEquals(Response.Status.UNAUTHORIZED, r.getStatusInfo());
	}

	@Test
	public void attemptBadPassLogin() {
		Response r = loginRes.attemptLogin(badPassLogin);
		Assertions.assertEquals(Response.Status.UNAUTHORIZED, r.getStatusInfo());
	}

	@Test
	public void attemptLoginWithInternalError() {
		Response r = loginRes.attemptLogin(dbFailLogin);
		Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, r.getStatusInfo());

	}
}