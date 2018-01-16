
package it.polito.dp2.NFV.sol3.client1.data;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Connection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Connection">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.polito.it/schemas/nfv}Relationship">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="latency" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="throughput" use="required" type="{http://www.w3.org/2001/XMLSchema}float" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Connection")
public class Connection
    extends Relationship
{

    @XmlAttribute(name = "latency", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger latency;
    @XmlAttribute(name = "throughput", required = true)
    protected float throughput;

    /**
     * Gets the value of the latency property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLatency() {
        return latency;
    }

    /**
     * Sets the value of the latency property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLatency(BigInteger value) {
        this.latency = value;
    }

    /**
     * Gets the value of the throughput property.
     * 
     */
    public float getThroughput() {
        return throughput;
    }

    /**
     * Sets the value of the throughput property.
     * 
     */
    public void setThroughput(float value) {
        this.throughput = value;
    }

}
