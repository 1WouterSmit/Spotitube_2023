package JSONDTO;

import Domain.Track;

import javax.inject.Inject;
import java.util.ArrayList;

public class TracksAssembler {
    private TrackAssembler trackAssembler;

    @Inject
    public void setTrackAssembler(TrackAssembler trackAssembler) {
        this.trackAssembler = trackAssembler;
    }

    public TracksDTO assembleTracksDTO(ArrayList<Track> tracks) {
        ArrayList<TrackDTO> trackDTOS = new ArrayList<>();

        for(Track track : tracks) {
            trackDTOS.add(trackAssembler.assembleTrackDTO(track));
        }

        return new TracksDTO((TrackDTO[]) trackDTOS.toArray());
    }
}
