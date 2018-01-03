//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.01.03 alle 03:57:55 PM CET 
//


package it.polito.dp2.NFV.sol1.jaxb;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.polito.dp2.NFV.sol1.jaxb package. 
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

    private final static QName _Host_QNAME = new QName("http://www.polito.it/schemas/nfvInfo", "host");
    private final static QName _MaxVNFs_QNAME = new QName("http://www.polito.it/schemas/nfvInfo", "maxVNFs");
    private final static QName _VnfCatalog_QNAME = new QName("http://www.polito.it/schemas/nfvInfo", "vnfCatalog");
    private final static QName _Connection_QNAME = new QName("http://www.polito.it/schemas/nfvInfo", "connection");
    private final static QName _Throughput_QNAME = new QName("http://www.polito.it/schemas/nfvInfo", "throughput");
    private final static QName _DeployTime_QNAME = new QName("http://www.polito.it/schemas/nfvInfo", "deployTime");
    private final static QName _AvailableMemory_QNAME = new QName("http://www.polito.it/schemas/nfvInfo", "availableMemory");
    private final static QName _AvailableStorage_QNAME = new QName("http://www.polito.it/schemas/nfvInfo", "availableStorage");
    private final static QName _Node_QNAME = new QName("http://www.polito.it/schemas/nfvInfo", "node");
    private final static QName _Performance_QNAME = new QName("http://www.polito.it/schemas/nfvInfo", "performance");
    private final static QName _Vnf_QNAME = new QName("http://www.polito.it/schemas/nfvInfo", "vnf");
    private final static QName _Link_QNAME = new QName("http://www.polito.it/schemas/nfvInfo", "link");
    private final static QName _Destination_QNAME = new QName("http://www.polito.it/schemas/nfvInfo", "destination");
    private final static QName _Nffg_QNAME = new QName("http://www.polito.it/schemas/nfvInfo", "nffg");
    private final static QName _RequiredMemory_QNAME = new QName("http://www.polito.it/schemas/nfvInfo", "requiredMemory");
    private final static QName _Hosts_QNAME = new QName("http://www.polito.it/schemas/nfvInfo", "hosts");
    private final static QName _Latency_QNAME = new QName("http://www.polito.it/schemas/nfvInfo", "latency");
    private final static QName _RequiredStorage_QNAME = new QName("http://www.polito.it/schemas/nfvInfo", "requiredStorage");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.polito.dp2.NFV.sol1.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Nffgs }
     * 
     */
    public Nffgs createNffgs() {
        return new Nffgs();
    }

    /**
     * Create an instance of {@link NffgType }
     * 
     */
    public NffgType createNffgType() {
        return new NffgType();
    }

    /**
     * Create an instance of {@link HostsType }
     * 
     */
    public HostsType createHostsType() {
        return new HostsType();
    }

    /**
     * Create an instance of {@link ConnectionType }
     * 
     */
    public ConnectionType createConnectionType() {
        return new ConnectionType();
    }

    /**
     * Create an instance of {@link DeployedNodes }
     * 
     */
    public DeployedNodes createDeployedNodes() {
        return new DeployedNodes();
    }

    /**
     * Create an instance of {@link DeployedNode }
     * 
     */
    public DeployedNode createDeployedNode() {
        return new DeployedNode();
    }

    /**
     * Create an instance of {@link VnfType }
     * 
     */
    public VnfType createVnfType() {
        return new VnfType();
    }

    /**
     * Create an instance of {@link NodeType }
     * 
     */
    public NodeType createNodeType() {
        return new NodeType();
    }

    /**
     * Create an instance of {@link ConnectionPerformanceType }
     * 
     */
    public ConnectionPerformanceType createConnectionPerformanceType() {
        return new ConnectionPerformanceType();
    }

    /**
     * Create an instance of {@link Nodes }
     * 
     */
    public Nodes createNodes() {
        return new Nodes();
    }

    /**
     * Create an instance of {@link HostType }
     * 
     */
    public HostType createHostType() {
        return new HostType();
    }

    /**
     * Create an instance of {@link Catalog }
     * 
     */
    public Catalog createCatalog() {
        return new Catalog();
    }

    /**
     * Create an instance of {@link Connections }
     * 
     */
    public Connections createConnections() {
        return new Connections();
    }

    /**
     * Create an instance of {@link Nfv }
     * 
     */
    public Nfv createNfv() {
        return new Nfv();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HostType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polito.it/schemas/nfvInfo", name = "host")
    public JAXBElement<HostType> createHost(HostType value) {
        return new JAXBElement<HostType>(_Host_QNAME, HostType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polito.it/schemas/nfvInfo", name = "maxVNFs")
    public JAXBElement<BigInteger> createMaxVNFs(BigInteger value) {
        return new JAXBElement<BigInteger>(_MaxVNFs_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Catalog }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polito.it/schemas/nfvInfo", name = "vnfCatalog")
    public JAXBElement<Catalog> createVnfCatalog(Catalog value) {
        return new JAXBElement<Catalog>(_VnfCatalog_QNAME, Catalog.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConnectionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polito.it/schemas/nfvInfo", name = "connection")
    public JAXBElement<ConnectionType> createConnection(ConnectionType value) {
        return new JAXBElement<ConnectionType>(_Connection_QNAME, ConnectionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polito.it/schemas/nfvInfo", name = "throughput")
    public JAXBElement<BigDecimal> createThroughput(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Throughput_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polito.it/schemas/nfvInfo", name = "deployTime")
    public JAXBElement<XMLGregorianCalendar> createDeployTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DeployTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polito.it/schemas/nfvInfo", name = "availableMemory")
    public JAXBElement<BigInteger> createAvailableMemory(BigInteger value) {
        return new JAXBElement<BigInteger>(_AvailableMemory_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polito.it/schemas/nfvInfo", name = "availableStorage")
    public JAXBElement<BigInteger> createAvailableStorage(BigInteger value) {
        return new JAXBElement<BigInteger>(_AvailableStorage_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polito.it/schemas/nfvInfo", name = "node")
    public JAXBElement<NodeType> createNode(NodeType value) {
        return new JAXBElement<NodeType>(_Node_QNAME, NodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConnectionPerformanceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polito.it/schemas/nfvInfo", name = "performance")
    public JAXBElement<ConnectionPerformanceType> createPerformance(ConnectionPerformanceType value) {
        return new JAXBElement<ConnectionPerformanceType>(_Performance_QNAME, ConnectionPerformanceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VnfType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polito.it/schemas/nfvInfo", name = "vnf")
    public JAXBElement<VnfType> createVnf(VnfType value) {
        return new JAXBElement<VnfType>(_Vnf_QNAME, VnfType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConnectionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polito.it/schemas/nfvInfo", name = "link")
    public JAXBElement<ConnectionType> createLink(ConnectionType value) {
        return new JAXBElement<ConnectionType>(_Link_QNAME, ConnectionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polito.it/schemas/nfvInfo", name = "destination")
    @XmlIDREF
    public JAXBElement<Object> createDestination(Object value) {
        return new JAXBElement<Object>(_Destination_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NffgType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polito.it/schemas/nfvInfo", name = "nffg")
    public JAXBElement<NffgType> createNffg(NffgType value) {
        return new JAXBElement<NffgType>(_Nffg_QNAME, NffgType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polito.it/schemas/nfvInfo", name = "requiredMemory")
    public JAXBElement<BigInteger> createRequiredMemory(BigInteger value) {
        return new JAXBElement<BigInteger>(_RequiredMemory_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HostsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polito.it/schemas/nfvInfo", name = "hosts")
    public JAXBElement<HostsType> createHosts(HostsType value) {
        return new JAXBElement<HostsType>(_Hosts_QNAME, HostsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polito.it/schemas/nfvInfo", name = "latency")
    public JAXBElement<Integer> createLatency(Integer value) {
        return new JAXBElement<Integer>(_Latency_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.polito.it/schemas/nfvInfo", name = "requiredStorage")
    public JAXBElement<BigInteger> createRequiredStorage(BigInteger value) {
        return new JAXBElement<BigInteger>(_RequiredStorage_QNAME, BigInteger.class, null, value);
    }

}
