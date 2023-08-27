package Services.JSONDTO;

public class PlaylistDTO {
    private Long id;
    private String name;
    private boolean owner;
    private TrackDTO[] tracks;

    public PlaylistDTO() {}

    public PlaylistDTO(Long id, String name, boolean owner, TrackDTO[] tracks) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.tracks = tracks;
    }

    /*public int getLength() {
        int length = 0;
        for(TrackDTO trackDTO : tracks) {
            length += trackDTO.getDuration();
        }
        return length;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public TrackDTO[] getTracks() {
        return tracks;
    }

    public void setTracks(TrackDTO[] tracks) {
        this.tracks = tracks;
    }
}
