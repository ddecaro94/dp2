//
// Questo file Ŕ stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrÓ persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.03 alle 03:57:55 PM CET 
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
 * <p>Classe Java per HostType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="HostType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element ref="{http://www.polito.it/schemas/nfvInfo}availableMemory"/>
 *         &lt;element ref="{http://www.polito.it/schemas/nfvInfo}availableStorage"/>
 *         &lt;element ref="{http://www.polito.it/schemas/nfvInfo}maxVNFs"/>
 *         &lt;element ref="{http://www.polito.it/schemas/nfvInfo}deployedNodes"/>
 *         &lt;element ref="{http://www.polito.it/schemas/nfvInfo}connections"/>
 *       &lt;/all>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostType", propOrder = {

})
public class HostType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger availableMemory;
    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger availableStorage;
    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger maxVNFs;
    @XmlElement(required = true)
    protected DeployedNodes deployedNodes;
    @XmlElement(required = true)
    protected Connections connections;
    @XmlAttribute(name = "name", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String name;

    /**
     * Recupera il valore della proprietÓ availableMemory.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAvailableMemory() {
        return availableMemory;
    }

    /**
     * Imposta il valore della proprietÓ availableMemory.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAvailableMemory(BigInteger value) {
        this.availableMemory = value;
    }

    /**
     * Recupera il valore della proprietÓ availableStorage.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAvailableStorage() {
        return availableStorage;
    }

    /**
     * Imposta il valore della proprietÓ availableStorage.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAvailableStorage(BigInteger value) {
        this.availableStorage = value;
    }

    /**
     * Recupera il valore della proprietÓ maxVNFs.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxVNFs() {
        return maxVNFs;
    }

    /**
     * Imposta il valore della proprietÓ maxVNFs.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxVNFs(BigInteger value) {
        this.maxVNFs = value;
    }

    /**
     * Recupera il valore della proprietÓ deployedNodes.
     * 
     * @return
     *     possible object is
     *     {@link DeployedNodes }
     *     
     */
    public DeployedNodes getDeployedNodes() {
        return deployedNodes;
    }

    /**
     * Imposta il valore della proprietÓ deployedNodes.
     * 
     * @param value
     *     allowed object is
     *     {@link DeployedNodes }
     *     
     */
    public void setDeployedNodes(DeployedNodes value) {
        this.deployedNodes = value;
    }

    /**
     * Recupera il valore della proprietÓ connections.
     * 
     * @return
     *     possible object is
     *     {@link Connections }
     *     
     */
    public Connections getConnections() {
        return connections;
    }

    /**
     * Imposta il valore della proprietÓ connections.
     * 
     * @param value
     *     allowed object is
     *     {@link Connections }
     *     
     */
    public void setConnections(Connections value) {
        this.connections = value;
    }

    /**
     * Recupera il valore della proprietÓ name.
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
     * Imposta il valore della proprietÓ name.
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
