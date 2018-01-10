//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.10 alle 11:42:05 AM CET 
//


package it.polito.dp2.NFV.sol3.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.polito.dp2.NFV.sol3.model package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Vnf_QNAME = new QName("http://www.polito.it/schemas/nfvInfo", "vnf");
    private final static QName _Host_QNAME = new QName("http://www.polito.it/schemas/nfvInfo", "host");
    private final static QName _Nffg_QNAME = new QName("http://www.polito.it/schemas/nfvInfo", "nffg");
    private final static QName _Node_QNAME = new QName("http://www.polito.it/schemas/nfvInfo", "node");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.polito.dp2.NFV.sol3.model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Node }
     * 
     */
    public Node createNode() {
        return new Node();
    }

    /**
     * Create an instance of {@link Nodes }
     * 
     */
    public Nodes createNodes() {
        return new Nodes();
    }

    /**
     * Create an instance of {@link NamedEntity }
     * 
     */
    public NamedEntity createNamedEntity() {
        return new NamedEntity();
    }

    /**
     * Create an instance of {@link Nffgs }
     * 
     */
    public Nffgs createNffgs() {
        return new Nffgs();
    }

    /**
     * Create an instance of {@link Catalog }
     * 
     */
    public Catalog createCatalog() {
        return new Catalog();
    }

    /**
     * Create an instance of {@link Vnf }
     * 
     */
    public Vnf createVnf() {
        return new Vnf();
    }

    /**
     * Create an instance of {@link Hosts }
     * 
     */
    public Hosts createHosts() {
        return new Hosts();
    }

    /**
     * Create an instance of {@link Host }
     * 
     */
    public Host createHost() {
        return new Host();
    }

    /**
     * Create an instance of {@link Links }
     * 
     */
    public Links createLinks() {
        return new Links();
    }

    /**
     * Create an instance of {@link Link }
     * 
     */
    public Link createLink() {
        return new Link();
    }

    /**
     * Create an instance of {@link Nffg }
     * 
     */
    public Nffg createNffg() {
        return new Nffg();
    }

    /**
     * Create an instance of {@link Connections }
     * 
     */
    public Connections createConnections() {
        return new Connections();
    }

    /**
     * Create an instance of {@link Connection }
     * 
     */
    public Connection createConnection() {
        return new Connection();
    }

    /**
     * Create an instance of {@link Nfv }
     * 
     */
    public Nfv createNfv() {
        return new Nfv();
    }

    /**
     * Create an instance of {@link Hyperlink }
     * 
     */
    public Hyperlink createHyperlink() {
        return new Hyperlink();
    }

    /**
     * Create an instance of {@link NamedRelationship }
     * 
     */
    public NamedRelationship createNamedRelationship() {
        return new NamedRelationship();
    }

    /**
     * Create an instance of {@link Relationship }
     * 
     */
    public Relationship createRelationship() {
        return new Relationship();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Vnf }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polito.it/schemas/nfvInfo", name = "vnf")
    public JAXBElement<Vnf> createVnf(Vnf value) {
        return new JAXBElement<Vnf>(_Vnf_QNAME, Vnf.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Host }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polito.it/schemas/nfvInfo", name = "host")
    public JAXBElement<Host> createHost(Host value) {
        return new JAXBElement<Host>(_Host_QNAME, Host.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Nffg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polito.it/schemas/nfvInfo", name = "nffg")
    public JAXBElement<Nffg> createNffg(Nffg value) {
        return new JAXBElement<Nffg>(_Nffg_QNAME, Nffg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Node }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polito.it/schemas/nfvInfo", name = "node")
    public JAXBElement<Node> createNode(Node value) {
        return new JAXBElement<Node>(_Node_QNAME, Node.class, null, value);
    }

}
