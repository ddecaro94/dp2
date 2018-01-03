//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.03 alle 03:57:55 PM CET 
//


package it.polito.dp2.NFV.sol1.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java per ConnectionType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ConnectionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element ref="{http://www.polito.it/schemas/nfvInfo}performance"/>
 *       &lt;/all>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="destination" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConnectionType", propOrder = {

})
public class ConnectionType {

    @XmlElement(required = true)
    protected ConnectionPerformanceType performance;
    @XmlAttribute(name = "name")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String name;
    @XmlAttribute(name = "destination", required = true)
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object destination;

    /**
     * Recupera il valore della propriet� performance.
     * 
     * @return
     *     possible object is
     *     {@link ConnectionPerformanceType }
     *     
     */
    public ConnectionPerformanceType getPerformance() {
        return performance;
    }

    /**
     * Imposta il valore della propriet� performance.
     * 
     * @param value
     *     allowed object is
     *     {@link ConnectionPerformanceType }
     *     
     */
    public void setPerformance(ConnectionPerformanceType value) {
        this.performance = value;
    }

    /**
     * Recupera il valore della propriet� name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il valore della propriet� name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Recupera il valore della propriet� destination.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getDestination() {
        return destination;
    }

    /**
     * Imposta il valore della propriet� destination.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setDestination(Object value) {
        this.destination = value;
    }

}
