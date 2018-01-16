
package it.polito.dp2.NFV.sol3.client1.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="vnfCatalog" type="{http://www.polito.it/schemas/nfv}Hyperlink"/>
 *         &lt;element name="hosts" type="{http://www.polito.it/schemas/nfv}Hyperlink"/>
 *         &lt;element name="nffgs" type="{http://www.polito.it/schemas/nfv}Hyperlink"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "nfv")
public class Nfv {

    @XmlElement(required = true)
    protected Hyperlink vnfCatalog;
    @XmlElement(required = true)
    protected Hyperlink hosts;
    @XmlElement(required = true)
    protected Hyperlink nffgs;

    /**
     * Gets the value of the vnfCatalog property.
     * 
     * @return
     *     possible object is
     *     {@link Hyperlink }
     *     
     */
    public Hyperlink getVnfCatalog() {
        return vnfCatalog;
    }

    /**
     * Sets the value of the vnfCatalog property.
     * 
     * @param value
     *     allowed object is
     *     {@link Hyperlink }
     *     
     */
    public void setVnfCatalog(Hyperlink value) {
        this.vnfCatalog = value;
    }

    /**
     * Gets the value of the hosts property.
     * 
     * @return
     *     possible object is
     *     {@link Hyperlink }
     *     
     */
    public Hyperlink getHosts() {
        return hosts;
    }

    /**
     * Sets the value of the hosts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Hyperlink }
     *     
     */
    public void setHosts(Hyperlink value) {
        this.hosts = value;
    }

    /**
     * Gets the value of the nffgs property.
     * 
     * @return
     *     possible object is
     *     {@link Hyperlink }
     *     
     */
    public Hyperlink getNffgs() {
        return nffgs;
    }

    /**
     * Sets the value of the nffgs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Hyperlink }
     *     
     */
    public void setNffgs(Hyperlink value) {
        this.nffgs = value;
    }

}
