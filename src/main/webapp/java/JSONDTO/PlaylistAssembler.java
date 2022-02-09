package JSONDTO;

import Domain.Playlist;

import java.util.ArrayList;

public class PlaylistAssembler {

    public PlaylistDTO assemblePlaylistDTO(Playlist playlist) {
        //TODO CHECK USING TOKEN
        boolean isOwner = true;
        return new PlaylistDTO(playlist.getID(), playlist.getName(),isOwner, new TrackDTO[] {});
    }

    public Playlist disassemblePlaylistDTO(PlaylistDTO playlistDTO) {
        String tokenusername = "Meron";
        return new Playlist(playlistDTO.getId(), playlistDTO.getName(), tokenusername);
    }
}
