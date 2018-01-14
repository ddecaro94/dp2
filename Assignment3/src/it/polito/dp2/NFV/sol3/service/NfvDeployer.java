package it.polito.dp2.NFV.sol3.service;

import java.math.BigInteger;
import java.net.URI;
import java.rmi.ServerError;
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

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.WebApplicationException;
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
import it.polito.dp2.NFV.sol3.data.Properties;
import it.polito.dp2.NFV.sol3.data.Property;
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
	private ConcurrentMap<String, Map<String, Link>> linkIds = new ConcurrentHashMap<>(); // (nffg, (linkame, Link))
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
				createVnf(FunctionalType.fromValue(t.getFunctionalType().toString()), t.getName()
						, BigInteger.valueOf(t.getRequiredMemory()),
						BigInteger.valueOf(t.getRequiredStorage()));

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
			this.linkIds.put(graph.getName(), new HashMap<>());
			this.nffgMap.put(graph.getName(),
					createNffg(graph.getName(), baseUri + nffgsPath + "/" + graph.getName()));
			for (NodeReader node : graph.getNodes()) {
				try {
					// create node
					createNode(graph.getName(), node.getName(), node.getFuncType().getName(), false);

				} catch (AlreadyLoadedException e) {
					// continue, the node is already loaded but nodes are system-wide
				} catch (NotDefinedException e) {
					e.printStackTrace();
					throw new InternalServerErrorException(e);
				}
			}
			// create NF-FGs links
			for (NodeReader node : graph.getNodes()) {
				// create relationships
				for (LinkReader link : node.getLinks()) {
					// create links having the node as source
					try {
						createLink(graph.getName(), link.getName(), link.getSourceNode().getName(),
								link.getDestinationNode().getName(), link.getLatency(), link.getThroughput(), false);
					} catch (AlreadyLoadedException e) {
						throw new InternalServerErrorException(e);
					}
				}
			}
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

	private String allocateNode(String nodeName, String preferenceHost) throws WebApplicationException {
		// TODO Auto-generated method stub

		if (nodeIds.containsKey(nodeName)) {
			// if the node is not allocated link the node to the host
			if (nodeMap.get(nodeIds.get(nodeName)).getHost() == null) {

				if (hostIds.containsKey(preferenceHost)) {
					it.polito.dp2.NFV.sol3.data.Relationship allocatedOn = new it.polito.dp2.NFV.sol3.data.Relationship();
					Host h = hostMap.get(hostIds.get(preferenceHost));
					Node n = nodeMap.get(nodeIds.get(nodeName));
					Vnf v = vnfs.get(n.getVnf().getName());
					
					if ( v.getRequiredMemory().add(h.getAllocatedMemory()).compareTo(h.getAvailableMemory()) <= 0 && v.getRequiredMemory().add(h.getAllocatedMemory()).compareTo(h.getAvailableMemory()) <= 0) {
						allocatedOn.setDstNode(this.hostIds.get(preferenceHost));
						allocatedOn.setType("AllocatedOn");
						h.setAllocatedMemory(h.getAllocatedMemory().add(v.getRequiredMemory()));
						h.setAllocatedStorage(h.getAllocatedStorage().add(v.getRequiredStorage()));
						h.getDeployedNodes().getNode().add(createNamedRef(nodeName, n.getHref()));
						// create relationship
						return this.dataApi.nodeNodeidRelationships(this.nodeIds.get(nodeName)).postXml(allocatedOn,
								it.polito.dp2.NFV.sol3.data.Relationship.class).getId();
					} else {
						for (Host hnew : hostMap.values()) {
							if ( v.getRequiredMemory().add(hnew.getAllocatedMemory()).compareTo(hnew.getAvailableMemory()) <= 0 && v.getRequiredMemory().add(hnew.getAllocatedMemory()).compareTo(hnew.getAvailableMemory()) <= 0) {
								allocatedOn.setDstNode(this.hostIds.get(hnew.getName()));
								allocatedOn.setType("AllocatedOn");
								hnew.setAllocatedMemory(hnew.getAllocatedMemory().add(v.getRequiredMemory()));
								hnew.setAllocatedStorage(hnew.getAllocatedStorage().add(v.getRequiredStorage()));
								hnew.getDeployedNodes().getNode().add(createNamedRef(nodeName, n.getHref()));
								// create relationship
								return this.dataApi.nodeNodeidRelationships(this.nodeIds.get(nodeName)).postXml(allocatedOn,
										it.polito.dp2.NFV.sol3.data.Relationship.class).getId();
							}
						}
						throw new ConflictException();
					}
					
	
				} else new NotFoundException("Host " + preferenceHost + " not found");
				
				
				
			} throw new ConflictException();
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
			h.setConnections(createHyperlink(baseUri + hostsPath + "/" + hostName+ "/"+ connectionsPath));
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
		if (linkIds.containsKey(graphName)) {
			boolean exists = linkIds.get(graphName).containsKey(linkName);
			if (exists && !replace) throw new AlreadyLoadedException("Link " + linkName + " already loaded");
			
				it.polito.dp2.NFV.sol3.data.Relationship requestedLink = new it.polito.dp2.NFV.sol3.data.Relationship();

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
						linkIds.get(graphName).put(linkName, l);
					} else {
						//should not happen
						throw new AlreadyLoadedException("Link " + linkName + " already loaded");
					}
				} else {
					nffgLinks.get(graphName).getLink().add(l);
					links.put(createdLink.getId(), l);
					linkIds.get(graphName).put(linkName, l);
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

	public Nffg createNffg(String name, String ref) throws DatatypeConfigurationException {
		Nffg nffg = new Nffg();
		nffgLock.writeLock().lock();
		try {
		nffg.setName(name);
		nffg.setHref(ref);
		nffg.setNodes(createHyperlink(baseUri + nffgsPath + "/" + name + "/" + nodesPath));
		nffg.setLinks(createHyperlink(baseUri + nffgsPath + "/" + name + "/" + NfvDeployer.linksPath));
		
		this.nffgNodes.put(name, new ConcurrentHashMap<>());

		this.nffgLinks.put(name, new Links());

		return nffg;
		} finally {
			nffgLock.writeLock().unlock();
		}
	}

	public Node createNode(String nffg, String nodeName, String type, boolean replace) throws AlreadyLoadedException, NotDefinedException {
		boolean exists = this.nodeIds.containsKey(nodeName);
		
		if (exists && !replace) throw new AlreadyLoadedException("Node " + nodeName + " already loaded");
		// the node has not already been created, nodes are unique system-wide

		if (!vnfs.containsKey(type)) throw new NotDefinedException("VNF type " + type + " does not exist");
		
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
					//should not happen
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

	public Node deleteNode(String name) {
		throw new ServerErrorException(501);
	}
	
	public Node undeployNffg(String name) {
		throw new ServerErrorException(501);
	}

	//only a thread at a time will be able to perform deployment
	public synchronized Nffg deployNffg(NewNffg nffg) throws Exception {
		// TODO Auto-generated method stub
		Stack<Object> createdObjects = new Stack<>();
		Nffg res = createNffg(nffg.getName(), baseUri + nffgsPath + "/" + nffg.getName());
		try {
			// load all nodes
			
			
			for (it.polito.dp2.NFV.sol3.model.NewNffg.Nodes.Node node : nffg.getNodes().getNode()) {
				createdObjects.push(createNode(nffg.getName(), node.getName(), node.getVnf(), false));
				createdObjects.push(allocateNode(node.getName(), node.getPreferredHost()));
				
			}
			Map<String, Link> links = new HashMap<>();
			this.linkIds.put(nffg.getName(), links);
			for (it.polito.dp2.NFV.sol3.model.NewNffg.Links.Link link : nffg.getLinks().getLink()) {
				createdObjects.push(createLink(nffg.getName(), link.getName(), link.getSourceNode(), link.getDestinationNode(), link.getRequiredLatency().intValue(), link.getRequiredThroughput(), false));
			}
			
			res.setDeployTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
			createdObjects.push(res);
			this.nffgMap.put(nffg.getName(), res);

			
			createdObjects.clear();
			return res;
		} catch (Exception e) {
			throw e;
		}
		
		finally {
			//cleanup stack - delete objects if deployment unsuccessful
			for (Object o : createdObjects) {
				if (o.getClass().equals(String.class)) {
					
				}
				if (o.getClass().equals(Link.class)) {
					
				}
				if (o.getClass().equals(Node.class)) {
					
				}
				if (o.getClass().equals(Nffg.class)) {
					
				}
				if (o.getClass().equals(HashMap.class)) {
					
				}
			}
		}

	}
	
	public boolean isDeployed(String name) {
		if (!nffgMap.containsKey(name)) return false;
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
		if (linkIds.containsKey(graphName)) {
			if (linkIds.get(graphName).containsKey(linkName)) {
				return linkIds.get(graphName).get(linkName);
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
		// TODO Auto-generated method stub
		if (nffgMap.containsKey(name)) {
			if (isDeployed(name)) {
				return nffgMap.get(name);
			}
		}
		
		throw new NotFoundException("No NF-FG defined with name " + name);
	}

	public Nffgs getNffgs(Date from) {
		Nffgs set = new Nffgs();
		
		//always only deployed nffgs are shown. In case of an incomplete deployment
		//a nffg will be present in the list but never shown GETting the resource,
		//in this way the calling function has possibility of deleting the nffg
		for (Nffg n : nffgMap.values()) {
			if (n.getDeployTime() != null) {
				if (from == null) {
					set.getNffg().add(createNamedRef(n.getName(), baseUri + nffgsPath + "/" + n.getName()));
				} else {
					if (nffgMap.get(n.getName()).getDeployTime().toGregorianCalendar().getTime().compareTo(from) >= 0) {
						set.getNffg().add(createNamedRef(n.getName(), baseUri + nffgsPath + "/" + n.getName()));
					}
				}
			}
		}

		return set;

	}

	public Nfv getNfv() {
		return this.nfv;
	}

	public Node getNode(String graph, String name) {
		if (!isDeployed(graph)) throw new NotFoundException("No NF-FG defined with name " + graph);
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
		if (!isDeployed(graph)) throw new NotFoundException("No nffg defined with name " + graph);
		if (nodeIds.containsKey(nodeName)) {

			Hosts result = new Hosts();

			// call reachablenodes api
			it.polito.dp2.NFV.sol3.data.Nodes reachableHosts = dataApi.nodeNodeidReachableNodes(nodeIds.get(nodeName)).getAsNodes(null,
					"Host");
			for (it.polito.dp2.NFV.sol3.data.Nodes.Node n : reachableHosts.getNode()) {
				String hostName = null;
				for (Property p : n.getProperties().getProperty()) {
					if (p.getName().equals("name"))
						hostName = p.getValue();
				}
				if (hostName == null)
					throw new InternalServerErrorException("Node " + n.getId() + " has no property 'name'");

				result.getHost().add(this.hostMap.get(hostName));
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

	public Link replaceLink(Link link) {
		// TODO Auto-generated method stub
		return null;
	}

}
