package Services.JSONDTO;

import Services.JSONDTO.TrackAssembler;
import Services.JSONDTO.TrackDTO;
import Services.JSONDTO.TracksDTO;
import Domain.Track;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.util.ArrayList;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TrackAssemblerTest {

	TrackAssembler trackAssembler;
	TrackDTO tdto1, tdto2;
	Track t1, t2;

	@BeforeAll
	public void setup() {
		tdto1 = new TrackDTO((long)1, "Song 1", "Performer1", 200,
				"Album1", null, null, null, false);
		tdto2 = new TrackDTO((long)2, "Song 2", "Performer2", 242,
				null, (long)39, "10-10-2010", "Second best song", false);
		t1 = new Track(); t1.setId((long)1); t1.setDuration(200); t1.setTitle("Song 1");
		t1.setAvailableOffline(false); t1.setAlbum("Album1"); t1.setPerformer("Performer1");
		t2 = new Track(); t2.setId((long)2); t2.setDescription("Second best song"); t2.setAvailableOffline(false);
		t2.setPlaycount((long) 39); t2.setTitle("Song 2"); t2.setPublicationDate("10-10-2010");
		t2.setPerformer("Performer2"); t2.setDuration(242);
		trackAssembler = new TrackAssembler();
	}

	@Test
	void testAssembleTrackDTO() {
		TrackDTO trackDTO = trackAssembler.assembleTrackDTO(t1);
		TrackDTO trackDTO2 = trackAssembler.assembleTrackDTO(t2);
		Assertions.assertEquals(1, trackDTO.getId());
		Assertions.assertEquals("Song 1", trackDTO.getTitle());
		Assertions.assertEquals("Performer1", trackDTO.getPerformer());
		Assertions.assertEquals("Album1", trackDTO.getAlbum());
		Assertions.assertNull(trackDTO.getDescription());
		Assertions.assertEquals(200, trackDTO.getDuration());
		Assertions.assertNull(trackDTO.getPublicationDate());
		Assertions.assertFalse(trackDTO.isOfflineAvailable());
		Assertions.assertNull(trackDTO.getPlaycount());

		Assertions.assertEquals(2, trackDTO2.getId());
		Assertions.assertEquals("Song 2", trackDTO2.getTitle());
		Assertions.assertEquals("Performer2", trackDTO2.getPerformer());
		Assertions.assertNull(trackDTO2.getAlbum());
		Assertions.assertEquals("Second best song", trackDTO2.getDescription());
		Assertions.assertEquals(242, trackDTO2.getDuration());
		Assertions.assertEquals("10-10-2010", trackDTO2.getPublicationDate());
		Assertions.assertFalse(trackDTO2.isOfflineAvailable());
		Assertions.assertEquals((long)39, trackDTO2.getPlaycount());
	}

	@Test
	void testAssembleTracksDTO() {
		trackAssembler = new TrackAssembler() {
			@Override
			public TrackDTO assembleTrackDTO(Track track) {
				if(track.getId() == (long)1) return tdto1;
				if(track.getId() == (long)2) return tdto2;
				else return null;
			}
		};
		ArrayList<Track> tracklist = new ArrayList<>();
		tracklist.add(t1);
		tracklist.add(t2);

		TracksDTO tracksDTO = trackAssembler.assembleTracksDTO(tracklist);

		Assertions.assertEquals(2, tracksDTO.getTracks().length);
		TrackDTO trackDTO = tracksDTO.getTracks()[0];
		TrackDTO trackDTO2 = tracksDTO.getTracks()[1];

		Assertions.assertEquals(1, trackDTO.getId());
		Assertions.assertEquals("Song 1", trackDTO.getTitle());
		Assertions.assertEquals("Performer1", trackDTO.getPerformer());
		Assertions.assertEquals("Album1", trackDTO.getAlbum());
		Assertions.assertNull(trackDTO.getDescription());
		Assertions.assertEquals(200, trackDTO.getDuration());
		Assertions.assertNull(trackDTO.getPublicationDate());
		Assertions.assertFalse(trackDTO.isOfflineAvailable());
		Assertions.assertNull(trackDTO.getPlaycount());

		Assertions.assertEquals(2, trackDTO2.getId());
		Assertions.assertEquals("Song 2", trackDTO2.getTitle());
		Assertions.assertEquals("Performer2", trackDTO2.getPerformer());
		Assertions.assertNull(trackDTO2.getAlbum());
		Assertions.assertEquals("Second best song", trackDTO2.getDescription());
		Assertions.assertEquals(242, trackDTO2.getDuration());
		Assertions.assertEquals("10-10-2010", trackDTO2.getPublicationDate());
		Assertions.assertFalse(trackDTO2.isOfflineAvailable());
		Assertions.assertEquals((long)39, trackDTO2.getPlaycount());

	}

	@Test
	void testDisassembleTrackDTO() {
		Track track1 = trackAssembler.disassembleTrackDTO(tdto1);
		Track track2 = trackAssembler.disassembleTrackDTO(tdto2);

		Assertions.assertEquals(1, track1.getId());
		Assertions.assertEquals("Song 1", track1.getTitle());
		Assertions.assertEquals("Performer1", track1.getPerformer());
		Assertions.assertEquals("Album1", track1.getAlbum());
		Assertions.assertNull(track1.getDescription());
		Assertions.assertEquals(200, track1.getDuration());
		Assertions.assertNull(track1.getPublicationDate());
		Assertions.assertFalse(track1.isAvailableOffline());
		Assertions.assertNull(track1.getPlaycount());

		Assertions.assertEquals(2, track2.getId());
		Assertions.assertEquals("Song 2", track2.getTitle());
		Assertions.assertEquals("Performer2", track2.getPerformer());
		Assertions.assertNull(track2.getAlbum());
		Assertions.assertEquals("Second best song", track2.getDescription());
		Assertions.assertEquals(242, track2.getDuration());
		Assertions.assertEquals("10-10-2010", track2.getPublicationDate());
		Assertions.assertFalse(track2.isAvailableOffline());
		Assertions.assertEquals((long)39, track2.getPlaycount());
	}
}