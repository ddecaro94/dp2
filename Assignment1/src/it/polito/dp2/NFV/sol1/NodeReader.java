package it.polito.dp2.NFV.sol1;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.Unmarshaller;

import it.polito.dp2.NFV.HostReader;
import it.polito.dp2.NFV.LinkReader;
import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.VNFTypeReader;
import it.polito.dp2.NFV.sol1.jaxb.HostType;
import it.polito.dp2.NFV.sol1.jaxb.NffgType;
import it.polito.dp2.NFV.sol1.jaxb.NodeType;
import it.polito.dp2.NFV.sol1.jaxb.VnfType;

public class NodeReader implements it.polito.dp2.NFV.NodeReader {
	private NodeType node;
	private NffgType parent;
	
	public NodeReader(NodeType t) {
		this.node = t;
	}
	
	public void afterUnmarshal(Unmarshaller u, Object parent) { 
	    this.parent = (NffgType) parent; 
	 }

	@Override
	public String getName() {
		return this.node.getName();
	}

	@Override
	public VNFTypeReader getFuncType() {
		return new VnfTypeReader((VnfType) this.node.getVnf());
	}

	@Override
	public HostReader getHost() {
		return new it.polito.dp2.NFV.sol1.HostReader((HostType) this.node.getHost());
	}

	@Override
	public Set<LinkReader> getLinks() {
		Set<LinkReader> linkSet = new HashSet<LinkReader>();
		linkSet.addAll(this.node.getLink().parallelStream().map(t->new it.polito.dp2.NFV.sol1.LinkReader(t)).collect(Collectors.toSet()));
		return linkSet;
	}

	@Override
	public NffgReader getNffg() {
		return new it.polito.dp2.NFV.sol1.NffgReader(this.parent);
	}

}
