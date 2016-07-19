package webservices;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Logger;

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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

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
    	//write transaction immediately to the database for the log
    	BigDecimal nextId = FactoryOrderScan.getNextID(em);
    	entity.setId(nextId);
    	
        //DEBUG TODO: REMOVE
        Logger.getLogger("FactoryOrderScanResource").info("Submitted scan in XML: " + printXMLString(entity));
    	
    	em.persist(entity);
    	
    	entity.setUploadDate(new Date());
    	entity.setPostedDate(new Date());
    	
    	//do some code to validate the VIN or any other data
    	//***business rules***
    	// example, test that Received scan isn't an already received vehicle. 
    	
    	entity.setMessage("Scans not accepted yet");
    	entity.setValidationError("Y");
    	
    	//update database with changes
    	em.merge(entity);
    	
    	//return updated scan back to scanner
    	return entity;
    }
    
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public FactoryOrderScan find(@PathParam("id") BigDecimal id) {
        return em.find(FactoryOrderScan.class, id);
    }
    
    @GET
    @Path("createtestdata/{id}")
    @Produces({"application/xml", "application/json"})
    public FactoryOrderScan createTestData(@PathParam("id") BigDecimal id) {
        FactoryOrderScan foscan = new FactoryOrderScan(id);
        foscan.setFactoryOrder("TEST"+id.toString().trim());
        foscan.setVin("0000000000000000"+id.toString().trim());
        foscan.setPostedDate(new Date());
        foscan.setUploadDate(new Date());
        foscan.setScanCode("TST");
        
        //write to database
        em.persist(foscan);
        
        return foscan;
    }
    
    @PostConstruct 
    void init() {

    }

    @PreDestroy 
    void exit() {

    }
    
    public static <T> String printXMLString(T obj) {
        try {
            //Marshalling the XML to see what it looks like.
            JAXBContext ctx = JAXBContext.newInstance(obj.getClass());
            //temp stringwriter to send the XML to a string for output
            StringWriter writer = new StringWriter();
            //send the XML version of header to the writer
            ctx.createMarshaller().marshal(obj, writer);
            return writer.toString();
        } catch (JAXBException jAXBException) {
            return jAXBException.getMessage();
        }
    }    
}
