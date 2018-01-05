package it.polito.dp2.NFV.sol1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import it.polito.dp2.NFV.ConnectionPerformanceReader;
import it.polito.dp2.NFV.HostReader;
import it.polito.dp2.NFV.LinkReader;
import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.NfvReaderException;
import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.VNFTypeReader;
import it.polito.dp2.NFV.sol1.jaxb.*;

public class NfvInfoSerializer {
	private it.polito.dp2.NFV.NfvReader monitor;
	private DateFormat dateFormat;
	private Nfv nfv;
	private Map<String, HostType> hosts;
	private Map<String, NodeType> nodes;
	private HashMap<String, VnfType> catalog;

	
	/**
	 * Default constructror
	 * @throws NfvReaderException 
	 */
	public NfvInfoSerializer() throws NfvReaderException {
		it.polito.dp2.NFV.NfvReaderFactory factory = NfvReaderFactory.newInstance();
		monitor = factory.newNfvReader();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		nfv = new Nfv();
		hosts = new HashMap<String, HostType>();
		nodes = new HashMap<String, NodeType>();
		catalog = new HashMap<String, VnfType>();
	}
	
	public NfvInfoSerializer(NfvReader monitor) {
		super();
		this.monitor = monitor;
		dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		nfv = new Nfv();
		catalog = new HashMap<String, VnfType>();
		hosts = new HashMap<String, HostType>();
		nodes = new HashMap<String, NodeType>();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NfvInfoSerializer wf;
		String outfile = args[0];
		OutputStream os = null;
		
		if (outfile != null) {
			try {
				os = new PrintStream(new File(outfile));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		try {
			wf = new NfvInfoSerializer();
			wf.printAll(os);
		} catch (NfvReaderException e) {
			System.err.println("Could not instantiate data generator.");
			e.printStackTrace();
			System.exit(1);
		} catch (NfvInfoSerializerException e) {
			System.err.println("Could not write data to xml");
			e.printStackTrace();
			System.exit(1);
		}
	}


	public void printAll(OutputStream os) throws NfvInfoSerializerException {
		JAXBContext jc;
		Marshaller m;
		
		setCatalog(catalog);
		setHosts(hosts);
		setConnections(hosts);
		setNodes(nodes, hosts, catalog);
		setDeployedNodes(hosts, nodes);
		setLinks(nodes);
		
		nfv.setNffgs(createNGGraph(nodes));
		nfv.setVnfCatalog(createVnfCatalog(catalog));
		nfv.setHosts(createHostSet(hosts));
		
		
		try {
	        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); 
	        Schema schema = sf.newSchema(new File("xsd/nfvInfo.xsd")); 
	        
			jc = JAXBContext.newInstance("it.polito.dp2.NFV.sol1.jaxb");
			m = jc.createMarshaller();
			
			m.setSchema(schema);
			m.setEventHandler(new NfvValidationEventHandler());
			
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			m.marshal(nfv, os);
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new NfvInfoSerializerException(e);
			
		} catch (SAXException e) {
			e.printStackTrace();
			throw new NfvInfoSerializerException(e);
		}
	}


	private HostsType createHostSet(Map<String, HostType> hosts) {
		HostsType hostSet = new HostsType();
		hostSet.getHost().addAll(hosts.values());
		return hostSet;
	}

	private Catalog createVnfCatalog(HashMap<String, VnfType> catalog) {
		Catalog vnfCatalog = new Catalog();
		vnfCatalog.getVnf().addAll(catalog.values());
		return vnfCatalog;
	}

	private void setNodes(Map<String, NodeType> nodes, Map<String, HostType> hosts,
			HashMap<String, VnfType> catalog2) {
		//get NF-FGs
		Set<NffgReader> set = monitor.getNffgs(null);

		for (NffgReader nffg_r: set) {
			//get nodes from reader
			Set<NodeReader> nodeSet = nffg_r.getNodes();

			//for each read node create the corresponding xml node
			for (NodeReader nr: nodeSet) {
				//create new node object
				NodeType node = new NodeType();
				node.setName(nr.getName());
				
				
				//set the host reference
				node.setHost(hosts.get(nr.getHost().getName()));
				
				//set the vnf type reference
				node.setVnf(catalog.get(nr.getFuncType().getName()));
				
				nodes.put(nr.getName(), node); //update global nodes registry
			}
		}
	}

	private void setCatalog(Map<String, VnfType> catalog) {;
		
		for (VNFTypeReader r : monitor.getVNFCatalog()) {
			VnfType type = new VnfType();
			type.setName(r.getName());
			type.setFunctionalType(it.polito.dp2.NFV.sol1.jaxb.FunctionalType.fromValue(r.getFunctionalType().toString()));
			type.setRequiredMemory(BigInteger.valueOf(r.getRequiredMemory()));
			type.setRequiredStorage(BigInteger.valueOf(r.getRequiredStorage()));
			
			catalog.put(r.getName(),type);
		}		
	}

	private void setHosts(Map<String, HostType> hosts) {
		// Get the list of Hosts
		Set<HostReader> set = monitor.getHosts();
		
		// For each Host print related data
		for (HostReader host_r: set) {
			HostType h = new HostType();
			h.setName(host_r.getName());
			h.setMaxVNFs(BigInteger.valueOf(host_r.getMaxVNFs()));
			h.setAvailableMemory(BigInteger.valueOf(host_r.getAvailableMemory()));
			h.setAvailableStorage(BigInteger.valueOf(host_r.getAvailableStorage()));
			
			hosts.put(host_r.getName(), h);
		}
	}

	private void setConnections(Map<String, HostType> hosts) {
		// Get the list of Hosts
		Set<HostReader> set = monitor.getHosts();

		for (HostReader sri: set) {
			Connections conns = new Connections();
			for (HostReader srj: set) {
				ConnectionPerformanceReader cpr = monitor.getConnectionPerformance(sri, srj);
				
				
				ConnectionType c = new ConnectionType();
				ConnectionPerformanceType p = new ConnectionPerformanceType();
				
				//add latency
				p.getLatencyOrThroughput().add((BigInteger.valueOf(cpr.getLatency())));
				
				//add throughput
				p.getLatencyOrThroughput().add(cpr.getThroughput());
				
				c.setPerformance(p);
				c.setDestination(hosts.get(srj.getName()));
				
				conns.getConnection().add(c);

			}
			//set connections for node
			hosts.get(sri.getName()).setConnections(conns);
		}
		
	}

	private void setDeployedNodes(Map<String, HostType> hosts, Map<String, NodeType> nodes) {
		// Get the list of Hosts
		Set<HostReader> set = monitor.getHosts();
		
		for (HostReader host_r: set) {
			
			Set<NodeReader> nodeSet = host_r.getNodes();
			DeployedNodes dn = new DeployedNodes();
			
			for (NodeReader nr: nodeSet) {
				DeployedNode node = new DeployedNode();
				node.setName(nodes.get(nr.getName()));
				dn.getDeployedNode().add(node);
			}
			
			if (dn.getDeployedNode().size() > 0) {
				hosts.get(host_r.getName()).setDeployedNodes(dn);
			}
			
		}
		
	}

	private Nffgs createNGGraph(Map<String, NodeType> nodes) throws NfvInfoSerializerException{
		// Get the list of NF-FGs
		Set<NffgReader> set = monitor.getNffgs(null);
		Nffgs nffgs = new Nffgs();
		
		for (NffgReader nffg_r: set) {
			//create new nffg object
			NffgType nffg = new NffgType();
			
			//set nffg properties: name, deployTime
			nffg.setName(nffg_r.getName());
			//set deploy time
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(nffg_r.getDeployTime().getTime());
			try {
				nffg.setDeployTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
			} catch (DatatypeConfigurationException e) {
				throw new NfvInfoSerializerException("Could not set xml date");
			}

			Nodes nodeSet = new Nodes();
			//for each read node attach the corresponding xml node
			for (NodeReader nr: nffg_r.getNodes()) {
				nodeSet.getNode().add(nodes.get(nr.getName()));
			}
			
			nffg.setNodes(nodeSet);
			nffgs.getNffg().add(nffg);
		}
		
		return nffgs;
		
	}
	
	private void setLinks(Map<String, NodeType> nodes) {
		for (NffgReader nffg_r: monitor.getNffgs(null)) {
			Set<NodeReader> nodeSet = nffg_r.getNodes();
			
			for (NodeReader nr: nodeSet) {
				//get links
				Set<LinkReader> linkSet = nr.getLinks();
				//iterate over links and add them as xml objects to node
				for (LinkReader l : linkSet) {
					ConnectionType link = new ConnectionType();
					ConnectionPerformanceType performance = new ConnectionPerformanceType();
					
					link.setName(l.getName());
					link.setDestination(nodes.get(l.getDestinationNode().getName()));
					
					BigInteger lat = BigInteger.valueOf(l.getLatency());
					if (lat.intValue() != 0) performance.getLatencyOrThroughput().add(lat);
					
					if (l.getThroughput() != 0.0) performance.getLatencyOrThroughput().add(l.getThroughput());
					
					//the performance object is attached only if one of its children is present
					if (lat.intValue() != 0||l.getThroughput() != 0.0) link.setPerformance(performance);
					
					nodes.get(nr.getName()).getLink().add(link);
				}
			}
		}
	}
}
