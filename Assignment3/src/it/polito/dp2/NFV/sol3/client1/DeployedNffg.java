/**
 * 
 */
package it.polito.dp2.NFV.sol3.client1;

import java.net.URI;
import javax.ws.rs.WebApplicationException;

import org.apache.commons.lang.math.RandomUtils;

import com.sun.jersey.api.client.ClientResponse;
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

	private String graphName;
	private URI graphUri;
	private URI nodesUri;
	private WebResource nodesService;
	private Links linksService;
	
	
	public DeployedNffg(Nffg graph) {
		this.graphName = graph.getName();
		this.graphUri = URI.create(graph.getHref());
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
		node.setName(vnf.getName()+graphName);
		
		
		try {
			System.out.println("Adding node: host "+hostName+", type "+type.getName());
			ClientResponse resp = nodesService.post(ClientResponse.class, node);
			
			if (resp.getStatus() >= 500) throw new ServiceException(resp.getStatusInfo().getReasonPhrase());
			if (resp.getStatus() == 409) throw new AllocationException(resp.getEntity(String.class));
			if (resp.getStatus() >= 400) throw new ServiceException(resp.getEntity(String.class));
			
			if (resp.getStatus() == 200) return new it.polito.dp2.NFV.sol3.client1.NodeReader(URI.create(resp.getEntity(Node.class).getHref()));
			
			throw new ServiceException();
			
		} catch (WebApplicationException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public LinkReader addLink(NodeReader source, NodeReader dest, boolean overwrite)
			throws NoNodeException, LinkAlreadyPresentException, ServiceException {
		Link l = new Link();
		l.setName("Link"+source.getName()+dest.getName());
		l.setSrc(source.getName());
		l.setDst(dest.getName());
		
		//System.out.println("Adding link from "+source.getName()+" to "+dest.getName()+" with name "+l.getName()+" - overwrite: "+overwrite);
		Link createdLink;
		ClientResponse resp;
		if (overwrite) {
			resp = linksService.linkName(l.getName()).putXml(l, ClientResponse.class);

		} else {
			resp = linksService.postXml(l, ClientResponse.class);
		}
		
		if (resp.getStatus() >= 500) throw new ServiceException(resp.getStatusInfo().getReasonPhrase());
		if (resp.getStatus() == 409) throw new LinkAlreadyPresentException(resp.getEntity(String.class));
		if (resp.getStatus() == 404) throw new NoNodeException(resp.getEntity(String.class));
		if (resp.getStatus() >= 400) throw new ServiceException(resp.getEntity(String.class));
		

		
		if (resp.getStatus() == 200) {
			createdLink = resp.getEntity(Link.class);
			return new it.polito.dp2.NFV.sol3.client1.LinkReader(URI.create(createdLink.getHref()), nodesUri);	
		}
		
		throw new ServiceException();
		
		
	}

	@Override
	public NffgReader getReader() throws ServiceException {
		return new it.polito.dp2.NFV.sol3.client1.NffgReader(graphUri);
	}

}
