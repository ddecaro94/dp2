//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.16 alle 02:59:32 AM CET 
//


package it.polito.dp2.NFV.sol3.service.model;

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
     * Recupera il valore della proprietà vnfCatalog.
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
     * Imposta il valore della proprietà vnfCatalog.
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
     * Recupera il valore della proprietà hosts.
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
     * Imposta il valore della proprietà hosts.
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
     * Recupera il valore della proprietà nffgs.
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
     * Imposta il valore della proprietà nffgs.
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
