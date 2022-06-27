package Services.IMappers;

import Domain.Track;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TrackMapper {
	ArrayList<Track> getAllTracksInPlaylist(Long playlistId) throws SQLException;
	ArrayList<Track> getAllTracksNotInPlaylist(Long playlistId) throws SQLException;
	void addTrackToPlaylist(Long playlistId, Track track) throws SQLException;
	void removeTrackFromPlaylist(Long playlistId, Long trackId) throws SQLException;
}
