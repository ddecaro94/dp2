//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.03 alle 03:57:55 PM CET 
//


package it.polito.dp2.NFV.sol1.jaxb;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *       &lt;sequence>
 *         &lt;element ref="{http://www.polito.it/schemas/nfvInfo}latency"/>
 *         &lt;element ref="{http://www.polito.it/schemas/nfvInfo}throughput"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConnectionPerformanceType", propOrder = {
    "latency",
    "throughput"
})
public class ConnectionPerformanceType {

    protected int latency;
    @XmlElement(required = true)
    protected BigDecimal throughput;

    /**
     * Recupera il valore della proprietà latency.
     * 
     */
    public int getLatency() {
        return latency;
    }

    /**
     * Imposta il valore della proprietà latency.
     * 
     */
    public void setLatency(int value) {
        this.latency = value;
    }

    /**
     * Recupera il valore della proprietà throughput.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getThroughput() {
        return throughput;
    }

    /**
     * Imposta il valore della proprietà throughput.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setThroughput(BigDecimal value) {
        this.throughput = value;
    }

}
