
package it.polito.dp2.NFV.sol3.client1.data;

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
 *     &lt;extension base="{http://www.polito.it/schemas/nfv}NamedEntity">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="requiredMemory" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="requiredStorage" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="functionalType" use="required" type="{http://www.polito.it/schemas/nfv}FunctionalType" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "vnf")
public class Vnf
    extends NamedEntity
{

    @XmlAttribute(name = "requiredMemory", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger requiredMemory;
    @XmlAttribute(name = "requiredStorage", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger requiredStorage;
    @XmlAttribute(name = "functionalType", required = true)
    protected FunctionalType functionalType;

    /**
     * Gets the value of the requiredMemory property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRequiredMemory() {
        return requiredMemory;
    }

    /**
     * Sets the value of the requiredMemory property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRequiredMemory(BigInteger value) {
        this.requiredMemory = value;
    }

    /**
     * Gets the value of the requiredStorage property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRequiredStorage() {
        return requiredStorage;
    }

    /**
     * Sets the value of the requiredStorage property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRequiredStorage(BigInteger value) {
        this.requiredStorage = value;
    }

    /**
     * Gets the value of the functionalType property.
     * 
     * @return
     *     possible object is
     *     {@link FunctionalType }
     *     
     */
    public FunctionalType getFunctionalType() {
        return functionalType;
    }

    /**
     * Sets the value of the functionalType property.
     * 
     * @param value
     *     allowed object is
     *     {@link FunctionalType }
     *     
     */
    public void setFunctionalType(FunctionalType value) {
        this.functionalType = value;
    }

}
