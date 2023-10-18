package Services.JSONDTO;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TracksDTO {
    private TrackDTO[] tracks;

    public TracksDTO() {}
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
