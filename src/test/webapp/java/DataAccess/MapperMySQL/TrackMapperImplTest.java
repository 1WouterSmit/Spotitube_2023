package DataAccess.MapperMySQL;

import DataAccess.DB;
import Domain.Track;
import com.mysql.cj.jdbc.result.ResultSetImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("SqlResolve")
class TrackMapperImplTest {

	TrackMapperImpl trackMapper = new TrackMapperImpl();
	DB database;
	Connection connection;
	PreparedStatement preparedStatement;

	@Test
	void getAllTracksInPlaylist() throws SQLException {
		// Arrange
		String findQuery = "SELECT * " +
				"FROM playlist_tracks " +
				"LEFT JOIN tracks " +
				"ON playlist_tracks.track_id = tracks.id " +
				"HAVING playlist_tracks.playlist_id = ?";
		{
			database = mock(DB.class);
			connection = mock(Connection.class);
			preparedStatement = mock(PreparedStatement.class);
			ResultSet rs = mock(ResultSetImpl.class);
			when(database.connection()).thenReturn(connection);
			when(connection.prepareStatement(findQuery)).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenReturn(rs);
			when(rs.next()).thenReturn(true);
			trackMapper.setDatabase(database);
		}

		// Act
		List<Track> tracks = trackMapper.getAllTracksInPlaylist((long)1);

		// Assert
		Assertions.assertEquals(2, tracks.size());
	}

	@Test
	void getAllTracksNotInPlaylist() {
	}

	@Test
	void addTrackToPlaylist() {
	}

	@Test
	void removeTrackFromPlaylist() {
	}
}