package DataAccess;

import DataAccess.DataMapper.SongMapper;
import Domain.*;
import Service.Login;
import Service.Token;

import javax.inject.Inject;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class DataAccessFacade implements IDataAccess{
    private SongMapper songMapper;
    private static DataAccessFacade DAF;

    public static DataAccessFacade getInstance() {
        if (DAF == null) DAF = new DataAccessFacade();
        return DAF;
    }

    @Inject
    public void setSongMapper(SongMapper songMapper) {
        this.songMapper = songMapper;
    }

    public DataAccessFacade() {
        songMapper = new SongMapper();
    }

    public boolean checkCredentials(Login login) {
        if(login.getUser().equalsIgnoreCase("Meron") &&
        login.getPassword().equalsIgnoreCase("MySuperSecretPassword12341")) {
            return true;
        }
        else return false;
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
