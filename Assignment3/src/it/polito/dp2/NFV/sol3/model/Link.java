//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.11 alle 11:27:59 AM CET 
//


package it.polito.dp2.NFV.sol3.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Link complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Link">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.polito.it/schemas/nfvInfo}NamedRelationship">
 *       &lt;attribute name="requiredLatency" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="requiredThroughput" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Link")
public class Link
    extends NamedRelationship
{

    @XmlAttribute(name = "requiredLatency")
    protected String requiredLatency;
    @XmlAttribute(name = "requiredThroughput")
    protected String requiredThroughput;

    /**
     * Recupera il valore della proprietà requiredLatency.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequiredLatency() {
        return requiredLatency;
    }

    /**
     * Imposta il valore della proprietà requiredLatency.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequiredLatency(String value) {
        this.requiredLatency = value;
    }

    /**
     * Recupera il valore della proprietà requiredThroughput.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequiredThroughput() {
        return requiredThroughput;
    }

    /**
     * Imposta il valore della proprietà requiredThroughput.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequiredThroughput(String value) {
        this.requiredThroughput = value;
    }

}
