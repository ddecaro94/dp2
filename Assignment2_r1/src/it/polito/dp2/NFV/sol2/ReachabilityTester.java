package it.polito.dp2.NFV.sol2;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.management.relation.Relation;
import javax.ws.rs.WebApplicationException;
import com.sun.jersey.api.client.ClientResponse;

import it.polito.dp2.NFV.NfvReaderFactory;
import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.FactoryConfigurationError;
import it.polito.dp2.NFV.HostReader;
import it.polito.dp2.NFV.LinkReader;
import it.polito.dp2.NFV.NfvReader;
import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.NfvReaderException;
import it.polito.dp2.NFV.lab2.AlreadyLoadedException;
import it.polito.dp2.NFV.lab2.ExtendedNodeReader;
import it.polito.dp2.NFV.lab2.NoGraphException;
import it.polito.dp2.NFV.lab2.ReachabilityTesterException;
import it.polito.dp2.NFV.lab2.ServiceException;
import it.polito.dp2.NFV.lab2.UnknownNameException;
import it.polito.dp2.NFV.sol2.Neo4JSimpleXML.Data;

public class ReachabilityTester implements it.polito.dp2.NFV.lab2.ReachabilityTester {

	private enum NodeType {
		NODE, HOST
	};

	private enum RelationshipType {
		ALLOCATION, LINK
	};

	private NfvReader nfv;
	private Map<String, NffgReader> loadedGraphs;
	private Map<String, String> allNodeIDs;
	private Map<String, String> hostIDs;
	private Map<String, Map<String, String>> nffgNodeIDs;
	private Map<String, ExtendedNodeReader> extendedReaders;
	private Data dataApi;

	public ReachabilityTester() throws ReachabilityTesterException {
		try {
			this.nfv = NfvReaderFactory.newInstance().newNfvReader();
		} catch (NfvReaderException e) {
			throw new ReachabilityTesterException(e);
		} catch (FactoryConfigurationError e) {
			throw new ReachabilityTesterException(e.getMessage());
		}

		this.extendedReaders = new HashMap<String, ExtendedNodeReader>();
		this.loadedGraphs = new HashMap<String, NffgReader>();
		this.nffgNodeIDs = new HashMap<String, Map<String, String>>();
		this.allNodeIDs = new HashMap<String, String>();
		this.hostIDs = new HashMap<String, String>();

		this.dataApi = Neo4JSimpleXML.data(Neo4JSimpleXML.createClient(),
				URI.create(System.getProperty("it.polito.dp2.NFV.lab2.URL")));
	}

	@Override
	public void loadGraph(String nffgName) throws UnknownNameException, AlreadyLoadedException, ServiceException {

		if (nffgName == null)
			throw new UnknownNameException("No graph name specified");
		if (nfv.getNffg(nffgName) == null)
			throw new UnknownNameException("Graph name: '" + nffgName + "' does not exist");

		if (isLoaded(nffgName))
			throw new AlreadyLoadedException("Graph name: '" + nffgName + "' is already loaded");

		Map<String, String> nodeIDs = new HashMap<String, String>();

		NffgReader graph = nfv.getNffg(nffgName);
		// load all nodes
		for (NodeReader node : graph.getNodes()) {
			if (!this.allNodeIDs.containsKey(node.getName())) {
				// the node has not already been created, nodes are unique system-wide
				try {
					//create node on database
					String nodeID = loadNode(node.getName(), NodeType.NODE);

					//add ID
					nodeIDs.put(node.getName(), nodeID);
					this.allNodeIDs.put(node.getName(), nodeID);

					// create ExtendedReader
					this.extendedReaders.put(nodeID, new it.polito.dp2.NFV.sol2.ExtendedNodeReader(nodeID,
							node.getName(), nffgName, nfv, this.loadedGraphs, dataApi));

					// if node is deployed create hosting node
					if (node.getHost() != null) {
						HostReader host = nfv.getHost(node.getHost().getName());
						if (host != null) {
							if (!this.hostIDs.containsKey(host.getName())) {
								// the host has not already been created in the database
								try {
									//create host type node
									String hostID = loadNode(host.getName(), NodeType.HOST);
									//add to host registry
									this.hostIDs.put(host.getName(), hostID);

								} catch (WebApplicationException e) {
									throw new ServiceException(e);
								}
							}

							// create allocation relationship
							createRelationship(nodeID, this.hostIDs.get(node.getHost().getName()),
									RelationshipType.ALLOCATION);
						}
					}

				} catch (WebApplicationException e) {
					throw new ServiceException(e);
				}
			} else {
				// add to the nffg node subset the id of the global node
				nodeIDs.put(node.getName(), this.allNodeIDs.get(node.getName()));
			}
		}

		// create link id map
		Map<String, String> linkIDs = new HashMap<String, String>();

		// create relationships
		for (NodeReader node : graph.getNodes()) {
			// create links having the node as source
			for (LinkReader link : node.getLinks()) {
				if (!linkIDs.containsKey(link.getName())) {

					try {
						
						//create link relationship
						String linkID = createRelationship(this.allNodeIDs.get(node.getName()),
								this.allNodeIDs.get(link.getDestinationNode().getName()), RelationshipType.LINK);
						//add link to registry
						linkIDs.put(link.getName(), linkID);

					} catch (WebApplicationException e) {
						throw new ServiceException(e);
					}
				}
			}

		}

		this.nffgNodeIDs.put(nffgName, nodeIDs);
		this.loadedGraphs.put(nffgName, graph);

	}

	@Override
	public Set<ExtendedNodeReader> getExtendedNodes(String nffgName)
			throws UnknownNameException, NoGraphException, ServiceException {

		if (nffgName == null)
			throw new UnknownNameException("No graph name specified");
		if (nfv.getNffg(nffgName) == null)
			throw new UnknownNameException("Graph name: '" + nffgName + "' does not exist");
		if (!isLoaded(nffgName))
			throw new NoGraphException("Graph name: '" + nffgName + "' has not been loaded");

		if (this.nffgNodeIDs.get(nffgName) == null) {
			throw new NoGraphException("Graph name: '" + nffgName + "' has no nodes loaded");
		}

		if (this.nffgNodeIDs.get(nffgName).isEmpty()) {
			throw new NoGraphException("Graph name: '" + nffgName + "' has no nodes loaded");
		}

		Set<ExtendedNodeReader> nffgNodes = new HashSet<ExtendedNodeReader>();

		for (String id : this.nffgNodeIDs.get(nffgName).values()) {
			// create an ExtendedNode for each nodeID
			nffgNodes.add(this.extendedReaders.get(id));
		}

		return nffgNodes;
	}

	@Override
	public boolean isLoaded(String nffgName) throws UnknownNameException {

		if (nffgName == null)
			throw new UnknownNameException("No graph name specified");
		if (nfv.getNffg(nffgName) == null)
			throw new UnknownNameException("Graph name: '" + nffgName + "' does not exist");

		if (this.loadedGraphs.containsKey(nffgName))
			return true;
		else
			return false;
	}

	private String loadNode(String name, NodeType type) throws ServiceException {
		Node requestedNode = new Node();
		Labels labels = new Labels();
		Properties properties = new Properties();
		Property nodeName = new Property();

		switch (type) {
		case NODE:
			labels.getLabel().add("Node");
			break;
		case HOST:
			labels.getLabel().add("Host");
			break;
		default:
			throw new RuntimeException("Wrong node type!");
		}

		nodeName.setName("name");
		nodeName.setValue(name);
		properties.getProperty().add(nodeName);
		requestedNode.setProperties(properties);

		ClientResponse r = this.dataApi.node().postXml(requestedNode, ClientResponse.class);
		if (r.getStatus() >= 400)
			throw new ServiceException("Unable to create node with name " + name);

		Node createdNode = r.getEntity(Node.class);

		if (createdNode == null)
			throw new ServiceException("Unable to create node with name " + name);
		if (createdNode.getId() == null)
			throw new ServiceException("Unable to retrieve nodeID for node with name " + name);

		// add labels for the node, replace if existing
		r = this.dataApi.nodeNodeidLabels(createdNode.getId()).putXml(labels, ClientResponse.class);
		if (r.getStatus() >= 400)
			throw new ServiceException("Unable to create labels for node with id " + createdNode.getId());

		return createdNode.getId();
	}

	private String createRelationship(String sourceID, String destinationID, RelationshipType type)
			throws ServiceException {
		// create allocation relationship
		Relationship rel = new Relationship();
		switch (type) {
		case ALLOCATION:
			rel.setType("AllocatedOn");
			break;
		case LINK:
			rel.setType("ForwardsTo");
			break;

		default:
			throw new RuntimeException("Wrong relationship type!");
		}

		rel.setDstNode(destinationID);
		// call webservice
		ClientResponse resp = this.dataApi.nodeNodeidRelationships(sourceID).postXml(rel, ClientResponse.class);

		//check status code
		if (resp.getStatus() >= 400)
			throw new ServiceException("Unable to create relationship between nodes " + sourceID + " and "
					+ destinationID + "; error code " + resp.getStatus());

		//parse as entity
		Relationship result = resp.getEntity(Relationship.class);
		if (result == null)
			throw new ServiceException(
					"Unable to create relationship between nodes " + sourceID + " and " + destinationID);

		return result.getId();
	}

}
