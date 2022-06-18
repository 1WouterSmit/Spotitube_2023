package DataAccess.Mapper;

import DataAccess.DB;
import Domain.User;
import Exceptions.AuthenticationException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
	public static final String COLUMNS = "id, username, name, password, token";

	protected String findStatement() {
		return "SELECT " + COLUMNS +
				" FROM users" +
				" WHERE username = ?";
	}

	protected String findStatement(String column1, String column2) {
		return "SELECT " + COLUMNS +
				" FROM users" +
				" WHERE "+ column1 +" = ?" +
				" AND "+ column2 +" = ?";
	}

	protected String findByTokenStatement() {
		return "SELECT " + COLUMNS +
				" FROM users " +
				" WHERE token = ?";
	}

	protected String updateStatement(String column) {
		return "UPDATE users" +
				" SET "+ column +" = ?" +
				" WHERE id = ?";
	}

	public User find(String username) throws SQLException {
		User result;
		PreparedStatement findStatement;

		try {
			findStatement = DB.connection().prepareStatement(findStatement());
			findStatement.setString(1, username);
			ResultSet rs = findStatement.executeQuery();
			rs.next();
			result = doLoad(username, rs);
			return result;
		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

	protected void update(String value, Long userId) throws SQLException {
		PreparedStatement updateTokenStatement = DB.connection().prepareStatement(updateStatement("token"));
		updateTokenStatement.setString(1, value);
		updateTokenStatement.setLong(2, userId);
		updateTokenStatement.executeUpdate();
	}

	protected User doLoad(String username, ResultSet rs) throws SQLException {
		try {
			Long id = rs.getLong(1);
			String name = rs.getString(3);
			String password = rs.getString(4);
			String token = rs.getString(5);
			return new User(id, username, name, password, token);
		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

	public void insertToken(Long userId, String token) throws SQLException {
		update(token, userId);
	}

	public Long checkCredentials(String username, String password) throws SQLException, AuthenticationException {
		PreparedStatement findByUsername = DB.connection().prepareStatement(findStatement());
		findByUsername.setString(1, username);
		ResultSet rs = findByUsername.executeQuery();

		if(rs.next()) {
			if(rs.getString("PASSWORD").equals(password)) return rs.getLong("ID");
			else throw new AuthenticationException("AuthenticationException: Invalid name and token combination.");
		}
		else throw new AuthenticationException("AuthenticationException: Invalid name and token combination.");
	}

	public Long validateToken(String tokenString) throws SQLException, AuthenticationException {
		PreparedStatement findByToken = DB.connection().prepareStatement(findByTokenStatement());
		findByToken.setString(1, tokenString);
		ResultSet rs = findByToken.executeQuery();

		if(rs.next()) return rs.getLong("ID");
		else throw new AuthenticationException("AuthenticationException: Invalid token.");
	}
}
