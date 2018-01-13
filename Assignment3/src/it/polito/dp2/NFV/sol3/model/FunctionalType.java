//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.13 alle 05:08:18 PM CET 
//


package it.polito.dp2.NFV.sol3.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per FunctionalType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="FunctionalType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SPAM"/>
 *     &lt;enumeration value="FW"/>
 *     &lt;enumeration value="DPI"/>
 *     &lt;enumeration value="NAT"/>
 *     &lt;enumeration value="CACHE"/>
 *     &lt;enumeration value="WEB_SERVER"/>
 *     &lt;enumeration value="WEB_CLIENT"/>
 *     &lt;enumeration value="MAIL_SERVER"/>
 *     &lt;enumeration value="MAIL_CLIENT"/>
 *     &lt;enumeration value="VPN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FunctionalType")
@XmlEnum
public enum FunctionalType {

    SPAM,
    FW,
    DPI,
    NAT,
    CACHE,
    WEB_SERVER,
    WEB_CLIENT,
    MAIL_SERVER,
    MAIL_CLIENT,
    VPN;

    public String value() {
        return name();
    }

    public static FunctionalType fromValue(String v) {
        return valueOf(v);
    }

}
