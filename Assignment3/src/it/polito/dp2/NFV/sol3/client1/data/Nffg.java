
package it.polito.dp2.NFV.sol3.client1.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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
     * Gets the value of the deployTime property.
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
     * Sets the value of the deployTime property.
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
     * Gets the value of the nodes property.
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
     * Sets the value of the nodes property.
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
     * Gets the value of the links property.
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
     * Sets the value of the links property.
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
