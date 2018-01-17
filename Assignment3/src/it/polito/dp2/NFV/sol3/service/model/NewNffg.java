//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.17 alle 07:20:58 PM CET 
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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


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
 *         &lt;element name="nodes">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="node" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="vnf" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *                             &lt;element name="preferredHost" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="links">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="link" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="sourceNode" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *                             &lt;element name="destinationNode" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                           &lt;attribute name="requiredLatency" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" default="0" />
 *                           &lt;attribute name="requiredThroughput" type="{http://www.polito.it/schemas/nfv}nonNegativeFloat" default="0" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "nodes",
    "links"
})
@XmlRootElement(name = "newNffg")
public class NewNffg {

    @XmlElement(required = true)
    protected NewNffg.Nodes nodes;
    @XmlElement(required = true)
    protected NewNffg.Links links;
    @XmlAttribute(name = "name")
    protected String name;

    /**
     * Recupera il valore della proprietà nodes.
     * 
     * @return
     *     possible object is
     *     {@link NewNffg.Nodes }
     *     
     */
    public NewNffg.Nodes getNodes() {
        return nodes;
    }

    /**
     * Imposta il valore della proprietà nodes.
     * 
     * @param value
     *     allowed object is
     *     {@link NewNffg.Nodes }
     *     
     */
    public void setNodes(NewNffg.Nodes value) {
        this.nodes = value;
    }

    /**
     * Recupera il valore della proprietà links.
     * 
     * @return
     *     possible object is
     *     {@link NewNffg.Links }
     *     
     */
    public NewNffg.Links getLinks() {
        return links;
    }

    /**
     * Imposta il valore della proprietà links.
     * 
     * @param value
     *     allowed object is
     *     {@link NewNffg.Links }
     *     
     */
    public void setLinks(NewNffg.Links value) {
        this.links = value;
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
     *         &lt;element name="link" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="sourceNode" type="{http://www.w3.org/2001/XMLSchema}token"/>
     *                   &lt;element name="destinationNode" type="{http://www.w3.org/2001/XMLSchema}token"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
     *                 &lt;attribute name="requiredLatency" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" default="0" />
     *                 &lt;attribute name="requiredThroughput" type="{http://www.polito.it/schemas/nfv}nonNegativeFloat" default="0" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
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
        "link"
    })
    public static class Links {

        protected List<NewNffg.Links.Link> link;

        /**
         * Gets the value of the link property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the link property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getLink().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link NewNffg.Links.Link }
         * 
         * 
         */
        public List<NewNffg.Links.Link> getLink() {
            if (link == null) {
                link = new ArrayList<NewNffg.Links.Link>();
            }
            return this.link;
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
         *         &lt;element name="sourceNode" type="{http://www.w3.org/2001/XMLSchema}token"/>
         *         &lt;element name="destinationNode" type="{http://www.w3.org/2001/XMLSchema}token"/>
         *       &lt;/sequence>
         *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
         *       &lt;attribute name="requiredLatency" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" default="0" />
         *       &lt;attribute name="requiredThroughput" type="{http://www.polito.it/schemas/nfv}nonNegativeFloat" default="0" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "sourceNode",
            "destinationNode"
        })
        public static class Link {

            @XmlElement(required = true)
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String sourceNode;
            @XmlElement(required = true)
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String destinationNode;
            @XmlAttribute(name = "name", required = true)
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String name;
            @XmlAttribute(name = "requiredLatency")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger requiredLatency;
            @XmlAttribute(name = "requiredThroughput")
            protected Float requiredThroughput;

            /**
             * Recupera il valore della proprietà sourceNode.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSourceNode() {
                return sourceNode;
            }

            /**
             * Imposta il valore della proprietà sourceNode.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSourceNode(String value) {
                this.sourceNode = value;
            }

            /**
             * Recupera il valore della proprietà destinationNode.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDestinationNode() {
                return destinationNode;
            }

            /**
             * Imposta il valore della proprietà destinationNode.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDestinationNode(String value) {
                this.destinationNode = value;
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

            /**
             * Recupera il valore della proprietà requiredLatency.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getRequiredLatency() {
                if (requiredLatency == null) {
                    return new BigInteger("0");
                } else {
                    return requiredLatency;
                }
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
            public float getRequiredThroughput() {
                if (requiredThroughput == null) {
                    return  0.0F;
                } else {
                    return requiredThroughput;
                }
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
     *         &lt;element name="node" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="vnf" type="{http://www.w3.org/2001/XMLSchema}token"/>
     *                   &lt;element name="preferredHost" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
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
    public static class Nodes {

        protected List<NewNffg.Nodes.Node> node;

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
         * {@link NewNffg.Nodes.Node }
         * 
         * 
         */
        public List<NewNffg.Nodes.Node> getNode() {
            if (node == null) {
                node = new ArrayList<NewNffg.Nodes.Node>();
            }
            return this.node;
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
         *         &lt;element name="vnf" type="{http://www.w3.org/2001/XMLSchema}token"/>
         *         &lt;element name="preferredHost" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "vnf",
            "preferredHost"
        })
        public static class Node {

            @XmlElement(required = true)
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String vnf;
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String preferredHost;
            @XmlAttribute(name = "name", required = true)
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String name;

            /**
             * Recupera il valore della proprietà vnf.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVnf() {
                return vnf;
            }

            /**
             * Imposta il valore della proprietà vnf.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVnf(String value) {
                this.vnf = value;
            }

            /**
             * Recupera il valore della proprietà preferredHost.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPreferredHost() {
                return preferredHost;
            }

            /**
             * Imposta il valore della proprietà preferredHost.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPreferredHost(String value) {
                this.preferredHost = value;
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

    }

}
