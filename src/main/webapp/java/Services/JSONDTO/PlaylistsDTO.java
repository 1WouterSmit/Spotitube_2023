package Services.JSONDTO;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class PlaylistsDTO {
    private PlaylistDTO[] playlists;

    private int length;

    public void setPlaylists(PlaylistDTO[] playlists) {
        this.playlists = playlists;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public PlaylistsDTO() {}

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
