//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.19 alle 03:53:38 AM CET 
//


package it.polito.dp2.NFV.sol3.service.model;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.polito.it/schemas/nfv}NamedRelationship">
 *       &lt;attribute name="requiredLatency" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="requiredThroughput" type="{http://www.polito.it/schemas/nfv}nonNegativeFloat" />
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
     * Recupera il valore della proprietà requiredLatency.
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
     * Imposta il valore della proprietà requiredLatency.
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
     * Recupera il valore della proprietà requiredThroughput.
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
     * Imposta il valore della proprietà requiredThroughput.
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
