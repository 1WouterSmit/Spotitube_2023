package Services;

import DataAccess.MapperMySQL.UserMapperImpl;
import Domain.User;
import Services.Exceptions.AuthenticationException;
import Services.IMappers.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginServiceTest {

	private final String username = "steve";
	private final String password = "ThisIsNotSteve";
	private final String fullname = "steve smith";
	private final String validToken = "123-123-123-123";
	private final String sqlError = "SQL_ERROR";
	private final Long userid = (long)27;

	private LoginService loginService;

	@BeforeAll
	public void setup() {
		UserMapper userMapper = new UserMapperImpl() {
			@Override
			public Long checkCredentials(String user, String pass) throws AuthenticationException, SQLException {
				if (username.equals(user) && password.equals(pass)) {
					return userid;
				} else if(user.equals(sqlError)) throw new SQLException();
				else throw new AuthenticationException();
			}
			@Override
			public User getUser(String user_name) throws SQLException {
				if (user_name.equals(username)) return new User(userid, username, fullname, password, validToken);
				else throw new SQLException();
			}
			@Override
			public void updateToken(Long user_id, String tokenString) throws SQLException {
				if (!user_id.equals(userid)) throw new SQLException();
			}
			@Override
			public Long validateToken(String tokenString) throws SQLException, AuthenticationException {
				if(tokenString.equals(validToken)) return userid;
				else if(tokenString.equals(sqlError)) throw new SQLException();
				else throw new AuthenticationException();
			}
		};
		loginService = new LoginService();
		loginService.setUserMapper(userMapper);
	}

	@Test
	public void attemptGoodLogin() {
		Login login = new Login(username, password);
		Token token = null;
		try {
			token = loginService.attemptLogin(login);
		} catch(SQLException | AuthenticationException e) {
			e.printStackTrace();
		}
		Assertions.assertNotNull(token);
		Assertions.assertEquals(token.user, fullname);
		Assertions.assertNotNull(token.token);
	}

	@Test
	public void attemptBadNameLogin() {
		Login login = new Login("schmeve", password);
		Assertions.assertThrows(AuthenticationException.class, () -> loginService.attemptLogin(login));
	}

	@Test
	public void attemptBadPassLogin() {
		Login login = new Login(username, "Hello world?");
		Assertions.assertThrows(AuthenticationException.class, () -> loginService.attemptLogin(login));
	}

	@Test
	public void testLoginSQLError() {
		Login login = new Login(sqlError, password);
		Assertions.assertThrows(SQLException.class, () -> loginService.attemptLogin(login));
	}

	@Test
	public void testGoodToken() {
		Long user_id = (long) -1;
		try {
			user_id = loginService.checkToken(validToken);
		} catch (SQLException | AuthenticationException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(userid, user_id);
	}

	@Test
	public void testBadToken() {
		String token = "111-222-333-123";
		Assertions.assertThrows(AuthenticationException.class, () -> loginService.checkToken(token));
	}

	@Test
	public void testTokenSQLError() {
		String token = sqlError;
		Assertions.assertThrows(SQLException.class, () -> loginService.checkToken(token));
	}
}