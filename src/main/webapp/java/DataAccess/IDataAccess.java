package DataAccess;

import Domain.*;

import java.util.List;

public interface IDataAccess {
    List<Playlist> getAllPlaylists();
    List<Playlist> getAllPlaylistsByOwner(String owner);
    Playlist getPlaylistById(int id);

    boolean addPlaylist(Playlist playlist);
    boolean deletePlaylist(int id);
    boolean editPlaylist(int id, Playlist playlist);

    List<Track> getAllTracksInPlaylist(int id);
    List<Track> getAllTracksNotInPlaylist(int id);

    boolean deleteTrackFromPlaylist(int playlistId, int trackId);
    boolean addTrackToPlaylist(int playlistId, int trackId);
}
