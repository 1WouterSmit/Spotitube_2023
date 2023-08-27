package Services.JSONDTO;

import Domain.Playlist;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class PlaylistAssembler {

    TrackAssembler trackAssembler;

    @Inject
    public void setTrackAssembler(TrackAssembler trackAssembler) {
        this.trackAssembler = trackAssembler;
    }

    public PlaylistDTO assemblePlaylistDTO(Playlist playlist, boolean isOwner, TrackDTO[] tracks) {
        return new PlaylistDTO(playlist.getID(), playlist.getName(),isOwner, tracks);
    }

    public Playlist disassemblePlaylistDTO(PlaylistDTO playlistDTO, Long userId) {
        return new Playlist(playlistDTO.getId(), playlistDTO.getName(), userId);
    }

    public PlaylistsDTO assemblePlaylistsDTO(List<Playlist> playlists, Long userId) {
        List<PlaylistDTO> playlistDTOS = new ArrayList<>();
        int playlistLength = 0;
        for(Playlist playlist : playlists) {
            TracksDTO tracksDTO = trackAssembler.assembleTracksDTO(playlist.getTracks());
            playlistDTOS.add(assemblePlaylistDTO(playlist, (userId.equals(playlist.getOwner())),
                    tracksDTO.getTracks()));
            playlistLength += playlist.getLength();
        }

        return new PlaylistsDTO(playlistDTOS, playlistLength);
    }
}
