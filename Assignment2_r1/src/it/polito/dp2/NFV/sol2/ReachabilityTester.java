package it.polito.dp2.NFV.sol2;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.Client;
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
import it.polito.dp2.NFV.lab2.ServiceException;
import it.polito.dp2.NFV.lab2.UnknownNameException;
import it.polito.dp2.NFV.sol2.Neo4JSimpleXML.Data;

public class ReachabilityTester implements it.polito.dp2.NFV.lab2.ReachabilityTester {

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
		
		this.dataApi = Neo4JSimpleXML.data(Neo4JSimpleXML.createClient() , URI.create(System.getProperty("it.polito.dp2.NFV.lab2.URL")));
	}

	@Override
	public void loadGraph(String nffgName) throws UnknownNameException, AlreadyLoadedException, ServiceException {

		if (nffgName == null) throw new UnknownNameException("No graph name specified");
		if (nfv.getNffg(nffgName) == null) throw new UnknownNameException("Graph name: '"+nffgName+"' does not exist");
		
		if(isLoaded(nffgName)) throw new AlreadyLoadedException("Graph name: '"+nffgName+"' is already loaded");
		
		Map<String, String> nodeIDs = new HashMap<String, String>();
		
		//load all hosts
		for (HostReader host : nfv.getHosts()) {
			if (!this.hostIDs.containsKey(host.getName())) {
				//the host has not already been created in the database
				try {
					Node requestedHost = new Node();
					Labels labels = new Labels();
					labels.getLabel().add("Host");
					Properties properties = new Properties();
					Property name = new Property();
					name.setName("name");
					name.setValue(host.getName());
					properties.getProperty().add(name);
					requestedHost.setProperties(properties);
					
					Node createdHost = this.dataApi.node().postXml(requestedHost, Node.class);
					
					if (createdHost == null) throw new ServiceException("Unable to create node with name "+host.getName());
					if (createdHost.getId() == null) throw new ServiceException("Unable to retrieve nodeID for node with name "+host.getName());
					
					this.hostIDs.put(host.getName(), createdHost.getId());
					
					//add labels to the node, replace if existing
					ClientResponse r = this.dataApi.nodeNodeidLabels(createdHost.getId()).putXml(labels, ClientResponse.class);
					if (r.getStatus() >= 400) throw new ServiceException("Unable to create labels for node with id "+createdHost.getId());
					
				} catch (WebApplicationException e) {
					throw new ServiceException(e);
				}
			}
		}
		
		NffgReader graph = nfv.getNffg(nffgName);
		//load all nodes
		for (NodeReader node : graph.getNodes()) {
			if (!this.allNodeIDs.containsKey(node.getName())) {
				//the node has not already been created, nodes are unique system-wide
				try {
					Node requestedNode = new Node();
					Labels labels = new Labels();
					labels.getLabel().add("Node");
					Properties properties = new Properties();
					Property name = new Property();
					name.setName("name");
					name.setValue(node.getName());
					properties.getProperty().add(name);
					requestedNode.setProperties(properties);
					
					Node createdNode = this.dataApi.node().postXml(requestedNode, Node.class);
					
					if (createdNode == null) throw new ServiceException("Unable to create node with name "+node.getName());
					if (createdNode.getId() == null) throw new ServiceException("Unable to retrieve nodeID for node with name "+node.getName());
					
					nodeIDs.put(node.getName(), createdNode.getId());
					this.allNodeIDs.put(node.getName(), createdNode.getId());
					
					//add labels for the node, replace if existing
					ClientResponse r = this.dataApi.nodeNodeidLabels(createdNode.getId()).putXml(labels, ClientResponse.class);
					if (r.getStatus() >= 400) throw new ServiceException("Unable to create labels for node with id "+createdNode.getId());
					
					//create ExtendedReader
					this.extendedReaders.put(createdNode.getId(), new it.polito.dp2.NFV.sol2.ExtendedNodeReader(createdNode.getId(), node.getName(), nffgName, nfv, this.loadedGraphs, dataApi));
					
				} catch (WebApplicationException e) {
					throw new ServiceException(e);
				}
			} else {
				//add to the nffg node subset the id of the global node
				nodeIDs.put(node.getName(), this.allNodeIDs.get(node.getName()));
			}
		}
		
		//create link id map
		Map<String, String> linkIDs = new HashMap<String, String>();
		
		//create relationships
		for (NodeReader node : graph.getNodes()) {
			//create links having the node as source
			for (LinkReader link : node.getLinks()) {
				if (!linkIDs.containsKey(link.getName())) {

					try {
						Relationship requestedLink = new Relationship();
	
						requestedLink.setDstNode(this.allNodeIDs.get(link.getDestinationNode().getName()));
						requestedLink.setType("ForwardsTo");
						
						Relationship createdLink = this.dataApi.nodeNodeidRelationships(this.allNodeIDs.get(node.getName())).postXml(requestedLink, Relationship.class);
	
						linkIDs.put(link.getName(), createdLink.getId());
						
					} catch (WebApplicationException e) {
						throw new ServiceException(e);
					}
				}
			}
			
			//if the node is deployed link the node to the host
			if (node.getHost() != null) {
				try {
					Relationship allocatedOn = new Relationship();

					allocatedOn.setDstNode(this.hostIDs.get(node.getHost().getName()));
					allocatedOn.setType("AllocatedOn");
					
					//create relationship
					this.dataApi.nodeNodeidRelationships(this.allNodeIDs.get(node.getName())).postXml(allocatedOn, Relationship.class);
					
				} catch (WebApplicationException e) {
					throw new ServiceException(e);
				}
			}
			
			
		}
		
		this.nffgNodeIDs.put(nffgName, nodeIDs);
		this.loadedGraphs.put(nffgName, graph);

	}

	@Override
	public Set<ExtendedNodeReader> getExtendedNodes(String nffgName)
			throws UnknownNameException, NoGraphException, ServiceException {
		// TODO Auto-generated method stub
		if (nffgName == null) throw new UnknownNameException("No graph name specified");
		if (nfv.getNffg(nffgName) == null) throw new UnknownNameException("Graph name: '"+nffgName+"' does not exist");
		if(!isLoaded(nffgName)) throw new NoGraphException("Graph name: '"+nffgName+"' has not been loaded");
		
		if (this.nffgNodeIDs.get(nffgName) == null) {
			throw new NoGraphException("Graph name: '"+nffgName+"' has no nodes loaded");
		}
		
		if (this.nffgNodeIDs.get(nffgName).isEmpty()) {
			throw new NoGraphException("Graph name: '"+nffgName+"' has no nodes loaded");
		}
		
		Set<ExtendedNodeReader> nffgNodes = new HashSet<ExtendedNodeReader>();
		
		for (String id : this.nffgNodeIDs.get(nffgName).values()) {
			//create an ExtendedNode for each nodeID
			nffgNodes.add(this.extendedReaders.get(id));
		}
		
		return nffgNodes;
	}

	@Override
	public boolean isLoaded(String nffgName) throws UnknownNameException {
		// TODO Auto-generated method stub
		if (nffgName == null) throw new UnknownNameException("No graph name specified");
		if (nfv.getNffg(nffgName) == null) throw new UnknownNameException("Graph name: '"+nffgName+"' does not exist");
		
		if(this.loadedGraphs.containsKey(nffgName)) return true;
		else return false;
	}

}
