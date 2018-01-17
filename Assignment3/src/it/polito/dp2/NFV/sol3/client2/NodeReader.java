package it.polito.dp2.NFV.sol3.client2;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.WebApplicationException;

import it.polito.dp2.NFV.HostReader;
import it.polito.dp2.NFV.LinkReader;
import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.lab3.ServiceException;
import it.polito.dp2.NFV.sol3.client2.data.Links;
import it.polito.dp2.NFV.sol3.client2.VNFTypeReader;
import it.polito.dp2.NFV.sol3.client2.data.Link;
import it.polito.dp2.NFV.sol3.client2.data.NfvDeployer;
import it.polito.dp2.NFV.sol3.client2.data.Node;

public class NodeReader implements it.polito.dp2.NFV.NodeReader {

	private Node node;
	private VNFTypeReader vnf;
	private URI host;
	private URI nffg;
	private Set<LinkReader> links;
	
	public NodeReader(URI uri) throws ServiceException {
		links = new HashSet<>();
		
		try {
			node = NfvDeployer.createClient().resource(uri).get(Node.class);
			vnf = new VNFTypeReader(URI.create(node.getVnf().getHref()));
			host = URI.create(node.getHost().getHref());
			URI nffgUri = URI.create(node.getNffg().getHref().concat("/nodes")).normalize();
			
			for (it.polito.dp2.NFV.sol3.client2.data.Link l : NfvDeployer.createClient().resource(node.getLinks().getHref()).get(Links.class).getLink()) {
				links.add(new it.polito.dp2.NFV.sol3.client2.LinkReader(URI.create(l.getHref()), nffgUri ));
			}
			
		} catch (WebApplicationException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public String getName() {
		return node.getName();
	}

	@Override
	public VNFTypeReader getFuncType() {
		return vnf;
	}

	@Override
	public HostReader getHost() {
		return new it.polito.dp2.NFV.sol3.client1.HostReader(host);
	}

	@Override
	public Set<LinkReader> getLinks() {
		return links;
	}

	@Override
	public NffgReader getNffg() {
		try {
			return new it.polito.dp2.NFV.sol3.client2.NffgReader(nffg);
		} catch (ServiceException e) {
			e.printStackTrace();
			return null;
		}
	}

}
