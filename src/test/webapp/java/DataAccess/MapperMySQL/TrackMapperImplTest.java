package DataAccess.MapperMySQL;

import DataAccess.DB;
import Domain.Track;
import com.mysql.cj.jdbc.result.ResultSetImpl;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("SqlResolve")
class TrackMapperImplTest {

	TrackMapperImpl trackMapper = new TrackMapperImpl();
	DB database;
	Connection connection;
	PreparedStatement preparedStatement;
	Track track1 = new Track(1, "song 1", "performer 1", 170, "album 1", null,
				null, null, false);

	@Test
	void getAllTracksInPlaylist() throws SQLException {
		// Arrange
		String findQuery = "SELECT * " +
				"FROM playlist_tracks " +
				"LEFT JOIN tracks " +
				"ON playlist_tracks.track_id = tracks.id " +
				"WHERE playlist_tracks.playlist_id = ?";
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
			when(rs.getInt("duration")).thenReturn(170).thenReturn(180);

			trackMapper.setDatabase(database);
		}

		// Act
		List<Track> tracks = trackMapper.getAllTracksInPlaylist((long)1);

		// Assert
		assertEquals(2, tracks.size());
		assertEquals(2, tracks.get(1).getId());
		assertEquals(180, tracks.get(1).getDuration());
	}

	@Test
	void getAllTracksInPlaylistErr() throws SQLException {
		// Arrange
		String findQuery = "SELECT * " +
				"FROM playlist_tracks " +
				"LEFT JOIN tracks " +
				"ON playlist_tracks.track_id = tracks.id " +
				"WHERE playlist_tracks.playlist_id = ?";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			ResultSet rs = mock(ResultSetImpl.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(findQuery)).thenThrow(new SQLException());
			when(preparedStatement.executeQuery()).thenReturn(rs);

			trackMapper.setDatabase(database);
		}

		// Act and Assert
		assertThrows(SQLException.class, () -> trackMapper.getAllTracksInPlaylist((long)1)) ;
	}

	@Test
	void getAllTracksNotInPlaylist() throws SQLException {
		// Arrange
		String findQuery = "SELECT * " +
				"FROM tracks " +
				"WHERE id NOT IN " +
				"(SELECT track_id " +
				"FROM spot.playlist_tracks " +
				"WHERE playlist_id = ?)";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			ResultSet rs = mock(ResultSetImpl.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(findQuery)).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenReturn(rs);
			when(rs.next()).thenReturn(true).thenReturn(false);

			when(rs.getLong("id")).thenReturn((long)3);
			when(rs.getInt("duration")).thenReturn(190);

			trackMapper.setDatabase(database);
		}

		// Act
		List<Track> tracks = trackMapper.getAllTracksNotInPlaylist((long)1);

		// Assert
		assertEquals(1, tracks.size());
		assertEquals(3, tracks.get(0).getId());
		assertEquals(190, tracks.get(0).getDuration());
	}

	@Test
	void getAllTracksNotInPlaylistErr() throws SQLException {
		// Arrange
		String findQuery = "SELECT * " +
				"FROM tracks " +
				"WHERE id NOT IN " +
				"(SELECT track_id " +
				"FROM spot.playlist_tracks " +
				"WHERE playlist_id = ?)";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(findQuery)).thenThrow(new SQLException());

			trackMapper.setDatabase(database);
		}

		// Act and Assert
		assertThrows(SQLException.class, () -> trackMapper.getAllTracksNotInPlaylist((long)1)) ;
	}

	@Test
	void addTrackToPlaylist() throws SQLException {
		// Arrange
		String insertQuery = "INSERT INTO playlist_tracks (playlist_id, track_id)" +
				" VALUES (?, ?)";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(insertQuery)).thenReturn(preparedStatement);
			trackMapper.setDatabase(database);
		}

		// Act
		trackMapper.addTrackToPlaylist((long)1, track1);
	}

	@Test
	void addTrackToPlaylistErr() throws SQLException {
		// Arrange
		String insertQuery = "INSERT INTO playlist_tracks (playlist_id, track_id)" +
				" VALUES (?, ?)";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(insertQuery)).thenReturn(preparedStatement);
			when(preparedStatement.execute()).thenThrow(new SQLException());
			trackMapper.setDatabase(database);
		}

		// Act and Assert
		assertThrows(SQLException.class, () -> trackMapper.addTrackToPlaylist((long)66, track1));
	}

	@Test
	void removeTrackFromPlaylist() throws SQLException {
		// Arrange
		String removeQuery = "DELETE FROM playlist_tracks " +
				"WHERE playlist_id = ? " +
				"AND track_id = ?";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(removeQuery)).thenReturn(preparedStatement);
			trackMapper.setDatabase(database);
		}

		// Act
		trackMapper.removeTrackFromPlaylist((long)1, (long)1);
	}

	@Test
	void removeTrackFromPlaylistErr() throws SQLException {
		// Arrange
		String removeQuery = "DELETE FROM playlist_tracks " +
				"WHERE playlist_id = ? " +
				"AND track_id = ?";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(removeQuery)).thenReturn(preparedStatement);
			when(preparedStatement.execute()).thenThrow(new SQLException());
			trackMapper.setDatabase(database);
		}

		// Act and Assert
		assertThrows(SQLException.class, () -> trackMapper.removeTrackFromPlaylist((long)66, (long)1));
	}
}