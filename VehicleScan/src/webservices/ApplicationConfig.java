package webservices;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;


/**
 * @author DarylD
 */
@javax.ws.rs.ApplicationPath("resources")
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        packages("webservices","mappers");
        //forces Glassfish 4.1 to use Jackson rather than default MOXy for json
        //- this is because we have a @Provider JacksonJAXBAnnotationMapper to
        //-- control the way the json is created. 
        property(ServerProperties.MOXY_JSON_FEATURE_DISABLE, true);
    }

}
