package it.polito.dp2.NFV.sol3.client1;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import com.sun.jersey.api.client.WebResource;
import it.polito.dp2.NFV.HostReader;
import it.polito.dp2.NFV.LinkReader;
import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.VNFTypeReader;
import it.polito.dp2.NFV.sol3.client1.data.Link;
import it.polito.dp2.NFV.sol3.client1.data.NfvDeployer;
import it.polito.dp2.NFV.sol3.client1.data.NfvDeployer.Index.Nffgs.NffgName.Nodes.NodeName.Links;
import it.polito.dp2.NFV.sol3.client1.data.Node;

public class NodeReader implements it.polito.dp2.NFV.NodeReader {
	
	private String nodeName;
	private URI graphUri;
	private URI vnfUri;
	private WebResource nodeService;
	private Links linksService;
	
	public NodeReader(URI nodeUri) {
		nodeService = NfvDeployer.createClient().resource(nodeUri);
		Node n = nodeService.get(Node.class);
		this.linksService = new Links(NfvDeployer.createClient(), nodeUri);
		this.graphUri = URI.create(n.getNffg().getHref());
		this.vnfUri = URI.create(n.getVnf().getHref());
		this.nodeName = n.getName();		
	}

	@Override
	public String getName() {
		return this.nodeName;
	}

	@Override
	public VNFTypeReader getFuncType() {
		return new it.polito.dp2.NFV.sol3.client1.VNFTypeReader(vnfUri);
	}

	@Override
	public HostReader getHost() {
		return new it.polito.dp2.NFV.sol3.client1.HostReader(URI.create(this.nodeService.get(Node.class).getHost().getHref()));
	}

	@Override
	public Set<LinkReader> getLinks() {
		Set<LinkReader> res = new HashSet<>();
		for (Link l : this.linksService.getAsLinksXml().getLink()) {
			res.add(new it.polito.dp2.NFV.sol3.client1.LinkReader(URI.create(l.getHref()), graphUri.resolve("nodes")));
		}
		return res;
	}

	@Override
	public NffgReader getNffg() {
		URI graphUri = URI.create(nodeService.get(Node.class).getNffg().getHref());
		return new it.polito.dp2.NFV.sol3.client1.NffgReader(graphUri);
	}

}
