package webservices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import entities.FactoryOrder;

/*************************************************************
* TEXT	Factory Order Resource
* <br><br>
* PGM ID:	FactoryOrderResource.java<br>
* @author	Daryl Davis<br>
* WRITTEN:	07/14/16<br>
* REVISIONS:<br>
*************************************************************/
@Stateless
@Path("factoryorder")
public class FactoryOrderResource {
    static final Logger LOG = Logger.getLogger(FactoryOrderResource.class.getName());
    @PersistenceContext(unitName = "PU")
    private EntityManager em;
    @Resource
    private SessionContext ctx;
    @Context 
    UriInfo uriInfo;
    //-Resources

    //error builder
    Response.ResponseBuilder errorBuilder;

    public FactoryOrderResource() {}
    
    @GET
    @Path("vin/{vin}")
    @Produces({"application/xml", "application/json"})
    public List<FactoryOrder> findByVIN(@PathParam("vin") String vin, @QueryParam("instprfx") @DefaultValue("") String installerPrefix) {
    	List<FactoryOrder> orders = null;
    	
    	TypedQuery<FactoryOrder> query = em.createNamedQuery(FactoryOrder.FIND_BY_VIN, FactoryOrder.class);
    	query.setParameter("vin", vin);
    	query.setParameter("installerPrefix", "%"+installerPrefix+"%");
    	
    	orders = query.getResultList();
    	
    	return orders;
    }
    
    @GET
    @Path("multi/vin/{vin}")
    @Produces({"application/xml", "application/json"})
    public List<FactoryOrder> findByVINMulti(@PathParam("vin") String vin, @QueryParam("instprfx") @DefaultValue("") String installerPrefix) {
        //PLACEHOLDER TO MATCH THE WEBSERVICE PATH USED IN THE SUBSEnterprise FactoryOrderResource
        //- this will function the same as the vin service above because there
        //- are not multiple sandboxes to check for vehicles when in ASC
        return findByVIN(vin, installerPrefix);
    }

// NOT NEEDED FOR VEHICLE SCANNING - FYI ONLY
//    @GET
//    @Path("{id}")
//    @Produces({"application/xml", "application/json"})
//    public FactoryOrder find(@PathParam("id") String id) {
//        FactoryOrder fo = em.find(FactoryOrder.class, id);
//        return fo;
//    }
//    
//    @POST
//    @Consumes({"application/xml", "application/json"})
//    public String create(FactoryOrder entity) {
//        return null;
//    }
//    
//    @PUT
//    @Consumes({"application/xml", "application/json"})
//    @Produces({"text/plain"})
//    public String update(FactoryOrder entity) {
//        return null;
//    }
//  
//    @DELETE
//    @Path("{id}")
//    @Produces("text/plain")
//    public String remove(@PathParam("id") String id) {
//    	return null;
//    }

    @PostConstruct
    void init() {
    }

    @PreDestroy 
    void exit() {
 
    }
}
