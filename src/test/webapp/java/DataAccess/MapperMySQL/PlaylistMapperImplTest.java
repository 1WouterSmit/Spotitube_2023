package DataAccess.MapperMySQL;

import DataAccess.DB;
import Domain.Playlist;
import com.mysql.cj.jdbc.result.ResultSetImpl;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("SqlResolve")
class PlaylistMapperImplTest {

	PlaylistMapperImpl playlistMapper = new PlaylistMapperImpl();
	DB database;
	Connection connection;
	PreparedStatement preparedStatement;

	@Test
	void deletePlaylist() throws SQLException {
		// Arrange
		String deleteQuery = "DELETE FROM playlists " +
				"WHERE id = ?";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(deleteQuery)).thenReturn(preparedStatement);
			playlistMapper.setDatabase(database);
		}
	}

	@Test
	void deletePlaylistErr() throws SQLException {
		// Arrange
		String deleteQuery = "DELETE FROM playlists " +
				"WHERE id = ?";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(deleteQuery)).thenReturn(preparedStatement);
			when(preparedStatement.execute()).thenThrow(new SQLException());
			playlistMapper.setDatabase(database);
		}

		// Act and Arrange
		assertThrows(SQLException.class, () -> playlistMapper.deletePlaylist((long)66));
	}

	@Test
	void getAllPlaylists() throws SQLException {
		// Arrange
		String findQuery = "SELECT * " +
				"FROM playlists";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			ResultSet rs = mock(ResultSetImpl.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(findQuery)).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenReturn(rs);
			when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
			when(rs.getLong("id")).thenReturn((long)1).thenReturn((long)2);
			when(rs.getString("name")).thenReturn("playlist1").thenReturn("playlist2");
			when(rs.getLong("owner")).thenReturn((long)1);
			playlistMapper.setDatabase(database);
		}

		// Act
		ArrayList<Playlist> playlists = playlistMapper.getAllPlaylists();

		// Assert
		assertEquals(2, playlists.size());
		assertEquals(1, playlists.get(0).getID());
		assertEquals("playlist2", playlists.get(1).getName());
		assertEquals(1, playlists.get(0).getOwner());
	}

	@Test
	void getAllPlaylistsErr() throws SQLException {
		// Arrange
		String findQuery = "SELECT * " +
				"FROM playlists";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(findQuery)).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenThrow(new SQLException());
			playlistMapper.setDatabase(database);
		}

		// Act and Arrange
		assertThrows(SQLException.class, () -> playlistMapper.getAllPlaylists());
	}

	@Test
	void insertPlaylist() throws SQLException {
		// Arrange
		String columns = "id, name, owner";
		String insertQuery = "INSERT INTO playlists (" + columns + ") " +
				"VALUES (?, ?, ?)";
		String highestIdQuery = "SELECT MAX(ID) as ID " +
				"FROM playlists";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(insertQuery)).thenReturn(preparedStatement);

			PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
			when(connection.prepareStatement(highestIdQuery)).thenReturn(preparedStatement2);
			ResultSet rs2 = mock(ResultSetImpl.class);
			when(preparedStatement2.executeQuery()).thenReturn(rs2);
			when(rs2.getLong("ID")).thenReturn((long)54);

			playlistMapper.setDatabase(database);
		}

		// Act
		playlistMapper.insertPlaylist(new Playlist((long)55, "empty playlist", (long)1));
	}

	@Test
	void insertPlaylistErr() throws SQLException {
		// Arrange
		String columns = "id, name, owner";
		String insertQuery = "INSERT INTO playlists (" + columns + ") " +
				"VALUES (?, ?, ?)";
		String highestIdQuery = "SELECT MAX(ID) as ID " +
				"FROM playlists";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(insertQuery)).thenReturn(preparedStatement);
			when(preparedStatement.execute()).thenThrow(new SQLException());

			PreparedStatement preparedStatement2 = mock(PreparedStatement.class);
			when(connection.prepareStatement(highestIdQuery)).thenReturn(preparedStatement2);
			ResultSet rs2 = mock(ResultSetImpl.class);
			when(preparedStatement2.executeQuery()).thenReturn(rs2);
			when(rs2.getLong("ID")).thenReturn((long)54);

			playlistMapper.setDatabase(database);
		}

		// Act and Assert
		assertThrows(SQLException.class, () -> playlistMapper.insertPlaylist(new Playlist(
				(long)55, "invalid owner playlist", (long)66)));
	}

	@Test
	void updatePlaylistName() throws SQLException {
		// Arrange
		String updateQuery = "UPDATE playlists" +
				" SET name = ?" +
				" WHERE id = ?";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(updateQuery)).thenReturn(preparedStatement);
			playlistMapper.setDatabase(database);
		}
	}

	@Test
	void updatePlaylistNameErr() throws SQLException {
		// Arrange
		String updateQuery = "UPDATE playlists" +
				" SET name = ?" +
				" WHERE id = ?";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(updateQuery)).thenReturn(preparedStatement);
			when(preparedStatement.execute()).thenThrow(new SQLException());
			playlistMapper.setDatabase(database);
		}

		// Act and Arrange
		assertThrows(SQLException.class, () -> playlistMapper.updatePlaylistName((long)66, "list bestaat niet"));
	}
}