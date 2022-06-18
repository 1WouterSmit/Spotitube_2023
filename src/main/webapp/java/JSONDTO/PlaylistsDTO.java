package JSONDTO;

import java.util.ArrayList;
import java.util.List;

public class PlaylistsDTO {
    private PlaylistDTO[] playlists;

    private int length;

    public PlaylistsDTO(PlaylistDTO[] playlists, int length) {
        this.playlists = playlists;
        this.length = length;
    }


    public PlaylistsDTO(List<PlaylistDTO> playlists, int length) {
        this.playlists = new PlaylistDTO[playlists.size()];
        int i = 0;
        for(PlaylistDTO playlistDTO : playlists) {
            this.playlists[i] = playlists.get(i);
            i++;
        }
        this.length = length;
    }

    public PlaylistDTO[] getPlaylists() {
        return playlists;
    }

    public void setPlaylists(PlaylistDTO[] playlists) {
        this.playlists = playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
