
package it.polito.dp2.NFV.sol3.client2.data;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.polito.it/schemas/nfv}NamedRelationship">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="requiredLatency" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="requiredThroughput" type="{http://www.w3.org/2001/XMLSchema}float" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "link")
public class Link
    extends NamedRelationship
{

    @XmlAttribute(name = "requiredLatency")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger requiredLatency;
    @XmlAttribute(name = "requiredThroughput")
    protected Float requiredThroughput;

    /**
     * Gets the value of the requiredLatency property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRequiredLatency() {
        return requiredLatency;
    }

    /**
     * Sets the value of the requiredLatency property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRequiredLatency(BigInteger value) {
        this.requiredLatency = value;
    }

    /**
     * Gets the value of the requiredThroughput property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getRequiredThroughput() {
        return requiredThroughput;
    }

    /**
     * Sets the value of the requiredThroughput property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setRequiredThroughput(Float value) {
        this.requiredThroughput = value;
    }

}
