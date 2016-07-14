package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Past;
import javax.xml.bind.annotation.*;

/**
 * @author Daryl Davis
 */
@Entity
@Table(name = "FOXREF")
@NamedQueries({
    @NamedQuery(name = FactoryOrder.FIND_BY_VIN, 
        query = "SELECT i FROM AbstractFactoryOrder i "
            + "WHERE "
            + "i.installerPrefix LIKE :installerPrefix AND "
            + "i.serialNumber = :serialNumber AND "
            + "i.vINPrefix >= :vINPrefix AND "
            + "i.dltCde IN :dltCde "
            + "ORDER BY i.creationDate DESC "),
})
@XmlRootElement
public class FactoryOrder implements Serializable {    
    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_VIN = "AbstractFactoryOrder.findByVIN";

    public FactoryOrder() {
        super(); //temp for placement
    }

//- Class Elements for JAXB and JPA

    @Id
    @Column(name = "FOFO#", length = 10, nullable = false)
    protected String factoryOrderNum = " ";

    @Column(name = "FODCOD", length = 1, nullable = false)
    protected String dltCde = " ";

    @Column(name = "FOENDCST", precision = 5, scale = 0, nullable = false, insertable=false, updatable=false)
    protected BigDecimal endCustNum = BigDecimal.ZERO;

    @Column(name = "FOSO#", precision = 6, scale = 0, nullable = false, insertable=false, updatable=false)
    protected BigDecimal salesOrderNum = BigDecimal.ZERO;

    @Column(name = "FOCUS#", precision = 5, scale = 0, nullable = false, insertable=false, updatable=false)
    protected BigDecimal customerNumber = BigDecimal.ZERO;

    @Column(name = "FOSERI", length = 8, nullable = false)
    protected String serialNumber = " ";

    @Column(name = "FOVINPFX", length = 9, nullable = false)
    protected String vINPrefix = " ";

    @Column(name = "FODTPR", precision = 8, scale = 0, nullable = false)
    protected Date partialInstallDate = null;

    @Column(name = "FODTRC", precision = 8, scale = 0, nullable = false)
    protected Date receivedDate = null;

    @Column(name = "FODTIN", precision = 8, scale = 0, nullable = false)
    protected Date installedDate = null;

    @Column(name = "FODTSH", precision = 8, scale = 0, nullable = false)
    protected Date shippedDate = null;

    @Column(name = "FODTEM", precision = 8, scale = 0, nullable = false)
    protected Date estMfgDate = null;

    @Column(name = "FODTEI", precision = 8, scale = 0, nullable = false)
    protected Date estInstallDate = null;

    @Column(name = "FOIPFX", length = 3, nullable = false)
    protected String installerPrefix = " ";

    @Column(name = "FOCMT1", length = 20, nullable = false)
    private String lotLocation = " ";

    @Column(name = "FODTCRT", precision = 8, scale = 0, nullable = false)
    protected Date creationDate = null;
       
    
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

    public String getDltCde() {
        return dltCde;
    }

    public void setDltCde(String dltCde) {
        this.dltCde = dltCde;
    }
    
    public BigDecimal getEndCustNum() {
		return endCustNum;
	}

	public void setEndCustNum(BigDecimal endCustNum) {
		this.endCustNum = endCustNum;
	}

	public BigDecimal getSalesOrderNum() {
		return salesOrderNum;
	}

	public void setSalesOrderNum(BigDecimal salesOrderNum) {
		this.salesOrderNum = salesOrderNum;
	}

	public BigDecimal getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(BigDecimal customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getvINPrefix() {
		return vINPrefix;
	}

	public void setvINPrefix(String vINPrefix) {
		this.vINPrefix = vINPrefix;
	}

	public Date getPartialInstallDate() {
		return partialInstallDate;
	}

	public void setPartialInstallDate(Date partialInstallDate) {
		this.partialInstallDate = partialInstallDate;
	}

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

    @Override
	public String toString() {
		return "AbstractFactoryOrder [factoryOrderNum=" + factoryOrderNum + ", dltCde=" + dltCde + ", endCustNum="
				+ endCustNum + ", salesOrderNum=" + salesOrderNum + ", customerNumber=" + customerNumber
				+ ", serialNumber=" + serialNumber + ", vINPrefix=" + vINPrefix + ", partialInstallDate="
				+ partialInstallDate + ", receivedDate=" + receivedDate + ", installedDate=" + installedDate
				+ ", shippedDate=" + shippedDate + ", estMfgDate=" + estMfgDate + ", estInstallDate=" + estInstallDate
				+ ", installerPrefix=" + installerPrefix + ", lotLocation=" + lotLocation + ", creationDate="
				+ creationDate + "]";
	}

	@Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.factoryOrderNum != null ? this.factoryOrderNum.hashCode() : 0);
        hash = 67 * hash + (this.creationDate != null ? this.creationDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FactoryOrder other = (FactoryOrder) obj;
        if ((this.factoryOrderNum == null) ? (other.factoryOrderNum != null) : !this.factoryOrderNum.equals(other.factoryOrderNum)) {
            return false;
        }
        if (this.creationDate != other.creationDate && (this.creationDate == null || !this.creationDate.equals(other.creationDate))) {
            return false;
        }
        return true;
    }
    
    
}
