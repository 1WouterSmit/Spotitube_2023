package JSONDTO;

public class TracksDTO {
    private TrackDTO[] tracks;

    public TracksDTO(TrackDTO[] tracks) {
        this.tracks = tracks;
    }

    public TrackDTO[] getTracks() {
        return tracks;
    }

    public void setTracks(TrackDTO[] tracks) {
        this.tracks = tracks;
    }
}
