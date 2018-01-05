//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.05 alle 12:13:44 PM CET 
//


package it.polito.dp2.NFV.sol1.jaxb;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java per VnfType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="VnfType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element ref="{http://www.polito.it/schemas/nfvInfo}requiredMemory"/>
 *         &lt;element ref="{http://www.polito.it/schemas/nfvInfo}requiredStorage"/>
 *       &lt;/all>
 *       &lt;attribute name="functionalType" use="required" type="{http://www.polito.it/schemas/nfvInfo}FunctionalType" />
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VnfType", propOrder = {

})
public class VnfType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger requiredMemory;
    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger requiredStorage;
    @XmlAttribute(name = "functionalType", required = true)
    protected FunctionalType functionalType;
    @XmlAttribute(name = "name", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String name;

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

    /**
     * Recupera il valore della proprietà name.
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
     * Imposta il valore della proprietà name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
