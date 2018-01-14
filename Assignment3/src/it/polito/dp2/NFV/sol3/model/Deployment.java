//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.13 alle 10:04:58 PM CET 
//


package it.polito.dp2.NFV.sol3.model;

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
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="time" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.polito.it/schemas/nfv}StatusType" minOccurs="0"/>
 *         &lt;element name="detail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nffg" type="{http://www.polito.it/schemas/nfv}NamedEntity"/>
 *         &lt;element name="preferences" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="node" type="{http://www.polito.it/schemas/nfv}NamedEntity"/>
 *                   &lt;element name="host" type="{http://www.polito.it/schemas/nfv}NamedEntity"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}token" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "time",
    "status",
    "detail",
    "nffg",
    "preferences"
})
@XmlRootElement(name = "deployment")
public class Deployment {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar time;
    @XmlElement(defaultValue = "NEW")
    @XmlSchemaType(name = "string")
    protected StatusType status;
    protected String detail;
    @XmlElement(required = true)
    protected NamedEntity nffg;
    protected List<Deployment.Preferences> preferences;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String id;

    /**
     * Recupera il valore della proprietà time.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTime() {
        return time;
    }

    /**
     * Imposta il valore della proprietà time.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTime(XMLGregorianCalendar value) {
        this.time = value;
    }

    /**
     * Recupera il valore della proprietà status.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Imposta il valore della proprietà status.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

    /**
     * Recupera il valore della proprietà detail.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Imposta il valore della proprietà detail.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetail(String value) {
        this.detail = value;
    }

    /**
     * Recupera il valore della proprietà nffg.
     * 
     * @return
     *     possible object is
     *     {@link NamedEntity }
     *     
     */
    public NamedEntity getNffg() {
        return nffg;
    }

    /**
     * Imposta il valore della proprietà nffg.
     * 
     * @param value
     *     allowed object is
     *     {@link NamedEntity }
     *     
     */
    public void setNffg(NamedEntity value) {
        this.nffg = value;
    }

    /**
     * Gets the value of the preferences property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the preferences property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPreferences().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Deployment.Preferences }
     * 
     * 
     */
    public List<Deployment.Preferences> getPreferences() {
        if (preferences == null) {
            preferences = new ArrayList<Deployment.Preferences>();
        }
        return this.preferences;
    }

    /**
     * Recupera il valore della proprietà id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Imposta il valore della proprietà id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
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
     *         &lt;element name="node" type="{http://www.polito.it/schemas/nfv}NamedEntity"/>
     *         &lt;element name="host" type="{http://www.polito.it/schemas/nfv}NamedEntity"/>
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
        "node",
        "host"
    })
    public static class Preferences {

        @XmlElement(required = true)
        protected NamedEntity node;
        @XmlElement(required = true)
        protected NamedEntity host;

        /**
         * Recupera il valore della proprietà node.
         * 
         * @return
         *     possible object is
         *     {@link NamedEntity }
         *     
         */
        public NamedEntity getNode() {
            return node;
        }

        /**
         * Imposta il valore della proprietà node.
         * 
         * @param value
         *     allowed object is
         *     {@link NamedEntity }
         *     
         */
        public void setNode(NamedEntity value) {
            this.node = value;
        }

        /**
         * Recupera il valore della proprietà host.
         * 
         * @return
         *     possible object is
         *     {@link NamedEntity }
         *     
         */
        public NamedEntity getHost() {
            return host;
        }

        /**
         * Imposta il valore della proprietà host.
         * 
         * @param value
         *     allowed object is
         *     {@link NamedEntity }
         *     
         */
        public void setHost(NamedEntity value) {
            this.host = value;
        }

    }

}
