package Service;

import JSONDTO.PlaylistDTO;
import JSONDTO.PlaylistsDTO;
import JSONDTO.TracksDTO;

import javax.inject.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("playlists")
public class PlaylistsResource {
    private PlaylistService playlistService;

    @Inject
    public void setPlaylistService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PlaylistsDTO getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public PlaylistsDTO addPlaylist(PlaylistDTO playlistDTO) {
        return playlistService.addPlaylist(playlistDTO);
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public PlaylistsDTO editPlaylist(@PathParam("id") int id, PlaylistDTO playlistDTO) {
        return playlistService.editPlaylist(id, playlistDTO);
    }

    @Path("/{id}")
    @DELETE
    public PlaylistsDTO deletePlaylist(@PathParam("id") int id) {
        return playlistService.deletePlaylist(id);
    }

    @Path("/{id}/tracks")
    @GET
    public TracksDTO getTracksFromPlaylist(@PathParam("id") int id) {
        return playlistService.getTracksFromPlaylist(id);
    }
}
