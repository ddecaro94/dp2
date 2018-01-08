package it.polito.dp2.NFV.sol2;

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

public class ExtendedNodeReader implements it.polito.dp2.NFV.lab2.ExtendedNodeReader {

	private String id;
	private NodeReader node;
	private Data nodeId;
	private NfvReader graph;
	
	public ExtendedNodeReader(String id, NfvReader graph, NodeReader r) {
		// TODO Auto-generated constructor stub
		this.node = r;
		this.id = id;
		this.graph = graph;
		this.nodeId = new Neo4JSimpleXML().data();
	}

	@Override
	public VNFTypeReader getFuncType() {
		// TODO Auto-generated method stub
		return this.node.getFuncType();
	}

	@Override
	public HostReader getHost() {
		// TODO Auto-generated method stub
		return this.node.getHost();
	}

	@Override
	public Set<LinkReader> getLinks() {
		// TODO Auto-generated method stub
		return this.node.getLinks();
	}

	@Override
	public NffgReader getNffg() {
		// TODO Auto-generated method stub
		return this.node.getNffg();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.node.getName();
	}

	@Override
	public Set<HostReader> getReachableHosts() throws NoGraphException, ServiceException {
		// TODO Auto-generated method stub
		nodeId.nodeNodeidReachableNodes(id);
		return null;
	}

}
