package it.polito.dp2.NFV.sol3.service;

import java.math.BigInteger;
import java.net.URI;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import com.sun.jersey.api.client.ClientResponse;
import it.polito.dp2.NFV.sol3.service.NotDefinedException;
import it.polito.dp2.NFV.FactoryConfigurationError;
import it.polito.dp2.NFV.HostReader;
import it.polito.dp2.NFV.LinkReader;
import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.NfvReader;
import it.polito.dp2.NFV.NfvReaderException;
import it.polito.dp2.NFV.NfvReaderFactory;
import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.VNFTypeReader;
import it.polito.dp2.NFV.sol3.model.*;
import it.polito.dp2.NFV.sol3.model.Host.DeployedNodes;
import it.polito.dp2.NFV.sol3.data.Labels;
import it.polito.dp2.NFV.sol3.data.Neo4JSimpleXML;
import it.polito.dp2.NFV.sol3.data.Neo4JSimpleXML.Data;
import it.polito.dp2.NFV.sol3.data.Neo4JSimpleXML.Data.RelationshipRelationshipid;
import it.polito.dp2.NFV.sol3.data.Properties;
import it.polito.dp2.NFV.sol3.data.Property;
import it.polito.dp2.NFV.sol3.data.Relationships;
import it.polito.dp2.NFV.sol3.data.Relationships.Relationship;

public class NfvDeployer {
	public static final String catalogPath = "catalog";
	public static final String connectionsPath = "connections";
	public static final String hostsPath = "hosts";
	public static final String linksPath = "links";
	public static final String nffgsPath = "nffgs";
	public static final String nodesPath = "nodes";
	public static final String reachableHostsPath = "reachableHosts";
	public static final String deploymentsPath = "deployments";
	public static final String undeploymentsPath = "undeployments";

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

	private NfvDeployer() throws DatatypeConfigurationException, NfvReaderException, FactoryConfigurationError,
			InternalServerErrorException {
		System.out.println("Starting up...");
		try {
			System.setProperty("it.polito.dp2.NFV.NfvReaderFactory", "it.polito.dp2.NFV.Random.NfvReaderFactoryImpl");
			System.setProperty("it.polito.dp2.NFV.lab3.Neo4JSimpleXMLURL",
					"http://localhost:8080/Neo4JSimpleXML/webapi");
			baseUri = System.getProperty("it.polito.dp2.NFV.lab3.URL", "http://localhost:8080/NfvDeployer/rest/");
			dataUri = System.getProperty("it.polito.dp2.NFV.lab3.Neo4JSimpleXMLURL",
					"http://localhost:8080/Neo4JSimpleXML/rest");

			reader = NfvReaderFactory.newInstance().newNfvReader();
			dataApi = Neo4JSimpleXML.data(Neo4JSimpleXML.createClient(), URI.create(dataUri));

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
			/*
			 * this.linkIds.put(graph.getName(), new HashMap<>());
			 * this.nffgMap.put(graph.getName(), createNffg(graph.getName(), baseUri +
			 * nffgsPath + "/" + graph.getName())); for (NodeReader node : graph.getNodes())
			 * { try { // create node createNode(graph.getName(), node.getName(),
			 * node.getFuncType().getName(), false);
			 * 
			 * } catch (AlreadyLoadedException e) { // continue, the node is already loaded
			 * but nodes are system-wide } catch (NotDefinedException e) {
			 * e.printStackTrace(); throw new InternalServerErrorException(e); } } // create
			 * NF-FGs links for (NodeReader node : graph.getNodes()) { // create
			 * relationships for (LinkReader link : node.getLinks()) { // create links
			 * having the node as source try { createLink(graph.getName(), link.getName(),
			 * link.getSourceNode().getName(), link.getDestinationNode().getName(),
			 * link.getLatency(), link.getThroughput(), false); } catch
			 * (AlreadyLoadedException e) { throw new InternalServerErrorException(e); } } }
			 */

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
		} catch (WebApplicationException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new InternalServerErrorException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new InternalServerErrorException(e);
		}

	}

	private boolean allocateNode(String nodeName, String preferenceHost) throws WebApplicationException {
		// TODO Auto-generated method stub
		if (nodeIds.containsKey(nodeName)) {

			if (preferenceHost != null && !hostIds.containsKey(preferenceHost))
				throw new NotFoundException("Host " + preferenceHost + " not found");

			// if the node is not allocated link the node to the host
			if (nodeMap.get(nodeIds.get(nodeName)).getHost() == null) {
				Host h;
				Node n = nodeMap.get(nodeIds.get(nodeName));
				Vnf v = vnfs.get(n.getVnf().getName());
				it.polito.dp2.NFV.sol3.data.Relationship allocatedOn = new it.polito.dp2.NFV.sol3.data.Relationship();
				
				if (preferenceHost != null) {
					// try allocating to the preference host
					h = hostMap.get(hostIds.get(preferenceHost));
					if (v.getRequiredMemory().add(h.getAllocatedMemory()).compareTo(h.getAvailableMemory()) <= 0
							&& v.getRequiredMemory().add(h.getAllocatedMemory()).compareTo(h.getAvailableMemory()) <= 0
							&& h.getDeployedNodes().getNode().size() < h.getMaxVNFs().intValue()) {
						allocatedOn.setDstNode(this.hostIds.get(preferenceHost));
						allocatedOn.setType("AllocatedOn");
						hostMap.get(hostIds.get(preferenceHost)).setAllocatedMemory(h.getAllocatedMemory().add(v.getRequiredMemory()));
						hostMap.get(hostIds.get(preferenceHost)).setAllocatedStorage(h.getAllocatedStorage().add(v.getRequiredStorage()));
						hostMap.get(hostIds.get(preferenceHost)).getDeployedNodes().getNode().add(createNamedRef(nodeName, n.getHref()));
						// create relationship
						nodeMap.get(nodeIds.get(nodeName)).setHost(createNamedRef(h.getName(), h.getHref()));
						this.dataApi.nodeNodeidRelationships(this.nodeIds.get(nodeName))
								.postXml(allocatedOn, it.polito.dp2.NFV.sol3.data.Relationship.class);
						return true;
					
					}
				}
				for (Host hnew : hostMap.values()) {
					if (v.getRequiredMemory().add(hnew.getAllocatedMemory())
							.compareTo(hnew.getAvailableMemory()) <= 0
							&& v.getRequiredMemory().add(hnew.getAllocatedMemory())
									.compareTo(hnew.getAvailableMemory()) <= 0
							&& hnew.getDeployedNodes().getNode().size() < hnew.getMaxVNFs().intValue()) {
						allocatedOn.setDstNode(this.hostIds.get(hnew.getName()));
						allocatedOn.setType("AllocatedOn");
						hostMap.get(hostIds.get(hnew.getName())).setAllocatedMemory(hnew.getAllocatedMemory().add(v.getRequiredMemory()));
						hostMap.get(hostIds.get(hnew.getName())).setAllocatedStorage(hnew.getAllocatedStorage().add(v.getRequiredStorage()));
						hostMap.get(hostIds.get(hnew.getName())).getDeployedNodes().getNode().add(createNamedRef(nodeName, n.getHref()));
						nodeMap.get(nodeIds.get(nodeName)).setHost(createNamedRef(hnew.getName(), hnew.getHref()));
						// create relationship
						this.dataApi.nodeNodeidRelationships(this.nodeIds.get(nodeName))
								.postXml(allocatedOn, it.polito.dp2.NFV.sol3.data.Relationship.class);
						return true;
					}
				}

				throw new ConflictException();

			} else
				throw new ConflictException();

		} else
			throw new NotFoundException("Node " + nodeName + " not found");
	}

	public String createHost(String hostName, Integer availableMemory, Integer availableStorage, Integer maxVnfs)
			throws AlreadyLoadedException, WebApplicationException {

		if (!this.hostIds.containsKey(hostName)) {

			// the host has not already been created in the database
			it.polito.dp2.NFV.sol3.data.Node requestedHost = new it.polito.dp2.NFV.sol3.data.Node();
			Labels labels = new Labels();
			labels.getLabel().add("Host");
			Properties properties = new Properties();
			Property name = new Property();
			name.setName("name");
			name.setValue(hostName);
			properties.getProperty().add(name);
			requestedHost.setProperties(properties);

			it.polito.dp2.NFV.sol3.data.Node createdHost = this.dataApi.node().postXml(requestedHost,
					it.polito.dp2.NFV.sol3.data.Node.class);

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
			h.setAllocatedMemory(BigInteger.valueOf(0));
			h.setAllocatedStorage(BigInteger.valueOf(0));
			h.setConnections(createHyperlink(baseUri + hostsPath + "/" + hostName + "/" + connectionsPath));
			h.setDeployedNodes(new DeployedNodes());

			this.hostIds.put(hostName, createdHost.getId());
			this.hostMap.put(createdHost.getId(), h);
			this.hosts.getHost().add(createNamedRef(hostName, baseUri + hostsPath + "/" + hostName));
			this.connections.put(createdHost.getId(), new Connections());

			// add labels to the node, replace if existing
			ClientResponse r = this.dataApi.nodeNodeidLabels(createdHost.getId()).putXml(labels, ClientResponse.class);
			if (r.getStatus() >= 400)
				throw new WebApplicationException("Unable to create labels for node with id " + createdHost.getId());

			return createdHost.getId();

		} else
			throw new AlreadyLoadedException("Host " + hostName + " already loaded");
	}

	private Hyperlink createHyperlink(String ref) {
		Hyperlink l = new Hyperlink();
		l.setHref(ref);
		return l;
	}

	public Link createLink(String graphName, String linkName, String srcNode, String dstNode, int minLatency,
			float minThroughput, boolean replace) throws AlreadyLoadedException, NotFoundException {
		// TODO
		if (linkNames.containsKey(graphName)) {

			boolean exists = linkNames.get(graphName).containsKey(linkName);
			if (exists && !replace)
				throw new AlreadyLoadedException("Link " + linkName + " already loaded");

			it.polito.dp2.NFV.sol3.data.Relationship requestedLink = new it.polito.dp2.NFV.sol3.data.Relationship();
			
			if (!this.nodeIds.containsKey(dstNode)) throw new ClientErrorException("Destination node not found", 422);
			if (!this.nodeIds.containsKey(srcNode)) throw new ClientErrorException("Source node not found", 422);
			
			requestedLink.setDstNode(this.nodeIds.get(dstNode));
			requestedLink.setType("ForwardsTo");

			it.polito.dp2.NFV.sol3.data.Relationship createdLink = this.dataApi
					.nodeNodeidRelationships(this.nodeIds.get(srcNode))
					.postXml(requestedLink, it.polito.dp2.NFV.sol3.data.Relationship.class);

			Link l = new Link();
			l.setName(linkName);
			l.setHref(baseUri + nffgsPath + "/" + graphName + "/" + NfvDeployer.linksPath + "/" + linkName);
			l.setSrc(srcNode);
			l.setDst(dstNode);
			l.setRequiredLatency(BigInteger.valueOf(minLatency));
			l.setRequiredThroughput(minThroughput);

			if (exists) {
				if (replace) {
					List<Link> list = nffgLinks.get(graphName).getLink();
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).getName().equals(l.getName())) {
							list.remove(i);
							list.add(l);
						}
					}
					links.put(createdLink.getId(), l);
					linkNames.get(graphName).put(linkName, l);
				} else {
					// should not happen
					throw new AlreadyLoadedException("Link " + linkName + " already loaded");
				}
			} else {
				nffgLinks.get(graphName).getLink().add(l);
				links.put(createdLink.getId(), l);
				linkNames.get(graphName).put(linkName, l);
			}

			return l;

		}
		throw new NotFoundException("NF-FG " + graphName + " not found");

	}

	private NamedEntity createNamedRef(String name, String ref) {
		NamedEntity e = new NamedEntity();
		e.setHref(ref);
		e.setName(name);
		return e;
	}

	public Nffg createNffg(String name) throws DatatypeConfigurationException {
		Nffg nffg = new Nffg();
		nffgLock.writeLock().lock();
		try {
			nffg.setName(name);
			nffg.setHref(baseUri + nffgsPath + "/" + name);
			nffg.setNodes(createHyperlink(baseUri + nffgsPath + "/" + name + "/" + nodesPath));
			nffg.setLinks(createHyperlink(baseUri + nffgsPath + "/" + name + "/" + NfvDeployer.linksPath));

			this.nffgNodes.put(name, new ConcurrentHashMap<>());

			this.nffgLinks.put(name, new Links());
			this.linkNames.put(nffg.getName(), new ConcurrentHashMap<>());
			this.nffgMap.put(nffg.getName(), nffg);

			return nffg;
		} finally {
			nffgLock.writeLock().unlock();
		}
	}

	public void deleteNffg(String name) throws NotDefinedException {
		nffgLock.writeLock().lock();
		try {
			if (nffgMap.containsKey(name)) {
				// remove relationships
				for (Node n : nffgNodes.get(name).values()) {
					deleteNode(nffgMap.get(name).getName(), n.getName());
				}

				this.nffgMap.remove(name);
				this.nffgNodes.remove(name);
				this.nffgLinks.remove(name);
				this.linkNames.remove(name);
			}
		} finally {
			nffgLock.writeLock().unlock();
		}
	}

	private boolean deleteLinks(String graph, String node) {
		// TODO Auto-generated method stub
		Relationships rels = dataApi.nodeNodeidRelationshipsOut(nodeIds.get(node)).getAsRelationships();
		for (Relationship r : rels.getRelationship()) {
			if (r.getType().equals("ForwardsTo")) {
				dataApi.relationshipRelationshipid(r.getId()).deleteAsXml(ClientResponse.class);
				linkNames.get(graph).remove(links.get(r.getId()).getName());
				links.remove(r.getId());
			}
		}

		nffgLinks.get(graph).getLink().removeIf(t -> t.getSrc().equals(node));

		return true;
	}

	public Node createNode(String nffg, String nodeName, String type, boolean replace)
			throws AlreadyLoadedException, NotDefinedException {
		boolean exists = this.nodeIds.containsKey(nodeName);

		if (exists && !replace)
			throw new AlreadyLoadedException("Node " + nodeName + " already loaded");
		// the node has not already been created, nodes are unique system-wide

		if (!vnfs.containsKey(type))
			throw new NotDefinedException("VNF type " + type + " does not exist");

		it.polito.dp2.NFV.sol3.data.Node requestedNode = new it.polito.dp2.NFV.sol3.data.Node();
		Labels labels = new Labels();
		labels.getLabel().add("Node");
		it.polito.dp2.NFV.sol3.data.Properties properties = new it.polito.dp2.NFV.sol3.data.Properties();
		it.polito.dp2.NFV.sol3.data.Property name = new it.polito.dp2.NFV.sol3.data.Property();
		name.setName("name");
		name.setValue(nodeName);
		properties.getProperty().add(name);
		requestedNode.setProperties(properties);

		it.polito.dp2.NFV.sol3.data.Node createdNode = this.dataApi.node().postXml(requestedNode,
				it.polito.dp2.NFV.sol3.data.Node.class);

		if (createdNode == null)
			throw new WebApplicationException("Unable to create node with name " + nodeName);
		if (createdNode.getId() == null)
			throw new WebApplicationException("Unable to retrieve nodeID for node with name " + nodeName);

		// add labels for the node, replace if existing
		ClientResponse r = this.dataApi.nodeNodeidLabels(createdNode.getId()).putXml(labels, ClientResponse.class);
		if (r.getStatus() >= 400)
			throw new WebApplicationException("Unable to create labels for node with id " + createdNode.getId());

		Node node = new Node();
		node.setName(nodeName);
		node.setHref(baseUri + nffgsPath + "/" + nffg + "/" + nodesPath + "/" + nodeName);
		node.setReachableHosts(createHyperlink(node.getHref() + "/" + reachableHostsPath));
		NamedEntity vnfRef = new NamedEntity();

		vnfRef.setName(vnfs.get(type).getName());
		vnfRef.setHref(vnfs.get(type).getHref());
		node.setVnf(vnfRef);
		node.setLinks(createHyperlink(node.getHref() + "/" + linksPath));

		if (exists) {
			if (replace) {
				nodeIds.put(nodeName, createdNode.getId());
				nodeMap.put(createdNode.getId(), node);
				nffgNodes.get(nffg).put(nodeName, node);
			} else
				// should not happen
				throw new AlreadyLoadedException("Node " + nodeName + " already loaded");

		} else {
			nodeIds.put(nodeName, createdNode.getId());
			this.nodeMap.put(createdNode.getId(), node);
			this.nffgNodes.get(nffg).put(nodeName, node);
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

	public boolean deleteNode(String graph, String n) throws NotDefinedException {
		if (nffgMap.containsKey(graph)) {
			if (nodeIds.containsKey(n)) {
				deleteAllocation(n);
				deleteLinks(graph, n);
				this.dataApi.nodeNodeid(nodeIds.get(n)).deleteAsXml(ClientResponse.class);
				nffgNodes.get(graph).remove(n);
				nodeMap.remove(nodeIds.get(n));
				nodeIds.remove(n);

				return true;
			}
			return false;

		} else
			throw new NotDefinedException("NF-FG " + graph + " not defined");
	}

	public void undeployNffg(String name) {
		throw new ServerErrorException(501);
	}

	// only a thread at a time will be able to perform deployment
	public synchronized Nffg deployNffg(NewNffg nffg)
			throws AlreadyLoadedException, NotDefinedException, ConflictException {
		// TODO Auto-generated method stub
		boolean success = false;

		if (nffg.getName() == null)
			throw new ClientErrorException(422);

		try {
			if (!nffgMap.containsKey(nffg.getName())) {
				createNffg(nffg.getName());
				// load all nodes
				for (it.polito.dp2.NFV.sol3.model.NewNffg.Nodes.Node node : nffg.getNodes().getNode()) {
					if (node.getName() == null || node.getVnf() == null)
						throw new ClientErrorException(422);
					createNode(nffg.getName(), node.getName(), node.getVnf(), false);
					allocateNode(node.getName(), node.getPreferredHost());
				}

				for (it.polito.dp2.NFV.sol3.model.NewNffg.Links.Link link : nffg.getLinks().getLink()) {
					if (link.getName() == null || link.getSourceNode() == null || link.getDestinationNode() == null)
						throw new ClientErrorException(422);
					int reqLatency = (link.getRequiredLatency() == null) ? 0 : link.getRequiredLatency().intValue();
					float reqThr = (link.getRequiredThroughput());
					createLink(nffg.getName(), link.getName(), link.getSourceNode(), link.getDestinationNode(),
							reqLatency, reqThr, false);
				}

				nffgMap.get(nffg.getName())
						.setDeployTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
				success = true;
				return nffgMap.get(nffg.getName());
			} else {
				// the nffg exists, is a conflict. In any case, nothing is deleted
				success = true;
				throw new ConflictException("NF-FG " + nffg.getName() + " already exists", 409);
			}
		} catch (DatatypeConfigurationException e1) {
			e1.printStackTrace();
			throw new InternalServerErrorException(e1);
		}

		finally {
			// cleanup stack - delete objects if deployment unsuccessful
			if (!success)
				deleteNffg(nffg.getName());
		}

	}

	private boolean deleteAllocation(String nodeName) {
		Node n = nodeMap.get(nodeIds.get(nodeName));
		if (n.getHost() != null) {
			Relationships rels = dataApi.nodeNodeidRelationshipsOut(nodeIds.get(n.getName())).getAsRelationships();
			for (Relationship r : rels.getRelationship()) {
				if (r.getType().equals("AllocatedOn")) {
					dataApi.relationshipRelationshipid(r.getId()).deleteAsXml(ClientResponse.class);
				}
				
			}	

			if (hostIds.containsKey(n.getHost().getName())) {
				Host h = hostMap.get(hostIds.get(n.getHost().getName()));
				hostMap.get(hostIds.get(n.getHost().getName())).setAllocatedMemory(
						h.getAllocatedMemory().subtract(vnfs.get(n.getVnf().getName()).getRequiredMemory()));
				hostMap.get(hostIds.get(n.getHost().getName())).setAllocatedStorage(
						h.getAllocatedStorage().subtract(vnfs.get(n.getVnf().getName()).getRequiredStorage()));
				hostMap.get(hostIds.get(n.getHost().getName())).getDeployedNodes().getNode().removeIf(t->t.getName().equals(nodeName));
				nodeMap.get(nodeIds.get(nodeName)).setHost(null);

				return true;
			}

			return false;
		}
		return false;

	}

	public boolean isDeployed(String name) {
		if (!nffgMap.containsKey(name))
			return false;
		return (nffgMap.get(name).getDeployTime() != null);
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

	public Link getLink(String graphName, String linkName) {
		if (linkNames.containsKey(graphName)) {
			if (linkNames.get(graphName).containsKey(linkName)) {
				return linkNames.get(graphName).get(linkName);
			}
			throw new NotFoundException("No link defined with name " + linkName + " in NF-FG " + graphName);
		} else
			throw new NotFoundException("No NF-FG defined with name " + graphName);
	}

	public Links getLinks(String graph, String node) {
		// TODO Auto-generated method stub
		if (nffgNodes.containsKey(graph)) {
			if (node == null)
				return this.nffgLinks.get(graph);

			if (nodeIds.containsKey(node)) {

				it.polito.dp2.NFV.sol3.data.Relationships linksFromNode = this.dataApi
						.nodeNodeidRelationshipsOut(nodeIds.get(node)).getAsRelationships();
				Links outLinks = new Links();
				for (Relationship r : linksFromNode.getRelationship()) {
					outLinks.getLink().add(links.get(r.getId()));
				}
				return outLinks;

			} else
				throw new NotFoundException("No node defined with name " + node);
		} else
			throw new NotFoundException("No NF-FG defined with name " + graph);
	}

	public Nffg getNffgByName(String name) {
		nffgLock.readLock().lock();
		try {
			if (nffgMap.containsKey(name)) {
				if (isDeployed(name)) {

					return nffgMap.get(name);
				}
			}

			throw new NotFoundException("No NF-FG defined with name " + name);
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

	public Node getNode(String graph, String name) {
		if (!isDeployed(graph))
			throw new NotFoundException("No NF-FG defined with name " + graph);
		if (nodeIds.containsKey(name)) {
			String id = nodeIds.get(name);
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

		if (nffgMap.containsKey(graph)) {
			Nodes nodes = new Nodes();
			if (isDeployed(graph)) {
				for (Node n : nffgNodes.get(graph).values()) {
					NamedEntity ent = new NamedEntity();
					ent.setName(n.getName());
					ent.setHref(n.getHref());
					nodes.getNode().add(ent);
				}
			}
			return nodes;
		} else
			throw new NotFoundException("No nffg defined with name " + graph);
	}

	public Hosts getReachableHosts(String graph, String nodeName) {
		if (!isDeployed(graph))
			throw new NotFoundException("No nffg defined with name " + graph);
		if (nodeIds.containsKey(nodeName)) {

			Hosts result = new Hosts();

			// call reachablenodes api
			it.polito.dp2.NFV.sol3.data.Nodes reachableHosts = dataApi.nodeNodeidReachableNodes(nodeIds.get(nodeName))
					.getAsNodes(null, "Host");
			for (it.polito.dp2.NFV.sol3.data.Nodes.Node n : reachableHosts.getNode()) {
				String hostName = null;
				for (Property p : n.getProperties().getProperty()) {
					if (p.getName().equals("name"))
						hostName = p.getValue();
				}
				if (hostName == null)
					throw new InternalServerErrorException("Node " + n.getId() + " has no property 'name'");
				Host h = this.hostMap.get(n.getId());
				result.getHost().add(createNamedRef(h.getName(), h.getHref()));
			}

			return result;

		} else
			throw new NotFoundException("No node defined for name " + nodeName);
	}

	public Vnf getVnfByName(String name) {
		if (vnfs.containsKey(name))
			return vnfs.get(name);
		else
			throw new NotFoundException("No VNF defined with the name " + name);
	}

}
