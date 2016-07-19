package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;

@Entity
@Table(name = "tblFactoryOrderScan")//, schema = "OEF")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = FactoryOrderScan.FIND_ALL, query = "SELECT s FROM FactoryOrderScan s"),
    @NamedQuery(name = FactoryOrderScan.FIND_MAX, query = "SELECT MAX(s.id) FROM FactoryOrderScan s")})
public class FactoryOrderScan implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static final String FIND_ALL = "FactoryOrderScan.findAll";
    public static final String FIND_MAX = "FactoryOrderScan.findMax";

    public FactoryOrderScan() {
        super(); //temp for placement
    }

//- Class Elements for JAXB and JPA

    @Id
//    @Column(name = "TRCTLN")
    private BigDecimal id = BigDecimal.ZERO;

    @Size(max=3)
//    @Column(name = "TRLOC")
    private String installerPrefix = "";

//    @Column(name = "TRUPLDT")
    @Temporal(TemporalType.DATE)
    private Date uploadDate = null;

//    @Column(name = "TRPSTDT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postedDate = null;

    @Size(max=10)
//    @Column(name = "TRASFO")
    private String factoryOrder = "";

//    @Column(name = "TRASSO")
    private BigDecimal tRASSO = BigDecimal.ZERO;

    @Size(max=1)
//    @Column(name = "TRERR", length = 1)
    private String validationError = "";

    @Size(max=1)
//    @Column(name = "TRRVW", length = 1)
    private String reviewed = "";

    @Size(max=3)
//    @Column(name = "TRCODE", length = 3, nullable = false)
    private String scanCode = "";

    @Size(max=5)
//    @Column(name = "TRUSER", length = 5, nullable = false)
    private String userInitials = "";

//    @Column(name = "TRSCANDT")
    @Temporal(TemporalType.DATE)
    private Date scanDate = null;

    @Size(max=20)
//    @Column(name = "TRVIN", length = 20, nullable = false)
    private String vin = "";

//    @Column(name = "TRFO")
//    private String tRFO = "";

//    @Column(name = "TRTRANDT")
    @Temporal(TemporalType.DATE)
    private Date transactionDate = null;

    @Size(max=15)
//    @Column(name = "TRVEHLOC", length = 15, nullable = false)
    private String vehicleLocation = "";

    @Size(max=1)
//    @Column(name = "TRVEHTYP", length = 1)
    private String vehicleType = "";

    @Size(max=5)
//    @Column(name = "TRDMG", length = 5)
    private String damageCode = "";

    @Size(max=1)
//    @Column(name = "TRPDI", length = 1)
    private String preDeliveryInspection = "";

//    @Column(name = "TRESTDT")
    @Temporal(TemporalType.DATE)
    private Date estimatedInstallDate = null;

//    @Column(name = "TRMATDT")
    @Temporal(TemporalType.DATE)
    private Date materialRequestDate = null;

    //Transient Fields not going to the database XML Only
//    @Transient
    private String message = "";
    
    public FactoryOrderScan(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getInstallerPrefix() {
        return installerPrefix;
    }

    public void setInstallerPrefix(String installerPrefix) {
        this.installerPrefix = installerPrefix;
    }

    public String getFactoryOrder() {
        return factoryOrder;
    }

    public void setFactoryOrder(String factoryOrder) {
        this.factoryOrder = factoryOrder;
    }

    public BigDecimal getTRASSO() {
        return tRASSO;
    }

    public void setTRASSO(BigDecimal tRASSO) {
        this.tRASSO = tRASSO;
    }

    public String getValidationError() {
        return validationError;
    }

    public void setValidationError(String validationError) {
        this.validationError = validationError;
    }

    public String getReviewed() {
        return reviewed;
    }

    public void setReviewed(String reviewed) {
        this.reviewed = reviewed;
    }

    public String getScanCode() {
        return scanCode;
    }

    public void setScanCode(String scanCode) {
        this.scanCode = scanCode;
    }

    public String getUserInitials() {
        return userInitials;
    }

    public void setUserInitials(String userInitials) {
        this.userInitials = userInitials;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getVehicleLocation() {
        return vehicleLocation;
    }

    public void setVehicleLocation(String vehicleLocation) {
        this.vehicleLocation = vehicleLocation;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getDamageCode() {
        return damageCode;
    }

    public void setDamageCode(String damageCode) {
        this.damageCode = damageCode;
    }

    public String getPreDeliveryInspection() {
        return preDeliveryInspection;
    }

    public void setPreDeliveryInspection(String preDeliveryInspection) {
        this.preDeliveryInspection = preDeliveryInspection;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	public Date getScanDate() {
		return scanDate;
	}

	public void setScanDate(Date scanDate) {
		this.scanDate = scanDate;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Date getEstimatedInstallDate() {
		return estimatedInstallDate;
	}

	public void setEstimatedInstallDate(Date estimatedInstallDate) {
		this.estimatedInstallDate = estimatedInstallDate;
	}

	public Date getMaterialRequestDate() {
		return materialRequestDate;
	}

	public void setMaterialRequestDate(Date materialRequestDate) {
		this.materialRequestDate = materialRequestDate;
	}

	public static BigDecimal getNextID(EntityManager em) {
        TypedQuery<BigDecimal> q = em.createNamedQuery(FIND_MAX, BigDecimal.class);
        //always round to the whole number as .1 and .2 can messes up the next number
        BigDecimal prevID = q.getSingleResult().setScale(0, RoundingMode.DOWN);
        BigDecimal newID = prevID.add(BigDecimal.ONE);
        BigDecimal nextID = new BigDecimal(newID.toString()+".0");
        Logger.getLogger("FactoryOrderScan getNextID: ").log(Level.INFO, "Previous ID: {0} -> Next ID: {1}", new Object[]{prevID,nextID});
        return nextID;
    }

}
