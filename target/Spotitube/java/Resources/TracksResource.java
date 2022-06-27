package Resources;

import Exceptions.AuthenticationException;
import JSONDTO.TracksDTO;
import Services.LoginService;
import Services.TrackService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("tracks")
public class TracksResource {
    private TrackService trackService;
    private LoginService loginService;

    @Inject
    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }
    @Inject
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksForPlaylist(@QueryParam("token") String token, @QueryParam("forPlaylist") Long playlistId) {
        try {
            loginService.checkToken(token);
            TracksDTO dto = trackService.getTrackListForPlaylist(playlistId);
            return Response.ok(Response.Status.OK)
                    .entity(dto)
                    .build();
        } catch (SQLException sqlException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (AuthenticationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
