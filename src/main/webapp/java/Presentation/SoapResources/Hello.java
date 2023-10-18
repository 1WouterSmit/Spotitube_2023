package Presentation.SoapResources;

import Services.JSONDTO.TrackDTO;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class Hello {
    @WebMethod
    public String hi() {
        return "Hi!";
    }

    @WebMethod
    public TrackDTO getTrack() {
        TrackDTO trackDTO = new TrackDTO((long)1, "All along the watchtower", "Jimi Hendrix",
                240, "Electric Ladyland", (long)1, "01-01-1968",
                "Cover of Bob Dylan", false);
        return trackDTO;
    }
}
