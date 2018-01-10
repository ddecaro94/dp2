//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.10 alle 11:42:05 AM CET 
//


package it.polito.dp2.NFV.sol3.model;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Vnf complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Vnf">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.polito.it/schemas/nfvInfo}NamedEntity">
 *       &lt;attribute name="requiredMemory" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="requiredStorage" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="functionalType" use="required" type="{http://www.polito.it/schemas/nfvInfo}FunctionalType" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Vnf")
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
     * Recupera il valore della proprietà requiredMemory.
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
     * Imposta il valore della proprietà requiredMemory.
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
     * Recupera il valore della proprietà requiredStorage.
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
     * Imposta il valore della proprietà requiredStorage.
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
     * Recupera il valore della proprietà functionalType.
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
     * Imposta il valore della proprietà functionalType.
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
