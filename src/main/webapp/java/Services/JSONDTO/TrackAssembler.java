package Services.JSONDTO;

import Domain.Track;

import java.util.ArrayList;

public class TrackAssembler {

    public TrackDTO assembleTrackDTO(Track track) {
        return new TrackDTO(track.getId(), track.getTitle(), track.getPerformer(), track.getDuration(),
                track.getAlbum(), track.getPlaycount(), track.getPublicationDate(), track.getDescription(),
                track.isAvailableOffline());
    }

    public TracksDTO assembleTracksDTO(ArrayList<Track> tracks) {
        ArrayList<TrackDTO> trackDTOS = new ArrayList<>();

        for(Track track : tracks) {
            trackDTOS.add(assembleTrackDTO(track));
        }

        return new TracksDTO(trackDTOS.toArray(new TrackDTO[0]));
    }

    public Track disassembleTrackDTO(TrackDTO trackDTO) {
        Track track = new Track();
        track.setId(trackDTO.getId());
        track.setTitle(trackDTO.getTitle());
        track.setPerformer(trackDTO.getPerformer());
        track.setDuration(trackDTO.getDuration());
        track.setAlbum(trackDTO.getAlbum());
        track.setPlaycount(trackDTO.getPlaycount());
        track.setPublicationDate(trackDTO.getPublicationDate());
        track.setDescription(trackDTO.getDescription());
        track.setAvailableOffline(trackDTO.isOfflineAvailable());
        return track;
    }
}
