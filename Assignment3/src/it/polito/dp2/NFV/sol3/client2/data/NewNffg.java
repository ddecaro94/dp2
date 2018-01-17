
package it.polito.dp2.NFV.sol3.client2.data;

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
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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
 *                           &lt;attribute name="requiredLatency" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                           &lt;attribute name="requiredThroughput" type="{http://www.w3.org/2001/XMLSchema}float" />
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
     * Gets the value of the nodes property.
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
     * Sets the value of the nodes property.
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
     * Gets the value of the links property.
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
     * Sets the value of the links property.
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
     * Gets the value of the name property.
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
     * Sets the value of the name property.
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
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
     *                 &lt;attribute name="requiredLatency" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *                 &lt;attribute name="requiredThroughput" type="{http://www.w3.org/2001/XMLSchema}float" />
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

        @XmlElement(nillable = true)
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
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
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
         *       &lt;attribute name="requiredLatency" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
         *       &lt;attribute name="requiredThroughput" type="{http://www.w3.org/2001/XMLSchema}float" />
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
             * Gets the value of the sourceNode property.
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
             * Sets the value of the sourceNode property.
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
             * Gets the value of the destinationNode property.
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
             * Sets the value of the destinationNode property.
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
             * Gets the value of the name property.
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
             * Sets the value of the name property.
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
             * Gets the value of the requiredLatency property.
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
             * Sets the value of the requiredLatency property.
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
             * Gets the value of the requiredThroughput property.
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
             * Sets the value of the requiredThroughput property.
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
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

        @XmlElement(nillable = true)
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
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
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
             * Gets the value of the vnf property.
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
             * Sets the value of the vnf property.
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
             * Gets the value of the preferredHost property.
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
             * Sets the value of the preferredHost property.
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
             * Gets the value of the name property.
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
             * Sets the value of the name property.
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
