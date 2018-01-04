package it.polito.dp2.NFV.sol1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import it.polito.dp2.NFV.*;
import it.polito.dp2.NFV.sol1.jaxb.*;
import it.polito.dp2.NFV.sol1.jaxb.FunctionalType;

public class NfvInfoSerializer {
	private NfvReader monitor;
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
		NfvReaderFactory factory = NfvReaderFactory.newInstance();
		monitor = factory.newNfvReader();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		nfv = new Nfv();
		hosts = new HashMap<String, HostType>();
		nodes = new HashMap<String, NodeType>();
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
		setNfggs(nodes);
		setDeployedNodes(nfv);
		setConnections(nfv);
		
		try {
			jc = JAXBContext.newInstance("it.polito.dp2.NFV.sol1.jaxb");
			m = jc.createMarshaller();
			m.marshal(nfv, os);
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new NfvInfoSerializerException(e);
			
		}
	}


	private void setCatalog(Map<String, VnfType> catalog) {;
		
		for (VNFTypeReader r : monitor.getVNFCatalog()) {
			VnfType type = new VnfType();
			type.setName(r.getName());
			type.setFunctionalType(FunctionalType.fromValue(r.getFunctionalType().toString()));
			type.setRequiredMemory(BigInteger.valueOf(r.getRequiredMemory()));
			type.setRequiredStorage(BigInteger.valueOf(r.getRequiredStorage()));
			
			catalog.put(r.getName(),type);
		}		
	}

	private void setHosts(Map<String, HostType> hosts2) {
		// Get the list of Hosts
		Set<HostReader> set = monitor.getHosts();
		HostsType hosts = new HostsType();
		
		// For each Host print related data
		for (HostReader host_r: set) {
			HostType h = new HostType();
			h.setName(host_r.getName());
			this.hosts.put(host_r.getName(), h); //update global hosts registry
			
			h.setMaxVNFs(BigInteger.valueOf(host_r.getMaxVNFs()));
			h.setAvailableMemory(BigInteger.valueOf(host_r.getAvailableMemory()));
			h.setAvailableStorage(BigInteger.valueOf(host_r.getAvailableStorage()));
			hosts.getHost().add(h);
		}
		hosts2.setHosts(hosts);
	}

	private void setConnections(Nfv nfv) {
		// Get the list of Hosts
		Set<HostReader> set = monitor.getHosts();

		for (HostReader sri: set) {
			for (HostReader srj: set) {
				ConnectionPerformanceReader cpr = monitor.getConnectionPerformance(sri, srj);
				
				Connections conns = new Connections();
				ConnectionType c = new ConnectionType();
				ConnectionPerformanceType p = new ConnectionPerformanceType();
				c.setDestination(this.hosts.get(srj.getName()));
				p.setLatency(cpr.getLatency());
				p.setThroughput(BigDecimal.valueOf(cpr.getThroughput()));
				c.setPerformance(p);
				
				//set connections for node
				this.hosts.get(sri.getName()).setConnections(conns);
			}
		}
		
	}

	private void setDeployedNodes(Nfv nfv) {
		// Get the list of Hosts
		Set<HostReader> set = monitor.getHosts();
		
		for (HostReader host_r: set) {
			
			Set<NodeReader> nodeSet = host_r.getNodes();
			DeployedNodes dn = new DeployedNodes();
			
			for (NodeReader nr: nodeSet) {
				DeployedNode node = new DeployedNode();
				node.setName(this.nodes.get(nr.getName()));
				dn.getDeployedNode().add(node);
			}
			
			for (HostType h : nfv.getHosts().getHost()) {
				if (h.getName().equals(host_r.getName())) {
					h.setDeployedNodes(dn);
				}
			}
			
		}
		
	}

	private void setNfggs(Map<String, NodeType> nodes2) throws NfvInfoSerializerException{
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

			//get nodes from reader
			Set<NodeReader> nodeSet = nffg_r.getNodes();
			
			//set nodes
			Nodes nodes = new Nodes();
			
			//for each read node create the corresponding xml node
			for (NodeReader nr: nodeSet) {
				//create new node object
				NodeType node = new NodeType();
				node.setName(nr.getName());
				this.nodes.put(nr.getName(), node); //update global nodes registry
				
				//set the host reference
				//the hosts object must have been already created and set!
				node.setHost(this.hosts.get(nr.getHost().getName()));
				
				//set the vnf type reference
				//the vnf catalog object must have been already created and set!
				//iterate over each entry of the catalog to search for the referenced one
				for (VnfType type : nodes2.getVnfCatalog().getVnf()) {
					//if the type has been found
					if (type.getName().equals(nr.getFuncType().getName())) {
						//set the reference using
						node.setVnf(type);
					}
				}

				nodes.getNode().add(node);
			}
			
			nffg.setNodes(nodes);
			nffgs.getNffg().add(nffg);
		}
		
		nodes2.setNffgs(nffgs);
		
		setLinks(nodes2);
		
	}
	
	private void setLinks(Nfv nfv) {
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
					link.setDestination(this.nodes.get(l.getDestinationNode().getName()));
					performance.setLatency(l.getLatency());
					performance.setThroughput(BigDecimal.valueOf(l.getThroughput()));
					link.setPerformance(performance);
					this.nodes.get(nr.getName()).getLink().add(link);
				}
			}
		}
	}

}
