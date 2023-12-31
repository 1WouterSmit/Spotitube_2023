package Services.IMappers;

import Domain.User;
import Services.Exceptions.AuthenticationException;
import java.sql.SQLException;

public interface UserMapper {
	User getUser(String username) throws SQLException;
	void updateToken(Long userId, String token) throws SQLException;
	Long checkCredentials(String username, String password) throws SQLException, AuthenticationException;
	Long validateToken(String tokenString) throws SQLException, AuthenticationException;
}
