package it.polito.dp2.NFV.sol1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import it.polito.dp2.NFV.HostReader;
import it.polito.dp2.NFV.LinkReader;
import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.NfvReader;
import it.polito.dp2.NFV.VNFTypeReader;
import it.polito.dp2.NFV.sol1.jaxb.ConnectionType;
import it.polito.dp2.NFV.sol1.jaxb.HostType;
import it.polito.dp2.NFV.sol1.jaxb.NodeType;
import it.polito.dp2.NFV.sol1.jaxb.VnfType;

public class NodeReader implements it.polito.dp2.NFV.NodeReader {
	private NodeType node;
	private NffgReader parentGraph;
	private NfvReader nfv;
	private Map<String, LinkReader> links;

	public NodeReader(NodeType t, NffgReader graph, NfvReader nfv) {
		this.node = t;
		this.parentGraph = graph;
		this.nfv = nfv;
		this.links = new HashMap<String, LinkReader>();
		for (ConnectionType link : this.node.getLink()) {
			this.links.put(link.getName(), new it.polito.dp2.NFV.sol1.LinkReader(link, this, nfv));
		}
	}

	@Override
	public VNFTypeReader getFuncType() {
		for (VNFTypeReader type : this.nfv.getVNFCatalog()) {
			if (type.getName().equals(((VnfType) this.node.getVnf()).getName())) {
				return type;
			}
		}
		// MUST NOT HAPPEN, existence of the specified type is granted by the XML Schema
		// validation
		return null;
	}

	@Override
	public HostReader getHost() {
		for (HostReader host : this.nfv.getHosts()) {
			if (host.getName().equals(((HostType) this.node.getHost()).getName())) {
				return host;
			}
		}
		// MUST NOT HAPPEN, existence of the specified host is granted by the XML Schema
		// validation
		return null;
	}

	@Override
	public Set<LinkReader> getLinks() {
		return this.links.values().stream().collect(Collectors.toSet());
	}

	@Override
	public String getName() {
		return this.node.getName();
	}

	@Override
	public NffgReader getNffg() {
		return this.parentGraph;
	}

}
