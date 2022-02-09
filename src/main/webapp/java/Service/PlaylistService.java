package Service;

import DataAccess.DataAccessFacade;
import Domain.Playlist;
import JSONDTO.*;

import javax.inject.Inject;

public class PlaylistService {
    PlaylistsAssembler playlistsAssembler;
    PlaylistAssembler playlistAssembler;
    TracksAssembler tracksAssembler;
    DataAccessFacade dataAccessFacade;

    @Inject
    public void setDataAccessFacade(DataAccessFacade dataAccessFacade) {
        this.dataAccessFacade = dataAccessFacade;
    }
    @Inject
    public void setPlaylistsAssembler(PlaylistsAssembler playlistsAssembler) {
        this.playlistsAssembler = playlistsAssembler;
    }
    @Inject
    public void setTracksAssembler(TracksAssembler tracksAssembler) {
        this.tracksAssembler = tracksAssembler;
    }
    @Inject
    public void setPlaylistAssembler(PlaylistAssembler playlistAssembler) {
        this.playlistAssembler = playlistAssembler;
    }

    public PlaylistsDTO getAllPlaylists() {
        return playlistsAssembler.assemblePlaylistsDTO(dataAccessFacade.getAllPlaylists());
    }

    // TODO IMPLEMENT
    public PlaylistsDTO deletePlaylist(int id) {
        dataAccessFacade.deletePlaylist(id);
        return getAllPlaylists();
    }

    // TODO IMPLEMENT
    public PlaylistsDTO addPlaylist(PlaylistDTO playlistDTO) {
        Playlist playlist = playlistAssembler.disassemblePlaylistDTO(playlistDTO);
        dataAccessFacade.addPlaylist(playlist);
        return getAllPlaylists();
    }

    // TODO IMPLEMENT
    public PlaylistsDTO editPlaylist(int id, PlaylistDTO playlistDTO) {
        Playlist playlist = playlistAssembler.disassemblePlaylistDTO(playlistDTO);
        dataAccessFacade.editPlaylist(id, playlist);
        return getAllPlaylists();
    }

    public TracksDTO getTracksFromPlaylist(int id) {
        Playlist playlist = dataAccessFacade.getPlaylistById(id);
        return tracksAssembler.assembleTracksDTO(playlist.getTracks());
    }
}
