
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
 *     &lt;extension base="{http://www.polito.it/schemas/nfv}NamedEntity">
 *       &lt;sequence>
 *         &lt;element name="host" type="{http://www.polito.it/schemas/nfv}NamedEntity" minOccurs="0"/>
 *         &lt;element name="vnf" type="{http://www.polito.it/schemas/nfv}NamedEntity"/>
 *         &lt;element name="nffg" type="{http://www.polito.it/schemas/nfv}NamedEntity" minOccurs="0"/>
 *         &lt;element name="reachableHosts" type="{http://www.polito.it/schemas/nfv}Hyperlink" minOccurs="0"/>
 *         &lt;element name="links" type="{http://www.polito.it/schemas/nfv}Hyperlink" minOccurs="0"/>
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
    "nffg",
    "reachableHosts",
    "links"
})
@XmlRootElement(name = "node")
public class Node
    extends NamedEntity
{

    protected NamedEntity host;
    @XmlElement(required = true)
    protected NamedEntity vnf;
    protected NamedEntity nffg;
    protected Hyperlink reachableHosts;
    protected Hyperlink links;

    /**
     * Gets the value of the host property.
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
     * Sets the value of the host property.
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
     * Gets the value of the vnf property.
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
     * Sets the value of the vnf property.
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
     * Gets the value of the nffg property.
     * 
     * @return
     *     possible object is
     *     {@link NamedEntity }
     *     
     */
    public NamedEntity getNffg() {
        return nffg;
    }

    /**
     * Sets the value of the nffg property.
     * 
     * @param value
     *     allowed object is
     *     {@link NamedEntity }
     *     
     */
    public void setNffg(NamedEntity value) {
        this.nffg = value;
    }

    /**
     * Gets the value of the reachableHosts property.
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
     * Sets the value of the reachableHosts property.
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
