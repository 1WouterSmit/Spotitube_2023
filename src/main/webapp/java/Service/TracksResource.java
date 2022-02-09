package Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("tracks")
public class TracksResource {

    @GET
    public String helloWorld() {
        return "Hello, local world!";
    }
}
