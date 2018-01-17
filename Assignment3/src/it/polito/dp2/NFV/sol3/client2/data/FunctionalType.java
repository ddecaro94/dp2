
package it.polito.dp2.NFV.sol3.client2.data;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FunctionalType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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
