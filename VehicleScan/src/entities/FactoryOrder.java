package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Daryl Davis
 ***
 */
@Entity
@Table(name = "tblCVI_VIN_Master")
@NamedQueries({
    @NamedQuery(name = FactoryOrder.FIND_BY_VIN, 
        query = "SELECT i FROM FactoryOrder i WHERE (i.installerPrefix LIKE :installerPrefix OR i.installerPrefix is null) AND i.vin = :vin ORDER BY i.creationDate DESC "),
})
@XmlRootElement
public class FactoryOrder implements Serializable {    
    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_VIN = "FactoryOrder.findByVIN";

    public FactoryOrder() {
        super(); //temp for placement
    }

//- Class Elements for JAXB and JPA

//    @Id
    @Column(name = "FO_Number")
    protected String factoryOrderNum = "";

//    @Column(name = "FODCOD")
//    protected String dltCde = " ";

//    @Column(name = "Customer", insertable=false, updatable=false)
//    protected String endCustNum = BigDecimal.ZERO;

    @Column(name = "OrderNumber", insertable=false, updatable=false)
    protected BigDecimal salesOrderNum = BigDecimal.ZERO;

//    @Column(name = "FOCUS#", insertable=false, updatable=false)
//    protected BigDecimal customerNumber = BigDecimal.ZERO;

//    @Column(name = "FOSERI", length = 8, nullable = false)
    @Transient
    protected String serialNumber = " ";

//    @Column(name = "FOVINPFX", length = 9, nullable = false)
    @Transient
    protected String vINPrefix = " ";
    
    @Id
    @Column(name = "VIN")
    protected String vin = "";

//    @Column(name = "FODTPR")
//    @Temporal(TemporalType.DATE)
//    protected Date partialInstallDate = null;

    @Column(name = "dtmVehicleReceived")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date receivedDate = null;

    @Column(name = "dtmWorkCompleted")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date installedDate = null;

    @Column(name = "dtmExitCompound")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date shippedDate = null;

    @Column(name = "dtmTPW")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date estMfgDate = null;

    @Column(name = "dtmScheduledForUpfitOnDate")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date estInstallDate = null;

    @Column(name = "CompoundCode")
    protected String installerPrefix = "";

    @Column(name = "BayLocation")
    protected String lotLocation = "";

    @Column(name = "dtmCreated")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date creationDate = null;
    
    @Column(name = "dtmRecordClosed")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date recordClosedDate = null;
       
    
    public FactoryOrder(String factoryOrderNum) {
        this.factoryOrderNum = factoryOrderNum;
    }

    @XmlAttribute
    public String getFactoryOrderNum() {
        return factoryOrderNum;
    }
    
    public void setFactoryOrderNum(String factoryOrderNum) {
        this.factoryOrderNum = factoryOrderNum;
    }

//    public String getDltCde() {
//        return dltCde;
//    }
//
//    public void setDltCde(String dltCde) {
//        this.dltCde = dltCde;
//    }
   
//    public BigDecimal getEndCustNum() {
//		return endCustNum;
//	}
//
//	public void setEndCustNum(BigDecimal endCustNum) {
//		this.endCustNum = endCustNum;
//	}

	public BigDecimal getSalesOrderNum() {
		return salesOrderNum;
	}

	public void setSalesOrderNum(BigDecimal salesOrderNum) {
		this.salesOrderNum = salesOrderNum;
	}

//	public BigDecimal getCustomerNumber() {
//		return customerNumber;
//	}
//
//	public void setCustomerNumber(BigDecimal customerNumber) {
//		this.customerNumber = customerNumber;
//	}

	public String getSerialNumber() {
		if (vin != null && vin.length() == 17) {
			return vin.substring(9, 17);
//			return serialNumber;
		} else if (vin != null && vin.length() > 9) {
			return vin.substring(9, vin.length());
		} else {
			return ""; //12345678912345678
		}
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
		this.vin = getvINPrefix() + serialNumber;
	}

	public String getvINPrefix() {
		if (vin != null && vin.length() > 9) {
			return vin.substring(0, 9);
// dnw cu			return vin.substring(1, 8);
		} else {
			return vin;
		}
//		return vINPrefix;
// 99cu99
	}
	
	@XmlElement(name="vinprefix")
	public void setvINPrefix(String vINPrefix) {
		this.vINPrefix = vINPrefix;
// dnw cu		this.vINPrefix = getvINPrefix();
		this.vin = vINPrefix + getSerialNumber();
// dnw cu		this.vin = getvINPrefix() + getSerialNumber();
	}

//	public Date getPartialInstallDate() {
//		return partialInstallDate;
//	}
//
//	public void setPartialInstallDate(Date partialInstallDate) {
//		this.partialInstallDate = partialInstallDate;
//	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Date getInstalledDate() {
		return installedDate;
	}

	public void setInstalledDate(Date installedDate) {
		this.installedDate = installedDate;
	}

	public Date getShippedDate() {
		return shippedDate;
	}

	public void setShippedDate(Date shippedDate) {
		this.shippedDate = shippedDate;
	}

	public Date getEstMfgDate() {
		return estMfgDate;
	}

	public void setEstMfgDate(Date estMfgDate) {
		this.estMfgDate = estMfgDate;
	}

	public Date getEstInstallDate() {
		return estInstallDate;
	}

	public void setEstInstallDate(Date estInstallDate) {
		this.estInstallDate = estInstallDate;
	}

	public String getInstallerPrefix() {
		return installerPrefix;
	}

	public void setInstallerPrefix(String installerPrefix) {
		this.installerPrefix = installerPrefix;
	}

	public String getLotLocation() {
		return lotLocation;
	}

	public void setLotLocation(String lotLocation) {
		this.lotLocation = lotLocation;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getRecordClosedDate() {
		return recordClosedDate;
	}

	public void setRecordClosedDate(Date recordClosedDate) {
		this.recordClosedDate = recordClosedDate;
	}
    
}
