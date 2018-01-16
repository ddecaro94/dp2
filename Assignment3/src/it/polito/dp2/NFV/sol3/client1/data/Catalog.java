
package it.polito.dp2.NFV.sol3.client1.data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{http://www.polito.it/schemas/nfv}vnf" maxOccurs="unbounded" minOccurs="0"/>
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
    "vnf"
})
@XmlRootElement(name = "catalog")
public class Catalog {

    @XmlElement(namespace = "http://www.polito.it/schemas/nfv")
    protected List<Vnf> vnf;

    /**
     * Gets the value of the vnf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vnf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVnf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Vnf }
     * 
     * 
     */
    public List<Vnf> getVnf() {
        if (vnf == null) {
            vnf = new ArrayList<Vnf>();
        }
        return this.vnf;
    }

}
