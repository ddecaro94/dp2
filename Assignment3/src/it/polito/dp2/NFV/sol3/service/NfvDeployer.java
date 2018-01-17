package it.polito.dp2.NFV.sol3.service;

import java.math.BigInteger;
import java.net.URI;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import com.sun.jersey.api.client.ClientResponse;

import it.polito.dp2.NFV.FactoryConfigurationError;
import it.polito.dp2.NFV.NfvReader;
import it.polito.dp2.NFV.NfvReaderException;
import it.polito.dp2.NFV.lab3.AllocationException;
import it.polito.dp2.NFV.lab3.ServiceException;
import it.polito.dp2.NFV.lab3.UnknownEntityException;
import it.polito.dp2.NFV.sol3.service.model.*;
import it.polito.dp2.NFV.sol3.service.model.Host.DeployedNodes;
import it.polito.dp2.NFV.sol3.service.data.Labels;
import it.polito.dp2.NFV.sol3.service.data.Neo4JSimpleXML;
import it.polito.dp2.NFV.sol3.service.data.Neo4JSimpleXML.Data;
import it.polito.dp2.NFV.sol3.service.data.Properties;
import it.polito.dp2.NFV.sol3.service.data.Property;
import it.polito.dp2.NFV.sol3.service.data.Relationships;
import it.polito.dp2.NFV.sol3.service.data.Relationships.Relationship;

public class NfvDeployer {
	public static final String catalogPath = "catalog";
	public static final String connectionsPath = "connections";
	public static final String hostsPath = "hosts";
	public static final String linksPath = "links";
	public static final String nffgsPath = "nffgs";
	public static final String nodesPath = "nodes";
	public static final String reachableHostsPath = "reachableHosts";

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
	private Data dataApi;
	private NfvReader reader;
	private String baseUri;
	private String dataUri;

	private Map<String, Vnf> vnfs = new HashMap<>(); // name,vnf;
	private Map<String, Connections> connections = new HashMap<>(); // hostname, connections
	private Map<String, String> hostIds = new HashMap<>(); // hostname, hostId
	private Map<String, Host> hostMap = new HashMap<>(); // hostId, host
	private ConcurrentMap<String, Nffg> nffgMap = new ConcurrentHashMap<>(); // name,nffg
	private ConcurrentMap<String, Map<String, Node>> nffgNodes = new ConcurrentHashMap<>();
	private ConcurrentMap<String, String> nodeIds = new ConcurrentHashMap<>(); // nodename, nodeId
	private ConcurrentMap<String, Node> nodeMap = new ConcurrentHashMap<>(); // nodeId, node
	private ConcurrentMap<String, Links> nffgLinks = new ConcurrentHashMap<>(); // nffg, links
	private ConcurrentMap<String, Link> links = new ConcurrentHashMap<>(); // (relationshipID, Link)
	private ConcurrentMap<String, Map<String, Link>> linkNames = new ConcurrentHashMap<>(); // (nffg, (linkame, Link))
	private ReadWriteLock nffgLock = new ReentrantReadWriteLock();

	private NfvDeployer() throws DatatypeConfigurationException, NfvReaderException, FactoryConfigurationError {
		System.out.println("Starting up...");
		try {
			baseUri = System.getProperty("it.polito.dp2.NFV.lab3.URL", "http://localhost:8080/NfvDeployer/rest/");
			dataUri = System.getProperty("it.polito.dp2.NFV.lab3.Neo4JSimpleXMLURL",
					"http://localhost:8080/Neo4JSimpleXML/rest");

			
			dataApi = Neo4JSimpleXML.data(Neo4JSimpleXML.createClient(), URI.create(dataUri));
			/*

			reader = NfvReaderFactory.newInstance().newNfvReader();
			this.nfv.setHosts(createHyperlink(baseUri + hostsPath));
			this.nfv.setNffgs(createHyperlink(baseUri + nffgsPath));
			this.nfv.setVnfCatalog(createHyperlink(baseUri + catalogPath));

			for (VNFTypeReader t : reader.getVNFCatalog()) {
				createVnf(FunctionalType.fromValue(t.getFunctionalType().toString()), t.getName(),
						BigInteger.valueOf(t.getRequiredMemory()), BigInteger.valueOf(t.getRequiredStorage()));

			}

			// load all hosts
			for (HostReader host : reader.getHosts()) {
				try {
					createHost(host.getName(), host.getAvailableMemory(), host.getAvailableStorage(),
							host.getMaxVNFs());
				} catch (AlreadyLoadedException e) {
					throw new ConflictException();
				}
			}

			// load connections
			for (HostReader hosti : reader.getHosts()) {
				for (HostReader hostj : reader.getHosts()) {
					Connection cij = new Connection();
					cij.setSrc(hosti.getName());
					cij.setDst(hostj.getName());
					cij.setHref(baseUri + hostsPath + "/" + hosti.getName() + "/" + connectionsPath);
					cij.setLatency(BigInteger.valueOf(reader.getConnectionPerformance(hosti, hostj).getLatency()));
					cij.setThroughput(Float.valueOf(reader.getConnectionPerformance(hosti, hostj).getThroughput()));

					this.connections.get(this.hostIds.get(hosti.getName())).getConnection().add(cij);
				}
			}

			// create NF-FG0 but NOT deployed
			NffgReader graph = reader.getNffg("Nffg0");

			NewNffg nffg0 = new NewNffg();
			nffg0.setName(graph.getName());
			NewNffg.Nodes nodes = new NewNffg.Nodes();
			NewNffg.Links links = new NewNffg.Links();
			for (NodeReader n : graph.getNodes()) {
				NewNffg.Nodes.Node node = new NewNffg.Nodes.Node();
				node.setName(n.getName());
				node.setVnf(n.getFuncType().getName());
				node.setPreferredHost(n.getHost().getName());
				for (LinkReader lr : n.getLinks()) {
					NewNffg.Links.Link link = new NewNffg.Links.Link();
					link.setName(lr.getName());
					link.setDestinationNode(lr.getDestinationNode().getName());
					link.setSourceNode(lr.getSourceNode().getName());
					link.setRequiredLatency(BigInteger.valueOf(lr.getLatency()));
					link.setRequiredThroughput(lr.getThroughput());
					links.getLink().add(link);
				}
				nodes.getNode().add(node);
			}
			nffg0.setNodes(nodes);
			nffg0.setLinks(links);

			deployNffg(nffg0);
			*/
			
		} catch (WebApplicationException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new InternalServerErrorException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new InternalServerErrorException(e);
		}

	}

	public synchronized boolean allocateNode(String nodeName, String preferenceHost) throws AllocationException, UnknownEntityException {
		if (!isExistingNode(nodeName)) throw new UnknownEntityException("Node " + nodeName + " does not exist");
		
		if (preferenceHost != null && !hostIds.containsKey(preferenceHost))
				throw new UnknownEntityException("Host " + preferenceHost + " not found");
		
		if (nodeMap.get(nodeIds.get(nodeName)).getHost() != null) throw new AllocationException("Node "+nodeName+" is already allocated");
			
		// if the node is not allocated link the node to the host
		Node n = nodeMap.get(nodeIds.get(nodeName));
		if (!isExistingType(n.getVnf().getName())) throw new UnknownEntityException("VNF type " + n.getVnf().getName() + " does not exist");
		
		Vnf v = vnfs.get(n.getVnf().getName());
		it.polito.dp2.NFV.sol3.service.data.Relationship allocatedOn = new it.polito.dp2.NFV.sol3.service.data.Relationship();
		
		int requiredMemory = (v.getRequiredMemory() != null) ? v.getRequiredMemory().intValue() : 0;
		int requiredStorage = (v.getRequiredStorage() != null) ? v.getRequiredStorage().intValue() : 0;
		
		if (preferenceHost != null) {
			// try allocating to the preference host
			Host h = hostMap.get(hostIds.get(preferenceHost));
			int availableMemory = (h.getAvailableMemory() != null) ? h.getAvailableMemory().intValue() : 0;
			int availableStorage = (h.getAvailableStorage() != null) ? h.getAvailableStorage().intValue() : 0;
			int deployedVnfs = (h.getDeployedNodes() != null) ? h.getDeployedNodes().getNode().size() : 0;
			int maxVnfs = (h.getMaxVNFs() != null) ? h.getMaxVNFs().intValue() : 0;
			
			if (requiredMemory < availableMemory && requiredStorage < availableStorage && deployedVnfs < maxVnfs) {
				//allocate node
				//hostMap.get(hostIds.get(preferenceHost)).setAllocatedMemory(BigInteger.valueOf(allocatedMemory + requiredMemory));
				//hostMap.get(hostIds.get(preferenceHost)).setAllocatedStorage(BigInteger.valueOf(allocatedStorage + requiredStorage));
				hostMap.get(hostIds.get(preferenceHost)).getDeployedNodes().getNode().add(createNamedRef(nodeName, n.getHref()));
				
				// create relationship
				nodeMap.get(nodeIds.get(nodeName)).setHost(createNamedRef(h.getName(), h.getHref()));
				allocatedOn.setDstNode(this.hostIds.get(preferenceHost));
				allocatedOn.setType("AllocatedOn");
				this.dataApi.nodeNodeidRelationships(this.nodeIds.get(nodeName))
						.postXml(allocatedOn, it.polito.dp2.NFV.sol3.service.data.Relationship.class);
				return true;
			}
		}
		
		//if preference host is not specified, find first available host
		
		for (Host h : hostMap.values()) {
			int availableMemory = (h.getAvailableMemory() != null) ? h.getAvailableMemory().intValue() : 0;
			int availableStorage = (h.getAvailableStorage() != null) ? h.getAvailableStorage().intValue() : 0;
			int deployedVnfs = (h.getDeployedNodes() != null) ? h.getDeployedNodes().getNode().size() : 0;
			int maxVnfs = (h.getMaxVNFs() != null) ? h.getMaxVNFs().intValue() : 0;
			
			
			if (requiredMemory < availableMemory && requiredStorage < availableStorage && deployedVnfs < maxVnfs) {
				//allocate node
				//hostMap.get(hostIds.get(h.getName())).setAllocatedMemory(BigInteger.valueOf(availableMemory - requiredMemory));
				//hostMap.get(hostIds.get(h.getName())).setAllocatedStorage(BigInteger.valueOf(availableStorage - requiredStorage));
				hostMap.get(hostIds.get(h.getName())).getDeployedNodes().getNode().add(createNamedRef(nodeName, n.getHref()));
				
				//create relationship
				nodeMap.get(nodeIds.get(nodeName)).setHost(createNamedRef(h.getName(), h.getHref()));
				allocatedOn.setDstNode(this.hostIds.get(h.getName()));
				allocatedOn.setType("AllocatedOn");
				this.dataApi.nodeNodeidRelationships(this.nodeIds.get(nodeName))
				.postXml(allocatedOn, it.polito.dp2.NFV.sol3.service.data.Relationship.class);
				
				return true;
			}
			
		}

		throw new AllocationException("Not enough resources to allocate node");


	}
	
	public Connection createConnection (String source, String destination, int latency, float throughput) throws InvalidEntityException, UnknownEntityException {
		if (!isExistingHost(source)) throw new UnknownEntityException("Host " + source + " does not exist");
		if (!isExistingHost(destination)) throw new UnknownEntityException("Host " + destination + " does not exist");
		
		if (latency < 0) throw new InvalidEntityException("Latency cannot be negative");
		if (throughput < 0) throw new InvalidEntityException("Throughput cannot be negative");
		
		Connection cij = new Connection();
		cij.setSrc(source);
		cij.setDst(destination);
		cij.setHref(baseUri + hostsPath + "/" + source + "/" + connectionsPath);
		cij.setLatency(BigInteger.valueOf(latency));
		cij.setThroughput(Float.valueOf(throughput));

		this.connections.get(this.hostIds.get(source)).getConnection().add(cij);
		
		return cij;
	}

	public String createHost(String hostName, Integer availableMemory, Integer availableStorage, Integer maxVnfs)
			throws AlreadyLoadedException, ServiceException {

		if (this.hostIds.containsKey(hostName)) throw new AlreadyLoadedException("Host " + hostName + " already loaded");
			
		try {

			// the host has not already been created in the database
			it.polito.dp2.NFV.sol3.service.data.Node requestedHost = new it.polito.dp2.NFV.sol3.service.data.Node();
			Labels labels = new Labels();
			labels.getLabel().add("Host");
			Properties properties = new Properties();
			Property name = new Property();
			name.setName("name");
			name.setValue(hostName);
			properties.getProperty().add(name);
			requestedHost.setProperties(properties);
	
			it.polito.dp2.NFV.sol3.service.data.Node createdHost = this.dataApi.node().postXml(requestedHost,
					it.polito.dp2.NFV.sol3.service.data.Node.class);
	
			if (createdHost == null)
				throw new WebApplicationException("Unable to create node with name " + hostName);
			if (createdHost.getId() == null)
				throw new WebApplicationException("Unable to retrieve nodeID for node with name " + hostName);
	
			Host h = new Host();
			h.setHref(baseUri + hostsPath + "/" + hostName);
			h.setName(hostName);
			h.setAvailableMemory(BigInteger.valueOf(availableMemory));
			h.setAvailableStorage(BigInteger.valueOf(availableStorage));
			h.setMaxVNFs(BigInteger.valueOf(maxVnfs));
			h.setConnections(createHyperlink(baseUri + hostsPath + "/" + hostName + "/" + connectionsPath));
			h.setDeployedNodes(new DeployedNodes());
	
			this.hostIds.put(hostName, createdHost.getId());
			this.hostMap.put(createdHost.getId(), h);
			this.hosts.getHost().add(createNamedRef(hostName, baseUri + hostsPath + "/" + hostName));
			this.connections.put(createdHost.getId(), new Connections());
	
			// add labels to the node, replace if existing
			ClientResponse r = this.dataApi.nodeNodeidLabels(createdHost.getId()).putXml(labels, ClientResponse.class);
			if (r.getStatus() >= 400)
				throw new ServiceException("Unable to create labels for node with id " + createdHost.getId());
	
			return createdHost.getId();
		} catch (WebApplicationException e) {
			throw new ServiceException(e);
		}
			
	}

	private Hyperlink createHyperlink(String ref) {
		Hyperlink l = new Hyperlink();
		l.setHref(ref);
		return l;
	}

	public synchronized Link createLink(String graphName, String linkName, String srcNode, String dstNode, int minLatency,
			float minThroughput, boolean replace) throws AlreadyLoadedException, UnknownEntityException, ServiceException, InvalidEntityException {
		
		//can be created even if graph not deployed, resource should check deployed status
		if (!nffgMap.containsKey(graphName)) throw new UnknownEntityException("NF-FG " + graphName + " not found");

		boolean exists = isExistingLink(graphName, linkName) || isDuplicateLink(graphName, srcNode, dstNode);
		
		if (exists && !replace) throw new AlreadyLoadedException("Link " + linkName + " already loaded");
		if (!isExistingNode(dstNode)) throw new UnknownEntityException("Destination node does not exist");	
		if (!isExistingNode(srcNode)) throw new UnknownEntityException("Source node does not exist");
		if (minLatency < 0) throw new InvalidEntityException("Required latency cannot be negative");
		if (minThroughput < 0) throw new InvalidEntityException("Required throughput cannot be negative");
			
		it.polito.dp2.NFV.sol3.service.data.Relationship requestedLink = new it.polito.dp2.NFV.sol3.service.data.Relationship();
		requestedLink.setDstNode(this.nodeIds.get(dstNode));
		requestedLink.setType("ForwardsTo");

		Link l = new Link();
		l.setName(linkName);
		l.setHref(baseUri + nffgsPath + "/" + graphName + "/" + NfvDeployer.linksPath + "/" + linkName);
		l.setSrc(srcNode);
		l.setDst(dstNode);
		l.setRequiredLatency(BigInteger.valueOf(minLatency));
		l.setRequiredThroughput(minThroughput);

		try {
			
		
			if (exists) { //delete it, if exists and should not be replaced an exception has been thrown
				
				Relationships rels = dataApi.nodeNodeidRelationshipsOut(nodeIds.get(srcNode)).getAsRelationships();
				for (Relationship r : rels.getRelationship()) {
					if (r.getType().equals("ForwardsTo")) {
						
						//delete existing link
						if (links.containsKey(r.getId())) {
							//found relationship
							if (links.get(r.getId()).getName().equals(linkName)) {
								dataApi.relationshipRelationshipid(r.getId()).deleteAsXml(ClientResponse.class);
								linkNames.get(graphName).remove(linkName);
								links.remove(r.getId());
								nffgLinks.get(graphName).getLink().removeIf(t->t.getName().equals(linkName));
							}
						}
					}
				}
	
			}
			
			
			it.polito.dp2.NFV.sol3.service.data.Relationship createdLink = this.dataApi
						.nodeNodeidRelationships(this.nodeIds.get(srcNode))
						.postXml(requestedLink, it.polito.dp2.NFV.sol3.service.data.Relationship.class);
			
			links.put(createdLink.getId(), l);
			linkNames.get(graphName).put(linkName, l);
			nffgLinks.get(graphName).getLink().add(l);
	
			return l;
			
		} catch (WebApplicationException e) {
			throw new ServiceException(e);
		}

	}

	private boolean isDuplicateLink(String graph, String srcNode, String dstNode) {
		// TODO Auto-generated method stub
		if (srcNode == null || dstNode == null) return false;
		if (!nffgMap.containsKey(graph)) return false;
		if (!isExistingNode(srcNode) || !isExistingNode(dstNode)) return false;
		if (!linkNames.containsKey(graph)) return false;
		if (!linkNames.get(graph).containsKey(srcNode)) return false;
		
		it.polito.dp2.NFV.sol3.service.data.Relationships linksFromNode = this.dataApi
				.nodeNodeidRelationshipsOut(nodeIds.get(srcNode)).getAsRelationships();
		
		for (Relationship r : linksFromNode.getRelationship()) {
			if (links.get(r.getId()).getName().equals(dstNode)) return true;
		}
		
		return false;
	}

	private NamedEntity createNamedRef(String name, String ref) {
		NamedEntity e = new NamedEntity();
		e.setHref(ref);
		e.setName(name);
		return e;
	}

	private Nffg createNffg(String name) throws DatatypeConfigurationException {
		Nffg nffg = new Nffg();
		nffg.setName(name);
		nffg.setHref(baseUri + nffgsPath + "/" + name);
		nffg.setNodes(createHyperlink(baseUri + nffgsPath + "/" + name + "/" + nodesPath));
		nffg.setLinks(createHyperlink(baseUri + nffgsPath + "/" + name + "/" + NfvDeployer.linksPath));

		this.nffgNodes.put(name, new ConcurrentHashMap<>());

		this.nffgLinks.put(name, new Links());
		this.linkNames.put(nffg.getName(), new ConcurrentHashMap<>());
		this.nffgMap.put(nffg.getName(), nffg);

		return nffg;
	}

	public synchronized Node createNode(String nffg, String nodeName, String type, boolean replace)
			throws AlreadyLoadedException, UnknownEntityException, ServiceException, InvalidEntityException {
		
		//a node can be created even if the nffg is not completely deployed, graph should be checked by invoker class
		if (nffg == null) throw new InvalidEntityException("NF-FG name not provided");
		if (nodeName == null) throw new InvalidEntityException("Node name not provided");
		if (type == null) throw new InvalidEntityException("VNF type not provided");
		
		if (!nffgMap.containsKey(nffg)) throw new UnknownEntityException("NF-FG " + nffg + " does not exist");
		if (!isExistingType(type)) throw new UnknownEntityException("VNF type " + type + " does not exist");		
		
		boolean exists = isExistingNode(nodeName);

		if (exists && !replace)
			throw new AlreadyLoadedException("Node " + nodeName + " already loaded");
		// the node has not already been created, nodes are unique system-wide
		
		Node node = new Node();
		node.setName(nodeName);
		node.setHref(baseUri + nffgsPath + "/" + nffg + "/" + nodesPath + "/" + nodeName);
		node.setReachableHosts(createHyperlink(node.getHref() + "/" + reachableHostsPath));

		node.setVnf(createNamedRef(vnfs.get(type).getName(), vnfs.get(type).getHref()));
		node.setNffg(createNamedRef(nffg, baseUri + nffgsPath + "/" + nffg));
		node.setLinks(createHyperlink(node.getHref() + "/" + linksPath));

		
		it.polito.dp2.NFV.sol3.service.data.Node requestedNode = new it.polito.dp2.NFV.sol3.service.data.Node();
		Labels labels = new Labels();
		labels.getLabel().add("Node");
		it.polito.dp2.NFV.sol3.service.data.Properties properties = new it.polito.dp2.NFV.sol3.service.data.Properties();
		it.polito.dp2.NFV.sol3.service.data.Property name = new it.polito.dp2.NFV.sol3.service.data.Property();
		name.setName("name");
		name.setValue(nodeName);
		properties.getProperty().add(name);
		requestedNode.setProperties(properties);

		try {
		it.polito.dp2.NFV.sol3.service.data.Node createdNode = this.dataApi.node().postXml(requestedNode,
				it.polito.dp2.NFV.sol3.service.data.Node.class);

		if (createdNode == null)
			throw new ServiceException("Unable to create node with name " + nodeName);
		if (createdNode.getId() == null)
			throw new ServiceException("Unable to retrieve nodeID for node with name " + nodeName);

		// add labels for the node, replace if existing
		ClientResponse r = this.dataApi.nodeNodeidLabels(createdNode.getId()).putXml(labels, ClientResponse.class);
		if (r.getStatus() >= 400)
			throw new ServiceException("Unable to create labels for node with id " + createdNode.getId());



		if (exists) {
			//replace
			nodeIds.put(nodeName, createdNode.getId());
			nodeMap.put(createdNode.getId(), node);
			nffgNodes.get(nffg).put(nodeName, node);

		} else {
			nodeIds.put(nodeName, createdNode.getId());
			this.nodeMap.put(createdNode.getId(), node);
			this.nffgNodes.get(nffg).put(nodeName, node);
		}
		
		} catch (WebApplicationException e) {
			throw new ServiceException(e);
		}

		return node;
	}

	public Vnf createVnf(FunctionalType type, String name, BigInteger reqMemory, BigInteger reqStorage) {
		Vnf vnf = new Vnf();
		vnf.setFunctionalType(type);
		vnf.setName(name);
		vnf.setHref(baseUri + catalogPath + "/" + name);
		vnf.setRequiredMemory(reqMemory);
		vnf.setRequiredStorage(reqStorage);
		this.catalog.getVnf().add(vnf);
		this.vnfs.put(name, vnf);

		return vnf;
	}

	private synchronized boolean deleteAllocation(String nodeName) throws ServiceException {
		if (!isExistingNode(nodeName)) return false; //nothing to delete
		
		Node n = nodeMap.get(nodeIds.get(nodeName));
		
		nodeMap.get(nodeIds.get(nodeName)).setHost(null);  //delete allocation info from node
		
		if (n.getHost() != null) { //node has been allocated on host
		
			if (isExistingHost(n.getHost().getName())) {
				//delete info from host
				String id = hostIds.get(n.getHost().getName());
				/*
				Host h = hostMap.get(id);
				
				Vnf v = vnfs.get(n.getVnf().getName());
				
				int requiredMemory = (v.getRequiredMemory() != null) ? v.getRequiredMemory().intValue() : 0;
				int requiredStorage = (v.getRequiredStorage() != null) ? v.getRequiredStorage().intValue() : 0;
				int availableMemory = (h.getAvailableMemory() != null) ? h.getAvailableMemory().intValue() : 0;
				int availableStorage = (h.getAvailableStorage() != null) ? h.getAvailableStorage().intValue() : 0;
				
				hostMap.get(id).setAvailableMemory(BigInteger.valueOf(availableMemory - requiredMemory));
				hostMap.get(id).setAvailableStorage(BigInteger.valueOf(availableStorage - requiredStorage));
				*/
				hostMap.get(id).getDeployedNodes().getNode().removeIf(t->t.getName().equals(nodeName));
	
			}
		}
		
		try {
			Relationships rels = dataApi.nodeNodeidRelationshipsOut(nodeIds.get(n.getName())).getAsRelationships();
			for (Relationship r : rels.getRelationship()) {
				if (r.getType().equals("AllocatedOn")) {
					dataApi.relationshipRelationshipid(r.getId()).deleteAsXml(ClientResponse.class);
				}	
			}

			return true;
		} catch (WebApplicationException e) {
			throw new ServiceException(e);
		}	

	}

	private synchronized boolean deleteLinks(String graph, String node) throws ServiceException {
		//always called with existing graph and node names
		//immediately delete links from list, it won't be shown to callers
		nffgLinks.get(graph).getLink().removeIf(t -> t.getSrc().equals(node));
		try {
			Relationships rels = dataApi.nodeNodeidRelationshipsOut(nodeIds.get(node)).getAsRelationships();
			for (Relationship r : rels.getRelationship()) {
				if (r.getType().equals("ForwardsTo")) {
					dataApi.relationshipRelationshipid(r.getId()).deleteAsXml(ClientResponse.class);
					if (links.containsKey(r.getId())) {
						linkNames.get(graph).remove(links.get(r.getId()).getName());
						links.remove(r.getId());
					}
				}
			}
			
			return true;
			
		} catch (WebApplicationException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public synchronized boolean deleteNffg(String name) throws UnknownEntityException, ServiceException {
		
		if (!nffgMap.containsKey(name)) return false; //nothing to delete
		
		nffgLock.writeLock().lock();
		try {
			
			// remove nodes and relationships
			for (Node n : nffgNodes.get(name).values()) {
				deleteNode(nffgMap.get(name).getName(), n.getName());
			}

			this.nffgMap.remove(name);
			this.nffgNodes.remove(name);
			this.nffgLinks.remove(name);
			this.linkNames.remove(name);
			
			return true;
			
		} finally {
			nffgLock.writeLock().unlock();
		}
	}

	public synchronized boolean deleteNode(String graph, String n) throws UnknownEntityException, ServiceException {
		//cannot use function isDeployed, a node can be deleted after a deployment error as rollback
		if (!nffgMap.containsKey(graph)) throw new UnknownEntityException("NF-FG " + graph + " not defined");
		
		if (!isExistingNode(n)) return false; //nothing to delete
		
		deleteAllocation(n);
		deleteLinks(graph, n);
		this.dataApi.nodeNodeid(nodeIds.get(n)).deleteAsXml(ClientResponse.class);
		nffgNodes.get(graph).remove(n);
		nodeMap.remove(nodeIds.get(n));
		nodeIds.remove(n);
		
		return true;
			
	}

	// only a thread at a time will be able to perform deployment
	public synchronized Nffg deployNffg(NewNffg nffg)
			throws AlreadyLoadedException, UnknownEntityException, AllocationException, InvalidEntityException, DatatypeConfigurationException, ServiceException {

		boolean success = false;
		
		if (nffgMap.containsKey(nffg.getName())) throw new AlreadyLoadedException("NF-FG " + nffg.getName() + " already exists");

		if (nffg.getName() == null) throw new InvalidEntityException("No graph name provided");
		
		for (NewNffg.Nodes.Node node : nffg.getNodes().getNode()) {
			if (node.getName() == null) throw new InvalidEntityException("Invalid node descriptor, no name defined");
			if (node.getVnf() == null) throw new InvalidEntityException("Invalid vnf descriptor");
			if (node.getPreferredHost() != null) {
				if (!isExistingHost(node.getPreferredHost())) throw new UnknownEntityException("Host "+node.getPreferredHost()+" does not exist");
			}
			if (isExistingNode(node.getName())) throw new AlreadyLoadedException("Node "+node.getName()+" is already existing");
			if (!isExistingType(node.getVnf())) throw new UnknownEntityException("VNF type "+node.getVnf()+" does not exist");
			
		}
		
		for (NewNffg.Links.Link link : nffg.getLinks().getLink()) {
			if (link.getName() == null) throw new InvalidEntityException("Invalid link descriptor, no name defined");
			if (link.getSourceNode() == null) throw new InvalidEntityException("Invalid link descriptor, no source node defined");
			if (link.getDestinationNode() == null) throw new InvalidEntityException("Invalid link descriptor, no destination node defined");

			int reqLatency = (link.getRequiredLatency() == null) ? 0 : link.getRequiredLatency().intValue();
			float reqThr = link.getRequiredThroughput();

			if (reqLatency < 0)  throw new InvalidEntityException("Invalid required latency");
			if (reqThr < 0)  throw new InvalidEntityException("Invalid required throughput");
		}
		
		nffgLock.writeLock().lock();

		try {
			
			createNffg(nffg.getName());
			// load all nodes
			for (NewNffg.Nodes.Node node : nffg.getNodes().getNode()) {
				createNode(nffg.getName(), node.getName(), node.getVnf(), false);
				allocateNode(node.getName(), node.getPreferredHost());
			}

			for (NewNffg.Links.Link link : nffg.getLinks().getLink()) {
				if (!isExistingNode(link.getSourceNode())) throw new UnknownEntityException("Source node "+link.getSourceNode()+" does not exist");
				if (!isExistingNode(link.getSourceNode())) throw new UnknownEntityException("Source node "+link.getDestinationNode()+" does not exist");
				int reqLatency = (link.getRequiredLatency() == null) ? 0 : link.getRequiredLatency().intValue();
				float reqThr = (link.getRequiredThroughput());
				createLink(nffg.getName(), link.getName(), link.getSourceNode(), link.getDestinationNode(),
						reqLatency, reqThr, false);
			}

			nffgMap.get(nffg.getName())
					.setDeployTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
			success = true;
			return nffgMap.get(nffg.getName());
		}

		finally {
			// cleanup stack - delete objects if deployment unsuccessful
			if (!success)
				deleteNffg(nffg.getName());
			nffgLock.writeLock().unlock();
		}

	}

	public Catalog getCatalog() {
		return this.catalog;
	}

	public Connections getConnections(String hostName) throws UnknownEntityException {
		if (!hostIds.containsKey(hostName)) throw new UnknownEntityException("No connections defined for hostname " + hostName);
			
		String id = hostIds.get(hostName);
			
		if (!connections.containsKey(id)) throw new UnknownEntityException("No host loaded with name " + hostName + " and ID " + id);
				
		return connections.get(id);
	
	}

	public Host getHostByName(String name) throws UnknownEntityException {
		if (!hostIds.containsKey(name)) throw new UnknownEntityException("No host defined with the name " + name);
			
		String id = this.hostIds.get(name);
		
		if (!hostMap.containsKey(id)) throw new UnknownEntityException("No host loaded with name " + name + " and ID " + id);
				
		return hostMap.get(id);
			
	}

	public Hosts getHosts() {
		return this.hosts;
	}

	public Link getLink(String graphName, String linkName) throws UnknownEntityException {
		if (!isDeployed(graphName)) throw new UnknownEntityException("No NF-FG defined with name " + graphName);
		
		if (!linkNames.containsKey(graphName)) throw new UnknownEntityException("No NF-FG defined with name " + graphName);
		
		if (!linkNames.get(graphName).containsKey(linkName)) throw new UnknownEntityException("No link defined with name " + linkName + " in NF-FG " + graphName);
				
		return linkNames.get(graphName).get(linkName);
	
	}

	public Links getLinks(String graph, String node) throws UnknownEntityException {
		
		if (!isDeployed(graph)) throw new UnknownEntityException("No NF-FG defined with name " + graph);
		
		if (node == null)
				return this.nffgLinks.get(graph);

		if (!nodeIds.containsKey(node)) throw new UnknownEntityException("No node defined with name " + node);
			

		it.polito.dp2.NFV.sol3.service.data.Relationships linksFromNode = this.dataApi
				.nodeNodeidRelationshipsOut(nodeIds.get(node)).getAsRelationships();
		Links outLinks = new Links();
		for (Relationship r : linksFromNode.getRelationship()) {
			outLinks.getLink().add(links.get(r.getId()));
		}
		return outLinks;
	
	}

	public Nffg getNffgByName(String name) throws UnknownEntityException {
		if (name == null || !isDeployed(name)) throw new UnknownEntityException("No NF-FG defined with name " + name);
		
		nffgLock.readLock().lock();
		try {
			return nffgMap.get(name);
		} finally {
			nffgLock.readLock().unlock();
		}
	}

	public Nffgs getNffgs(Date from) {
		Nffgs set = new Nffgs();
		nffgLock.readLock().lock();
		try {
			// always only deployed nffgs are shown. In case of an incomplete deployment
			// a nffg will be present in the list but never shown GETting the resource,
			// in this way the calling function has possibility of deleting the nffg
			for (Nffg n : nffgMap.values()) {
				if (n.getDeployTime() != null) {
					if (from == null) {
						set.getNffg().add(createNamedRef(n.getName(), baseUri + nffgsPath + "/" + n.getName()));
					} else {
						if (nffgMap.get(n.getName()).getDeployTime().toGregorianCalendar().getTime()
								.compareTo(from) >= 0) {
							set.getNffg().add(createNamedRef(n.getName(), baseUri + nffgsPath + "/" + n.getName()));
						}
					}
				}
			}

			return set;
		} finally {
			nffgLock.readLock().unlock();
		}

	}

	public Nfv getNfv() {
		return this.nfv;
	}

	public Node getNode(String graph, String name) throws UnknownEntityException {
		if (!isDeployed(graph)) throw new UnknownEntityException("No NF-FG defined with name " + graph);
		if (!nodeIds.containsKey(name)) throw new UnknownEntityException("No node defined for name " + name);
		
		String id = nodeIds.get(name);
		
		if (!nodeMap.containsKey(id)) throw new UnknownEntityException("No node loaded with name " + name + " and ID " + id);
			
		return nodeMap.get(id);		
			
	}

	public Nodes getNodes(String graph) throws UnknownEntityException {

		if (graph == null || !isDeployed(graph)) throw new UnknownEntityException("No nffg defined with name " + graph);

		Nodes nodes = new Nodes();
		for (Node n : nffgNodes.get(graph).values()) {
			NamedEntity ent = new NamedEntity();
			ent.setName(n.getName());
			ent.setHref(n.getHref());
			nodes.getNode().add(ent);
		}
		return nodes;	
	}
	
	
	public Nodes getAllNodes() {

		Nodes allNodes = new Nodes();
		for (String g : nffgNodes.keySet()) {
			if (isDeployed(g)) {
				for (Node n : nffgNodes.get(g).values()) {
					NamedEntity ent = new NamedEntity();
					ent.setName(n.getName());
					ent.setHref(n.getHref());
					allNodes.getNode().add(ent);
				}
			}
		}
		return allNodes;
	}

	public Hosts getReachableHosts(String graph, String nodeName) throws UnknownEntityException, ServiceException {
		if (!isDeployed(graph)) throw new UnknownEntityException("No nffg defined with name " + graph);
		if (!nodeIds.containsKey(nodeName)) throw new UnknownEntityException("No node defined for name " + nodeName);

		Hosts result = new Hosts();

		// call reachablenodes api
		it.polito.dp2.NFV.sol3.service.data.Nodes reachableHosts = dataApi.nodeNodeidReachableNodes(nodeIds.get(nodeName))
				.getAsNodes(null, "Host");
		for (it.polito.dp2.NFV.sol3.service.data.Nodes.Node n : reachableHosts.getNode()) {
			String hostName = null;
			for (Property p : n.getProperties().getProperty()) {
				if (p.getName().equals("name"))
					hostName = p.getValue();
			}
			if (hostName == null)
				throw new ServiceException("Node " + n.getId() + " has no property 'name'");
			Host h = this.hostMap.get(n.getId());
			result.getHost().add(createNamedRef(h.getName(), h.getHref()));
		}

		return result;

			
	}

	public Vnf getVnfByName(String name) throws UnknownEntityException {
		if (vnfs.containsKey(name))
			return vnfs.get(name);
		else
			throw new UnknownEntityException("No VNF defined with the name " + name);
	}

	public boolean isDeployed(String name) {
		if (!nffgMap.containsKey(name))
			return false;
		return (nffgMap.get(name).getDeployTime() != null);
	}
	
	public boolean isExistingNode(String name) {
		if (name == null) return false;
		if (!nodeIds.containsKey(name)) return false;
		if (!nodeMap.containsKey(nodeIds.get(name))) return false;
		
		return true;
	}
	
	public boolean isExistingHost(String name) {
		if (name == null) return false;
		if (!hostIds.containsKey(name)) return false;
		if (!hostMap.containsKey(hostIds.get(name))) return false;
		
		return true;
	}
	
	public boolean isExistingType(String name) {
		if (name == null) return false;
		if (!vnfs.containsKey(name)) return false;
		
		return true;
	}
	
	public boolean isExistingLink(String graph, String name) {
		if (name == null) return false;
		if (!linkNames.containsKey(graph)) return false;
		if (!linkNames.get(graph).containsKey(name)) return false;
		return true;
	}

}
