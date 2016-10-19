package webservices;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import entities.FactoryOrder;
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
    public FactoryOrderScan createNew(FactoryOrderScan scan) {
    	//write transaction immediately to the database for the log
    	BigDecimal nextId = FactoryOrderScan.getNextID(em);
    	scan.setId(nextId);
    	
    	//Validations
		String vin = scan.getVin();
		if (vin.length()>17){
			scan.setVin(vin.substring(0,  17));
		}
		String fo = scan.getFactoryOrder();
		if (fo.length()>10){
			scan.setFactoryOrder(fo.substring(0,  10));
		}
		
    	
        //DEBUG TODO: REMOVE
        Logger.getLogger("FactoryOrderScanResource").info("Submitted scan in XML: " + printXMLString(scan));
    	
    	em.persist(scan);
    	
    	scan.setUploadDate(new Date());
    	scan.setPostedDate(new Date());
    	
    	boolean inError = false;
    	String message = "";
    	
    	//do some code to validate the VIN or any other data
    	//***business rules***
    	//Find Record by VIN
    	vin = scan.getVin(); //make sure we have newest vin. 
		TypedQuery<FactoryOrder> q = em.createQuery("select f from FactoryOrder f where f.vin = :vin and f.recordClosedDate is null", FactoryOrder.class);
		q.setParameter("vin", vin);
		//Get the FactoryOrder Object
		FactoryOrder f = null;
		try {
			f = q.getSingleResult();
		} catch (NoResultException nre) {
			message = "VIN not found";
			inError = true;
		} catch (NonUniqueResultException nure) {
			message = "Multiple VINs found, please review.";
			inError = true;
		}
		if (f == null) { //safety code for VIN
			message = "Problem getting VIN, please call IS.";
			inError = true;
		}
		
    	// example, test that Received scan isn't an already received vehicle. 
    	//
		// Processed 'received' scans
		if (inError == false) {
			if (scan.getScanCode().trim().equals("ST")) {
				// Receive Scan
				if (f.getReceivedDate() != null) {
					message = "VIN is already received.";
					inError = true;
				} else {
					//uPDATE THE Sql sERVER  cvi vin mASTER TABLE
					f.setReceivedDate(scan.getScanDate());
				}
			} else if (scan.getScanCode().trim().equals("LOC")) {
				//Location Scan
				String loc = scan.getVehicleLocation();
				if (loc != null && !loc.isEmpty()) {
					f.setLotLocation(scan.getVehicleLocation());
				} else {
					message = "Scan must have a location";
					inError = true;
				}
			} else if (scan.getScanCode().trim().equals("IN")) {
				//Install Scan
				if (f.getReceivedDate() == null) {
					message = "VIN must first be received";
					inError = true;
				} else if (f.getInstalledDate() != null) {
					message = "VIN already installed";
					inError = true;
				} else if (f.getSalesOrderNum() == null || f.getSalesOrderNum().equals(BigDecimal.ZERO)) {
					message = "Installed VIN must have an Order";
					inError = true;
				}
				else {
					f.setInstalledDate(scan.getScanDate());
				}
			} else if (scan.getScanCode().trim().equals("SHP")) {
				//Shipped Scan
				if (f.getReceivedDate() == null) {
					message = "VIN must first be received";
					inError = true;
				} else if (f.getInstalledDate() == null) {
					message = "VIN must be installed";
					inError = true;
				} else if (f.getShippedDate() != null) {
					message = "VIN already shipped";
					inError = true;
				} else if (f.getSalesOrderNum() == null || f.getSalesOrderNum().equals(BigDecimal.ZERO)) {
					message = "Shipped VIN must have an Order";
					inError = true;
				}
				else {
					f.setShippedDate(scan.getScanDate());
				}
			}
			else {
				message = "Scan type not supported: " + scan.getScanCode();
				inError = true;
			}
		}
    	
    	scan.setMessage(message);
    	scan.setValidationError((inError)?"Y":"");
    	
    	//update database with changes
    	em.merge(scan);
    	
    	//return updated scan back to scanner
    	return scan;
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
