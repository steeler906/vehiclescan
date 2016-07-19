package webservices;

import javax.ejb.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

@Path("ClientLogin")
@Singleton
public class ClientLoginResource {

    /** Creates a new instance of GenericResource */
    public ClientLoginResource() {

    }
        
    @GET @Path("poll")
    @Produces("text/plain")
    public String pollServer() {
        //THis end point is used to poll the server for a simple connection test. 
        return "connected";
    }
    
    @GET @Path("test")
    @Produces("text/plain")
    public String testServer() {
        //This end point is used to poll the server for a simple connection test.
        //-will not return a token, instead gives a simple test. 
        return "notokenfortest";
    }
    
    @GET @Path("{service}")
    @Produces("text/plain")
    public String getToken(@Context SecurityContext sec, @PathParam("service") String service) {
        return service;
    }
}
