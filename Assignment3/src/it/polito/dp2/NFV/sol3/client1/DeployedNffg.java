/**
 * 
 */
package it.polito.dp2.NFV.sol3.client1;

import java.net.URI;
import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;

import com.sun.jersey.api.client.WebResource;

import it.polito.dp2.NFV.LinkReader;
import it.polito.dp2.NFV.NffgReader;
import it.polito.dp2.NFV.NodeReader;
import it.polito.dp2.NFV.VNFTypeReader;
import it.polito.dp2.NFV.lab3.AllocationException;
import it.polito.dp2.NFV.lab3.LinkAlreadyPresentException;
import it.polito.dp2.NFV.lab3.NoNodeException;
import it.polito.dp2.NFV.lab3.ServiceException;
import it.polito.dp2.NFV.sol3.client1.data.Link;
import it.polito.dp2.NFV.sol3.client1.data.NamedEntity;
import it.polito.dp2.NFV.sol3.client1.data.Nffg;
import it.polito.dp2.NFV.sol3.client1.data.NfvDeployer;
import it.polito.dp2.NFV.sol3.client1.data.NfvDeployer.Index.Nffgs.NffgName;
import it.polito.dp2.NFV.sol3.client1.data.NfvDeployer.Index.Nffgs.NffgName.Links;
import it.polito.dp2.NFV.sol3.client1.data.Node;

public class DeployedNffg implements it.polito.dp2.NFV.lab3.DeployedNffg {

	private URI graphUri;
	private URI linksUri;
	private URI nodesUri;
	private WebResource nodesService;
	private Links linksService;
	
	
	public DeployedNffg(Nffg graph) {
		this.graphUri = URI.create(graph.getHref());
		this.linksUri = URI.create(graph.getLinks().getHref());
		this.nodesUri = URI.create(graph.getNodes().getHref());
		nodesService = NfvDeployer.createClient().resource(nodesUri);
		linksService = new NffgName.Links(NfvDeployer.createClient(), graphUri);
	}


	@Override
	public NodeReader addNode(VNFTypeReader type, String hostName) throws AllocationException, ServiceException {
		Node node = new Node();
		NamedEntity host = new NamedEntity();
		host.setName(hostName);
		node.setHost(host);
		
		NamedEntity vnf = new NamedEntity();
		vnf.setName(type.getName());
		node.setVnf(vnf);
		
		Node n;
		
		try {
			n = nodesService.post(Node.class, node);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return new it.polito.dp2.NFV.sol3.client1.NodeReader(URI.create(n.getHref()));
	}

	@Override
	public LinkReader addLink(NodeReader source, NodeReader dest, boolean overwrite)
			throws NoNodeException, LinkAlreadyPresentException, ServiceException {
		// TODO Auto-generated method stub
		Link l = new Link();
		l.setName("Link"+RandomUtils.nextInt());
		l.setSrc(source.getName());
		l.setDst(dest.getName());
		
		
		Link createdLink;
		
		if (overwrite) {
			createdLink = linksService.linkName(l.getName()).putXmlAsLink(l);
		} else {
			createdLink = linksService.postXmlAsLink(l);
		}
		
		return new it.polito.dp2.NFV.sol3.client1.LinkReader(URI.create(createdLink.getHref()), nodesUri);
	}

	@Override
	public NffgReader getReader() throws ServiceException {
		return new it.polito.dp2.NFV.sol3.client1.NffgReader(graphUri);
	}

}
