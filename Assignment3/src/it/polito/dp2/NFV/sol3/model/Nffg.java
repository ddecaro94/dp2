//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.14 alle 02:50:43 AM CET 
//


package it.polito.dp2.NFV.sol3.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="deployTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="nodes" type="{http://www.polito.it/schemas/nfv}Hyperlink"/>
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
    "deployTime",
    "nodes",
    "links"
})
@XmlRootElement(name = "nffg")
public class Nffg
    extends NamedEntity
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar deployTime;
    @XmlElement(required = true)
    protected Hyperlink nodes;
    @XmlElement(required = true)
    protected Hyperlink links;

    /**
     * Recupera il valore della proprietà deployTime.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDeployTime() {
        return deployTime;
    }

    /**
     * Imposta il valore della proprietà deployTime.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDeployTime(XMLGregorianCalendar value) {
        this.deployTime = value;
    }

    /**
     * Recupera il valore della proprietà nodes.
     * 
     * @return
     *     possible object is
     *     {@link Hyperlink }
     *     
     */
    public Hyperlink getNodes() {
        return nodes;
    }

    /**
     * Imposta il valore della proprietà nodes.
     * 
     * @param value
     *     allowed object is
     *     {@link Hyperlink }
     *     
     */
    public void setNodes(Hyperlink value) {
        this.nodes = value;
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
