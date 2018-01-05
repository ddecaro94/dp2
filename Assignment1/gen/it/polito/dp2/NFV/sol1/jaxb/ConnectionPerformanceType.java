//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.05 alle 12:13:44 PM CET 
//


package it.polito.dp2.NFV.sol1.jaxb;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ConnectionPerformanceType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ConnectionPerformanceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="2">
 *         &lt;element ref="{http://www.polito.it/schemas/nfvInfo}latency"/>
 *         &lt;element ref="{http://www.polito.it/schemas/nfvInfo}throughput"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConnectionPerformanceType", propOrder = {
    "latencyOrThroughput"
})
public class ConnectionPerformanceType {

    @XmlElements({
        @XmlElement(name = "latency", type = BigInteger.class),
        @XmlElement(name = "throughput", type = Float.class)
    })
    protected List<Comparable> latencyOrThroughput;

    /**
     * Gets the value of the latencyOrThroughput property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the latencyOrThroughput property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLatencyOrThroughput().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * {@link Float }
     * 
     * 
     */
    public List<Comparable> getLatencyOrThroughput() {
        if (latencyOrThroughput == null) {
            latencyOrThroughput = new ArrayList<Comparable>();
        }
        return this.latencyOrThroughput;
    }

}
