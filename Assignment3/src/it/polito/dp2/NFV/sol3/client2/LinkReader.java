package it.polito.dp2.NFV.sol3.client2;

import java.net.URI;

import javax.ws.rs.WebApplicationException;

import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.lab3.ServiceException;
import it.polito.dp2.NFV.sol3.client2.data.Link;
import it.polito.dp2.NFV.sol3.client2.data.NamedEntity;
import it.polito.dp2.NFV.sol3.client2.data.NfvDeployer;
import it.polito.dp2.NFV.sol3.client2.data.Nodes;

public class LinkReader implements it.polito.dp2.NFV.LinkReader {

	private Link l;
	private URI source;
	private URI dest;
	
	public LinkReader(URI linkUri, URI nodesUri) throws ServiceException {
		try {
			l = NfvDeployer.createClient().resource(linkUri).get(Link.class);
			
			Nodes nodes = NfvDeployer.createClient().resource(nodesUri).get(Nodes.class);
			
			for (NamedEntity n : nodes.getNode()) {
				if (n.getName().equals(l.getSrc())) source = URI.create(n.getHref());
				if (n.getName().equals(l.getDst())) dest = URI.create(n.getHref());
			}
		} catch (WebApplicationException e) {
			throw new ServiceException();
		}
	}

	@Override
	public String getName() {
		return l.getName();
	}

	@Override
	public NodeReader getDestinationNode() {
		try {
			return new it.polito.dp2.NFV.sol3.client2.NodeReader(source);
		} catch (ServiceException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getLatency() {
		return (l.getRequiredLatency() != null)? l.getRequiredLatency().intValue() : 0;
	}

	@Override
	public NodeReader getSourceNode() {
		try {
			return new it.polito.dp2.NFV.sol3.client2.NodeReader(dest);
		} catch (ServiceException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public float getThroughput() {
		return l.getRequiredThroughput();
	}

}
