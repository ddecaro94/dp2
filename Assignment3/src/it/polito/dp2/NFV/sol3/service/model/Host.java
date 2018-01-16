//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.16 alle 02:59:32 AM CET 
//


package it.polito.dp2.NFV.sol3.service.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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
 *     &lt;extension base="{http://www.polito.it/schemas/nfv}NamedEntity">
 *       &lt;sequence>
 *         &lt;element name="deployedNodes">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="node" type="{http://www.polito.it/schemas/nfv}NamedEntity" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="connections" type="{http://www.polito.it/schemas/nfv}Hyperlink"/>
 *       &lt;/sequence>
 *       &lt;attribute name="availableMemory" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="availableStorage" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="allocatedMemory" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="allocatedStorage" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="maxVNFs" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "deployedNodes",
    "connections"
})
@XmlRootElement(name = "host")
public class Host
    extends NamedEntity
{

    @XmlElement(required = true)
    protected Host.DeployedNodes deployedNodes;
    @XmlElement(required = true)
    protected Hyperlink connections;
    @XmlAttribute(name = "availableMemory", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger availableMemory;
    @XmlAttribute(name = "availableStorage", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger availableStorage;
    @XmlAttribute(name = "allocatedMemory", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger allocatedMemory;
    @XmlAttribute(name = "allocatedStorage", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger allocatedStorage;
    @XmlAttribute(name = "maxVNFs", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger maxVNFs;

    /**
     * Recupera il valore della proprietà deployedNodes.
     * 
     * @return
     *     possible object is
     *     {@link Host.DeployedNodes }
     *     
     */
    public Host.DeployedNodes getDeployedNodes() {
        return deployedNodes;
    }

    /**
     * Imposta il valore della proprietà deployedNodes.
     * 
     * @param value
     *     allowed object is
     *     {@link Host.DeployedNodes }
     *     
     */
    public void setDeployedNodes(Host.DeployedNodes value) {
        this.deployedNodes = value;
    }

    /**
     * Recupera il valore della proprietà connections.
     * 
     * @return
     *     possible object is
     *     {@link Hyperlink }
     *     
     */
    public Hyperlink getConnections() {
        return connections;
    }

    /**
     * Imposta il valore della proprietà connections.
     * 
     * @param value
     *     allowed object is
     *     {@link Hyperlink }
     *     
     */
    public void setConnections(Hyperlink value) {
        this.connections = value;
    }

    /**
     * Recupera il valore della proprietà availableMemory.
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
     * Imposta il valore della proprietà availableMemory.
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
     * Recupera il valore della proprietà availableStorage.
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
     * Imposta il valore della proprietà availableStorage.
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
     * Recupera il valore della proprietà allocatedMemory.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAllocatedMemory() {
        return allocatedMemory;
    }

    /**
     * Imposta il valore della proprietà allocatedMemory.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAllocatedMemory(BigInteger value) {
        this.allocatedMemory = value;
    }

    /**
     * Recupera il valore della proprietà allocatedStorage.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAllocatedStorage() {
        return allocatedStorage;
    }

    /**
     * Imposta il valore della proprietà allocatedStorage.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAllocatedStorage(BigInteger value) {
        this.allocatedStorage = value;
    }

    /**
     * Recupera il valore della proprietà maxVNFs.
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
     * Imposta il valore della proprietà maxVNFs.
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
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="node" type="{http://www.polito.it/schemas/nfv}NamedEntity" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "node"
    })
    public static class DeployedNodes {

        protected List<NamedEntity> node;

        /**
         * Gets the value of the node property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the node property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getNode().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link NamedEntity }
         * 
         * 
         */
        public List<NamedEntity> getNode() {
            if (node == null) {
                node = new ArrayList<NamedEntity>();
            }
            return this.node;
        }

    }

}
