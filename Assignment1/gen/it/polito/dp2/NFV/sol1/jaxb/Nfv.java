//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.03 alle 03:57:55 PM CET 
//


package it.polito.dp2.NFV.sol1.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element ref="{http://www.polito.it/schemas/nfvInfo}vnfCatalog"/>
 *         &lt;element ref="{http://www.polito.it/schemas/nfvInfo}hosts"/>
 *         &lt;element ref="{http://www.polito.it/schemas/nfvInfo}nffgs"/>
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
    protected Catalog vnfCatalog;
    @XmlElement(required = true)
    protected HostsType hosts;
    @XmlElement(required = true)
    protected Nffgs nffgs;

    /**
     * Recupera il valore della proprietà vnfCatalog.
     * 
     * @return
     *     possible object is
     *     {@link Catalog }
     *     
     */
    public Catalog getVnfCatalog() {
        return vnfCatalog;
    }

    /**
     * Imposta il valore della proprietà vnfCatalog.
     * 
     * @param value
     *     allowed object is
     *     {@link Catalog }
     *     
     */
    public void setVnfCatalog(Catalog value) {
        this.vnfCatalog = value;
    }

    /**
     * Recupera il valore della proprietà hosts.
     * 
     * @return
     *     possible object is
     *     {@link HostsType }
     *     
     */
    public HostsType getHosts() {
        return hosts;
    }

    /**
     * Imposta il valore della proprietà hosts.
     * 
     * @param value
     *     allowed object is
     *     {@link HostsType }
     *     
     */
    public void setHosts(HostsType value) {
        this.hosts = value;
    }

    /**
     * Recupera il valore della proprietà nffgs.
     * 
     * @return
     *     possible object is
     *     {@link Nffgs }
     *     
     */
    public Nffgs getNffgs() {
        return nffgs;
    }

    /**
     * Imposta il valore della proprietà nffgs.
     * 
     * @param value
     *     allowed object is
     *     {@link Nffgs }
     *     
     */
    public void setNffgs(Nffgs value) {
        this.nffgs = value;
    }

}
