package Services;

import DataAccess.Mapper.PlaylistMapper;
import Domain.Playlist;
import JSONDTO.*;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;

public class PlaylistService {
    PlaylistAssembler playlistAssembler;
    PlaylistMapper playlistMapper;
    TrackService trackService;

    @Inject
    public void setPlaylistAssembler(PlaylistAssembler playlistAssembler) {
        this.playlistAssembler = playlistAssembler;
    }
    @Inject
    public void setPlaylistMapper(PlaylistMapper playlistMapper) { this.playlistMapper = playlistMapper;}
    @Inject
    public void setTrackService(TrackService trackService) { this.trackService = trackService;}

    public PlaylistsDTO getAllPlaylists(Long userId) throws SQLException {
        List<Playlist> playlists = playlistMapper.findAll();
        for(Playlist playlist : playlists) {
            playlist.setTracks(trackService.getTracksFromPlaylist(playlist.getID()));
        }
        return playlistAssembler.assemblePlaylistsDTO(playlists, userId);
    }

    public void deletePlaylist(Long id) throws SQLException {
        playlistMapper.deletePlaylist(id);
    }

    public void addPlaylist(PlaylistDTO playlistDTO, Long userId) throws SQLException {
        Playlist playlist = playlistAssembler.disassemblePlaylistDTO(playlistDTO, userId);
        playlistMapper.insertPlaylist(playlist);
    }

    public void editPlaylist(PlaylistDTO playlistDTO, Long userid) throws SQLException {
        Playlist playlist = playlistAssembler.disassemblePlaylistDTO(playlistDTO, userid);
        playlistMapper.updateName(playlist.getID(), playlist.getName());
    }
}
