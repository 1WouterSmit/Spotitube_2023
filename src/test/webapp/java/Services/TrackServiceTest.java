package Services;

import DataAccess.MapperMySQL.TrackMapperImpl;
import Domain.Track;
import JSONDTO.TrackAssembler;
import JSONDTO.TrackDTO;
import JSONDTO.TracksDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;
import java.util.ArrayList;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TrackServiceTest {
	private TrackService trSer;

	@BeforeEach
	public void setup() {
		TrackMapperImpl trackMapperImpl = new TrackMapperImpl() {
			@Override
			public ArrayList<Track> getAllTracksInPlaylist(Long playlistId) throws SQLException {
				if(playlistId != 1) throw new SQLException();

				ArrayList<Track> tracks = new ArrayList<>();
				Track t1,t2,t3;

				t1 = new Track();
				t1.setId((long)1);
				t1.setTitle("track1");
				t1.setPerformer("performer1");
				t1.setDuration(210);
				t1.setAlbum("album1");
				t1.setAvailableOffline(true);

				t2 = new Track();
				t2.setId((long)2);
				t2.setTitle("track2");
				t2.setPerformer("performer1");
				t2.setDuration(210);
				t2.setAlbum("album1");
				t2.setAvailableOffline(false);

				t3 = new Track();
				t3.setId((long)3);
				t3.setTitle("track3");
				t3.setPerformer("performer2");
				t3.setDuration(210);
				t3.setAlbum("albumX");
				t3.setAvailableOffline(false);

				tracks.add(t1);
				tracks.add(t2);
				tracks.add(t3);
				return tracks;
			}
			@Override
			public ArrayList<Track> getAllTracksNotInPlaylist(Long playlistId) throws SQLException {
				if(playlistId != 1) throw new SQLException();
				ArrayList<Track> tracks = new ArrayList<>();
				Track t4,t5;

				t4 = new Track();
				t4.setId((long)4);
				t4.setTitle("track4");
				t4.setPerformer("performer3");
				t4.setDuration(340);
				t4.setPlaycount((long)37);
				t4.setPublicationDate("01-01-2010");
				t4.setDescription("Her best");
				t4.setAvailableOffline(true);

				t5 = new Track();
				t5.setId((long)5);
				t5.setTitle("track5");
				t5.setPerformer("performer4");
				t5.setDuration(310);
				t5.setPlaycount((long)210);
				t5.setPublicationDate("12-12-2012");
				t5.setDescription("Live");
				t5.setAvailableOffline(false);

				tracks.add(t4);
				tracks.add(t5);
				return tracks;
			}
			@Override
			public void addTrackToPlaylist(Long playlistId, Track track) throws SQLException {
				if(playlistId != 1 || track.getId() != 5) throw new SQLException();
			}
			@Override
			public void removeTrackFromPlaylist(Long playlistId, Long trackId) throws SQLException {
				if(!((playlistId == 1) && (trackId==1 || trackId==2 || trackId==3))) throw new SQLException();
			}
		};
		TrackAssembler trackAssembler = new TrackAssembler() {
			@Override
			public TracksDTO assembleTracksDTO(ArrayList<Track> tracks) {
				if(tracks.size() == 3) {
					TrackDTO t1 = new TrackDTO((long) 1, "track1", "performer1", 210, "album1",
							null, null, null, true);

					TrackDTO t2 = new TrackDTO((long) 2, "track2", "performer1", 210, "album1",
							null, null, null, false);

					TrackDTO t3 = new TrackDTO((long) 3, "track3", "performer2", 210, "albumX",
							null, null, null, false);
					TrackDTO[] trackDTOs = {t1, t2, t3};
					return new TracksDTO(trackDTOs);
				} else {
					TrackDTO t4 = new TrackDTO((long)4, "track4", "performer3", 340, null,
							(long)37, "01-01-2010", "Her best", true);

					TrackDTO t5 = new TrackDTO((long)5, "track5", "performer4", 310, null,
							(long)210, "12-12-2012", "Live", false);
					TrackDTO[] trackDTOs = {t4,t5};
					return new TracksDTO(trackDTOs);
				}
			}
			@Override
			public Track disassembleTrackDTO(TrackDTO tdto) {
				if (tdto.getId() == 3)
				{
					Track t3 = new Track();
					t3.setId((long)3);
					t3.setTitle("track3");
					t3.setPerformer("performer2");
					t3.setDuration(210);
					t3.setAlbum("albumX");
					t3.setAvailableOffline(false);
					return t3;
				}
				Track t5 = new Track();
				t5.setId((long)5);
				t5.setTitle("track5");
				t5.setPerformer("performer4");
				t5.setDuration(310);
				t5.setPlaycount((long)210);
				t5.setPublicationDate("12-12-2012");
				t5.setDescription("Live");
				t5.setAvailableOffline(false);
				return t5;
			}
		};
		trSer = new TrackService();
		trSer.setTrackMapper(trackMapperImpl);
		trSer.setTrackAssembler(trackAssembler);
	}

	@Test
	public void testCorrectGetTrackListFromPL() throws SQLException{
		TracksDTO tracksDTO = trSer.getTrackListFromPlaylist((long)1);
		Assertions.assertEquals(3, tracksDTO.getTracks().length);
	}

	@Test
	public void testInvalidTrackIdTrackListFromPL() {
		Assertions.assertThrows(SQLException.class, () -> trSer.getTrackListFromPlaylist((long)9));
	}

	@Test
	public void getCorrectTracksFromPlaylist() throws SQLException {
		ArrayList<Track> tracklist = trSer.getTracksFromPlaylist((long)1);
		Assertions.assertEquals("track1", tracklist.get(0).getTitle());
		Assertions.assertEquals("track2", tracklist.get(1).getTitle());
		Assertions.assertEquals("track3", tracklist.get(2).getTitle());
	}

	@Test
	public void getTracksFromInvalidPlaylist() {
		Assertions.assertThrows(SQLException.class, () -> trSer.getTracksFromPlaylist((long)7));
	}

	@Test
	public void testCorrectGetTrackListForPL() throws SQLException{
		TracksDTO tracksDTO = trSer.getTrackListForPlaylist((long)1);
		Assertions.assertEquals(2, tracksDTO.getTracks().length);
	}

	@Test
	public void testInvalidTrackIdTrackListForPL() {
		Assertions.assertThrows(SQLException.class, () -> trSer.getTrackListFromPlaylist((long)9));
	}

	@Test
	public void getCorrectTracksForPlaylist() throws SQLException {
		ArrayList<Track> tracklist = trSer.getTracksForPlaylist((long)1);
		Assertions.assertEquals("track4", tracklist.get(0).getTitle());
		Assertions.assertEquals("track5", tracklist.get(1).getTitle());
	}

	@Test
	public void getTracksForInvalidPlaylist() {
		Assertions.assertThrows(SQLException.class, () -> trSer.getTracksFromPlaylist((long)7));
	}

	@Test
	public void addTrackCorrectlyToPL() throws SQLException {
		TrackDTO tdto = new TrackDTO();
		tdto.setId((long)5);
		trSer.addTrackToPlaylist((long)1, tdto);
	}

	@Test
	public void addTrackAlreadyInPlaylist() {
		TrackDTO tdto = new TrackDTO();
		tdto.setId((long)3);
		Assertions.assertThrows(SQLException.class, () -> trSer.addTrackToPlaylist((long)1, tdto));
	}

	@Test
	public void addTrackToInvalidPlaylist() {
		TrackDTO tdto = new TrackDTO();
		tdto.setId((long)5);
		Assertions.assertThrows(SQLException.class, () -> trSer.addTrackToPlaylist((long)4, tdto));
	}

	@Test
	public void removeTrackCorrectlyFromPL() throws SQLException {
		trSer.removeTrackFromPlaylist((long)1, (long)3);
	}

	@Test
	public void removeTrackFromInvalidPL() {
		Assertions.assertThrows(SQLException.class, () -> trSer.removeTrackFromPlaylist((long)6, (long)3));
	}

	@Test
	public void removeTrackNotInPL() {
		Assertions.assertThrows(SQLException.class, () -> trSer.removeTrackFromPlaylist((long)1, (long)4));
	}
}