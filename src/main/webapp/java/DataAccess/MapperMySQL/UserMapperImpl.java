package DataAccess.MapperMySQL;

import DataAccess.DB;
import Domain.User;
import Services.Exceptions.AuthenticationException;
import Services.IMappers.UserMapper;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapperImpl implements UserMapper {
	private DB database;

	@Inject
	public void setDatabase(DB database) {
		this.database = database;
	}

	private String findStatement() {
		return "SELECT *" +
				" FROM users" +
				" WHERE username = ?";
	}

	private String findByTokenStatement() {
		return "SELECT *" +
				" FROM users " +
				" WHERE token = ?";
	}

	private String updateStatement() {
		return "UPDATE users" +
				" SET token = ?" +
				" WHERE id = ?";
	}

	private User load(String username, ResultSet rs) throws SQLException {
		Long id = rs.getLong(1);
		String name = rs.getString(3);
		String password = rs.getString(4);
		String token = rs.getString(5);
		return new User(id, username, name, password, token);
	}

	private PreparedStatement statement(String statement) throws SQLException {
		return database.connection().prepareStatement(statement);
	}

	@Override
	public User getUser(String username) throws SQLException {
		PreparedStatement findStatement = statement(findStatement());
		findStatement.setString(1, username);
		ResultSet rs = findStatement.executeQuery();
		rs.next();
		return load(username, rs);
	}

	@Override
	public void updateToken(Long userId, String token) throws SQLException {
		PreparedStatement updateTokenStatement = statement(updateStatement());
		updateTokenStatement.setString(1, token);
		updateTokenStatement.setLong(2, userId);
		updateTokenStatement.executeUpdate();
	}

	@Override
	public Long checkCredentials(String username, String password) throws SQLException, AuthenticationException {
		PreparedStatement findByUsername = statement(findStatement());
		findByUsername.setString(1, username);
		ResultSet rs = findByUsername.executeQuery();

		if(rs.next()) {
			if(rs.getString("PASSWORD").equals(password)) return rs.getLong("ID");
			else throw new AuthenticationException("AuthenticationException: Invalid name and token combination.");
		}
		else throw new AuthenticationException("AuthenticationException: Invalid name and token combination.");
	}

	@Override
	public Long validateToken(String tokenString) throws SQLException, AuthenticationException {
		PreparedStatement findByToken = statement(findByTokenStatement());
		findByToken.setString(1, tokenString);
		ResultSet rs = findByToken.executeQuery();

		if(rs.next()) return rs.getLong("ID");
		else throw new AuthenticationException("AuthenticationException: Invalid token.");
	}
}
