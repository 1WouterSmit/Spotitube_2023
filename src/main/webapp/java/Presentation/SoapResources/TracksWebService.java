package Presentation.SoapResources;

import DataAccess.DB;
import DataAccess.MapperMySQL.TrackMapperImpl;
import DataAccess.MapperMySQL.UserMapperImpl;
import Services.Exceptions.AuthenticationException;
import Services.JSONDTO.TrackAssembler;
import Services.JSONDTO.TracksDTO;
import Services.LoginService;
import Services.TrackService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.sql.SQLException;

@WebService
@SOAPBinding( style = SOAPBinding.Style.RPC)
public class TracksWebService {
    private final TrackService trackService;
    private final LoginService loginService;

    public TracksWebService() {
        this.loginService = new LoginService();
        this.trackService = new TrackService();

        UserMapperImpl userMapper = new UserMapperImpl();
        userMapper.setDatabase(DB.getInstance());
        this.loginService.setUserMapper(userMapper);

        TrackMapperImpl trackMapper = new TrackMapperImpl();
        trackMapper.setDatabase(DB.getInstance());
        this.trackService.setTrackAssembler(new TrackAssembler());
        this.trackService.setTrackMapper(trackMapper);
    }

    @WebMethod
    public TracksDTO getTracksForPlaylist( @WebParam( name = "token" ) String token,
                                          @WebParam( name = "forPlaylist" ) Long playlistId)
            throws SQLException, AuthenticationException {

            loginService.checkToken(token);
            return trackService.getTrackListForPlaylist(playlistId);
    }
}
