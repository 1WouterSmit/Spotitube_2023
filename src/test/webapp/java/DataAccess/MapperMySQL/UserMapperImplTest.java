package DataAccess.MapperMySQL;

import DataAccess.DB;
import Domain.User;
import Services.Exceptions.AuthenticationException;
import com.mysql.cj.jdbc.result.ResultSetImpl;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings("SqlResolve")
class UserMapperImplTest {

	UserMapperImpl userMapper = new UserMapperImpl();
	DB database;
	Connection connection;
	PreparedStatement preparedStatement;

	@Test
	void getUser() throws SQLException {
		// Arrange
		String findQuery = "SELECT *" +
				" FROM users" +
				" WHERE username = ?";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			ResultSet rs = mock(ResultSetImpl.class);
			when(rs.getLong(1)).thenReturn(99L);
			when(rs.getString(3)).thenReturn("Barack Obama");
			when(rs.getString(4)).thenReturn("Obamaster@911");
			when(rs.getString(5)).thenReturn("1234-1234-1234");
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(findQuery)).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenReturn(rs);
			userMapper.setDatabase(database);
		}

		// Act
		User user = userMapper.getUser("B_omb");

		// Assert
		Assertions.assertEquals("Barack Obama", user.getName());
	}

	@Test
	void getUserErr() throws SQLException {
		// Arrange
		String findQuery = "SELECT *" +
				" FROM users" +
				" WHERE username = ?";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			ResultSet rs = mock(ResultSetImpl.class);
			when(rs.getLong(1)).thenReturn(99L);
			when(rs.getString(3)).thenReturn("Barack Obama");
			when(rs.getString(4)).thenReturn("Obamaster@911");
			when(rs.getString(5)).thenReturn("1234-1234-1234");
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(findQuery)).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenThrow(new SQLException());
			userMapper.setDatabase(database);
		}

		// Act and Assert
		Assertions.assertThrows(SQLException.class, () -> userMapper.getUser("Bomb_"));
	}

	@Test
	void updateToken() throws SQLException {
		// Arrange
		String updateQuery = "UPDATE users" +
				" SET token = ?" +
				" WHERE id = ?";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			ResultSet rs = mock(ResultSetImpl.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(updateQuery)).thenReturn(preparedStatement);
			userMapper.setDatabase(database);
		}

		// Act
		userMapper.updateToken((long) 1, "tokenstring");

		// Assert
		Assertions.assertTrue(true);
	}

	@Test
	void updateTokenErr() throws SQLException {
		// Arrange
		String updateQuery = "UPDATE users" +
				" SET token = ?" +
				" WHERE id = ?";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			ResultSet rs = mock(ResultSetImpl.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(updateQuery)).thenReturn(preparedStatement);
			when(preparedStatement.executeUpdate()).thenThrow(new SQLException());
			userMapper.setDatabase(database);
		}

		// Act and Assert
		Assertions.assertThrows(SQLException.class, () -> userMapper.updateToken((long)1, "strokenting"));
	}

	@Test
	void checkCredentials() throws SQLException, AuthenticationException {
		// Arrange
		String findQuery = "SELECT *" +
				" FROM users" +
				" WHERE username = ?";
		String password = "rightpass";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			ResultSet rs = mock(ResultSetImpl.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(findQuery)).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenReturn(rs);
			when(rs.next()).thenReturn(true);
			when(rs.getString("PASSWORD")).thenReturn(password);
			when(rs.getLong("ID")).thenReturn((long)37);
			userMapper.setDatabase(database);
		}

		// Act
		long user_id = userMapper.checkCredentials("Steve", password);

		// Assert
		Assertions.assertEquals(37, user_id);
	}

	@Test
	void checkWrongPassCredentials() throws SQLException {
		// Arrange
		String findQuery = "SELECT *" +
				" FROM users" +
				" WHERE username = ?";
		String password = "rightpass";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			ResultSet rs = mock(ResultSetImpl.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(findQuery)).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenReturn(rs);
			when(rs.next()).thenReturn(true);
			when(rs.getString("PASSWORD")).thenReturn("wrongpass");
			userMapper.setDatabase(database);
		}

		// Act and Assert
		AuthenticationException exception = Assertions.assertThrows(AuthenticationException.class,
				() -> userMapper.checkCredentials("Steve", password));

		Assertions.assertEquals("AuthenticationException: Invalid name and token combination.",
				exception.getMessage());
	}

	@Test
	void checkWrongNameCredentials() throws SQLException {
		// Arrange
		String findQuery = "SELECT *" +
				" FROM users" +
				" WHERE username = ?";
		String password = "rightpass";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			ResultSet rs = mock(ResultSetImpl.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(findQuery)).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenReturn(rs);
			when(rs.next()).thenReturn(false);
			userMapper.setDatabase(database);
		}

		// Act and Assert
		AuthenticationException exception = Assertions.assertThrows(AuthenticationException.class,
				() -> userMapper.checkCredentials("Schmeve", password));

		Assertions.assertEquals("AuthenticationException: Invalid name and token combination.",
				exception.getMessage());
	}

	@Test
	void checkCredentialsErr() throws SQLException {
		// Arrange
		String findQuery = "SELECT *" +
				" FROM users" +
				" WHERE username = ?";
		String password = "rightpass";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			ResultSet rs = mock(ResultSetImpl.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(findQuery)).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenThrow(new SQLException());
			userMapper.setDatabase(database);
		}

		// Act and Assert
		Assertions.assertThrows(SQLException.class,
				() -> userMapper.checkCredentials("Steve", password));
	}

	@Test
	void validateToken() throws SQLException, AuthenticationException {
		// Arrange
		String findQuery = "SELECT *" +
				" FROM users " +
				" WHERE token = ?";
		String token = "tokenstring";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			ResultSet rs = mock(ResultSetImpl.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(findQuery)).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenReturn(rs);
			when(rs.next()).thenReturn(true);
			when(rs.getLong("ID")).thenReturn((long)37);
			userMapper.setDatabase(database);
		}

		// Act
		long user_id = userMapper.validateToken(token);

		// Assert
		Assertions.assertEquals(37, user_id);
	}

	@Test
	void validateWrongToken() throws SQLException {
		// Arrange
		String findQuery = "SELECT *" +
				" FROM users " +
				" WHERE token = ?";
		String token = "tokenstring";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			ResultSet rs = mock(ResultSetImpl.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(findQuery)).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenReturn(rs);
			when(rs.next()).thenReturn(false);
			userMapper.setDatabase(database);
		}

		// Act and Assert
		AuthenticationException exception = Assertions.assertThrows(AuthenticationException.class,
				() -> userMapper.validateToken(token));

		Assertions.assertEquals("AuthenticationException: Invalid token.", exception.getMessage());
	}

	@Test
	void validateTokenErr() throws SQLException {
		// Arrange
		String findQuery = "SELECT *" +
				" FROM users " +
				" WHERE token = ?";
		String token = "tokenstring";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			ResultSet rs = mock(ResultSetImpl.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(findQuery)).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenThrow(new SQLException());
			userMapper.setDatabase(database);
		}

		// Act and Assert
		Assertions.assertThrows(SQLException.class, () -> userMapper.validateToken(token));
	}
}