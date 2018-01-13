//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.13 alle 01:16:31 AM CET 
//


package it.polito.dp2.NFV.sol3.model;

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
 *     &lt;extension base="{http://www.polito.it/schemas/nfv}NamedEntity">
 *       &lt;sequence>
 *         &lt;element name="host" type="{http://www.polito.it/schemas/nfv}NamedEntity"/>
 *         &lt;element name="vnf" type="{http://www.polito.it/schemas/nfv}NamedEntity"/>
 *         &lt;element name="reachableHosts" type="{http://www.polito.it/schemas/nfv}Hyperlink"/>
 *         &lt;element name="links" type="{http://www.polito.it/schemas/nfv}Hyperlink"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "host",
    "vnf",
    "reachableHosts",
    "links"
})
@XmlRootElement(name = "node")
public class Node
    extends NamedEntity
{

    @XmlElement(required = true)
    protected NamedEntity host;
    @XmlElement(required = true)
    protected NamedEntity vnf;
    @XmlElement(required = true)
    protected Hyperlink reachableHosts;
    @XmlElement(required = true)
    protected Hyperlink links;

    /**
     * Recupera il valore della proprietà host.
     * 
     * @return
     *     possible object is
     *     {@link NamedEntity }
     *     
     */
    public NamedEntity getHost() {
        return host;
    }

    /**
     * Imposta il valore della proprietà host.
     * 
     * @param value
     *     allowed object is
     *     {@link NamedEntity }
     *     
     */
    public void setHost(NamedEntity value) {
        this.host = value;
    }

    /**
     * Recupera il valore della proprietà vnf.
     * 
     * @return
     *     possible object is
     *     {@link NamedEntity }
     *     
     */
    public NamedEntity getVnf() {
        return vnf;
    }

    /**
     * Imposta il valore della proprietà vnf.
     * 
     * @param value
     *     allowed object is
     *     {@link NamedEntity }
     *     
     */
    public void setVnf(NamedEntity value) {
        this.vnf = value;
    }

    /**
     * Recupera il valore della proprietà reachableHosts.
     * 
     * @return
     *     possible object is
     *     {@link Hyperlink }
     *     
     */
    public Hyperlink getReachableHosts() {
        return reachableHosts;
    }

    /**
     * Imposta il valore della proprietà reachableHosts.
     * 
     * @param value
     *     allowed object is
     *     {@link Hyperlink }
     *     
     */
    public void setReachableHosts(Hyperlink value) {
        this.reachableHosts = value;
    }

    /**
     * Recupera il valore della proprietà links.
     * 
     * @return
     *     possible object is
     *     {@link Hyperlink }
     *     
     */
    public Hyperlink getLinks() {
        return links;
    }

    /**
     * Imposta il valore della proprietà links.
     * 
     * @param value
     *     allowed object is
     *     {@link Hyperlink }
     *     
     */
    public void setLinks(Hyperlink value) {
        this.links = value;
    }

}
