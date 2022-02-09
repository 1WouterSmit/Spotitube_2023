package DataAccess;

import DataAccess.DataMapper.SongMapper;
import Domain.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class DataAccessFacade implements IDataAccess{
    private SongMapper songMapper;

    @Inject
    public void setSongMapper(SongMapper songMapper) {
        this.songMapper = songMapper;
    }

    public DataAccessFacade() {
        songMapper = new SongMapper();
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return null;
    }

    @Override
    public List<Playlist> getAllPlaylistsByOwner(String owner) {
        return null;
    }

    @Override
    public Playlist getPlaylistById(int id) {
        return null;
    }

    @Override
    public boolean addPlaylist(Playlist playlist) {
        return false;
    }

    @Override
    public boolean deletePlaylist(int id) {
        return false;
    }

    @Override
    public boolean editPlaylist(int id, Playlist playlist) {
        return false;
    }

    @Override
    public List<Track> getAllTracksInPlaylist(int id) {
        return null;
    }

    @Override
    public List<Track> getAllTracksNotInPlaylist(int id) {
        return null;
    }

    @Override
    public boolean deleteTrackFromPlaylist(int playlistId, int trackId) {
        return false;
    }

    @Override
    public boolean addTrackToPlaylist(int playlistId, int trackId) {
        return false;
    }

    public Song getSong(int id) {
        return songMapper.find(id);
    }
}
