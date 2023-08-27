package Services.JSONDTO;

import java.util.List;

public class PlaylistsDTO {
    private final PlaylistDTO[] playlists;

    private final int length;

    public PlaylistsDTO(PlaylistDTO[] playlists, int length) {
        this.playlists = playlists;
        this.length = length;
    }

    public PlaylistsDTO(List<PlaylistDTO> playlists, int length) {
        this.playlists = new PlaylistDTO[playlists.size()];
        int i = 0;
        for(PlaylistDTO playlistDTO : playlists) {
            this.playlists[i] = playlistDTO;
            i++;
        }
        this.length = length;
    }

    public PlaylistDTO[] getPlaylists() {
        return playlists;
    }

    public int getLength() {
        return length;
    }
}
