package webservices;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import entities.FactoryOrderScan;

/*************************************************************
* TEXT	Factory Order Scan Resource
* <br><br>
* PGM ID:	FactoryOrderScanResource.java<br>
* @author	Daryl Davis<br>
* WRITTEN:	Jan 27, 2012, Prj 1541<br>
* REMARKS: 	-<br>
* REVISIONS:<br>
*************************************************************/
@Stateless
@Path("factoryorderscan")
public class FactoryOrderScanResource {
    @PersistenceContext(unitName = "PU")
    private EntityManager em;
    
    //error biulder
    Response.ResponseBuilder errorBuilder;
    
    public FactoryOrderScanResource() {}
    
    @POST
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public FactoryOrderScan createNew(FactoryOrderScan entity) {
    	return null;
    }
    
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public FactoryOrderScan find(@PathParam("id") BigDecimal id) {
        return em.find(FactoryOrderScan.class, id);
    }
    
    @PostConstruct 
    void init() {

    }

    @PreDestroy 
    void exit() {

    }
    
}
