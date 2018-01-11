package it.polito.dp2.NFV.sol3.service;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import it.polito.dp2.NFV.FactoryConfigurationError;
import it.polito.dp2.NFV.NfvReader;
import it.polito.dp2.NFV.NfvReaderException;
import it.polito.dp2.NFV.NfvReaderFactory;
import it.polito.dp2.NFV.VNFTypeReader;
import it.polito.dp2.NFV.sol3.model.*;

public class NfvDeployer {
	private static final String catalogPath = "catalog";
	private static final String connectionsPath = "connections";
	private static final String hostsPath = "hosts";
	private static final String linksPath = "links";
	private static final String nffgsPath = "nffgs";
	private static final String nfvPath = "";
	private static final String nodesPath = "nodes";
	private static final String reachableHostsPath = "hosts";

	private static NfvDeployer INSTANCE;
	static {
		try {
				INSTANCE = new NfvDeployer();
			} catch (Exception e) {
				e.printStackTrace();
				throw new ExceptionInInitializerError(e);
			}
	}

	public static NfvDeployer getInstance() {
		return INSTANCE;
	}

	private Nfv nfv = new Nfv();
	private Catalog catalog = new Catalog();
	private Hosts hosts = new Hosts();
	private Nffgs nffgs = new Nffgs();

	private Map<String, Vnf> vnfs = new HashMap<>(); // name,vnf;
	private Map<String, Connections> connections = new HashMap<>(); // hostname, connections
	private Map<String, String> hostIds = new HashMap<>(); // hostname, hostId
	private Map<String, Host> hostMap = new HashMap<>(); // hostId, host
	private Map<String, Nffg> nffgMap = new HashMap<>(); // name,nffg
	private Map<String, Nodes> nffgNodes = new HashMap<>();
	private Map<String, String> nodeIds = new HashMap<>(); // nodename, nodeId
	private Map<String, Node> nodeMap = new HashMap<>(); // nodeId, node
	private Map<String, Links> nffgLinks = new HashMap<>(); // nodeId, node

	private NfvDeployer() throws DatatypeConfigurationException, NfvReaderException, FactoryConfigurationError {

		System.setProperty("it.polito.dp2.NFV.NfvReaderFactory", "it.polito.dp2.NFV.Random.NfvReaderFactoryImpl");
		String baseUri = System.getProperty("it.polito.dp2.NFV.lab3.URL", "http://localhost:8080/NfvDeployer/rest/");
		NfvReader reader = NfvReaderFactory.newInstance().newNfvReader();
		

		this.nfv.setHosts(createLink(baseUri + hostsPath));
		this.nfv.setNffgs(createLink(baseUri + nffgsPath));
		this.nfv.setVnfCatalog(createLink(baseUri + catalogPath));

		for (VNFTypeReader t : reader.getVNFCatalog()) {
			this.catalog.getVnf()
					.add(createVnf(FunctionalType.fromValue(t.getFunctionalType().toString()), t.getName(),
							baseUri + catalogPath + "/" + t.getName(), BigInteger.valueOf(t.getRequiredMemory()),
							BigInteger.valueOf(t.getRequiredStorage())));
		}

		this.hosts.getHost().add(createNamedRef("host1", hostsPath + "/host1"));
		this.hostIds.put("host1", "1");
		this.hostMap.put("1", createHost("host1", 0, 0, 0));

		this.nffgs.getNffg().add(createNamedRef("Nffg0", nffgsPath + "/Nffg0"));
		this.nffgMap.put("Nffg0", createNffg("Nffg0", nffgsPath + "/Nffg0", Calendar.getInstance()));
		this.nffgNodes.put("Nffg0", new Nodes());

		this.nffgNodes.get("Nffg0").getNode()
				.add(createNamedRef("node1", nffgsPath + "/Nffg0/" + nodesPath + "/node1"));
		this.nffgLinks.put("Nffg0", new Links());

		this.nodeIds.put("node1", "1");
		this.nodeMap.put("1", createNode("node1", "fw1"));

	}

	private Host createHost(String name, Integer availableMem, Integer availableStorage, Integer maxVnfs) {
		Host h = new Host();
		h.setHref(NfvDeployer.nfvPath + "/" + NfvDeployer.hostsPath + "/" + name);
		h.setName(name);
		h.setAvailableMemory(availableMem.toString());
		h.setAvailableStorage(availableStorage.toString());
		h.setMaxVNFs(maxVnfs.toString());
		h.setConnections(createLink(nfvPath + "/" + hostsPath + "/" + connectionsPath));

		return h;
	}

	private Hyperlink createLink(String ref) {
		Hyperlink l = new Hyperlink();
		l.setHref(ref);
		return l;
	}

	private NamedEntity createNamedRef(String name, String ref) {
		NamedEntity e = new NamedEntity();
		e.setHref(ref);
		e.setName(name);
		return e;
	}

	private Nffg createNffg(String name, String ref, Calendar deployTime) throws DatatypeConfigurationException {
		Nffg nffg = new Nffg();
		nffg.setDeployTime(DatatypeFactory.newInstance().newXMLGregorianCalendar());
		nffg.setName(name);
		nffg.setHref(ref);
		nffg.setNodes(createLink(nfvPath + "/" + NfvDeployer.nffgsPath + "/" + name + "/" + NfvDeployer.nodesPath));
		nffg.setLinks(createLink(
				NfvDeployer.nfvPath + "/" + NfvDeployer.nffgsPath + "/" + name + "/" + NfvDeployer.linksPath));
		return nffg;
	}

	private Node createNode(String name, String type) {
		Node node = new Node();
		node.setName(name);
		node.setHref(nfvPath + "/" + hostsPath + "/" + name);
		node.setReachableHosts(createLink("/" + reachableHostsPath));
		node.setVnf(vnfs.get(type));
		node.setLinks(createLink("/" + linksPath));
		return node;
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

	public Nffg deployNffg(String name) {
		// TODO Auto-generated method stub
		if (nffgMap.containsKey(name)) {
			return nffgMap.get(name);
		} else
			throw new NotFoundException("No nffg defined with name " + name);
	}

	public Catalog getCatalog() {
		return this.catalog;
	}

	public Connections getConnections(String hostName) {
		// TODO
		if (hostIds.containsKey(hostName)) {
			String id = hostIds.get(hostName);
			if (connections.containsKey(id)) {
				return connections.get(id);
			} else
				throw new NotFoundException("No host loaded with name " + hostName + " and ID " + id);
		} else
			throw new NotFoundException("No connections defined for hostname " + hostName);
	}

	public Host getHostByName(String name) {
		if (hostIds.containsKey(name)) {
			String id = this.hostIds.get(name);
			if (hostMap.containsKey(id)) {
				return hostMap.get(id);
			} else
				throw new NotFoundException("No host loaded with name " + name + " and ID " + id);
		} else
			throw new NotFoundException("No host defined with the name " + name);
	}

	public Hosts getHosts() {
		return this.hosts;
	}

	public Nffg getNffgByName(String name) {
		// TODO Auto-generated method stub
		if (nffgMap.containsKey(name)) {
			return nffgMap.get(name);
		} else
			throw new NotFoundException("No nffg defined with name " + name);
	}

	public Nffgs getNffgs(Date from) {

		if (from == null)
			return this.nffgs;
		else {
			Nffgs set = new Nffgs();
			for (NamedEntity n : nffgs.getNffg()) {
				if (nffgMap.get(n.getName()).getDeployTime().toGregorianCalendar().getTime().compareTo(from) >= 0) {
					set.getNffg().add(n);
				}
			}

			return set;
		}
	}

	public Nfv getNfv() {
		return this.nfv;
	}

	public Node getNode(String name) {
		// TODO Auto-generated method stub
		if (nodeIds.containsKey(name)) {
			String id = hostIds.get(name);
			if (nodeMap.containsKey(id)) {
				return nodeMap.get(id);
			} else
				throw new NotFoundException("No node loaded with name " + name + " and ID " + id);
		} else
			throw new NotFoundException("No node defined for name " + name);
	}

	public Nodes getNodes(String graph) {
		if (graph == null) {
			Nodes allNodes = new Nodes();
			allNodes.getNode().addAll(this.nodeMap.values());
			return allNodes;
		}

		if (nffgMap.containsKey(graph)) {
			return nffgNodes.get(graph);
		} else
			throw new NotFoundException("No nffg defined with name " + graph);
	}

	public Vnf getVnfByName(String name) {
		if (vnfs.containsKey(name))
			return vnfs.get(name);
		else
			throw new NotFoundException("No VNF defined with the name " + name);
	}

	public Links getLinksByGraph(String nffg) {
		// TODO Auto-generated method stub
		return null;
	}

}
