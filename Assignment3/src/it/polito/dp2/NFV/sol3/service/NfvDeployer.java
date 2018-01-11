package it.polito.dp2.NFV.sol3.service;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import it.polito.dp2.NFV.FactoryConfigurationError;
import it.polito.dp2.NFV.NfvReader;
import it.polito.dp2.NFV.NfvReaderException;
import it.polito.dp2.NFV.NfvReaderFactory;
import it.polito.dp2.NFV.lab1.NfvInfo;
import it.polito.dp2.NFV.sol3.model.*;

public class NfvDeployer {
	private static final NfvDeployer INSTANCE;
	static {
		try {
			INSTANCE = new NfvDeployer();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}
	}
	
	private static final String nfvPath = "";
	private static final String hostsPath = "hosts";
	private static final String nffgsPath = "nffgs";
	private static final String catalogPath = "catalog";
	private static final String connectionsPath = "connections";
	private static final String nodesPath = "nodes";
	private static final String linksPath = "links";

	private Nfv nfv = new Nfv();
	private Hosts hosts = new Hosts();
	private Nffgs nffgs = new Nffgs();
	private Nodes nodes = new Nodes();
	private Catalog catalog = new Catalog();

	private static Map<String, Host> hostMap = new HashMap<>(); //hostId, host
	private static Map<String, String> hostIds = new HashMap<>(); //hostname, hostId
	private static Map<String, Connections> connections = new HashMap<>(); //hostname, connections
	private static Map<String, Vnf> vnfs = new HashMap<>(); //name,vnf
	private static Map<String, Nffg> nffgMap = new HashMap<>(); //name,nffg
	private static Map<String, Node> nodeMap = new HashMap<>(); //nodeId, node
	private static Map<String, String> nodeIds = new HashMap<>(); //nodename, nodeId
    
    private NfvDeployer() throws DatatypeConfigurationException, NfvReaderException, FactoryConfigurationError {
    	
		NfvReader NfvReader = NfvReaderFactory.newInstance().newNfvReader();
 	
    	this.nfv.setHosts(createLink(hostsPath));
    	this.nfv.setNffgs(createLink(nffgsPath));
    	this.nfv.setVnfCatalog(createLink(catalogPath));
    	
    	this.hosts.getHost().add(createNamedRef("host1", hostsPath+"/host1"));
    	NfvDeployer.hostIds.put("host1", "1");
    	NfvDeployer.hostMap.put("1", createHost("host1", 0, 0, 0));
    	
    	this.nffgs.getNffg().add(createNamedRef("Nffg0", nffgsPath+"/Nffg0"));
    	NfvDeployer.nffgMap.put("Nffg0", createNffg("Nffg0", nffgsPath+"/Nffg0", Calendar.getInstance()));
    	
    	this.nodes.getNode().add(createNamedRef("node1", nffgsPath+"/Nffg0"+nodesPath+"/node1"));

    	this.catalog.getVnf().add(createVnf(FunctionalType.FW, "fw1", "/catalog/fw1", BigInteger.valueOf(120), BigInteger.valueOf(240)));

    };



	private Nffg createNffg(String name, String ref, Calendar deployTime) throws DatatypeConfigurationException {
		Nffg nffg = new Nffg();
		nffg.setDeployTime(DatatypeFactory.newInstance().newXMLGregorianCalendar());
		nffg.setName(name);
		nffg.setHref(ref);
		nffg.setNodes(createLink(nfvPath+"/"+NfvDeployer.nffgsPath+"/"+name+"/"+NfvDeployer.nodesPath));
		nffg.setLinks(createLink(NfvDeployer.nfvPath+"/"+NfvDeployer.nffgsPath+"/"+name+"/"+NfvDeployer.linksPath));
		return nffg;
	}



	public static NfvDeployer getInstance() {
		return INSTANCE;
	}

	public Nfv getNfv() {
		return this.nfv;
	}

	private Hyperlink createLink(String ref) {
		Hyperlink l = new Hyperlink();
		l.setHref(ref);
		return l;
	}

	public Hosts getHosts() {
		return this.hosts;
	}

	public Nffgs getNffgs() {
		return this.nffgs;
	}

	public Catalog getCatalog() {
		return this.catalog;
	}

	private NamedEntity createNamedRef(String name, String ref) {
		NamedEntity e = new NamedEntity();
		e.setHref(ref);
		e.setName(name);
		return e;
	}

	private Vnf createVnf(FunctionalType type, String name, String href, BigInteger reqMemory, BigInteger reqStorage) {
		Vnf vnf = new Vnf();
		vnf.setFunctionalType(type);
		vnf.setName(name);
		vnf.setHref(href);
		vnf.setRequiredMemory(reqMemory);
		vnf.setRequiredStorage(reqStorage);
		return vnf;
	}
	
	private Host createHost(String name, Integer availableMem, Integer availableStorage, Integer maxVnfs) {
		Host h = new Host();
		h.setHref(NfvDeployer.nfvPath+"/"+NfvDeployer.hostsPath+"/"+name);
		h.setName(name);
		h.setAvailableMemory(availableMem.toString());
		h.setAvailableStorage(availableStorage.toString());
		h.setMaxVNFs(maxVnfs.toString());
		h.setConnections(createLink(nfvPath+"/"+hostsPath+"/"+connectionsPath));
		
		return h;
	}

	public Host getHostByName(String name) {
		String id = NfvDeployer.hostIds.get(name);
		return NfvDeployer.hostMap.get(id);
	}

	public Vnf getVnfByName(String name) {
		return vnfs.get(name);
	}

	public Connections getConnections(String hostName) {
		// TODO Auto-generated method stub
		String id = hostIds.get(hostName);
		return NfvDeployer.connections.get(id);
	}



	public Nffg getNffgByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
