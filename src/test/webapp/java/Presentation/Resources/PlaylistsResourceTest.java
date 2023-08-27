package Presentation.Resources;

import Services.Exceptions.AuthenticationException;
import Services.JSONDTO.PlaylistDTO;
import Services.JSONDTO.PlaylistsDTO;
import Services.JSONDTO.TrackDTO;
import Services.JSONDTO.TracksDTO;
import Presentation.Resources.PlaylistsResource;
import Services.LoginService;
import Services.PlaylistService;
import Services.TrackService;
import org.junit.jupiter.api.*;

import javax.ws.rs.core.Response;
import java.sql.SQLException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlaylistsResourceTest {
	private PlaylistsResource plRes;

	private final String VALIDTOKEN = "123-123-123-123";
	private final Long VALIDUSERID = (long)27;
	private final String TOKENFORINVALIDUSERID = "wronguseridplease";
	private final String SOMEINTERNALERROR = "someInternalError";

	@BeforeEach
	public void setup() {
		plRes = new PlaylistsResource();
		PlaylistService playlistService = new PlaylistService() {
			PlaylistDTO dto1 = new PlaylistDTO((long) 1, "playlist1", true, null);
			PlaylistDTO dto2 = new PlaylistDTO((long) 2, "playlist2", false, null);
			PlaylistDTO[] pdtos = {dto1, dto2};
			PlaylistsDTO playlistsDTO = new PlaylistsDTO(pdtos, 520);

			@Override
			public PlaylistsDTO getAllPlaylists(Long userid) throws SQLException {
				if (!userid.equals(VALIDUSERID)) throw new SQLException();
				return playlistsDTO;
			}

			@Override
			public void deletePlaylist(Long playlistId) throws SQLException {
				if (!(playlistId == (long) 1 || playlistId == (long) 2)) throw new SQLException();
				pdtos = new PlaylistDTO[]{dto2};
				playlistsDTO = new PlaylistsDTO(pdtos, 520);
			}

			@Override
			public void addPlaylist(PlaylistDTO playlistDTO, Long userId) throws SQLException {
				if (playlistDTO.getName().equals(SOMEINTERNALERROR)) throw new SQLException();
				PlaylistDTO newDTO = new PlaylistDTO((long) 3, playlistDTO.getName(), true, null);
				pdtos = new PlaylistDTO[]{dto1, dto2, newDTO};
				playlistsDTO = new PlaylistsDTO(pdtos, 720);
			}

			@Override
			public void editPlaylist(PlaylistDTO playlistDTO, Long userId) throws SQLException {
				pdtos = new PlaylistDTO[]{dto1, dto2};
				playlistsDTO = new PlaylistsDTO(pdtos, 520);
				for (PlaylistDTO pdto : pdtos) {
					if (pdto.getId().equals(playlistDTO.getId())) {
						pdto.setName(playlistDTO.getName());
						return;
					}
				}
				throw new SQLException();
			}
		};
		TrackService trackService = new TrackService() {
			TrackDTO track1 = new TrackDTO((long) 1, "track1", "performer1", 300, "album1",
					null, null, null, true);
			TrackDTO track2 = new TrackDTO((long) 2, "track2", "performer1", 300, "album2",
					null, null, null, true);
			TrackDTO track3 = new TrackDTO((long) 3, "track3", "performer2", 300, "album3",
					null, null, null, true);
			PlaylistDTO dto1 = new PlaylistDTO((long) 1, "playlist1", true, new TrackDTO[]{track2, track3});
			PlaylistDTO dto2 = new PlaylistDTO((long) 2, "playlist2", false, new TrackDTO[]{track1});

			@Override
			public TracksDTO getTrackListFromPlaylist(Long playlistId) throws SQLException {
				if (playlistId == (long) 1) {
					return new TracksDTO(dto1.getTracks());
				} else if (playlistId == (long) 2) {
					return new TracksDTO(dto2.getTracks());
				} else throw new SQLException();
			}

			@Override
			public void addTrackToPlaylist(Long playlistId, TrackDTO trackDTO) throws SQLException {
				if (playlistId == (long) 1 && trackDTO.getId() == (long) 1) {
					dto1.setTracks(new TrackDTO[]{track1, track2, track3});
				} else if (playlistId == (long) 2) {
					if (trackDTO.getId() == (long) 2) dto2.setTracks(new TrackDTO[]{track1, track2});
					else if (trackDTO.getId() == (long) 3) {
						dto2.setTracks(new TrackDTO[]{track1, track3});
					} else throw new SQLException();
				} else throw new SQLException();
			}

			@Override
			public void removeTrackFromPlaylist(Long playlistId, Long trackId) throws SQLException {
				if (playlistId == (long) 1) {
					if (trackId == (long) 2) dto1.setTracks(new TrackDTO[]{track3});
					else if (trackId == (long) 3) dto1.setTracks(new TrackDTO[]{track2});
					else throw new SQLException();
				} else if (playlistId == (long) 2 && trackId == (long) 1) {
					dto2.setTracks(new TrackDTO[]{});
				} else throw new SQLException();
			}
		};
		LoginService loginService = new LoginService() {
			@Override
			public Long checkToken(String token) throws AuthenticationException {
				if (token.equals(VALIDTOKEN)) return VALIDUSERID;
				else if (token.equals(TOKENFORINVALIDUSERID)) return (long) 120;
				else throw new AuthenticationException();
			}
		};
		plRes.setPlaylistService(playlistService);
		plRes.setTrackService(trackService);
		plRes.setLoginService(loginService);
	}

	// getAllPlaylists
	@Test
	public void testCorrectGetAllPL() {
		Response r = plRes.getAllPlaylists(VALIDTOKEN);
		Assertions.assertInstanceOf(PlaylistsDTO.class, r.getEntity());
		PlaylistsDTO dto = (PlaylistsDTO) r.getEntity();
		Assertions.assertEquals("playlist2", dto.getPlaylists()[1].getName());
		Assertions.assertEquals(2, dto.getPlaylists().length);
	}

	@Test
	public void testWrongTokenGetAllPL() {
		Response r = plRes.getAllPlaylists("000-000");
		Assertions.assertEquals(Response.Status.UNAUTHORIZED, r.getStatusInfo());
	}

	@Test
	public void testWrongUserIdGetAllPL() {
		Response r = plRes.getAllPlaylists(TOKENFORINVALIDUSERID);
		Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR ,r.getStatusInfo());
	}

	// deletePlaylist
	@Test
	public void testCorrectDeletePlaylist() {
		Response r = plRes.deletePlaylist(VALIDTOKEN, (long)1);
		Assertions.assertInstanceOf(PlaylistsDTO.class, r.getEntity());
		PlaylistsDTO dto = (PlaylistsDTO) r.getEntity();
		Assertions.assertEquals(1, dto.getPlaylists().length);
		Assertions.assertEquals("playlist2", dto.getPlaylists()[0].getName());
	}

	@Test
	public void testInvalidTokenDeletePL() {
		Response r = plRes.deletePlaylist("000-000", (long)1);
		Assertions.assertEquals(Response.Status.UNAUTHORIZED, r.getStatusInfo());
	}

	@Test
	public void testInvalidPlaylistIdDeletePL() {
		Response r = plRes.deletePlaylist(VALIDTOKEN, (long)55);
		Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, r.getStatusInfo());
	}

	// addPlaylist
	@Test
	public void testCorrectAddPL() {
		PlaylistDTO playlistDTO = new PlaylistDTO((long) -1, "Smooth Jazz", false, null);
		Response r = plRes.addPlaylist(VALIDTOKEN, playlistDTO);
		Assertions.assertInstanceOf(PlaylistsDTO.class, r.getEntity());
		PlaylistsDTO plsdto = (PlaylistsDTO) r.getEntity();
		Assertions.assertNotEquals(playlistDTO.getId(), plsdto.getPlaylists()[2].getId());
		Assertions.assertTrue(plsdto.getPlaylists()[2].isOwner());
	}

	@Test
	public void testSomeInternalErrorAddPL() {
		PlaylistDTO playlistDTO = new PlaylistDTO((long) -1, SOMEINTERNALERROR, false, null);
		Response r = plRes.addPlaylist(VALIDTOKEN, playlistDTO);
		Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, r.getStatusInfo());
	}

	// editPlaylist
	@Test
	public void testCorrectEditPL() {
		PlaylistDTO pdto = new PlaylistDTO((long)1, "Find a better name..", true, null);
		Response r = plRes.editPlaylist(VALIDTOKEN, (long)1, pdto);
		Assertions.assertInstanceOf(PlaylistsDTO.class, r.getEntity());
		PlaylistsDTO psdto = (PlaylistsDTO) r.getEntity();
		String foundName = null;
		for(PlaylistDTO p : psdto.getPlaylists()) {
			if(p.getId() == (long)1) foundName = p.getName();
		}
		Assertions.assertEquals("Find a better name..", foundName);
	}

	@Test
	public void testWrongPlaylistIdEditPL() {
		PlaylistDTO pdto = new PlaylistDTO((long)1, "Find a better name..", true, null);
		Response r = plRes.editPlaylist(VALIDTOKEN, (long)66, pdto);
		Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, r.getStatusInfo());
	}

	// getTracksFromPlaylist
	@Test
	public void testCorrectGetFromPL() {
		Response r = plRes.getTracksFromPlaylist(VALIDTOKEN, (long)1);
		Assertions.assertInstanceOf(TracksDTO.class, r.getEntity());
		TracksDTO tracksDTO = (TracksDTO) r.getEntity();
		Assertions.assertEquals(2, tracksDTO.getTracks().length);
		Assertions.assertEquals("track3", tracksDTO.getTracks()[1].getTitle());
	}

	@Test
	public void testInvalidPlaylistIdGetFromPL() {
		Response r = plRes.getTracksFromPlaylist(VALIDTOKEN, (long)67);
		Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, r.getStatusInfo());
	}

	// addTrackToPlaylist
	@Test
	public void testCorrectAddTrackToPL() {
		TrackDTO trackDTO = new TrackDTO((long)3, "track3", "performer2", 300, "album3",
				null, null, null, false);
		Response r = plRes.addTrackToPlaylist(VALIDTOKEN, (long)2, trackDTO);
		Assertions.assertInstanceOf(TracksDTO.class, r.getEntity());
		TracksDTO tsdto = (TracksDTO) r.getEntity();
		Assertions.assertEquals("track3", tsdto.getTracks()[1].getTitle());
	}

	@Test
	public void testInvalidPlaylistIdAddTrackToPL() {
		TrackDTO trackDTO = new TrackDTO((long)3, "track3", "performer2", 300, "album3",
				null, null, null, false);
		Response r = plRes.addTrackToPlaylist(VALIDTOKEN, (long)6, trackDTO);
		Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, r.getStatusInfo());
	}

	@Test
	public void testInvalidTrackIdAddTrackToPL() {
		TrackDTO trackDTO = new TrackDTO((long)6, "track6", "performer1", 300, "album1",
				null, null, null, false);
		Response r = plRes.addTrackToPlaylist(VALIDTOKEN, (long)2, trackDTO);
		Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, r.getStatusInfo());
	}

	// removeTrackFromPlaylist
	@Test
	public void testCorrectRemoveTrackFromPL() {
		Response r = plRes.removeTrackFromPlaylist(VALIDTOKEN, (long)1, (long)2);
		Assertions.assertInstanceOf(TracksDTO.class, r.getEntity());
		TracksDTO tracksDTO = (TracksDTO) r.getEntity();
		Assertions.assertEquals(1, tracksDTO.getTracks().length);
		Assertions.assertEquals(3, tracksDTO.getTracks()[0].getId());
	}

	@Test
	public void testInvalidPLidRemoveTrackFromPL() {
		Response r = plRes.removeTrackFromPlaylist(VALIDTOKEN, (long)6, (long)1);
		Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, r.getStatusInfo());
	}

	@Test
	public void testInvalidTrackIdRemoveTrackFromPL() {
		Response r = plRes.removeTrackFromPlaylist(VALIDTOKEN, (long)1, (long)1);
		Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, r.getStatusInfo());
	}
}
