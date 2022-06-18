package Resources;

import Exceptions.AuthenticationException;
import JSONDTO.PlaylistDTO;
import JSONDTO.PlaylistsDTO;
import JSONDTO.TrackDTO;
import JSONDTO.TracksDTO;
import Services.LoginService;
import Services.PlaylistService;
import Services.TrackService;

import javax.inject.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("playlists")
public class PlaylistsResource {
    private PlaylistService playlistService;
    private LoginService loginService;
    private TrackService trackService;

    @Inject
    public void setPlaylistService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }
    @Inject
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
    @Inject
    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam("token") String token) {
        try {
            Long userid = loginService.checkToken(token);
            PlaylistsDTO dto = playlistService.getAllPlaylists(userid);
            return buildResponseWithDTO(dto);
        } catch (SQLException sqlException) {
            return internalError();
        } catch (AuthenticationException sqlException) {
            return unauthorizedError();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{playlist_id}")
    public Response deletePlaylist(@QueryParam("token") String token, @PathParam("playlist_id") Long playlistId) {
        try {
            Long userid = loginService.checkToken(token);
            playlistService.deletePlaylist(playlistId);

            PlaylistsDTO dto = playlistService.getAllPlaylists(userid);
            return buildResponseWithDTO(dto);
        } catch (SQLException sqlException) {
            return internalError();
        } catch (AuthenticationException sqlException) {
            return unauthorizedError();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, PlaylistDTO playlistDTO) {
        try {
            Long userid = loginService.checkToken(token);
            playlistService.addPlaylist(playlistDTO, userid);

            PlaylistsDTO dto = playlistService.getAllPlaylists(userid);
            return buildResponseWithDTO(dto);
        } catch (SQLException e) {
            return internalError();
        } catch (AuthenticationException e) {
            return unauthorizedError();
        }
    }

    @Path("/{playlist_id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@QueryParam("token") String token, @PathParam("playlist_id") Long id,
                                     PlaylistDTO playlistDTO) {
        try {
            Long userid = loginService.checkToken(token);
            playlistDTO.setId(id);
            playlistService.editPlaylist(playlistDTO, userid);

            PlaylistsDTO dto = playlistService.getAllPlaylists(userid);
            return buildResponseWithDTO(dto);
        } catch (SQLException e) {
            return internalError();
        } catch (AuthenticationException e) {
            return unauthorizedError();
        }
    }

    @Path("/{playlist_id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksFromPlaylist(@QueryParam("token") String token, @PathParam("playlist_id") Long playlistId) {
        try {
            Long userId = loginService.checkToken(token);
            TracksDTO tracksDTO = trackService.getTrackListFromPlaylist(playlistId);

            return buildResponseWithDTO(tracksDTO);
        } catch (SQLException e) {
            return internalError();
        } catch (AuthenticationException e) {
            return unauthorizedError();
        }
    }

    @Path("/{playlistId}/tracks")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@QueryParam("token") String token, @PathParam("playlistId") Long playlistId,
                                       TrackDTO trackDTO) {
        try {
            Long userId = loginService.checkToken(token);
            trackService.addTrackToPlaylist(playlistId, trackDTO);
            TracksDTO dto = trackService.getTrackListFromPlaylist(playlistId);
            return buildResponseWithDTO(dto);
        } catch (SQLException e) {
            return internalError();
        } catch (AuthenticationException e) {
            return unauthorizedError();
        }
    }

    @Path("/{playlistId}/tracks/{trackId}")
    @DELETE
    public Response removeTrackFromPlaylist(@QueryParam("token") String token, @PathParam("playlistId") Long playlistId,
                                            @PathParam("trackId") Long trackId) {
        try {
            loginService.checkToken(token);
            trackService.removeTrackFromPlaylist(playlistId, trackId);
            TracksDTO dto = trackService.getTrackListFromPlaylist(playlistId);
            return buildResponseWithDTO(dto);
        } catch (SQLException e) {
            return internalError();
        } catch (AuthenticationException e) {
            return unauthorizedError();
        }
    }

    private Response internalError() {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
    private Response unauthorizedError() { return Response.status(Response.Status.UNAUTHORIZED).build();}
    private Response buildResponseWithDTO(Object entity) {
        return Response.ok(Response.Status.OK)
                .entity(entity)
                .build();
    }
}
