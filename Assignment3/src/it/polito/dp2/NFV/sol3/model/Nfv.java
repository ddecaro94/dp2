//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.01.10 at 05:03:39 PM CET 
//


package it.polito.dp2.NFV.sol3.model;

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
 *         &lt;element name="vnfCatalog" type="{http://www.polito.it/schemas/nfvInfo}Hyperlink"/>
 *         &lt;element name="hosts" type="{http://www.polito.it/schemas/nfvInfo}Hyperlink"/>
 *         &lt;element name="nffgs" type="{http://www.polito.it/schemas/nfvInfo}Hyperlink"/>
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
