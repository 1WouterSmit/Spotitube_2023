package Resources;

import Exceptions.AuthenticationException;
import JSONDTO.TrackDTO;
import JSONDTO.TracksDTO;
import Services.LoginService;
import Services.TrackService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.ws.rs.core.Response;
import java.sql.SQLException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TracksResourceTest {
	private TracksResource tracksRes;
	private String validtoken = "123-123-123-123";
	private Long playlistId = (long)1;
	private Long userId = (long)27;

	@BeforeAll
	public void setup() {
		TrackService trackService = new TrackService() {
			@Override
			public TracksDTO getTrackListForPlaylist(Long playlistId) throws SQLException {
				if (!(playlistId == 1)) throw new SQLException();
				TrackDTO tdto1 = new TrackDTO((long) 4, "So Long, Marianne", "Leonard Cohen", 546,
						"Songs of Leonard Cohen", null, null, null,
						false);
				return new TracksDTO(new TrackDTO[]{tdto1});
			}
		};
		LoginService loginService = new LoginService() {
			@Override
			public Long checkToken(String token) throws AuthenticationException {
				if(token.equals(validtoken)) return userId;
				else throw new AuthenticationException();
			}
		};
		tracksRes = new TracksResource();
		tracksRes.setTrackService(trackService);
		tracksRes.setLoginService(loginService);
	}

	@Test
	public void testCorrect() {
		Response r = tracksRes.getTracksForPlaylist(validtoken, playlistId);
		Assertions.assertInstanceOf(TracksDTO.class, r.getEntity());
		TrackDTO[] tracks = ((TracksDTO)r.getEntity()).getTracks();
		Assertions.assertEquals("Leonard Cohen", tracks[0].getPerformer());
	}

	@Test
	public void testWrongPlaylist() {
		Response r = tracksRes.getTracksForPlaylist(validtoken, (long)413);
		Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, r.getStatusInfo());
	}

	@Test
	public void testWrongToken() {
		Response r = tracksRes.getTracksForPlaylist("000-000-000-000", playlistId);
		Assertions.assertEquals(Response.Status.UNAUTHORIZED, r.getStatusInfo());
	}
}
