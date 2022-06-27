package Services.IMappers;

import Domain.Playlist;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PlaylistMapper {
	void deletePlaylist(Long id) throws SQLException;
	ArrayList<Playlist> getAllPlaylists() throws SQLException;
	void insertPlaylist(Playlist playlist) throws SQLException;
	void updatePlaylistName(Long id, String name) throws SQLException;
}
