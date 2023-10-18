package Presentation.SoapResources;

import DataAccess.DB;
import DataAccess.MapperMySQL.PlaylistMapperImpl;
import DataAccess.MapperMySQL.TrackMapperImpl;
import DataAccess.MapperMySQL.UserMapperImpl;
import Services.Exceptions.AuthenticationException;
import Services.JSONDTO.*;
import Services.LoginService;
import Services.PlaylistService;
import Services.TrackService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.sql.SQLException;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class PlaylistsWebService {
    private final PlaylistService playlistService;
    private final LoginService loginService;
    private final TrackService trackService;

    public PlaylistsWebService() {
        this.loginService = new LoginService();
        this.trackService = new TrackService();
        this.playlistService = new PlaylistService();

        UserMapperImpl userMapper = new UserMapperImpl();
        userMapper.setDatabase(DB.getInstance());
        PlaylistMapperImpl playlistMapper = new PlaylistMapperImpl();
        playlistMapper.setDatabase(DB.getInstance());
        TrackMapperImpl trackMapper = new TrackMapperImpl();
        trackMapper.setDatabase(DB.getInstance());

        loginService.setUserMapper(userMapper);

        trackService.setTrackMapper(trackMapper);
        TrackAssembler trackAssembler = new TrackAssembler();
        trackService.setTrackAssembler(trackAssembler);

        playlistService.setTrackService(trackService);
        playlistService.setPlaylistMapper(playlistMapper);
        PlaylistAssembler playlistAssembler = new PlaylistAssembler();
        playlistAssembler.setTrackAssembler(trackAssembler);
        playlistService.setPlaylistAssembler(playlistAssembler);
    }

    @WebMethod
    public PlaylistsDTO getAllPlaylists( @WebParam( name = "token" ) String token)
            throws SQLException, AuthenticationException {

        Long userid = loginService.checkToken(token);
        return playlistService.getAllPlaylists(userid);
    }

    @WebMethod
    public PlaylistsDTO deletePlaylist( @WebParam( name = "token" ) String token,
                                        @WebParam( name = "playlistId" ) Long playlistId)
            throws SQLException, AuthenticationException {

        Long userid = loginService.checkToken(token);
        playlistService.deletePlaylist(playlistId);
        return playlistService.getAllPlaylists(userid);
    }

    @WebMethod
    public PlaylistsDTO addPlaylist( @WebParam( name = "token" ) String token,
                                     @WebParam( name = "playlistDTO" ) PlaylistDTO playlistDTO)
            throws SQLException, AuthenticationException {

        Long userid = loginService.checkToken(token);
        playlistService.addPlaylist(playlistDTO, userid);
        return playlistService.getAllPlaylists(userid);
    }

    @WebMethod
    public PlaylistsDTO editPlaylist( @WebParam( name = "token" ) String token,
                                      @WebParam( name = "playlistId" ) Long playlistId,
                                      @WebParam( name = "playlistDTO" ) PlaylistDTO playlistDTO)
            throws SQLException, AuthenticationException {

        Long userid = loginService.checkToken(token);
        playlistDTO.setId(playlistId);
        playlistService.editPlaylist(playlistDTO, userid);
        return playlistService.getAllPlaylists(userid);
    }

    @WebMethod
    public TracksDTO getTracksFromPlaylist( @WebParam( name = "token" ) String token,
                                            @WebParam( name = "playlistId" ) Long playlistId)
            throws SQLException, AuthenticationException {

        loginService.checkToken(token);
        return trackService.getTrackListFromPlaylist(playlistId);
    }

    @WebMethod
    public TracksDTO addTrackToPlaylist( @WebParam( name = "token" ) String token,
                                         @WebParam( name = "playlistId" ) Long playlistId,
                                         @WebParam( name = "trackDTO" ) TrackDTO trackDTO)
            throws SQLException, AuthenticationException {

        loginService.checkToken(token);
        trackService.addTrackToPlaylist(playlistId, trackDTO);
        return trackService.getTrackListFromPlaylist(playlistId);
    }

    @WebMethod
    public TracksDTO removeTrackFromPlaylist( @WebParam( name = "token" ) String token,
                                              @WebParam( name = "playlistId" ) Long playlistId,
                                              @WebParam( name = "trackId" ) Long trackId)
            throws SQLException, AuthenticationException {

        loginService.checkToken(token);
        trackService.removeTrackFromPlaylist(playlistId, trackId);
        return trackService.getTrackListFromPlaylist(playlistId);

    }
}
