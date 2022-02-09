package JSONDTO;

import Domain.Playlist;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class PlaylistsAssembler {
    @Inject
    PlaylistAssembler playlistAssembler;

    public PlaylistsDTO assemblePlaylistsDTO(List<Playlist> playlists) {
        List<PlaylistDTO> playlistDTOS = new ArrayList<>();
        int playlistLength = 0;
        for(Playlist playlist : playlists) {
            playlistDTOS.add(playlistAssembler.assemblePlaylistDTO(playlist));
            playlistLength += playlist.getLength();
        }

        return new PlaylistsDTO(playlistDTOS, playlistLength);
    }
}
