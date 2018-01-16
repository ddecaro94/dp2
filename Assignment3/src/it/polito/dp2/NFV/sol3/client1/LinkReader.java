package it.polito.dp2.NFV.sol3.client1;

import java.net.URI;

import com.sun.jersey.api.client.WebResource;

import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.sol3.client1.data.Link;
import it.polito.dp2.NFV.sol3.client1.data.NamedEntity;
import it.polito.dp2.NFV.sol3.client1.data.NfvDeployer;
import it.polito.dp2.NFV.sol3.client1.data.Nodes;

public class LinkReader implements it.polito.dp2.NFV.LinkReader {

	private WebResource linkService;
	private WebResource nodesService;
	
	public LinkReader(URI linkUri, URI nodesUri) {
		this.linkService = NfvDeployer.createClient().resource(linkUri);
		this.nodesService = NfvDeployer.createClient().resource(nodesUri);
	}

	@Override
	public String getName() {
		return linkService.get(Link.class).getName();
	}

	@Override
	public NodeReader getDestinationNode() {
		Nodes nodes = nodesService.get(Nodes.class);
		String dst = linkService.get(Link.class).getDst();
		for (NamedEntity node : nodes.getNode()) {
			if (node.getName().equals(dst)) {
				return new it.polito.dp2.NFV.sol3.client1.NodeReader(URI.create(node.getHref()));
			}
		}
		return null;
	}

	@Override
	public int getLatency() {
		return linkService.get(Link.class).getRequiredLatency().intValue();
	}

	@Override
	public NodeReader getSourceNode() {
		Nodes nodes = nodesService.get(Nodes.class);
		String dst = linkService.get(Link.class).getSrc();
		for (NamedEntity node : nodes.getNode()) {
			if (node.getName().equals(dst)) {
				return new it.polito.dp2.NFV.sol3.client1.NodeReader(URI.create(node.getHref()));
			}
		}
		return null;
	}

	@Override
	public float getThroughput() {
		return linkService.get(Link.class).getRequiredThroughput();
	}

}
