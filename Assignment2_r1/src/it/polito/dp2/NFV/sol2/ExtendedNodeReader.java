package it.polito.dp2.NFV.sol2;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import it.polito.dp2.NFV.HostReader;
import it.polito.dp2.NFV.LinkReader;
import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.NfvReader;
import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.VNFTypeReader;
import it.polito.dp2.NFV.lab2.NoGraphException;
import it.polito.dp2.NFV.lab2.ServiceException;
import it.polito.dp2.NFV.sol2.Neo4JSimpleXML.Data;
import it.polito.dp2.NFV.sol2.Nodes.Node;

public class ExtendedNodeReader implements it.polito.dp2.NFV.lab2.ExtendedNodeReader {

	private String id;
	private String name;
	private NodeReader node;
	private NfvReader nfv;
	private Data dataApi;
	private Map<String, NffgReader> loadedGraphs;

	public ExtendedNodeReader(String id, String nodeName, String nffgName, NfvReader nfv,
			Map<String, NffgReader> loadedGraphs, Data dataApi) {
		this.id = id;
		this.name = nffgName;
		this.nfv = nfv;
		this.dataApi = dataApi;
		this.loadedGraphs = loadedGraphs;
		this.node = nfv.getNffg(nffgName).getNode(nodeName);
	}

	@Override
	public VNFTypeReader getFuncType() {
		return this.node.getFuncType();
	}

	@Override
	public HostReader getHost() {
		return this.node.getHost();
	}

	@Override
	public Set<LinkReader> getLinks() {
		return this.node.getLinks();
	}

	@Override
	public NffgReader getNffg() {
		return this.node.getNffg();
	}

	@Override
	public String getName() {
		return this.node.getName();
	}

	@Override
	public Set<HostReader> getReachableHosts() throws NoGraphException, ServiceException {
		if (!loadedGraphs.containsKey(name))
			throw new NoGraphException("NF-FG " + name + " has not been loaded");

		Set<HostReader> resultSet = new HashSet<HostReader>();

		// call reachablenodes api
		Nodes reachableHosts = dataApi.nodeNodeidReachableNodes(id).getAsNodes(null, "Host");
		for (Node n : reachableHosts.getNode()) {
			String hostName = null;
			for (Property p : n.getProperties().getProperty()) {
				if (p.getName().equals("name"))
					hostName = p.getValue();
			}
			if (hostName == null)
				throw new ServiceException("Node " + n.getId() + " has no property 'name'");

			resultSet.add(this.nfv.getHost(hostName));
		}

		return resultSet;
	}

}
