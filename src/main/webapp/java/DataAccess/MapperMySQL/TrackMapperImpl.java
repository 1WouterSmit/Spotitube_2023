package DataAccess.MapperMySQL;

import DataAccess.DB;
import Domain.Track;
import Services.IMappers.TrackMapper;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class TrackMapperImpl implements TrackMapper {
	private HashMap<Long, Track> map = new HashMap<>();
	private DB database;

	@Inject
	public void setDatabase(DB database) {
		this.database = database;
	}

	private PreparedStatement statement(String statement) throws SQLException {
		return database.connection().prepareStatement(statement);
	}

	private Track load(ResultSet rs) throws SQLException {
		Long id = rs.getLong("id");
		if (map.containsKey(id)) return map.get(id);
		Track newTrack = doLoad(rs, id);
		map.put(newTrack.getId(), newTrack);
		return newTrack;
	}

	private Track doLoad(ResultSet rs, Long id) throws SQLException {
		Track track = new Track();
		track.setId(id);
		track.setTitle(rs.getString("title"));
		track.setPerformer(rs.getString("performer"));
		track.setDuration(rs.getInt("duration"));
		track.setAlbum(rs.getString("album"));

		Long playcount = rs.getLong("playcount");
		if(rs.wasNull()) {
			playcount = null;
		}
		track.setPlaycount(playcount);

		track.setPublicationDate(rs.getString("publication_date"));
		track.setDescription(rs.getString("description"));
		return track;
	}

	// Query Statements

	private String findAllInPLStatement() {
		return "SELECT * " +
				"FROM playlist_tracks " +
				"LEFT JOIN tracks " +
				"ON playlist_tracks.track_id = tracks.id " +
				"WHERE playlist_tracks.playlist_id = ?";
	}

	private String findAllNotInPLStatement() {
		return "SELECT * " +
				"FROM tracks " +
				"WHERE id NOT IN " +
				"(SELECT track_id " +
				"FROM spot.playlist_tracks " +
				"WHERE playlist_id = ?)";
	}

	private String addToPlaylistStatement() {
		return "INSERT INTO playlist_tracks (playlist_id, track_id)" +
				" VALUES (?, ?)";
	}

	private String removeTrackFromPlaylistStatement() {
		return "DELETE FROM playlist_tracks " +
				"WHERE playlist_id = ? " +
				"AND track_id = ?";
	}

	// Functionality

	private ArrayList<Track> getTrackListFromSet(ResultSet rs) throws SQLException {
		ArrayList<Track> tracks = new ArrayList<>();
		while(rs.next()) {
			Track track = load(rs);
			tracks.add(track);
		}
		return tracks;
	}

	public ArrayList<Track> getAllTracksInPlaylist(Long playlistId) throws SQLException {
		PreparedStatement getAllInPlaylist = statement(findAllInPLStatement());
		getAllInPlaylist.setLong(1, playlistId);

		ResultSet rs = getAllInPlaylist.executeQuery();
		return getTrackListFromSet(rs);
	}

	public ArrayList<Track> getAllTracksNotInPlaylist(Long playlistId) throws SQLException {
		PreparedStatement getAllInPlaylist = statement(findAllNotInPLStatement());
		getAllInPlaylist.setLong(1, playlistId);
		ResultSet rs = getAllInPlaylist.executeQuery();
		return getTrackListFromSet(rs);
	}

	public void addTrackToPlaylist(Long playlistId, Track track) throws SQLException {
		PreparedStatement statement = statement(addToPlaylistStatement());
		statement.setLong(1, playlistId);
		statement.setLong(2, track.getId());
		statement.execute();
	}

	public void removeTrackFromPlaylist(Long playlistId, Long trackId) throws SQLException {
		PreparedStatement statement = statement(removeTrackFromPlaylistStatement());
		statement.setLong(1, playlistId);
		statement.setLong(2, trackId);
		statement.execute();
	}
}
