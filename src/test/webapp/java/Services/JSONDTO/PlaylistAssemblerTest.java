package Services.JSONDTO;

import Domain.Playlist;
import Domain.Track;
import Services.JSONDTO.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlaylistAssemblerTest {

	PlaylistAssembler playlistAssembler;
	TrackDTO tdto1, tdto2;
	Track t1, t2;
	TrackDTO[] trackDTOs;
	Playlist p1, p2;

	@BeforeAll
	public void setup() {
		tdto1 = new TrackDTO((long)1, "Song 1", "Performer1", 200,
				"Album1", null, null, null, false);
		tdto2 = new TrackDTO((long)2, "Song 2", "Performer2", 242,
				null, (long)39, "10-10-2010", "Second best song", false);
		t1 = new Track(); t1.setId((long)1); t1.setDuration(200);
		t1.setAvailableOffline(false); t1.setAlbum("Album1"); t1.setPerformer("Performer1");
		t2 = new Track(); t2.setId((long)2); t2.setDescription("Second best song"); t2.setAvailableOffline(false);
		t2.setPlaycount((long) 39); t2.setTitle("Song 2"); t2.setPublicationDate("10-10-2010");
		t2.setPerformer("Performer2"); t2.setDuration(242);
		p1 = new Playlist((long)1, "playlist1", (long)12);
		p2 = new Playlist((long)2, "playlist2", (long)24);
		ArrayList<Track> tracks = new ArrayList<>();
		tracks.add(t1);
		tracks.add(t2);
		p1.setTracks(tracks);
		p2.setTracks(tracks);
		trackDTOs = new TrackDTO[] {tdto1, tdto2};
		TrackAssembler trackAssembler = new TrackAssembler() {
			@Override
			public TracksDTO assembleTracksDTO(ArrayList<Track> tracks) {

				return new TracksDTO(trackDTOs);
			}
		};
		playlistAssembler = new PlaylistAssembler();
		playlistAssembler.setTrackAssembler(trackAssembler);
	}

	@Test
	public void testAssemblePlaylistDTO() {
		PlaylistDTO pdto = playlistAssembler.assemblePlaylistDTO(p1, true, trackDTOs);
		Assertions.assertEquals("playlist1", pdto.getName());
		Assertions.assertEquals((long)1, pdto.getId());
		Assertions.assertTrue(pdto.isOwner());
		Assertions.assertEquals("Song 1", pdto.getTracks()[0].getTitle());
	}

	@Test
	public void testDisassemblePlaylistDTO() {
		PlaylistDTO playlistDTO = new PlaylistDTO((long)3, "playlistnumber3", true, trackDTOs);
		Playlist playlist = playlistAssembler.disassemblePlaylistDTO(playlistDTO, (long)12);
		Assertions.assertEquals((long)3, playlist.getID());
		Assertions.assertEquals("playlistnumber3", playlist.getName());
		Assertions.assertEquals(0, playlist.getTracks().size());
	}

	@Test
	public void testAssemblePlaylistsDTO() {
		ArrayList<Playlist> playlists = new ArrayList<>();
		playlists.add(p1);
		playlists.add(p2);
		PlaylistsDTO playlistsDTO = playlistAssembler.assemblePlaylistsDTO(playlists, (long)24);
		Assertions.assertEquals(p1.getLength()+p2.getLength(), playlistsDTO.getLength());
		Assertions.assertFalse(playlistsDTO.getPlaylists()[0].isOwner());
		Assertions.assertTrue(playlistsDTO.getPlaylists()[1].isOwner());
		Assertions.assertEquals("playlist2", playlistsDTO.getPlaylists()[1].getName());
		Assertions.assertEquals("Song 1", playlistsDTO.getPlaylists()[0].getTracks()[0].getTitle());
	}
}
