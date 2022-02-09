package JSONDTO;

import Domain.Song;
import Domain.Track;
import Domain.Video;

public class TrackAssembler {

    public TrackDTO assembleTrackDTO(Track track) {
        TrackDTO trackDTO;

        if(track instanceof Song) {

            Song song = (Song)track;
            trackDTO = new TrackDTO(song.getId(), song.getTitle(), song.getPerformer(), song.getDuration(),
                    song.getAlbum(), song.isOfflineAvailable());
        } else {

            Video video = (Video)track;
            trackDTO = new TrackDTO(video.getId(), video.getTitle(), video.getPerformer(), video.getDuration(),
                    video.getPlaycount(), video.getPublicationDate(), video.getDescription(), video.isOfflineAvailable());
        }

        return trackDTO;
    }
}
